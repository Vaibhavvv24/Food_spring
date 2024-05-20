import React, { useState } from "react";
import { useParams } from "react-router-dom";

const PaymentSuccess = () => {
  const [refId, setRefId] = useState("");
  const [paymentId, setPaymentId] = useState("");
  const [status, setStatus] = useState("");
  const { orderId } = useParams();
  return (
    <div>
      <h1>Payment Successful</h1>
      <p>{orderId}</p>
    </div>
  );
};

export default PaymentSuccess;
