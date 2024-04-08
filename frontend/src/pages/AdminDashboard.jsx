import React from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const AdminDashboard = () => {
  const [name, setName] = React.useState("");
  const [address, setAddress] = React.useState("");
  const [image, setImage] = React.useState(null);
  const [ownerId, setOwnerId] = React.useState("");

  const navigate = useNavigate();
  const currentUser = JSON.parse(localStorage.getItem("user"));

  const handleSubmit = async (e) => {
    e.preventDefault();
    const formData = new FormData();
    formData.append("image", image);
    formData.append("name", name);
    formData.append("address", address);
    formData.append("ownerId", ownerId);

    console.log(formData);

    try {
      const response = await axios.post(
        "http://localhost:8080/api/admin/create",
        formData,
        {
          headers: {
            Authorization: "Bearer " + currentUser.jwt,
            "Content-Type": "multipart/form-data", // Ensure correct content type
          },
        }
      );
      console.log("Response:", response.data);
      alert("Restaurant added successfully");

      navigate("/admin/dashboard/restraunts");

      // Handle response as needed
    } catch (error) {
      console.error("Error uploading file:", error);
      // Handle error
    }
  };

  return (
    <div>
      <h1>Admin Dashboard</h1>

      <div>
        <form
          className="flex flex-col items-center justify-center gap-6 mt-1 w-[400px] h-[400px]"
          onSubmit={handleSubmit}
        >
          <h1>Add Restaurant</h1>
          <input
            type="text"
            placeholder="Name"
            value={name}
            onChange={(e) => setName(e.target.value)}
          />
          <input
            type="text"
            placeholder="Address"
            value={address}
            onChange={(e) => setAddress(e.target.value)}
          />
          <input type="file" onChange={(e) => setImage(e.target.files[0])} />
          <input
            type="text"
            placeholder="Owner Id"
            value={ownerId}
            onChange={(e) => setOwnerId(e.target.value)}
          />
          <button type="submit">Add</button>
        </form>
      </div>
    </div>
  );
};

export default AdminDashboard;
