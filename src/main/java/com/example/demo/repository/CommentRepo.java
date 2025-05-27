package com.example.demo.repository;

import com.example.demo.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, Long> {
    List<Comment> findByCoursCollectifIdAndActiveTrue(Long coursId);
    List<Comment> findByCoachIdAndActiveTrue(Long coachId);
    long countByActiveTrue();
}

