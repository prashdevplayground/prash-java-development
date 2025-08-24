package com.example.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.example.inventory.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
