package com.example.financas.controller;

import com.example.financas.dto.CategoryDTO;
import com.example.financas.dto.HttpErrorResponse;
import com.example.financas.model.Category;
import com.example.financas.service.CategoryService;
import com.github.javafaker.Cat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(produces = "application/json")
    ResponseEntity<List<Category>> getListCategories(){
        List<Category> categories = this.categoryService.findAll();

        return ResponseEntity.ok().body(categories);
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    ResponseEntity<Category> addCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        Category newCategory = categoryService.toCategory(categoryDTO);
        newCategory = categoryService.addCategory(newCategory);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newCategory.getId())
                .toUri();

        return ResponseEntity.created(uri).body(newCategory);
    }

    @PutMapping(value = "/{id}", produces = "application/json", consumes = "application/json")
    ResponseEntity<Object> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable Long id){
        Category updatedCategory = categoryService.toCategory(categoryDTO);
        updatedCategory.setId(id);
        updatedCategory = categoryService.updateCategory(updatedCategory, id);

        if(updatedCategory == null){
            return ResponseEntity.badRequest().body(new HttpErrorResponse(HttpStatus.NOT_FOUND.value(), "Categoria não encontrada"));
        }

        return ResponseEntity.ok().body(updatedCategory);
    }

    @DeleteMapping(value = "/{id}")
    ResponseEntity<Object> deleteCategory(@PathVariable Long id){
        boolean isDeleted = categoryService.deleteCategory(id);
        if(isDeleted){
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().body(new HttpErrorResponse(HttpStatus.NOT_FOUND.value(), "Categoria não encontrada"));
    }
}
