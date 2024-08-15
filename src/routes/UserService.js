import { useState } from "react";
import Navbar from "../Components/Navbar";
import "./UserService.css";
import graphImg from "./graph.png";
import axios from "axios";
import Autocomplete from 'react-google-autocomplete';

function UserService() {
  const [selectedService, setSelectedService] = useState("");
  const [selectedGender, setSelectedGender] = useState("");
  const [selectedDiet, setSelectedDiet] = useState("");
  const [formData, setFormData] = useState({
    name: "",
    phoneNumber: "",
    dob: "",
    firstName: "",
    middleName: "",
    lastName: "",
    gender: "",
    motherTongue: "",
    nationality: "",
    diet: "",
    city: "",
    district: "",
    state: "",
    streetName: "",
    areaLocation: "",
    phoneType: "",
    guardianFirstName: "",
    guardianMiddleName: "",
    guardianLastName: "",
    guardianPhoneNumber: "",
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
const handlePlaceSelected = (place) => {
  const addressComponents = place.address_components;

  let city = '';
  let district = '';
  let state = '';

  addressComponents.forEach((component) => {
    const types = component.types;

    if (types.includes('locality')) {
      city = component.long_name;
    }

    if (types.includes('administrative_area_level_2')) {
      district = component.long_name;
    }

    if (types.includes('administrative_area_level_1')) {
      state = component.long_name;
    }
  });

  setFormData((prevData) => ({
    ...prevData,
    city,
    district,
    state,
  }));
};

const handleSubmit = async () => {
  const data = { ...formData };
  try {
    const response = await axios.post("http://15.207.85.39/api/register", data);
    alert(response.data.message);
  } catch (error) {
    console.error('Error details:', error);
    alert(error.response?.data?.message || `An error occurred: ${error.message}`);
  }
};

// Generate Report
const handleDownloadReport = async () => {
  if (!reportPhoneNumber) {
    alert("Please enter a phone number.");
    return;
  }
  setLoading(true);
  try {
    const response = await axios.post(
      "http://15.207.85.39/api/generate_report",
      { phoneNumber: reportPhoneNumber },
      { responseType: 'blob' }
    );
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

// Update Customer Data
const handleCustomerDataUpdate = async () => {
  const { uuid, name, phoneNumber, diet } = updateData;
  if (!uuid) {
    alert("Please enter the customer UUID.");
    return;
  }
  const data = { uuid, name, phoneNumber, diet };
  try {
    const response = await axios.post("http://15.207.85.39/api/update_customer", data);
    alert(response.data.message);
  } catch (error) {
    alert(error.response?.data?.message || "An error occurred");
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
                placeholder="First Name"
                name="firstName"
                value={formData.firstName}
                onChange={handleChange}
                required
              />
              <input
                className="input-field"
                type="text"
                placeholder="Middle Name (if any)"
                name="middleName"
                value={formData.middleName}
                onChange={handleChange}
              />
              <input
                className="input-field"
                type="text"
                placeholder="Last Name"
                name="lastName"
                value={formData.lastName}
                onChange={handleChange}
                required
              />
              <select
                className="input-field"
                name="gender"
                value={formData.gender}
                onChange={handleChange}
                required
              >
                <option selected="true" disabled="disabled">
                  Select Gender
                </option>
                <option value="Male">Male</option>
                <option value="Female">Female</option>
                <option value="Transgender">Transgender</option>
                <option value="Non-Binary">Non-Binary</option>
              </select>
              <select
                className="input-field"
                name="motherTongue"
                value={formData.motherTongue}
                onChange={handleChange}
                required
              >
                <option selected="true" disabled="disabled">
                  Select Mother Tongue
                </option>
                <option value="Hindi">Hindi</option>
                <option value="English">English</option>
                <option value="Bengali">Bengali</option>
                <option value="Telugu">Telugu</option>
                <option value="Marathi">Marathi</option>
                <option value="Tamil">Tamil</option>
                <option value="Urdu">Urdu</option>
                <option value="Gujarati">Gujarati</option>
                <option value="Malayalam">Malayalam</option>
                <option value="Kannada">Kannada</option>
                <option value="Odia">Odia</option>
                <option value="Punjabi">Punjabi</option>
                <option value="Assamese">Assamese</option>
                <option value="Maithili">Maithili</option>
                <option value="Sanskrit">Sanskrit</option>
              </select>
              <select
                className="input-field"
                name="nationality"
                value={formData.nationality}
                onChange={handleChange}
                required
              >
                <option selected="true" disabled="disabled">
                  Select Nationality
                </option>
                <option value="India">India</option>
                <option value="Afghanistan">Afghanistan</option>
                <option value="Australia">Australia</option>
                <option value="Bangladesh">Bangladesh</option>
                <option value="Canada">Canada</option>
                <option value="China">China</option>
                <option value="France">France</option>
                <option value="Germany">Germany</option>
                <option value="Japan">Japan</option>
                <option value="Nepal">Nepal</option>
                <option value="Pakistan">Pakistan</option>
                <option value="Russia">Russia</option>
                <option value="Sri Lanka">Sri Lanka</option>
                <option value="United Kingdom">United Kingdom</option>
                <option value="United States">United States</option>
                {/* Add options for all other countries here */}
              </select>
              <select
                className="input-field"
                name="diet"
                value={formData.diet}
                onChange={handleChange}
                required
              >
                <option selected="true" disabled="disabled">
                  Select Diet Preference
                </option>
                <option value="Vegetarian">Vegetarian</option>
                <option value="Non-Vegetarian">Non-Vegetarian</option>
                <option value="Other">Other</option>
              </select>
              <label>City:</label>
              <Autocomplete
                apiKey="AIzaSyDH41jq3NNmvBCrBubBmAHiRBr44CthKsk"
                onPlaceSelected={handlePlaceSelected}
                types={['(cities)']}
                componentRestrictions={{ country: 'in' }}
                value={formData.city}
                onChange={(e) => setFormData({ ...formData, city: e.target.value })}
              />

              <label>District:</label>
              <input
                type="text"
                value={formData.district}
                readOnly
              />

              <label>State:</label>
              <input
                type="text"
                value={formData.state}
                readOnly
              />

              <input
                className="input-field"
                type="text"
                placeholder="Door No.-Street Name"
                name="streetName"
                value={formData.streetName}
                onChange={handleChange}
                required
              />
              <input
                className="input-field"
                type="text"
                placeholder="Area-Location"
                name="areaLocation"
                value={formData.areaLocation}
                onChange={handleChange}
                required
              />
              <div className="phone-selection">
                <label>
                  <input
                    type="radio"
                    name="phoneType"
                    value="Personal"
                    checked={formData.phoneType === "False"}
                    onChange={handleChange}
                  />
                  Personal Phone Number (Preferably WhatsApp)
                </label>
                <label>
                  <input
                    type="radio"
                    name="phoneType"
                    value="Non-Personal"
                    checked={formData.phoneType === "True"}
                    onChange={handleChange}
                  />
                  Parent/Guardian's Phone Number
                </label>
              </div>
              {formData.phoneType === "Non-Personal" && (
                <>
                  <input
                    className="input-field"
                    type="text"
                    placeholder="Parent/Guardian First Name"
                    name="guardianFirstName"
                    value={formData.guardianFirstName}
                    onChange={handleChange}
                    required
                  />
                  <input
                    className="input-field"
                    type="text"
                    placeholder="Parent/Guardian Middle Name"
                    name="guardianMiddleName"
                    value={formData.guardianMiddleName}
                    onChange={handleChange}
                  />
                  <input
                    className="input-field"
                    type="text"
                    placeholder="Parent/Guardian Last Name"
                    name="guardianLastName"
                    value={formData.guardianLastName}
                    onChange={handleChange}
                    required
                  />
                </>
              )}
              <input
                className="input-field"
                type="tel"
                placeholder={
                  formData.phoneType === "False"
                    ? "Personal Phone Number"
                    : "Parent/Guardian's Phone Number"
                }
                name="phoneNumber"
                value={formData.phoneNumber}
                maxLength="10"
                onChange={handleChange}
                required
              />
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
