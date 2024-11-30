const express = require('express');
const Employee = require('../models/employeeModel');
const router = express.Router();

// Get all employees
router.get('/employees', async (req, res) => {
  try {
    const employees = await Employee.find();
    res.json(employees);
  } catch (err) {
    res.status(500).json({ message: err.message });
  }
});

// Get employee by ID
router.get('/employees/:id', async (req, res) => {
  try {
    const employee = await Employee.findById(req.params.id);
    res.json(employee);
  } catch (err) {
    res.status(404).json({ message: 'Employee not found' });
  }
});

// Add a new employee
router.post('/employees', async (req, res) => {
  console.log('Request Body:', req.body); // Log Request Body
  const employee = new Employee(req.body); // Pass the body directly
  try {
    const newEmployee = await employee.save();
    console.log('Employee Saved:', newEmployee); // Log Saved Employee
    res.status(201).json(newEmployee);
  } catch (err) {
    console.error('Error Creating Employee:', err); // Log Errors
    res.status(400).json({ message: 'Error creating employee', error: err.message });
  }
});

router.put('/employees/:employeeID', async (req, res) => {
  try {
    const updateData = {};
    Object.keys(req.body).forEach((key) => {
      if (req.body[key]) {
        updateData[key] = req.body[key];
      }
    });

    console.log('Filtered Update Data:', updateData); // Debugging

    const updatedEmployee = await Employee.findOneAndUpdate(
      { EmployeeID: req.params.employeeID }, // Match EmployeeID
      updateData, // Update only non-empty fields
      { new: true, runValidators: true } // Validate and return updated document
    );

    if (!updatedEmployee) {
      return res.status(404).json({ message: 'Employee not found' });
    }

    res.status(200).json({ message: 'Employee updated successfully', data: updatedEmployee });
  } catch (error) {
    console.error('Error Updating Employee:', error);
    res.status(500).json({ message: 'Failed to update employee', error });
  }
});



// Delete employee by EmployeeID
router.delete('/employees/:employeeID', async (req, res) => {
  try {
    const deletedEmployee = await Employee.findOneAndDelete({ EmployeeID: req.params.employeeID }); // EmployeeID பயன்படுத்தி delete செய்யவும்
    if (!deletedEmployee) {
      return res.status(404).json({ message: 'Employee not found' });
    }
    res.status(200).json({ message: 'Employee deleted successfully' });
  } catch (error) {
    console.error('Error Deleting Employee:', error);
    res.status(500).json({ message: 'Failed to delete employee', error });
  }
});


module.exports = router;
