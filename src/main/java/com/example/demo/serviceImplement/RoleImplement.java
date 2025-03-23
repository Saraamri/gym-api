package com.example.demo.serviceImplement;

import com.example.demo.services.RoleInterface;
import com.example.demo.entities.Role;
import com.example.demo.repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class RoleImplement implements RoleInterface {

    @Autowired
    private RoleRepo roleRepo;
    @Override
    public Role add(Role role) {
        return  roleRepo.save(role);
    }
}
