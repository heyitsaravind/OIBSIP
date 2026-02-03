import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const Exam = () => {
  const [questions, setQuestions] = useState([]);
  const [currentQuestion, setCurrentQuestion] = useState(0);
  const [answers, setAnswers] = useState({});
  const [timeLeft, setTimeLeft] = useState(1800); // 30 minutes in seconds
  const [loading, setLoading] = useState(true);
  const [submitting, setSubmitting] = useState(false);
  const [examStarted, setExamStarted] = useState(false);
  
  const navigate = useNavigate();

  // Load questions
  useEffect(() => {
    const loadQuestions = async () => {
      try {
        const response = await axios.get('/api/exam/questions?limit=10');
        setQuestions(response.data.questions);
        setTimeLeft(response.data.timeLimit * 60); // Convert minutes to seconds
        setLoading(false);
      } catch (error) {
        console.error('Error loading questions:', error);
        alert('Failed to load exam questions. Please try again.');
        navigate('/');
      }
    };

    loadQuestions();
  }, [navigate]);

  // Timer countdown
  useEffect(() => {
    if (!examStarted || timeLeft <= 0) return;

    const timer = setInterval(() => {
      setTimeLeft(prev => {
        if (prev <= 1) {
          handleSubmit(true); // Auto submit when time runs out
          return 0;
        }
        return prev - 1;
      });
    }, 1000);

    return () => clearInterval(timer);
  }, [examStarted, timeLeft]);

  const startExam = () => {
    setExamStarted(true);
  };

  const handleAnswerSelect = (questionId, optionId) => {
    setAnswers(prev => ({
      ...prev,
      [questionId]: optionId
    }));
  };

  const handleSubmit = async (autoSubmit = false) => {
    if (submitting) return;
    
    if (!autoSubmit) {
      const confirmSubmit = window.confirm('Are you sure you want to submit your exam? You cannot change your answers after submission.');
      if (!confirmSubmit) return;
    }

    setSubmitting(true);

    try {
      const examAnswers = questions.map(question => ({
        questionId: question._id,
        selectedOption: answers[question._id] || null
      }));

      const timeTaken = examStarted ? (1800 - timeLeft) : 0; // Time taken in seconds

      await axios.post('/api/exam/submit', {
        answers: examAnswers,
        timeTaken
      });

      navigate('/results');
    } catch (error) {
      console.error('Error submitting exam:', error);
      alert('Failed to submit exam. Please try again.');
      setSubmitting(false);
    }
  };

  const formatTime = (seconds) => {
    const minutes = Math.floor(seconds / 60);
    const remainingSeconds = seconds % 60;
    return `${minutes.toString().padStart(2, '0')}:${remainingSeconds.toString().padStart(2, '0')}`;
  };

  const getAnsweredCount = () => {
    return Object.keys(answers).length;
  };

  if (loading) {
    return (
      <div className="container">
        <div className="loading">Loading exam questions...</div>
      </div>
    );
  }

  if (!examStarted) {
    return (
      <div className="container">
        <div className="card" style={{ textAlign: 'center', maxWidth: '600px', margin: '50px auto' }}>
          <h2>Ready to Start Your Exam?</h2>
          <div style={{ margin: '30px 0' }}>
            <p style={{ fontSize: '18px', marginBottom: '20px' }}>
              You are about to start an exam with <strong>{questions.length} questions</strong>.
            </p>
            <p style={{ fontSize: '16px', color: '#666', marginBottom: '20px' }}>
              Time limit: <strong>30 minutes</strong>
            </p>
            <div className="alert alert-warning">
              <strong>Important:</strong> Once you start the exam, the timer will begin and cannot be paused. 
              Make sure you have a stable internet connection.
            </div>
          </div>
          <button 
            onClick={startExam}
            className="btn btn-primary"
            style={{ fontSize: '18px', padding: '15px 30px' }}
          >
            Start Exam
          </button>
        </div>
      </div>
    );
  }

  const question = questions[currentQuestion];

  return (
    <div className="container">
      {/* Timer */}
      <div className="timer">
        Time Left: {formatTime(timeLeft)}
      </div>

      {/* Question */}
      <div className="question-card">
        <div className="question-number">
          Question {currentQuestion + 1} of {questions.length}
        </div>
        
        <div className="question-text">
          {question.question}
        </div>

        <div className="options">
          {question.options.map((option, index) => (
            <div
              key={option._id}
              className={`option ${answers[question._id] === option._id ? 'selected' : ''}`}
              onClick={() => handleAnswerSelect(question._id, option._id)}
            >
              <input
                type="radio"
                name={`question-${question._id}`}
                value={option._id}
                checked={answers[question._id] === option._id}
                onChange={() => handleAnswerSelect(question._id, option._id)}
              />
              <span>{option.text}</span>
            </div>
          ))}
        </div>
      </div>

      {/* Navigation Controls */}
      <div className="exam-controls">
        <div>
          <button
            onClick={() => setCurrentQuestion(prev => Math.max(0, prev - 1))}
            disabled={currentQuestion === 0}
            className="btn btn-secondary"
            style={{ marginRight: '10px' }}
          >
            Previous
          </button>
          
          <button
            onClick={() => setCurrentQuestion(prev => Math.min(questions.length - 1, prev + 1))}
            disabled={currentQuestion === questions.length - 1}
            className="btn btn-secondary"
          >
            Next
          </button>
        </div>

        <div className="progress-info">
          Answered: {getAnsweredCount()} / {questions.length}
        </div>

        <button
          onClick={() => handleSubmit(false)}
          disabled={submitting}
          className="btn btn-success"
        >
          {submitting ? 'Submitting...' : 'Submit Exam'}
        </button>
      </div>

      {/* Question Navigation */}
      <div className="card" style={{ marginTop: '20px' }}>
        <h4>Question Navigation</h4>
        <div style={{ display: 'flex', flexWrap: 'wrap', gap: '10px', marginTop: '15px' }}>
          {questions.map((_, index) => (
            <button
              key={index}
              onClick={() => setCurrentQuestion(index)}
              className={`btn ${
                index === currentQuestion 
                  ? 'btn-primary' 
                  : answers[questions[index]._id] 
                    ? 'btn-success' 
                    : 'btn-secondary'
              }`}
              style={{ minWidth: '40px', padding: '8px' }}
            >
              {index + 1}
            </button>
          ))}
        </div>
        <div style={{ marginTop: '15px', fontSize: '14px', color: '#666' }}>
          <span style={{ color: '#007bff' }}>■</span> Current Question &nbsp;
          <span style={{ color: '#28a745' }}>■</span> Answered &nbsp;
          <span style={{ color: '#6c757d' }}>■</span> Not Answered
        </div>
      </div>
    </div>
  );
};

export default Exam;