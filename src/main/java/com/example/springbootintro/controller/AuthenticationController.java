package com.example.springbootintro.controller;

import com.example.springbootintro.dto.request.UserLoginRequestDto;
import com.example.springbootintro.dto.request.UserRegistrationRequestDto;
import com.example.springbootintro.dto.response.UserLoginResponseDto;
import com.example.springbootintro.dto.response.UserResponseDto;
import com.example.springbootintro.exception.RegistrationException;
import com.example.springbootintro.security.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public UserLoginResponseDto login(@RequestBody @Valid UserLoginRequestDto userLoginRequestDto) {
        return authenticationService.authenticate(userLoginRequestDto);
    }

    @PostMapping("/registration")
    public UserResponseDto register(@RequestBody @Valid UserRegistrationRequestDto
                                                userRegistrationRequestDto)
            throws RegistrationException {
        return authenticationService.register(userRegistrationRequestDto);
    }
}
