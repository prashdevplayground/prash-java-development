package com.example.inventory.dto;

import java.math.BigDecimal;

public class ProductDtos {
    public static class ProductRequest {
        public String name;
        public Long categoryId;
        public BigDecimal price;
        public Integer quantity;
    }
    public static class ProductResponse {
        public Long id;
        public String name;
        public String category;
        public BigDecimal price;
        public Integer quantity;
    }
}
