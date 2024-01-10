package com.example.Project.controllers;


import com.example.Project.entities.Role;
import com.example.Project.services.RoleService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class RoleController {


    RoleService roleService;

    @PostMapping("/addRole")
    public Role addRole(@RequestBody Role role) {
        Role  r = roleService.createNewRole(role);
        return r;
    }


}
