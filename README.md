
# OIBSIP - Oasis Infobyte Internship Projects

This repository contains all the projects completed during my Java Developer internship at Oasis Infobyte, showcasing advanced programming skills and modern software development practices.

## 🚀 Projects Overview

### Task 1: ATM Interface System
**Location:** `Task1-ATM-Interface/`

A comprehensive console-based ATM system demonstrating core banking operations with secure authentication and transaction management.

**Key Features:**
- Secure PIN-based authentication
- Core banking operations (withdraw, deposit, transfer)
- Complete transaction history tracking
- Multiple user account support
- Robust error handling and validation

**Technologies:** Java, OOP Principles, Console UI

---

### Task 2: Number Guessing Game
**Location:** `Task2-GuessTheNumber/`

An interactive console game that challenges players to guess randomly generated numbers with scoring and statistics.

**Key Features:**
- Random number generation (1-100)
- Limited attempts per round
- Multiple rounds support
- Scoring system based on performance
- Game statistics and analytics

**Technologies:** Java, Random Generation, Game Logic

---

### Task 3: Online Reservation System - RailConnect
**Location:** `Task3-Online-Reservation-System/`

An advanced train booking management system with dynamic pricing, real-time availability, and professional architecture.

**Key Features:**
- **Intelligent Train Search** - Real-time availability checking
- **Dynamic Pricing Engine** - Surge pricing and time-based discounts
- **Advanced Booking System** - Unique confirmation codes
- **User Management** - Secure registration and authentication
- **Enhanced Console UI** - Color-coded interface with intuitive navigation
- **Professional Database Design** - MySQL with optimized schema

**Technologies:** Java 11+, MySQL 8.0, JDBC, Maven, Advanced OOP

---

### Task 4: Online Examination System - EduExam Pro
**Location:** `Task4-Online-Examination-System/`

A comprehensive web-based examination platform with real-time assessment capabilities, modern UI/UX, and robust backend architecture.

**Key Features:**
- **Secure Authentication** - JWT-based student login and registration
- **Interactive MCQ System** - Dynamic question loading with multiple choice options
- **Smart Timer Management** - Real-time countdown with automatic submission
- **Profile Management** - Update personal information and change passwords
- **Results Analytics** - Comprehensive exam history and performance tracking
- **Modern UI/UX** - Responsive design with smooth animations and transitions
- **Security Features** - Input validation, secure sessions, password hashing

**Technologies:** React.js, Node.js, Express.js, MongoDB, JWT, CSS3

## 📁 Repository Structure

```
OIBSIP/
├── README.md                           # This overview
├── Task2-GuessTheNumber/               # Task 2: Number Guessing Game
│   └── [Game implementation files]
├── Task1-ATM-Interface/               # Task 1: ATM System
│   ├── ATMSystem.java                 # Main application
│   ├── ATM.java                       # ATM operations
│   ├── Account.java                   # Account management
│   ├── Transaction.java               # Transaction handling
│   ├── UserDatabase.java              # User data management
│   └── README.md                      # Task-specific documentation
├── Task3-Online-Reservation-System/   # Task 3: Train Booking System
│   ├── src/main/java/com/reservation/ # Source code
│   │   ├── model/                     # Data models
│   │   ├── dao/                       # Data access layer
│   │   ├── service/                   # Business logic
│   │   ├── ui/                        # User interface
│   │   └── util/                      # Utility classes
│   ├── database_schema.sql            # Database setup
│   ├── pom.xml                        # Maven configuration
│   ├── deploy.sh                      # Deployment script
│   └── README.md                      # Comprehensive documentation
└── Task4-Online-Examination-System/   # Task 4: EduExam Pro
    ├── backend/                       # Express.js API server
    │   ├── models/                    # MongoDB schemas
    │   ├── routes/                    # API endpoints
    │   ├── middleware/                # Authentication middleware
    │   └── server.js                  # Main server file
    ├── frontend/                      # React.js application
    │   ├── src/                       # Source code
    │   │   ├── components/            # React components
    │   │   ├── contexts/              # Context providers
    │   │   └── App.js                 # Main app component
    │   └── public/                    # Static assets
    ├── setup.sh                       # Quick setup script
    └── README.md                      # Project documentation
```

## 🛠️ Technologies & Skills Demonstrated

### Programming Languages
- **Java 11+** - Modern Java features and best practices
- **JavaScript (ES6+)** - Modern frontend development with React
- **SQL** - Database design and optimization

### Frameworks & Tools
- **React.js** - Modern frontend framework with hooks and context
- **Node.js & Express.js** - Backend API development
- **Maven** - Build automation and dependency management
- **JDBC** - Database connectivity and operations
- **MongoDB & Mongoose** - NoSQL database and ODM
- **Git** - Version control and collaboration

## 🎯 Learning Outcomes

Through these projects, I have demonstrated proficiency in:

1. **Core Java Development** - Advanced OOP concepts and modern Java features
2. **Database Integration** - MySQL design, optimization, and JDBC operations
3. **Software Architecture** - Scalable, maintainable code structure
4. **Problem Solving** - Complex business logic implementation
5. **User Experience** - Intuitive interface design and error handling
6. **Professional Development** - Code documentation, version control, deployment

## 🚀 Quick Start Guide

### Task 1: ATM Interface
```bash
cd Task1-ATM-Interface
javac *.java
java ATMSystem
```

### Task 2: Number Guessing Game
```bash
cd Task2-GuessTheNumber
javac *.java
java NumberGuessingGame
```

### Task 3: Train Reservation System
```bash
cd Task3-Online-Reservation-System
# Setup database first
mysql -u root -p < database_schema.sql
# Run the application
./deploy.sh
```

### Task 4: Online Examination System
```bash
cd Task4-Online-Examination-System
# Quick setup (installs dependencies and seeds database)
./setup.sh
# Start backend server
./start-backend.sh
# In a new terminal, start frontend
./start-frontend.sh
# Access at http://localhost:3000
```

## 📞 Contact Information

**Name:** Aravind M S  
**Email:** aravindms046@gmail.com  
**GitHub:** [heyitsaravind](https://github.com/heyitsaravind)

## 📝 Acknowledgments

Special thanks to **Oasis Infobyte** for providing this internship opportunity and the chance to work on these challenging and educational projects. Each task has contributed significantly to my growth as a Java developer and software engineer.
