package com.example.springbootintro.dto.response;

import lombok.Data;

@Data
public class CategoryResponseDto {
    private Long id;
    private String name;
    private String description;
}
