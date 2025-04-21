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

    @Positive(message = "Le poids cible doit être supérieur à 0")
    private double poids;

    @Positive(message = "La taille cible doit être supérieure à 0")
    private double taille;

    @Positive(message = "L'IMC cible doit être supérieur à 0")
    private double imc;

    @PositiveOrZero(message = "Le pourcentage de graisse corporelle doit être positif ou nul")
    private double bodyFatPercentage;

    @PositiveOrZero(message = "La masse musculaire doit être positive ou nulle")
    private double muscleMass;

    @PositiveOrZero(message = "Le tour de taille doit être positif ou nul")
    private double waistCircumference;

    @Min(value = 1, message = "La fréquence d'entraînement doit être au moins de 1 jour/semaine")
    @Max(value = 7, message = "La fréquence d'entraînement ne peut pas dépasser 7 jours/semaine")
    private int trainingFrequency;

    @PositiveOrZero(message = "L'objectif de force doit être positif ou nul")
    private double strengthGoal;

    @PositiveOrZero(message = "L'objectif de performance doit être positif ou nul")
    private double performanceGoal;

    @NotNull(message = "La date cible est requise")
    @Future(message = "La date cible doit être dans le futur")
    private LocalDate targetDate;
}
