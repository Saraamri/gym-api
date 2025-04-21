package com.example.demo.controllers;

import com.example.demo.entities.Comment;
import com.example.demo.entities.CoursCollectif;
import com.example.demo.entities.UserEntity;
import com.example.demo.services.CommentInterface;
import com.example.demo.services.CoursCollectifInterface;
import com.example.demo.services.UserInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/comments")
@CrossOrigin(origins = "*")
public class CommentController {

    @Autowired
    private CommentInterface commentInterface;

    @Autowired
    private UserInterface userInterface;

    @Autowired
    private CoursCollectifInterface coursCollectifInterface;

    // ➕ Ajouter un commentaire sur un cours
    @PostMapping("/cours/{coursId}/{userId}")
    public ResponseEntity<?> addCommentToCours(
            @PathVariable Long coursId,
            @PathVariable Long userId,
            @RequestBody @Valid Comment comment) {
        try {
            UserEntity user = userInterface.getUserById(userId);
            if (user == null) {
                return ResponseEntity.badRequest().body("Utilisateur introuvable");
            }

            CoursCollectif cours = coursCollectifInterface.getCoursById(coursId);
            if (cours == null) {
                return ResponseEntity.badRequest().body("Cours introuvable");
            }

            comment.setUser(user);
            comment.setCoursCollectif(cours);
            comment.setCreated(new Timestamp(System.currentTimeMillis()));
            comment.setActive(true);

            return ResponseEntity.ok(commentInterface.saveComment(comment));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Erreur interne : " + e.getMessage());
        }
    }

    // ➕ Ajouter un commentaire sur un coach
    @PostMapping("/coach/{coachId}/{userId}")
    public ResponseEntity<?> addCommentToCoach(
            @PathVariable Long coachId,
            @PathVariable Long userId,
            @RequestBody @Valid Comment comment) {
        try {
            UserEntity user = userInterface.getUserById(userId);
            if (user == null) {
                return ResponseEntity.badRequest().body("Utilisateur introuvable");
            }

            UserEntity coach = userInterface.getUserById(coachId);
            if (coach == null) {
                return ResponseEntity.badRequest().body("Coach introuvable");
            }

            comment.setUser(user);
            comment.setCoach(coach);
            comment.setCreated(new Timestamp(System.currentTimeMillis()));
            comment.setActive(true);

            return ResponseEntity.ok(commentInterface.saveComment(comment));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Erreur interne : " + e.getMessage());
        }
    }

    // 📥 Récupérer les commentaires d’un cours
    @GetMapping("/cours/{coursId}")
    public ResponseEntity<List<Comment>> getCommentsByCours(@PathVariable Long coursId) {
        return ResponseEntity.ok(commentInterface.getActiveCommentsByCoursId(coursId));
    }

    // 📥 Récupérer les commentaires d’un coach
    @GetMapping("/coach/{coachId}")
    public ResponseEntity<List<Comment>> getCommentsByCoach(@PathVariable Long coachId) {
        return ResponseEntity.ok(commentInterface.getActiveCommentsByCoachId(coachId));
    }

    // ❌ Supprimer un commentaire (si utilisateur est auteur)
    @DeleteMapping("/delete/{commentId}/user/{userId}")
    public ResponseEntity<String> deleteCommentByUser(
            @PathVariable Long commentId,
            @PathVariable Long userId) {

        Comment comment = commentInterface.getCommentById(commentId);
        if (comment == null) {
            return ResponseEntity.notFound().build();
        }
        if (!comment.getUser().getId().equals(userId)) {
            return ResponseEntity.status(403).body("Vous n'êtes pas autorisé à supprimer ce commentaire");
        }

        commentInterface.deleteComment(commentId);
        return ResponseEntity.ok("Commentaire supprimé avec succès");
    }

    // 📴 Désactiver un commentaire (admin)
    @PutMapping("/disable/{commentId}")
    public ResponseEntity<String> disableComment(@PathVariable Long commentId) {
        Comment comment = commentInterface.getCommentById(commentId);
        if (comment == null) {
            return ResponseEntity.notFound().build();
        }
        comment.setActive(false);
        commentInterface.saveComment(comment);
        return ResponseEntity.ok("Commentaire désactivé avec succès");
    }
}
