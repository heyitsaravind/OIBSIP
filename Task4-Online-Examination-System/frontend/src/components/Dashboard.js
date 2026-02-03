import React from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';

const Dashboard = () => {
  const { user } = useAuth();

  return (
    <div className="container">
      <div className="card">
        <h2>Welcome to Online Exam System</h2>
        <p style={{ marginBottom: '30px', fontSize: '18px', color: '#666' }}>
          Hello {user?.name}! Ready to take your exam?
        </p>

        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(300px, 1fr))', gap: '20px' }}>
          <div className="card" style={{ textAlign: 'center' }}>
            <h3 style={{ color: '#007bff', marginBottom: '15px' }}>Start Exam</h3>
            <p style={{ marginBottom: '20px', color: '#666' }}>
              Take a new examination with multiple choice questions. 
              You'll have 30 minutes to complete the exam.
            </p>
            <Link to="/exam" className="btn btn-primary">
              Start Exam
            </Link>
          </div>

          <div className="card" style={{ textAlign: 'center' }}>
            <h3 style={{ color: '#28a745', marginBottom: '15px' }}>View Results</h3>
            <p style={{ marginBottom: '20px', color: '#666' }}>
              Check your previous exam results and performance history.
            </p>
            <Link to="/results" className="btn btn-success">
              View Results
            </Link>
          </div>

          <div className="card" style={{ textAlign: 'center' }}>
            <h3 style={{ color: '#6c757d', marginBottom: '15px' }}>Manage Profile</h3>
            <p style={{ marginBottom: '20px', color: '#666' }}>
              Update your profile information and change your password.
            </p>
            <Link to="/profile" className="btn btn-secondary">
              Manage Profile
            </Link>
          </div>
        </div>

        <div className="card" style={{ marginTop: '30px', backgroundColor: '#f8f9fa' }}>
          <h3 style={{ color: '#007bff', marginBottom: '15px' }}>Exam Instructions</h3>
          <ul style={{ paddingLeft: '20px', lineHeight: '1.8' }}>
            <li>Each exam contains multiple choice questions</li>
            <li>You have 30 minutes to complete the exam</li>
            <li>Select one answer for each question</li>
            <li>The exam will auto-submit when time runs out</li>
            <li>You can submit early if you finish before time</li>
            <li>Make sure you have a stable internet connection</li>
          </ul>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;