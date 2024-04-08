import React, { useEffect, useState } from "react";
import Base64decode from "../components/Base64decode";
import { useNavigate } from "react-router-dom";

const RestrauntAdminSide = () => {
  const [ownerId, setOwnerId] = useState("");
  const currentUser = JSON.parse(localStorage.getItem("user"));

  const [restraunt, setRestraunt] = useState({});
  const [imageString, setImageString] = useState("");

  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    console.log(ownerId);

    const res = await fetch(
      `http://localhost:8080/api/admin/restraunt/${ownerId}`,
      {
        method: "GET",
        headers: {
          Authorization: "Bearer " + currentUser.jwt,
        },
      }
    );
    const data = await res.json();
    console.log(data);

    setRestraunt(data);
    setImageString(data.img);
    console.log(imageString);
  };
  return (
    <div>
      <h1>Restraunt</h1>
      <div>
        <form onSubmit={handleSubmit}>
          <label>Enter owner id to get details</label>
          <input
            type="text"
            placeholder="Owner id"
            value={ownerId}
            onChange={(e) => setOwnerId(e.target.value)}
          />
          <button type="submit">Submit</button>
        </form>
      </div>

      <div>
        <h1>Name: {restraunt.name}</h1>
        <h1>Address: {restraunt.address}</h1>
        <h1>Owner id: {restraunt.ownerId}</h1>
        {/* <img src={restraunt.img} alt="" /> */}
        <Base64decode base64String={imageString} />
        <button
          onClick={() => navigate(`/admin/dashboard/restraunt/${restraunt.id}`)}
        >
          View Details
        </button>
      </div>
    </div>
  );
};

export default RestrauntAdminSide;
