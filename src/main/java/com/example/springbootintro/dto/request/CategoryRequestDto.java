package com.example.springbootintro.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryRequestDto {
    @NotBlank(message = "Category Name field can`t be blank")
    private String name;
    private String description;
}
