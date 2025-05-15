package com.example.demo.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Objectif {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "L'utilisateur est requis")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @NotNull(message = "Le type d'objectif est requis")

    private String type;

    @PositiveOrZero(message = "Les valeurs doivent être positives ou nulles")
    private Double poidsCible; // Poids cible (si pertinent)

    @PositiveOrZero(message = "Les valeurs doivent être positives ou nulles")
    private Double tailleCible; // Taille cible (si pertinent)

    @PositiveOrZero(message = "Les valeurs doivent être positives ou nulles")
    private Double imcCible; // IMC cible (si pertinent)

    @PositiveOrZero(message = "Les valeurs doivent être positives ou nulles")
    private Double bodyFatPercentageCible; // Graisse corporelle cible (si pertinent)

    @PositiveOrZero(message = "Les valeurs doivent être positives ou nulles")
    private Double muscleMassCible; // Masse musculaire cible (si pertinent)

    @Min(value = 1, message = "La fréquence d'entraînement doit être au moins de 1 jour/semaine")
    @Max(value = 7, message = "La fréquence d'entraînement ne peut pas dépasser 7 jours/semaine")
    private Integer frequency; // Fréquence d'entraînement par semaine

    @NotNull(message = "La date cible est requise")
    @Future(message = "La date cible doit être dans le futur")
    private LocalDate targetDate; // Date à laquelle l'objectif doit être atteint

}
