package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.List;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoursCollectif {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom du cours est requis")
    private String nom;

    @NotBlank(message = "La description est requise")
    private String description;

    @Positive(message = "La durée totale doit être un nombre positif")
    private int dureeTotale; // en minutes ?

    @NotBlank(message = "Le niveau est requis (Débutant, Intermédiaire, Avancé)")
    private String niveau;
    @ElementCollection
    @NotEmpty(message = "Les jours de cours sont requis")
    private List <String> jours;

    @NotBlank(message = "L'horaire est requis")
    private String horaire; // e.g. "18:00 - 19:00"

    @NotBlank(message = "L'URL de l'image est requise")
    private String image;



  @NotNull(message = "Le coach responsable est requis")
    @ManyToOne
    @JoinColumn(name = "coach_id")
  @JsonBackReference
    private UserEntity coach;
}
