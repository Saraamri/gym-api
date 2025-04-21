package com.example.demo.controllers;

import com.example.demo.entities.Progress;
import com.example.demo.entities.UserEntity;
import com.example.demo.services.ProgressInterface;
import com.example.demo.services.UserInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/progress")
@CrossOrigin(origins = "*")
public class ProgressController {

    @Autowired
    private ProgressInterface progressInterface;

    @Autowired
    private UserInterface userInterface;


    // Ajouter un progrès pour un utilisateur spécifique
    @PostMapping("/add/{userId}")
    public ResponseEntity<?> addProgress(@PathVariable Long userId, @RequestBody @Valid Progress progress) {
        UserEntity user = userInterface.getUserById(userId);
        if (user == null) {
            return ResponseEntity.badRequest().body("Utilisateur non trouvé avec l'ID : " + userId);
        }

        progress.setUser(user);
        Progress savedProgress = progressInterface.saveProgress(progress);
        return ResponseEntity.ok(savedProgress);
    }

    // Obtenir tous les progrès d’un utilisateur
    @GetMapping("/{userId}")
    public ResponseEntity<?> getProgressByUser(@PathVariable Long userId) {
        UserEntity user = userInterface.getUserById(userId);
        if (user == null) {
            return ResponseEntity.status(404).body("Utilisateur non trouvé avec l'ID : " + userId);
        }

        List<Progress> progressList = progressInterface.getProgressByUser(user);
        return ResponseEntity.ok(progressList);
    }

    // Supprimer un progrès par son ID
    @DeleteMapping("/{progressId}")
    public ResponseEntity<?> deleteProgress(@PathVariable Long progressId) {
        progressInterface.deleteProgress(progressId);
        return ResponseEntity.ok("Progrès supprimé avec succès (ID : " + progressId + ")");
    }
}
