package com.example.inventory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

public record ProductDto(Long id,
                         @NotBlank(message = "name is required") String name,
                         @NotBlank(message = "category is required") String category,
                         @NotNull(message = "quantity required") @Min(value = 0, message = "quantity must be >= 0") Integer quantity) {}
