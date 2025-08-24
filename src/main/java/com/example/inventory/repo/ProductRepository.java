package com.example.inventory.repo;

import com.example.inventory.model.Product;
import com.example.inventory.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(Category category);

    @Query("select p from Product p where lower(p.name) like lower(concat('%', :q, '%'))")
    List<Product> searchByName(@Param("q") String q);
}
