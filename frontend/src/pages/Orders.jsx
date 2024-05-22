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
  return `Date: ${day}-${month}-${year}
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
      <h1 className="text-center my-5 text-2xl"> Your Orders</h1>
      <div className="grid grid-row-3 mx-10 gap-4">
        {orders.map((item) => (
          <div className="flex flex-row gap-4 bg-blue-200 p-10" key={item.id}>
            <p className="text-md font-semibold">
              Customer name: {item.ownerName}
            </p>
            <p className="text-md font-semibold">OrderId: {item.id}</p>
            <p className="text-md font-semibold">Status: {item.orderStatus}</p>
            <p className="text-md font-semibold">Restraunt: {item.restName}</p>

            <p className="text-md font-semibold">Bill: ₹{item.total}</p>
            <p className="text-md font-semibold">
              {convertDate(item.orderedAt)}
            </p>
            <p className="text-md font-semibold">
              Payment Status: {item.paymentStatus}
            </p>
            <p className="text-md font-semibold">
              Payment reference Id:{" "}
              {item.paymentid === null ? "NA" : item.paymentid}
            </p>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Orders;
