package com.example.foodOrder.dto;

public class CartRequest {

    private Long catId;

    private Long restId;
    private int price;

    public CartRequest(){

    }

    public CartRequest(Long catId, Long restId, int price) {
        this.catId = catId;
        this.restId = restId;
        this.price = price;
    }

    public Long getCatId() {
        return catId;
    }

    public void setCatId(Long catId) {
        this.catId = catId;
    }

    public Long getRestId() {
        return restId;
    }

    public void setRestId(Long restId) {
        this.restId = restId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
