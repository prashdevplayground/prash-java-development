package com.example.inventory.controller;

import com.example.inventory.dto.CategoryDtos.*;
import com.example.inventory.model.Category;
import com.example.inventory.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categories;
    public CategoryController(CategoryService categories){ this.categories = categories; }

    @PostMapping
    public ResponseEntity<CategoryResponse> create(@RequestBody CategoryRequest req){
        return ResponseEntity.ok(categories.create(req));
    }

    @GetMapping
    public List<Category> all(){ return categories.all(); }
}
