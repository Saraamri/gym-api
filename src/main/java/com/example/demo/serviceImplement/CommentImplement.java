package com.example.demo.serviceImplement;

import com.example.demo.entities.Comment;
import com.example.demo.repository.CommentRepo;
import com.example.demo.services.CommentInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentImplement implements CommentInterface {

    @Autowired
    private CommentRepo commentRepo;

    @Override
    public Comment saveComment(Comment comment) {
        return commentRepo.save(comment);
    }

    @Override
    public Comment getCommentById(Long id) {
        return commentRepo.findById(id).orElse(null);
    }

    @Override
    public void deleteComment(Long id) {
        commentRepo.deleteById(id);
    }

    @Override
    public List<Comment> getActiveCommentsByCoursId(Long coursId) {
        return commentRepo.findByCoursCollectifIdAndActiveTrue(coursId);
    }

    @Override
    public List<Comment> getActiveCommentsByCoachId(Long coachId) {
        return commentRepo.findByCoachIdAndActiveTrue(coachId);
    }
}

