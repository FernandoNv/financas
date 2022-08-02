package com.example.financas.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class CategoryDTO implements Serializable {
    private Long id;
    private String name;
    private LocalDate createdAt;

    public CategoryDTO() {
    }

    public CategoryDTO(Long id, String name, LocalDate createdAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}
