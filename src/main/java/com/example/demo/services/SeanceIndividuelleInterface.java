package com.example.demo.services;

import com.example.demo.entities.SeanceIndividuelle;

import java.util.List;

public interface SeanceIndividuelleInterface {
    SeanceIndividuelle addSeance(SeanceIndividuelle seance);
    void deleteSeance(Long id);
    SeanceIndividuelle updateSeance(Long id, SeanceIndividuelle seance);
    List<SeanceIndividuelle> getAllSeances();
    SeanceIndividuelle getSeanceById(Long id);
    List<SeanceIndividuelle> getSeancesByCoach(Long coachId);
    List<SeanceIndividuelle> getSeancesByAdherent(Long adherentId);
    // Proposer une séance (créée par un coach)
    SeanceIndividuelle proposerParCoach(SeanceIndividuelle seance);

    // Demander une séance (créée par un adhérent)
    SeanceIndividuelle demandeParAdherent(SeanceIndividuelle seance);

    // Changer le statut de la séance (acceptée, refusée, etc.)
    SeanceIndividuelle changerStatutSeance(Long id, String statut);
}
