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

    @OneToMany(mappedBy = "coursCollectif", cascade = CascadeType.ALL)
    private List<SeanceIndividuelle> seances;

}
