package com.example.inventory.controller;

import org.springframework.web.bind.annotation.*;
import com.example.inventory.service.AuthService;
import com.example.inventory.dto.AuthRequest;
import com.example.inventory.dto.AuthResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService){ this.authService = authService; }

    @PostMapping("/signup")
    public AuthResponse signup(@Valid @RequestBody AuthRequest req){
        String token = authService.signup(req.username(), req.password());
        return new AuthResponse(token);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody AuthRequest req){
        String token = authService.login(req.username(), req.password());
        return new AuthResponse(token);
    }
}
