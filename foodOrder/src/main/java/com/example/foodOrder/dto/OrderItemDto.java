package com.example.foodOrder.dto;

import com.example.foodOrder.entity.*;
import com.example.foodOrder.enums.OrderStatus;
import com.example.foodOrder.enums.PaymentStatus;

import java.util.Date;
import java.util.List;

public class OrderItemDto {

    private Long id;
    private String ownerName;

    private Long UserId;

//    private Long CartId;
//
//    private List<CartItem> cartItemList;


    private Long restId;

    private String restName;

    private PaymentStatus paymentStatus;

    private String paymentid;



    private OrderStatus orderStatus;

    private Date orderedAt;

    private Long orderId;

    private int total;



    public OrderItemDto(){

    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentid() {
        return paymentid;
    }

    public void setPaymentid(String paymentid) {
        this.paymentid = paymentid;
    }

    public OrderItemDto(OrderStatus orderStatus, Date orderedAt) {
        this.orderStatus = orderStatus;
        this.orderedAt = orderedAt;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Long getUserId() {
        return UserId;
    }

    public void setUserId(Long userId) {
        UserId = userId;
    }

//    public Long getCartId() {
//        return CartId;
//    }

//    public void setCartId(Long cartId) {
//        CartId = cartId;
//    }
//
//    public List<CartItem> getCartItemList() {
//        return cartItemList;
//    }
//
//    public void setCartItemList(List<CartItem> cartItemList) {
//        this.cartItemList = cartItemList;
//    }

    public Long getRestId() {
        return restId;
    }

    public void setRestId(Long restId) {
        this.restId = restId;
    }

    public String getRestName() {
        return restName;
    }

    public void setRestName(String restName) {
        this.restName = restName;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getOrderedAt() {
        return orderedAt;
    }

    public void setOrderedAt(Date orderedAt) {
        this.orderedAt = orderedAt;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
