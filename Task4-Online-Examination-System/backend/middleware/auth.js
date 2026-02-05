const jwt = require('jsonwebtoken');
const Student = require('../models/User');

const authenticateStudent = async (req, res, next) => {
  try {
    const authHeader = req.header('Authorization');
    const token = authHeader?.replace('Bearer ', '');
    
    if (!token) {
      return res.status(401).json({ 
        success: false,
        message: 'Access denied. Authentication token required.' 
      });
    }

    const decodedToken = jwt.verify(token, process.env.JWT_SECRET);
    const student = await Student.findById(decodedToken.studentId).select('-loginPassword');
    
    if (!student) {
      return res.status(401).json({ 
        success: false,
        message: 'Invalid authentication token provided' 
      });
    }

    if (student.accountStatus !== 'active') {
      return res.status(403).json({
        success: false,
        message: 'Account access has been restricted'
      });
    }

    req.user = student;
    req.studentId = student._id;
    next();
  } catch (error) {
    if (error.name === 'JsonWebTokenError') {
      return res.status(401).json({ 
        success: false,
        message: 'Invalid authentication token format' 
      });
    }
    if (error.name === 'TokenExpiredError') {
      return res.status(401).json({ 
        success: false,
        message: 'Authentication token has expired' 
      });
    }
    
    console.error('Authentication middleware error:', error);
    res.status(500).json({ 
      success: false,
      message: 'Authentication verification failed' 
    });
  }
};

module.exports = authenticateStudent;