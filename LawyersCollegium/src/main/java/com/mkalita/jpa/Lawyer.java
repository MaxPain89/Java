package com.mkalita.jpa;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

@Entity
@Table(name = "Адвокаты")
public class Lawyer {
    private long id;
    String fullName;
    private Collegium collegium;
    private boolean out;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "`Код адвоката`", nullable = false, updatable = false)
    public long getId() {
        return id;
    }

    protected void setId(final long activationId) {
        this.id = activationId;
    }

    @Basic
    @Column(name = "ФИО")
    public String getFullName() {
        return fullName;
    }

    public void setFullName(final String fullName) {
        this.fullName = fullName;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "`Код коллегии`", referencedColumnName = "`Код коллегии`")
    public Collegium getCollegium() {
        return collegium;
    }

    public void setCollegium(Collegium collegium) {
        this.collegium = collegium;
    }

    @Basic
    @Column(name = "`Выбыл`")
    public boolean isOut() {
        return out;
    }

    public void setOut(boolean out) {
        this.out = out;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Lawyer{");
        sb.append("id=").append(id);
        sb.append(", fullName='").append(fullName).append('\'');
        sb.append(", collegium=").append(collegium);
        sb.append(", out=").append(out);
        sb.append('}');
        return sb.toString();
    }
}
