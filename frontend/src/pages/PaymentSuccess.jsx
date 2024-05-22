import React, { useEffect, useState } from "react";
import { useParams, useSearchParams } from "react-router-dom";

const PaymentSuccess = () => {
  const [refId, setRefId] = useState("");
  // const [paymentId, setPaymentId] = useState("");
  const [status, setStatus] = useState("");
  const { orderId } = useParams();
  const [param, setParams] = useSearchParams();
  const paymentId = param.get("razorpay_payment_id");
  console.log(orderId, paymentId);

  const currentUser = JSON.parse(localStorage.getItem("user"));

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
        setRefId(data.refId);
        setStatus(data.status);
        console.log(data);
      } catch (error) {
        console.error(error);
      }
    };
    fetchPaymentDetails();
  }, [orderId]);
  return (
    <div>
      <h1>Payment Successful</h1>
      <p>{orderId}</p>
    </div>
  );
};

export default PaymentSuccess;
