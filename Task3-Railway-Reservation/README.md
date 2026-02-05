# RailConnect - Advanced Railway Reservation System

A comprehensive console-based train booking management system that simulates real-world railway reservation operations with modern features and user-friendly interface.

## 🚂 System Overview

RailConnect is a feature-rich railway reservation platform that enables users to search trains, make bookings, manage reservations, and track their travel history. The system provides both guest browsing and authenticated user functionalities with a focus on user experience and data integrity.

## 🌟 Key Features

### User Management
- **Secure Authentication** - Username/password based login system
- **User Registration** - Easy account creation with profile management
- **Profile Management** - Update personal information and preferences
- **Guest Access** - Browse trains without registration

### Train Operations
- **Comprehensive Train Database** - Detailed train information with routes
- **Smart Search System** - Search by route, train number, or station
- **Real-time Availability** - Live seat availability tracking
- **Dynamic Pricing** - Fare calculation based on route and class

### Booking Management
- **Easy Reservation** - Intuitive booking process with confirmation
- **PNR Generation** - Unique booking reference numbers
- **Booking History** - Complete travel history tracking
- **Cancellation System** - Flexible cancellation with refund calculation

### Advanced Features
- **Multi-passenger Booking** - Book for up to 6 passengers
- **Date Validation** - Prevent past date bookings
- **Seat Management** - Real-time seat allocation and release
- **Payment Integration** - Multiple payment method support

## 🚀 Getting Started

### Prerequisites
- Java 8 or higher
- Command line interface

### Installation & Running
```bash
# Compile all Java files
javac *.java

# Run the main application
java RailwayReservationSystem
```

### Demo Credentials
```
Username: user123
Password: pass123
Name: Aravind Kumar
```

## 🎯 User Guide

### For New Users
1. **Create Account** - Register with personal details
2. **Browse Trains** - Explore available train options
3. **Make Booking** - Select train and complete reservation
4. **Manage Bookings** - View and modify your reservations

### For Existing Users
1. **Login** - Access your account securely
2. **Search Trains** - Find trains by route or number
3. **Book Tickets** - Complete booking with payment
4. **Track History** - Monitor all your travel records

## 🚂 Available Trains

### Sample Train Database
```
Train No. | Name              | Route                    | Fare    | Seats
----------|-------------------|--------------------------|---------|-------
12345     | Rajdhani Express  | New Delhi → Mumbai       | ₹1,200  | 100
67890     | Shatabdi Express  | Chennai → Bangalore      | ₹800    | 80
11111     | Duronto Express   | Kolkata → New Delhi      | ₹1,500  | 120
22222     | Garib Rath        | Mumbai → Ahmedabad       | ₹400    | 150
33333     | Jan Shatabdi      | Pune → Mumbai            | ₹200    | 200
```

## 💳 Booking Process

### Step-by-Step Booking
1. **Login/Register** - Authenticate or create account
2. **Search Trains** - Find suitable trains for your route
3. **Select Train** - Choose from available options
4. **Enter Details** - Specify passengers and journey date
5. **Review Booking** - Confirm booking details and fare
6. **Make Payment** - Complete transaction securely
7. **Get PNR** - Receive booking confirmation number

### Booking Validation
- **Date Validation** - Journey date must be in future
- **Passenger Limits** - Maximum 6 passengers per booking
- **Seat Availability** - Real-time seat checking
- **Fare Calculation** - Automatic total fare computation

## 📊 Booking Management

### View Bookings
```
📋 MY CURRENT BOOKINGS
======================================================================
PNR          Train    Name              Date        Passengers  Fare
-------------|--------|------------------|-----------|-----------|-------
PNR1001      12345    Rajdhani Express  15/03/2024  2          ₹2,400
PNR1002      67890    Shatabdi Express  20/03/2024  1          ₹800
======================================================================
```

### Cancellation Policy
- **7+ Days Advance**: 5% cancellation charges
- **3-6 Days Advance**: 10% cancellation charges  
- **1-2 Days Advance**: 25% cancellation charges
- **Same Day**: 50% cancellation charges

## 🏗️ Technical Architecture

### Class Structure
- **RailwayReservationSystem.java** - Main application controller
- **User.java** - User account management
- **Train.java** - Train information and operations
- **Reservation.java** - Booking record management

