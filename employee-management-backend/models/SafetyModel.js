const mongoose = require('mongoose');

const SafetySchema = new mongoose.Schema({
  incidentId: { type: String, required: true, unique: true },
  employeeId: { type: String, required: true },
  description: { type: String, required: true },
  dateReported: { type: Date, required: true },
  status: { type: String, default: 'Pending' }
});

module.exports = mongoose.model('Safety', SafetySchema);
