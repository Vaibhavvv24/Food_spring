import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import Base64decode from "../components/Base64decode";

const Cart = () => {
  const navigate = useNavigate();
  const [cart, setCart] = useState([]);

  const currentUser = JSON.parse(localStorage.getItem("user"));

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
      setCart(data);
    };
    fetchCart();
  }, []);
  async function handleorder() {
    const res = await fetch(
      `http://localhost:8080/api/customer/order/${currentUser.id}/restraunt/${cart[0].restId}/cart/${cart[0].cartId}`,
      {
        method: "POST",
        headers: {
          Authorization: "Bearer " + currentUser.jwt,
        },
      }
    );
  }

  const handleRemove = async (prodId) => {
    const res = await fetch(
      `http://localhost:8080/api/customer/cart/${currentUser.id}/update/${prodId}`,
      {
        method: "PUT",
        headers: {
          Authorization: "Bearer " + currentUser.jwt,
        },
      }
    );
    const data = await res.json();
    console.log(data);
    window.location.reload();
  };
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
    alert("Cart cleared successfully");
    navigate("/");
  };
  if (cart.length === 0) {
    return (
      <div>
        <h1>Cart is empty</h1>
      </div>
    );
  }

  return (
    <div>
      Cart
      {cart.map((item) => (
        <div className="flex flex-col" key={item.id}>
          <p>{item.productName}</p>
          <p>{item.productPrice}</p>
          <p>{item.restrauntName}</p>
          <Base64decode base64String={item.productImg} />
          <button onClick={() => handleRemove(item.id)}>Remove</button>
        </div>
      ))}
      <button onClick={handleDelete}>Clear Cart</button>
      <button onClick={handleorder}>Place Order</button>
    </div>
  );
};

export default Cart;
