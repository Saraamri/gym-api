package com.example.demo.controllers;

import com.example.demo.entities.SeanceIndividuelle;
import com.example.demo.services.SeanceIndividuelleInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seances")
@CrossOrigin(origins = "*")
public class SeanceIndividuelleController {

    @Autowired
    private SeanceIndividuelleInterface service;

    @PostMapping
    public ResponseEntity<SeanceIndividuelle> create(@RequestBody SeanceIndividuelle s) {
        return ResponseEntity.ok(service.addSeance(s));
    }

    @GetMapping
    public List<SeanceIndividuelle> getAll() {
        return service.getAllSeances();
    }

    @GetMapping("/{id}")
    public SeanceIndividuelle getById(@PathVariable Long id) {
        return service.getSeanceById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteSeance(id);
    }

    @PutMapping("/{id}")
    public SeanceIndividuelle update(@PathVariable Long id, @RequestBody SeanceIndividuelle s) {
        return service.updateSeance(id, s);
    }

    @GetMapping("/coach/{coachId}")
    public List<SeanceIndividuelle> getByCoach(@PathVariable Long coachId) {
        return service.getSeancesByCoach(coachId);
    }

    @GetMapping("/adherent/{adherentId}")
    public List<SeanceIndividuelle> getByAdherent(@PathVariable Long adherentId) {
        return service.getSeancesByAdherent(adherentId);
    }
    // Coach propose une séance
    @PostMapping("/proposer")
    public ResponseEntity<SeanceIndividuelle> proposerParCoach(@RequestBody SeanceIndividuelle s) {
        s.setProposeeParCoach(true); // Indiquer que c'est une séance proposée par le coach
        s.setStatut("proposée");
        return ResponseEntity.ok(service.addSeance(s));
    }

    // Adhérent fait une demande de séance
    @PostMapping("/demande")
    public ResponseEntity<SeanceIndividuelle> demandeParAdherent(@RequestBody SeanceIndividuelle s) {
        s.setProposeeParCoach(false); // Indiquer que c'est une demande de l'adhérent
        s.setStatut("en attente");
        return ResponseEntity.ok(service.addSeance(s));
    }

    // Coach change le statut de la séance (acceptée, refusée, etc.)
    @PutMapping("/{id}/statut")
    public ResponseEntity<SeanceIndividuelle> changerStatut(@PathVariable Long id, @RequestParam String statut) {
        return ResponseEntity.ok(service.changerStatutSeance(id, statut));
    }
}

