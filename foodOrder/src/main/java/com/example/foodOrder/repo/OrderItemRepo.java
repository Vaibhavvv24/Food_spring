package com.example.foodOrder.repo;

import com.example.foodOrder.dto.OrderItemDto;
import com.example.foodOrder.entity.OrderItem;
import com.example.foodOrder.entity.Restraunt;
import com.example.foodOrder.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface OrderItemRepo extends JpaRepository<OrderItem,Long> {
    List<OrderItem> findAllByRestraunt(Restraunt restraunt);
}
