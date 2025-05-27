package com.example.demo.controllers;

import com.example.demo.Dto.CommentDTO;
import com.example.demo.entities.Comment;
import com.example.demo.entities.CoursCollectif;
import com.example.demo.entities.UserEntity;
import com.example.demo.repository.CommentRepo;
import com.example.demo.services.CommentInterface;
import com.example.demo.services.CoursCollectifInterface;
import com.example.demo.services.UserInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private CommentRepo commentRepo;

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
            if (isEmpty(user.getFirstName()) || isEmpty(user.getLastName())) {
                return ResponseEntity.badRequest().body("Le prénom et le nom de l'utilisateur sont obligatoires");
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


    @GetMapping("/cours/{coursId}")
    public List<CommentDTO> getCommentsByCours(@PathVariable Long coursId) {
        List<Comment> comments = commentRepo.findByCoursCollectifIdAndActiveTrue(coursId);
        return comments.stream()
                .map(CommentDTO::new)
                .collect(Collectors.toList());
    }


    @GetMapping("/all")
    public ResponseEntity<List<Comment>> getAllComments() {
        return ResponseEntity.ok(commentInterface.getAllComments());
    }


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

    // 🔧 Méthode utilitaire pour vérifier si une chaîne est vide ou nulle
    private boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}
