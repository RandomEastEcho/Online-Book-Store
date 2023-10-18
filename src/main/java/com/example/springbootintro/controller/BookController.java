package com.example.springbootintro.controller;

import com.example.springbootintro.dto.request.CreateBookRequestDto;
import com.example.springbootintro.dto.response.BookDto;
import com.example.springbootintro.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Book management", description = "Endpoints for managing books")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/books/")
public class BookController {
    private final BookService bookService;

    @GetMapping
    @Operation(summary = "Get list of all books", description = "Get list of all books")
    public List<BookDto> getAll(Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get book by id", description = "Get book by it`s id")
    public BookDto getBookById(@Parameter(
            description = "id of searched book",
            name = "id",
            required = true,
            example = "5"
            ) @PathVariable Long id) {
        return bookService.getById(id);
    }

    @PostMapping
    @Operation(summary = "Create a book", description = "Create a new book")
    public BookDto createBook(@Parameter(
            description = "create a book",
            required = true,
            content = @Content(schema = @Schema(implementation = CreateBookRequestDto.class))
            ) @RequestBody @Valid CreateBookRequestDto bookDto) {
        return bookService.save(bookDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete book", description = "Delete book by it`s id")
    public void deleteById(@Parameter(
            description = "delete book by id",
            name = "id",
            required = true,
            example = "3"
            ) @PathVariable Long id) {
        bookService.deleteById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update book", description = "Update existing book")
    public BookDto update(@Parameter(
            description = "update book by id",
            name = "id",
            required = true,
            example = "1"
            ) @PathVariable Long id, @Parameter(
                    description = "update book",
                    required = true,
                    content =
                    @Content(schema = @Schema(implementation = CreateBookRequestDto.class))
            ) @RequestBody @Valid CreateBookRequestDto bookRequestDto) {
        return bookService.update(id, bookRequestDto);
    }
}
