package com.example.inventory.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    private final Key key = Keys.hmacShaKeyFor(System.getenv().getOrDefault("JWT_SECRET",
            "change-me-change-me-change-me-32chars!").getBytes());

    public String generate(String username, java.util.Collection<String> roles){
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + 1000L * 60 * 60 * 10)) // 10 hours
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Jws<Claims> parse(String token){
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
    }
}
