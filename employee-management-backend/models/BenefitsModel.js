const mongoose = require('mongoose');

const BenefitSchema = new mongoose.Schema({
  employeeId: {
    type: String,
    required: true,
  },
  benefitType: {
    type: String,
    required: true,
  },
  status: {
    type: String,
    default: 'Active',
  },
  startDate: {
    type: Date,
    required: true,
  },
  endDate: {
    type: Date,
  },
});

module.exports = mongoose.model('Benefit', BenefitSchema);
