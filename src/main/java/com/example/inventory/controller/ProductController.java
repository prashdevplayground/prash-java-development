package com.example.inventory.controller;

import org.springframework.web.bind.annotation.*;
import com.example.inventory.service.ProductService;
import com.example.inventory.dto.ProductDto;
import com.example.inventory.model.Product;
import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;
    public ProductController(ProductService productService){ this.productService = productService; }

    @GetMapping
    public List<ProductDto> all(){
        return productService.allProducts().stream().map(this::toDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProductDto get(@PathVariable Long id){
        Product p = productService.get(id).orElseThrow(() -> new RuntimeException("not found"));
        return toDto(p);
    }

    @PostMapping
    public ProductDto create(@Valid @RequestBody ProductDto dto){
        Product p = productService.create(dto.name(), dto.category(), dto.quantity());
        return toDto(p);
    }

    @PutMapping("/{id}")
    public ProductDto update(@PathVariable Long id, @Valid @RequestBody ProductDto dto){
        Product p = productService.update(id, dto.name(), dto.category(), dto.quantity());
        return toDto(p);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        productService.delete(id);
    }

    @GetMapping("/category/{name}")
    public List<ProductDto> byCategory(@PathVariable String name){
        return productService.findByCategory(name).stream().map(this::toDto).collect(Collectors.toList());
    }

    private ProductDto toDto(Product p){
        return new ProductDto(p.getId(), p.getName(), p.getCategory().getName(), p.getQuantity());
    }
}
