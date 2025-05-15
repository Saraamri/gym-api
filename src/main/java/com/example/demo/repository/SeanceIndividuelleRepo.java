package com.example.demo.repository;

import com.example.demo.entities.SeanceIndividuelle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeanceIndividuelleRepo extends JpaRepository<SeanceIndividuelle, Long> {
    List<SeanceIndividuelle> findByCoachId(Long coachId);
    List<SeanceIndividuelle> findByAdherentId(Long adherentId);
    List<SeanceIndividuelle> findByProposeeParCoachTrueAndStatut(String statut);

}
