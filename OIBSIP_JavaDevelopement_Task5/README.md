# Task 5: Digital Library Management System

## Objective
Develop a comprehensive console-based library automation system that digitizes all library functionalities including book-keeping, issuing books, member management, and report generation with separate admin and user modules.

## Steps Performed

1. **System Design**
   - Designed five-class architecture (LibraryManagementSystem, User, Book, Member, IssuedBook)
   - Implemented role-based access control (Admin and User modules)
   - Created centralized database using HashMaps

2. **Admin Module Implementation**
   - Book Management: Add, update, delete, and view all books
   - Member Management: Add, update, delete, and view all members
   - Report Generation: View issued books report with due dates
   - Complete control over system records

3. **User Module Implementation**
   - Browse books by category
   - Search books by title or author
   - Issue books with automatic due date calculation
   - Return books with availability update
   - View personal issued books
   - Send query emails to administrator

4. **Core Features**
   - Automated book-keeping with unique book IDs
   - Book issuing system with 14-day return period
   - Member registration and management
   - Category-based book browsing
   - Search functionality for books
   - Query email system for user support

## Tools Used

- **Programming Language:** Java (JDK 8+)
- **Development Environment:** Terminal/Command Line
- **Data Structures:** HashMap, HashSet, ArrayList
- **Date Management:** java.util.Date, Calendar, SimpleDateFormat
- **Design Pattern:** Object-Oriented Programming (OOP)
- **Version Control:** Git & GitHub

## Outcome

Successfully created a fully functional Digital Library Management System that demonstrates:

**Admin Capabilities:**
- Complete CRUD operations on books and members
- Comprehensive reporting system
- Full system control and monitoring

**User Capabilities:**
- Easy book browsing and searching
- Simple issue and return process
- Personal book tracking
- Query submission system

**System Features:**
- Role-based access control
- Automated due date calculation
- Real-time availability tracking
- User-friendly console interface
- Centralized data management

The system provides a complete library automation solution with separate modules for administrators and users, ensuring efficient library operations and user satisfaction.

## How to Run

```bash
cd OIBSIP_JavaDevelopement_Task5
javac *.java
java LibraryManagementSystem
```

## Sample Credentials

**Admin Access:**
- Username: admin | Password: admin123

**User Access:**
- Username: user1 | Password: pass123
- Username: user2 | Password: pass456

## Pre-loaded Data

**Books:**
- Java Programming by James Gosling (Programming)
- Data Structures by Robert Lafore (Computer Science)
- Clean Code by Robert Martin (Programming)
- The Alchemist by Paulo Coelho (Fiction)
- Rich Dad Poor Dad by Robert Kiyosaki (Finance)

**Members:**
- M2001 - Aravind Kumar
- M2002 - Priya Sharma

## Features Summary

**Admin Module:**
1. Add New Book
2. Update Book
3. Delete Book
4. View All Books
5. Add New Member
6. Update Member
7. Delete Member
8. View All Members
9. View Issued Books Report

**User Module:**
1. Browse Books by Category
2. Search Book
3. Issue Book
4. Return Book
5. View My Issued Books
6. Send Query Email
