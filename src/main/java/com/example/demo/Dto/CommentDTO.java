package com.example.demo.Dto;

import com.example.demo.entities.Comment;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class CommentDTO {
    private Long commentId;
    private String body;
    private Timestamp created;
    private boolean active;
    private UserDTO user;

    public CommentDTO(Comment comment) {
        this.commentId = comment.getCommentId();
        this.body = comment.getBody();
        this.created = comment.getCreated();
        this.active = comment.isActive();
        this.user = new UserDTO(comment.getUser());
    }
}

