
const express = require('express');
const mongoose = require('mongoose');
const path = require('path');
const cors = require('cors');
const User = require('./models/User'); // Adjust the path according to your file structure
const app = express();

// Middleware
app.use(express.json());
app.use(cors());

// MongoDB connection
mongoose.connect('mongodb://localhost:27017/aaroth_test', {
  useNewUrlParser: true,
  useUnifiedTopology: true,
})
.then(() => console.log('MongoDB connected'))
.catch(err => console.log(err));

// Serve static files from the React app
app.use(express.static(path.join(__dirname, 'client/build')));

// API endpoint to fetch user data by patient_id
app.get('/api/user/:patient_id', async (req, res) => {
  try {
    const user = await User.findOne({ patient_id: req.params.patient_id });
    if (!user) {
      return res.status(404).json({ message: 'User not found' });
    }
    res.json(user);
  } catch (error) {
    res.status(500).json({ message: 'Server error' });
  }
});

const generatePDF = require('./utils/pdfGenerator');

app.post('/api/generate-pdf', async (req, res) => {
  const { patientId } = req.body;

  try {
    const stream = await generatePDF(patientId);

    res.setHeader('Content-Type', 'application/pdf');
    stream.pipe(res);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});
// Handle React routing, return all requests to React app
app.get('*', (req, res) => {
  res.sendFile(path.join(__dirname, 'client/build', 'index.html'));
});

// Start the server
const PORT = process.env.PORT || 5000;
app.listen(PORT, () => {
  console.log(`Server running on port ${PORT}`);
});
