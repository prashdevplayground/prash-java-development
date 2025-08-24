package com.example.inventory.model;

import jakarta.persistence.*;

@Entity
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    private Category category;

    private Integer quantity;

    public Product(){}
    public Product(Long id, String name, Category category, Integer quantity){
        this.id = id; this.name = name; this.category = category; this.quantity = quantity;
    }

    public Long getId(){ return id; }
    public void setId(Long id){ this.id = id; }
    public String getName(){ return name; }
    public void setName(String name){ this.name = name; }
    public Category getCategory(){ return category; }
    public void setCategory(Category category){ this.category = category; }
    public Integer getQuantity(){ return quantity; }
    public void setQuantity(Integer quantity){ this.quantity = quantity; }
}
