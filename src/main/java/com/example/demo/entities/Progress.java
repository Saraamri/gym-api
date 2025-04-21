package com.example.demo.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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

    @NotNull(message = "La date est obligatoire")
    @PastOrPresent(message = "La date ne peut pas être dans le futur")
    private LocalDate date;

    @Positive(message = "Le poids doit être supérieur à 0")
    private double poids;

    @Positive(message = "La taille doit être supérieure à 0")
    private double taille;

    @DecimalMin(value = "0.0", inclusive = false, message = "L'IMC doit être supérieur à 0")
    private double imc;

    @ManyToOne
    @JoinColumn(name = "user_id")
   // @NotNull(message = "L'utilisateur est requis")
    private UserEntity user;
}
