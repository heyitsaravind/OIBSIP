import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useStudentAuth } from '../contexts/AuthContext';

const NavigationHeader = () => {
  const { currentStudent, signOut } = useStudentAuth();
  const navigate = useNavigate();

  const handleSignOut = async () => {
    await signOut();
    navigate('/signin');
  };

  return (
    <header className="header">
      <nav className="nav">
        <Link to="/" style={{ textDecoration: 'none', color: 'white' }}>
          <div style={{ display: 'flex', alignItems: 'center', gap: '10px' }}>
            <span style={{ fontSize: '24px' }}>🎓</span>
            <h1 style={{ fontSize: '24px', margin: 0 }}>EduExam Pro</h1>
          </div>
        </Link>
        
        <div className="nav-links">
          {currentStudent ? (
            <>
              <span style={{ 
                padding: '8px 12px', 
                backgroundColor: 'rgba(255,255,255,0.1)', 
                borderRadius: '20px',
                fontSize: '14px'
              }}>
                👋 Hi, {currentStudent.fullName.split(' ')[0]}
              </span>
              <Link to="/">Dashboard</Link>
              <Link to="/profile">My Profile</Link>
              <Link to="/results">My Results</Link>
              <button 
                onClick={handleSignOut}
                className="btn btn-secondary"
                style={{ 
                  padding: '8px 16px', 
                  fontSize: '14px',
                  backgroundColor: 'rgba(255,255,255,0.2)',
                  border: '1px solid rgba(255,255,255,0.3)'
                }}
              >
                Sign Out
              </button>
            </>
          ) : (
            <>
              <Link to="/signin">Sign In</Link>
              <Link to="/signup" className="btn btn-secondary" style={{ 
                padding: '8px 16px', 
                fontSize: '14px',
                backgroundColor: 'rgba(255,255,255,0.2)',
                border: '1px solid rgba(255,255,255,0.3)'
              }}>
                Get Started
              </Link>
            </>
          )}
        </div>
      </nav>
    </header>
  );
};

export default NavigationHeader;