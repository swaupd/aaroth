import React, { useState } from "react";
import "./LoginModal.css";

const LoginModal = ({ isOpen, onClose }) => {
  const [isSignUp, setIsSignUp] = useState(false);

  if (!isOpen) return null;

  return (
    <div className="modal-overlay">
      <div className="modal-content">
        <button className="close-button" onClick={onClose}>×</button>
        {isSignUp ? (
          <div className="signup-form">
            <h2>Sign Up</h2>
            <input type="text" placeholder="Username" />
            <input type="text" placeholder="Organization" />
            <input type="password" placeholder="Password" />
            <input type="password" placeholder="Confirm Password" />
            <button>Sign Up</button>
            <a href="#" onClick={() => setIsSignUp(false)}>Already have an account? Log in</a>
          </div>
        ) : (
          <div className="login-form">
            <h2>Login</h2>
            <input type="text" placeholder="Username" />
            <input type="password" placeholder="Password" />
            <button>Login</button>
            <a href="#" onClick={() => setIsSignUp(true)}>Don't have an account? Sign Up</a>
          </div>
        )}
      </div>
    </div>
  );
};

export default LoginModal;
