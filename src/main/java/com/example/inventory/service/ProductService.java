package com.example.inventory.service;

import com.example.inventory.dto.ProductDtos.*;
import com.example.inventory.model.Category;
import com.example.inventory.model.Product;
import com.example.inventory.repo.CategoryRepository;
import com.example.inventory.repo.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository products;
    private final CategoryRepository categories;

    public ProductService(ProductRepository products, CategoryRepository categories){
        this.products = products; this.categories = categories;
    }

    @Transactional
    public ProductResponse create(ProductRequest req){
        Category cat = categories.findById(req.categoryId).orElseThrow();
        Product p = products.save(Product.builder()
                .name(req.name).category(cat).price(req.price).quantity(req.quantity == null ? 0 : req.quantity)
                .build());
        ProductResponse res = new ProductResponse();
        res.id = p.getId(); res.name = p.getName(); res.category = p.getCategory().getName();
        res.price = p.getPrice(); res.quantity = p.getQuantity();
        return res;
    }

    public List<Product> all(){ return products.findAll(); }

    public Product get(Long id){ return products.findById(id).orElseThrow(); }

    public List<Product> search(String q){ return products.searchByName(q == null ? "" : q); }

    public ProductResponse toDto(Product p){
        ProductResponse res = new ProductResponse();
        res.id = p.getId(); res.name = p.getName(); res.category = p.getCategory().getName();
        res.price = p.getPrice(); res.quantity = p.getQuantity();
        return res;
    }
}
