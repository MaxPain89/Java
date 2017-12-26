package com.mkalita;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class HibernateUtil implements Closeable{

    private static final String PERSISTENT_UNIT_NAME = "JPA";

    private final EntityManagerFactory emf;
    private List<EntityManager> ems = new ArrayList<>();

    HibernateUtil(String path) {
        try {
            Map addedOrOverriddenProperties = Collections.singletonMap("hibernate.connection.url", String.format("jdbc:ucanaccess://%s", path));
            emf = Persistence.createEntityManagerFactory(PERSISTENT_UNIT_NAME, addedOrOverriddenProperties);
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public EntityManager getEm() {
        EntityManager em = emf.createEntityManager();
        ems.add(em);
        return em;
    }

    @Override
    public void close() {
        for (EntityManager em : ems) {
            em.close();
        }
        emf.close();
    }
}
