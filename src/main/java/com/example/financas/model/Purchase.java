package com.example.financas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Purchase {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, updatable = false)
    private LocalDate date;
    @Column(nullable = false, precision = 10, scale = 2)
    private Double totalPrice;
    @Column(precision = 10, scale = 2)
    private Double sharedPrice;
    private String description;
    private int instalments;
    private int paidInstalments;
    private boolean isShared;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_category_id")
    private Category category;

    @JsonIgnore
    @ManyToMany(mappedBy = "purchases", cascade = CascadeType.MERGE)
    private Set<Bill> bills = new HashSet<>();

    public Purchase() {
    }

    public Purchase(Long id, LocalDate date, Double totalPrice, String description, int instalments, int paidInstalments, boolean isShared) {
        this.id = id;
        this.date = date;
        this.totalPrice = totalPrice;
        this.description = description;
        this.instalments = instalments;
        this.paidInstalments = paidInstalments;
        this.isShared = isShared;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getSharedPrice() {
        return sharedPrice;
    }

    public void setSharedPrice(Double sharedPrice) {
        this.sharedPrice = sharedPrice;
    }

    public void setSharedPrice() {
        if(isShared && bills.size() > 0) this.sharedPrice = totalPrice/bills.size();
        System.out.println(this.sharedPrice);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getInstalments() {
        return instalments;
    }

    public void setInstalments(int instalments) {
        this.instalments = instalments;
    }

    public int getPaidInstalments() {
        return paidInstalments;
    }

    public void setPaidInstalments(int paidInstalments) {
        this.paidInstalments = paidInstalments;
    }

    public boolean isShared() {
        return isShared;
    }

    public void setShared(boolean shared) {
        isShared = shared;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<Bill> getBills() {
        return bills;
    }

    public void setBills(Set<Bill> bills) {
        this.bills = bills;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Purchase purchase = (Purchase) o;
        return Objects.equals(id, purchase.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", date=" + date +
                ", totalPrice=" + totalPrice +
                ", sharedPrice=" + sharedPrice +
                ", description='" + description + '\'' +
                ", instalments=" + instalments +
                ", paidInstalments=" + paidInstalments +
                ", isShared=" + isShared +
                ", category=" + category.getName() +
                '}';
    }
}
