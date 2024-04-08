import React from "react";
import { Link, useNavigate } from "react-router-dom";
import { useState } from "react";

const Login = () => {
  const [formData, setFormData] = useState({});

  const [currentUser, setCurrentUser] = useState({});
  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.id]: e.target.value,
    });
  };
  const handleSubmit = async (e) => {
    e.preventDefault();
    const res = await fetch("http://localhost:8080/api/auth/login", {
      //we set up a proxy for /api in package.json and now we can use it and post formdata to /api/auth/signup
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(formData),
    });
    const data = await res.json();
    setCurrentUser(data);
    localStorage.setItem("user", JSON.stringify(data));
    if (data.role === "ADMIN") {
      navigate("/admin/dashboard");
      window.location.reload();
    }
    if (data.role === "CUSTOMER") {
      navigate("/");
      window.location.reload();
    }
    if (data.role === "OWNER") {
      navigate("/owner/dashboard");
      window.location.reload();
    }
    console.log(data);
  };

  return (
    <div className="p-3 max-w-lg mx-auto">
      <h1 className="text-3xl text-center font-semibold my-7">Sign In</h1>
      <form onSubmit={handleSubmit} className="flex flex-col gap-4">
        <input
          type="email"
          placeholder="email"
          className="border p-3 rounded-lg"
          id="email"
          onChange={handleChange}
        />
        <input
          type="password"
          placeholder="password"
          className="border p-3 rounded-lg"
          id="password"
          onChange={handleChange}
        />

        <button className="bg-slate-700 text-white p-3 rounded-lg uppercase hover:opacity-95 disabled:opacity-80">
          Login
        </button>
      </form>
      <div className="flex gap-2 mt-5">
        <p>Dont have an account?</p>
        <Link to={"/signup"}>
          <span className="text-blue-700">Sign up</span>
        </Link>
      </div>
    </div>
  );
};
export default Login;
