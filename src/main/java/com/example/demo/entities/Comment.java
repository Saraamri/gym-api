package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    private Timestamp created;
    private String body;

    private boolean active = true; // commentaire actif ou désactivé

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user; // adhérent auteur du commentaire

    @ManyToOne
    @JoinColumn(name = "cours_id", nullable = true)
    private CoursCollectif coursCollectif; // cours commenté

    @ManyToOne
    @JoinColumn(name = "coach_id", nullable = true)
    private UserEntity coach; // coach commenté
}

