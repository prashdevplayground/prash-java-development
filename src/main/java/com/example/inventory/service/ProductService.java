package com.example.inventory.service;

import org.springframework.stereotype.Service;
import com.example.inventory.repository.ProductRepository;
import com.example.inventory.repository.CategoryRepository;
import com.example.inventory.model.Product;
import com.example.inventory.model.Category;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Product> allProducts(){ return productRepository.findAll(); }
    public Optional<Product> get(Long id){ return productRepository.findById(id); }
    public Product create(String name, String categoryName, Integer qty){
        Category cat = categoryRepository.findByName(categoryName).orElseGet(() -> categoryRepository.save(new Category(null, categoryName)));
        Product p = new Product(null, name, cat, qty);
        return productRepository.save(p);
    }
    public Product update(Long id, String name, String categoryName, Integer qty){
        Product p = productRepository.findById(id).orElseThrow(() -> new RuntimeException("not found"));
        p.setName(name);
        Category cat = categoryRepository.findByName(categoryName).orElseGet(() -> categoryRepository.save(new Category(null, categoryName)));
        p.setCategory(cat);
        p.setQuantity(qty);
        return productRepository.save(p);
    }
    public void delete(Long id){ productRepository.deleteById(id); }

    public List<Product> findByCategory(String category){ return productRepository.findByCategory_Name(category); }
}
