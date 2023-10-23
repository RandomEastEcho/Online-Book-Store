package com.example.springbootintro.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegistrationRequestDto {
    @NotBlank(message = "Email field can`t be blank")
    private String email;
    @NotBlank
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
    private String repeatPassword;
    @NotBlank(message = "First name field can`t be blank")
    private String firstName;
    @NotBlank(message = "Last name field can`t be blank")
    private String lastName;
    @NotBlank(message = "Shipping address field can`t be blank")
    private String shippingAddress;
}
