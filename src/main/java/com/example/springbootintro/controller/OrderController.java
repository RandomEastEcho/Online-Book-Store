package com.example.springbootintro.controller;

import com.example.springbootintro.dto.request.OrderRequestDto;
import com.example.springbootintro.dto.request.OrderStatusRequestDto;
import com.example.springbootintro.dto.response.OrderItemResponseDto;
import com.example.springbootintro.dto.response.OrderResponseDto;
import com.example.springbootintro.model.User;
import com.example.springbootintro.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order management", description = "Endpoints for managing orders")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @Secured("USER")
    @Operation(summary = "save order", description = "save and submit your order")
    public OrderResponseDto save(@RequestBody @Valid OrderRequestDto orderRequestDto,
                                 Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return orderService.save(user.getId(), orderRequestDto.getShippingAddress());
    }

    @GetMapping
    @Secured("USER")
    @Operation(summary = "get order history", description = "get your order history")
    public List<OrderResponseDto> findAll(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return orderService.getByUser(user.getId());
    }

    @GetMapping("/{orderId}/items")
    @Secured("USER")
    @Operation(summary = "get items from order",
            description = "get list of all items from certain order")
    public List<OrderItemResponseDto> findByOrderId(@PathVariable
                                                        @Parameter(description = "order id")
                                                    Long orderId,
                                                    Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return orderService.findItemsByOrderId(orderId, user.getId());
    }

    @GetMapping("/{orderId}/items/{orderItemId}")
    @Secured("USER")
    @Operation(summary = "find item from order",
            description = "finds exact item from chosen order")
    public OrderItemResponseDto findExactItem(@PathVariable @Parameter(description = "order id")
                                              Long orderId,
                                              @PathVariable
                                              @Parameter(description = "order item id")
                                              Long orderItemId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return orderService.findExactItemByOrderId(orderId, orderItemId, user.getId());
    }

    @PatchMapping("/{orderId}")
    @Secured("ADMIN")
    @Operation(summary = "change order status", description = "change order status by order id")
    public OrderResponseDto changeStatus(@PathVariable @Parameter(description = "order id")
                                         Long orderId, @RequestBody @Parameter(schema =
            @Schema(implementation = OrderStatusRequestDto.class))
            OrderStatusRequestDto orderStatus) {
        return orderService.updateStatus(orderId, orderStatus);
    }
}
