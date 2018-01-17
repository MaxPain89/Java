package com.mkalita.controllers;

import com.mkalita.jpa.Decree;
import com.mkalita.jpa.Lawyer;
import com.mkalita.utils.JPAUtil;
import com.mkalita.webserver.exceptions.NotFoundException;
import com.mkalita.wire.WireDecree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class DecreeController {
    private static final Logger log = LoggerFactory.getLogger(DecreeController.class);
    private EntityManager em;
    private LawyerController lawyerController;

    public DecreeController(EntityManager em,
                            LawyerController lawyerController) {
        this.em = em;
        this.lawyerController = lawyerController;
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

    public List<WireDecree> getDecreesForYear(Integer year) {
        if (year != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, Calendar.JANUARY);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            Date startDate = calendar.getTime();
            calendar.set(Calendar.YEAR, year + 1);
            Date endDate = calendar.getTime();
            return getDecreesByDate(startDate, endDate);
        } else {
            return getDecreesByDate(null, null);
        }
    }

    @SuppressWarnings("unused")
    public WireDecree getDecree(Long decreeId) {
        return _getDecree(decreeId).toWire();
    }

    private Decree _getDecree(Long decreeId) {
        return JPAUtil.getObject(em, "id", decreeId, Decree.class)
                .orElseThrow(() -> new NotFoundException(String.format("Couldn't find decree with id %s", decreeId)));
    }

    public WireDecree createDecree(WireDecree wireDecree, Long lawyerId) {
        em.getTransaction().begin();
        Decree decree = new Decree(wireDecree);
        Lawyer lawyer = null;
        if (lawyerId != null) {
            lawyer = lawyerController._getLawyer(lawyerId);
        }
        decree.setLawyer(lawyer);
        em.persist(decree);
        em.getTransaction().commit();
        return decree.toWire();
    }

    public WireDecree updateDecree(Long decreeId, WireDecree wireDecree, Long lawyerId) {
        em.getTransaction().begin();
        Decree decree = _getDecree(decreeId);
        Lawyer newLawyer = null;
        if (lawyerId != null) {
            newLawyer = lawyerController._getLawyer(lawyerId);
        }
        Lawyer lawyer = decree.getLawyer();
        if (!Objects.equals(lawyer, newLawyer)) {
            decree.setLawyer(newLawyer);
        }
        decree.updateFromWire(wireDecree);
        em.merge(decree);
        em.getTransaction().commit();
        return decree.toWire();
    }
}
