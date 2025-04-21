package com.example.demo.controllers;
import com.example.demo.entities.Paiement;
import com.example.demo.entities.Abonnement;
import com.example.demo.entities.UserEntity;
import com.example.demo.services.AbonnementInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import com.example.demo.repository.PaiementRepo;
import com.example.demo.repository.AbonnementRepo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/abonnements")

public class AbonnementController {

    @Autowired
    private AbonnementRepo abonnementRepo;
    @Autowired
    private PaiementRepo paiementRepo;
    @Autowired
    private AbonnementInterface abonnementInterface;



    @PostMapping("/add")
    public ResponseEntity<Abonnement> addAbonnement(@RequestBody  @Valid Abonnement abonnement) {

        return ResponseEntity.ok(abonnementInterface.addAbonnement(abonnement));
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, String>> deleteAbonnement(@PathVariable Long id) {
        abonnementInterface.deleteAbonnement(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Abonnement supprimé avec succès");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/addList")
    public ResponseEntity<List<Abonnement>> addListAbonnements(@RequestBody @Valid List<Abonnement> abonnements) {
        return ResponseEntity.ok(abonnementInterface.addListAbonnements(abonnements));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Abonnement> updateAbonnement(@PathVariable Long id, @RequestBody @Valid Abonnement abonnement) {
        return ResponseEntity.ok(abonnementInterface.updateAbonnement(id, abonnement));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Abonnement>> getAllAbonnements() {
        return ResponseEntity.ok(abonnementInterface.getAllAbonnements());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Abonnement> getAbonnement(@PathVariable Long id) {
        return ResponseEntity.ok(abonnementInterface.getAbonnement(id));
    }

    @GetMapping("/getByAdherent/{adherentId}")
    public ResponseEntity<List<Abonnement>> getAbonnementsByAdherent(@PathVariable Long adherentId) {
        UserEntity adherent = new UserEntity();
        adherent.setId(adherentId);
        return ResponseEntity.ok(abonnementInterface.getAbonnementsByAdherent(adherent));
    }



    // ✅ Ajouter un paiement à un abonnement spécifique
    @PostMapping("/{id}/paiements/add")
    public ResponseEntity<Paiement> addPaiement(@PathVariable Long id, @RequestBody @Valid Paiement paiement) {
        Optional<Abonnement> abonnement = abonnementRepo.findById(id);
        if (abonnement.isPresent()) {
            paiement.setAbonnement(abonnement.get()); // Lier le paiement à l'abonnement
            Paiement savedPaiement = paiementRepo.save(paiement);
            return ResponseEntity.ok(savedPaiement);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // ✅ Récupérer les paiements d’un abonnement
    @GetMapping("/{id}/paiements")
    public ResponseEntity<List<Paiement>> getPaiementsByAbonnement(@PathVariable Long id) {
        List<Paiement> paiements = paiementRepo.findByAbonnementId(id);
        return ResponseEntity.ok(paiements);
    }

}

