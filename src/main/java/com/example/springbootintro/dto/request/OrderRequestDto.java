package com.example.springbootintro.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OrderRequestDto {
    @NotBlank(message = "ShippingAddress field can`t be blank")
    private String shippingAddress;
}
