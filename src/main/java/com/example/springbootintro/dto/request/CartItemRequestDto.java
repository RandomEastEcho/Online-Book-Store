package com.example.springbootintro.dto.request;

import lombok.Data;

@Data
public class CartItemRequestDto {
    private Long shoppingCartId;
    private Long bookId;
    private int quantity;
}
