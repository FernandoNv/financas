package com.example.financas.service;

import com.example.financas.dto.CategoryDTO;
import com.example.financas.model.Category;
import com.example.financas.repository.CategoryRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryDTO toDTO(@NotNull Category category){
        return new CategoryDTO(category.getId(), category.getName(), category.getCreatedAt());
    }

    public Category toCategory(@NotNull CategoryDTO categoryDTO){
        return new Category(
                categoryDTO.getId(),
                categoryDTO.getName(),
                categoryDTO.getCreatedAt() != null ? categoryDTO.getCreatedAt() : LocalDate.now()
        );
    }


    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category addCategory(Category newCategory) {
        return categoryRepository.save(newCategory);
    }

    public Category findById(Long id){
        return categoryRepository.findById(id).orElse(null);
    }


    public Category updateCategory(@NotNull Category updatedCategory, Long id) {
        if (findById(id) != null) {
            return categoryRepository.save(updatedCategory);
        }

        return null;
    }

    public boolean deleteCategory(Long id) {
        if (findById(id) != null) {
            categoryRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
