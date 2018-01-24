package com.mkalita.controllers;

import com.mkalita.jpa.Collegium;
import com.mkalita.jpa.Lawyer;
import com.mkalita.utils.JPAUtil;
import com.mkalita.webserver.exceptions.ForbiddenException;
import com.mkalita.webserver.exceptions.NotFoundException;
import com.mkalita.wire.WireLawyer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class LawyerController {
    private static final Logger log = LoggerFactory.getLogger(DecreeController.class);
    private CollegiumController collegiumController;
    private EntityManager em;

    public LawyerController(CollegiumController collegiumController,
                            EntityManager em) {
        this.collegiumController = collegiumController;
        this.em = em;
    }

    @SuppressWarnings("WeakerAccess")
    public List<Lawyer> _getLawyers() {
        return JPAUtil.getObjects(em, null, Lawyer.class);
    }

    public List<Lawyer> _getLawyersByCollegium(long collegiumId) {
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("collegium.id", collegiumId);
        return JPAUtil.getObjects(em, conditions, null, Lawyer.class);
    }

    public List<WireLawyer> getLawyers() {
        return _getLawyers().stream().map(Lawyer::toWire).collect(Collectors.toList());
    }

    public List<WireLawyer> getLawyers(Long collegiumId) {
        return _getLawyersByCollegium(collegiumId).stream().map(Lawyer::toWire).collect(Collectors.toList());
    }

    public Lawyer _getLawyer(long lawyerId) {
        return JPAUtil.getObject(em, "id", lawyerId, Lawyer.class)
                .orElseThrow(() -> new NotFoundException(String.format("Couldn't find lawyer with id %s", lawyerId)));
    }

    public WireLawyer getLawyer(long lawyerId) {
        return _getLawyer(lawyerId).toWire();
    }

    public WireLawyer createLawyer(WireLawyer wireLawyer, Long collegiumId) {
        em.getTransaction().begin();
        Collegium collegium = null;
        if (collegiumId != null) {
            collegium = collegiumController._getCollegium(collegiumId);
        }
        Lawyer lawyer = new Lawyer(wireLawyer, collegium);
        em.persist(lawyer);
        em.getTransaction().commit();
        return lawyer.toWire();
    }

    public WireLawyer updateLawyer(WireLawyer updatedLawyer, long lawyerId, Long collegiumId) {
        em.getTransaction().begin();
        Lawyer lawyer = _getLawyer(lawyerId);
        Collegium newCollegium = null;
        if (collegiumId != null) {
            newCollegium = collegiumController._getCollegium(collegiumId);
        }
        Collegium collegium = lawyer.getCollegium();
        lawyer.updateFromWire(updatedLawyer);
        if (!Objects.equals(collegium, newCollegium)) {
            lawyer.setCollegium(newCollegium);
        }
        em.merge(lawyer);
        em.getTransaction().commit();
        return getLawyer(lawyerId);
    }

    public WireLawyer deleteLawyer(long lawyerId, boolean force, boolean deleteDecrees) {
        em.getTransaction().begin();
        Lawyer lawyer = _getLawyer(lawyerId);
        if (lawyer.getDecrees().size() > 0 && !force) {
            throw new ForbiddenException("Can't delete lawyers with decrees.");
        }
        if (deleteDecrees) {
            lawyer.getDecrees().forEach(decree -> decree.setLawyer(null));
        } else {
            lawyer.getDecrees().forEach(decree -> em.remove(decree));
        }
        em.remove(lawyer);
        em.getTransaction().commit();
        return lawyer.toWire();
    }
}
