package com.example.demo.repository;
import com.example.demo.entities.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PaiementRepo extends JpaRepository<Paiement, Long> {

    // ✅ Trouver tous les paiements associés à un abonnement donné
    List<Paiement> findByAbonnementId(Long abonnementId);
}