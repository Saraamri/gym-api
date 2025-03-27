package com.example.demo.entities;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Abonnement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;  // (Mensuel, Annuel, etc.)
    private double prix;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    @Enumerated(EnumType.STRING)
    private statutAbonnement statut; // ACTIF, EXPIRE, SUSPENDU
    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id" ,nullable = false)
    private UserEntity user;

   @OneToMany(mappedBy = "abonnement", cascade = CascadeType.ALL)
   private List<Paiement> paiements;





}
