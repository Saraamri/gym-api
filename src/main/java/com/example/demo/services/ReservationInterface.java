package com.example.demo.services;

import com.example.demo.entities.Reservation;
import com.example.demo.entities.UserEntity;
import com.example.demo.entities.CoursCollectif;

import java.util.List;

public interface ReservationInterface {

    // Ajouter une réservation
    Reservation addReservation(Reservation reservation);

    // Supprimer une réservation
    void deleteReservation(Long id);

    // Ajouter plusieurs réservations
    List<Reservation> addListReservations(List<Reservation> reservations);

    // Mettre à jour une réservation
    Reservation updateReservation(Long id, Reservation reservationDetails);

    // Récupérer toutes les réservations
    List<Reservation> getAllReservations();

    // Récupérer une réservation par son ID
    Reservation getReservation(Long id);

    // Récupérer les réservations d'un adhérent spécifique
    List<Reservation> getReservationsByAdherent(Long adherentID);

    // Récupérer les réservations pour un cours collectif spécifique
    List<Reservation> getReservationsByCours(Long coursID);
    Reservation reserverCours(Long adherentId, Long coursId);

    boolean updateStatutReservation(Long id, String statut);

    List<Reservation> getReservationsByCoach(Long coachId);
}

