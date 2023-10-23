package com.example.springbootintro.service;

import com.example.springbootintro.model.Role;

public interface RoleService {
    Role save(Role role);

    Role findByName(Role.RoleName roleName);
}
