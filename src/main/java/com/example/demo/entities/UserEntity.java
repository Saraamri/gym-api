package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.URL;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Le prénom ne peut pas être nul.")
    @Size(max = 10, message = "Le prénom ne doit pas dépasser 10 caractères.")
    @Column(name = "firstName", nullable = false)
    private String firstName;

    @NotNull(message = "Le nom ne peut pas être nul.")
    @Size(max = 10, message = "Le nom ne doit pas dépasser 15 caractères.")
    private String lastName;

    @NotNull(message = "L'email ne peut pas être nul.")
    @Email(message = "L'email doit être dans un format valide.")
    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @NotNull(message = "Le nom d'utilisateur ne peut pas être nul.")
    @Size(min = 5, max = 15, message = "Le nom d'utilisateur doit avoir entre 5 et 15 caractères.")
    @Column(length = 15, nullable = false, unique = true)
    private String username;


    @NotNull(message = "Le mot de passe ne peut pas être nul.")
    @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}", message = "Le mot de passe doit contenir au moins un chiffre, une lettre majuscule et un caractère spécial.")

    private String password;

    @Transient
    private String confPassword;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String specialite;

    @NotNull(message = "Le numéro de téléphone ne peut pas être nul.")
    @Pattern(regexp = "^[0-9]{8}$", message = "Le téléphone doit contenir exactement 8 chiffres.")
    private String telephone; // Pour l'adhérent

    @Column(name = "profile_picture")
    private String profilePicture;

    private boolean isActive = true;
    private boolean isAccountNonLocked = true;

    @OneToMany(mappedBy = "user")
    private List<Abonnement> abonnements;
    @OneToMany(mappedBy = "coach")
    @JsonManagedReference("coach-seances")
    private List<SeanceIndividuelle> seancesProposees;



    @OneToMany(mappedBy = "adherent")
    @JsonManagedReference("adherent-seances")
    private List<SeanceIndividuelle> seancesAdherents;

    @NotNull(message = "Le rôle ne peut pas être nul.")
    @Enumerated(EnumType.STRING)
    private RoleName role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments;

    @JsonGetter("specialite")
    public String getSpecialite() {
        if (role != null && role == RoleName.COACH) {
            return this.specialite;
        }
        return null;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role == null) return List.of();
        return List.of(() -> "ROLE_" + role.name());
    }





}


