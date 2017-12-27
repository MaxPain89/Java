package com.mkalita.wire;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WireCollegium {
    private String name;
    private String other;

    @JsonCreator
    public WireCollegium(@JsonProperty("name") String name,
                         @JsonProperty("other") String other) {
        this.name = name;
        this.other = other;
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
