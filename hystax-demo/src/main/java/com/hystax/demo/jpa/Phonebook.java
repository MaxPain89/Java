package com.hystax.demo.jpa;

import com.hystax.demo.wire.WireBook;

import javax.persistence.*;

@Entity
public class Phonebook {
    private long id;
    private String fullName;
    private String address;
    private String phone;
    private Long idNumber;

    public Phonebook() {
    }

    public Phonebook(WireBook wireBook) {
        this.fullName = wireBook.getFullName();
        this.address = wireBook.getAddress();
        this.phone = wireBook.getPhone();
        this.idNumber = System.currentTimeMillis();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_generator")
    @SequenceGenerator(name="book_generator", sequenceName = "PHONEBOOK_SEQ", allocationSize=1)
    @Column(name = "ID", nullable = false, precision = 0)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "FULL_NAME", nullable = true, length = 255)
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Basic
    @Column(name = "ADDRESS", nullable = true, length = 255)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "PHONE", nullable = true, length = 255)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "ID_NUMBER", nullable = true, precision = 0)
    public Long getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(Long idNumber) {
        this.idNumber = idNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Phonebook phonebook = (Phonebook) o;

        if (id != phonebook.id) return false;
        if (fullName != null ? !fullName.equals(phonebook.fullName) : phonebook.fullName != null) return false;
        if (address != null ? !address.equals(phonebook.address) : phonebook.address != null) return false;
        if (phone != null ? !phone.equals(phonebook.phone) : phonebook.phone != null) return false;
        return idNumber != null ? idNumber.equals(phonebook.idNumber) : phonebook.idNumber == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (idNumber != null ? idNumber.hashCode() : 0);
        return result;
    }

    public WireBook toWire() {
        return new WireBook(this.id, this.fullName, this.address, this.phone, this.idNumber);
    }

    public void updateFromWire(WireBook wireBook) {
        this.setFullName(wireBook.getFullName());
        this.setAddress(wireBook.getAddress());
        this.setPhone(wireBook.getPhone());
    }
}
