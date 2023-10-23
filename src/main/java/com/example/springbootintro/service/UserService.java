package com.example.springbootintro.service;

import com.example.springbootintro.dto.request.UserRegistrationRequestDto;
import com.example.springbootintro.dto.response.UserResponseDto;
import com.example.springbootintro.exception.RegistrationException;
import com.example.springbootintro.model.User;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto userRegistrationRequestDto)
            throws RegistrationException;

    User findByEmail(String email);
}
