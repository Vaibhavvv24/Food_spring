import React, { useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";

const AddProduct = () => {
  const [name, setName] = useState("");
  const [price, setPrice] = useState(0);
  const [image, setImage] = useState(null);
  const [restId, setRestId] = useState("");
  const [catId, setCatId] = useState("");

  const currentUser = JSON.parse(localStorage.getItem("user"));

  const { catid, id } = useParams();
  console.log(catid, id);

  const handleSubmit = async (e) => {
    e.preventDefault();
    const formData = new FormData();

    formData.append("productName", name);
    formData.append("img", image);
    formData.append("price", price);
    formData.append("restId", restId);
    formData.append("catId", catId);

    console.log(formData);

    try {
      const response = await axios.post(
        "http://localhost:8080/api/owner/product",
        formData,
        {
          headers: {
            Authorization: "Bearer " + currentUser.jwt,
            "Content-Type": "multipart/form-data", // Ensure correct content type
          },
        }
      );
      console.log("Response:", response.data);
      alert("Product added successfully");
      // Handle response as needed
    } catch (error) {
      console.error("Error uploading file:", error);
      // Handle error
    }
  };
  return (
    <div>
      <h1 className="text-3xl text-center mt-10">Add Product</h1>
      <div className="flex justify-center items-center mt-5">
        <form className="flex flex-col w-1/2 gap-4" onSubmit={handleSubmit}>
          <div className="flex gap-6">
            <label htmlFor="title" className="">
              Name
            </label>
            <input
              type="text"
              id="name"
              placeholder="Name"
              onChange={(e) => setName(e.target.value)}
              required
              value={name}
              className="p-2 shadow-md border-solid rounded-md w-full"
            />
          </div>
          <div className="flex gap-6">
            <label htmlFor="description">Price</label>
            <input
              type="number"
              id="price"
              placeholder="Price"
              onChange={(e) => setPrice(e.target.value)}
              required
              value={price}
              className="p-2 shadow-md border-solid rounded-md w-full"
            />
          </div>
          <div className="flex gap-5 ">
            <label htmlFor="image">Image</label>

            <input
              type="file"
              id="file-upload"
              onChange={(e) => setImage(e.target.files[0])}
              name="myFile"
              required
              //value={formData.image}
              className="p-2 shadow-md border-solid rounded-md w-full"
            />
          </div>
          <div className="flex gap-6">
            <label htmlFor="description">Restruant Id(Take from url)</label>
            <input
              type="text"
              id="restId"
              placeholder="Restruant Id"
              onChange={(e) => setRestId(e.target.value)}
              required
              value={restId}
              className="p-2 shadow-md border-solid rounded-md w-full"
            />
          </div>
          <div className="flex gap-6">
            <label htmlFor="description">Category Id(Take from url)</label>
            <input
              type="text"
              id="catId"
              placeholder="Category Id"
              onChange={(e) => setCatId(e.target.value)}
              required
              value={catId}
              className="p-2 shadow-md border-solid rounded-md w-full"
            />
          </div>

          <button type="submit" className="bg-green-100 p-3 rounded-md">
            Add Product
          </button>
        </form>
      </div>
    </div>
  );
};

export default AddProduct;
