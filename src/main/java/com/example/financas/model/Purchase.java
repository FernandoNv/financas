package com.example.financas.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Purchase {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, updatable = false)
    private LocalDate date;
    @Column(nullable = false, precision = 10, scale = 2)
    private Double priceTotal;
    @Column(precision = 10, scale = 2)
    private Double priceShared;
    private String description;
    private int instalments;
    private int paidInstalments;
    private boolean isShared;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_category_id")
    private Category category;

    @ManyToMany
    @JoinTable(
            name = "purchase_bill",
            joinColumns = @JoinColumn(name = "fk_purchase_id"),
            inverseJoinColumns = @JoinColumn(name = "fk_bill_id")
    )
    private List<Bill> bills = new ArrayList<>();

    public Purchase() {
    }

    public Purchase(Long id, LocalDate date, Double priceTotal, String description, int instalments, int paidInstalments, boolean isShared) {
        this.id = id;
        this.date = date;
        this.priceTotal = priceTotal;
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

    public Double getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(Double priceTotal) {
        this.priceTotal = priceTotal;
    }

    public Double getPriceShared() {
        return priceShared;
    }

    public void setPriceShared(Double priceShared) {
        this.priceShared = priceShared;
    }

    public void setPriceShared() {
        if(isShared && bills.size() > 0) this.priceShared = priceTotal/bills.size();
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

    public List<Bill> getBills() {
        return bills;
    }

    public void setBills(List<Bill> bills) {
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
                ", priceTotal=" + priceTotal +
                ", priceShared=" + priceShared +
                ", description='" + description + '\'' +
                ", instalments=" + instalments +
                ", paidInstalments=" + paidInstalments +
                ", isShared=" + isShared +
                ", category=" + category +
                ", bills=" + bills +
                '}';
    }
}
