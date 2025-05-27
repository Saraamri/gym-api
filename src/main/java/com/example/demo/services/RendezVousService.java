package com.example.demo.services;

import com.example.demo.entities.RendezVous;
import jakarta.validation.Valid;

import java.util.List;

public interface RendezVousService {


    RendezVous addRendezVous(RendezVous rdv);
    List<RendezVous> getAllForUser(Long userId);
    //void delete(Long id, Long userId);

    void delete(Long id);
}
