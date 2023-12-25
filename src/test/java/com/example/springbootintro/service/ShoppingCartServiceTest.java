package com.example.springbootintro.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.springbootintro.dto.request.CartItemRequestDto;
import com.example.springbootintro.dto.request.ShoppingCartQuantityRequestDto;
import com.example.springbootintro.dto.response.ShoppingCartResponseDto;
import com.example.springbootintro.mapper.CartItemMapper;
import com.example.springbootintro.mapper.ShoppingCartMapper;
import com.example.springbootintro.model.Book;
import com.example.springbootintro.model.CartItem;
import com.example.springbootintro.model.ShoppingCart;
import com.example.springbootintro.model.User;
import com.example.springbootintro.repository.CartItemRepository;
import com.example.springbootintro.repository.ShoppingCartRepository;
import com.example.springbootintro.repository.UserRepository;
import com.example.springbootintro.service.impl.ShoppingCartServiceImpl;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ShoppingCartServiceTest {
    private ShoppingCart testShoppingCart;
    private ShoppingCartResponseDto expectedResponse;
    private User testUser;
    private CartItem testCartItem;
    private Book testBook;

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private ShoppingCartMapper shoppingCartMapper;

    @Mock
    private CartItemMapper cartItemMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ShoppingCartServiceImpl shoppingCartService;

    @BeforeEach
    public void init() {
        testBook = new Book();
        testBook.setId(1L);
        testBook.setTitle("testTitle");
        testBook.setAuthor("testAuthor");
        testBook.setIsbn("testISBN");
        testBook.setPrice(BigDecimal.ONE);
        testBook.setDescription("testDescription");
        testBook.setCoverImage("testCoverImage");

        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("testEmail");
        testUser.setPassword("testPassword");
        testUser.setFirstName("testFirstName");
        testUser.setLastName("testLastName");

        testCartItem = new CartItem();
        testCartItem.setId(1L);
        testCartItem.setShoppingCart(testShoppingCart);
        testCartItem.setBook(testBook);
        testCartItem.setQuantity(5);

        Set<CartItem> testSetOfCartItems = new HashSet<>();
        testSetOfCartItems.add(testCartItem);

        testShoppingCart = new ShoppingCart();
        testShoppingCart.setId(1L);
        testShoppingCart.setUser(testUser);
        testShoppingCart.setCartItems(testSetOfCartItems);

        expectedResponse = new ShoppingCartResponseDto();
        expectedResponse.setId(testShoppingCart.getId());
        expectedResponse.setUserId(testShoppingCart.getUser().getId());
    }

    @Test
    void save_saveShoppingCart_ok() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));
        when(shoppingCartRepository.save(any())).thenReturn(testShoppingCart);

        ShoppingCart result = shoppingCartService.save(userId);

        assertNotNull(result);
        assertEquals(expectedResponse.getUserId(), result.getUser().getId());
    }

    @Test
    void getById_getShoppingCartById_ok() {
        Long cartId = 1L;
        when(shoppingCartRepository.findById(cartId)).thenReturn(Optional.of(testShoppingCart));
        when(shoppingCartMapper.toDto(testShoppingCart)).thenReturn(expectedResponse);

        ShoppingCartResponseDto result = shoppingCartService.getById(cartId);

        assertNotNull(result);
        assertEquals(expectedResponse.getId(), result.getId());
    }

    @Test
    void addBook_addBookToShoppingCart_ok() {
        CartItemRequestDto cartItemRequestDto = new CartItemRequestDto();
        cartItemRequestDto.setShoppingCartId(1L);

        when(cartItemMapper.toModel(cartItemRequestDto)).thenReturn(testCartItem);
        when(cartItemRepository.save(testCartItem)).thenReturn(testCartItem);
        when(shoppingCartRepository.findById(cartItemRequestDto.getShoppingCartId()))
                .thenReturn(Optional.of(testShoppingCart));
        when(shoppingCartRepository.save(testShoppingCart)).thenReturn(testShoppingCart);
        when(shoppingCartMapper.toDto(testShoppingCart)).thenReturn(expectedResponse);

        ShoppingCartResponseDto result = shoppingCartService.addBook(cartItemRequestDto);

        assertNotNull(result);
        verify(cartItemRepository, times(1)).save(testCartItem);
        verify(shoppingCartRepository, times(1)).save(testShoppingCart);
        verify(shoppingCartMapper, times(1)).toDto(testShoppingCart);
    }

    @Test
    void updateQuantity_updateCartItemQuantity_ok() {
        Long cartItemId = 1L;
        Long shoppingCartId = 1L;
        ShoppingCartQuantityRequestDto cartQuantityDto = new ShoppingCartQuantityRequestDto();
        cartQuantityDto.setId(shoppingCartId);
        cartQuantityDto.setQuantity(10);

        when(cartItemRepository.findById(cartItemId)).thenReturn(Optional.of(testCartItem));
        when(shoppingCartMapper.toDto(testShoppingCart)).thenReturn(expectedResponse);
        when(shoppingCartRepository.findById(shoppingCartId))
                .thenReturn(Optional.of(testShoppingCart));

        ShoppingCartResponseDto result = shoppingCartService
                .updateQuantity(cartItemId, cartQuantityDto);

        assertNotNull(result);
        assertEquals(cartQuantityDto.getQuantity(), testCartItem.getQuantity());
    }

    @Test
    void deleteBookFromCart_deleteCartItemAndUpdateShoppingCart_ok() {
        Long cartItemId = 1L;
        Long userId = 1L;

        when(shoppingCartMapper.toDto(testShoppingCart)).thenReturn(expectedResponse);
        when(shoppingCartRepository.findByUserId(userId)).thenReturn(Optional.of(testShoppingCart));

        ShoppingCartResponseDto result = shoppingCartService.deleteBookFromCart(cartItemId, userId);

        assertNotNull(result);
        verify(cartItemRepository, times(1)).deleteById(cartItemId);
        verify(shoppingCartMapper, times(1)).toDto(testShoppingCart);
    }

    @Test
    void getModelById_returnShoppingCartByUserId_ok() {
        Long userId = 1L;
        when(shoppingCartRepository.findByUserId(userId)).thenReturn(Optional.of(testShoppingCart));

        ShoppingCart result = shoppingCartService.getModelById(userId);

        assertNotNull(result);
        assertEquals(testShoppingCart, result);
    }
}
