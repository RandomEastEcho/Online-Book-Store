package com.example.springbootintro.repository;

import com.example.springbootintro.model.OrderItem;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query("FROM OrderItem oi "
            + "LEFT JOIN FETCH oi.order o "
            + "LEFT JOIN FETCH o.user u "
            + "LEFT JOIN FETCH u.roles "
            + "WHERE o.id = :id AND u.id = :userId AND oi.id = :orderItemId")
    Optional<OrderItem> findOrderItemByOrderId(@Param("id") Long orderId,
                                               @Param("userId") Long userId,
                                               @Param("orderItemId") Long orderItemId);

}
