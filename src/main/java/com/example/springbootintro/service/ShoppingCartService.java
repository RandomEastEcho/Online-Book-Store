package com.example.springbootintro.service;

import com.example.springbootintro.dto.request.CartItemRequestDto;
import com.example.springbootintro.dto.request.ShoppingCartQuantityRequestDto;
import com.example.springbootintro.dto.response.ShoppingCartResponseDto;
import com.example.springbootintro.model.ShoppingCart;

public interface ShoppingCartService {
    ShoppingCart save(Long userId);

    ShoppingCartResponseDto getById(Long cartId);

    ShoppingCartResponseDto addBook(CartItemRequestDto cartItemRequestDto);

    ShoppingCartResponseDto updateQuantity(Long cartItemId,
                                           ShoppingCartQuantityRequestDto cartQuantityDto);

    ShoppingCartResponseDto deleteBookFromCart(Long cartItemId, Long userId);

    ShoppingCart getModelById(Long userId);
}
