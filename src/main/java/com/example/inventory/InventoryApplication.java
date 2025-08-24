package com.example.inventory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.inventory.model.Category;
import com.example.inventory.model.Product;
import com.example.inventory.model.Role;
import com.example.inventory.model.User;
import com.example.inventory.repository.CategoryRepository;
import com.example.inventory.repository.ProductRepository;
import com.example.inventory.repository.UserRepository;

import java.util.Set;

@SpringBootApplication
public class InventoryApplication {
    public static void main(String[] args){
        SpringApplication.run(InventoryApplication.class, args);
    }

    // seed data
    @Bean
    CommandLineRunner runner(CategoryRepository catRepo, ProductRepository prodRepo, UserRepository userRepo){
        return args -> {
            if(catRepo.count()==0){
                Category cameras = new Category(null, "camera");
                Category thermostats = new Category(null, "thermostats");
                catRepo.save(cameras);
                catRepo.save(thermostats);

                prodRepo.save(new Product(null, "Apple camera", cameras, 50));
                prodRepo.save(new Product(null, "Sony camera", cameras, 30));
                prodRepo.save(new Product(null, "Samsung camera", cameras, 20));
                prodRepo.save(new Product(null, "Foxconn Thermostats", thermostats, 80));
                prodRepo.save(new Product(null, "Qualcomm Thermostats", thermostats, 60));
            }
            if(userRepo.count()==0){
                var encoder = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
                User admin = new User(null, "admin", encoder.encode("admin123"), Set.of(Role.ROLE_ADMIN));
                User user = new User(null, "user", encoder.encode("user123"), Set.of(Role.ROLE_USER));
                userRepo.save(admin);
                userRepo.save(user);
            }
        };
    }
}
