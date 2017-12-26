package com.mkalita.jpa;

import javax.persistence.*;

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
        final StringBuffer sb = new StringBuffer("Collegium{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", other='").append(other).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
