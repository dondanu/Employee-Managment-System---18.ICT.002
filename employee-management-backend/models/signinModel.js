const mongoose = require('mongoose');

const signinSchema = new mongoose.Schema({
    username: { type: String, required: true },
    password: { type: String, required: true }
});

module.exports = mongoose.model('Signin', signinSchema);
