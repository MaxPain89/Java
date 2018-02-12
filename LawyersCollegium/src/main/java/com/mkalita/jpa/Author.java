package com.mkalita.jpa;

import com.mkalita.wire.WireAuthor;

import java.util.Objects;

public class Author {
    private long id;
    private String name;
    private String phone;

    public Author(long id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public Author(WireAuthor wireAuthor) {
        this.name = wireAuthor.getName();
        this.phone = wireAuthor.getPhone();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public WireAuthor toWire(){
        return new WireAuthor(id, this.name, this.phone);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Author author = (Author) o;
        return Objects.equals(name, author.name) &&
                Objects.equals(phone, author.phone);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, phone);
    }
}
