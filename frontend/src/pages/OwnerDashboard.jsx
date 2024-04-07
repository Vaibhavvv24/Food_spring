import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

const Dashboard = () => {
  const [categories, setCategories] = useState([]);
  const currentUser = JSON.parse(localStorage.getItem("user"));
  const [search, setSearch] = useState("");

  const navigate = useNavigate();

  const fetchCategories = async () => {
    const res = await fetch("http://localhost:8080/api/admin/categories", {
      method: "GET",
      headers: {
        Authorization: "Bearer " + currentUser.jwt,
      },
    });
    const data = await res.json();
    setCategories(data);
  };
  useEffect(() => {
    fetchCategories();
  }, []);

  const handleSearch = async () => {
    const res = await fetch(
      `http://localhost:8080/api/admin/categories/${search}`,
      {
        method: "GET",
        headers: {
          Authorization: "Bearer " + currentUser.jwt,
        },
      }
    );
    const data = await res.json();
    setCategories(data);
  };

  if (categories.length == 0) {
    return (
      <div>
        <h1>Dashboard</h1>
        <div className="flex">
          <input
            type="text"
            onChange={(e) => setSearch(e.target.value)}
            value={search}
          />
          <button
            className="p-2 bg-green-200 rounded-md "
            onClick={handleSearch}
          >
            Search
          </button>
        </div>
        <h1>No categories found</h1>
      </div>
    );
  }
  return (
    <div className="flex flex-col items-center">
      <h1 className="text-3xl mt-10">Dashboard</h1>
      <div className="flex my-10">
        <input
          type="text"
          onChange={(e) => setSearch(e.target.value)}
          value={search}
          placeholder="Search by name"
          className="p-2 rounded-md shadow-md"
        />
        <button className="p-2 bg-green-200 rounded-md " onClick={handleSearch}>
          Search
        </button>
      </div>
      <ul className="flex flex-col items-center justify-center gap-5">
        {categories.map((category) => (
          <div
            key={category.id}
            className="flex flex-col bg-blue-400 m-3 p-10 w-[500px] h-[250px] gap-3"
          >
            <p className="text-2xl text-center" key={category.id}>
              {category.name}
            </p>
            <p className="text-xl text-center">
              Description: {category.description}
            </p>
            <button
              className="p-2 bg-green-200 rounded-md "
              onClick={() => navigate(`/admin/${category.id}/products`)}
            >
              View products
            </button>
            <button
              className="p-2 bg-green-200 rounded-md "
              onClick={() => navigate(`/admin/${category.id}/product`)}
            >
              Add product
            </button>
          </div>
        ))}
      </ul>
    </div>
  );
};

export default Dashboard;
