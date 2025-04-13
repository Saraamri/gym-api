package com.example.demo.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;

@Configuration
public class JwtProvider {

    @Value("${jwt.key}")
    private String jwtKey;

    public static Long extractExpirationTime(String jwtToken) {
        return null;
    }

    public String generateToken(Authentication authentication) {
        return "";
    }
}
