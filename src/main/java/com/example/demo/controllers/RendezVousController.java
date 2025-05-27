package com.example.demo.controllers;

import com.example.demo.entities.Abonnement;
import com.example.demo.entities.RendezVous;
import com.example.demo.entities.UserEntity;
import com.example.demo.services.RendezVousService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins ="*")
@RestController
@RequestMapping("/rendezvous")
public class RendezVousController {

    @Autowired
    private RendezVousService service;




    @PostMapping("/add")
    public ResponseEntity<RendezVous> addRendezVous(@RequestBody  @Valid RendezVous rdv) {

        return ResponseEntity.ok(service.addRendezVous(rdv));
    }



    @GetMapping("/{userId}")
    public ResponseEntity<?> getAll(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getAllForUser(userId));
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok("Supprimé");
    }
}
