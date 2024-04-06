package com.example.foodOrder.entity;

import com.example.foodOrder.dto.CategoryDto;
import com.example.foodOrder.dto.ProductDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.util.Base64;

@Entity
@Table(name = "product")
public class Product {

    public static String blobToBase64(Blob blob) {
        try (InputStream inputStream = blob.getBinaryStream()) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            byte[] imageBytes = outputStream.toByteArray();
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String productName;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "category_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore //references Category with category_id as fk referring to id of category
    private Category category;

    private int  price;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "restraunt_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Restraunt restraunt;

    @Lob
    @Column(columnDefinition = "longblob")
    private Blob img;

    public Product(){

    }

    public Product(String productName, Category category, int price, Blob img) {
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.img = img;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Restraunt getRestraunt() {
        return restraunt;
    }

    public void setRestraunt(Restraunt restraunt) {
        this.restraunt = restraunt;
    }

    public Blob getImg() {
        return img;
    }

    public void setImg(Blob img) {
        this.img = img;
    }

    public ProductDto getProductDto() {
        ProductDto productDto=new ProductDto();
        productDto.setName(productName);
        productDto.setPrice(price);
        Blob blob=img;
        String base64=blobToBase64(blob);
        productDto.setReturnedimg(base64);
        productDto.setId(id);
        productDto.setRestrauntId(restraunt.getId());
        productDto.setCategoryid(category.getId());
        productDto.setCategoryname(category.getName());
        productDto.setRestrauntName(restraunt.getName());
        return productDto;
    }
}
