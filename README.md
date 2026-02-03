# OIBSIP - Oasis Infobyte Internship Projects

This repository contains all the projects completed during my Java Developer internship at Oasis Infobyte, showcasing advanced programming skills and modern software development practices.

## 🚀 Projects Overview

### Task 1: ATM Interface System
**Location:** `Task1-ATM-Interface/`

A comprehensive console-based ATM system demonstrating core banking operations with secure authentication and transaction management.

**Key Features:**
- 🔐 Secure PIN-based authentication
- 💰 Core banking operations (withdraw, deposit, transfer)
- 📊 Complete transaction history tracking
- 👥 Multiple user account support
- 🛡️ Robust error handling and validation

**Technologies:** Java, OOP Principles, Console UI

---

### Task 2: Number Guessing Game
**Location:** `GuessTheNumber/`

An interactive console game that challenges players to guess randomly generated numbers with scoring and statistics.

**Key Features:**
- 🎲 Random number generation (1-100)
- ⏱️ Limited attempts per round
- 🔄 Multiple rounds support
- 🏆 Scoring system based on performance
- 📈 Game statistics and analytics

**Technologies:** Java, Random Generation, Game Logic

---

### Task 3: Online Reservation System - RailConnect
**Location:** `Task3-Online-Reservation-System/`

An advanced train booking management system with dynamic pricing, real-time availability, and professional architecture.

**Key Features:**
- 🚂 **Intelligent Train Search** - Real-time availability checking
- 💳 **Dynamic Pricing Engine** - Surge pricing and time-based discounts
- 🎫 **Advanced Booking System** - Unique confirmation codes
- 👤 **User Management** - Secure registration and authentication
- 🎨 **Enhanced Console UI** - Color-coded interface with intuitive navigation
- 🗄️ **Professional Database Design** - MySQL with optimized schema

**Technical Highlights:**
- **Architecture:** Layered architecture with service layer, DAO pattern
- **Design Patterns:** Singleton, Factory, Service Layer patterns
- **Database:** MySQL 8.0 with proper indexing and relationships
- **Validation:** Multi-layer input validation and error handling
- **Security:** SQL injection prevention with parameterized queries

**Technologies:** Java 11+, MySQL 8.0, JDBC, Maven, Advanced OOP

## 📁 Repository Structure

```
OIBSIP/
├── README.md                           # This overview
├── GuessTheNumber/                     # Task 2: Number Guessing Game
│   └── [Game implementation files]
├── Task1-ATM-Interface/               # Task 1: ATM System
│   ├── ATMSystem.java                 # Main application
│   ├── ATM.java                       # ATM operations
│   ├── Account.java                   # Account management
│   ├── Transaction.java               # Transaction handling
│   ├── UserDatabase.java              # User data management
│   └── README.md                      # Task-specific documentation
└── Task3-Online-Reservation-System/   # Task 3: Train Booking System
    ├── src/main/java/com/reservation/ # Source code
    │   ├── model/                     # Data models
    │   ├── dao/                       # Data access layer
    │   ├── service/                   # Business logic
    │   ├── ui/                        # User interface
    │   └── util/                      # Utility classes
    ├── database_schema.sql            # Database setup
    ├── pom.xml                        # Maven configuration
    ├── deploy.sh                      # Deployment script
    └── README.md                      # Comprehensive documentation
```

## 🛠️ Technologies & Skills Demonstrated

### Programming Languages
- **Java 11+** - Modern Java features and best practices
- **SQL** - Database design and optimization

### Frameworks & Tools
- **Maven** - Build automation and dependency management
- **JDBC** - Database connectivity and operations
- **Git** - Version control and collaboration

### Software Engineering Practices
- **Object-Oriented Programming** - Encapsulation, inheritance, polymorphism
- **Design Patterns** - Singleton, DAO, Service Layer, Factory patterns
- **Software Architecture** - Layered architecture, separation of concerns
- **Database Design** - Normalized schema, indexing, relationships
- **Error Handling** - Comprehensive exception management
- **Input Validation** - Multi-layer validation and security
- **Documentation** - Professional code documentation and user guides

### Advanced Concepts
- **Dynamic Pricing Algorithms** - Surge pricing and time-based calculations
- **Connection Pooling** - Database connection optimization
- **Business Logic Separation** - Clean architecture principles
- **User Experience Design** - Intuitive interfaces and feedback systems

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
cd GuessTheNumber
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

## 📊 Project Statistics

- **Total Lines of Code:** 4,000+
- **Files Created:** 25+
- **Design Patterns Used:** 5+
- **Database Tables:** 3 (with proper relationships)
- **Test Cases Covered:** Multiple user scenarios
- **Documentation Pages:** Comprehensive guides for each project

## 🏆 Key Achievements

✅ **Complete Implementation** - All projects fully functional with comprehensive features  
✅ **Professional Architecture** - Enterprise-level design patterns and practices  
✅ **Advanced Features** - Dynamic pricing, real-time processing, secure authentication  
✅ **Robust Error Handling** - Graceful exception management throughout  
✅ **Comprehensive Documentation** - Detailed setup guides and technical documentation  
✅ **Original Codebase** - 100% plagiarism-free, unique implementations  
✅ **Modern Java Practices** - Java 11+ features and industry best practices  

## 📞 Contact Information

**Name:** Aravind M S  
**Email:** aravindms046@gmail.com  
**Internship:** Oasis Infobyte - Java Developer  
**GitHub:** [heyitsaravind](https://github.com/heyitsaravind)

## 📝 Acknowledgments

Special thanks to **Oasis Infobyte** for providing this internship opportunity and the chance to work on these challenging and educational projects. Each task has contributed significantly to my growth as a Java developer and software engineer.

---

**This repository showcases advanced Java development skills suitable for enterprise-level applications and demonstrates readiness for professional software development roles.** 🚀✨