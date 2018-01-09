package com.mkalita.controllers;

import com.mkalita.utils.HibernateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.util.List;

public class MdbController {
    private static final Logger log = LoggerFactory.getLogger(MdbController.class);
    private static final String selectInconsistencyEntitiesSql = "SELECT * FROM Постановления WHERE Адвокат NOT IN (SELECT `Код адвоката` FROM Адвокаты)";
    private static final String updateInconsistencyEntitiesSql = "UPDATE Постановления SET Адвокат=NULL WHERE Адвокат NOT IN (SELECT `Код адвоката` FROM Адвокаты)";
    private EntityManager em;

    public MdbController(HibernateUtil hibernateUtil) {
        em = hibernateUtil.getEm();
    }

    public static void fixInconsistency() {
        try (HibernateUtil hibernateUtil = new HibernateUtil()) {
            EntityManager em = hibernateUtil.getEm();
            em.getTransaction().begin();
            int inconsistencyRecordsCount = _checkInconsistency(em);
            if (inconsistencyRecordsCount != 0) {
                log.info("Found {} inconsistency records. Fixing.", inconsistencyRecordsCount);
                em.createNativeQuery(updateInconsistencyEntitiesSql).executeUpdate();
                em.getTransaction().commit();
                log.info("Fixed");
            } else {
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int _checkInconsistency(EntityManager em) {
        List resultList = em.createNativeQuery(selectInconsistencyEntitiesSql).getResultList();
        return resultList.size();
    }

}
