package com.example.demo.services;

import com.example.demo.Dto.AuthRequest;
import com.example.demo.Dto.AuthResponse;
import com.example.demo.entities.UserEntity;
import com.example.demo.repository.UserRepo;
import com.example.demo.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
  @Autowired
    private  JwtProvider jwtProvider;
    @Autowired
    private UserRepo userRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;

    public AuthResponse authenticate(AuthRequest authRequest) {

      UserEntity user = userRepository.findByUsername(authRequest.getUsername()); // Supposons que tu aies une méthode pour retrouver l'utilisateur

        if (user == null) {
            // Lancer une exception si l'utilisateur n'est pas trouvé
            throw new UsernameNotFoundException("Nom d'utilisateur incorrect");
        }
      if (!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) { // À adapter si tu utilises un encoder de mot de passe
            // Lancer une exception si le mot de passe est incorrect
            throw new BadCredentialsException("Mot de passe incorrect");
        }

        var auth = new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());


        String token = jwtProvider.generateToken(auth);


        return new AuthResponse(token,authRequest.getUsername(),null);
    }
}