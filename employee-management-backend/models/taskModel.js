// models/TaskModel.js
const mongoose = require('mongoose');

// Task Schema
const taskSchema = new mongoose.Schema({
  taskId: { type: String, required: true },
  title: { type: String, required: true },
  description: { type: String, required: true },
  assignedTo: { type: String, required: true },
  startDate: { type: Date, required: true },
  endDate: { type: Date, required: true },
  status: { type: String, default: 'Pending' },  // Default status is Pending
  priority: { type: String, enum: ['High', 'Medium', 'Low'], default: 'Medium' }  // Priority levels
});

// Export the Task model
module.exports = mongoose.model('Task', taskSchema);
