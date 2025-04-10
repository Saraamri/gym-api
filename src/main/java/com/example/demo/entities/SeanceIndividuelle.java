package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
public class SeanceIndividuelle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime date;
    private String statut; // "disponible", "en attente", "réservée", "acceptée", "refusée"
    private String origine; // "coach" ou "adherent"
    private String lieu; // salle, extérieur, etc.
    private String objectif; // gain de force, cardio, etc.
    // Indicateur si la séance a été proposée par un coach
    private boolean proposeeParCoach;
    @ManyToOne
    @JoinColumn(name = "coach_id")
    private UserEntity coach;

    @ManyToOne
    @JoinColumn(name = "adherent_id")
    private UserEntity adherent;
}





