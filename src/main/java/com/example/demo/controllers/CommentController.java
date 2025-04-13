package com.example.demo.controllers;

import com.example.demo.entities.Comment;
import com.example.demo.entities.CoursCollectif;
import com.example.demo.entities.UserEntity;
import com.example.demo.services.CommentInterface;
import com.example.demo.services.CoursCollectifInterface;
import com.example.demo.services.UserInterface;
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
    private CoursCollectifInterface CoursCollectifInterface;

    // Ajouter un commentaire sur un cours
    @PostMapping("/cours/{coursId}/{userId}")
    public ResponseEntity<?> addCommentToCours(@PathVariable Long coursId, @PathVariable Long userId, @RequestBody Comment comment) {
        UserEntity user =  userInterface.getUserById(userId);
        CoursCollectif cours = CoursCollectifInterface.getCoursById(coursId);
        if (user == null || cours == null) {
            return ResponseEntity.badRequest().body("Utilisateur ou cours introuvable");
        }

        comment.setUser(user);
        comment.setCoursCollectif(cours);
        comment.setCreated(new Timestamp(System.currentTimeMillis()));
        Comment saved = commentInterface.saveComment(comment);
        return ResponseEntity.ok(saved);
    }

    // Ajouter un commentaire sur un coach
    @PostMapping("/coach/{coachId}/{userId}")
    public ResponseEntity<?> addCommentToCoach(@PathVariable Long coachId, @PathVariable Long userId, @RequestBody Comment comment) {
        UserEntity user = userInterface.getUserById(userId);
        UserEntity coach = userInterface.getUserById(coachId);
        if (user == null || coach == null) {
            return ResponseEntity.badRequest().body("Utilisateur ou coach introuvable");
        }

        comment.setUser(user);
        comment.setCoach(coach);
        comment.setCreated(new Timestamp(System.currentTimeMillis()));
        Comment saved = commentInterface.saveComment(comment);
        return ResponseEntity.ok(saved);
    }


    // Récupérer les commentaires d’un cours
    @GetMapping("/cours/{coursId}")
    public ResponseEntity<?> getCommentsByCours(@PathVariable Long coursId) {
        List<Comment> comments = commentInterface.getActiveCommentsByCoursId(coursId);
        return ResponseEntity.ok(comments);
    }

    // Récupérer les commentaires d’un coach
    @GetMapping("/coach/{coachId}")
    public ResponseEntity<?> getCommentsByCoach(@PathVariable Long coachId) {
        List<Comment> comments = commentInterface.getActiveCommentsByCoachId(coachId);
        return ResponseEntity.ok(comments);
    }

    // Supprimer un commentaire par l'adhérent s'il est auteur
    @DeleteMapping("/delete/{commentId}/user/{userId}")
    public ResponseEntity<?> deleteCommentByUser(@PathVariable Long commentId, @PathVariable Long userId) {
        Comment comment = commentInterface.getCommentById(commentId);
        if (comment == null) {
            return ResponseEntity.status(404).body("Commentaire non trouvé");
        }

        if (!comment.getUser().getId().equals(userId)) {
            return ResponseEntity.status(403).body("Vous n'êtes pas autorisé à supprimer ce commentaire");
        }

        commentInterface.deleteComment(commentId);
        return ResponseEntity.ok("Commentaire supprimé avec succès");
    }

    // Désactiver un commentaire (admin uniquement)
    @PutMapping("/disable/{commentId}")
    public ResponseEntity<?> disableComment(@PathVariable Long commentId) {
        Comment comment = commentInterface.getCommentById(commentId);
        if (comment == null) {
            return ResponseEntity.status(404).body("Commentaire non trouvé");
        }

        comment.setActive(false);
        commentInterface.saveComment(comment);
        return ResponseEntity.ok("Commentaire désactivé avec succès");
    }
}

