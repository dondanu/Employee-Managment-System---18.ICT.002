const express = require('express');
const router = express.Router();
const Salary = require('../models/salaryModel');

// 1. சம்பள விவரங்களை சேர்க்க (Add Salary)
router.post('/api/salary', async (req, res) => {
    try {
        const salary = new Salary(req.body);
        const savedSalary = await salary.save();
        res.status(201).json(savedSalary);
    } catch (error) {
        res.status(500).json({ message: 'Error adding salary', error });
    }
});

// 2. அனைத்து சம்பள விவரங்களையும் பெற (Get All Salaries)
router.get('/api/salary', async (req, res) => {
    try {
        const salaries = await Salary.find();
        res.status(200).json(salaries);
    } catch (error) {
        res.status(500).json({ message: 'Error fetching salaries', error });
    }
});

// 3. ஒரு ஊழியரின் சம்பள விவரங்களை EmployeeID மூலம் பெற (Get Salary by EmployeeID)
router.get('/api/salary/:EmployeeID', async (req, res) => {
    try {
        const salary = await Salary.findOne({ EmployeeID: req.params.EmployeeID });
        if (!salary) {
            res.status(404).json({ message: 'Salary record not found' });
        } else {
            res.status(200).json(salary);
        }
    } catch (error) {
        res.status(500).json({ message: 'Error fetching salary', error });
    }
});

// 4. சம்பள விவரங்களை புதுப்பிக்க (Update Salary)
router.put('/api/salary/:EmployeeID', async (req, res) => {
    try {
        const updatedSalary = await Salary.findOneAndUpdate(
            { EmployeeID: req.params.EmployeeID },
            req.body,
            { new: true }
        );
        if (!updatedSalary) {
            res.status(404).json({ message: 'Salary record not found' });
        } else {
            res.status(200).json(updatedSalary);
        }
    } catch (error) {
        res.status(500).json({ message: 'Error updating salary', error });
    }
});

// 5. சிறந்த செயல்திறனுக்கு 10% சம்பள உயர்வை வைக்க (Apply Bonus)
router.put('/api/salary/bonus/:EmployeeID', async (req, res) => {
    try {
        const salary = await Salary.findOne({ EmployeeID: req.params.EmployeeID });
        if (!salary) {
            res.status(404).json({ message: 'Salary record not found' });
        } else {
            salary.Bonuses += salary.BaseSalary * 0.10; // 10% Bonus
            salary.FinalSalary = salary.BaseSalary + salary.Bonuses - salary.Deductions;
            const updatedSalary = await salary.save();
            res.status(200).json(updatedSalary);
        }
    } catch (error) {
        res.status(500).json({ message: 'Error applying bonus', error });
    }
});

// 6. சம்பள விவரங்களை அழிக்க (Delete Salary)
router.delete('/api/salary/:EmployeeID', async (req, res) => {
    try {
        const deletedSalary = await Salary.findOneAndDelete({ EmployeeID: req.params.EmployeeID });
        if (!deletedSalary) {
            res.status(404).json({ message: 'Salary record not found' });
        } else {
            res.status(200).json({ message: 'Salary deleted successfully' });
        }
    } catch (error) {
        res.status(500).json({ message: 'Error deleting salary', error });
    }
});



module.exports = router;
