package com.example.springbootintro.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.springbootintro.dto.request.CartItemRequestDto;
import com.example.springbootintro.dto.request.ShoppingCartQuantityRequestDto;
import com.example.springbootintro.dto.response.CartItemResponseDto;
import com.example.springbootintro.dto.response.ShoppingCartResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShoppingCartControllerTest {
    protected static MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void setUp(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(username = "user", authorities = "USER")
    @Sql(scripts = "classpath:database/shoppingCart/add-new-shopping-cart-to-table.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/shoppingCart/remove-shopping-cart-from-table.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getShoppingCartById_getShoppingCartById_ok() throws Exception {
        Long cartId = 1L;
        mockMvc.perform(get("/api/cart/" + cartId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cartId));
    }

    @Test
    @Sql(scripts = "classpath:database/shoppingCart/add-new-shopping-cart-to-table.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/shoppingCart/remove-shopping-cart-from-table.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getShoppingCartById_getShoppingCartByIdUnauthorizedUser_notOk() throws Exception {
        Long cartId = 1L;
        mockMvc.perform(get("/api/cart/" + cartId))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Sql(scripts = "classpath:database/shoppingCart/add-new-shopping-cart-to-table.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/shoppingCart/remove-shopping-cart-from-table.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithUserDetails("testUser")
    void addBookToShoppingCart_addBookToShoppingCart_ok() throws Exception {
        CartItemRequestDto cartItemRequestDto = new CartItemRequestDto();
        cartItemRequestDto.setBookId(1L);
        cartItemRequestDto.setQuantity(3);

        CartItemResponseDto cartItemResponseDto = new CartItemResponseDto();
        cartItemResponseDto.setId(1L);
        cartItemResponseDto.setBookId(1L);
        cartItemResponseDto.setQuantity(5);
        cartItemResponseDto.setBookTitle("testTitle");

        ShoppingCartResponseDto expected = new ShoppingCartResponseDto();
        expected.setId(1L);
        expected.setUserId(1L);
        expected.setCartItemResponseDtos(Set.of(cartItemResponseDto));

        String json = objectMapper.writeValueAsString(cartItemRequestDto);

        mockMvc.perform(post("/api/cart")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expected.getId()))
                .andExpect(jsonPath("$.userId").value(expected.getUserId()));
    }

    @Test
    @Sql(scripts = "classpath:database/shoppingCart/add-new-shopping-cart-to-table.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/shoppingCart/remove-shopping-cart-from-table.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithUserDetails("testAdmin")
    void addBookToShoppingCart_addBookToShoppingCartAdminForbidden_notOk() throws Exception {
        Long bookId = 1L;
        ShoppingCartQuantityRequestDto quantityRequestDto = new ShoppingCartQuantityRequestDto();
        quantityRequestDto.setQuantity(3);
        quantityRequestDto.setId(bookId);

        String json = objectMapper.writeValueAsString(quantityRequestDto);

        mockMvc.perform(post("/api/cart")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @Sql(scripts = "classpath:database/shoppingCart/add-new-shopping-cart-to-table.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/shoppingCart/remove-shopping-cart-from-table.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithUserDetails("testUser")
    void deleteBookFromShoppingCart_ValidRequest_ok() throws Exception {
        Long cartItemId = 1L;

        mockMvc.perform(delete("/api/cart/cart-items/" + cartItemId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cartItemId));
    }

    @Test
    @Sql(scripts = "classpath:database/shoppingCart/add-new-shopping-cart-to-table.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/shoppingCart/remove-shopping-cart-from-table.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithUserDetails("testAdmin")
    void deleteBookFromShoppingCart_ForbiddenRequest_notOk() throws Exception {
        Long cartItemId = 1L;

        mockMvc.perform(delete("/api/cart/cart-items/" + cartItemId))
                .andExpect(status().isForbidden());
    }
}
