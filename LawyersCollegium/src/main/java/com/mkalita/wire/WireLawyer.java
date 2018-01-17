package com.mkalita.wire;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("unused")
public class WireLawyer {
    private Long id;
    private String fullName;
    private boolean out;

    @JsonCreator
    public WireLawyer(@JsonProperty("id") Long id,
                      @JsonProperty("fullname") String fullName,
                      @JsonProperty("out") boolean out) {
        this.id = id;
        this.fullName = fullName;
        this.out = out;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isOut() {
        return out;
    }

    public void setOut(boolean out) {
        this.out = out;
    }
}
