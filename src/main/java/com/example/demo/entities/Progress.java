package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private double poids;
    private double taille;
    private double imc;  // calculé = poids / (taille * taille)

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}

