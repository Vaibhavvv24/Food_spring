package com.example.foodOrder.entity;

import com.example.foodOrder.dto.OrderItemDto;
import com.example.foodOrder.enums.OrderStatus;
import com.example.foodOrder.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;
import java.util.List;

@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="user_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;


//    @ManyToOne(fetch = FetchType.LAZY,optional = true)
//    @JoinColumn(name = "cart_id")
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JsonIgnore
//    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY,optional= true)
    @JoinColumn(name = "restraunt_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Restraunt restraunt;

    private int total;


    private OrderStatus orderStatus;

    private String paymentId;

    private PaymentStatus paymentStatus;

    private Date orderedAt;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "orders_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Order orders;

    public Order getOrder() {
        return orders;
    }

    public void setOrder(Order order) {
        this.orders = order;
    }

    public Date getOrderedAt() {
        return orderedAt;
    }

    public void setOrderedAt(Date orderedAt) {
        this.orderedAt = orderedAt;
    }

    public OrderItem(){

    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public OrderItem(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

//    public Cart getCart() {
//        return cart;
//    }
//
//    public void setCart(Cart cart) {
//        this.cart = cart;
//    }
    public Restraunt getRestraunt() {
        return restraunt;
    }

    public void setRestraunt(Restraunt restraunt) {
        this.restraunt = restraunt;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }


    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public OrderItemDto getOrderItemDto() {
        OrderItemDto orderItemDto=new OrderItemDto();
        orderItemDto.setOrderId(orders.getId());
        orderItemDto.setOrderStatus(orderStatus);
        orderItemDto.setRestName(restraunt.getName());
        orderItemDto.setRestId(restraunt.getId());
        orderItemDto.setOrderedAt(orderedAt);
        orderItemDto.setId(id);
        orderItemDto.setOwnerName(user.getName());
        orderItemDto.setUserId(user.getId());
        orderItemDto.setTotal(total);
        return orderItemDto;
    }
}
