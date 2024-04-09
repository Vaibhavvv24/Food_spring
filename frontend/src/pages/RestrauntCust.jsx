import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import Base64decode from "../components/Base64decode";

const RestrauntCust = () => {
  const { id } = useParams();
  const currentUser = JSON.parse(localStorage.getItem("user"));

  const [restCats, setRestCats] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [searchTermProduct, setSearchTermProduct] = useState("");
  const [clicked, setClicked] = useState(false);
  const [searchTermByCat, setSearchTermByCat] = useState("");
  const [catId, setCatId] = useState("");

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
        {!clicked && (
          <form onSubmit={handleProductSearch}>
            <input
              type="text"
              value={searchTermProduct}
              onChange={(e) => setSearchTermProduct(e.target.value)}
              placeholder="Enter Product Name"
            />
            <button type="submit">Search</button>
          </form>
        )}
        {clicked && (
          <div>
            <form onSubmit={handleProductByCatSearch}>
              <input
                type="text"
                value={searchTermByCat}
                onChange={(e) => setSearchTermByCat(e.target.value)}
                placeholder="Enter Producty Name"
              />
              <button type="submit">Search</button>
            </form>
          </div>
        )}
        {products.map((product) => (
          <div key={product.id}>
            <h1>{product.name}</h1>
            <h2>{product.price}</h2>
            <Base64decode base64String={product.returnedimg} />
            <button onClick={() => navigate(`/product/${product.id}`)}>
              View Product
            </button>
          </div>
        ))}
      </div>
    </div>
  );
};

export default RestrauntCust;
