import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useStudentAuth } from '../contexts/AuthContext';

const StudentLogin = () => {
  const [credentials, setCredentials] = useState({
    emailAddress: '',
    loginPassword: ''
  });
  const [errorMessage, setErrorMessage] = useState('');
  const [isSubmitting, setIsSubmitting] = useState(false);
  
  const { signIn } = useStudentAuth();
  const navigate = useNavigate();

  const handleInputChange = (e) => {
    setCredentials({
      ...credentials,
      [e.target.name]: e.target.value
    });
    // Clear error when user starts typing
    if (errorMessage) setErrorMessage('');
  };

  const handleLoginSubmit = async (e) => {
    e.preventDefault();
    setErrorMessage('');
    setIsSubmitting(true);

    const result = await signIn(credentials.emailAddress, credentials.loginPassword);
    
    if (result.success) {
      navigate('/');
    } else {
      setErrorMessage(result.message);
    }
    
    setIsSubmitting(false);
  };

  return (
    <div className="container">
      <div className="card" style={{ maxWidth: '420px', margin: '60px auto' }}>
        <div style={{ textAlign: 'center', marginBottom: '30px' }}>
          <h2 style={{ color: '#2c3e50', marginBottom: '10px' }}>Welcome Back!</h2>
          <p style={{ color: '#7f8c8d', fontSize: '16px' }}>
            Sign in to access your assessment dashboard
          </p>
        </div>
        
        {errorMessage && (
          <div className="alert alert-error">
            <strong>Oops!</strong> {errorMessage}
          </div>
        )}

        <form onSubmit={handleLoginSubmit}>
          <div className="form-group">
            <label htmlFor="emailAddress">Email Address</label>
            <input
              type="email"
              id="emailAddress"
              name="emailAddress"
              value={credentials.emailAddress}
              onChange={handleInputChange}
              required
              placeholder="Enter your email address"
              autoComplete="email"
            />
          </div>

          <div className="form-group">
            <label htmlFor="loginPassword">Password</label>
            <input
              type="password"
              id="loginPassword"
              name="loginPassword"
              value={credentials.loginPassword}
              onChange={handleInputChange}
              required
              placeholder="Enter your password"
              autoComplete="current-password"
            />
          </div>

          <button 
            type="submit" 
            className="btn btn-primary"
            style={{ width: '100%', marginBottom: '25px', padding: '12px' }}
            disabled={isSubmitting}
          >
            {isSubmitting ? 'Signing In...' : 'Sign In'}
          </button>
        </form>

        <div style={{ textAlign: 'center' }}>
          <p style={{ marginBottom: '20px' }}>
            New to EduExam Pro? <Link to="/signup" style={{ fontWeight: '600' }}>Create Account</Link>
          </p>
          
          <div style={{ 
            marginTop: '25px', 
            padding: '20px', 
            backgroundColor: '#e8f4fd', 
            borderRadius: '8px',
            border: '1px solid #bee5eb'
          }}>
            <div style={{ fontSize: '14px', fontWeight: '600', marginBottom: '8px', color: '#0c5460' }}>
              🎯 Demo Account
            </div>
            <div style={{ fontSize: '13px', color: '#0c5460' }}>
              <strong>Email:</strong> student@eduexam.com<br />
              <strong>Password:</strong> demo2024
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default StudentLogin;