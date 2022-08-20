package com.example.financas.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Bill {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate expiredAt;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_person_id")
    private Person person;

    @Column(precision = 10, scale = 2)
    private Double totalPrice;

    @ManyToMany
    @JoinTable(
            name = "bill_purchase",
            joinColumns = @JoinColumn(name = "fk_bill_id"),
            inverseJoinColumns = @JoinColumn(name = "fk_purchase_id")
    )
    private Set<Purchase> purchases = new HashSet<>();

    public Bill() {
    }

    public Bill(Long id, LocalDate expiredAt) {
        this.id = id;
        this.expiredAt = expiredAt;
        this.totalPrice = 0D;
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
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setTotalPrice() {
        this.totalPrice = purchases.stream()
                .map(p -> p.isShared() ? p.getSharedPrice() : p.getTotalPrice())
                .reduce(0d, Double::sum);
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Set<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(Set<Purchase> purchases) {
        this.purchases = purchases;
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
