package com.mkalita.jpa;

import com.mkalita.wire.WireDecree;
import javax.persistence.criteria.Fetch;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import javax.persistence.criteria.FetchParent;
import javax.persistence.criteria.JoinType;
import java.util.Date;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(name = "Постановления")
public class Decree extends ConfigurableFetchMode {
    private long id;
    private Date date;
    private Lawyer lawyer;
    private String accused;
    private float amount;
    private Date payDate;

    public Decree() {
    }

    public Decree(WireDecree wireDecree) {
        this.date = wireDecree.getDate();
        this.lawyer = null;
        this.accused = wireDecree.getAccused();
        this.amount = wireDecree.getAmount();
        this.payDate = wireDecree.getPayDate();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`Код постановления`", nullable = false, updatable = false)
    public long getId() {
        return id;
    }

    public void setId(long decreeId) {
        this.id = decreeId;
    }

    @Basic
    @Column(name = "`Дата постановления`")
    public Date getDate() {
        return new Date(date.getTime());
    }

    public void setDate(Date date) {
        this.date = new Date(date.getTime());
    }

    @ManyToOne
    @JoinColumn(name = "Адвокат", referencedColumnName = "`Код адвоката`")
    @NotFound( action = NotFoundAction.IGNORE )
    public Lawyer getLawyer() {
        return lawyer;
    }

    public void setLawyer(Lawyer lawyer) {
        this.lawyer = lawyer;
    }

    @Basic
    @Column(name = "Обвиняемый")
    public String getAccused() {
        return accused;
    }

    public void setAccused(String accused) {
        this.accused = accused;
    }

    @Basic
    @Column(name = "`Сумма`")
    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    @Basic
    @Column(name = "`Дата выплаты`")
    public Date getPayDate() {
        return new Date(payDate.getTime());
    }

    public void setPayDate(Date date) {
        this.payDate = new Date(date.getTime());
    }

    @Override
    public String toString() {
        return String.format("Decree{decreeId=%d, date=%s, lawyer=%s, accused='%s', amount=%s, date=%s}", id, date, lawyer, accused, amount, payDate);
    }

    public WireDecree toWire(){
        return new WireDecree(this.date, this.accused, this.amount, this.payDate);
    }

    @SuppressWarnings("unused")
    public static <K, V> void addFetchTypes(FetchParent<K,V> root) {
        if (!checkBreakCondition(root, Lawyer.class)) {
            Fetch<Decree, Lawyer> d_l_fetch = root.fetch("lawyer", JoinType.LEFT);
            Lawyer.addFetchTypes(d_l_fetch);
        }
    }
}
