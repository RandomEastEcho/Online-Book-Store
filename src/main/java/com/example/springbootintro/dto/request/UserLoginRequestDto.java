package com.example.springbootintro.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginRequestDto {
    @NotBlank(message = "Login field can`t be empty")
    private String login;
    @NotBlank(message = "Password field can`t be empty")
    private String password;
}
