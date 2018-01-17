package com.mkalita.wire;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("unused")
public class WireCollegium {
    private Long id;
    private String name;
    private String other;

    @JsonCreator
    public WireCollegium(@JsonProperty("id") Long id,
                         @JsonProperty("name") String name,
                         @JsonProperty("other") String other) {
        this.id = id;
        this.name = name;
        this.other = other;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
