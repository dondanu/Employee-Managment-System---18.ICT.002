const mongoose = require('mongoose');

const leaveSchema = new mongoose.Schema({
  employeeId: {
    type: String,  // ObjectId ஐ String ஆக மாற்றுங்கள்
    required: true
  },
  leaveType: {
    type: String,
    required: true
  },
  startDate: {
    type: Date,
    required: true
  },
  endDate: {
    type: Date,
    required: true
  },
  LeaveStatus: {
    type: String,
    default: 'Pending' // இந்த இடத்தில் ஏற்கனவே Default status சேர்க்கப்பட்டுள்ளது
  }
});

//const Leave = mongoose.model('Leave', leaveSchema);
const Leave = mongoose.model('Leave', leaveSchema, 'leaves'); // Collection name 'leaves'

module.exports = Leave;
