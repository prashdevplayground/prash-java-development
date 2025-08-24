package com.example.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.inventory.model.Category;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
}
