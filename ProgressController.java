package com.example.demo.controllers;

import com.example.demo.entities.Progress;
import com.example.demo.entities.UserEntity;
import com.example.demo.services.ProgressInterface;
import com.example.demo.services.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/progress")
@CrossOrigin(origins = "*")
public class ProgressController {

    @Autowired
    private ProgressInterface progressService; // Interface pour gérer les progrès

    @Autowired
    private UserInterface userService; // Interface pour gérer les utilisateurs

    // Méthode pour ajouter un progrès
    @PostMapping("/{userId}")
    public Progress addProgress(@PathVariable Long userId, @RequestBody Progress progress) {
        UserEntity user = userService.getUserById(userId); // Appel à la méthode getUserById via l'instance userService
        if (user != null) {
            progress.setUser(user); // Associer l'utilisateur au progrès
            return progressService.saveProgress(progress); // Sauvegarder le progrès
        } else {
            return null; // Si l'utilisateur n'existe pas, retourne null
        }
    }

    // Méthode pour obtenir les progrès d'un utilisateur
    @GetMapping("/{userId}")
    public List<Progress> getProgressByUser(@PathVariable Long userId) {
        UserEntity user = userService.getUserById(userId); // Appel à la méthode getUserById via l'instance userService
        if (user != null) {
            return progressService.getProgressByUser(user); // Retourne les progrès de l'utilisateur
        } else {
            return null; // Si l'utilisateur n'existe pas, retourne null
        }
    }

    // Méthode pour supprimer un progrès par son ID
    @DeleteMapping("/{progressId}")
    public void deleteProgress(@PathVariable Long progressId) {
        progressService.deleteProgress(progressId); // Supprime le progrès
    }
}
