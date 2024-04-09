import React, { useEffect, useState } from "react";

import { useParams } from "react-router-dom";

const ProductIndividual = () => {
  const { currentUser } = JSON.parse(localStorage.getItem("user"));
  const [product, setProduct] = useState({});

  const { id } = useParams();

  const fetchProduct = async () => {
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
    setProduct(data);
  };
  useEffect(() => {
    fetchProduct();
  }, []);
  const handleAddToCart = async (prodId) => {
    const res = await fetch(
      `http://localhost:8080/api/customer/products/${currentUser.id}/cart/${prodId}`,
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + currentUser.jwt,
        },
        body: JSON.stringify({}),
      }
    );
    const data = await res.json();
    console.log(data);
  };
  return (
    <div>
      <h1>{product.name}</h1>
      <p>{product.description}</p>
      <p>{product.price}</p>
      <button onClick={() => handleAddToCart(product.id)}>Add to Cart</button>
    </div>
  );
};

export default ProductIndividual;
