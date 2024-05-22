import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import Base64decode from "../components/Base64decode";

const Shop = () => {
  const navigate = useNavigate();
  const [categories, setCategories] = useState([]);
  const [restraunts, setRestaurants] = useState([]);
  const currentUser = JSON.parse(localStorage.getItem("user"));
  const [searchTerm, setSearchTerm] = useState("");
  const [resName, setResName] = useState("");
  async function handleSearch(e) {
    e.preventDefault();
    const res = await fetch(
      `http://localhost:8080/api/customer/categories/search/restraunt/${searchTerm}`,
      {
        headers: {
          Authorization: "Bearer " + currentUser.jwt,
        },
      }
    );
    const data = await res.json();
    console.log(data);
    setCategories(data);
  }

  async function handleRestrauntSearch(e) {
    e.preventDefault();
    const res = await fetch(
      `http://localhost:8080/api/customer/restraunts/search/${resName}`,
      {
        headers: {
          Authorization: "Bearer " + currentUser.jwt,
        },
      }
    );
    const data = await res.json();
    console.log(data);
    if (data.length === 0) {
      alert("No restraunts found");
      window.location.reload();
    }
    setRestaurants(data);
  }
  useEffect(() => {
    const fetchCategories = async () => {
      const res = await fetch("http://localhost:8080/api/customer/categories", {
        headers: {
          Authorization: "Bearer " + currentUser.jwt,
        },
      });
      const data = await res.json();
      console.log(data);
      setCategories(data);
    };
    fetchCategories();
  }, []);
  useEffect(() => {
    const fetchRes = async () => {
      const res = await fetch("http://localhost:8080/api/customer/restraunts", {
        headers: {
          Authorization: "Bearer " + currentUser.jwt,
        },
      });
      const data = await res.json();
      console.log(data);
      setRestaurants(data);
    };
    fetchRes();
  }, []);

  return (
    <div>
      <h1 className="text-center my-5 text-2xl">Shop</h1>
      <h1 className="text-center my-5 text-2xl">Categories</h1>
      <div className="flex justify-center my-5">
        <form onSubmit={handleSearch}>
          <input
            type="text"
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            placeholder="Enter Category Name"
          />
          <button type="submit">Search</button>
        </form>
      </div>
      <div className="flex justify-center gap-4 flex-wrap">
        {categories.map((category) => (
          <div
            key={category.id}
            onClick={() => navigate(`/shop/${category.id}`)}
            className="flex flex-col gap-4 bg-blue-200 p-10"
          >
            {category.name}
            <Base64decode base64String={category.returnedimage} />
          </div>
        ))}
      </div>

      <h1 className="text-center my-5 text-2xl">Restraunts</h1>
      <div className="flex justify-center my-5">
        <form onSubmit={handleRestrauntSearch}>
          <input
            type="text"
            value={resName}
            onChange={(e) => setResName(e.target.value)}
            placeholder="Enter Restraunt Name"
          />
          <button type="submit">Search</button>
        </form>
      </div>
      <div className="flex justify-center flex-wrap gap-7">
        {restraunts.map((restraunt) => (
          <div
            key={restraunt.id}
            onClick={() => navigate(`/shop/restraunt/${restraunt.id}`)}
          >
            {restraunt.name}
            <Base64decode base64String={restraunt.img} />
          </div>
        ))}
      </div>
    </div>
  );
};

export default Shop;
