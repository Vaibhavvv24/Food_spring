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
      <h1 className="text-center text-bold text-2xl">Fetch Restraunts</h1>
      <div className="flex justify-center gap-4 mt-10">
        <form onSubmit={handleSubmit} className="flex flex-col gap-5">
          <label>Enter owner id to get details</label>
          <input
            type="text"
            placeholder="Owner id"
            value={ownerId}
            onChange={(e) => setOwnerId(e.target.value)}
            className="border-2 border-black p-2 shadow-lg "
          />
          <button
            type="submit"
            className="p-2 bg-blue-300 text-white rounded-md"
          >
            Submit
          </button>
        </form>
      </div>

      <div className="flex flex-col justify-center items-center gap-4 bg-red-300 w-[300px] p-4 mt-10">
        {/* <img src={restraunt.img} alt="" /> */}
        <Base64decode base64String={imageString} />
        <h1>Name: {restraunt.name}</h1>
        <h1>Address: {restraunt.address}</h1>
        <h1>Owner name: {restraunt.ownerName}</h1>
        <button
          onClick={() => navigate(`/admin/dashboard/restraunt/${restraunt.id}`)}
          className="p-2 bg-blue-300 text-white rounded-md"
        >
          View Details
        </button>
      </div>
    </div>
  );
};

export default RestrauntAdminSide;
