package com.example.inventory.service;

import org.springframework.stereotype.Service;
import com.example.inventory.repository.UserRepository;
import com.example.inventory.model.User;
import com.example.inventory.model.Role;
import com.example.inventory.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder encoder, JwtUtil jwtUtil){
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }

    public String signup(String username, String password){
        if(userRepository.findByUsername(username).isPresent()){
            throw new RuntimeException("username exists");
        }
        User u = new User();
        u.setUsername(username);
        u.setPassword(encoder.encode(password));
        u.setRoles(Set.of(Role.ROLE_USER));
        userRepository.save(u);
        var roles = u.getRoles().stream().map(Enum::name).collect(Collectors.toSet());
        return jwtUtil.generateToken(u.getUsername(), roles);
    }

    public String login(String username, String password){
        var opt = userRepository.findByUsername(username);
        if(opt.isEmpty()) throw new RuntimeException("invalid");
        var u = opt.get();
        if(!encoder.matches(password, u.getPassword())) throw new RuntimeException("invalid");
        var roles = u.getRoles().stream().map(Enum::name).collect(Collectors.toSet());
        return jwtUtil.generateToken(u.getUsername(), roles);
    }
}
