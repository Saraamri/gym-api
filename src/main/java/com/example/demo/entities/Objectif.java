package com.example.demo.entities;


import jakarta.persistence.*;
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user; // L'utilisateur associé à l'objectif

    private double poids; // Poids cible
    private double taille; // Taille cible
    private double imc; // IMC cible
    private double bodyFatPercentage; // Pourcentage de graisse corporelle cible
    private double muscleMass; // Masse musculaire cible
    private double waistCircumference; // Tour de taille cible
    private int trainingFrequency; // Fréquence d'entraînement cible (nombre de jours par semaine)
    private double strengthGoal; // Objectif de force
    private double performanceGoal; // Objectif de performance (par exemple, temps pour un 5 km)
    private LocalDate targetDate; // Date cible pour atteindre l'objectif
}

