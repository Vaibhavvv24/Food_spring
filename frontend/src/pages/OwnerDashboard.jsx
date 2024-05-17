import React from "react";

import axios from "axios";
import { useNavigate } from "react-router-dom";
const OwnerDashboard = () => {
  const [name, setName] = React.useState("");
  const [description, setDescription] = React.useState("");
  const [image, setImage] = React.useState(null);
  const [restId, setRestId] = React.useState("");

  const navigate = useNavigate();
  const currentUser = JSON.parse(localStorage.getItem("user"));

  const handleSubmit = async (e) => {
    e.preventDefault();
    const formData = new FormData();
    formData.append("img", image);
    formData.append("name", name);
    formData.append("description", description);
    formData.append("restId", restId);

    console.log(formData);

    try {
      const response = await axios.post(
        "http://localhost:8080/api/owner/category",
        formData,
        {
          headers: {
            Authorization: "Bearer " + currentUser.jwt,
            "Content-Type": "multipart/form-data", // Ensure correct content type
          },
        }
      );
      console.log("Response:", response.data);
      alert("Category added successfully");

      navigate(`/owner/dashboard/categories/${restId}`);

      // Handle response as needed
    } catch (error) {
      console.error("Error uploading file:", error);
      // Handle error
    }
  };
  return (
    <div>
      <div>
        <h1 className="text-center text-3xl font-bold">Owner Dashboard</h1>
        <div className="flex flex-col justify-center bg-green-300 mt-10">
          <h1 className="text-center text-xl font-bold">Add Category</h1>
          <form
            onSubmit={handleSubmit}
            className="flex flex-col gap-4 items-center"
          >
            <input
              type="text"
              placeholder="Name"
              value={name}
              className="w-1/3 p-3 shadow-lg "
              onChange={(e) => setName(e.target.value)}
            />
            <input
              type="text"
              placeholder="Description"
              value={description}
              className="w-1/3 p-3 shadow-lg "
              onChange={(e) => setDescription(e.target.value)}
            />
            <input type="file" onChange={(e) => setImage(e.target.files[0])} />
            <input
              type="text"
              placeholder="Restraunt Id"
              value={restId}
              className="w-1/3 p-3 shadow-lg "
              onChange={(e) => setRestId(e.target.value)}
            />
            <button type="submit">Add Category</button>
          </form>
        </div>
      </div>
    </div>
  );
};

export default OwnerDashboard;
