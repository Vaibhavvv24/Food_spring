package com.example.foodOrder.dto;

public class SignUpReq {
    private String email;

    private String name;

    private String password;

    private String phoneNum;

    public SignUpReq(){

    }

    public SignUpReq(String email, String name, String password,String phoneNum) {
        this.email = email;
        this.name = name;
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

}
