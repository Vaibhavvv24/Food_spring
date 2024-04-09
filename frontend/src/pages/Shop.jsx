import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import Base64decode from "../components/Base64decode";

const Shop = () => {
  const navigate = useNavigate();
  const [categories, setCategories] = useState([]);
  const [restraunts, setRestaurants] = useState([]);
  const currentUser = JSON.parse(localStorage.getItem("user"));

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
      <h1>Shop</h1>

      <div>
        <h1>Categories</h1>
        {categories.map((category) => (
          <div
            key={category.id}
            onClick={() => navigate(`/shop/${category.id}`)}
          >
            {category.name}
            <Base64decode base64String={category.returnedimage} />
          </div>
        ))}
      </div>
      <div>
        <h1>Restraunts</h1>
        {restraunts.map((restraunt) => (
          <div
            key={restraunt.id}
            onClick={() => navigate(`/shop/${restraunt.id}`)}
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
