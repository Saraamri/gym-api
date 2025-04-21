package com.example.demo.services;

import com.example.demo.Dto.AuthRequest;
import com.example.demo.Dto.AuthResponse;
import com.example.demo.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
  @Autowired
    private  JwtProvider jwtProvider;


    public AuthResponse authenticate(AuthRequest authRequest) {
        var auth = new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());


        String token = jwtProvider.generateToken(auth);


        return new AuthResponse(token,authRequest.getUsername(),null);
    }
}