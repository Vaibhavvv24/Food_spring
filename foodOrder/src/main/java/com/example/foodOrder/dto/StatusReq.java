package com.example.foodOrder.dto;

public class StatusReq {

    private String Status;

    private Long orderItemId;

    public StatusReq(String status, Long orderItemId) {
        Status = status;
        this.orderItemId = orderItemId;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }
}
