package com.example.springbootintro.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.springbootintro.dto.request.BookRequestDto;
import com.example.springbootintro.dto.response.BookResponseDto;
import com.example.springbootintro.exception.EntityNotFoundException;
import com.example.springbootintro.mapper.BookMapper;
import com.example.springbootintro.model.Book;
import com.example.springbootintro.repository.BookRepository;
import com.example.springbootintro.service.impl.BookServiceImpl;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    private Book testBook;
    private BookRequestDto testRequestDto;
    private BookResponseDto expectedResponse;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    public void init() {
        testBook = new Book();
        testRequestDto = new BookRequestDto();
        expectedResponse = new BookResponseDto();
    }

    @Test
    void saveBook_ok() {

        when(bookMapper.toModel(testRequestDto)).thenReturn(testBook);
        when(bookRepository.save(testBook)).thenReturn(testBook);
        when(bookMapper.toDto(testBook)).thenReturn(expectedResponse);

        BookResponseDto actualResponse = bookService.save(testRequestDto);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void findAllBooks_ok() {
        Pageable pageable = mock(Pageable.class);
        List<Book> books = Arrays.asList(new Book(), new Book());
        Page<Book> bookPage = new PageImpl<>(books);
        List<BookResponseDto> expectedResponse = Arrays.asList(new BookResponseDto(),
                new BookResponseDto());

        when(bookRepository.findAll(pageable)).thenReturn(bookPage);
        when(bookMapper.toDto(any())).thenReturn(new BookResponseDto());

        List<BookResponseDto> actualResponse = bookService.findAll(pageable);

        assertEquals(expectedResponse.size(), actualResponse.size());
    }

    @Test
    void getBookById_ok() {
        Long existingId = 1L;

        when(bookRepository.findById(existingId)).thenReturn(Optional.of(testBook));
        when(bookMapper.toDto(testBook)).thenReturn(expectedResponse);

        BookResponseDto actualResponse = bookService.getById(existingId);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void getBookByNonExistingId_notOk() {
        Long nonExistingId = 2L;

        when(bookRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> bookService.getById(nonExistingId));
    }

    @Test
    void deleteBookById_ok() {
        Long idToDelete = 1L;

        bookService.deleteById(idToDelete);

        verify(bookRepository, times(1)).deleteById(idToDelete);
    }

    @Test
    void updateBook_ok() {
        Long existingId = 1L;
        Book updatedBook = new Book();

        when(bookRepository.findById(existingId)).thenReturn(Optional.of(testBook));
        when(bookRepository.save(any())).thenReturn(updatedBook);
        when(bookMapper.toDto(updatedBook)).thenReturn(expectedResponse);

        BookResponseDto actualResponse = bookService.update(existingId, testRequestDto);

        assertEquals(expectedResponse, actualResponse);
    }
}
