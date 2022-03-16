package com.example.financas.domain;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class Bill {
    private Long id;
    private LocalDate expiredAt;

    private Person person;
    private Set<Purchase> purchaseSet = new TreeSet<>(Comparator.comparing(Purchase::getDate));

    public Bill(Long id, LocalDate expiredAt) {
        this.id = id;
        this.expiredAt = expiredAt;
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

    public double getTotalPrice() {
        return purchaseSet.stream().map(Purchase::getPrice).reduce(0d, Double::sum);
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Set<Purchase> getPurchaseSet() {
        return purchaseSet;
    }

    public void setPurchaseSet(Set<Purchase> purchaseSet) {
        this.purchaseSet = purchaseSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bill bill = (Bill) o;
        return Objects.equals(id, bill.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", expiredAt=" + expiredAt +
                ", totalPrice=" + getTotalPrice() +
                '}';
    }
}
