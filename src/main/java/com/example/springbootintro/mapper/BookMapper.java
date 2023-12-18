package com.example.springbootintro.mapper;

import com.example.springbootintro.config.MapperConfig;
import com.example.springbootintro.dto.request.BookRequestDto;
import com.example.springbootintro.dto.response.BookResponseDto;
import com.example.springbootintro.dto.response.BookWithoutCategoryResponseDto;
import com.example.springbootintro.model.Book;
import com.example.springbootintro.model.Category;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookResponseDto toDto(Book book);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    Book toModel(BookRequestDto book);

    BookWithoutCategoryResponseDto toDtoWithoutCategories(Book book);

    @AfterMapping
    default void setCategoriesId(@MappingTarget BookResponseDto bookResponseDto, Book book) {
        bookResponseDto.setCategoriesId(book.getCategories().stream()
                .map(Category::getId)
                .collect(Collectors.toSet()));
    }

    @Named("bookFromId")
    default Book bookFromId(Long id) {
        if (id == null) {
            return null;
        }
        Book book = new Book();
        book.setId(id);
        return book;
    }
}
