package com.example.springbootintro.dto.request;

import com.example.springbootintro.lib.FieldsValueMatch;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@FieldsValueMatch(
        field = "password",
        fieldMatch = "repeatPassword",
        message = "passwords don`t match"
)
public class UserRegistrationRequestDto {
    @NotBlank(message = "User Email field can`t be blank")
    private String email;
    @NotBlank
    @Size(min = 8, message = "User Password must be at least 8 characters")
    private String password;
    private String repeatPassword;
    @NotBlank(message = "User First name field can`t be blank")
    private String firstName;
    @NotBlank(message = "User Last name field can`t be blank")
    private String lastName;
    @NotBlank(message = "User Shipping address field can`t be blank")
    private String shippingAddress;
}
