package com.example.foodOrder.repo;

import com.example.foodOrder.entity.Cart;
import com.example.foodOrder.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepo extends JpaRepository<Cart,Long> {
    Cart findByCustomer(User customer);
}
