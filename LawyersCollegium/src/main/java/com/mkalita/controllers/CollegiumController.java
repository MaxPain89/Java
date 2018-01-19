package com.mkalita.controllers;

import com.mkalita.jpa.Collegium;
import com.mkalita.utils.JPAUtil;
import com.mkalita.webserver.exceptions.NotFoundException;
import com.mkalita.wire.WireCollegium;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CollegiumController {
    private static final Logger log = LoggerFactory.getLogger(DecreeController.class);
    private EntityManager em;

    public CollegiumController(EntityManager em) {
        this.em = em;
    }

    @SuppressWarnings("WeakerAccess")
    public List<Collegium> _getCollegiums() {
        return JPAUtil.getObjects(em, null, null, Collegium.class);
    }

    public List<WireCollegium> getCollegiums() {
        return _getCollegiums().stream().map(Collegium::toWire).collect(Collectors.toList());
    }

    public Collegium _getCollegium(long collegiumId) {
        return JPAUtil.getObject(em, "id", collegiumId, Collegium.class)
                .orElseThrow(() -> new NotFoundException(String.format("Couldn't find collegium with id %s", collegiumId)));
    }

    public WireCollegium getCollegium(long collegiumId) {
        return _getCollegium(collegiumId).toWire();
    }

    public WireCollegium createCollegium(WireCollegium wireCollegium) {
        em.getTransaction().begin();
        Collegium collegium = new Collegium(wireCollegium);
        em.persist(collegium);
        em.getTransaction().commit();
        return collegium.toWire();
    }

    public WireCollegium updateCollegium(WireCollegium updatedCollegium, long collegiumId) {
        em.getTransaction().begin();
        Collegium collegium = _getCollegium(collegiumId);
        collegium.updateFromWire(updatedCollegium);
        em.merge(collegium);
        em.getTransaction().commit();
        return getCollegium(collegiumId);
    }
}
