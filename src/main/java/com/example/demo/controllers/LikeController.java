package com.example.demo.controllers;

import com.example.demo.services.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/like")
@CrossOrigin(origins="*")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/{coursId}/{userId}")
    public ResponseEntity<?> likeCours(@PathVariable Long coursId, @PathVariable Long userId) {
        likeService.likeCours(coursId, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{coursId}/{userId}")
    public ResponseEntity<?> unlikeCours(@PathVariable Long coursId, @PathVariable Long userId) {
        likeService.unlikeCours(coursId, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/exists/{coursId}/{userId}")
    public ResponseEntity<Boolean> hasLiked(@PathVariable Long coursId, @PathVariable Long userId) {
        return ResponseEntity.ok(likeService.hasUserLiked(coursId, userId));
    }

    @GetMapping("/count/{coursId}")
    public ResponseEntity<Long> countLikes(@PathVariable Long coursId) {
        return ResponseEntity.ok(likeService.countLikes(coursId));
    }
}
