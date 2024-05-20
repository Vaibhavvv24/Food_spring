package com.example.foodOrder.dto;

public class PaymentResponse {

    private String paymentLink;
    private String paymentId;

    public PaymentResponse(String paymentLink, String paymentId) {
        this.paymentLink = paymentLink;
        this.paymentId = paymentId;
    }
    public PaymentResponse(){

    }

    public String getPaymentLink() {
        return paymentLink;
    }

    public void setPaymentLink(String paymentLink) {
        this.paymentLink = paymentLink;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }
}
