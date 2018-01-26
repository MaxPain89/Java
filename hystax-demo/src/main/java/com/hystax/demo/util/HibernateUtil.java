package com.hystax.demo.util;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Collections;
import java.util.Map;

@Component
public class HibernateUtil {

    private static final String PERSISTENT_UNIT_NAME = "OracleJPA";
    private final EntityManagerFactory emf;
    public HibernateUtil() {
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENT_UNIT_NAME);
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    @Bean
    public EntityManager getEm() {
        EntityManager em = emf.createEntityManager();
        return em;
    }
}
