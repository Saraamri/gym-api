package com.example.demo.entities;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate dateReservation;
    private String statut;
    @ManyToOne
    @JoinColumn(name = "adherent_id")
    private UserEntity adherent;

    @ManyToOne
    @JoinColumn(name = "cours_id")
    private CoursCollectif cours;
}
