package com.example.demo.security;

import com.example.demo.entities.UserEntity;
import com.example.demo.repository.UserRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.jwt.Jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Configuration
public class JwtProvider {

    @Autowired
    private UserRepo userRepo;

    @Value("${jwt.key}")
    private String jwtKey;

    public static Long extractExpirationTime(String jwtToken) {
        return null;
    }

    public String generateToken(Authentication authentication) {
        Map<String, Object> claims = new HashMap<>();
        UserEntity user = userRepo.findByUsername(authentication.getName());
        claims.put("role", user.getRole().name());
        // Build JWT token with claims, subject, issued time, expiration time, and signing algorithm
        // Token valid for 3 minutes
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(authentication.getName())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 3))
                .compact();
              //  .signWith( SignatureAlgorithm.HS256).compact();
    }
    private Claims extractAllClaims(String token) {
        // Parse and return all claims from the token
        return Jwts.parserBuilder()

                .build().parseClaimsJws(token).getBody();
    }
    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        // Extract the specified claim using the provided function
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }
    public String extractUserName(String token) {
        // Extract and return the subject claim from the token
        return extractClaim(token, Claims::getSubject);
    }
    public Boolean validateToken(String token, UserEntity userDetails) {
        // Extract username from token and check if it matches UserDetails' username
        final String userName = extractUserName(token);
        // Also check if the token is expired
        return (userName.equals(userDetails.getUsername())) ;
    }
}
