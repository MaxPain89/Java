package com.hystax.demo.wire;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WireBook {
    private Long id;
    private String fullName;
    private String address;
    private String phone;
    private Long idNumber;

    @JsonCreator
    public WireBook(@JsonProperty Long id,
                @JsonProperty String fullName,
                @JsonProperty String address,
                @JsonProperty String phone,
                @JsonProperty Long idNumber) {
        this.id = id;
        this.fullName = fullName;
        this.address = address;
        this.phone = phone;
        this.idNumber = idNumber;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(Long idNumber) {
        this.idNumber = idNumber;
    }
}
