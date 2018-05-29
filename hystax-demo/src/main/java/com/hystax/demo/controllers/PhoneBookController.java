package com.hystax.demo.controllers;

import com.hystax.demo.jpa.Phonebook;
import com.hystax.demo.util.JPAUtil;
import com.hystax.demo.wire.CalculateResponse;
import com.hystax.demo.wire.WireBook;
import net.spy.memcached.MemcachedClient;
import org.springframework.stereotype.Component;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class PhoneBookController {

    private final EntityManagerFactory emf;
    private EntityManager em;
    private MemcachedClient memClient = null;
    private static final String MEMCACHED_KEY = "HystaxDemoBookKey";
    private static final String PERSISTENT_UNIT_NAME = "OracleJPA";
//    private static final String SQL_REQUEST = "select pb.tid as ID, pb.FULL_NAME, pb.ADDRESS, pb.PHONE, pb.ID_NUMBER from (select ID as tid, FULL_NAME, ADDRESS, PHONE, ID_NUMBER, (select count(*) from phonebook pb2 where substr(pb2.phone, 4, 5) LIKE substr(phonebook.phone, 4, 5) group by substr(pb2.phone, 4, 5)) as cnt, substr(phonebook.phone, 4, 5) as code from phonebook order by cnt desc, code) pb";
    private static final String SQL_REQUEST = "select pb.tid as ID, pb.FULL_NAME, pb.ADDRESS, pb.PHONE, pb.ID_NUMBER from (select ID as tid, FULL_NAME, ADDRESS, PHONE, ID_NUMBER, (select count(*) from phonebook pb2 where pb2.phone = phonebook.phone group by substr(pb2.phone, 4, 5)) as cnt, substr(phonebook.phone, 4, 5) as code from phonebook order by cnt desc, code ) pb";

    public PhoneBookController() {
        emf = Persistence.createEntityManagerFactory(PERSISTENT_UNIT_NAME);
        em = emf.createEntityManager();
        try {
            Context initial_ctx = new InitialContext();
            String jndiMemcached = (String) initial_ctx.lookup("memcachedServers");
            List<InetSocketAddress> servers = getServers(jndiMemcached);
            if (servers.size() >0) {
                memClient = new MemcachedClient(servers);
            } else {
                memClient = null;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    public PhoneBookController(EntityManager em) {
        emf = null;
        this.em = em;
    }

    public List<WireBook> getBooks() {
        List<Phonebook> books = JPAUtil.getObjects(em, Collections.<String, Object>emptyMap(), null, Phonebook.class);
        List<WireBook> wireBooks = new ArrayList<WireBook>();
        for (Phonebook book : books) {
            wireBooks.add(book.toWire());
        }
        return wireBooks;
    }

    public WireBook getBook(Long id) {
        return _getBook(id).toWire();
    }

    private Phonebook _getBook(Long id) {
        return JPAUtil.getObject(em, "id", id, Phonebook.class);
    }

    public WireBook addBook(WireBook wireBook) {
        try {
            em.getTransaction().begin();
            Phonebook book = new Phonebook(wireBook);
            em.persist(book);
            em.getTransaction().commit();
            invalidateCache();
            return book.toWire();
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
    }

    public WireBook updateBook(Long id, WireBook wireBook) {
        try {
            em.getTransaction().begin();
            Phonebook book = _getBook(id);
            book.updateFromWire(wireBook);
            em.merge(book);
            em.getTransaction().commit();
            return getBook(id);
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
    }

    public WireBook removeBook(Long id) {
        try {
            em.getTransaction().begin();
            Phonebook book = _getBook(id);
            em.remove(book);
            em.getTransaction().commit();
            invalidateCache();
            return book.toWire();
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
    }

    public CalculateResponse calculate() {
        boolean isCached;
        long startTime = System.currentTimeMillis();
        List<WireBook> result = checkMemcached();
        if (result != null) {
            isCached = true;
        } else {
            isCached = false;
            result = getFromBase();
            putToMem(result);
        }
        long endTime = System.currentTimeMillis();
        return new CalculateResponse(result, isCached, endTime - startTime, SQL_REQUEST);
    }

    @SuppressWarnings("unchecked")
    private List<WireBook> checkMemcached() {
        if (memClient == null) {
            return null;
        }
        try {
            Object obj = memClient.get(MEMCACHED_KEY);
            if (obj != null) {
                if (obj instanceof ArrayList) {
                    return (List<WireBook>) obj;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    private void putToMem(List<WireBook> books) {
        if (memClient == null) {
            return;
        }
        try {
            int TTL_FOR_MEMCACHE_MINS = 20;
            memClient.set(MEMCACHED_KEY, TTL_FOR_MEMCACHE_MINS * 60, books);
        } catch (Exception ignored) {
        }
    }

    public String invalidateCache() {
        if (memClient == null) {
            return "Failed";
        }
        try {
            memClient.delete(MEMCACHED_KEY);
            return "Ok";
        } catch (Exception e) {
            return "Failed";
        }
    }
    @SuppressWarnings("unchecked")
    private List<WireBook> getFromBase() {
        List<Phonebook> result = em.createNativeQuery(SQL_REQUEST, Phonebook.class).getResultList();
        List<WireBook> wireResult = new ArrayList<WireBook>();
        for (Phonebook phonebook : result) {
            wireResult.add(phonebook.toWire());
        }
        return wireResult;
    }

    static private List<InetSocketAddress> getServers(String str) {
        List<InetSocketAddress> servers = new ArrayList<InetSocketAddress>();
        String[] arr = str.split(";");
        for (String anArr : arr) {
            String host = anArr.split(":")[0];
            Integer port = Integer.valueOf(anArr.split(":")[1]);
            servers.add(new InetSocketAddress(host, port));
        }
        return servers;
    }
}
