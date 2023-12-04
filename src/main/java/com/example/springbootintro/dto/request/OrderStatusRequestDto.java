package com.example.springbootintro.dto.request;

import com.example.springbootintro.model.Order;
import lombok.Data;

@Data
public class OrderStatusRequestDto {
    private Order.Status status;
}
