package com.example.springbootintro.dto.response;

import lombok.Data;

@Data
public class OrderItemResponseDto {
    private Long id;
    private Long bookId;
    private int quantity;
}
