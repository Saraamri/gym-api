package com.example.demo.controllers;

import com.example.demo.services.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/password")
public class PasswordResetController {

    @Autowired
    private PasswordResetService resetService;

    @PostMapping("/request-reset")
    public ResponseEntity<String> requestReset(@RequestParam String email) {
        try {
            resetService.requestPasswordReset(email);
            return ResponseEntity.ok("Un lien de réinitialisation a été envoyé.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(@RequestParam String token,
                                                @RequestParam String newPassword) {
        try {
            resetService.resetPassword(token, newPassword);
            return ResponseEntity.ok("Mot de passe mis à jour avec succès.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

