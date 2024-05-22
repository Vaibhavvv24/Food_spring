import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import Base64decode from "../components/Base64decode";

const ProductsResOwner = () => {
  const [prods, setProds] = useState([]);

  const currentUser = JSON.parse(localStorage.getItem("user"));
  const { id } = useParams();
  const { catid } = useParams();
  const navigate = useNavigate();
  const fetchProducts = async () => {
    console.log(id);
    const res = await fetch(
      `http://localhost:8080/api/owner/productsGet/${catid}/category/${id}`,
      {
        headers: {
          Authorization: "Bearer " + currentUser.jwt,
        },
      }
    );
    const data = await res.json();

    console.log(data[0]);
    console.log(data);
    setProds(data);
    console.log(prods); //[]
  };
  console.log(prods);
  useEffect(() => {
    fetchProducts();
  }, []);

  const handleDelete = async (prodid) => {
    const res = await fetch(
      `http://localhost:8080/api/owner/${id}/products/delete/${prodid}`,
      {
        method: "DELETE",
        headers: {
          Authorization: "Bearer " + currentUser.jwt,
        },
      }
    );
    const data = await res.json();
    alert("Product Deleted successfully");
    console.log(data);
    fetchProducts();
  };
  return (
    <div>
      <h1 className="text-3xl text-center">Products</h1>
      <div className="flex flex-col items-center">
        <ul className="flex flex-col items-center justify-center gap-5">
          {prods &&
            prods.map((product) => (
              <div
                key={product.id}
                className="flex flex-col bg-blue-400 m-3 p-10 w-[500px] h-[550px] gap-3"
              >
                <p className="text-2xl text-center" key={product.id}>
                  {product.name}
                </p>
                <p className="text-2xl text-center">
                  Category: {product.categoryname}
                </p>
                <p className="text-xl text-center">Price: ₹{product.price}</p>

                <Base64decode base64String={product.returnedimg} />

                <button
                  className="p-2 bg-red-200 rounded-md "
                  onClick={() => handleDelete(product.id)}
                >
                  Delete
                </button>
              </div>
            ))}
        </ul>
      </div>
    </div>
  );
};

export default ProductsResOwner;
