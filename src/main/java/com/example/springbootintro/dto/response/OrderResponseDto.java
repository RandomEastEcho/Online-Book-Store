package com.example.springbootintro.dto.response;

import com.example.springbootintro.model.Order;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;

@Data
public class OrderResponseDto {
    private Long id;
    private Long userId;
    private Set<OrderItemResponseDto> orderItemResponseDtos;
    private LocalDateTime localDateTime;
    private BigDecimal total;
    private Order.Status status;
}
