
const PDFDocument = require('pdfkit');
const blobStream = require('blob-stream');
const User = require('../models/User'); // Adjust the path to your User model

const generatePDF = async (patientId) => {
  const doc = new PDFDocument();
  const stream = doc.pipe(blobStream());

  // Fetch user data from the database
  const userData = await User.findOne({ patient_id: patientId });

  if (!userData) {
    throw new Error('User not found');
  }

  // Set document properties
  doc.info.Title = 'User Data';
  doc.info.Author = 'Your App Name';

  // Add title
  doc.fontSize(20).text('User Data', { align: 'center' });
  doc.moveDown();

  // Create a function to add a table
  const addTable = (title, data) => {
    doc.fontSize(16).text(title, { underline: true });
    doc.moveDown(0.5);

    // Set up table headers
    doc.fontSize(12).text('Parameter', { continued: true, underline: true }).text('Value', { align: 'right', underline: true });
    doc.moveDown(0.5);

    // Add table rows
    data.forEach(item => {
      doc.text(item.label, { continued: true }).text(item.value, { align: 'right' });
      doc.moveDown(0.3);
    });

    doc.moveDown(1); // Add space after each table
  };

  // Personal Information Table
  const personalInfo = [
    { label: 'Patient ID', value: userData.patient_id },
    { label: 'First Name', value: userData.firstName },
    { label: 'Middle Name', value: userData.middleName || 'N/A' },
    { label: 'Last Name', value: userData.lastName || 'N/A' },
    { label: 'Age', value: userData.Age },
    { label: 'Gender', value: userData.gender },
  ];
  addTable('Personal Information', personalInfo);

  // Physical Information Table
  const physicalInfo = [
    { label: 'Height', value: userData.Height },
    { label: 'Weight', value: userData.Weight },
    { label: 'Physical Lifestyle', value: userData.PhysicalLifestyle },
  ];
  addTable('Physical Information', physicalInfo);

  // Medical Information Table
  const medicalInfo = [
    { label: 'Medical Condition', value: userData.Medical_Condition || 'N/A' },
    { label: 'Heart Rate', value: userData.Heart_Rate || 'N/A' },
    { label: 'SPO2', value: userData.SPO2 || 'N/A' },
    { label: 'Body Temperature', value: userData.Body_Temperature || 'N/A' },
    { label: 'Ambient Temperature', value: userData.Ambient_Temperature || 'N/A' },
    { label: 'Body Score', value: userData.Body_Score || 'N/A' },
  ];
  addTable('Medical Information', medicalInfo);

  // Finalize the PDF and return the stream
  doc.end();
  return stream;
};

module.exports = generatePDF;
