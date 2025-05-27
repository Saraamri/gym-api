package com.example.demo.Dto;

import com.example.demo.entities.UserEntity;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String profilePicture;

    public UserDTO(UserEntity user) {
        if (user != null) {
            this.id = user.getId();
            this.firstName = user.getFirstName();
            this.lastName = user.getLastName();
            this.profilePicture = user.getProfilePicture();
        }
    }
}

