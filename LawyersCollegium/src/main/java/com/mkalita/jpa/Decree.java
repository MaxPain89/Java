package com.mkalita.jpa;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(name = "Постановления")
public class Decree {
    private long decreeId;
    private Date DateTime;
    private Lawyer lawyer;
    private String accused;
    private float amount;
    private Date date;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "`Код постановления`", nullable = false, updatable = false)
    public long getDecreeId() {
        return decreeId;
    }

    public void setDecreeId(long decreeId) {
        this.decreeId = decreeId;
    }

    @Basic
    @Column(name = "`Дата постановления`")
    public Date getDateTime() {
        return DateTime;
    }

    public void setDateTime(Date dateTime) {
        DateTime = dateTime;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
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
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return String.format("Decree{decreeId=%d, DateTime=%s, lawyer=%s, accused='%s', amount=%s, date=%s}", decreeId, DateTime, lawyer, accused, amount, date);
    }
}
