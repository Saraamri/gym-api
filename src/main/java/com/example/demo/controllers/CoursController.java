package com.example.demo.controllers;

import com.example.demo.entities.CoursCollectif;
import com.example.demo.services.CoursCollectifInterface;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/coursCollectifs")

public class CoursController {
    @Autowired
    private CoursCollectifInterface coursCollectifInterface;

    // Ajouter un cours
    @PostMapping(path="/add",consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })

    public ResponseEntity<CoursCollectif> addCours(
             @RequestPart ("cours") CoursCollectif cours,
            @RequestPart("image") MultipartFile imageFile) {

        return ResponseEntity.ok(coursCollectifInterface.addCours(cours, imageFile));
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
    public ResponseEntity<List<CoursCollectif>> addListCours(@RequestBody @Valid List<CoursCollectif> coursList) {
        return ResponseEntity.ok(coursCollectifInterface.addListCours(coursList));
    }

    // Modifier un cours
    @PutMapping(value = "/updateWithImage/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CoursCollectif> updateCoursWithImage(
            @PathVariable Long id,
            @RequestPart("cours") String coursJson,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            CoursCollectif cours = objectMapper.readValue(coursJson, CoursCollectif.class);

            CoursCollectif updatedCours = coursCollectifInterface.updateCoursWithImage(id, cours, imageFile);
            return ResponseEntity.ok(updatedCours);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
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