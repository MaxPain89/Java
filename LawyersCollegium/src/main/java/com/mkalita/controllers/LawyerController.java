package com.mkalita.controllers;

import com.mkalita.jpa.Lawyer;
import com.mkalita.utils.HibernateUtil;
import com.mkalita.utils.JPAUtil;
import com.mkalita.wire.WireLawyer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class LawyerController {
    private static final Logger log = LoggerFactory.getLogger(DecreeController.class);
    private EntityManager em;

    public LawyerController(HibernateUtil hibernateUtil) {
        em = hibernateUtil.getEm();
    }

    public WireLawyer getLawyer(long lawyerId) {
        return _getLawyer(lawyerId).toWire();
    }

    private Lawyer _getLawyer(long lawyerId) {
        return JPAUtil.getObject(em, "id", lawyerId, Lawyer.class)
                .orElseThrow(() -> new RuntimeException(String.format("Couldn't find lawyer %s", lawyerId)));
    }
}
