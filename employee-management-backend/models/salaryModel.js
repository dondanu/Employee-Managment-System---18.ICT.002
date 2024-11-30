const mongoose = require('mongoose');

// Salary Schema
const salarySchema = new mongoose.Schema({
    EmployeeID: { type: String, required: true },
    BaseSalary: { type: Number, required: true },
    Bonuses: { type: Number, default: 0 },
    Deductions: { type: Number, default: 0 },
    FinalSalary: { type: Number, required: true }
});

module.exports = mongoose.model('Salary', salarySchema);
