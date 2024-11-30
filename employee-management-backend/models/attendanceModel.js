const mongoose = require('mongoose');

const attendanceSchema = new mongoose.Schema({
    EmployeeID: { type: String, required: true },
    Date: { type: Date, required: true },
    CheckInTime: { type: String, required: true },
    CheckOutTime: { type: String, required: true },
    Status: { type: String, required: true } // Present/Absent/Leave
});

module.exports = mongoose.model('Attendance', attendanceSchema);
