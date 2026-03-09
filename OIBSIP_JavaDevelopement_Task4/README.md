# Task 4: Number Guessing Game

## Objective
Develop a fun and interactive console-based number guessing game where users try to guess a randomly generated number within a limited number of attempts, with scoring based on performance.

## Steps Performed

1. **System Design**
   - Designed single-class game architecture
   - Implemented random number generation (1-100)
   - Created attempt-based gameplay with scoring system

2. **Core Features Implementation**
   - Developed random number generation within specified range
   - Created user input prompt with dialogue-style interaction
   - Implemented higher/lower feedback system
   - Built attempt limiting (10 attempts per round)
   - Added multiple rounds functionality
   - Created score display and tracking
   - Implemented points system based on attempts used

3. **Game Mechanics**
   - Random number generation between 1 and 100
   - User guess validation and comparison
   - Higher/Lower hints after each guess
   - Attempt counter with remaining attempts display
   - Round-based gameplay
   - Score calculation (fewer attempts = higher points)

4. **Scoring System**
   - 1 attempt: 100 points
   - 2-3 attempts: 80 points
   - 4-5 attempts: 60 points
   - 6-7 attempts: 40 points
   - 8-9 attempts: 20 points
   - 10 attempts: 10 points
   - Failed round: 0 points

## Tools Used

- **Programming Language:** Java (JDK 8+)
- **Development Environment:** Terminal/Command Line
- **Random Generation:** java.util.Random
- **Design Pattern:** Procedural Programming
- **Version Control:** Git & GitHub

## Outcome

Successfully created a fully functional Number Guessing Game that demonstrates:
- Random number generation within specified range
- Interactive user input with clear prompts
- Real-time feedback (higher/lower hints)
- Attempt limitation (10 attempts per round)
- Multiple rounds support
- Score tracking and display
- Points awarded based on number of attempts
- Performance rating system
- Professional console interface with clear game flow

The game provides an engaging experience with progressive difficulty through the scoring system that rewards efficient guessing.

## How to Run

```bash
cd OIBSIP_JavaDevelopement_Task4
javac *.java
java NumberGuessingGame
```

## Game Features
- Range: 1 to 100
- Maximum Attempts: 10 per round
- Multiple Rounds: Play as many rounds as you want
- Scoring: Based on attempts used (fewer = better)
- Performance Rating: Based on total score
