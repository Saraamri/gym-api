package com.example.demo.repository;
import com.example.demo.entities.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PaiementRepo extends JpaRepository<Paiement, Long> {


    List<Paiement> findByAbonnementId(Long abonnementId);
    @Query("SELECT COALESCE(SUM(p.montant), 0) FROM Paiement p")
    double sumPaiements();

}