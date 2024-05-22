import React, { useEffect, useState } from "react";
import { useSearchParams, useParams, useNavigate } from "react-router-dom";

const Profile = () => {
  const { id } = useParams();
  const [user, setUser] = useState({});

  const [formData, setFormData] = useState({});
  const navigate = useNavigate();

  const currentUser = JSON.parse(localStorage.getItem("user"));

  async function getUser() {
    const response = await fetch(`http://localhost:8080/api/customer/${id}`, {
      method: "GET",
      headers: {
        Authorization: "Bearer " + currentUser.jwt,
      },
    });
    const data = await response.json();
    setUser(data);
    console.log(data);
  }
  useEffect(() => {
    getUser();
  }, []);
  const handleChangeupdate = (e) => {
    setFormData({ ...formData, [e.target.id]: e.target.value });
  };
  const handleUpdate = async (e) => {
    e.preventDefault();
    try {
      const res = await fetch(
        `http://localhost:8080/api/customer/update/${currentUser.id}`,
        {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
            Authorization: "Bearer " + currentUser.jwt,
          },
          body: JSON.stringify(formData),
        }
      );
      const data = await res.json();
      console.log(data);
      setFormData(data);
      alert("Profile Updated Successfully redirecting to login page");
      navigate("/login");
    } catch (error) {
      console.log(error);
    }
  };

  const handleDelete = async (e) => {
    e.preventDefault();
    try {
      const res = await fetch(
        `http://localhost:8080/api/customer/delete/${currentUser.id}`,
        {
          method: "DELETE",
          headers: {
            "Content-Type": "application/json",
            Authorization: "Bearer " + currentUser.jwt,
          },
        }
      );
      const data = await res.json();
      console.log(data);
      setFormData({});
      localStorage.removeItem("user");

      navigate("/signup");
    } catch (error) {
      console.log(error);
    }
  };
  const handleLogout = async (e) => {
    e.preventDefault();
    try {
      const res = await fetch(`/api/auth/logout`, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
        },
      });
      const data = await res.json();
      setFormData({});
      localStorage.removeItem("user");
      localStorage.removeItem("authToken");

      //setEmail("");
      //setPassword("");
      //setUsername("");
      console.log(data);
      navigate("/login");
    } catch (error) {
      console.log(error);
    }
  };
  return (
    <>
      <div className="text-center my-10 ">
        <h1 className="text-2xl my-10">Your Profile</h1>
        <div className="flex justify-center items-center ">
          <form className="flex flex-col gap-2 w-1/2 ">
            <input
              type="text"
              placeholder="name"
              id="name"
              defaultValue={user.name}
              name="name"
              //value={username}
              className="rounded-md shadow-md p-3  w-full"
              onChange={handleChangeupdate}
            />
            <input
              type="email"
              placeholder="email"
              id="email"
              name="email"
              defaultValue={user.email}
              className="rounded-md shadow-md p-3  w-full"
              onChange={handleChangeupdate}
            />
            <input
              type="text"
              id="phoneNum"
              placeholder="Phone number"
              name="phoneNum"
              defaultValue={user.phoneNum}
              className="rounded-md shadow-md p-3 w-full"
              onChange={handleChangeupdate}
            />
            <button
              type="submit"
              className="rounded-md shadow-md p-3 bg-red-400 w-full text-white cursor-pointer"
              onClick={handleUpdate}
            >
              Update
            </button>
            <div className="flex justify-between">
              <div className="">
                <button className="text-red-400" onClick={handleDelete}>
                  Delete
                </button>
              </div>
              <div>
                {/* <button className="text-red-400" onClick={handleLogout}>
                  Logout
                </button> */}
              </div>
            </div>
          </form>
        </div>
      </div>
    </>
  );
};

export default Profile;
