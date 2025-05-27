package com.example.demo.services;

import com.example.demo.entities.Comment;

import java.util.List;

public interface CommentInterface {
    Comment saveComment(Comment comment);
    Comment getCommentById(Long id);
    void deleteComment(Long id);
    List<Comment> getActiveCommentsByCoursId(Long coursId);
    List<Comment> getActiveCommentsByCoachId(Long coachId);
    List<Comment> getAllComments();

}

