package com.example.inventory.controller;

import com.example.inventory.dto.ProductDtos.*;
import com.example.inventory.model.Product;
import com.example.inventory.service.AIService;
import com.example.inventory.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService products;
    private final AIService ai;
    public ProductController(ProductService products, AIService ai){ this.products = products; this.ai = ai; }

    @PostMapping
    public ResponseEntity<ProductResponse> create(@RequestBody ProductRequest req){
        return ResponseEntity.ok(products.create(req));
    }

    @GetMapping
    public List<Product> all(){ return products.all(); }

    @GetMapping("/search")
    public List<Product> search(@RequestParam String q){ return products.search(q); }

    @GetMapping("/{id}/ai/reorder")
    public Map<String, Object> aiReorder(@PathVariable Long id){
        var p = products.get(id);
        return ai.reorderRecommendation(p);
    }
}
