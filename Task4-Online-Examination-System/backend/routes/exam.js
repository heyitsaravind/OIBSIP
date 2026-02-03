const express = require('express');
const AssessmentQuestion = require('../models/Question');
const Student = require('../models/User');
const authenticateStudent = require('../middleware/auth');

const router = express.Router();

// Fetch assessment questions for exam
router.get('/questions', authenticateStudent, async (req, res) => {
  try {
    const { questionCount = 10, subject, difficulty } = req.query;
    
    // Build query filters
    const queryFilters = { isActive: true };
    if (subject && subject !== 'all') queryFilters.subject = subject;
    if (difficulty && difficulty !== 'all') queryFilters.difficultyLevel = difficulty;
    
    // Get randomized questions using aggregation pipeline
    const questions = await AssessmentQuestion.aggregate([
      { $match: queryFilters },
      { $sample: { size: parseInt(questionCount) } },
      {
        $project: {
          questionText: 1,
          answerChoices: {
            $map: {
              input: '$answerChoices',
              as: 'choice',
              in: {
                choiceId: '$$choice.choiceId',
                optionText: '$$choice.optionText'
                // Deliberately exclude isCorrectAnswer for security
              }
            }
          },
          subject: 1,
          difficultyLevel: 1,
          pointValue: 1
        }
      }
    ]);

    if (questions.length === 0) {
      return res.status(404).json({
        success: false,
        message: 'No questions available for the specified criteria'
      });
    }

    res.json({
      success: true,
      questions,
      totalQuestions: questions.length,
      examDurationMinutes: 30,
      instructions: [
        'Read each question carefully before selecting your answer',
        'You can navigate between questions using the navigation panel',
        'Your progress is automatically saved',
        'Submit your exam before the timer expires'
      ]
    });
  } catch (error) {
    console.error('Error fetching questions:', error);
    res.status(500).json({ 
      success: false,
      message: 'Failed to load assessment questions',
      error: error.message 
    });
  }
});

// Submit completed assessment
router.post('/submit', authenticateStudent, async (req, res) => {
  try {
    const { studentAnswers, examDurationSeconds } = req.body;
    const studentId = req.studentId;

    if (!studentAnswers || !Array.isArray(studentAnswers)) {
      return res.status(400).json({
        success: false,
        message: 'Invalid answer format provided'
      });
    }

    let correctAnswers = 0;
    let totalQuestions = 0;
    let totalPoints = 0;
    let earnedPoints = 0;

    // Evaluate each answer
    for (const answer of studentAnswers) {
      const question = await AssessmentQuestion.findById(answer.questionId);
      if (question) {
        totalQuestions++;
        totalPoints += question.pointValue;
        
        const correctChoice = question.answerChoices.find(choice => choice.isCorrectAnswer);
        if (correctChoice && correctChoice.choiceId === answer.selectedChoiceId) {
          correctAnswers++;
          earnedPoints += question.pointValue;
        }
      }
    }

    const percentageScore = totalQuestions > 0 ? Math.round((correctAnswers / totalQuestions) * 100) : 0;

    // Save exam result to student record
    const student = await Student.findById(studentId);
    const examResult = {
      assessmentId: `EXAM_${Date.now()}_${Math.random().toString(36).substr(2, 5)}`,
      achievedScore: correctAnswers,
      totalQuestions,
      completedAt: new Date(),
      durationInSeconds: examDurationSeconds || 0,
      percentageScore
    };

    student.examHistory.push(examResult);
    await student.save();

    res.json({
      success: true,
      message: 'Assessment submitted successfully',
      examResult: {
        correctAnswers,
        totalQuestions,
        percentageScore,
        earnedPoints,
        totalPoints,
        durationInSeconds: examDurationSeconds,
        grade: getLetterGrade(percentageScore),
        performance: getPerformanceLevel(percentageScore)
      }
    });
  } catch (error) {
    console.error('Error submitting assessment:', error);
    res.status(500).json({ 
      success: false,
      message: 'Failed to submit assessment',
      error: error.message 
    });
  }
});

// Get student's exam history
router.get('/history', authenticateStudent, async (req, res) => {
  try {
    const student = await Student.findById(req.studentId).select('examHistory');
    const examHistory = student.examHistory.map(exam => ({
      ...exam.toObject(),
      grade: getLetterGrade(exam.percentageScore),
      performance: getPerformanceLevel(exam.percentageScore)
    }));

    res.json({ 
      success: true,
      examHistory: examHistory.reverse() // Most recent first
    });
  } catch (error) {
    console.error('Error fetching exam history:', error);
    res.status(500).json({ 
      success: false,
      message: 'Failed to retrieve exam history',
      error: error.message 
    });
  }
});

// Helper function to determine letter grade
function getLetterGrade(percentage) {
  if (percentage >= 90) return 'A+';
  if (percentage >= 85) return 'A';
  if (percentage >= 80) return 'A-';
  if (percentage >= 75) return 'B+';
  if (percentage >= 70) return 'B';
  if (percentage >= 65) return 'B-';
  if (percentage >= 60) return 'C+';
  if (percentage >= 55) return 'C';
  if (percentage >= 50) return 'C-';
  return 'F';
}

// Helper function to determine performance level
function getPerformanceLevel(percentage) {
  if (percentage >= 90) return 'Excellent';
  if (percentage >= 80) return 'Very Good';
  if (percentage >= 70) return 'Good';
  if (percentage >= 60) return 'Satisfactory';
  if (percentage >= 50) return 'Needs Improvement';
  return 'Unsatisfactory';
}

module.exports = router;