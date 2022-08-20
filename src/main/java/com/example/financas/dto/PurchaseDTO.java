package com.example.financas.dto;

import com.example.financas.model.Category;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PurchaseDTO implements Serializable {
    private Long id;
    private LocalDate date;
    private Double totalPrice;
    private Double sharedPrice;
    private String description;
    private int instalments;
    private int paidInstalments;
    private boolean isShared;
    private Category category;
    private List<PersonDTO> persons = new ArrayList<>();

    public PurchaseDTO() {
    }

    public PurchaseDTO(Long id, LocalDate date, Double totalPrice, String description, int instalments, int paidInstalments, boolean isShared) {
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

    public boolean getShared() {
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

    public List<PersonDTO> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonDTO> persons) {
        this.persons = persons;
    }
}
