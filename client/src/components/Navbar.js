
// src/components/Navbar.js

import React from 'react';
import { useNavigate } from 'react-router-dom';
import Logo from './assets/logo.png'; // Adjust the path according to your file structure
import './Navbar.css';

function Navbar() {
  const navigate = useNavigate();

  return (
    <div className="navbar">
      <img src={Logo} alt="Logo" className="navbar-logo" />
      <button className="navbar-button" onClick={() => navigate('/')}>Login</button>
    </div>
  );
}

export default Navbar;
