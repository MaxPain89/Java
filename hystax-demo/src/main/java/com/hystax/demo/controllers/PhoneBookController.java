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
    private Context initial_ctx;
    private static final String MEMCACHED_KEY = "HystaxDemoBookKey";
    private static final String PERSISTENT_UNIT_NAME = "OracleJPA";
    private static final String SQL_REQUEST = "select pb.tid as ID, pb.FULL_NAME, pb.ADDRESS, pb.PHONE, pb.ID_NUMBER from (select ID as tid, FULL_NAME, ADDRESS, PHONE, ID_NUMBER, (select count(*)       from phonebook pb2 where substr(pb2.phone, 4, 5) LIKE substr(phonebook.phone, 4, 5) group by substr(pb2.phone, 4, 5)) as cnt, substr(phonebook.phone, 4, 5) as code from phonebook order by cnt desc, code) pb";

    public PhoneBookController() {
        emf = Persistence.createEntityManagerFactory(PERSISTENT_UNIT_NAME);
        em = emf.createEntityManager();
        try {
            initial_ctx = new InitialContext();
            String jndiMemcached = (String) initial_ctx.lookup("memcachedServers");
            List<InetSocketAddress> servers = getServers(jndiMemcached);
            memClient = new MemcachedClient(servers);
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
        Phonebook book = JPAUtil.getObject(em, "id", id, Phonebook.class);
        return book;
    }

    public WireBook addBook(WireBook wireBook) {
        try {
            em.getTransaction().begin();
            Phonebook book = new Phonebook(wireBook);
            em.persist(book);
            em.getTransaction().commit();
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
            return book.toWire();
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
    }

    public CalculateResponse calculate() {
        boolean isCached = false;
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

    private List<WireBook> checkMemcached() {
        Object obj = memClient.get(MEMCACHED_KEY);
        if (obj != null) {
            return (List<WireBook>) obj;
        } else {
            return null;
        }
    }

    private void putToMem(List<WireBook> books) {
        memClient.set(MEMCACHED_KEY, 3600, books);
    }

    public String invalidateCache() {
        memClient.delete(MEMCACHED_KEY);
        return "Ok";
    }

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
        for (int i = 0; i < arr.length; i++) {
            String host = arr[i].split(":")[0];
            Integer port = Integer.valueOf(arr[i].split(":")[1]);
            servers.add(new InetSocketAddress(host, port));
        }
        return servers;
    }
}
