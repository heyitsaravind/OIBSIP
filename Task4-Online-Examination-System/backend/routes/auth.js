const express = require('express');
const jwt = require('jsonwebtoken');
const Student = require('../models/User');
const authMiddleware = require('../middleware/auth');

const router = express.Router();

// Student registration endpoint
router.post('/signup', async (req, res) => {
  try {
    const { fullName, emailAddress, loginPassword, contactNumber } = req.body;

    // Check if student already exists
    const existingStudent = await Student.findOne({ emailAddress });
    if (existingStudent) {
      return res.status(409).json({ 
        success: false,
        message: 'A student account with this email already exists' 
      });
    }

    // Validate password strength
    if (loginPassword.length < 6) {
      return res.status(400).json({
        success: false,
        message: 'Password must be at least 6 characters long'
      });
    }

    // Create new student account
    const newStudent = new Student({ 
      fullName, 
      emailAddress, 
      loginPassword, 
      contactNumber 
    });
    
    await newStudent.save();

    // Generate authentication token
    const authToken = jwt.sign(
      { studentId: newStudent._id, email: newStudent.emailAddress },
      process.env.JWT_SECRET,
      { expiresIn: process.env.JWT_EXPIRE }
    );

    res.status(201).json({
      success: true,
      message: 'Student account created successfully',
      authToken,
      student: {
        id: newStudent._id,
        fullName: newStudent.fullName,
        emailAddress: newStudent.emailAddress,
        contactNumber: newStudent.contactNumber,
        studentRole: newStudent.studentRole
      }
    });
  } catch (error) {
    console.error('Registration error:', error);
    res.status(500).json({ 
      success: false,
      message: 'Account creation failed. Please try again.',
      error: error.message 
    });
  }
});

// Student login endpoint
router.post('/signin', async (req, res) => {
  try {
    const { emailAddress, loginPassword } = req.body;

    // Find student by email
    const student = await Student.findOne({ emailAddress });
    if (!student) {
      return res.status(401).json({ 
        success: false,
        message: 'Invalid login credentials provided' 
      });
    }

    // Verify password
    const isPasswordValid = await student.verifyPassword(loginPassword);
    if (!isPasswordValid) {
      return res.status(401).json({ 
        success: false,
        message: 'Invalid login credentials provided' 
      });
    }

    // Update last login timestamp
    await student.updateLastLogin();

    // Generate authentication token
    const authToken = jwt.sign(
      { studentId: student._id, email: student.emailAddress },
      process.env.JWT_SECRET,
      { expiresIn: process.env.JWT_EXPIRE }
    );

    res.json({
      success: true,
      message: 'Login successful',
      authToken,
      student: {
        id: student._id,
        fullName: student.fullName,
        emailAddress: student.emailAddress,
        contactNumber: student.contactNumber,
        studentRole: student.studentRole,
        lastLoginAt: student.lastLoginAt
      }
    });
  } catch (error) {
    console.error('Login error:', error);
    res.status(500).json({ 
      success: false,
      message: 'Login process failed. Please try again.',
      error: error.message 
    });
  }
});

// Student logout endpoint
router.post('/signout', authMiddleware, (req, res) => {
  res.json({ 
    success: true,
    message: 'Successfully logged out' 
  });
});

// Get current student info
router.get('/profile', authMiddleware, (req, res) => {
  res.json({
    success: true,
    student: {
      id: req.user._id,
      fullName: req.user.fullName,
      emailAddress: req.user.emailAddress,
      contactNumber: req.user.contactNumber,
      studentRole: req.user.studentRole,
      lastLoginAt: req.user.lastLoginAt
    }
  });
});

module.exports = router;