package com.example.demo.serviceImplement;

import com.example.demo.entities.Role;
import com.example.demo.entities.RoleName;
import com.example.demo.entities.UserEntity;
import com.example.demo.repository.RoleRepo;
import com.example.demo.repository.UserRepo;
import com.example.demo.services.UserInterface;
import jakarta.annotation.Resource;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Getter
@Setter
@Service
public class UserImplement implements UserInterface {
    @Autowired
    UserRepo userRepository;
    @Autowired
    RoleRepo roleRepository;
    @Autowired
    PasswordEncoder encoder;


    @Override
    public UserEntity addUser(UserEntity user) {



        // Encodage du mot de passe de l'utilisateur
        user.setPassword(encoder.encode(user.getPassword()));

        // Sauvegarde de l'utilisateur dans la base de données
        return userRepository.save(user);
    }



    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserEntity> addListUsers(List<UserEntity> listusers) {
        return userRepository.saveAll(listusers);
    }
    @Override
    public String addUserWTCP(UserEntity user)
    {
        String ch ="";
        if(user.getPassword().equals(user.getConfPassword()))
        {
            userRepository.save(user);
            ch="user added successfuly";

        }
        else
        {
            ch="check conf password!" ;
        }
        return ch ;
    }

    @Override
    public String addUserWTUN(UserEntity user) {
        String ch="";
        if(userRepository.existsByUsername(user.getUsername()))
        {
            ch=" user already exists";
        }else {
            userRepository.save(user);
            ch="user added !!" ;
        }
        return ch;
    }

    @Override
    public UserEntity updateUser(Long id, UserEntity user) {

        UserEntity usr= userRepository.findById(id).get();
        usr.setFirstName(user.getFirstName());
        usr.setLastName((user.getLastName()));
        return userRepository.save(usr);
    }

    @Override
    public List<UserEntity> getAllusers() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity getUser(Long id) {
        return userRepository.findById(id).orElse(null);

    }

    @Override
    public UserEntity getuserByUsername(String un) {
        return userRepository.findByUsername(un);
    }

    @Override
    public List<UserEntity> getUsersSW(String un) {
        return userRepository.getUserSW(un);
    }

    @Override
    public List<UserEntity> getUsersByEmail(String un) {
        return userRepository.getUsersByEmail(un);
    }

    @Override
    public UserEntity getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

}
