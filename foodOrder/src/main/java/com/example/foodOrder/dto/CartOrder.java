package com.example.foodOrder.dto;

import com.example.foodOrder.entity.CartItem;

import java.util.List;

public class CartOrder {

    private int price;

    private Long restId;
    private List<CartItem> items;

    public CartOrder(int price, Long restId, List<CartItem> items) {
        this.price = price;
        this.restId = restId;
        this.items = items;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Long getRestId() {
        return restId;
    }

    public void setRestId(Long restId) {
        this.restId = restId;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }
}

