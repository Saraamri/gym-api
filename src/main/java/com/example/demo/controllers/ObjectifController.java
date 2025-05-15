package com.example.demo.controllers;

import com.example.demo.entities.Objectif;
import com.example.demo.entities.UserEntity;
import com.example.demo.services.ObjectifInterface;
import com.example.demo.services.UserInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/objectif")
@CrossOrigin(origins = "*")
public class ObjectifController {

    @Autowired
    private ObjectifInterface objectifInterface;

    @Autowired
    private UserInterface userInterface;

    // ✅ Ajouter un objectif à un utilisateur
    @PostMapping("/add/{userId}")
    public ResponseEntity<?> addObjectif(@PathVariable Long userId, @RequestBody @Valid Objectif objectif) {
        UserEntity user = userInterface.getUserById(userId);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur non trouvé avec l'ID : " + userId);
        }

        objectif.setUser(user);
        Objectif saved = objectifInterface.saveObjectif(objectif);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // ✅ Récupérer tous les objectifs d’un utilisateur
    @GetMapping("/{userId}")
    public ResponseEntity<?> getObjectifsByUser(@PathVariable Long userId) {
        UserEntity user = userInterface.getUserById(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur non trouvé avec l'ID : " + userId);
        }

        List<Objectif> objectifs = objectifInterface.getObjectifsByUser(user);
        return ResponseEntity.ok(objectifs);
    }

    // ✅ Supprimer un objectif
    @DeleteMapping("/{objectifId}")
    public ResponseEntity<?> deleteObjectif(@PathVariable Long objectifId) {
        objectifInterface.deleteObjectif(objectifId);
        return ResponseEntity.ok("Objectif supprimé avec succès.");
    }

    // ✅ Récupérer tous les objectifs
    @GetMapping("/all")
    public ResponseEntity<List<Objectif>> getAllObjectifs() {
        return ResponseEntity.ok(objectifInterface.getAllObjectifs());
    }



    // ✅ Mettre à jour un objectif
    @PutMapping("/update/{objectifId}")
    public ResponseEntity<?> updateObjectif(@PathVariable Long objectifId, @RequestBody @Valid Objectif updatedObjectif) {
        Objectif existing = objectifInterface.getObjectifById(objectifId);
        if (existing == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Objectif non trouvé avec l'ID : " + objectifId);
        }

        // Mise à jour des champs
        existing.setType(updatedObjectif.getType());
        existing.setPoidsCible(updatedObjectif.getPoidsCible());
        existing.setTailleCible(updatedObjectif.getTailleCible());
        existing.setImcCible(updatedObjectif.getImcCible());
        existing.setBodyFatPercentageCible(updatedObjectif.getBodyFatPercentageCible());
        existing.setMuscleMassCible(updatedObjectif.getMuscleMassCible());
        existing.setFrequency(updatedObjectif.getFrequency());
        existing.setTargetDate(updatedObjectif.getTargetDate());

        Objectif updated = objectifInterface.saveObjectif(existing);
        return ResponseEntity.ok(updated);
    }

}
