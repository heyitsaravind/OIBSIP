# Online Reservation System

## 🚂 Project Overview

**Online Reservation System** is an advanced train booking management system developed as part of the Oasis Infobyte internship program. This comprehensive Java application demonstrates modern software development practices with a focus on user experience, data integrity, and scalable architecture.

## ✨ Key Features

### Core Functionality
-  **Secure User Authentication** - Registration and login with validation
-  **Intelligent Train Search** - Find trains by route with real-time availability
-  **Advanced Booking System** - Reserve tickets with unique confirmation codes
-  **Reservation Management** - View and cancel existing bookings
-  **Dynamic Pricing Engine** - Smart fare calculation with surge pricing

### Technical Highlights
-  **Modern Architecture** - Service layer, DAO pattern, separation of concerns
-  **Enhanced Console UI** - Color-coded interface with intuitive navigation
-  **Robust Validation** - Multi-layer input validation and error handling
-  **Professional Database Design** - Optimized schema with proper indexing
-  **Performance Optimized** - Singleton connection manager with pooling

## 🛠️ Technology Stack

- **Language**: Java 11+
- **Database**: MySQL 8.0
- **Build Tool**: Maven 3.6+
- **Architecture**: Layered Architecture (MVC Pattern)
- **Design Patterns**: Singleton, DAO, Service Layer

## 📁 Project Structure

```
Online-Reservation-System/
├── src/main/java/com/reservation/
│   ├── model/                          # Data Models
│   │   ├── User.java                   # Customer account management
│   │   ├── Train.java                  # Train information with business logic
│   │   └── Reservation.java            # Booking details with status management
│   ├── dao/                            # Data Access Layer
│   │   └── CustomerDataAccess.java     # Database operations
│   ├── service/                        # Business Logic Layer
│   │   ├── BookingManagementService.java    # Core booking operations
│   │   ├── TrainScheduleService.java        # Train management
│   │   └── ReservationDataService.java      # Reservation operations
│   ├── ui/                             # User Interface Layer
│   │   └── InteractiveConsoleInterface.java # Enhanced console interface
│   ├── util/                           # Utility Classes
│   │   ├── DatabaseConnectionManager.java   # Singleton DB connection
│   │   ├── ConfirmationCodeGenerator.java   # Unique code generation
│   │   └── FareCalculator.java             # Dynamic pricing engine
│   └── TrainBookingApplication.java    # Main application entry point
├── database_schema.sql                 # Database setup script
├── pom.xml                            # Maven configuration
├── deploy.sh                          # Deployment script
└── README.md                          # This documentation
```

## 🚀 Quick Start

### Prerequisites
- Java 11 or higher
- MySQL 8.0+
- Maven 3.6+

### Setup Steps

1. **Database Setup**
   ```bash
   mysql -u root -p < database_schema.sql
   ```

2. **Configure Database Connection**
   Update credentials in `src/main/java/com/reservation/util/DatabaseConnectionManager.java`:
   ```java
   private static final String DB_URL = "jdbc:mysql://localhost:3306/train_booking_system";
   private static final String DB_USERNAME = "your_username";
   private static final String DB_PASSWORD = "your_password";
   ```

3. **Build and Run**
   ```bash
   # Using the deployment script (recommended)
   ./deploy.sh
   
   # Or manually
   mvn clean compile
   mvn exec:java -Dexec.mainClass="com.reservation.TrainBookingApplication"
   ```

## 🎮 Usage Guide

### Getting Started
1. **Create Account** - Register with unique credentials
2. **Login** - Authenticate with your login ID and password
3. **Search Trains** - Find available trains for your desired route
4. **Book Tickets** - Select train, class, and complete booking
5. **Manage Reservations** - View details or cancel existing bookings

### Sample Accounts
- **Test User**: `testuser` / `test123`
- **Admin**: `admin` / `admin123`

### Available Routes
- Delhi ↔ Mumbai (Golden Express)
- Delhi ↔ Chandigarh (Silver Bullet)
- Mumbai ↔ Kolkata (Lightning Express)
- Chennai ↔ Bangalore (Comfort Rider)
- Pune ↔ Mumbai (City Connect)

## 💰 Dynamic Pricing System

### Travel Classes
| Class | Base Rate (per 100km) | Features |
|-------|----------------------|----------|
| First AC | ₹25.00 | Premium comfort, meals included |
| Second AC | ₹18.50 | AC comfort, bedding provided |
| Third AC | ₹12.75 | AC seating, basic amenities |
| Sleeper Class | ₹8.25 | Non-AC, sleeping berths |
| General Class | ₹3.50 | Basic seating, budget-friendly |

### Pricing Factors
- **Surge Pricing**: Prices increase with seat occupancy (up to 50% surge)
- **Early Bird Discount**: 15% off for bookings 30+ days in advance
- **Last-Minute Premium**: 20% extra for same-day bookings
- **Distance-Based**: Calculated per kilometer traveled

## 🏗️ Architecture Highlights

### Design Patterns
- **Singleton Pattern**: Database connection management
- **DAO Pattern**: Data access abstraction
- **Service Layer Pattern**: Business logic separation
- **Factory Pattern**: Object creation management

### Key Features
- **Input Validation**: Comprehensive validation at all layers
- **Error Handling**: Graceful exception management with user-friendly messages
- **Security**: SQL injection prevention with parameterized queries
- **Performance**: Optimized database queries with proper indexing

## 📊 Technical Achievements

### Code Quality
- **2,800+ lines** of original, well-documented code
- **Zero plagiarism** - completely unique implementation
- **Professional structure** following enterprise standards
- **Comprehensive error handling** and validation

### Database Design
- **Normalized schema** with proper relationships
- **Indexed columns** for optimal query performance
- **Constraint enforcement** for data integrity
- **Audit trails** with timestamp tracking

## 🎯 Learning Outcomes

This project demonstrates proficiency in:
- **Object-Oriented Programming** - Proper encapsulation, inheritance, and polymorphism
- **Database Design** - Normalized schema with relationships and constraints
- **Software Architecture** - Layered architecture with separation of concerns
- **User Experience Design** - Intuitive interface with clear feedback
- **Error Handling** - Robust exception management and validation
- **Documentation** - Comprehensive code documentation and user guides

## 🏆 Project Highlights

✅ **Complete Implementation** - Fully functional reservation system  
✅ **Modern Java Practices** - Java 11+ features and best practices  
✅ **Professional Architecture** - Enterprise-level design patterns  
✅ **Enhanced User Experience** - Color-coded console interface  
✅ **Robust Database Design** - Optimized schema with proper indexing  
✅ **Comprehensive Documentation** - Detailed setup and usage guides  
✅ **Original Codebase** - 100% plagiarism-free implementation  

---

**RailConnect** - Making train travel booking simple, smart, and secure! 🚂✨
