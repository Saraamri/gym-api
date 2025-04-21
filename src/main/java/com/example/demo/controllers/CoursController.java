package com.example.demo.controllers;

import com.example.demo.entities.CoursCollectif;
import com.example.demo.services.CoursCollectifInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.*;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/coursCollectifs")

public class CoursController {
    @Autowired
    private CoursCollectifInterface coursCollectifInterface;
    // Ajouter un cours
    @PostMapping("/add")
    public ResponseEntity<CoursCollectif> addCours(@RequestBody CoursCollectif cours) {
        return ResponseEntity.ok(coursCollectifInterface.addCours(cours));
    }

    // Supprimer un cours
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, String>> deleteCours(@PathVariable Long id) {
        coursCollectifInterface.deleteCours(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Cours collectif supprimé avec succès");
        return ResponseEntity.ok(response);
    }

    // Ajouter une liste de cours
    @PostMapping("/addList")
    public ResponseEntity<List<CoursCollectif>> addListCours(@RequestBody List<CoursCollectif> coursList) {
        return ResponseEntity.ok(coursCollectifInterface.addListCours(coursList));
    }

    // Modifier un cours
    @PutMapping("/update/{id}")
    public ResponseEntity<CoursCollectif> updateCours(@PathVariable Long id, @RequestBody CoursCollectif cours) {
        return ResponseEntity.ok(coursCollectifInterface.updateCours(id, cours));
    }

    // Récupérer tous les cours
    @GetMapping("/getAll")
    public ResponseEntity<List<CoursCollectif>> getAllCours() {
        return ResponseEntity.ok(coursCollectifInterface.getAllCours());
    }

    // Récupérer un cours par ID
    @GetMapping("/get/{id}")
    public ResponseEntity<CoursCollectif> getCoursById(@PathVariable Long id) {
        return ResponseEntity.ok(coursCollectifInterface.getCoursById(id));
    }

    // (Optionnel) Récupérer les cours selon un coach par exemple
    /*@GetMapping("/getByCoach/{coachId}")
    public ResponseEntity<List<CoursCollectif>> getCoursByCoach(@PathVariable Long coachId) {
        return ResponseEntity.ok(coursCollectifInterface.getCoursByCoach(coachId));
    }*/

}