package com.example.demo.serviceImplement;

import com.example.demo.entities.SeanceIndividuelle;

import java.util.List;
import com.example.demo.repository.SeanceIndividuelleRepo;
import com.example.demo.services.SeanceIndividuelleInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class SeanceIndividuelleImplement implements SeanceIndividuelleInterface {

    @Autowired
    private SeanceIndividuelleRepo repo;

    @Override
    public SeanceIndividuelle addSeance(SeanceIndividuelle seance) {
        return repo.save(seance);
    }

    @Override
    public void deleteSeance(Long id) {
        repo.deleteById(id);
    }

    @Override
    public SeanceIndividuelle updateSeance(Long id, SeanceIndividuelle seance) {
        seance.setId(id);
        return repo.save(seance);
    }

    @Override
    public List<SeanceIndividuelle> getAllSeances() {
        return repo.findAll();
    }

    @Override
    public SeanceIndividuelle getSeanceById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Séance non trouvée"));
    }

    @Override
    public List<SeanceIndividuelle> getSeancesByCoach(Long coachId) {
        return repo.findByCoachId(coachId);
    }

    @Override
    public List<SeanceIndividuelle> getSeancesByAdherent(Long adherentId) {
        return repo.findByAdherentId(adherentId);
    }
    // Proposer une séance par le coach
    @Override
    public SeanceIndividuelle proposerParCoach(SeanceIndividuelle seance) {
        // Le coach peut proposer une séance en définissant les détails de la séance
        // Ici, tu pourrais ajouter des vérifications, comme si le coach existe, ou si la date est valide, etc.
        return repo.save(seance);
    }

    // Demander une séance par un adhérent
    @Override
    public SeanceIndividuelle demandeParAdherent(SeanceIndividuelle seance) {
        // L'adhérent demande une séance, elle est enregistrée avec son statut "en attente" ou similaire
        seance.setStatut("En attente");
        return repo.save(seance);
    }

    // Changer le statut de la séance (acceptée, refusée, etc.)
    @Override
    public SeanceIndividuelle changerStatutSeance(Long id, String statut) {
        SeanceIndividuelle seance = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Séance non trouvée"));
        seance.setStatut(statut);
        return repo.save(seance);
    }
    @Override
    public List<SeanceIndividuelle> getSeancesProposeesParCoach() {
        return repo.findByProposeeParCoachTrueAndStatut("proposée");
    }

}

