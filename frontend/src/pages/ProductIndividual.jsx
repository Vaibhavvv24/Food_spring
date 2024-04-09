import React, { useEffect, useState } from "react";

import { useParams } from "react-router-dom";
import Base64decode from "../components/Base64decode";

const ProductIndividual = () => {
  const currentUser = JSON.parse(localStorage.getItem("user"));
  const [product, setProduct] = useState({});
  const [imageString, setImageString] = useState("");

  const { id } = useParams();

  const fetchProduct = async () => {
    const res = await fetch(
      `http://localhost:8080/api/customer/product/${id}`,
      {
        headers: {
          Authorization: "Bearer " + currentUser.jwt,
        },
      }
    );
    const data = await res.json();
    console.log(data);
    setProduct(data);
    setImageString(data.returnedimg);
  };
  useEffect(() => {
    fetchProduct();
  }, []);
  const handleAddToCart = async (prodId) => {
    const res = await fetch(
      `http://localhost:8080/api/customer/products/${currentUser.id}/add/${prodId}`,
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + currentUser.jwt,
        },
        body: JSON.stringify({
          price: product.price,
          restId: product.restrauntId,
          catId: product.categoryid,
        }),
      }
    );
    const data = await res.json();
    console.log(data);
  };
  return (
    <div>
      <h1>{product.name}</h1>
      <p>{product.price}</p>
      <Base64decode base64String={imageString} />
      <button onClick={() => handleAddToCart(product.id)}>Add to Cart</button>
    </div>
  );
};

export default ProductIndividual;
