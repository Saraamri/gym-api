package com.example.demo.entities;

import com.example.demo.validation.ValidAbonnementDates;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ValidAbonnementDates
public class Abonnement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le type d'abonnement est requis")
    private String type;  // (Mensuel, Annuel, etc.)

    @Positive(message = "Le prix doit être supérieur à 0")
    private double prix;

    @NotNull(message = "La date de début est requise")
    @FutureOrPresent(message = "La date de début doit être aujourd'hui ou dans le futur")

    private LocalDate dateDebut;

    @NotNull(message = "La date de fin est requise")
    @Future(message = "La date de fin doit être dans le futur")
    private LocalDate dateFin;

    @NotNull(message = "Le statut est requis")
    @Enumerated(EnumType.STRING)
    private statutAbonnement statut; // ACTIF, EXPIRE, SUSPENDU



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id" ,nullable = true)
    private UserEntity user;


    @OneToMany(mappedBy = "abonnement", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Paiement> paiements;
}
