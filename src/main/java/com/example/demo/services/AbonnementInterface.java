package com.example.demo.services;
import com.example.demo.entities.Abonnement;
import com.example.demo.entities.UserEntity;

import java.util.List;
public interface AbonnementInterface {
    Abonnement addAbonnement(Abonnement abonnement);
    void deleteAbonnement(Long id);
    List<Abonnement> addListAbonnements(List<Abonnement> abonnements);
    Abonnement updateAbonnement(Long id, Abonnement abonnementDetails);
    List<Abonnement> getAllAbonnements();
    Abonnement getAbonnement(Long id);
    List<Abonnement> getAbonnementsByAdherent(UserEntity adherent);

}
