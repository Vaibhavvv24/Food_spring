import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import Base64decode from "../components/Base64decode";

const Products = () => {
  const [products, setProducts] = useState([]);

  const currentUser = JSON.parse(localStorage.getItem("user"));
  const { id } = useParams();
  const Navigate = useNavigate();

  const fetchProducts = async () => {
    const res = await fetch(
      `http://localhost:8080/api/owner/productsGet/${id}`,
      {
        method: "GET",
        headers: {
          Authorization: "Bearer " + currentUser.jwt,
        },
      }
    );
    const data = await res.json();
    setProducts(data);
    console.log(data);
  };

  useEffect(() => {
    fetchProducts();
  }, []);
  const handleSearch = async () => {
    const res = await fetch(
      `http://localhost:8080/api/admin/${id}/product/${search}`,
      {
        method: "GET",
        headers: {
          Authorization: "Bearer " + currentUser.jwt,
        },
      }
    );
    const data = await res.json();
    console.log(data);
    setProducts(data);
  };
  const handleDelete = async (id) => {
    const res = await fetch(`http://localhost:8080/api/admin/product/${id}`, {
      method: "DELETE",
      headers: {
        Authorization: "Bearer " + currentUser.jwt,
      },
    });
    const data = await res.json();
    console.log(data);
    alert("Product Deleted successfully");
    fetchProducts();
  };
  if (products.length == 0) {
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
        <h1>No products found</h1>
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
        {products.map((product) => (
          <div
            key={product.id}
            className="flex flex-col bg-blue-400 m-3 p-10 w-[500px] h-[350px] gap-4"
          >
            <p className="text-2xl text-center" key={product.id}>
              {product.name}
            </p>
            <Base64decode base64String={product.returnedimage} />
            <p className="text-xl text-center">Price: ₹{product.price}</p>
            <p className="text-xl text-center">
              Category: {product.categoryname}
            </p>
            <button
              className="p-2 bg-green-200 rounded-md "
              onClick={() => Navigate(`/admin/product/${product.id}`)}
            >
              Update
            </button>
            <button
              className="p-2 bg-red-400 rounded-md text-white"
              onClick={() => handleDelete(product.id)}
            >
              Delete
            </button>
          </div>
        ))}
      </ul>
    </div>
  );
};

export default Products;
