package com.example.demo.services;

import com.example.demo.entities.Objectif;
import com.example.demo.entities.UserEntity;

import java.util.List;
import java.util.stream.Collectors;

public interface ObjectifInterface {
    // Méthode pour sauvegarder un objectif
    Objectif saveObjectif(Objectif objectif);

    // Méthode pour obtenir les objectifs d'un utilisateur spécifique
    List<Objectif> getObjectifsByUser(UserEntity user);

    // Méthode pour supprimer un objectif par ID
    void deleteObjectif(Long id);
    Objectif getObjectifById(Long id);

    List<Objectif> getAllObjectifs();


}
