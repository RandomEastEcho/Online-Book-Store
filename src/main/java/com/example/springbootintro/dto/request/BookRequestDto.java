package com.example.springbootintro.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class BookRequestDto {
    @NotBlank(message = "Title field can`t be blank")
    private String title;
    @NotBlank(message = "Author field can`t be blank")
    private String author;
    @NotBlank(message = "Isbn field can`t be blank")
    private String isbn;
    @NotNull
    @Min(0)
    private BigDecimal price;
    private String description;
    private String coverImage;
}
