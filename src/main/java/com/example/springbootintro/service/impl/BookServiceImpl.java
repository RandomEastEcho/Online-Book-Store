package com.example.springbootintro.service.impl;

import com.example.springbootintro.dto.request.BookRequestDto;
import com.example.springbootintro.dto.response.BookResponseDto;
import com.example.springbootintro.dto.response.BookWithoutCategoryResponseDto;
import com.example.springbootintro.exception.EntityNotFoundException;
import com.example.springbootintro.mapper.BookMapper;
import com.example.springbootintro.model.Book;
import com.example.springbootintro.repository.BookRepository;
import com.example.springbootintro.service.BookService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookResponseDto save(BookRequestDto book) {
        return bookMapper.toDto(bookRepository.save(bookMapper.toModel(book)));
    }

    @Override
    public List<BookResponseDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookResponseDto getById(Long id) {
        return bookMapper.toDto(bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can`t find book by id: " + id)));
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public BookResponseDto update(Long id, BookRequestDto bookRequestDto) {
        Book book = bookRepository.findById(id).get();
        book.setTitle(bookRequestDto.getTitle());
        book.setAuthor(bookRequestDto.getAuthor());
        book.setIsbn(bookRequestDto.getIsbn());
        book.setPrice(bookRequestDto.getPrice());
        book.setDescription(bookRequestDto.getDescription());
        book.setCoverImage(bookRequestDto.getCoverImage());
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookWithoutCategoryResponseDto> findAllByCategoryId(Pageable pageable, Long id) {
        return bookRepository.findAllByCategoriesId(id, pageable)
                .stream()
                .map(bookMapper::toDtoWithoutCategories)
                .collect(Collectors.toList());
    }
}
