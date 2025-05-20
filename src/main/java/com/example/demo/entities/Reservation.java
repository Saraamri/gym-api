package com.example.demo.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La date de réservation est obligatoire")
    @FutureOrPresent(message = "La date de réservation ne peut pas être dans le passé")
    private LocalDate dateReservation;

    @NotBlank(message = "Le statut de la réservation est obligatoire")
    private String statut;

    @NotNull(message = "L'adhérent est obligatoire")
    @ManyToOne
    @JoinColumn(name = "adherent_id")
    private UserEntity adherent;

    @NotNull(message = "Le cours collectif est obligatoire")
    @ManyToOne
    @JoinColumn(name = "cours_id")
    private CoursCollectif cours;
}
