package com.example.springbootintro.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ShoppingCartQuantityRequestDto {
    private Long id;
    @NotBlank(message = "Quantity field can`t be blank")
    @Min(0)
    private int quantity;
}
