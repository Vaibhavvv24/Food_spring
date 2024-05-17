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
    alert("Product added to cart");
    console.log(data);
  };
  return (
    <div className="flex ml-20 flex-col justify-center items-center h-[400px] mt-10 w-[300px] bg-blue-300">
      <Base64decode base64String={imageString} />
      <h1>{product.name}</h1>
      <p>{product.price}</p>
      <p>{product.categoryname}</p>
      <p>{product.restrauntName}</p>
      <button
        onClick={() => handleAddToCart(product.id)}
        className="bg-red-500 p-4 text-white rounded-md"
      >
        Add to Cart
      </button>
    </div>
  );
};

export default ProductIndividual;
