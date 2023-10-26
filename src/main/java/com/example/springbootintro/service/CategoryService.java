package com.example.springbootintro.service;

import com.example.springbootintro.dto.request.CategoryRequestDto;
import com.example.springbootintro.dto.response.CategoryResponseDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    List<CategoryResponseDto> findAll(Pageable pageable);

    CategoryResponseDto getById(Long id);

    CategoryResponseDto save(CategoryRequestDto categoryRequestDto);

    CategoryResponseDto update(Long id, CategoryRequestDto categoryRequestDto);

    void deleteById(Long id);
}
