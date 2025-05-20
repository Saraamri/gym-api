package com.example.demo.serviceImplement;

import com.example.demo.entities.Reservation;
import com.example.demo.entities.UserEntity;
import com.example.demo.entities.CoursCollectif;
import com.example.demo.repository.ReservationRepo;
import com.example.demo.repository.UserRepo;
import com.example.demo.repository.CoursRepo;
import com.example.demo.services.ReservationInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationImplement implements ReservationInterface {

    @Autowired
    private ReservationRepo reservationRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CoursRepo coursRepo;

    @Override
    public Reservation addReservation(Reservation reservation) {
        // Vérifie que l'adhérent existe
        UserEntity adherent = userRepo.findById(reservation.getAdherent().getId())
                .orElseThrow(() -> new RuntimeException("Adhérent non trouvé"));

        // Vérifie que le cours existe
        CoursCollectif cours = coursRepo.findById(reservation.getCours().getId())
                .orElseThrow(() -> new RuntimeException("Cours non trouvé"));

        // Associe l'adhérent et le cours à la réservation
        reservation.setAdherent(adherent);
        reservation.setCours(cours);
        reservation.setDateReservation(reservation.getDateReservation() != null ? reservation.getDateReservation() : java.time.LocalDate.now());
        reservation.setStatut("confirmée");

        // Sauvegarde la réservation dans la base de données
        return reservationRepo.save(reservation);
    }

    @Override
    public void deleteReservation(Long id) {
        // Recherche et supprime la réservation
        Reservation reservation = reservationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Réservation non trouvée"));

        reservationRepo.delete(reservation);
    }

    @Override
    public List<Reservation> addListReservations(List<Reservation> reservations) {
        // Sauvegarde une liste de réservations
        return reservationRepo.saveAll(reservations);
    }

    @Override
    public Reservation updateReservation(Long id, Reservation reservationDetails) {
        // Vérifie si la réservation existe
        Reservation existingReservation = reservationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Réservation non trouvée"));

        // Met à jour les informations de la réservation
        existingReservation.setStatut(reservationDetails.getStatut());
        existingReservation.setDateReservation(reservationDetails.getDateReservation());

        // Sauvegarde la réservation mise à jour
        return reservationRepo.save(existingReservation);
    }

    @Override
    public List<Reservation> getAllReservations() {
        // Récupère toutes les réservations
        return reservationRepo.findAll();
    }

    @Override
    public Reservation getReservation(Long id) {
        // Récupère une réservation par son ID
        return reservationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Réservation non trouvée"));
    }

    @Override
    public List<Reservation> getReservationsByAdherent(Long adherentId) {
        // Récupère toutes les réservations d'un adhérent
        return reservationRepo.findByAdherentId(adherentId);
    }

    @Override
    public List<Reservation> getReservationsByCours(Long coursId) {
        // Récupère toutes les réservations pour un cours
        return reservationRepo.findByCoursId(coursId);
    }
    @Override
    public Reservation reserverCours(Long adherentId, Long coursId) {
        UserEntity adherent = userRepo.findById(adherentId)
                .orElseThrow(() -> new RuntimeException("Adhérent non trouvé"));

        CoursCollectif cours = coursRepo.findById(coursId)
                .orElseThrow(() -> new RuntimeException("Cours non trouvé"));

        Reservation reservation = new Reservation();
        reservation.setAdherent(adherent);
        reservation.setCours(cours);
        reservation.setDateReservation(LocalDate.now());
        reservation.setStatut("confirmée");

        return reservationRepo.save(reservation);
    }
    public boolean updateStatutReservation(Long id, String statut) {
        Optional<Reservation> optReservation = reservationRepo.findById(id);
        if (optReservation.isPresent()) {
            Reservation reservation = optReservation.get();
            reservation.setStatut(statut);
            reservationRepo.save(reservation);
            return true;
        }
        return false;
    }

    @Override
    public List<Reservation> getReservationsByCoach(Long coachId) {
        List<Reservation> allReservations = reservationRepo.findAll();
        return allReservations.stream()
                .filter(r -> r.getCours() != null && r.getCours().getCoach() != null
                        && r.getCours().getCoach().getId().equals(coachId))
                .collect(Collectors.toList());
    }

}

