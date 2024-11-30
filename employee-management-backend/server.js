// Importing required packages
const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');

const dotenv = require('dotenv');

// Load environment variables from .env file
dotenv.config();  // Ensure dotenv is loaded at the top of your file

// Verify if the environment variable is loaded correctly
console.log('MongoDB URI:', process.env.MONGO_URI);  // Log the MongoDB URI to ensure it's loaded

const app = express();
const port = process.env.PORT || 5000;

// Middleware to parse JSON data from incoming requests
app.use(express.json());  // This line is crucial to handle request body as JSON

// Enable CORS (Cross-Origin Resource Sharing) to allow requests from different domains
app.use(cors()); 


// Import routes
const employeeRoutes = require('./routes/employeeRoutes');
const benefitsRoute = require('./routes/BenefitsRoute');
const taskRoutes = require('./routes/taskRoutes');
const attendanceRoutes = require('./routes/attendanceRoutes');
const leaveRoutes = require('./routes/leaveRoutes');
const salaryRoutes = require('./routes/salaryRoutes');
const safetyRoutes = require('./routes/safetyRoutes'); // Import the Safety Routes
const signinRoutes = require('./routes/signinRoutes'); // Import signin routes

// ரோட்டுகள் இணைத்தல்
app.use(salaryRoutes);
app.use('/api/employees', employeeRoutes);
app.use('/api/benefits', benefitsRoute);
app.use('/api/safety', safetyRoutes); // Register the safety routes
app.use(attendanceRoutes);


// Use routes
app.use('/api', employeeRoutes); //
app.use('/api', attendanceRoutes);
app.use('/api', leaveRoutes);
app.use('/api', taskRoutes);


app.use('/api/leaves', leaveRoutes);

app.use('/api/tasks', taskRoutes);  // For task management
app.use('/api/attendances', attendanceRoutes);  // For attendance management
app.use('/api/leavemanagements', leaveRoutes);  // For leave management
app.use('/api/auth', signinRoutes); // All signin-related routes will start with /api/auth

// MongoDB connection
mongoose.connect(process.env.MONGO_URI, { useNewUrlParser: true, useUnifiedTopology: true })
  .then(() => console.log('MongoDB connected'))
  .catch((err) => console.log('MongoDB connection error:', err));


// Test API route to check if server is running
app.get('/', (req, res) => {
  res.send('Employee Management System Backend is Running');
});


// Start the server
app.listen(port, () => {
  console.log(`Server running on port ${port}`);
});
