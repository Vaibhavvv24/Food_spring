import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import Base64decode from "../components/Base64decode";

const RestrauntCust = () => {
  const { id } = useParams();
  const currentUser = JSON.parse(localStorage.getItem("user"));

  const [restCats, setRestCats] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");

  const [products, setProducts] = useState([]);

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
        <h1>No Restraunt Found</h1>
      </div>
    );
  }
  const handleFilter = async (catid) => {
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
  return (
    <div>
      <h1>Restraunt</h1>
      <form onSubmit={handleSearch}>
        <input
          type="text"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          placeholder="Enter Category Name"
        />
        <button type="submit">Search</button>
      </form>
      {
        <div>
          {restCats.map((restCat) => (
            <div key={restCat.id} onClick={() => handleFilter(restCat.id)}>
              <h1>{restCat.name}</h1>
              <h2>{restCat.description}</h2>
              <Base64decode base64String={restCat.returnedimage} />
            </div>
          ))}
        </div>
      }
      <div>
        {products.map((product) => (
          <div key={product.id}>
            <h1>{product.name}</h1>
            <h2>{product.price}</h2>
            <Base64decode base64String={product.returnedimg} />
          </div>
        ))}
      </div>
    </div>
  );
};

export default RestrauntCust;
