package com.example.springbootintro.security;

import com.example.springbootintro.dto.request.UserLoginRequestDto;
import com.example.springbootintro.dto.request.UserRegistrationRequestDto;
import com.example.springbootintro.dto.response.UserLoginResponseDto;
import com.example.springbootintro.dto.response.UserResponseDto;
import com.example.springbootintro.exception.RegistrationException;
import com.example.springbootintro.model.User;
import com.example.springbootintro.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public UserResponseDto register(UserRegistrationRequestDto userRegistrationRequestDto)
            throws RegistrationException {
        userRegistrationRequestDto.setPassword(passwordEncoder
                .encode(userRegistrationRequestDto.getPassword()));
        return userService.register(userRegistrationRequestDto);
    }

    public UserLoginResponseDto authenticate(UserLoginRequestDto userLoginRequestDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userLoginRequestDto.getLogin(),
                userLoginRequestDto.getPassword()));
        User user = userService.findByEmail(userLoginRequestDto.getLogin());
        String jwtToken = jwtUtil.generateToken(user);
        return new UserLoginResponseDto(jwtToken);
    }
}
