# Task 4: Online Examination System - EduExam Pro

A comprehensive web-based examination platform built with modern technologies, featuring real-time assessment capabilities, secure authentication, and intuitive user experience.

## 🎯 Project Overview

EduExam Pro is a full-stack web application designed to facilitate online examinations with a focus on user experience, security, and reliability. The system provides a seamless testing environment for students with comprehensive result tracking and profile management.

## ✨ Key Features

### 🔐 Authentication & Security
- **Secure Registration & Login** - JWT-based authentication with bcrypt password hashing
- **Session Management** - Automatic token refresh and secure logout
- **Input Validation** - Multi-layer validation to prevent malicious inputs
- **Password Security** - Strong password requirements and secure storage

### 📝 Examination System
- **Interactive MCQ Interface** - Clean, intuitive multiple-choice question display
- **Dynamic Question Loading** - Random question selection from database
- **Real-time Progress Tracking** - Visual progress indicators and question navigation
- **Answer Selection** - Easy-to-use radio button interface with visual feedback

### ⏰ Timer Management
- **Smart Countdown Timer** - Real-time timer display with visual alerts
- **Automatic Submission** - Auto-submit when time expires to prevent data loss
- **Time Tracking** - Accurate time measurement for performance analytics
- **Warning Notifications** - Visual and audio alerts for time remaining

### 👤 Profile Management
- **Personal Information Updates** - Edit name, contact details
- **Password Management** - Secure password change functionality
- **Account Settings** - User preferences and account status

### 📊 Results & Analytics
- **Comprehensive Score Display** - Detailed results with percentage and grades
- **Exam History** - Complete history of all attempted exams
- **Performance Analytics** - Score trends and improvement tracking
- **Grade Calculation** - Automatic letter grade assignment based on performance

### 🎨 Modern UI/UX
- **Responsive Design** - Works seamlessly on desktop, tablet, and mobile
- **Smooth Animations** - CSS3 transitions and loading animations
- **Intuitive Navigation** - User-friendly interface with clear visual hierarchy
- **Accessibility Features** - Keyboard navigation and screen reader support

## 🛠️ Technical Architecture

### Frontend (React.js)
```
frontend/
├── src/
│   ├── components/          # React components
│   │   ├── Login.js        # Authentication component
│   │   ├── Dashboard.js    # Main dashboard
│   │   ├── Exam.js         # Examination interface
│   │   ├── Profile.js      # Profile management
│   │   ├── Results.js      # Results display
│   │   └── Header.js       # Navigation header
│   ├── contexts/           # Context providers
│   │   └── AuthContext.js  # Authentication state management
│   ├── App.js              # Main application component
│   ├── index.js            # Application entry point
│   └── index.css           # Global styles
└── public/
    └── index.html          # HTML template
```

### Backend (Express.js)
```
backend/
├── models/                 # Database schemas
│   ├── User.js            # Student model
│   └── Question.js        # Assessment question model
├── routes/                # API endpoints
│   ├── auth.js           # Authentication routes
│   ├── user.js           # User management routes
│   └── exam.js           # Examination routes
├── middleware/           # Custom middleware
│   └── auth.js          # JWT authentication middleware
├── server.js            # Express server setup
├── seedData.js          # Database initialization
└── package.json         # Dependencies and scripts
```

## 🚀 Quick Start Guide

### Prerequisites
- Node.js (v14 or higher)
- MongoDB (v4.4 or higher)
- npm or yarn package manager

### Installation & Setup

1. **Clone and Navigate**
   ```bash
   cd Task4-Online-Examination-System
   ```

2. **Quick Setup (Recommended)**
   ```bash
   ./setup.sh
   ```
   This script will:
   - Install all dependencies
   - Set up the database with sample data
   - Create demo user account

3. **Manual Setup**
   ```bash
   # Install backend dependencies
   cd backend
   npm install
   
   # Install frontend dependencies
   cd ../frontend
   npm install
   
   # Initialize database
   cd ../backend
   node seedData.js
   ```

### Running the Application

1. **Start Backend Server**
   ```bash
   ./start-backend.sh
   # Or manually: cd backend && npm start
   ```

