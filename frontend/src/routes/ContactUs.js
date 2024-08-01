import Navbar from "../Components/Navbar";
import Hero from "../Components/Hero";
import axios from "axios";

function ContactUs() {
  const handleFormSubmit = (event) => {
    event.preventDefault();
    const name = document.getElementById("name").value;
    const email = document.getElementById("email").value;
    const message = document.getElementById("message").value;

    if (name === "" || email === "" || message === "") {
      alert("All fields are mandatory");
      return;
    }

    // Check if email is valid
    if (!/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(email)) {
      alert("Invalid email address");
      return;
    }

    // Send the form data to a server-side API using Axios
    axios
      .post("/api/contact", { name, email, message })
      .then((response) => {
        console.log(response);
        alert("Message sent successfully");
      })
      .catch((error) => {
        console.error(error);
        alert("Error sending message");
      });
  };

  return (
    <>
      <Navbar />
      {/* If you want to include a hero section */}
      <div className="contact-container">
        <div className="contact-left">
          <h1>Get in Touch</h1>
          <p>
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed sit
            amet nulla auctor, vestibulum magna sed, convallis ex.
          </p>
        </div>
        <div className="contact-right">
          <h2>Contact Us</h2>
          <form onSubmit={handleFormSubmit}>
            <label for="name">Name:</label>
            <input type="text" id="name" name="name" required />
            <br />
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required />
            <br />
            <label for="message">Message:</label>
            <textarea id="message" name="message" required />
            <br />
            <input type="submit" value="Send" />
          </form>
        </div>
      </div>
    </>
  );
}

export default ContactUs;
