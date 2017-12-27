package com.mkalita.controllers;

import com.mkalita.utils.HibernateUtil;
import com.mkalita.jpa.Decree;
import com.mkalita.jpa.Lawyer;
import com.mkalita.utils.JPAUtil;
import com.mkalita.wire.WireDecree;
import com.mkalita.wire.WireLawyer;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MdbController {
    private EntityManager em;

    public MdbController(HibernateUtil hibernateUtil) {
        em = hibernateUtil.getEm();
    }

    public List<Decree> getAllDecrees() {
        return JPAUtil.getObjects(em, Collections.emptyMap(), null, Decree.class);
    }

    public WireDecree getDecree(Long decreeId) {
        return _getDecree(decreeId).toWire();
    }

    private Decree _getDecree(Long decreeId) {
        return JPAUtil.getObject(em, "id", decreeId, Decree.class)
                .orElseThrow(() -> new RuntimeException(String.format("Couldn't find decree %s", decreeId)));
    }

    public WireLawyer getLawyer(long lawyerId) {
        return _getLawyer(lawyerId).toWire();
    }

    private Lawyer _getLawyer(long lawyerId) {
        return JPAUtil.getObject(em, "id", lawyerId, Lawyer.class)
                .orElseThrow(() -> new RuntimeException(String.format("Couldn't find lawyer %s", lawyerId)));
    }

    public void createDecree(WireDecree wireDecree, long lawyerId) {
        em.getTransaction().begin();
        Decree decree = new Decree(wireDecree);
        Lawyer lawyer = _getLawyer(lawyerId);
        decree.setLawyer(lawyer);
        em.persist(decree);
        em.getTransaction().commit();
    }
}
