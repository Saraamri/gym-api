package com.example.demo.services;

import com.example.demo.entities.PasswordResetToken;
import com.example.demo.entities.UserEntity;
import com.example.demo.repository.PasswordResetTokenRepository;
import com.example.demo.repository.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetService {

    @Autowired
    private PasswordResetTokenRepository tokenRepo;

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private EmailService emailService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Génère un token et envoie un mail de réinitialisation
     */
    public void requestPasswordReset(String email) throws Exception {
        UserEntity user = userRepository.findByEmail(email)

                .orElseThrow(() -> new Exception("Email non trouvé"));
        tokenRepo.findByUser(user).ifPresent(tokenRepo::delete);

        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpirationDate(LocalDateTime.now().plusMinutes(30)); // Valide 30 minutes

        tokenRepo.save(resetToken);

        emailService.sendResetPasswordEmail(user.getEmail(), token);
    }

    /**
     * Réinitialise le mot de passe en vérifiant le token
     */
    public void resetPassword(String token, String newPassword) throws Exception {
        PasswordResetToken resetToken = tokenRepo.findByToken(token)
                .orElseThrow(() -> new Exception("Token invalide"));

        if (resetToken.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new Exception("Token expiré");
        }

        UserEntity user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        tokenRepo.delete(resetToken); // Invalide le token après utilisation
    }
}

