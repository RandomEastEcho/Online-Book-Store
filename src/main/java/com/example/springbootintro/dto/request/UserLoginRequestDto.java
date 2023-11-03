package com.example.springbootintro.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginRequestDto {
    @NotBlank(message = "User Login field can`t be empty")
    private String login;
    @NotBlank(message = "User Password field can`t be empty")
    private String password;
}
