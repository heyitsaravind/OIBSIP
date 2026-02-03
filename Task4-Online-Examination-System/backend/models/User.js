const mongoose = require('mongoose');
const bcrypt = require('bcryptjs');

const studentSchema = new mongoose.Schema({
  fullName: {
    type: String,
    required: [true, 'Full name is required'],
    trim: true,
    minlength: [2, 'Name must be at least 2 characters'],
    maxlength: [50, 'Name cannot exceed 50 characters']
  },
  emailAddress: {
    type: String,
    required: [true, 'Email address is required'],
    unique: true,
    lowercase: true,
    trim: true,
    match: [/^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/, 'Please enter a valid email']
  },
  loginPassword: {
    type: String,
    required: [true, 'Password is required'],
    minlength: [6, 'Password must be at least 6 characters']
  },
  contactNumber: {
    type: String,
    trim: true,
    match: [/^[\+]?[1-9][\d]{0,15}$/, 'Please enter a valid phone number']
  },
  studentRole: {
    type: String,
    enum: ['student', 'instructor', 'admin'],
    default: 'student'
  },
  examHistory: [{
    assessmentId: {
      type: String,
      required: true
    },
    achievedScore: {
      type: Number,
      required: true
    },
    totalQuestions: {
      type: Number,
      required: true
    },
    completedAt: {
      type: Date,
      default: Date.now
    },
    durationInSeconds: {
      type: Number,
      required: true
    },
    percentageScore: {
      type: Number
    }
  }],
  accountStatus: {
    type: String,
    enum: ['active', 'suspended', 'pending'],
    default: 'active'
  },
  lastLoginAt: {
    type: Date
  }
}, {
  timestamps: true
});

// Hash password before saving
studentSchema.pre('save', async function(next) {
  // Only hash password if it has been modified
  if (!this.isModified('loginPassword')) return next();
  
  try {
    const saltRounds = 12;
    this.loginPassword = await bcrypt.hash(this.loginPassword, saltRounds);
    next();
  } catch (error) {
    next(error);
  }
});

// Method to verify password
studentSchema.methods.verifyPassword = async function(candidatePassword) {
  return await bcrypt.compare(candidatePassword, this.loginPassword);
};

// Method to update last login
studentSchema.methods.updateLastLogin = function() {
  this.lastLoginAt = new Date();
  return this.save();
};

// Virtual for getting latest exam result
studentSchema.virtual('latestExamResult').get(function() {
  if (this.examHistory.length === 0) return null;
  return this.examHistory[this.examHistory.length - 1];
});

module.exports = mongoose.model('Student', studentSchema);