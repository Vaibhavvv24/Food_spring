import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

const PaymentSuccess = () => {
  const currentUser = JSON.parse(localStorage.getItem("user"));
  const { id } = useParams();
  //const [response, setResponse] = useState({});
  const [paymentlink, setPaymentlink] = useState("");
  const [refId, setRefId] = useState("");
  const [paymentId, setPaymentId] = useState("");
  const [status, setStatus] = useState("");
  const { orderId } = useParams();

  const Navigate = useNavigate();

  async function makePayment() {
    const res = await fetch(
      `http://localhost:8080/api/customer/order/payment/${id}`,
      {
        method: "POST",
        headers: {
          Authorization: "Bearer " + currentUser.jwt,
        },
      }
    );

    const data = await res.json();

    console.log(data);
    //Navigate(" " + data.paymentLink);

    setPaymentlink(data.paymentLink);
    setPaymentId(data.paymentId);
    alert("Payment Link sent on email");
  }

  useEffect(() => {
    makePayment();
  }, []);

  useEffect(() => {
    const fetchPaymentDetails = async () => {
      try {
        const response = await fetch(
          `http://localhost:8080/api/customer/payments?payment_id=${paymentId}&order_id=${orderId}`
        );
        const data = await response.json();
        setPaymentId(data.paymentId);
        setRefId(data.refId);
        setStatus(data.status);
      } catch (error) {
        console.error(error);
      }
    };
    fetchPaymentDetails();
  }, [orderId, paymentId]);
  return (
    <div>
      <h1>Payment Link</h1>
      <p>{paymentlink}</p>
      <h1>Payment Successful</h1>
      <p>{orderId}</p>
    </div>
  );
};

export default PaymentSuccess;
