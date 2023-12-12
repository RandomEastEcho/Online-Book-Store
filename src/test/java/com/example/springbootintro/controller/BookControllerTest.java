package com.example.springbootintro.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.springbootintro.dto.request.BookRequestDto;
import com.example.springbootintro.dto.response.BookResponseDto;
import com.example.springbootintro.service.BookService;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;

class BookControllerTest {
    private BookResponseDto expectedBook;
    private BookRequestDto bookRequestDto;

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        expectedBook = new BookResponseDto();
        bookRequestDto = new BookRequestDto();
    }

    @Test
    void getAllBooks_ok() {
        List<BookResponseDto> expectedBooks = Arrays.asList(new BookResponseDto(),
                new BookResponseDto());

        when(bookService.findAll(any(Pageable.class))).thenReturn(expectedBooks);

        List<BookResponseDto> actualBooks = bookController.getAll(mock(Pageable.class));

        assertEquals(expectedBooks, actualBooks);
        verify(bookService, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getBookById_ok() {
        Long bookId = 1L;

        when(bookService.getById(bookId)).thenReturn(expectedBook);

        BookResponseDto actualBook = bookController.getBookById(bookId);

        assertEquals(expectedBook, actualBook);
        verify(bookService, times(1)).getById(bookId);
    }

    @Test
    void createBook_ok() {
        when(bookService.save(bookRequestDto)).thenReturn(expectedBook);

        BookResponseDto actualBook = bookController.createBook(bookRequestDto);

        assertEquals(expectedBook, actualBook);
        verify(bookService, times(1)).save(bookRequestDto);
    }

    @Test
    void deleteBookById_ok() {
        Long bookId = 1L;

        bookController.deleteById(bookId);

        verify(bookService, times(1)).deleteById(bookId);
    }

    @Test
    void update_ShouldReturnUpdatedBook() {
        Long bookId = 1L;

        when(bookService.update(bookId, bookRequestDto)).thenReturn(expectedBook);

        BookResponseDto actualBook = bookController.update(bookId, bookRequestDto);

        assertEquals(expectedBook, actualBook);
        verify(bookService, times(1)).update(bookId, bookRequestDto);
    }
}
