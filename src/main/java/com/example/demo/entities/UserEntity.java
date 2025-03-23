package com.example.demo.entities;
import jakarta.persistence.*;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Data
@Table(name="users")

public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="firstName" , length = 10,nullable = true)
   // @Size(max=10, message =" le prénom ne doit pas depasser 10 caractéres")
    private String firstName;
    private String lastName;
    @Column(length=100,nullable = false,unique = true)
    private String email;
    private  String username;
    private String password ;
    private String confPassword;
    private String specialite;
    @ManyToMany
    @JoinTable(name = "userrole",joinColumns = @JoinColumn(name = "id"),inverseJoinColumns = @JoinColumn(name ="idrole"))
    private Set<Role> role = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments;
}
