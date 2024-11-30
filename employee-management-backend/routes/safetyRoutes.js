const express = require('express');
const router = express.Router();
const Safety = require('../models/SafetyModel');

// GET: Fetch all safety incidents
router.get('/', async (req, res) => {
  try {
    const incidents = await Safety.find();
    res.status(200).json(incidents);
  } catch (err) {
    res.status(500).json({ message: 'Error fetching incidents', error: err });
  }
});

// POST: Add a new safety incident
router.post('/', async (req, res) => {
  const { incidentId, employeeId, description, dateReported, status } = req.body;

  const newIncident = new Safety({
    incidentId,
    employeeId,
    description,
    dateReported: new Date(dateReported),
    status
  });

  try {
    const savedIncident = await newIncident.save();
    res.status(201).json({ message: 'Incident added successfully', incident: savedIncident });
  } catch (err) {
    res.status(500).json({ message: 'Error adding incident', error: err });
  }
});

// PUT: Update safety incident status
router.put('/:incidentId', async (req, res) => {
  const { status } = req.body;

  try {
    const updatedIncident = await Safety.findOneAndUpdate(
      { incidentId: req.params.incidentId },
      { status },
      { new: true }
    );
    if (!updatedIncident) {
      return res.status(404).json({ message: 'Incident not found' });
    }
    res.status(200).json({ message: 'Incident updated successfully', incident: updatedIncident });
  } catch (err) {
    res.status(500).json({ message: 'Error updating incident', error: err });
  }
});

// DELETE: Remove a safety incident
router.delete('/:incidentId', async (req, res) => {
  try {
    const deletedIncident = await Safety.findOneAndDelete({ incidentId: req.params.incidentId });
    if (!deletedIncident) {
      return res.status(404).json({ message: 'Incident not found' });
    }
    res.status(200).json({ message: 'Incident deleted successfully', incident: deletedIncident });
  } catch (err) {
    res.status(500).json({ message: 'Error deleting incident', error: err });
  }
});

module.exports = router;
