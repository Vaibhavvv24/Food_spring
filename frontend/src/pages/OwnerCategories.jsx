import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import Base64decode from "../components/Base64decode";

const OwnerCategories = () => {
  const [categories, setCategories] = useState([]);
  const currentUser = JSON.parse(localStorage.getItem("user"));

  const navigate = useNavigate();

  const { id } = useParams();
  const fetchCategories = async () => {
    const res = await fetch(
      `http://localhost:8080/api/owner/categories/${id}`,
      {
        method: "GET",
        headers: {
          Authorization: "Bearer " + currentUser.jwt,
        },
      }
    );
    const data = await res.json();
    console.log(data);
    setCategories(data);
  };
  useEffect(() => {
    fetchCategories();
  }, []);

  if (categories.length == 0) {
    return (
      <div>
        <h1>Dashboard</h1>
        <h1>No categories found</h1>
      </div>
    );
  }

  const handleDelete = async (catid) => {
    const res = await fetch(
      `http://localhost:8080/api/owner/category/${catid}/delete/${id}`,
      {
        method: "DELETE",
        headers: {
          Authorization: "Bearer " + currentUser.jwt,
        },
      }
    );
    const data = await res.json();
    alert("Category Deleted successfully");
    console.log(data);
    fetchCategories();
  };
  return (
    <div className="flex flex-col items-center">
      <h1 className="text-3xl mt-10">Dashboard</h1>
      <ul className="flex flex-col items-center justify-center gap-5">
        {categories.map((category) => (
          <div
            key={category.id}
            className="flex flex-col bg-blue-400 m-3 p-10 w-[500px] h-[550px] gap-3"
          >
            <p className="text-2xl text-center" key={category.id}>
              {category.name}
            </p>
            <p className="text-xl text-center">
              Description: {category.description}
            </p>
            <p className="text-xl text-center">
              Restaurant: {category.restName}
            </p>
            <Base64decode base64String={category.returnedimage} />
            <button
              className="p-2 bg-green-200 rounded-md "
              onClick={() => navigate(`/owner/products/${id}`)}
            >
              View products
            </button>
            <button
              className="p-2 bg-green-200 rounded-md "
              onClick={() => navigate(`/owner/${category.id}/product/${id}`)}
            >
              Add product
            </button>
            <button
              className="p-2 bg-red-200 rounded-md "
              onClick={() => handleDelete(category.id)}
            >
              Delete
            </button>
          </div>
        ))}
      </ul>
    </div>
  );
};

export default OwnerCategories;
