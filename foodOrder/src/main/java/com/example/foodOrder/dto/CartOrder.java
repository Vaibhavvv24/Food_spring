package com.example.foodOrder.dto;

import com.example.foodOrder.entity.CartItem;

import java.util.List;

public class CartOrder {

    private int price;
    public CartOrder(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}

