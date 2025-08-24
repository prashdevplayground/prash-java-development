package com.example.inventory.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.inventory.model.Category;
import com.example.inventory.model.Product;
import com.example.inventory.repository.CategoryRepository;
import com.example.inventory.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.Optional;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    private ProductService productService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        productService = new ProductService(productRepository, categoryRepository);
    }

    @Test
    public void create_newCategory_created(){
        when(categoryRepository.findByName("newcat")).thenReturn(Optional.empty());
        when(categoryRepository.save(any(Category.class))).thenAnswer(i -> i.getArgument(0));
        when(productRepository.save(any(Product.class))).thenAnswer(i -> {
            Product p = i.getArgument(0);
            p.setId(1L);
            return p;
        });

        Product p = productService.create("Prod1","newcat",10);
        assertNotNull(p);
        assertEquals(1L, p.getId());
        assertEquals("Prod1", p.getName());
        assertEquals(10, p.getQuantity());
    }
}
