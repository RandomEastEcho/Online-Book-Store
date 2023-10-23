package com.example.springbootintro.repository;

import com.example.springbootintro.model.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(Role.RoleName roleName);
}
