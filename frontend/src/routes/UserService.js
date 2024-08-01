import { useState } from "react";
import Navbar from "../Components/Navbar";
import "./UserService.css";
import graphImg from "./graph.png";
import axios from "axios";

function UserService() {
  const [selectedService, setSelectedService] = useState("");
  const [selectedGender, setSelectedGender] = useState("");
  const [selectedDiet, setSelectedDiet] = useState("");
  const [formData, setFormData] = useState({
    name: "",
    phoneNumber: "",
    dob: "",
  });
  const [reportPhoneNumber, setReportPhoneNumber] = useState("");
  const [updateData, setUpdateData] = useState({
    uuid: "",
    name: "",
    phoneNumber: "",
    diet: "",
  });
  const [loading, setLoading] = useState(false);

  const handleGenderChange = (gender) => setSelectedGender(gender);
  const handleDietChange = (diet) => {
  setSelectedDiet(diet);
  setUpdateData({ ...updateData, diet: diet });
};
  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleReportChange = (e) => {
    setReportPhoneNumber(e.target.value);
  };

  const handleUpdateChange = (e) => {
    setUpdateData({ ...updateData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async () => {
    const data = { ...formData, gender: selectedGender, diet: selectedDiet };
    try {
      const response = await axios.post("http://localhost:5000/register", data);
      alert(response.data.message);
    } catch (error) {
      alert(error.response.data.message);
    }
  };

  const handleDownloadReport = async () => {
    if (!reportPhoneNumber) {
      alert("Please enter a phone number.");
      return;
    }
    setLoading(true);
    try {
      const response = await axios.post(
        "http://localhost:5000/generate_report",
        { phoneNumber: reportPhoneNumber },
        { responseType: 'blob' } // Important for handling binary data
      );

      // Create a link element, set its href to a URL created from the blob, and simulate a click
      const url = window.URL.createObjectURL(new Blob([response.data], { type: 'application/pdf' }));
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', `${reportPhoneNumber}_latest_report.pdf`);
      document.body.appendChild(link);
      link.click();
      link.remove();
    } catch (error) {
      alert("Error generating report. Please check the phone number.");
    } finally {
      setLoading(false);
    }
  };

  const handleCustomerDataUpdate = async () => {
    const { uuid, name, phoneNumber, diet } = updateData;
    if (!uuid) {
      alert("Please enter the customer UUID.");
      return;
    }
    const data = { uuid, name, phoneNumber, diet };
    try {
      const response = await axios.post("http://localhost:5000/update_customer", data);
      alert(response.data.message);
    } catch (error) {
      alert(error.response.data.message);
    }
  };

  return (
    <>
      <Navbar />
      <div className="user-service-container">
        <div className="services-list">
          <h2>Services</h2>
          <ul>
            <li onClick={() => setSelectedService("deviceRegistration")}>
              Device Registration Request
            </li>
            <li onClick={() => setSelectedService("pinReset")}>
              Device Login Pin Reset
            </li>
            <li onClick={() => setSelectedService("dataUpdation")}>
              User Data Updation
            </li>
            <li onClick={() => setSelectedService("customerRegistration")}>
              Customer Registration
            </li>
            <li onClick={() => setSelectedService("customerDataUpdation")}>
              Customer Data Updation
            </li>
            <li onClick={() => setSelectedService("deactivation")}>
              Customer ID Deactivation Request
            </li>
            <li onClick={() => setSelectedService("generateReport")}>
              Generate and Download Customer Report
            </li>
            <li onClick={() => setSelectedService("recordedReports")}>
              Reports Recorded
            </li>
          </ul>
        </div>
        <div className="service-details">
          {selectedService === "deviceRegistration" && (
            <>
              <h2>Device Registration Request</h2>
              <input
                className="input-field"
                type="text"
                placeholder="Device Model Number"
              />
              <button className="submit-button">Submit</button>
            </>
          )}
          {selectedService === "pinReset" && (
            <>
              <h2>Device Login Pin Reset</h2>
              <input
                className="input-field"
                type="text"
                placeholder="Device Model ID"
              />
              <input className="input-field" type="text" placeholder="OTP" />
              <label>Send OTP</label>
              <button className="submit-button">Submit</button>
            </>
          )}
          {selectedService === "dataUpdation" && (
            <>
              <h2>User Data Updation</h2>
              <p>Work in progress</p>
            </>
          )}
          {selectedService === "customerRegistration" && (
            <div className="customer-form">
              <h2>Customer Registration</h2>
              <input
                className="input-field"
                type="text"
                placeholder="Name"
                name="name"
                value={formData.name}
                onChange={handleChange}
              />
              <input
                className="input-field"
                type="tel"
                placeholder="Phone Number"
                name="phoneNumber"
                value={formData.phoneNumber}
                maxLength="10"
                onChange={handleChange}
              />
              <input
                className="input-field"
                type="date"
                placeholder="Date of Birth"
                name="dob"
                value={formData.dob}
                onChange={handleChange}
              />
              <div className="gender-selection">
                <button
                  className={`gender-button ${
                    selectedGender === "Male" ? "active" : ""
                  }`}
                  onClick={() => handleGenderChange("Male")}
                >
                  Male
                </button>
                <button
                  className={`gender-button ${
                    selectedGender === "Female" ? "active" : ""
                  }`}
                  onClick={() => handleGenderChange("Female")}
                >
                  Female
                </button>
              </div>
              <div className="diet-selection">
                <button
                  className={`diet-button ${
                    selectedDiet === "Vegan" ? "active" : ""
                  }`}
                  onClick={() => handleDietChange("Vegan")}
                >
                  Vegan
                </button>
                <button
                  className={`diet-button ${
                    selectedDiet === "Vegetarian" ? "active" : ""
                  }`}
                  onClick={() => handleDietChange("Vegetarian")}
                >
                  Vegetarian
                </button>
                <button
                  className={`diet-button ${
                    selectedDiet === "Non-Vegetarian" ? "active" : ""
                  }`}
                  onClick={() => handleDietChange("Non-Vegetarian")}
                >
                  Non-Vegetarian
                </button>
              </div>
              <button className="submit-button" onClick={handleSubmit}>
                Submit
              </button>
            </div>
          )}
          {selectedService === "customerDataUpdation" && (
            <div className="customer-form">
              <h2>Customer Data Updation</h2>
              <input
                className="input-field"
                type="text"
                placeholder="Customer UUID"
                name="uuid"
                value={updateData.uuid}
                onChange={handleUpdateChange}
                maxLength="10"
              />
              <input
                className="input-field"
                type="text"
                placeholder="Name"
                name="name"
                value={updateData.name}
                onChange={handleUpdateChange}
              />
              <input
                className="input-field"
                type="tel"
                placeholder="Phone Number"
                name="phoneNumber"
                value={updateData.phoneNumber}
                onChange={handleUpdateChange}
                maxLength="10"
              />
              <div className="diet-selection">
                <button
                  className={`diet-button ${
                    selectedDiet === "Vegan" ? "active" : ""
                  }`}
                  onClick={() => handleDietChange("Vegan")}
                >
                  Vegan
                </button>
                <button
                  className={`diet-button ${
                    selectedDiet === "Vegetarian" ? "active" : ""
                  }`}
                  onClick={() => handleDietChange("Vegetarian")}
                >
                  Vegetarian
                </button>
                <button
                  className={`diet-button ${
                    selectedDiet === "Non-Vegetarian" ? "active" : ""
                  }`}
                  onClick={() => handleDietChange("Non-Vegetarian")}
                >
                  Non-Vegetarian
                </button>
                <button
                  className={`diet-button ${
                    selectedDiet === "No Change" ? "active" : ""
                  }`}
                  onClick={() => handleDietChange("No Change")}
                >
                  No Change
                </button>
              </div>
              <button className="submit-button" onClick={handleCustomerDataUpdate}>
                Submit
              </button>
            </div>
          )}
          {selectedService === "deactivation" && (
            <>
              <h2>Customer ID Deactivation</h2>
              <input
                className="input-field"
                type="text"
                placeholder="Customer UUID"
                maxLength="10"
              />
              <button className="submit-button">Submit</button>
            </>
          )}
          {selectedService === "generateReport" && (
            <>
              <h2>Generate and Download Customer Report</h2>
              <input
                className="input-field"
                type="tel"
                placeholder="Phone Number"
                maxLength="10"
                value={reportPhoneNumber}
                onChange={handleReportChange}
              />
              <button
                className="submit-button"
                onClick={handleDownloadReport}
                disabled={loading}
              >
                {loading ? 'Generating...' : 'Download Latest Report'}
              </button>
            </>
          )}
          {selectedService === "recordedReports" && (
            <>
              <h2>Reports Recorded</h2>
              <img src={graphImg} alt="Graph" className="graphImg" />
            </>
          )}
          {selectedService === "" && (
            <div className="placeholder-text">
              <h2>Please choose a service</h2>
            </div>
          )}
        </div>
      </div>
    </>
  );
}

export default UserService;
