import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { StudentAuthProvider } from './contexts/AuthContext';
import NavigationHeader from './components/Header';
import StudentLogin from './components/Login';
import StudentRegistration from './components/Register';
import StudentDashboard from './components/Dashboard';
import StudentProfile from './components/Profile';
import AssessmentExam from './components/Exam';
import ExamResults from './components/Results';
import ProtectedRoute from './components/ProtectedRoute';

function EduExamApp() {
  return (
    <StudentAuthProvider>
      <Router>
        <div className="EduExamApp">
          <NavigationHeader />
          <Routes>
            <Route path="/signin" element={<StudentLogin />} />
            <Route path="/signup" element={<StudentRegistration />} />
            <Route path="/" element={
              <ProtectedRoute>
                <StudentDashboard />
              </ProtectedRoute>
            } />
            <Route path="/profile" element={
              <ProtectedRoute>
                <StudentProfile />
              </ProtectedRoute>
            } />
            <Route path="/assessment" element={
              <ProtectedRoute>
                <AssessmentExam />
              </ProtectedRoute>
            } />
            <Route path="/results" element={
              <ProtectedRoute>
                <ExamResults />
              </ProtectedRoute>
            } />
          </Routes>
        </div>
      </Router>
    </StudentAuthProvider>
  );
}

export default EduExamApp;