# Guess the Number Game

A console-based Java game where players attempt to guess a randomly generated number between 1 and 100.

## How to Play

1. The computer generates a random number between 1 and 100
2. You have 7 attempts to guess the correct number
3. After each guess, you'll receive feedback:
   - "Too high!" if your guess is above the target
   - "Too low!" if your guess is below the target
   - "Correct!" if you guessed right
4. Points are awarded based on how quickly you guess (fewer attempts = more points)
5. Play multiple rounds and track your total score

## Features

- **Random Number Generation**: Each round uses a new random number
- **Attempt Limiting**: Maximum 7 attempts per round
- **Smart Feedback**: Guides you toward the correct answer
- **Scoring System**: Points based on attempts used
- **Multiple Rounds**: Play as many rounds as you want
- **Statistics**: Track your performance across rounds

## How to Run

```bash
cd src
javac GuessTheNumberGame.java
java GuessTheNumberGame
```

## Scoring System

- 1st attempt: 100 points
- 2nd attempt: 85 points
- 3rd attempt: 70 points
- 4th attempt: 55 points
- 5th attempt: 40 points
- 6th attempt: 25 points
- 7th attempt: 10 points
- Failed to guess: 0 points

---

**Author:** Aravind M S  
**Project:** Oasis Infobyte Internship Task