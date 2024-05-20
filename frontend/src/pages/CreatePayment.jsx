import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

const CreatePayment = () => {
  const currentUser = JSON.parse(localStorage.getItem("user"));
  const { id } = useParams();
  const [response, setResponse] = useState({});

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

    setResponse(data);
  }

  useEffect(() => {
    makePayment();
  }, []);
  return (
    <div>
      <h1>Payment</h1>
      <p>{response.paymentLink}</p>
    </div>
  );
};

export default CreatePayment;
