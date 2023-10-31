package com.example.springbootintro.controller;

import com.example.springbootintro.dto.request.BookRequestDto;
import com.example.springbootintro.dto.response.BookResponseDto;
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
import org.springframework.security.access.annotation.Secured;
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
    @Secured({"ADMIN","USER"})
    @Operation(summary = "Get list of all books", description = "Get list of all books")
    public List<BookResponseDto> getAll(Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Secured({"ADMIN","USER"})
    @Operation(summary = "Get book by id", description = "Get book by it`s id")
    public BookResponseDto getBookById(@Parameter(
            description = "Id of searched book",
            name = "Id",
            required = true,
            example = "5"
            ) @PathVariable Long id) {
        return bookService.getById(id);
    }

    @PostMapping
    @Secured("ADMIN")
    @Operation(summary = "Create a book", description = "Create a new book")
    public BookResponseDto createBook(@Parameter(
            description = "Create a book",
            required = true,
            content = @Content(schema = @Schema(implementation = BookRequestDto.class))
            ) @RequestBody @Valid BookRequestDto bookDto) {
        return bookService.save(bookDto);
    }

    @DeleteMapping("/{id}")
    @Secured("ADMIN")
    @Operation(summary = "Delete book", description = "Delete book by it`s id")
    public void deleteById(@Parameter(
            description = "Delete book by id",
            name = "Id",
            required = true,
            example = "3"
            ) @PathVariable Long id) {
        bookService.deleteById(id);
    }

    @PutMapping("/{id}")
    @Secured("ADMIN")
    @Operation(summary = "Update book", description = "Update existing book")
    public BookResponseDto update(@Parameter(
            description = "Update book by id",
            name = "Id",
            required = true,
            example = "1"
            ) @PathVariable Long id, @Parameter(
                    description = "Update book",
                    required = true,
                    content =
                    @Content(schema = @Schema(implementation = BookRequestDto.class))
            ) @RequestBody @Valid BookRequestDto bookRequestDto) {
        return bookService.update(id, bookRequestDto);
    }
}
