const mongoose = require('mongoose');

const EmployeeSchema = new mongoose.Schema({
  EmployeeID: { type: String, required: true, unique: true }, // EmployeeID ஐ unique ஆக வைக்கவும்
  Name: { type: String, required: true },
  Designation: { type: String, required: true },
  Salary: { type: Number, required: true },
  JoinDate: { type: Date, required: true },
  Contact: { type: String, required: true },
});

module.exports = mongoose.model('Employee', EmployeeSchema);
