package com.example.financas.domain;

import java.time.LocalDate;
import java.util.*;

public class Purchase {
    private Long id;
    private LocalDate date;
    private double price;
    private String description;
    private int instalments;
    private boolean shared;

    private Category category;
    private Set<Person> personSet = new HashSet<>();
    private Set<Bill> billSet = new TreeSet<>(Comparator.comparing(Bill::getExpiredAt));

    public Purchase(Long id, LocalDate date, double price, String description, int instalments, boolean shared, Category category) {
        this.id = id;
        this.date = date;
        this.price = price;
        this.description = description;
        this.instalments = instalments;
        this.shared = shared;
        this.category = category;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    public boolean isShared() {
        return shared;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<Person> getPersonSet() {
        return personSet;
    }

    public void setPersonSet(Set<Person> personSet) {
        this.personSet = personSet;
    }

    public Set<Bill> getBillSet() {
        return billSet;
    }

    public void setBillSet(Set<Bill> billSet) {
        this.billSet = billSet;
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
                ", price=" + price +
                ", description='" + description + '\'' +
                ", instalments=" + instalments +
                ", shared=" + shared +
                '}';
    }
}
