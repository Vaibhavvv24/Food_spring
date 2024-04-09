package com.example.foodOrder.entity;

import com.example.foodOrder.dto.CategoryDto;
import com.example.foodOrder.dto.ResDto;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.util.Base64;
import java.util.List;

@Entity
@Table(name="restraunt")
public class Restraunt {

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

    private String name;

    private String address;


    @OneToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "owner_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User owner;

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Lob
  @Column(columnDefinition = "longblob")
    private Blob img;

    public Restraunt(String name, String address,Blob img) {
        this.name = name;
        this.address = address;
        this.img = img;
    }
    public Restraunt(){

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

    public ResDto getResDto() {
        ResDto resDto=new ResDto();
        resDto.setName(name);
        resDto.setAddress(address);
        Blob blob=img;
        String base64=blobToBase64(blob);
        resDto.setImg(base64);
        resDto.setOwnerId(owner.getId());
        resDto.setId(id);
        resDto.setOwnerName(owner.getName());
        return resDto;
    }
}
