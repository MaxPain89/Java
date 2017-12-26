package com.mkalita.jpa;

import javax.persistence.*;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(name = "Коллегии")
public class Collegium {
    private long id;
    private String name;
    private String other;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
}
