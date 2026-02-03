const mongoose = require('mongoose');

const assessmentQuestionSchema = new mongoose.Schema({
  questionText: {
    type: String,
    required: [true, 'Question text is required'],
    trim: true,
    minlength: [10, 'Question must be at least 10 characters long']
  },
  answerChoices: [{
    optionText: {
      type: String,
      required: true,
      trim: true
    },
    isCorrectAnswer: {
      type: Boolean,
      default: false
    },
    choiceId: {
      type: String,
      default: () => Math.random().toString(36).substr(2, 9)
    }
  }],
  subject: {
    type: String,
    default: 'General Knowledge',
    trim: true
  },
  difficultyLevel: {
    type: String,
    enum: ['beginner', 'intermediate', 'advanced'],
    default: 'intermediate'
  },
  pointValue: {
    type: Number,
    default: 1,
    min: [1, 'Point value must be at least 1']
  },
  createdBy: {
    type: String,
    default: 'System Administrator'
  },
  isActive: {
    type: Boolean,
    default: true
  }
}, {
  timestamps: true,
  toJSON: { virtuals: true },
  toObject: { virtuals: true }
});

// Virtual for getting correct answer
assessmentQuestionSchema.virtual('correctAnswer').get(function() {
  return this.answerChoices.find(choice => choice.isCorrectAnswer);
});

// Index for better query performance
assessmentQuestionSchema.index({ subject: 1, difficultyLevel: 1 });
assessmentQuestionSchema.index({ isActive: 1 });

module.exports = mongoose.model('AssessmentQuestion', assessmentQuestionSchema);