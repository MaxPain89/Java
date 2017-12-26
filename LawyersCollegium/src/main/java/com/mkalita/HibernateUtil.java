package com.mkalita;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.Closeable;
import java.io.IOException;
import java.util.*;

public class HibernateUtil implements Closeable{

    private static final String PERSISTENT_UNIT_NAME = "JPA";

    private final EntityManagerFactory emf;
    private List<EntityManager> ems = new ArrayList<>();

    public HibernateUtil(String path) {
        try {
            Map addedOrOverridenProperties = Collections.singletonMap("hibernate.connection.url" , String.format("jdbc:ucanaccess://%s", path));
            emf = Persistence.createEntityManagerFactory(PERSISTENT_UNIT_NAME, addedOrOverridenProperties);
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public EntityManager getEm() {
        EntityManager em = emf.createEntityManager();
        ems.add(em);
        return em;
    }

    private Properties getProps(String path){
        Properties props = new Properties();
        props.setProperty("hibernate.connection.driver_class", "net.ucanaccess.jdbc.UcanaccessDriver");
        props.setProperty("hibernate.connection.url", String.format("jdbc:ucanaccess://%s", path));
        props.setProperty("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect");
        return props;
    }

    @Override
    public void close() throws IOException {
        for (EntityManager em : ems) {
            em.close();
        }
        emf.close();
    }
}
