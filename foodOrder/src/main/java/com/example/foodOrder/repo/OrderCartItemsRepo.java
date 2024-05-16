package com.example.foodOrder.repo;

import com.example.foodOrder.entity.OrderCartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderCartItemsRepo extends JpaRepository<OrderCartItems,Long> {
}
