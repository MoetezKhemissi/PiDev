package com.example.Project.services;

import com.example.Project.entities.Role;
import com.example.Project.Interfaces.IRoleService;
import com.example.Project.Repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleService implements IRoleService {


    @Autowired
    private RoleRepository roleRepository ;

    public Role createNewRole(Role role) {
        return roleRepository.save(role);
    }

}
