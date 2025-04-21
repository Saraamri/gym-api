package com.example.demo.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
public class SeanceIndividuelle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La date de la séance est obligatoire")
    @FutureOrPresent(message = "La date de la séance ne peut pas être dans le passé")
    private LocalDateTime date;

    @NotBlank(message = "Le statut est obligatoire")
    private String statut; // "disponible", "en attente", "réservée", "acceptée", "refusée"

    @NotBlank(message = "L'origine est obligatoire")
    private String origine; // "coach" ou "adherent"

    @NotBlank(message = "Le lieu est obligatoire")
    private String lieu; // salle, extérieur, etc.

    @NotBlank(message = "L'objectif est obligatoire")
    private String objectif; // gain de force, cardio, etc.

    private boolean proposeeParCoach;

   // @NotNull(message = "Le coach est obligatoire")
    @ManyToOne
    @JoinColumn(name = "coach_id")
    private UserEntity coach;

    //@NotNull(message = "L'adhérent est obligatoire")
    @ManyToOne
    @JoinColumn(name = "adherent_id")
    private UserEntity adherent;
}
