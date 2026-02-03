import React, { createContext, useState, useContext, useEffect } from 'react';
import axios from 'axios';

const StudentAuthContext = createContext();

export const useStudentAuth = () => {
  const context = useContext(StudentAuthContext);
  if (!context) {
    throw new Error('useStudentAuth must be used within a StudentAuthProvider');
  }
  return context;
};

export const StudentAuthProvider = ({ children }) => {
  const [currentStudent, setCurrentStudent] = useState(null);
  const [isLoading, setIsLoading] = useState(true);
  const [authToken, setAuthToken] = useState(localStorage.getItem('eduexam_token'));

  // Configure axios with authentication header
  useEffect(() => {
    if (authToken) {
      axios.defaults.headers.common['Authorization'] = `Bearer ${authToken}`;
    } else {
      delete axios.defaults.headers.common['Authorization'];
    }
  }, [authToken]);

  // Verify authentication on app initialization
  useEffect(() => {
    const verifyAuthentication = async () => {
      if (authToken) {
        try {
          const response = await axios.get('/api/auth/profile');
          setCurrentStudent(response.data.student);
        } catch (error) {
          console.error('Authentication verification failed:', error);
          localStorage.removeItem('eduexam_token');
          setAuthToken(null);
        }
      }
      setIsLoading(false);
    };

    verifyAuthentication();
  }, [authToken]);

  const signIn = async (emailAddress, loginPassword) => {
    try {
      const response = await axios.post('/api/auth/signin', { 
        emailAddress, 
        loginPassword 
      });
      
      const { authToken: newToken, student } = response.data;
      
      localStorage.setItem('eduexam_token', newToken);
      setAuthToken(newToken);
      setCurrentStudent(student);
      
      return { success: true, message: 'Welcome back!' };
    } catch (error) {
      return { 
        success: false, 
        message: error.response?.data?.message || 'Sign in failed. Please try again.' 
      };
    }
  };

  const signUp = async (studentData) => {
    try {
      const response = await axios.post('/api/auth/signup', studentData);
      const { authToken: newToken, student } = response.data;
      
      localStorage.setItem('eduexam_token', newToken);
      setAuthToken(newToken);
      setCurrentStudent(student);
      
      return { success: true, message: 'Account created successfully!' };
    } catch (error) {
      return { 
        success: false, 
        message: error.response?.data?.message || 'Account creation failed. Please try again.' 
      };
    }
  };

  const signOut = async () => {
    try {
      await axios.post('/api/auth/signout');
    } catch (error) {
      console.error('Sign out error:', error);
    } finally {
      localStorage.removeItem('eduexam_token');
      setAuthToken(null);
      setCurrentStudent(null);
    }
  };

  const updateStudentProfile = async (profileData) => {
    try {
      const response = await axios.put('/api/student/profile', profileData);
      setCurrentStudent(response.data.student);
      return { success: true, message: 'Profile updated successfully!' };
    } catch (error) {
      return { 
        success: false, 
        message: error.response?.data?.message || 'Profile update failed. Please try again.' 
      };
    }
  };

  const updatePassword = async (passwordData) => {
    try {
      const response = await axios.put('/api/student/credentials', passwordData);
      return { success: true, message: 'Password updated successfully!' };
    } catch (error) {
      return { 
        success: false, 
        message: error.response?.data?.message || 'Password update failed. Please try again.' 
      };
    }
  };

  const contextValue = {
    currentStudent,
    signIn,
    signUp,
    signOut,
    updateStudentProfile,
    updatePassword,
    isLoading,
    isAuthenticated: !!currentStudent
  };

  return (
    <StudentAuthContext.Provider value={contextValue}>
      {children}
    </StudentAuthContext.Provider>
  );
};