package com.example.springbootintro.service.impl;

import com.example.springbootintro.dto.request.UserRegistrationRequestDto;
import com.example.springbootintro.dto.response.UserResponseDto;
import com.example.springbootintro.exception.EntityNotFoundException;
import com.example.springbootintro.exception.RegistrationException;
import com.example.springbootintro.mapper.UserMapper;
import com.example.springbootintro.model.Role;
import com.example.springbootintro.model.User;
import com.example.springbootintro.repository.UserRepository;
import com.example.springbootintro.service.RoleService;
import com.example.springbootintro.service.UserService;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleService roleService;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto userRegistrationRequestDto)
            throws RegistrationException {
        if (userRepository.findByEmail(userRegistrationRequestDto.getEmail()).isPresent()) {
            throw new RegistrationException("User with email "
                    + userRegistrationRequestDto.getEmail()
                    + "is already exist!");
        }
        User user = userMapper.toModel(userRegistrationRequestDto);
        user.setRoles(Set.of(roleService.findByName(Role.RoleName.USER)));
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() ->
               new EntityNotFoundException("Can`t find user with email: " + email));
    }
}
