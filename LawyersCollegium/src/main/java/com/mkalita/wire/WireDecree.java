package com.mkalita.wire;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("unused")
public class WireDecree {
    private Long id;
    private String date;
    private String accused;
    private Long lawyerId;
    private String lawyer;
    private float amount;
    private String payDate;

    @JsonCreator
    public WireDecree(@JsonProperty("id") Long id,
                      @JsonProperty("date") String date,
                      @JsonProperty("accused") String accused,
                      @JsonProperty("lawyerId") Long lawyerId,
                      @JsonProperty("lawyer") String lawyer,
                      @JsonProperty("amount") float amount,
                      @JsonProperty("payDate") String payDate) {
        this.id = id;
        this.date = date;
        this.accused = accused;
        this.lawyerId = lawyerId;
        this.lawyer = lawyer;
        this.amount = amount;
        this.payDate = payDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
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

    public Long getLawyerId() {
        return lawyerId;
    }

    public void setLawyerId(Long lawyerId) {
        this.lawyerId = lawyerId;
    }

    public String getLawyer() {
        return lawyer;
    }

    public void setLawyer(String lawyer) {
        this.lawyer = lawyer;
    }
}
