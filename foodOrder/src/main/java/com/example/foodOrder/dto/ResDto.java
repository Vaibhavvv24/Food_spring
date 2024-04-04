package com.example.foodOrder.dto;

import jakarta.persistence.Lob;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Blob;

public class ResDto {
    private Long id;

    private String name;

    private String address;

    private Long ownerId;

    private String ownerName;

    private Blob img;

//    private MultipartFile img1;

//    public MultipartFile getImg1() {
//        return img1;
//    }
//
//    public void setImg1(MultipartFile img1) {
//        this.img1 = img1;
//    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public ResDto(String name, String address,Blob img) {
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

    public Blob getImg() {
        return img;
    }

    public void setImg(Blob img) {
        this.img = img;
    }
}
