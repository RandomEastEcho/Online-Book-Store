package com.example.springbootintro.mapper;

import com.example.springbootintro.config.MapperConfig;
import com.example.springbootintro.dto.request.CartItemRequestDto;
import com.example.springbootintro.dto.response.CartItemResponseDto;
import com.example.springbootintro.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = BookMapper.class)
public interface CartItemMapper {
    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "book.title", target = "bookTitle")
    CartItemResponseDto toDto(CartItem cartItem);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(source = "bookId", target = "book", qualifiedByName = "bookFromId")
    @Mapping(source = "shoppingCartId", target = "shoppingCart.id")
    CartItem toModel(CartItemRequestDto cartItemRequestDto);
}
