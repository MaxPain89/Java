package com.hystax.demo.controllers;
import com.hystax.demo.jpa.Phonebook;
import com.hystax.demo.util.JPAUtil;
import com.hystax.demo.wire.WireBook;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class PhoneBookController {

    private final EntityManagerFactory emf;
    private EntityManager em;
    private static final String PERSISTENT_UNIT_NAME = "OracleJPA";

    public PhoneBookController() {
        emf = Persistence.createEntityManagerFactory(PERSISTENT_UNIT_NAME);
        em = emf.createEntityManager();
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
}
