package com.mkalita.wire;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mkalita.controllers.MdbController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("unused")
public class WireDecree {
    private Date date;
    private String accused;
    private String lawyer;
    private String collegium;
    private float amount;
    private Date payDate;

    @JsonCreator
    public WireDecree(@JsonProperty("date") Date date,
                      @JsonProperty("accused") String accused,
                      @JsonProperty("lawyer") String lawyer,
                      @JsonProperty("collegium") String collegium,
                      @JsonProperty("amount") float amount,
                      @JsonProperty("payDate") Date payDate) {
        this.date = new Date(date.getTime());
        this.accused = accused;
        this.lawyer = lawyer;
        this.collegium = collegium;
        this.amount = amount;
        this.payDate = new Date(payDate.getTime());
    }

    public Date getDate() {
        return new Date(date.getTime());
    }

    public void setDate(Date date) {
        this.date = new Date(date.getTime());
    }

    public String getAccused() {
        return accused;
    }

    public void setAccused(String accused) {
        this.accused = accused;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Date getPayDate() {
        return new Date(payDate.getTime());
    }

    public void setPayDate(Date payDate) {
        this.payDate = new Date(payDate.getTime());
    }

    public String getLawyer() {
        return lawyer;
    }

    public void setLawyer(String lawyer) {
        this.lawyer = lawyer;
    }

    @Override
    public String toString() {
        DateFormat df = new SimpleDateFormat(MdbController.dateFormatStr);
        return String.format("%s| %-15s| %-35s| %-60s| %-10s| %s",
                df.format(date), accused, lawyer, collegium, amount, df.format(payDate));
    }
}
