package com.example.springbootintro.service.impl;

import com.example.springbootintro.dto.request.OrderStatusRequestDto;
import com.example.springbootintro.dto.response.OrderItemResponseDto;
import com.example.springbootintro.dto.response.OrderResponseDto;
import com.example.springbootintro.exception.EntityNotFoundException;
import com.example.springbootintro.mapper.OrderItemMapper;
import com.example.springbootintro.mapper.OrderMapper;
import com.example.springbootintro.model.CartItem;
import com.example.springbootintro.model.Order;
import com.example.springbootintro.model.OrderItem;
import com.example.springbootintro.model.ShoppingCart;
import com.example.springbootintro.model.User;
import com.example.springbootintro.repository.OrderItemRepository;
import com.example.springbootintro.repository.OrderRepository;
import com.example.springbootintro.service.OrderService;
import com.example.springbootintro.service.ShoppingCartService;
import com.example.springbootintro.service.UserService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    @Override
    public OrderResponseDto save(Long userId, String shippingAddress) {
        Order order = createNewOrder(userId, shippingAddress);
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public OrderResponseDto updateStatus(Long orderId,
                                         OrderStatusRequestDto orderStatusRequestDto) {
        Order existingOrder = findOrderById(orderId);
        existingOrder.setStatus(orderStatusRequestDto.getStatus());
        return orderMapper.toDto(orderRepository.save(existingOrder));
    }

    @Override
    public List<OrderResponseDto> getByUser(Long userId) {
        return orderRepository.findAllByUserId(userId).stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderItemResponseDto> findItemsByOrderId(Long orderId, Long userId) {
        return orderMapper.toDto(orderRepository.findOrderByUserIdAndId(userId, orderId)
                        .orElseThrow(() ->
                new EntityNotFoundException("Can`t find order items with userId: " + userId
                        + " and order id: " + orderId)))
                .getOrderItemResponseDtos().stream()
                .toList();
    }

    @Override
    public OrderItemResponseDto findExactItemByOrderId(Long orderId,
                                                       Long orderItemId,
                                                       Long userId) {
        return orderItemMapper.toDto(orderItemRepository
                .findOrderItemByOrderId(orderId, userId, orderItemId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Can`t find order item by id: " + orderItemId
                + "and order id: " + orderId + " and user id: " + userId)));
    }

    private Order createNewOrder(Long userId, String shippingAddress) {
        Order order = new Order();
        order.setOrderTime(LocalDateTime.now());
        order.setStatus(Order.Status.PENDING);
        User user = userService.findById(userId);
        order.setUser(user);
        order.setShippingAddress(shippingAddress);
        order.setTotal(BigDecimal.ZERO);
        ShoppingCart shoppingCart = shoppingCartService.getModelById(userId);
        Set<OrderItem> orderItems = createItemsForOrder(order, shoppingCart);
        order.setOrderItems(orderItems);
        order.setTotal(getTotal(orderItems));
        return orderRepository.save(order);
    }

    private Set<OrderItem> createItemsForOrder(Order order, ShoppingCart shoppingCart) {
        Set<OrderItem> orderItemSet = new HashSet<>();
        for (CartItem cartItem : shoppingCart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setBook(cartItem.getBook());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getBook().getPrice()
                    .multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            orderItemSet.add(orderItem);
        }
        return orderItemSet;
    }

    private BigDecimal getTotal(Set<OrderItem> orderItems) {
        return orderItems.stream()
                .map(OrderItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() ->
                new EntityNotFoundException("Can`t find order by id: " + orderId));
    }
}
