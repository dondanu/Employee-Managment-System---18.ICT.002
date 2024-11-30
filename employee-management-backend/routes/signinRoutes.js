const express = require('express');
const router = express.Router();
const Signin = require('../models/signinModel');

// POST route for login
router.post('/signin', async (req, res) => {
    const { username, password } = req.body;

    // Debug: Incoming request data
    console.log('Incoming Request:', req.body);

    try {
        const user = await Signin.findOne({ username, password });

        if (user) {
            res.status(200).json({ success: true, message: 'Login successful', user });
        } else {
            res.status(401).json({ success: false, message: 'Username/Password is incorrect' });
        }
    } catch (error) {
        console.error('Server Error:', error);
        res.status(500).json({ message: 'Server error', error });
    }
});


module.exports = router;
