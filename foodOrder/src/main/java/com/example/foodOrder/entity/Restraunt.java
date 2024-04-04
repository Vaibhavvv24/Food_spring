package com.example.foodOrder.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="restraunt")
public class Restraunt {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String address;

    @Lob
  @Column(columnDefinition = "longblob")
    private byte[] img;

    public Restraunt(String name, String address, byte[] img) {
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

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }
}
