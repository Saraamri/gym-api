package com.example.demo.entities;
    import jakarta.persistence.*;
import lombok.Data;


import java.sql.Timestamp;
import java.util.List;

    @Entity
    @Data
    public class Comment {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long commentId;

        private Timestamp created;
        private String body;
        @ManyToOne
        @JoinColumn(name = "user_id", referencedColumnName = "id")
        private UserEntity user;
}
