package com.example.springbootcrud.service;

import com.example.springbootcrud.model.Role;
import com.example.springbootcrud.repository.RoleRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public void add(Role role) {
        roleRepository.save(role);
    }

    public void edit(Role role) {
        roleRepository.save(role);
    }

    public Role getById(Long id) {
        Role role = null;
        Optional<Role> opt = roleRepository.findById(id);
        if (opt.isPresent()) {
            role = opt.get();
        }
        return role;
    }

    public Role getByName(String name) throws NotFoundException {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            throw new NotFoundException(name);
        }
        return role;
    }
}
