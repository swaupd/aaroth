// Navbar.js
import { Component } from "react";
import "./NavbarStyles.css";
import { MenuItems } from "./MenuItems";
import { Link } from "react-router-dom";
import LoginModal from "./LoginModal"; // Import the LoginModal component
import logo from "../assets/logo.png";
class Navbar extends Component {
  state = { clicked: false, isLoginModalOpen: false };

  handleclick = () => {
    this.setState({ clicked: !this.state.clicked });
  };

  handleLoginClick = (event) => {
    event.preventDefault();
    this.setState({ isLoginModalOpen: true });
  };

  handleCloseModal = () => {
    this.setState({ isLoginModalOpen: false });
  };

  render() {
    return (
      <>
        <nav className="NavbarItems">
          <img src={logo} className="navbar-logo"/>
          <div className="menu-icons" onClick={this.handleclick}>
            <i
              className={this.state.clicked ? "fas fa-times" : "fas fa-bars"}
            ></i>
          </div>
          <ul className={this.state.clicked ? "nav-menu active" : "nav-menu"}>
            {MenuItems.map((item, index) => {
              if (item.isLogin) {
                return (
                  <li key={index}>
                    <a
                      className={item.cName}
                      href="#"
                      onClick={this.handleLoginClick}
                    >
                      {item.title}
                    </a>
                  </li>
                );
              }
              return (
                <li key={index}>
                  <Link className={item.cName} to={item.url}>
                    {item.title}
                  </Link>
                </li>
              );
            })}
          </ul>
        </nav>
        <LoginModal
          isOpen={this.state.isLoginModalOpen}
          onClose={this.handleCloseModal}
        />
      </>
    );
  }
}

export default Navbar;
