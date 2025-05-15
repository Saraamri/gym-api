package com.example.demo.security;

import com.example.demo.entities.UserEntity;
import com.example.demo.repository.UserRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Autowired
    private UserRepo userRepo;

    @Value("${jwt.key}")
    private String jwtKey;

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public Boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }



    public String generateToken(Authentication authentication) {
        Map<String, Object> claims = new HashMap<>();
        UserEntity user = userRepo.findByUsername(authentication.getName());
        claims.put("role", user.getRole().name());
        claims.put("id", user.getId());
        claims.put("email", user.getEmail());
        claims.put("username", user.getUsername());


        Date now = new Date(System.currentTimeMillis());
        Date expiration = new Date(now.getTime() + 1000 * 60 * 3);

        logger.info("Date d'expiration du token : {}", expiration);
        return  Jwts.builder()
                .setClaims(claims)
                .setSubject(authentication.getName())
                .setIssuedAt(now)
                .setExpiration(expiration) // 3 minutes
                .signWith(getSignKey(), SignatureAlgorithm.HS256) // signer ici AVANT compact
                .compact();
    }


    private Claims extractAllClaims(String token) {
        // Parse and return all claims from the token
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())

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
        return (userName.equals(userDetails.getUsername())&& !isTokenExpired(token)) ;
    }
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
