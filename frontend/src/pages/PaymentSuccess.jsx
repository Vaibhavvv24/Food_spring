import React, { useEffect, useState } from "react";
import { useNavigate, useParams, useSearchParams } from "react-router-dom";

const PaymentSuccess = () => {
  const { orderId } = useParams();
  const [param, setParams] = useSearchParams();
  const paymentId = param.get("razorpay_payment_id");
  console.log(orderId, paymentId);

  const currentUser = JSON.parse(localStorage.getItem("user"));

  const Navigate = useNavigate();
  useEffect(() => {
    const fetchPaymentDetails = async () => {
      try {
        const response = await fetch(
          `http://localhost:8080/api/customer/payments`,
          {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
              Authorization: "Bearer " + currentUser.jwt,
            },
            body: JSON.stringify({
              paymentId: paymentId,
              orderId: orderId,
            }),
          }
        );

        const data = await response.json();
        //setPaymentId(data.paymentId);
        console.log(data);
      } catch (error) {
        console.error(error);
      }
    };
    fetchPaymentDetails();
  }, [orderId]);
  return (
    <div>
      <h1>Payment Successful for order {orderId}</h1>
      <button onClick={() => Navigate(`/orders/${currentUser.id}`)}>
        Go to Orders to Check Status of Order
      </button>
    </div>
  );
};

export default PaymentSuccess;
