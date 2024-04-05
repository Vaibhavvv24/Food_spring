package com.example.foodOrder.entity;

import com.example.foodOrder.dto.CategoryDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.util.Base64;
import java.util.List;

@Entity
@Table(name="category")
public class Category {

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "rest_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Restraunt restraunt;

    private String description;


    public Category(){

    }

    @Lob
    @Column(columnDefinition = "longblob")
    private Blob img;
    public Category(String name, Blob img,String des) {
        this.name = name;
        this.img = img;
        this.description=des;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CategoryDto getCatDto() {
        CategoryDto categoryDto=new CategoryDto();
        categoryDto.setName(name);
        categoryDto.setDescription(description);
        Blob blob=img;
        String base64=blobToBase64(blob);
        categoryDto.setReturnedimage(base64);
        categoryDto.setId(id);
        categoryDto.setResId(restraunt.getId());
        categoryDto.setRestName(restraunt.getName());
        return categoryDto;
    }
}
