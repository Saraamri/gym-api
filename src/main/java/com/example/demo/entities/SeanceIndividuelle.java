package com.example.demo.entities;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
public class SeanceIndividuelle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime date;
    private String coach;

    @ManyToOne
    @JoinColumn(name = "cours_collectif_id")
    private CoursCollectif coursCollectif;
}
