const express = require('express');
const router = express.Router();
const Leave = require('../models/LeaveModel'); // leave model import

// Get all leaves
// GET route to fetch all leaves
router.get('/', async (req, res) => {
  try {
    const leaves = await Leave.find().populate('employeeId');
    res.status(200).json(leaves);
  } catch (err) {
    res.status(400).json({ message: "Error fetching leaves", error: err });
  }
});

// Add a new leave
// POST route to add a new leave
router.post('/', async (req, res) => {
  const { employeeId, leaveType, startDate, endDate } = req.body;

  // Convert the startDate and endDate to Date objects
  const newLeave = new Leave({
    employeeId,
    leaveType,
    startDate: new Date(startDate),  // Convert string to Date
    endDate: new Date(endDate),      // Convert string to Date
    status: 'pending'
  });

  try {
    const savedLeave = await newLeave.save();
    res.status(201).json({ message: "Leave added successfully", leave: savedLeave });
  } catch (err) {
    res.status(400).json({ message: "Error adding leave", error: err });
  }
});


// PUT request to update leave status by Employee ID
router.put('/:employeeId', async (req, res) => { // Correct path
  const employeeId = req.params.employeeId; // Extract employeeId from URL
  const { LeaveStatus } = req.body; // Extract LeaveStatus from request body

  try {
    // Employee ID மூலம் LeaveStatus புதுப்பிக்கிறது
    const leave = await Leave.findOneAndUpdate(
      { employeeId: employeeId }, // Match employeeId
      { LeaveStatus }, // Update LeaveStatus
      { new: true } // Return updated document
    );

    if (!leave) {
      return res.status(404).json({ message: 'Leave not found for this Employee ID' });
    }

    res.status(200).json({ message: 'Leave status updated successfully', leave });
  } catch (error) {
    console.error('Error updating leave status:', error);
    res.status(500).json({ message: 'Internal server error', error });
  }
});







//delete
router.delete('/:employeeId', async (req, res) => {
  try {
    const leave = await Leave.findOneAndDelete({ employeeId: req.params.employeeId });
    if (!leave) {
      return res.status(404).json({ message: 'Leave not found for this Employee ID' });
    }
    res.json({ message: 'Leave deleted successfully', leave });
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
});

module.exports = router;
