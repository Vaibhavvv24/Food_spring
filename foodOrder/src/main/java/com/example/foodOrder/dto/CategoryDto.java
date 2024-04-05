package com.example.foodOrder.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import org.springframework.web.multipart.MultipartFile;

public class CategoryDto {
    private Long id;

    private String name;

    private String description;

    private String base64;

    private Long resId;

    private String restName;

    public Long getResId() {
        return resId;
    }

    public void setResId(Long resId) {
        this.resId = resId;
    }

    public String getRestName() {
        return restName;
    }

    public void setRestName(String restName) {
        this.restName = restName;
    }

    public CategoryDto() {

    }

    public CategoryDto(String name, String description,String base) {
        this.name = name;
        this.description = description;
        this.base64=base;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReturnedimage() {
        return base64;
    }

    public void setReturnedimage(String returnedimage) {
        this.base64= returnedimage;
    }
}
