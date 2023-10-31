package com.example.springbootintro.controller;

import com.example.springbootintro.dto.request.CategoryRequestDto;
import com.example.springbootintro.dto.response.BookWithoutCategoryResponseDto;
import com.example.springbootintro.dto.response.CategoryResponseDto;
import com.example.springbootintro.service.BookService;
import com.example.springbootintro.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Category management", description = "Endpoints for managing books")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final BookService bookService;

    @GetMapping
    @Secured({"ADMIN","USER"})
    @Operation(summary = "Get list of all categories", description = "Get list of all categories")
    public List<CategoryResponseDto> getAll(Pageable pageable) {
        return categoryService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Secured({"ADMIN","USER"})
    @Operation(summary = "Get category by id", description = "Get category by it`s id")
    public CategoryResponseDto getCategoryById(@Parameter (
            description = "Id of searched category",
            name = "Id",
            required = true,
            example = "5"
            )@PathVariable Long id) {
        return categoryService.getById(id);
    }

    @PostMapping
    @Secured("ADMIN")
    @Operation(summary = "Create a category", description = "Create a new Category")
    public CategoryResponseDto createCategory(@Parameter(
            description = "Create a category",
            required = true,
            content = @Content(schema = @Schema(implementation = CategoryRequestDto.class))
            )@RequestBody @Valid CategoryRequestDto categoryRequestDto) {
        return categoryService.save(categoryRequestDto);
    }

    @DeleteMapping("/{id}")
    @Secured("ADMIN")
    @Operation(summary = "Delete category", description = "Delete category by it`s id")
    public void deleteById(@Parameter(
            description = "Delete category by Id",
            name = "Id",
            required = true,
            example = "3"
            )@PathVariable Long id) {
        categoryService.deleteById(id);
    }

    @PutMapping("/{id}")
    @Secured("ADMIN")
    @Operation(summary = "Update category", description = "Update existing category")
    public CategoryResponseDto update(@Parameter(
            description = "Update category by id",
            name = "Id",
            required = true,
            example = "1"
            )@PathVariable Long id, @Parameter(
            description = "Update category",
            required = true,
            content =
            @Content(schema = @Schema(implementation = CategoryRequestDto.class))
            )@RequestBody @Valid CategoryRequestDto categoryRequestDto) {
        return categoryService.update(id, categoryRequestDto);
    }

    @GetMapping("/{id}/books")
    @Secured({"ADMIN","USER"})
    @Operation(summary = "Get all books by category",
            description = "Get all books by category`s id")
    public List<BookWithoutCategoryResponseDto> getBooksByCategoryId(@Parameter(
            description = "Id of category",
            name = "Id",
            required = true,
            example = "1"
    )@PathVariable Long id, Pageable pageable) {
        return bookService.findAllByCategoryId(pageable, id);
    }
}
