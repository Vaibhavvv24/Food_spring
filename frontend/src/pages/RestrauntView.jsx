import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import Base64decode from "../components/Base64decode";

const RestrauntView = () => {
  const [restaurant, setRestaurant] = useState({});
  const [imageString, setImageString] = useState("");

  const currentUser = JSON.parse(localStorage.getItem("user"));
  const { id } = useParams();
  const navigate = useNavigate();

  async function fetchRes() {
    const res = await fetch(`http://localhost:8080/api/admin/get/${id}`, {
      method: "GET",
      headers: {
        Authorization: "Bearer " + currentUser.jwt,
      },
    });
    const data = await res.json();
    setRestaurant(data);
    setImageString(data.img);
  }

  useEffect(() => {
    fetchRes();
  }, []);

  async function handleDelete() {
    const res = await fetch(
      `http://localhost:8080/api/admin/restraunt/delete/${id}`,
      {
        method: "DELETE",
        headers: {
          Authorization: "Bearer " + currentUser.jwt,
        },
      }
    );
    const data = await res.json();
    console.log(data);
    alert("Restraunt Deleted successfully");
    navigate("/admin/dashboard/restraunts");
  }

  return (
    <div className="flex flex-col items-center gap-3 justify-center">
      <h1>Restraunt Details</h1>
      <Base64decode base64String={imageString} />
      <h1>Name: {restaurant.name}</h1>
      <h1>Address: {restaurant.address}</h1>

      <h1>OwnerId: {restaurant.ownerId}</h1>
      <h1>OwnerName: {restaurant.ownerName}</h1>
      <button onClick={handleDelete} className="bg-red-500 p-3 rounded-md">
        Delete Restraunt
      </button>
    </div>
  );
};

export default RestrauntView;
