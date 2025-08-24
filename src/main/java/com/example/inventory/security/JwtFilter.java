package com.example.inventory.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import com.example.inventory.model.User;
import com.example.inventory.repository.UserRepository;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public JwtFilter(JwtUtil jwtUtil, UserRepository userRepository){
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if(header!=null && header.startsWith("Bearer ")){
            String token = header.substring(7);
            try {
                var claims = jwtUtil.validateToken(token).getBody();
                String username = claims.getSubject();
                var roles = (List<String>) claims.get("roles");
                var authorities = roles.stream().map(r -> new org.springframework.security.core.authority.SimpleGrantedAuthority(r)).collect(Collectors.toList());
                var auth = new UsernamePasswordAuthenticationToken(username, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch(Exception e){
                // invalid token - ignore, Spring security will handle
            }
        }
        filterChain.doFilter(request, response);
    }
}
