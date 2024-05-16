import React, { useEffect, useState } from "react";

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
      {/* {orders.map((item) => (
        <div className="flex flex-col" key={item.id}>
          <p>{item.restrauntName}</p>
          <p>{item.}</p>
          <p>{item.restrauntName}</p>
          <Base64decode base64String={item.productImg} />
          <button onClick={() => handleRemove(item.id)}>Remove</button>
        </div>
      ))} */}
    </div>
  );
};

export default Orders;
