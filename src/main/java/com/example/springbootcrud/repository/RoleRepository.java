package com.example.springbootcrud.repository;

import com.example.springbootcrud.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Set<Role> getRoleByNameIn(Set<String> roles);

    Role getRoleByName(String defaultRoleName);
}
