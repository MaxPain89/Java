package com.mkalita.wire;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class WireDecree {
    private Date date;
    private String accused;
    private float amount;
    private Date payDate;

    @JsonCreator
    public WireDecree(@JsonProperty("date") Date date,
                      @JsonProperty("accused") String accused,
                      @JsonProperty("amount") float amount,
                      @JsonProperty("payDate") Date payDate) {
        this.date = date;
        this.accused = accused;
        this.amount = amount;
        this.payDate = payDate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }
}
