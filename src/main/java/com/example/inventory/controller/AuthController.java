package com.example.inventory.controller;

import com.example.inventory.dto.AuthDtos.*;
import com.example.inventory.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService users;
    public AuthController(UserService users){ this.users = users; }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody SignupRequest req){
        String token = users.signup(req);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest req){
        String token = users.login(req);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
