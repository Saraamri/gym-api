package com.example.demo.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    private Timestamp created;

    @NotBlank(message = "Le contenu du commentaire ne peut pas être vide")
    private String body;

    private boolean active = true; // commentaire actif ou désactivé

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")

    private UserEntity user; // adhérent auteur du commentaire

    @ManyToOne
    @JoinColumn(name = "cours_id", nullable = true)
    private CoursCollectif coursCollectif; // cours commenté (optionnel)

    @ManyToOne
    @JoinColumn(name = "coach_id", nullable = true)
    private UserEntity coach; // coach commenté (optionnel)
    @PrePersist
    protected void onCreate() {
        this.created = new Timestamp(System.currentTimeMillis());
    }
}
