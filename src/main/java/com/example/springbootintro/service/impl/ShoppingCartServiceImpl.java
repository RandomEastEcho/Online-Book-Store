package com.example.springbootintro.service.impl;

import com.example.springbootintro.dto.request.CartItemRequestDto;
import com.example.springbootintro.dto.request.ShoppingCartQuantityRequestDto;
import com.example.springbootintro.dto.response.ShoppingCartResponseDto;
import com.example.springbootintro.exception.EntityNotFoundException;
import com.example.springbootintro.mapper.CartItemMapper;
import com.example.springbootintro.mapper.ShoppingCartMapper;
import com.example.springbootintro.model.CartItem;
import com.example.springbootintro.model.ShoppingCart;
import com.example.springbootintro.model.User;
import com.example.springbootintro.repository.CartItemRepository;
import com.example.springbootintro.repository.ShoppingCartRepository;
import com.example.springbootintro.repository.UserRepository;
import com.example.springbootintro.service.ShoppingCartService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemMapper cartItemMapper;
    private final UserRepository userRepository;

    @Override
    public ShoppingCart save(Long userId) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(getUserById(userId));
        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCartResponseDto getById(Long cartId) {
        return shoppingCartMapper.toDto(shoppingCartRepository.findById(cartId).orElseThrow(
                () -> new EntityNotFoundException("Can`t find shopping cart by id: " + cartId)));
    }

    @Override
    public ShoppingCartResponseDto addBook(CartItemRequestDto cartItemRequestDto) {
        CartItem cartItem = cartItemMapper.toModel(cartItemRequestDto);
        cartItemRepository.save(cartItem);
        ShoppingCart shoppingCart = getShoppingCartById(cartItemRequestDto.getShoppingCartId());
        shoppingCart.getCartItems().add(cartItem);
        return shoppingCartMapper.toDto(shoppingCartRepository.save(shoppingCart));
    }

    @Override
    public ShoppingCartResponseDto updateQuantity(Long cartItemId,
                                                  ShoppingCartQuantityRequestDto cartQuantityDto) {
        CartItem cartItem = getCartItemById(cartItemId);
        cartItem.setQuantity(cartQuantityDto.getQuantity());
        return shoppingCartMapper.toDto(getShoppingCartById(cartQuantityDto.getId()));
    }

    @Override
    public ShoppingCartResponseDto deleteBookFromCart(Long cartItemId, Long userId) {
        cartItemRepository.deleteById(cartItemId);
        return shoppingCartMapper.toDto(getByShoppingCartByUserId(userId));
    }

    @Override
    public ShoppingCart getModelById(Long id) {
        return shoppingCartRepository.findByUserId(id).orElseThrow(() ->
                new EntityNotFoundException("Can`t find shopping cart by user id: " + id));
    }

    private User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Can`t find user by id: " + id));
    }

    private ShoppingCart getShoppingCartById(Long cartId) {
        return shoppingCartRepository.findById(cartId).orElseThrow(() ->
                new EntityNotFoundException("Can`t find shopping cart by id:" + cartId));
    }

    private CartItem getCartItemById(Long cartItemId) {
        return cartItemRepository.findById(cartItemId).orElseThrow(() ->
                new EntityNotFoundException("Can`t find cart item by id: " + cartItemId));
    }

    private ShoppingCart getByShoppingCartByUserId(Long userId) {
        return shoppingCartRepository.findByUserId(userId).orElseThrow(() ->
                new EntityNotFoundException("Can`t find cart item by user id: " + userId));
    }
}
