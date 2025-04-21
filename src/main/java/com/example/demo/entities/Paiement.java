package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DecimalMin(value = "0.0", inclusive = false, message = "Le montant doit être supérieur à zéro")
    private double montant;

    @NotNull(message = "La date de paiement est obligatoire")
    private LocalDate datePaiement;

    @NotNull(message = "Le mode de paiement est obligatoire")
    @Enumerated(EnumType.STRING)
    private ModePaiement modePaiement;  // CARTE, ESPECE, VIREMENT

   // @NotNull(message = "L'abonnement associé est requis")
    @ManyToOne
    @JoinColumn(name = "abonnement_id", referencedColumnName = "id")
    @JsonBackReference
    private Abonnement abonnement;

    public void setDatePaiement(Date date) {
    }

    public void setReference(String id) {
    }
}
