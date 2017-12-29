package com.mkalita.jpa;

import com.mkalita.wire.WireCollegium;

import javax.persistence.*;

@SuppressWarnings({"JpaDataSourceORMInspection", "unused"})
@Entity
@Table(name = "Коллегии")
public class Collegium {
    private long id;
    private String name;
    private String other;

    public Collegium() {
    }

    public Collegium(WireCollegium wireCollegium) {
        this.name = wireCollegium.getName();
        this.other = wireCollegium.getOther();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`Код коллегии`", nullable = false, updatable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "Наименование")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "Прочее")
    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    @Override
    public String toString() {
        return String.format("Collegium{id=%d, name='%s', other='%s'}", id, name, other);
    }

    public WireCollegium toWire(){
        return new WireCollegium(this.name, this.other);
    }
}
