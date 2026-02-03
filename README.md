# EduExam Pro - Smart Online Testing Platform

A modern, intuitive web-based examination platform designed for seamless online testing experiences. Built with student-friendly interfaces and robust backend architecture.

## Core Capabilities

- Secure Student Authentication & Registration
- Personal Profile & Credential Management
- Interactive Multiple Choice Question Engine
- Smart Timer with Automatic Submission
- Comprehensive Session & Result Tracking

## Technology Foundation

- **Frontend**: React.js with Modern Hooks Architecture
- **Backend**: Express.js REST API Server
- **Database**: MongoDB with Mongoose ODM
- **Security**: JSON Web Token Authentication
- **UI/UX**: Custom CSS3 with Responsive Design

## Project Architecture

```
eduexam-pro/
├── server/               # Express.js API backend
├── client/              # React.js frontend app
├── database/            # MongoDB schemas & seed data
└── docs/               # Project documentation
```

## Quick Start Guide

### Backend Server Setup
```bash
cd server
npm install
npm run dev
```

### Frontend Client Setup
```bash
cd client
npm install
npm start
```

## API Reference

- POST /api/auth/signin - Student authentication
- POST /api/auth/signup - New student registration
- PUT /api/student/profile - Update personal info
- PUT /api/student/credentials - Change login password
- GET /api/assessment/questions - Fetch exam questions
- POST /api/assessment/submit - Submit completed exam
- POST /api/auth/signout - End user session

## Demo Credentials
- Username: student@eduexam.com
- Password: demo2024