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

  async function handleStatus(id) {
    const res = await fetch(
      `http://localhost:8080/api/owner/orders/${restraunt}/ownerId/${currentUser.id}/change`,
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + currentUser.jwt,
        },
        body: JSON.stringify({
          status: status,
          orderItemId: id,
        }),
      }
    );
    const data = await res.json();
    alert("Status changed successfully");
    window.location.reload();
    console.log(data);
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
      <div className="flex justify-center">
        <input
          type="text"
          placeholder="restraunt id"
          value={restraunt}
          className="w-[150px] h-[30px] rounded-md shadow-lg my-10 border "
          onChange={(e) => setRestraunt(e.target.value)}
        />
        <button
          type="submit"
          onClick={fetchOrders}
          className="bg-blue-500 px-2 text-white rounded-md my-10"
        >
          fetch
        </button>
      </div>
      <div className="grid grid-row-3 mx-10 gap-4">
        {orderItems.map((item) => (
          <div key={item.id} className=" flex bg-blue-200 p-10 gap-2">
            <h1>Order Id: {item.id}</h1>
            <h1>Customer Name: {item.ownerName}</h1>
            {/* <h1>Restraunt: {item.restName}</h1> */}
            <h1>Bill: ₹{item.total}</h1>
            <h1>{convertDate(item.orderedAt)}</h1>
            <div className="flex justify-center gap-3">
              <h1>Status: {item.orderStatus}</h1>
              <input
                type="text"
                placeholder="status"
                value={status}
                onChange={(e) => setStatus(e.target.value)}
              />
              <button
                className="bg-blue-500 p-1 text-white rounded-md"
                onClick={() => handleStatus(item.id)}
              >
                Change Status
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default ResAdmincheck;
