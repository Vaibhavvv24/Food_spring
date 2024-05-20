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

import ProductsResOwner from "./pages/ProductsResOwner";
import Profile from "./pages/Profile";
import Shop from "./pages/Shop";
import RestrauntCust from "./pages/RestrauntCust";
import ProductIndividual from "./pages/ProductIndividual";
import Cart from "./pages/Cart";
import Orders from "./pages/Orders";
import ResAdmincheck from "./pages/ResAdmincheck";
import Contact from "./pages/Contact";
import PaymentSuccess from "./pages/PaymentSuccess";
import CreatePayment from "./pages/CreatePayment";

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
          <Route path="/owner/products/:id" element={<ProductsResOwner />} />
          <Route path="/customer/profile/:id" element={<Profile />} />
          <Route path="/customer/shop" element={<Shop />} />
          <Route path="/shop/restraunt/:id" element={<RestrauntCust />} />
          <Route path="/product/:id" element={<ProductIndividual />} />
          <Route path="/cart/:id" element={<Cart />} />
          <Route path="/orders/:id" element={<Orders />} />
          <Route path="/contact" element={<Contact />} />
          <Route path="/restraunt/orders" element={<ResAdmincheck />} />
          <Route path="/payment/:orderId" element={<PaymentSuccess />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
