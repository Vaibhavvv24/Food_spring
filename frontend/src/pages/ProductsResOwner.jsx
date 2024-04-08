import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import Base64decode from "../components/Base64decode";

const ProductsResOwner = () => {
  const [prods, setProds] = useState([]);

  const currentUser = JSON.parse(localStorage.getItem("user"));
  const { id } = useParams();
  const navigate = useNavigate();
  const fetchProducts = async () => {
    console.log(id);
    const res = await fetch(
      `http://localhost:8080/api/owner/productsGet/${id}`,
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
    alert("Category Deleted successfully");
    console.log(data);
    fetchProducts();
  };
  return (
    <div>
      ProductsResOwner
      <div>
        <h1>Products</h1>
        <div className="flex flex-col items-center">
          <h1 className="text-3xl mt-10">Dashboard</h1>
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
                  <p className="text-xl text-center">Price: {product.price}</p>

                  <Base64decode base64String={product.returnedimg} />
                  <button
                    className="p-2 bg-green-200 rounded-md "
                    onClick={() => navigate(`/owner/${product.id}/products`)}
                  >
                    View products
                  </button>
                  <button
                    className="p-2 bg-green-200 rounded-md "
                    onClick={() =>
                      navigate(`/owner/${product.id}/product/${id}`)
                    }
                  >
                    Add product
                  </button>
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
    </div>
  );
};

export default ProductsResOwner;
