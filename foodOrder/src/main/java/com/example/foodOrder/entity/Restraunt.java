package com.example.foodOrder.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Blob;
import java.util.List;

@Entity
@Table(name="restraunt")
public class Restraunt {

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
}
