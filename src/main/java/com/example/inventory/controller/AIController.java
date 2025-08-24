package com.example.inventory.controller;

import org.springframework.web.bind.annotation.*;
import com.example.inventory.repository.ProductRepository;
import com.example.inventory.model.Product;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ai")
public class AIController {
    private final ProductRepository productRepository;
    public AIController(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    // Simple rule-based "AI" recommendations: suggest reorder for low stock < threshold
    @GetMapping("/recommendations")
    public List<String> recommendations(@RequestParam(defaultValue = "25") int threshold){
        return productRepository.findAll().stream()
                .filter(p -> p.getQuantity() != null && p.getQuantity() < threshold)
                .map(p -> String.format("Reorder recommended for '%s' (qty=%d)", p.getName(), p.getQuantity()))
                .collect(Collectors.toList());
    }
}
