import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import Base64decode from "../components/Base64decode";
import { MdDelete } from "react-icons/md";

function calculateTotal(cart) {
  let total = 0;
  for (let i = 0; i < cart.length; i++) {
    total += cart[i].productPrice;
  }
  return total;
}

const Cart = () => {
  const navigate = useNavigate();
  const [cart, setCart] = useState([]);
  //const [orderId, setOrderId] = useState("");

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
      if (data.message === "Your cart is empty") {
        alert("Your cart is empty");
        navigate("/customer/shop");
      }
      setCart(data);
    };
    fetchCart();
  }, []);
  async function handleorder() {
    const total = calculateTotal(cart);
    console.log(total);
    const res = await fetch(
      `http://localhost:8080/api/customer/order/${currentUser.id}/restraunt/${cart[0].restId}/orders/${total}`,
      {
        method: "POST",
        headers: {
          Authorization: "Bearer " + currentUser.jwt,
          "Content-Type": "application/json",
        },
      }
    );
    const data = await res.json();
    console.log(data);
    alert("Order placed successfully");
    //handleDelete();
    navigate(`/make-payment/${data.id}`);
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
  };
  if (cart.length === 0 || cart === null) {
    return (
      <div>
        <h1>Cart is empty</h1>
      </div>
    );
  }

  return (
    <div className="">
      <h1 className="text-center my-5 text-2xl">Cart</h1>

      <div className="flex justify-around">
        <div className="flex flex-col justify-center gap-5">
          {cart.map((item) => (
            <div className="flex bg-blue-400 p-5 gap-8" key={item.id}>
              <Base64decode base64String={item.productImg} />
              <div className="flex flex-col gap-4">
                <p>Name: {item.productName}</p>
                <p>Price: ₹{item.productPrice}</p>
                <p>Restraunt: {item.restrauntName}</p>
              </div>
              <button onClick={() => handleRemove(item.id)}>
                <MdDelete size={30} />
              </button>
            </div>
          ))}
        </div>
        <div className="flex flex-col gap-5 justify-center items-center bg-red-300 rounded-md w-[300px] h-[300px]">
          <p className="text-center text-xl">Total: ₹{calculateTotal(cart)}</p>
          <p className="text-center text-xl">Cart Items: {cart.length}</p>
          <button
            onClick={handleDelete}
            className="bg-blue-500 text-white p-4 rounded-md"
          >
            Clear Cart
          </button>
          <button
            onClick={handleorder}
            className="bg-blue-500 text-white p-4 rounded-md"
          >
            Place Order
          </button>
        </div>
      </div>
    </div>
  );
};

export default Cart;
