package com.example.springbootintro.service;

import com.example.springbootintro.dto.request.OrderStatusRequestDto;
import com.example.springbootintro.dto.response.OrderItemResponseDto;
import com.example.springbootintro.dto.response.OrderResponseDto;
import java.util.List;

public interface OrderService {
    OrderResponseDto save(Long userId, String shippingAddress);

    OrderResponseDto updateStatus(Long orderId, OrderStatusRequestDto orderStatusRequestDto);

    List<OrderResponseDto> getByUser(Long userId);

    List<OrderItemResponseDto> findItemsByOrderId(Long orderId, Long userId);

    OrderItemResponseDto findExactItemByOrderId(Long orderId, Long orderItemId, Long userId);

}
