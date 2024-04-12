package com.example.foodOrder.repo;

import com.example.foodOrder.entity.OrderItem;
import com.example.foodOrder.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepo extends JpaRepository<OrderItem,Long> {
    OrderItem findByUser(User user);
}
