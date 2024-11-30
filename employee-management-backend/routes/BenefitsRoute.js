const express = require('express');
const router = express.Router();
const Benefit = require('../models/BenefitsModel');

// GET: அனைத்து நலன்களையும் பெற
router.get('/', async (req, res) => {
  try {
    const benefits = await Benefit.find();
    res.status(200).json(benefits);
  } catch (err) {
    res.status(500).json({ message: 'நலன்களை பெறுவதில் பிழை', error: err });
  }
});

// POST: புதிய நலன் சேர்க்க
router.post('/', async (req, res) => {
  const { employeeId, benefitType, startDate, endDate } = req.body;

  const newBenefit = new Benefit({
    employeeId,
    benefitType,
    startDate,
    endDate,
  });

  try {
    const savedBenefit = await newBenefit.save();
    res.status(201).json({ message: 'நலன் வெற்றிகரமாக சேர்க்கப்பட்டது', benefit: savedBenefit });
  } catch (err) {
    res.status(500).json({ message: 'நலனை சேர்க்க பிழை', error: err });
  }
});

// PUT: Status-ஐ புதுப்பிக்க
router.put('/:id', async (req, res) => {
  const { status } = req.body;

  try {
    const updatedBenefit = await Benefit.findByIdAndUpdate(req.params.id, { status }, { new: true });
    if (!updatedBenefit) {
      return res.status(404).json({ message: 'நலன் காணப்படவில்லை' });
    }
    res.status(200).json({ message: 'நலனின் நிலை வெற்றிகரமாக புதுப்பிக்கப்பட்டது', benefit: updatedBenefit });
  } catch (err) {
    res.status(500).json({ message: 'நிலையை புதுப்பிக்க பிழை', error: err });
  }
});

// DELETE: நலன்களை நீக்க
router.delete('/:id', async (req, res) => {
  try {
    const deletedBenefit = await Benefit.findByIdAndDelete(req.params.id);
    if (!deletedBenefit) {
      return res.status(404).json({ message: 'நலன் காணப்படவில்லை' });
    }
    res.status(200).json({ message: 'நலன் வெற்றிகரமாக நீக்கப்பட்டது' });
  } catch (err) {
    res.status(500).json({ message: 'நலனை நீக்க பிழை', error: err });
  }
});

module.exports = router;
