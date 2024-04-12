package com.example.foodOrder.repo;

import com.example.foodOrder.entity.Order;
import com.example.foodOrder.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends JpaRepository<Order,Long> {
    Order findByUser(User user);
}
