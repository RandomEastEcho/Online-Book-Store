package com.example.springbootintro.service;

import com.example.springbootintro.dto.request.CreateBookRequestDto;
import com.example.springbootintro.dto.response.BookDto;
import java.util.List;

public interface BookService {
    BookDto save(CreateBookRequestDto book);

    List<BookDto> findAll();

    BookDto getById(Long id);
}
