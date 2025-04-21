package com.example.demo.controllers;

import com.example.demo.entities.Objectif;
import com.example.demo.entities.UserEntity;
import com.example.demo.services.ObjectifInterface;
import com.example.demo.services.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/objectif")
@CrossOrigin(origins = "*")
public class ObjectifController {

    @Autowired
    private ObjectifInterface objectifInterface; // Utilisation de l'interface
    @Autowired
    private UserInterface userInterface;

    @PostMapping("/add/{userId}")
    public ResponseEntity<?> addObjectif(@PathVariable Long userId, @RequestBody Objectif objectif) {
        UserEntity user = userInterface.getUserById(userId);

        if (user == null) {
            return ResponseEntity.badRequest().body("Utilisateur non trouvé avec l'ID : " + userId);
        }

        objectif.setUser(user);
        Objectif saved = objectifInterface.saveObjectif(objectif);

        return ResponseEntity.ok(saved);
    }







    @GetMapping("/{userId}")
    public List<Objectif> getObjectifsByUser(@PathVariable Long userId) {
        UserEntity user = userInterface.getUserById(userId); // Récupère l'utilisateur
        if (user != null) {
            return objectifInterface.getObjectifsByUser(user); // Retourne la liste des objectifs via l'interface
        } else {
            return null; // Retourner null si l'utilisateur n'existe pas
        }
    }

    @DeleteMapping("/{objectifId}")
    public void deleteObjectif(@PathVariable Long objectifId) {
        objectifInterface.deleteObjectif(objectifId); // Supprime l'objectif via l'interface
    }
}
