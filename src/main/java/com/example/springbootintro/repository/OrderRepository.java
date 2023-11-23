package com.example.springbootintro.repository;

import com.example.springbootintro.model.Order;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUserId(Long userId);

    Optional<Order> findOrderByUserIdAndId(Long userId, Long orderId);

}
