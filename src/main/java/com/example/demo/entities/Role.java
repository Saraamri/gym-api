package com.example.demo.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    @Enumerated(EnumType.STRING)
    private RoleName rolename;
    @JsonIgnore
    @ManyToMany(mappedBy = "role")
    private Set<UserEntity> user =new HashSet<>();



}