### System Components
1. **Authentication Module** - User login/registration system
2. **Train Management** - Train database and search functionality
3. **Booking Engine** - Reservation processing and validation
4. **Payment System** - Transaction handling and confirmation
5. **Data Management** - In-memory data storage and retrieval

### Design Patterns
- **MVC Pattern** - Separation of concerns
- **Factory Pattern** - Object creation management
- **Observer Pattern** - Real-time updates
- **Strategy Pattern** - Multiple payment methods

## 🔧 System Features

### Search Functionality
```java
// Search by route
🔍 SEARCH RESULTS
Route: Mumbai → Delhi
=====================================
Number   Name              Departure  Arrival   Fare     Seats
12345    Rajdhani Express  06:00     20:30     ₹1,200   95
11111    Duronto Express   22:15     12:30     ₹1,500   118
```

### Real-time Updates
- **Seat Availability** - Live seat count updates
- **Booking Status** - Real-time reservation status
- **Payment Confirmation** - Instant booking confirmation
- **Cancellation Processing** - Immediate seat release

## 💡 Advanced Features

### Smart Search System
- **Route-based Search** - Find trains between stations
- **Number-based Search** - Direct train lookup
- **Availability Filter** - Show only available trains
- **Fare Comparison** - Compare prices across trains

### Booking Intelligence
- **Date Validation** - Prevent invalid date selection
- **Capacity Management** - Automatic seat allocation
- **Conflict Resolution** - Handle booking conflicts
- **Error Recovery** - Graceful error handling

## 📈 Statistics & Analytics

### User Statistics
```
👤 MY PROFILE
===================================
Username: user123
Name: Aravind Kumar
Email: aravind@email.com
Phone: 9876543210
Member Since: Jan 2024
Total Bookings: 5
===================================
```

### System Metrics
- **Total Users** - Registered user count
- **Active Bookings** - Current reservation count
- **Revenue Tracking** - Total booking value
- **Popular Routes** - Most booked train routes

## 🔒 Security Features

### Data Protection
- **Password Security** - Secure password storage
- **Session Management** - Secure login sessions
- **Input Validation** - Prevent malicious inputs
- **Data Integrity** - Consistent data operations

### Transaction Security
- **Booking Validation** - Verify booking authenticity
- **Payment Security** - Secure payment processing
- **PNR Protection** - Unique booking identifiers
- **Cancellation Security** - Authorized cancellations only

## 🎨 User Interface Design

### Menu System
- **Hierarchical Navigation** - Logical menu structure
- **Visual Indicators** - Clear status and progress indicators
- **Error Messages** - Helpful error descriptions
- **Success Confirmations** - Clear operation confirmations

### Display Formatting
- **Tabular Data** - Organized information display
- **Emoji Integration** - Enhanced visual appeal
- **Color Coding** - Status-based visual cues
- **Responsive Layout** - Adaptable to different terminals

## 🔮 Future Enhancements

### Planned Features
- **Database Integration** - Persistent data storage
- **Web Interface** - Browser-based access
- **Mobile App** - Smartphone application
- **Real-time Notifications** - SMS/Email alerts
- **Seat Selection** - Choose specific seats
- **Meal Preferences** - Food ordering system

### Advanced Capabilities
- **Multi-language Support** - Regional language options
- **Dynamic Pricing** - Demand-based fare adjustment
- **Loyalty Program** - Reward frequent travelers
- **Travel Insurance** - Integrated insurance options
- **Group Bookings** - Special group reservation features

## 🎓 Learning Outcomes

### Programming Concepts
- **Object-Oriented Design** - Clean class architecture
- **Data Structures** - Efficient data management
- **Algorithm Implementation** - Search and booking algorithms
- **Input/Output Handling** - User interaction management
- **Error Handling** - Robust exception management
- **Date/Time Operations** - Temporal data processing

### Software Engineering
- **System Design** - Scalable architecture planning
- **User Experience** - Interface design principles
- **Data Modeling** - Entity relationship design
- **Business Logic** - Real-world process implementation
- **Testing Strategies** - Quality assurance methods

## 👨‍💻 Author

**Aravind M S**
- Email: aravindms046@gmail.com
- GitHub: heyitsaravind
- Project: OIBSIP Java Development Internship

## 📝 License

This project is created for educational purposes as part of the Oasis Infobyte internship program.

---

*Your journey begins with a simple click! 🚂✨*