package com.example.foodOrder.dto;

public class SignUpReq {
    private String name;
    private String email;

    private String password;

    private String phoneNum;

    public SignUpReq(){

    }

    public SignUpReq(String name,String email, String password,String phoneNum) {
        this.name = name;
        this.email = email;

        this.password = password;
        this.phoneNum=phoneNum;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
