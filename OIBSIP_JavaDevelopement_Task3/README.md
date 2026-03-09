# Task 3: Online Reservation System

## Objective
Develop a console-based online train reservation system that allows users to book tickets, view reservation details, and cancel bookings with centralized database management.

## Steps Performed

1. **System Design**
   - Designed four-class architecture (ReservationSystem, User, Reservation, Train)
   - Implemented secure login system with user authentication
   - Created menu-driven reservation interface

2. **Core Features Implementation**
   - Developed login form with valid credentials check
   - Created reservation system with passenger details collection
   - Implemented train database with automatic train name display
   - Built PNR generation system
   - Added cancellation form with PNR-based ticket cancellation

3. **Data Management**
   - Used HashMap for user, reservation, and train storage
   - Implemented automatic PNR counter for unique ticket numbers
   - Created Reservation class for booking details
   - Built Train class for train information

4. **Validation & Features**
   - Added login ID and password validation
   - Implemented passenger detail validation
   - Created train number verification
   - Added class type selection (Sleeper, 3A, 2A, 1A)
   - Built confirmation system for cancellations

## Tools Used

- **Programming Language:** Java (JDK 8+)
- **Development Environment:** Terminal/Command Line
- **Data Structures:** HashMap
- **Design Pattern:** Object-Oriented Programming (OOP)
- **Version Control:** Git & GitHub

## Outcome

Successfully created a fully functional Online Reservation System that demonstrates:
- Secure user authentication with login form
- Complete reservation system with all necessary fields
- Automatic train name display based on train number
- PNR generation for each booking
- Cancellation system with PNR-based ticket retrieval
- Centralized database for all reservations
- Professional console interface with clear navigation

The system provides a realistic train booking experience with complete passenger information management and easy cancellation process.

## How to Run

```bash
cd OIBSIP_JavaDevelopement_Task3
javac *.java
java ReservationSystem
```

## Sample Credentials
- Login ID: user1 | Password: pass123
- Login ID: user2 | Password: pass456

## Available Trains
- 12345 - Rajdhani Express (Delhi to Mumbai)
- 12346 - Shatabdi Express (Chennai to Bangalore)
- 12347 - Duronto Express (Kolkata to Delhi)
- 12348 - Garib Rath (Mumbai to Ahmedabad)
- 12349 - Jan Shatabdi (Bangalore to Mysore)
