import React from "react";
import { Routes, Route } from "react-router-dom";
import "./styles.css";
import Home from "./routes/Home";
import AboutUs from "./routes/AboutUs";
import Product from "./routes/Product";
import UserService from "./routes/UserService";
import BCH from "./routes/BCH";
import Research from "./routes/Research";
import ContactUs from "./routes/ContactUs";
import Footer from "./Components/Footer";

function App() {
  return (
    <div className="App">
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/aboutus" element={<AboutUs />} />
        <Route path="/product" element={<Product />} />
        <Route path="/userservice" element={<UserService />} />
        <Route path="/bch" element={<BCH />} />
        <Route path="/research" element={<Research />} />
        <Route path="/contactus" element={<ContactUs />} />
      </Routes>
      <Footer />
    </div>
  );
}

export default App;
