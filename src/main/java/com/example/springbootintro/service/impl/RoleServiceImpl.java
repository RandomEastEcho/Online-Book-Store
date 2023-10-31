package com.example.springbootintro.service.impl;

import com.example.springbootintro.model.Role;
import com.example.springbootintro.repository.RoleRepository;
import com.example.springbootintro.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role findByName(Role.RoleName roleName) {
        return roleRepository.findByRoleName(roleName).orElseThrow(() ->
                new RuntimeException("Can`t find name of role by name: " + roleName));
    }
}
