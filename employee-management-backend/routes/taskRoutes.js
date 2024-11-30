// routes/taskRoutes.js
const express = require('express');
const router = express.Router();
const Task = require('../models/TaskModel');

// Create a new task
router.post('/', async (req, res) => {
  const { taskId, title, description, assignedTo, startDate, endDate, priority } = req.body;

  const newTask = new Task({
    taskId,
    title,
    description,
    assignedTo,
    startDate,
    endDate,
    priority
  });

  try {
    const savedTask = await newTask.save();
    res.status(201).json({ message: 'Task added successfully', task: savedTask });
  } catch (err) {
    res.status(400).json({ message: 'Error adding task', error: err });
  }
});

// Get all tasks
router.get('/', async (req, res) => {
  try {
    const tasks = await Task.find();
    res.status(200).json(tasks);
  } catch (err) {
    res.status(400).json({ message: 'Error fetching tasks', error: err });
  }
});

// Update task status
router.put('/:taskId', async (req, res) => {
  const taskId = req.params.taskId;
  const { status } = req.body;

  try {
    const updatedTask = await Task.findOneAndUpdate({ taskId }, { status }, { new: true });

    if (!updatedTask) {
      return res.status(404).json({ message: 'Task not found' });
    }

    res.status(200).json({ message: 'Task updated successfully', task: updatedTask });
  } catch (err) {
    res.status(400).json({ message: 'Error updating task', error: err });
  }
});

// Delete a task
router.delete('/:taskId', async (req, res) => {
  try {
    const task = await Task.findOneAndDelete({ taskId: req.params.taskId });
    if (!task) {
      return res.status(404).json({ message: 'Task not found' });
    }
    res.json({ message: 'Task deleted successfully', task });
  } catch (err) {
    res.status(500).json({ message: 'Error deleting task', error: err });
  }
});

module.exports = router;
