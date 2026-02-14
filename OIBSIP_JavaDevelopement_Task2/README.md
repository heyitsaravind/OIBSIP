# Task 2: Online Examination System

## Objective
Develop a console-based online examination platform that conducts timed MCQ tests with automatic submission, score calculation, and detailed answer key display.

## Steps Performed

1. **System Design**
   - Designed four-class architecture (ExamSystem, UserProfile, ExamSession, Question)
   - Implemented secure username/password authentication
   - Created menu-driven examination interface

2. **Core Features Implementation**
   - Developed user profile management (update name, email, password)
   - Created MCQ question system with 4 options per question
   - Implemented 5-minute countdown timer with real-time display
   - Built automatic submission on timeout
   - Added score calculation and result display
   - Created comprehensive answer key with correct/incorrect marking

3. **Data Management**
   - Used HashMap for user account storage and answer tracking
   - Implemented ArrayList for question management
   - Created Question class for MCQ structure
   - Built ExamSession class for exam state management

4. **Validation & Security**
   - Added email format validation
   - Implemented password strength checking (minimum 6 characters)
   - Created password confirmation matching
   - Added answer validation (1-4 only)
   - Implemented timer-based auto-submission

## Tools Used

- **Programming Language:** Java (JDK 8+)
- **Development Environment:** Terminal/Command Line
- **Data Structures:** HashMap, ArrayList
- **Design Pattern:** Object-Oriented Programming (OOP)
- **Timer Management:** Date and SimpleDateFormat classes
- **Version Control:** Git & GitHub

## Outcome

Successfully created a fully functional Online Examination System that demonstrates:
- Secure user authentication and profile management
- Interactive MCQ interface with real-time timer
- Automatic submission on timeout
- Accurate score calculation
- Detailed answer key showing correct answers for wrong responses
- Professional console interface with clear navigation

The system provides a realistic online exam experience with time pressure simulation and immediate feedback through answer keys.

## How to Run

```bash
cd OIBSIP_JavaDevelopement_Task2
javac *.java
java ExamSystem
```

## Sample Credentials
- Username: student1 | Password: pass123
- Username: student2 | Password: pass456
