package com.example.springbootintro.service;

import com.example.springbootintro.dto.request.BookRequestDto;
import com.example.springbootintro.dto.response.BookResponseDto;
import com.example.springbootintro.dto.response.BookWithoutCategoryResponseDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookResponseDto save(BookRequestDto book);

    List<BookResponseDto> findAll(Pageable pageable);

    BookResponseDto getById(Long id);

    void deleteById(Long id);

    BookResponseDto update(Long id, BookRequestDto bookRequestDto);

    List<BookWithoutCategoryResponseDto> findAllByCategoryId(Pageable pageable, Long id);
}
