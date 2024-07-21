// src/components/Login.js

import React from 'react';
import { useNavigate } from 'react-router-dom';
import './Login.css'; // Import custom CSS for styling

function Login() {
  const navigate = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();
    // Perform login logic here
    navigate('/about'); // Navigate to the About page
  };

  return (
    <div className="login-container">
      <form className="login-form" onSubmit={handleSubmit}>
        <input type="text" placeholder="Username or Email" className="form-input" />
        <input type="password" placeholder="Password" className="form-input" />
        <button type="submit" className="form-submit">Login</button>
        <div className="register-link">
          <p>New User? <a href="#">Register Here</a></p>
        </div>
      </form>
    </div>
  );
}

export default Login;
