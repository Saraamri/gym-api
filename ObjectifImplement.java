package com.example.demo.serviceImplement;

import com.example.demo.entities.Objectif;
import com.example.demo.entities.UserEntity;
import com.example.demo.repository.ObjectifRepo;
import com.example.demo.services.ObjectifInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObjectifImplement implements ObjectifInterface {

    @Autowired
    private ObjectifRepo objectifRepo; // Utilisation du repository pour accéder aux données

    // Méthode pour sauvegarder un objectif
    @Override
    public Objectif saveObjectif(Objectif objectif) {
        return objectifRepo.save(objectif); // Sauvegarde l'objectif dans la base de données
    }

    // Méthode pour obtenir les objectifs d'un utilisateur
    @Override
    public List<Objectif> getObjectifsByUser(UserEntity user) {
        return objectifRepo.findByUser(user); // Recherche des objectifs de l'utilisateur dans la base de données
    }

    // Méthode pour supprimer un objectif par ID
    @Override
    public void deleteObjectif(Long id) {
        objectifRepo.deleteById(id); // Supprime un objectif de la base de données par son ID
    }
}
