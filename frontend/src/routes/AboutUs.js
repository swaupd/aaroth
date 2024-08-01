import Navbar from "../Components/Navbar";
import Hero from "../Components/Hero";
import AboutUsImg from "../assets/about.png";
import React from "react";
import SecondImg from "../Components/SecondImg.jpeg";
import ThirdImg from "../Components/ThirdImg.jpeg";

function AboutUs() {
  return (
    <div>
      <Navbar />
      <Hero cName="hero" videoSrc="videoplayback.mp4" url="/" />
      <div className="about-us-page">
        <h1>About Us</h1>
        <p>
          Welcome to our website! We are a team of passionate individuals who
          are dedicated to providing high-quality products and services to our
          customers.
        </p>
        <h2>Our Mission</h2>
        <p>
          Our mission is to provide innovative solutions that meet the needs of
          our customers, while also promoting sustainability and social
          responsibility.
        </p>
        <h2>Our Team</h2>
        <ul>
          <li>
            <img src={AboutUsImg} alt="Team Member 1" />
            <h3>John Doe</h3>
            <p>Founder and CEO</p>
          </li>
          <li>
            <img src={ThirdImg} alt="Team Member 2" />
            <h3>Jane Smith</h3>
            <p>CTO</p>
          </li>
          <li>
            <img src={SecondImg} alt="Team Member 3" />
            <h3>Bob Johnson</h3>
            <p>Marketing Manager</p>
          </li>
        </ul>
        <h2>Our History</h2>
        <p>
          We were founded in 2010 with the goal of providing high-quality
          products and services to our customers. Since then, we have grown to
          become a leading provider in our industry.
        </p>
      </div>
    </div>
  );
}

export default AboutUs;
