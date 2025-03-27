package com.example.demo.entities;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
public class CoursCollectif {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String description;
    private int dureeTotale;
    private String niveau;
    private String jours;
    private String horaire;
    private String imageUrl;
    @ManyToOne
    @JoinColumn(name = "coach_id")
    private UserEntity coach;
    @OneToMany(mappedBy = "coursCollectif", cascade = CascadeType.ALL)
    private List<SeanceIndividuelle> seances;

}
