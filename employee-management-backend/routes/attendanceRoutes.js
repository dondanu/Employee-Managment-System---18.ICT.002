const express = require('express');
const router = express.Router();
const Attendance = require('../models/attendanceModel');

// வருகை சேர்க்க (Add Attendance)
router.post('/api/attendance', async (req, res) => {
    try {
        const attendance = new Attendance(req.body);
        const savedAttendance = await attendance.save();
        res.status(201).json(savedAttendance);
    } catch (error) {
        res.status(500).json({ message: 'Error adding attendance', error });
    }
});

// அனைத்து வருகைகளையும் பெற (Get All Attendance)
router.get('/api/attendance', async (req, res) => {
    try {
        const attendanceList = await Attendance.find();
        res.status(200).json(attendanceList);
    } catch (error) {
        res.status(500).json({ message: 'Error fetching attendance', error });
    }
});

// ஒரு குறிப்பிட்ட ஊழியரின் வருகையை பெற (Get Attendance by EmployeeID)
router.get('/api/attendance/:EmployeeID', async (req, res) => {
    try {
        const attendance = await Attendance.find({ EmployeeID: req.params.EmployeeID });
        if (!attendance) {
            res.status(404).json({ message: 'Attendance not found' });
        } else {
            res.status(200).json(attendance);
        }
    } catch (error) {
        res.status(500).json({ message: 'Error fetching attendance', error });
    }
});

// வருகையை புதுப்பிக்க (Update Attendance)
router.put('/api/attendance/:id', async (req, res) => {
    try {
        const updatedAttendance = await Attendance.findByIdAndUpdate(req.params.id, req.body, { new: true });
        if (!updatedAttendance) {
            res.status(404).json({ message: 'Attendance not found' });
        } else {
            res.status(200).json(updatedAttendance);
        }
    } catch (error) {
        res.status(500).json({ message: 'Error updating attendance', error });
    }
});

// வருகையை நீக்க (Delete Attendance)
router.delete('/api/attendance/:id', async (req, res) => {
    try {
        const deletedAttendance = await Attendance.findByIdAndDelete(req.params.id);
        if (!deletedAttendance) {
            res.status(404).json({ message: 'Attendance not found' });
        } else {
            res.status(200).json({ message: 'Attendance deleted successfully' });
        }
    } catch (error) {
        res.status(500).json({ message: 'Error deleting attendance', error });
    }
});

module.exports = router;
