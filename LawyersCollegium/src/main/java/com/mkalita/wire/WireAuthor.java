package com.mkalita.wire;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WireAuthor {
    private Long id;
    private String name;
    private String phone;

    @JsonCreator
    public WireAuthor(@JsonProperty("id")Long id,
                      @JsonProperty("name") String name,
                      @JsonProperty("phone") String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
