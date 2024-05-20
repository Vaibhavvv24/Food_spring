package com.example.foodOrder.dto;

public class ApiRes {

    private String message;

    private Boolean Status;

    public ApiRes(){

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean status) {
        Status = status;
    }
}
