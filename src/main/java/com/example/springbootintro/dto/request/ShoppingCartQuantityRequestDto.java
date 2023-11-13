package com.example.springbootintro.dto.request;

import lombok.Data;

@Data
public class ShoppingCartQuantityRequestDto {
    private Long id;
    private int quantity;
}
