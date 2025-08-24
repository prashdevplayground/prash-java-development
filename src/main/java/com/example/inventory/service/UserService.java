package com.example.inventory.service;

import com.example.inventory.dto.AuthDtos.*;
import com.example.inventory.model.Role;
import com.example.inventory.model.User;
import com.example.inventory.repo.RoleRepository;
import com.example.inventory.repo.UserRepository;
import com.example.inventory.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository users;
    private final RoleRepository roles;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final JwtUtil jwt;

    public UserService(UserRepository users, RoleRepository roles, JwtUtil jwt){
        this.users = users; this.roles = roles; this.jwt = jwt;
    }

    @Transactional
    public String signup(SignupRequest req){
        if (users.existsByUsername(req.username)) throw new RuntimeException("Username already exists");
        Role roleUser = roles.findByName("ROLE_USER").orElseGet(() -> roles.save(Role.builder().name("ROLE_USER").build()));
        Role roleAdmin = roles.findByName("ROLE_ADMIN").orElseGet(() -> roles.save(Role.builder().name("ROLE_ADMIN").build()));
        var user = User.builder()
                .username(req.username)
                .password(encoder.encode(req.password))
                .roles(req.admin ? Set.of(roleUser, roleAdmin) : Set.of(roleUser))
                .build();
        users.save(user);
        return jwt.generate(user.getUsername(), user.getRoles().stream().map(Role::getName).toList());
    }

    public String login(LoginRequest req){
        var user = users.findByUsername(req.username).orElseThrow(() -> new RuntimeException("Invalid credentials"));
        if (!encoder.matches(req.password, user.getPassword())) throw new RuntimeException("Invalid credentials");
        return jwt.generate(user.getUsername(), user.getRoles().stream().map(Role::getName).toList());
    }
}
