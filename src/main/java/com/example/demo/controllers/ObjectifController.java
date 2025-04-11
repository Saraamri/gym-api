package com.example.demo.controllers;

import com.example.demo.entities.Objectif;
import com.example.demo.entities.UserEntity;
import com.example.demo.services.ObjectifInterface;
import com.example.demo.services.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/objectif")
public class ObjectifController {

    @Autowired
    private ObjectifInterface objectifService; // Utilisation de l'interface

    @Autowired
    private UserInterface userService;

    @PostMapping("/{userId}")
    public Objectif addObjectif(@PathVariable Long userId, @RequestBody Objectif objectif) {
        UserEntity user = userService.getUserById(userId); // Récupère l'utilisateur par son ID
        if (user != null) {
            objectif.setUser(user); // Associe l'objectif à l'utilisateur
            return objectifService.saveObjectif(objectif); // Sauvegarde l'objectif via l'interface
        } else {
            return null; // Retourner null si l'utilisateur n'existe pas
        }
    }

    @GetMapping("/{userId}")
    public List<Objectif> getObjectifsByUser(@PathVariable Long userId) {
        UserEntity user = userService.getUserById(userId); // Récupère l'utilisateur
        if (user != null) {
            return objectifService.getObjectifsByUser(user); // Retourne la liste des objectifs via l'interface
        } else {
            return null; // Retourner null si l'utilisateur n'existe pas
        }
    }

    @DeleteMapping("/{objectifId}")
    public void deleteObjectif(@PathVariable Long objectifId) {
        objectifService.deleteObjectif(objectifId); // Supprime l'objectif via l'interface
    }
}
