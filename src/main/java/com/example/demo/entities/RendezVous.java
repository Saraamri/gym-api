package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Entity
public class RendezVous {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String titre;
    @NotNull
    private LocalDateTime dateHeure;

    @ManyToOne
    @JoinColumn(name = "user_id")
    //@NotNull(message = "L'utilisateur est requis")
    @JsonBackReference
    private UserEntity user;
}
