package com.example.foodOrder.dto;

public class PaymentRedirect {

    private String paymentId;

    private Long orderId;

    public PaymentRedirect(){

    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
