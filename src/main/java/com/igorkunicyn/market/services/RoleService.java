package com.igorkunicyn.market.services;

import com.igorkunicyn.market.entities.Role;
import com.igorkunicyn.market.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private RoleRepository roleRepo;

    @Autowired
    public void setRoleRepo(RoleRepository roleRepo) {
        this.roleRepo = roleRepo;
    }

    public Role findRoleBiName(String name){
        return roleRepo.findByName(name);
    }
}
