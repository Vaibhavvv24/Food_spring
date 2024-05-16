package com.example.foodOrder.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "ordercart")
public class OrderCartItems {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="orderItem_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private OrderItem orderItem;

    private int price;

    private String name;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="restarunt_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restraunt restraunt;

    public OrderCartItems(){

    }

    public OrderCartItems(int price, String name) {
        this.price = price;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Restraunt getRestraunt() {
        return restraunt;
    }

    public void setRestraunt(Restraunt restraunt) {
        this.restraunt = restraunt;
    }
}
