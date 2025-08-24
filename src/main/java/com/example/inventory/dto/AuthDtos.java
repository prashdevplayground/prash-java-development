package com.example.inventory.dto;

import jakarta.validation.constraints.*;

public class AuthDtos {
    public static class SignupRequest {
        @NotBlank public String username;
        @NotBlank @Size(min = 6) public String password;
        public boolean admin = false;
    }
    public static class LoginRequest {
        @NotBlank public String username;
        @NotBlank public String password;
    }
    public static class AuthResponse {
        public String token;
        public AuthResponse(String token){ this.token = token; }
    }
}
