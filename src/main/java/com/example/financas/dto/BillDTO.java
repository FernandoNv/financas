package com.example.financas.dto;

import com.example.financas.model.Person;
import com.example.financas.model.Purchase;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BillDTO implements Serializable {
    private Long id;
    private LocalDate expiredAt;
    private Person person;
    private Double totalPrice;
    private Set<Purchase> purchases = new HashSet<>();

    public BillDTO(Long id, LocalDate expiredAt, Person person, Double totalPrice) {
        this.id = id;
        this.expiredAt = expiredAt;
        this.person = person;
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(LocalDate expiredAt) {
        this.expiredAt = expiredAt;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Set<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(Set<Purchase> purchases) {
        this.purchases = purchases;
    }
}
