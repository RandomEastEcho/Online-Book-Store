package com.example.springbootintro.controller;

import com.example.springbootintro.dto.request.CartItemRequestDto;
import com.example.springbootintro.dto.request.ShoppingCartQuantityRequestDto;
import com.example.springbootintro.dto.response.ShoppingCartResponseDto;
import com.example.springbootintro.model.User;
import com.example.springbootintro.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Shopping cart management", description = "Endpoints for managing shopping carts")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @GetMapping("/{id}")
    @Secured({"ADMIN", "USER"})
    @Operation(summary = "Get shopping cart", description = "Get shopping cart by it`s id")
    public ShoppingCartResponseDto getShoppingCartById(@Parameter(
            description = "Id of searched shopping cart",
            name = "id",
            required = true,
            example = "5"
            ) @PathVariable Long id) {
        return shoppingCartService.getById(id);
    }

    @PostMapping
    @Secured("USER")
    @Operation(summary = "Add an book",
                description = "Add books to your shopping cart")
    public ShoppingCartResponseDto addBookToShoppingCart(@RequestBody @Parameter(
            schema = @Schema(implementation = CartItemRequestDto.class))
            CartItemRequestDto cartItemRequestDto, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        cartItemRequestDto.setShoppingCartId(user.getId());
        return shoppingCartService.addBook(cartItemRequestDto);
    }

    @PutMapping("/cart-items/{cartItemId}")
    @Secured("USER")
    @Operation(summary = "Update quantity of book",
            description = "Change number of books in shopping cart")
    public ShoppingCartResponseDto updateQuantityOfBooks(@Parameter(
            description = "Id of cart book",
            name = "id",
            required = true,
            example = "5"
            ) @PathVariable Long cartItemId,
            @Parameter(schema =
            @Schema(implementation = ShoppingCartQuantityRequestDto.class))
            @RequestBody ShoppingCartQuantityRequestDto dto, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        dto.setId(user.getId());
        return shoppingCartService.updateQuantity(cartItemId,dto);
    }

    @DeleteMapping("/cart-items/{cartItemId}")
    @Secured("USER")
    @Operation(summary = "Delete book from shopping cart",
            description = "Delete book from shopping cart")
    public ShoppingCartResponseDto deleteBookFromShoppingCart(@Parameter(
            description = "Id of book in shopping cart",
            name = "id",
            required = true,
            example = "5"
    ) @PathVariable Long cartItemId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.deleteBookFromCart(cartItemId, user.getId());
    }
}
