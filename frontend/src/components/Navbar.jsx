import React from "react";
import { Link, useNavigate } from "react-router-dom";
import { FaRegUserCircle } from "react-icons/fa";
const Navbar = () => {
  const currentUser = JSON.parse(localStorage.getItem("user"));
  console.log(currentUser);

  const navigate = useNavigate();
  const handleLogout = () => {
    localStorage.removeItem("user");
    window.location.reload();
    window.location.href = "/login";
  };

  //   Ignore this comment.

  return (
    <nav className="bg-blue-200">
      <div className="flex ">
        <div className="flex">
          <Link to="/">
            <img
              src="booklogo.jpeg"
              alt="logo"
              className="w-36 h-24 mx-4 my-3"
            />
          </Link>
        </div>
        <ul className=" mx-48 flex justify-between items-center gap-14">
          <li>
            <Link to="/">Home</Link>
          </li>
          {currentUser && (
            <li>
              <Link to={`/profile/${currentUser.id}`}>
                <div className="flex gap-2">
                  <FaRegUserCircle size={30} />
                </div>
              </Link>
            </li>
          )}

          {!currentUser && (
            <li>
              <Link to="/login">Login</Link>
            </li>
          )}
          {currentUser && currentUser.role === "ADMIN" && (
            <li className="flex gap-3">
              <Link to="/admin/dashboard">Dashboard</Link>
              <Link to="/admin/category">Category</Link>
              {/* <Link to="/admin/reservations">Reservations</Link> */}
            </li>
          )}
          {currentUser && currentUser.role === "CUSTOMER" && (
            <li className="flex gap-3">
              <Link to="/customer/dashboard">Dashboard</Link>
              {/* <button
                onClick={() =>
                  navigate(`/customer/${currentUser.id}/reservations`)
                }
              > */}
              {/* Reservation
              </button> */}
              {/* <Link to="/customer/reservations">Reservations</Link> */}
              {/* <Link to="/cart">Cart</Link> */}
              <button onClick={() => navigate(`/cart/${currentUser.id}`)}>
                Cart
              </button>
            </li>
          )}
          {currentUser && (
            <li>
              <Link onClick={handleLogout}>Logout</Link>
            </li>
          )}
          <li>
            <Link to="/contact">Contact</Link>
          </li>
        </ul>
      </div>
    </nav>
  );
};

export default Navbar;
