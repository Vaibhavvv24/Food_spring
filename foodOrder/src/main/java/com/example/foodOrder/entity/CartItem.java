package com.example.foodOrder.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Blob;

@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name="product_id",nullable = false)
    private Product product;


    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "cart_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Cart cart;


    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "restraunt_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Restraunt restraunt;

    public CartItem(){

    }

    public Restraunt getRestraunt() {
        return restraunt;
    }

    public void setRestraunt(Restraunt restraunt) {
        this.restraunt = restraunt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
