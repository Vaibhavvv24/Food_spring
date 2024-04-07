package com.example.foodOrder.repo;

import com.example.foodOrder.entity.Cart;
import com.example.foodOrder.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem,Long> {
    List<CartItem> findAllByCart(Cart cart);
}
