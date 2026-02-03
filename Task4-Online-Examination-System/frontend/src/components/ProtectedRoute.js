import React from 'react';
import { Navigate } from 'react-router-dom';
import { useStudentAuth } from '../contexts/AuthContext';

const ProtectedRoute = ({ children }) => {
  const { currentStudent, isLoading } = useStudentAuth();

  if (isLoading) {
    return (
      <div className="container">
        <div className="loading" style={{ 
          display: 'flex', 
          flexDirection: 'column', 
          alignItems: 'center', 
          gap: '20px',
          padding: '60px 20px'
        }}>
          <div style={{ 
            width: '40px', 
            height: '40px', 
            border: '4px solid #f3f3f3',
            borderTop: '4px solid #007bff',
            borderRadius: '50%',
            animation: 'spin 1s linear infinite'
          }}></div>
          <p style={{ color: '#666', fontSize: '16px' }}>Loading your dashboard...</p>
        </div>
      </div>
    );
  }

  return currentStudent ? children : <Navigate to="/signin" />;
};

export default ProtectedRoute;