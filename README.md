# RailConnect - Train Booking Management System

A comprehensive Java-based train reservation system with advanced features, dynamic pricing, and enhanced user experience.

## 🌟 Key Features

- **Advanced User Management**: Secure authentication with input validation
- **Intelligent Train Search**: Search trains by route with real-time availability
- **Dynamic Pricing**: Smart fare calculation based on demand and booking time
- **Reservation Management**: Book, view, and cancel reservations with unique confirmation codes
- **Enhanced Console UI**: Color-coded interface with intuitive navigation
- **Robust Database Design**: Optimized schema with proper indexing and relationships

## 🏗️ Project Architecture

```
src/main/java/com/reservation/
├── model/                  # Data models with business logic
│   ├── User.java          # Customer account management
│   ├── Train.java         # Train information and operations
│   └── Reservation.java   # Booking details with status management
├── dao/                   # Data Access Layer
│   └── CustomerDataAccess.java  # Database operations for users
├── service/               # Business Logic Layer
│   ├── BookingManagementService.java    # Core booking operations
│   ├── TrainScheduleService.java        # Train management
│   └── ReservationDataService.java      # Reservation operations
├── ui/                    # User Interface Layer
│   └── InteractiveConsoleInterface.java # Enhanced console interface
├── util/                  # Utility Classes
│   ├── DatabaseConnectionManager.java   # Singleton DB connection
│   ├── ConfirmationCodeGenerator.java   # Unique code generation
│   └── FareCalculator.java             # Dynamic pricing engine
└── TrainBookingApplication.java         # Main application entry
```

## 🚀 Advanced Features

### Dynamic Pricing Engine
- **Surge Pricing**: Prices increase with demand (seat occupancy)
- **Time-based Pricing**: Early bird discounts and last-minute premiums
- **Class-based Rates**: Different pricing for various travel classes
- **Route-based Calculation**: Distance-aware fare computation

### Enhanced Security
- **Input Validation**: Comprehensive data validation at all levels
- **SQL Injection Prevention**: Parameterized queries throughout
- **Business Logic Validation**: Multi-layer validation for data integrity

### User Experience
- **Color-coded Console**: Visual feedback with colored output
- **Intuitive Navigation**: Easy-to-use menu system
- **Comprehensive Error Handling**: Graceful error management
- **Detailed Feedback**: Clear success/failure messages

## 📋 Prerequisites

1. **Java 11+** - Modern Java features and performance
2. **MySQL 8.0+** - Reliable database with advanced features
3. **Maven 3.6+** - Dependency management and build automation

## ⚙️ Setup Instructions

### 1. Database Configuration

1. Install and start MySQL server
2. Create the database and tables:
   ```bash
   mysql -u root -p < database_schema.sql
   ```

### 2. Application Configuration

Update database credentials in `DatabaseConnectionManager.java`:
```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/train_booking_system";
private static final String DB_USERNAME = "your_username";
private static final String DB_PASSWORD = "your_password";
```

### 3. Build and Run

1. Compile the project:
   ```bash
   mvn clean compile
   ```

2. Run the application:
   ```bash
   mvn exec:java -Dexec.mainClass="com.reservation.TrainBookingApplication"
   ```

## 🎯 Usage Guide

### Getting Started
1. **Create Account**: Register with unique login ID and email
2. **Login**: Authenticate with your credentials
3. **Search Trains**: Find available trains for your route
4. **Book Tickets**: Select train and complete booking
5. **Manage Reservations**: View or cancel existing bookings

### Sample Accounts
- **Test User**: `testuser` / `test123`
- **Admin**: `admin` / `admin123`

### Available Routes
The system includes pre-configured routes:
- Delhi ↔ Mumbai (Golden Express)
- Delhi ↔ Chandigarh (Silver Bullet)
- Mumbai ↔ Kolkata (Lightning Express)
- Chennai ↔ Bangalore (Comfort Rider)
- Pune ↔ Mumbai (City Connect)

## 💰 Travel Classes & Pricing

| Class | Base Rate (per 100km) | Features |
|-------|----------------------|----------|
| First AC | ₹25.00 | Premium comfort, meals included |
| Second AC | ₹18.50 | AC comfort, bedding provided |
| Third AC | ₹12.75 | AC seating, basic amenities |
| Sleeper Class | ₹8.25 | Non-AC, sleeping berths |
| General Class | ₹3.50 | Basic seating, budget-friendly |

## 🔧 Technical Highlights

### Design Patterns Used
- **Singleton Pattern**: Database connection management
- **DAO Pattern**: Data access abstraction
- **Service Layer Pattern**: Business logic separation
- **Builder Pattern**: Complex object construction

### Database Optimizations
- **Indexed Columns**: Fast query performance
- **Foreign Key Constraints**: Data integrity
- **Enum Types**: Controlled status values
- **Timestamp Tracking**: Audit trail support

### Code Quality Features
- **Input Validation**: Comprehensive data validation
- **Error Handling**: Graceful exception management
- **Documentation**: Extensive JavaDoc comments
- **Modular Design**: Loosely coupled components

## 🚀 Future Enhancements

- **Web Interface**: Spring Boot REST API
- **Payment Integration**: Online payment gateway
- **Email Notifications**: Booking confirmations
- **Mobile App**: React Native application
- **Admin Dashboard**: System management interface
- **Reporting System**: Analytics and insights

## 🛠️ Technologies Used

- **Java 11**: Modern language features
- **MySQL 8.0**: Robust database system
- **JDBC**: Database connectivity
- **Maven**: Build and dependency management
- **JUnit**: Unit testing framework (future)

## 📝 License

This project is developed for educational purposes as part of an internship program.

## 🤝 Contributing

This is an internship project. Suggestions and improvements are welcome through:
- Code reviews
- Feature suggestions
- Bug reports
- Documentation improvements

---

**RailConnect** - Making train travel booking simple, smart, and secure! 🚂✨