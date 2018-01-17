package com.mkalita.jpa;

import com.mkalita.wire.WireLawyer;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import javax.persistence.criteria.FetchParent;
import javax.persistence.criteria.JoinType;

@SuppressWarnings({"JpaDataSourceORMInspection", "unused"})
@Entity
@Table(name = "Адвокаты")
public class Lawyer extends ConfigurableFetchMode{
    private long id;
    private String fullName;
    private Collegium collegium;
    private boolean out;

    public Lawyer(WireLawyer wirelawyer) {
        this.fullName = wirelawyer.getFullName();
        this.out = wirelawyer.isOut();
    }

    public Lawyer() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToOne
    @JoinColumn(name = "`Код коллегии`", referencedColumnName = "`Код коллегии`")
    @NotFound(action = NotFoundAction.IGNORE)
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
        return String.format("Lawyer{id=%d, fullName='%s', collegium=%s, out=%s}", id, fullName, collegium, out);
    }

    public WireLawyer toWire(){
        return new WireLawyer(this.id,
                this.fullName,
                              this.isOut());
    }

    @SuppressWarnings("unused")
    public static <K, V> void addFetchTypes(FetchParent<K,V> root) {
        root.fetch("collegium", JoinType.LEFT);
    }
}
