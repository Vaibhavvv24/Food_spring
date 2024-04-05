package com.example.foodOrder.dto;
import com.example.foodOrder.entity.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;


public class ProductDto {
    private Long id;

    private String name;

    private int price;
    private Long categoryid;
    private String categoryname;

    private Long restrauntId;

    private String restrauntName;

    private String returnedimg;

    public String getReturnedimg() {
        return returnedimg;
    }

    public void setReturnedimg(String returnedimg) {
        this.returnedimg = returnedimg;
    }

    public Long getRestrauntId() {
        return restrauntId;
    }

    public void setRestrauntId(Long restrauntId) {
        this.restrauntId = restrauntId;
    }

    public String getRestrauntName() {
        return restrauntName;
    }

    public void setRestrauntName(String restrauntName) {
        this.restrauntName = restrauntName;
    }

    public Long getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(Long categoryid) {
        this.categoryid = categoryid;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public ProductDto(String name, int price,String img) {
        this.name = name;
        this.price = price;
        this.returnedimg=img;
    }
    public ProductDto(){

    }
}