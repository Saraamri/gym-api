package com.example.demo.controllers;

import com.example.demo.entities.Reservation;
import com.example.demo.services.ReservationInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationsController {

    @Autowired
    private ReservationInterface reservationInterface;

    // Réserver un cours
    @PostMapping
    public ResponseEntity<Reservation> reserverCours(@RequestParam Long adherentId, @RequestParam Long coursId) {
        Reservation reservation = reservationInterface.reserverCours(adherentId, coursId);
        return ResponseEntity.ok(reservation);
    }

    // Obtenir toutes les réservations d'un adhérent
    @GetMapping("/adherent/{id}")
    public List<Reservation> getReservationsByAdherent(@PathVariable Long id) {
        return reservationInterface.getReservationsByAdherent(id);
    }

    // Obtenir toutes les réservations d'un cours
    @GetMapping("/cours/{id}")
    public List<Reservation> getReservationsByCours(@PathVariable Long id) {
        return reservationInterface.getReservationsByCours(id);
    }
}

