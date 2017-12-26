package com.mkalita.controllers;

import com.mkalita.HibernateUtil;
import com.mkalita.jpa.Decree;

import javax.persistence.EntityManager;
import java.util.List;

public class MdbController {
    private static final String getAllDecreesSql = "SELECT d from Decree d JOIN fetch d.lawyer l join fetch l.collegium c";
    private EntityManager em;

    public MdbController(HibernateUtil hibernateUtil) {
        em = hibernateUtil.getEm();
    }

    public List<Decree> getAllDecrees() {
        return em.createQuery(getAllDecreesSql, Decree.class).getResultList();
    }
}
