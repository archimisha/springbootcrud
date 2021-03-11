package com.example.springbootcrud.service;

import com.example.springbootcrud.model.Role;
import com.example.springbootcrud.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Set<Role> getRoles(Set<String> roles) {
        return roleRepository.getRoleByNameIn(roles);
    }

    public Role getDefaultRole() {
        String defaultRoleName = "ROLE_USER";
        return roleRepository.getRoleByName(defaultRoleName);
    }

    public Role getAdminRole() {
        String adminRoleName = "ROLE_ADMIN";
        return roleRepository.getRoleByName(adminRoleName);
    }
}
