package com.example.demo.controllers;

import com.example.demo.entities.Role;
import com.example.demo.entities.UserEntity;
import com.example.demo.services.RoleInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleInterface roleInterface;
    @PostMapping("/add")
    public Role addRole(@RequestBody Role role)
    {

        return roleInterface.add(role);
    }
}
