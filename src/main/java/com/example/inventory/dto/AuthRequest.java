package com.example.inventory.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthRequest(@NotBlank(message = "username is required") String username,
                          @NotBlank(message = "password is required") String password) {}
