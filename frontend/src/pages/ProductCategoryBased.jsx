import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import Base64decode from "../components/Base64decode";

const ProductCategoryBased = () => {
  const [products, setProducts] = useState([]);

  const { catid } = useParams();

  const currentUser = JSON.parse(localStorage.getItem("user"));
  const navigate = useNavigate();

  useEffect(() => {
    const fetchProducts = async () => {
      const res = await fetch(
        `http://localhost:8080/api/customer/products/${catid}/category`,
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
    fetchProducts();
  }, []);

  return (
    <div>
      <h1>Products</h1>
      <ul className="grid grid-cols-3 gap-10 ">
        {products.map((product) => (
          <li
            key={product.id}
            className="flex flex-col items-center gap-4 bg-red-200"
          >
            <Base64decode base64String={product.returnedimg} />
            <h2>{product.name}</h2>
            <p>Price: ₹{product.price}</p>
            <p>Restraunt: {product.restrauntName}</p>
            <button
              onClick={() => navigate(`/product/${product.id}`)}
              className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
            >
              View Product Details
            </button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default ProductCategoryBased;
