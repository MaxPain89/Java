package com.mkalita.controllers;

import com.mkalita.jpa.Decree;
import com.mkalita.jpa.Lawyer;
import com.mkalita.utils.HibernateUtil;
import com.mkalita.utils.JPAUtil;
import com.mkalita.wire.WireDecree;
import com.mkalita.wire.WireLawyer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

public class MdbController {
    private static final Logger log = LoggerFactory.getLogger(MdbController.class);
    private static final String selectInconsistencyEntitiesSql = "SELECT * FROM Постановления WHERE Адвокат NOT IN (SELECT `Код адвоката` FROM Адвокаты)";
    private static final String updateInconsistencyEntitiesSql = "UPDATE Постановления SET Адвокат=NULL WHERE Адвокат NOT IN (SELECT `Код адвоката` FROM Адвокаты)";
    private EntityManager em;
    public static final String dateFormatStr = "dd/MM/yyyy";

    public MdbController(HibernateUtil hibernateUtil) {
        em = hibernateUtil.getEm();
    }

    private static boolean checkDateRange(Date actualDate, Date startDate, Date endDate) {
        if (actualDate == null) {
            return false;
        }
        boolean beforeCondition = startDate == null || actualDate.after(startDate);
        boolean afterCondition = endDate == null || actualDate.before(endDate);
        return beforeCondition && afterCondition;
    }

    private List<Decree> _getAllDecrees() {
        return JPAUtil.getObjects(em, Collections.emptyMap(), null, Decree.class);
    }

    @SuppressWarnings("unused")
    public List<WireDecree> getAllDecrees() {
        return _getAllDecrees().stream().map(Decree::toWire).collect(Collectors.toList());
    }

    @SuppressWarnings("WeakerAccess")
    public List<WireDecree> getDecreesByDate(Date startDate, Date endDate) {
        return _getAllDecrees().stream()
                .filter((item) -> checkDateRange(item.getDate(), startDate, endDate))
                .sorted(Comparator.comparing(Decree::getDate))
                .map(Decree::toWire)
                .collect(Collectors.toList());
    }

    public List<WireDecree> getDecreesForYear(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date startDate = calendar.getTime();
        calendar.set(Calendar.YEAR, year + 1);
        Date endDate = calendar.getTime();
        return getDecreesByDate(startDate, endDate);
    }

    @SuppressWarnings("unused")
    public WireDecree getDecree(Long decreeId) {
        return _getDecree(decreeId).toWire();
    }

    private Decree _getDecree(Long decreeId) {
        return JPAUtil.getObject(em, "id", decreeId, Decree.class)
                .orElseThrow(() -> new RuntimeException(String.format("Couldn't find decree %s", decreeId)));
    }

    @SuppressWarnings("unused")
    public WireLawyer getLawyer(long lawyerId) {
        return _getLawyer(lawyerId).toWire();
    }

    private Lawyer _getLawyer(long lawyerId) {
        return JPAUtil.getObject(em, "id", lawyerId, Lawyer.class)
                .orElseThrow(() -> new RuntimeException(String.format("Couldn't find lawyer %s", lawyerId)));
    }

    @SuppressWarnings("unused")
    public void createDecree(WireDecree wireDecree, long lawyerId) {
        em.getTransaction().begin();
        Decree decree = new Decree(wireDecree);
        Lawyer lawyer = _getLawyer(lawyerId);
        decree.setLawyer(lawyer);
        em.persist(decree);
        em.getTransaction().commit();
    }

    public void fixInconsistency() {
        em.getTransaction().begin();
        int inconsistencyRecordsCount = _checkInconsistency();
        if (inconsistencyRecordsCount != 0) {
            log.info("Found {} inconsistency records. Fixing.", inconsistencyRecordsCount);
            em.createNativeQuery(updateInconsistencyEntitiesSql).executeUpdate();
            em.getTransaction().commit();
            log.info("Fixed");
        } else {
            em.getTransaction().commit();
        }
    }

    private int _checkInconsistency() {
        List resultList = em.createNativeQuery(selectInconsistencyEntitiesSql).getResultList();
        return resultList.size();
    }

}
