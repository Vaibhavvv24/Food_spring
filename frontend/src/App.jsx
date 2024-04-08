import { BrowserRouter, Routes, Route } from "react-router-dom";
import "./App.css";
import Login from "./pages/Login";
import Signup from "./pages/Signup";
import Navbar from "./components/Navbar";
import AdminDashboard from "./pages/AdminDashboard";
import RestrauntAdminSide from "./pages/RestrauntAdminSide";
import RestrauntView from "./pages/RestrauntView";
import OwnerDashboard from "./pages/OwnerDashboard";
import OwnerCategories from "./pages/OwnerCategories";
import AddProduct from "./pages/AddProduct";

function App() {
  return (
    <div>
      <BrowserRouter>
        <Navbar />
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<Signup />} />
          <Route path="/admin/dashboard" element={<AdminDashboard />} />
          <Route
            path="/admin/dashboard/restraunts"
            element={<RestrauntAdminSide />}
          />
          <Route
            path="/admin/dashboard/restraunt/:id"
            element={<RestrauntView />}
          />
          <Route path="/owner/dashboard" element={<OwnerDashboard />} />
          <Route
            path="/owner/dashboard/categories/:id"
            element={<OwnerCategories />}
          />
          <Route path="/owner/:catid/product/:id" element={<AddProduct />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
