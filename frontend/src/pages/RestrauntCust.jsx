import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import Base64decode from "../components/Base64decode";

const RestrauntCust = () => {
  const { id } = useParams();
  const currentUser = JSON.parse(localStorage.getItem("user"));

  const [restCats, setRestCats] = useState([]);
  const [restaurant, setRestaurant] = useState("");

  const [searchTermProduct, setSearchTermProduct] = useState("");
  const [clicked, setClicked] = useState(false);
  const [searchTermByCat, setSearchTermByCat] = useState("");
  const [catId, setCatId] = useState("");
  const [searchTerm, setSearchTerm] = useState("");

  const [products, setProducts] = useState([]);
  const navigate = useNavigate();

  async function fetchRestCats() {
    const res = await fetch(
      `http://localhost:8080/api/customer/categories/${id}`,
      {
        headers: {
          Authorization: "Bearer " + currentUser.jwt,
        },
      }
    );
    const data = await res.json();
    console.log(data);
    setRestCats(data);
    setRestaurant(data[0].restName);
  }

  useEffect(() => {
    console.log(id);
    fetchRestCats();
  }, []);

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
    setRestCats(data);
  }
  const fetchProducts = async () => {
    const res = await fetch(
      `http://localhost:8080/api/customer/products/${id}`,
      {
        headers: {
          Authorization: "Bearer " + currentUser.jwt,
        },
      }
    );
    const data = await res.json();
    console.log(data);
    setProducts(data);
  };
  useEffect(() => {
    fetchProducts();
  }, []);

  if (restCats.length == 0) {
    return (
      <div>
        <h1>No Category Found</h1>
      </div>
    );
  }
  if (products.length == 0) {
    return (
      <div>
        <h1>No Products Found</h1>
      </div>
    );
  }
  const handleFilter = async (catid) => {
    setClicked(true);
    setCatId(catid);
    const res = await fetch(
      `http://localhost:8080/api/customer/category/${catid}/products/${id}`,
      {
        headers: {
          Authorization: "Bearer " + currentUser.jwt,
        },
      }
    );
    const data = await res.json();
    console.log(data);
    setProducts(data);
    console.log("djjdjd");
  };

  const handleProductSearch = async (e) => {
    e.preventDefault();
    const res = await fetch(
      `http://localhost:8080/api/customer/products/search/${id}/restraunt/${searchTermProduct}`,
      {
        headers: {
          Authorization: "Bearer " + currentUser.jwt,
        },
      }
    );
    const data = await res.json();
    console.log(data);
    setProducts(data);
  };
  const handleProductByCatSearch = async (e) => {
    e.preventDefault();
    const res = await fetch(
      `http://localhost:8080/api/customer/products/search/${id}/restraunt/${catId}/category/${searchTermByCat}`,
      {
        headers: {
          Authorization: "Bearer " + currentUser.jwt,
        },
      }
    );
    const data = await res.json();
    console.log(data);
    setProducts(data);
  };

  return (
    <div>
      <h1 className="text-center text-xl font-bold">
        {restaurant.toUpperCase()}
      </h1>
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

      <div className=" flex justify-center flex-wrap  gap-10">
        {restCats.map((restCat) => (
          <div
            key={restCat.id}
            onClick={() => handleFilter(restCat.id)}
            className="flex flex-col  bg-blue-400 gap-4 p-3 cursor-pointer"
          >
            <Base64decode base64String={restCat.returnedimage} />
            <h1>Category: {restCat.name}</h1>
            <h2>Description: {restCat.description}</h2>
          </div>
        ))}
      </div>

      <div className="flex flex-col justify-center">
        {!clicked && ( //if clicked is false its normal product search
          <div className="flex justify-center mt-10">
            <form onSubmit={handleProductSearch}>
              <input
                type="text"
                value={searchTermProduct}
                onChange={(e) => setSearchTermProduct(e.target.value)}
                placeholder="Enter Product Name"
                className="shadow-lg p-2"
              />
              <button
                type="submit"
                className="bg-blue-400 m-1 text-white rounded-md p-2"
              >
                Search
              </button>
            </form>
          </div>
        )}
        {clicked && ( //if clicked is true its product by category search
          <div className="flex justify-center mt-10">
            <form onSubmit={handleProductByCatSearch}>
              <input
                type="text"
                value={searchTermByCat}
                onChange={(e) => setSearchTermByCat(e.target.value)}
                placeholder="Enter Product Name for Category"
                className="shadow-lg p-2"
              />
              <button
                type="submit"
                className="bg-blue-400 m-1 text-white rounded-md p-2"
              >
                Search by Category
              </button>
            </form>
          </div>
        )}
        <div className="flex justify-center gap-4 mt-10">
          {products.map((product) => (
            <div
              key={product.id}
              className="flex flex-col justify-center items-center gap-5 bg-blue-300 p-5 "
            >
              <Base64decode base64String={product.returnedimg} />
              <h1>Name: {product.name}</h1>
              <h2>Price: ₹{product.price}</h2>
              <button
                onClick={() => navigate(`/product/${product.id}`)}
                className="bg-blue-400 p-3 text-white"
              >
                View Product
              </button>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default RestrauntCust;
