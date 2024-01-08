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
import com.example.springbootintro.dto.response.BookWithoutCategoryResponseDto;
import com.example.springbootintro.exception.EntityNotFoundException;
import com.example.springbootintro.mapper.BookMapper;
import com.example.springbootintro.model.Book;
import com.example.springbootintro.repository.BookRepository;
import com.example.springbootintro.service.impl.BookServiceImpl;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
    private final BookRequestDto testRequestDto = new BookRequestDto();
    private final BookResponseDto expectedResponse = new BookResponseDto();

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void save_saveBook_ok() {
        Book testBook = createBook();
        when(bookMapper.toModel(testRequestDto)).thenReturn(testBook);
        when(bookRepository.save(testBook)).thenReturn(testBook);
        when(bookMapper.toDto(testBook)).thenReturn(expectedResponse);

        BookResponseDto actualResponse = bookService.save(testRequestDto);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void findAll_findAllBooks_ok() {
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
    void getById_getBookById_ok() {
        Book testBook = createBook();
        when(bookRepository.findById(testBook.getId())).thenReturn(Optional.of(testBook));
        when(bookMapper.toDto(testBook)).thenReturn(expectedResponse);

        BookResponseDto actualResponse = bookService.getById(testBook.getId());

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void getById_getBookByNonExistingId_notOk() {
        Long nonExistingId = 2L;

        when(bookRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> bookService.getById(nonExistingId));
    }

    @Test
    void deleteById_deleteBookById_ok() {
        Book testBook = createBook();
        bookService.deleteById(testBook.getId());

        verify(bookRepository, times(1)).deleteById(testBook.getId());
    }

    @Test
    void update_updateBook_ok() {
        Book testBook = createBook();
        Book updatedBook = new Book();

        when(bookRepository.findById(testBook.getId())).thenReturn(Optional.of(testBook));
        when(bookRepository.save(any())).thenReturn(updatedBook);
        when(bookMapper.toDto(updatedBook)).thenReturn(expectedResponse);

        BookResponseDto actualResponse = bookService.update(testBook.getId(), testRequestDto);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void update_updateBookByNotExistingId_notOk() {
        Long nonExistingId = 2L;

        when(bookRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> bookService.getById(nonExistingId));
        verify(bookRepository, times(1)).findById(nonExistingId);
    }

    @Test
    void findAllByCategoryId_findAllBooksByCategoryId_ok() {
        Pageable pageable = mock(Pageable.class);
        List<Book> books = Arrays.asList(new Book(), new Book());
        Long existingCategoryId = 1L;
        List<BookWithoutCategoryResponseDto> expectedResponse =
                Arrays.asList(new BookWithoutCategoryResponseDto(),
                new BookWithoutCategoryResponseDto());

        when(bookRepository.findAllByCategoriesId(existingCategoryId, pageable)).thenReturn(books);
        when(bookMapper.toDtoWithoutCategories(any()))
                .thenReturn(new BookWithoutCategoryResponseDto());

        List<BookWithoutCategoryResponseDto> actualResponse =
                bookService.findAllByCategoryId(pageable, existingCategoryId);

        assertEquals(expectedResponse.size(), actualResponse.size());
    }

    private Book createBook() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("testTitle");
        book.setAuthor("testAuthor");
        book.setIsbn("testISBN");
        book.setPrice(BigDecimal.ONE);
        book.setDescription("testDescription");
        book.setCoverImage("testCoverImage");
        return book;
    }
}
