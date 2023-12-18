package com.example.springbootintro.mapper;

import com.example.springbootintro.config.MapperConfig;
import com.example.springbootintro.dto.request.CategoryRequestDto;
import com.example.springbootintro.dto.response.CategoryResponseDto;
import com.example.springbootintro.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {
    CategoryResponseDto toDto(Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    Category toModel(CategoryRequestDto categoryRequestDto);
}
