package com.example.springbootintro.mapper;

import com.example.springbootintro.config.MapperConfig;
import com.example.springbootintro.dto.response.OrderItemResponseDto;
import com.example.springbootintro.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface OrderItemMapper {
    @Mapping(source = "book.id", target = "bookId")
    OrderItemResponseDto toDto(OrderItem orderItem);
}
