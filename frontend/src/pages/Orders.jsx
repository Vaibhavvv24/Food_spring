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
const Orders = () => {
  const [orders, setOrders] = useState([]);

  const currentUser = JSON.parse(localStorage.getItem("user"));
  useEffect(() => {
    const fetchOrders = async () => {
      const res = await fetch(
        `http://localhost:8080/api/customer/order/${currentUser.id}`,
        {
          headers: {
            Authorization: "Bearer " + currentUser.jwt,
          },
        }
      );
      const data = await res.json();
      console.log(data);
      setOrders(data);
    };
    fetchOrders();
  }, []);

  return (
    <div>
      <h1>Orders</h1>
      {orders.map((item) => (
        <div className="flex flex-col" key={item.id}>
          <p>{item.orderId}</p>
          <p>{item.orderStatus}</p>
          <p>{item.restName}</p>
          <p>{item.ownerName}</p>
          <p>{convertDate(item.orderedAt)}</p>
        </div>
      ))}
    </div>
  );
};

export default Orders;
