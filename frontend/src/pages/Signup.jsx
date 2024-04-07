import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";

const Signup = () => {
  const [formData, setFormData] = useState({}); //{username:"",email:"",password:""}
  const navigate = useNavigate();
  const handleChange = (e) => {
    //
    setFormData({
      ...formData, //sets formdata to the current state
      [e.target.id]: e.target.value, //sets the id of the input to the value
    });
  };
  const handleSubmit = async (e) => {
    console.log(formData);
    e.preventDefault();
    const res = await fetch("http://localhost:8080/api/auth/signup", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(formData),
    });
    const data = await res.json();
    console.log(data);
    navigate("/login");
  };
  console.log(formData);
  return (
    <div className="flex flex-col items-center bg-slate-200  ">
      <h1 className="text-3xl mt-10">Signup</h1>
      <form
        className="flex flex-col items-center justify-center gap-6 mt-1 w-[400px] h-[400px]"
        onSubmit={handleSubmit}
      >
        <input
          type="text"
          placeholder="name"
          id="name"
          className="rounded-md shadow-md p-3 mt-2 w-full"
          onChange={handleChange}
        />
        <input
          type="email"
          placeholder="email"
          id="email"
          className="rounded-md shadow-md p-3  w-full"
          onChange={handleChange}
        />
        <input
          type="password"
          id="password"
          placeholder="password"
          className="rounded-md shadow-md p-3 w-full"
          onChange={handleChange}
        />
        <input
          type="text"
          id="phoneNum"
          placeholder="phone number"
          className="rounded-md shadow-md p-3 w-full"
          onChange={handleChange}
        />

        <button
          type="submit"
          className="rounded-md shadow-md p-3 bg-red-400 w-full text-white cursor-pointer"
        >
          Signup
        </button>
      </form>
      <p>
        Already have an account? <Link to="/login">Login</Link>
      </p>
    </div>
  );
};

export default Signup;
