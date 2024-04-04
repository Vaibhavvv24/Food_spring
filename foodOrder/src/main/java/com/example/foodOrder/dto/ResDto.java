package com.example.foodOrder.dto;

import jakarta.persistence.Lob;

public class ResDto {
    private Long id;

    private String name;

    private String address;
    private byte[] img;

    public ResDto(String name, String address, byte[] img) {
        this.name = name;
        this.address = address;
        this.img = img;
    }
    public ResDto(){

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }
}
