package com.example.demo.entities;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity

@Data
@Table(name="users")

public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Le prénom ne peut pas être nul.")
    @Size(max = 10, message = "Le prénom ne doit pas dépasser 10 caractères.")
    @Column(name = "firstName", nullable = false)


    private String firstName;

    @NotNull(message = "Le nom ne peut pas être nul.")

    private String lastName;

    @NotNull(message = "L'email ne peut pas être nul.")
    @Email(message = "L'email doit être dans un format valide.")
    @Column(length = 100, nullable = false, unique = true)
    private String email;
    @NotNull(message = "Le nom d'utilisateur ne peut pas être nul.")
    @Size(min = 5, max = 15, message = "Le nom d'utilisateur doit avoir entre 5 et 15 caractères.")
    private  String username;
    @NotNull(message = "Le mot de passe ne peut pas être nul.")
    @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères.")
    private String password ;

    @Transient
    private String confPassword;

    private String specialite;
    @NotNull(message = "Le numéro de téléphone ne peut pas être nul.")
    @Pattern(regexp = "^[0-9]{8}$", message = "Le téléphone doit contenir exactement 8 chiffres.")
    private String telephone;// Pour l'adhérent
    @Min(value = 18, message = "L'âge doit être supérieur ou égal à 18.")
    @Max(value = 120, message = "L'âge doit être inférieur ou égal à 120.")

    private Double age;

    private boolean isActive = true;
    private boolean isAccountNonLocked = true;
    private String adresse;
    private String profilePicture;
    @Transient
    private String roleName; // Non stocké dans la base de données, utilisé juste pour l’inscription

    @OneToMany(mappedBy = "coach")
    private List<SeanceIndividuelle> seancesCoaches;

    @OneToMany(mappedBy = "adherent")
    private List<SeanceIndividuelle> seancesAdherents;
    @ManyToMany
    @JoinTable(name = "userrole",joinColumns = @JoinColumn(name = "id"),inverseJoinColumns = @JoinColumn(name ="idrole"))
    private Set<Role> role = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    // @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
   // private List<Comment> comments;
}