2. **Start Frontend Application** (in new terminal)
   ```bash
   ./start-frontend.sh
   # Or manually: cd frontend && npm start
   ```

3. **Access Application**
   - Open browser and navigate to: `http://localhost:3000`
   - Backend API available at: `http://localhost:5000`

## 🔑 Demo Credentials

**Student Account:**
- Email: `student@eduexam.com`
- Password: `demo2024`

## 📡 API Endpoints

### Authentication
- `POST /api/auth/signup` - Student registration
- `POST /api/auth/signin` - Student login
- `POST /api/auth/signout` - Logout
- `GET /api/auth/profile` - Get current user

### Student Management
- `PUT /api/student/profile` - Update profile information
- `PUT /api/student/credentials` - Change password

### Assessment System
- `GET /api/assessment/questions` - Fetch exam questions
- `POST /api/assessment/submit` - Submit exam answers
- `GET /api/assessment/history` - Get exam history

## 🗄️ Database Schema

### Student Model
```javascript
{
  fullName: String,
  emailAddress: String (unique),
  loginPassword: String (hashed),
  contactNumber: String,
  studentRole: String,
  examHistory: [{
    assessmentId: String,
    achievedScore: Number,
    totalQuestions: Number,
    completedAt: Date,
    durationInSeconds: Number,
    percentageScore: Number
  }],
  accountStatus: String,
  lastLoginAt: Date
}
```

### Assessment Question Model
```javascript
{
  questionText: String,
  answerChoices: [{
    optionText: String,
    isCorrectAnswer: Boolean,
    choiceId: String
  }],
  subject: String,
  difficultyLevel: String,
  pointValue: Number,
  createdBy: String,
  isActive: Boolean
}
```

## 🎨 UI/UX Features

- **Modern Design Language** - Clean, professional interface
- **Color-coded Feedback** - Visual indicators for different states
- **Loading States** - Smooth loading animations and progress indicators
- **Error Handling** - User-friendly error messages and recovery options
- **Mobile Responsive** - Optimized for all device sizes
- **Keyboard Shortcuts** - Enhanced accessibility features

## 🔒 Security Features

- **JWT Authentication** - Secure token-based authentication
- **Password Hashing** - bcrypt with salt rounds for password security
- **Input Sanitization** - Protection against XSS and injection attacks
- **CORS Configuration** - Proper cross-origin resource sharing setup
- **Environment Variables** - Secure configuration management
- **Session Timeout** - Automatic logout for inactive sessions

## 📈 Performance Optimizations

- **Database Indexing** - Optimized queries for better performance
- **Lazy Loading** - Components loaded on demand
- **Caching Strategies** - Efficient data caching mechanisms
- **Minified Assets** - Optimized build for production
- **CDN Ready** - Prepared for content delivery network deployment

## 🧪 Testing & Quality Assurance

- **Input Validation Testing** - Comprehensive form validation
- **Authentication Flow Testing** - Login/logout functionality
- **Timer Accuracy Testing** - Precise time management verification
- **Cross-browser Compatibility** - Tested on major browsers
- **Responsive Design Testing** - Mobile and desktop compatibility

## 🚀 Deployment Ready

The application is configured for easy deployment with:
- Environment-based configuration
- Production build optimization
- Docker containerization support
- Cloud platform compatibility (Heroku, AWS, etc.)

## 📝 Future Enhancements

- **Question Categories** - Subject-wise question filtering
- **Difficulty Levels** - Adaptive difficulty based on performance
- **Bulk Question Import** - CSV/Excel question import functionality
- **Advanced Analytics** - Detailed performance insights
- **Multi-language Support** - Internationalization features
- **Real-time Notifications** - WebSocket-based live updates

## 🤝 Contributing

This project is part of the OIBSIP internship program. For suggestions or improvements, please refer to the main repository guidelines.

## 📄 License

This project is created for educational purposes as part of the Oasis Infobyte internship program.

---

**Developed by:** Aravind M S  
**Internship:** Oasis Infobyte - Java Developer  
**Project:** Task 4 - Online Examination System  
**Technologies:** React.js, Node.js, Express.js, MongoDB, JWT, CSS3