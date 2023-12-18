package com.example.springbootintro.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OrderRequestDto {
    @NotBlank(message = "Order ShippingAddress field can`t be blank")
    private String shippingAddress;
}
