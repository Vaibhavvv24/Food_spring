import React, { useEffect, useState } from "react";

function convertDate(date) {
  const timestamp = date;
  const dateObject = new Date(timestamp);

  // Convert to IST
  dateObject.setHours(dateObject.getHours()); // Add 5 hours for IST
  dateObject.setMinutes(dateObject.getMinutes()); // Add 30 minutes for IST

  const year = dateObject.getFullYear();
  const month = dateObject.getMonth() + 1; // Note: getMonth() returns zero-based month (0 for January)
  const day = dateObject.getDate();

  const hours = dateObject.getHours();
  const minutes = dateObject.getMinutes();
  const seconds = dateObject.getSeconds();
  return `Date:${day}-${month}-${year}
   Time: ${hours}:${minutes}:${seconds}`;
}
const ResAdmincheck = () => {
  const [orderItems, setOrderItems] = useState([]);
  const currentUser = JSON.parse(localStorage.getItem("user"));

  const [restraunt, setRestraunt] = useState("");
  const [status, setStatus] = useState("");

  async function handleStatus(e) {
    e.preventDefault();
    const res = await fetch(
      `http://localhost:8080/api/owner/orders/${restraunt}/${status}/ownerId/${currentUser.id}`,
      {
        headers: {
          Authorization: "Bearer " + currentUser.jwt,
        },
      }
    );
    const data = await res.json();
    console.log(data);
    setOrderItems(data);
  }
  const fetchOrders = async (e) => {
    e.preventDefault();

    const res = await fetch(
      `http://localhost:8080/api/owner/orders/${restraunt}/ownerId/${currentUser.id}`,
      {
        headers: {
          Authorization: "Bearer " + currentUser.jwt,
        },
      }
    );
    const data = await res.json();
    if (data.message === "you can access only your orders") {
      alert("you can access only your orders");
    }
    console.log(data);
    setOrderItems(data);
  };
  if (orderItems.length === 0) {
    return (
      <div>
        <input
          type="text"
          placeholder="restraunt id"
          value={restraunt}
          onChange={(e) => setRestraunt(e.target.value)}
        />
        <button type="submit" onClick={fetchOrders}>
          Submit
        </button>
        <h1>No Orders yet</h1>
      </div>
    );
  }
  return (
    <div>
      <input
        type="text"
        placeholder="restraunt id"
        value={restraunt}
        onChange={(e) => setRestraunt(e.target.value)}
      />
      <button type="submit" onClick={fetchOrders}>
        Submit
      </button>
      {orderItems.map((item) => (
        <div key={item.id}>
          <h1>{item.orderId}</h1>
          <h1>{item.ownerName}</h1>
          <h1>{item.restName}</h1>
          <h1>{convertDate(item.orderedAt)}</h1>
          <div>
            <h1>{item.orderStatus}</h1>
            <input
              type="text"
              placeholder="status"
              value={item.orderStatus}
              onChange={(e) => setStatus(e.target.value)}
            />
            <button onClick={handleStatus}>Change Status</button>
          </div>
        </div>
      ))}
    </div>
  );
};

export default ResAdmincheck;
