package com.example.inventory.service;

import com.example.inventory.dto.CategoryDtos.*;
import com.example.inventory.model.Category;
import com.example.inventory.repo.CategoryRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categories;
    public CategoryService(CategoryRepository categories){ this.categories = categories; }

    public CategoryResponse create(CategoryRequest req){
        var existing = categories.findByName(req.name);
        if (existing.isPresent()) throw new RuntimeException("Category exists");
        var saved = categories.save(Category.builder().name(req.name).build());
        var res = new CategoryResponse();
        res.id = saved.getId(); res.name = saved.getName();
        return res;
    }

    public List<Category> all(){ return categories.findAll(); }
    public Category get(Long id){ return categories.findById(id).orElseThrow(); }
}
