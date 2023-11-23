package com.example.springbootintro.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartItemRequestDto {
    @NotNull
    private Long shoppingCartId;
    @NotNull
    private Long bookId;
    @NotNull
    @Min(0)
    private int quantity;
}
