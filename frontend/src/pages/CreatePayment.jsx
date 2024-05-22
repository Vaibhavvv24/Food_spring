import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import Base64decode from "../components/Base64decode";

const CreatePayment = () => {
  const currentUser = JSON.parse(localStorage.getItem("user"));
  const { id } = useParams();
  const [response, setResponse] = useState({});
  const [order, setOrder] = useState({});

  const Navigate = useNavigate();

  const [cart, setCart] = useState([]);
  //const [orderId, setOrderId] = useState("");

  useEffect(() => {
    const fetchCart = async () => {
      const res = await fetch(
        `http://localhost:8080/api/customer/cart/${currentUser.id}`,
        {
          headers: {
            Authorization: "Bearer " + currentUser.jwt,
          },
        }
      );
      const data = await res.json();
      console.log(data);
      if (data.message === "Your cart is empty") {
        alert("Your cart is empty");
        Navigate("/customer/shop");
      }
      setCart(data);
    };
    fetchCart();
  }, []);

  async function getOrder() {
    const res = await fetch(
      `http://localhost:8080/api/customer/fetchorder/${id}`,
      {
        method: "GET",
        headers: {
          Authorization: "Bearer " + currentUser.jwt,
        },
      }
    );

    const data = await res.json();
    setOrder(data);
    console.log(data);
  }

  useEffect(() => {
    getOrder();
  }, []);

  const handleDelete = async () => {
    const res = await fetch(
      `http://localhost:8080/api/customer/cart/${currentUser.id}/delete`,
      {
        method: "DELETE",
        headers: {
          Authorization: "Bearer " + currentUser.jwt,
        },
      }
    );
    const data = await res.json();
    console.log(data);
    //alert("Cart cleared successfully");
  };

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
    handleDelete();
    //Navigate(" " + data.paymentLink);
    window.location.replace(data.paymentLink);

    setResponse(data);
    //alert("Payment Link sent on email");
  }
  return (
    <div className="">
      <h1 className="text-center my-5 text-2xl">Order Details</h1>
      <div className="flex">
        <div className="flex flex-col gap-10 m-5 w-1/2">
          {cart.map((item) => (
            <div key={item.id} className="flex flex-col  bg-red-300 p-7 gap-4">
              <Base64decode base64String={item.productImg} />
              <p>Name: {item.productName}</p>
              <p>Price: ₹{item.productPrice}</p>
            </div>
          ))}
        </div>
        <div className="flex flex-col gap-4 m-5 w-1/2 bg-blue-300 h-1/2 p-5">
          <p>Customer name: {order.ownerName}</p>
          <p>Total: ₹{order.total}</p>
          <p>Restraunt: {order.restName}</p>
          <button
            onClick={makePayment}
            className="bg-green-500 p-4 rounded-md text-white"
          >
            {" "}
            Make Payment
          </button>
        </div>
      </div>
    </div>
  );
};

export default CreatePayment;
