package com.example.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.inventory.model.Product;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory_Name(String name);
}
