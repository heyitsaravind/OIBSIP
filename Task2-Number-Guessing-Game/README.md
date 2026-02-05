# Interactive Number Guessing Challenge

An engaging console-based number guessing game with multiple difficulty levels, achievements system, and comprehensive statistics tracking.

## 🎮 Game Overview

Challenge your intuition and logical thinking skills by guessing randomly generated numbers within limited attempts. The game features multiple difficulty levels, a scoring system, achievements, and detailed statistics to track your progress.

## 🌟 Features

### Game Modes
- **🟢 EASY** - Numbers 1-50, 10 attempts, 100 base points
- **🟡 MEDIUM** - Numbers 1-100, 7 attempts, 150 base points  
- **🟠 HARD** - Numbers 1-200, 5 attempts, 200 base points
- **🔴 EXPERT** - Numbers 1-500, 3 attempts, 300 base points

### Advanced Features
- **Smart Hint System** - Dynamic hints based on proximity to target
- **Scoring Algorithm** - Points based on difficulty, attempts, and speed
- **Achievement System** - Unlock rewards for special accomplishments
- **Statistics Tracking** - Comprehensive performance analytics
- **Progress Monitoring** - Win rates and improvement tracking

### User Experience
- **Intuitive Interface** - Clean, emoji-rich console design
- **Real-time Feedback** - Immediate response to guesses
- **Motivational Messages** - Encouraging feedback throughout gameplay
- **Replay Options** - Continue playing with different difficulties

## 🚀 Getting Started

### Prerequisites
- Java 8 or higher
- Command line interface

### Installation & Running
```bash
# Compile the Java files
javac *.java

# Run the game
java NumberGuessingChallenge
```

## 🎯 How to Play

### Basic Gameplay
1. **Choose Difficulty** - Select from Easy to Expert mode
2. **Make Guesses** - Enter numbers within the specified range
3. **Use Hints** - Pay attention to "higher/lower" and proximity hints
4. **Win Points** - Complete games to earn points and achievements

### Scoring System
```
Base Score = Difficulty Points
Attempt Bonus = (Max Attempts - Used Attempts) × 20
Speed Bonus = Max(0, 50 - Seconds Taken)
Final Score = Base + Attempt Bonus + Speed Bonus
```

### Strategy Tips
- **Binary Search** - Start with middle values to eliminate ranges efficiently
- **Proximity Hints** - Use "VERY CLOSE", "WARM", "COLD" feedback strategically
- **Time Management** - Balance speed with accuracy for maximum points

## 🏆 Achievements System

### Available Achievements
- **🎯 FIRST SHOT** - Guess correctly on first try
- **⚡ LIGHTNING FAST** - Complete game in under 10 seconds
- **🏆 EXPERT CONQUEROR** - Win on Expert difficulty
- **🌟 FIRST VICTORY** - Win your first game
- **🔥 VETERAN PLAYER** - Win 10 games total

## 📊 Statistics & Analytics

### Tracked Metrics
- **Games Played/Won/Lost** - Complete game history
- **Win Rate Percentage** - Success rate calculation
- **Total & Average Score** - Point accumulation tracking
- **Best Performance** - Fastest time and fewest attempts
- **Difficulty Breakdown** - Performance per difficulty level

### Example Statistics Display
```
📊 YOUR GAME STATISTICS
========================================
🎮 Total Games Played: 25
🏆 Games Won: 18
💔 Games Lost: 7
📈 Win Rate: 72.0%
🏅 Total Score: 2,850
⭐ Average Score: 158.3
⚡ Best Time: 8 seconds
🎯 Best Attempts: 1
```

## 🏗️ Technical Architecture

### Class Structure
- **NumberGuessingChallenge.java** - Main game engine and UI
- **GameStats.java** - Statistics tracking and management

### Key Components
1. **Game Engine** - Core gameplay logic and flow control
2. **Difficulty System** - Configurable game parameters
3. **Hint Algorithm** - Proximity-based feedback system
4. **Statistics Manager** - Performance tracking and analytics
5. **Achievement System** - Progress rewards and milestones
6. **User Interface** - Interactive console menus

### Design Patterns Used
- **Enum Pattern** - Difficulty level configuration
- **Strategy Pattern** - Different scoring algorithms
- **Observer Pattern** - Achievement notification system

## 💡 Game Features Deep Dive

### Intelligent Hint System
```java
// Proximity-based hints
if (difference <= range * 0.05) → "🔥 VERY CLOSE!"
if (difference <= range * 0.15) → "🎯 WARMER!"
if (difference <= range * 0.30) → "🌡️ WARM!"
else → "❄️ COLD!"
```

### Dynamic Scoring Algorithm
- **Difficulty Multiplier** - Higher difficulties yield more points
- **Efficiency Bonus** - Rewards for fewer attempts
- **Speed Bonus** - Time-based performance rewards
- **Consistency Tracking** - Long-term performance analysis

## 🎨 User Interface Design

### Menu System
- **Main Menu** - Game start, statistics, achievements, settings
- **Difficulty Selection** - Visual difficulty indicators
- **Game Interface** - Clear feedback and progress indicators
- **Statistics Dashboard** - Comprehensive performance overview

### Visual Elements
- **Emoji Integration** - Enhanced visual appeal and clarity
- **Color Coding** - Difficulty levels and status indicators
- **Progress Bars** - Visual representation of attempts remaining
- **Formatted Output** - Clean, organized information display

## 🔧 Customization Options

### Settings Menu
- **Reset Statistics** - Clear all performance data
- **Clear Achievements** - Remove earned achievements
- **Export Statistics** - Generate performance reports
- **Difficulty Adjustment** - Modify game parameters

## 🎓 Educational Value

### Programming Concepts Demonstrated
- **Random Number Generation** - Secure randomization techniques
- **Input Validation** - Robust error handling
- **Data Structures** - Collections and maps usage
- **Algorithm Design** - Hint generation and scoring logic
- **Object-Oriented Design** - Clean class architecture
- **User Experience Design** - Intuitive interface creation

### Problem-Solving Skills
- **Logical Reasoning** - Strategic number selection
- **Pattern Recognition** - Hint interpretation
- **Risk Assessment** - Attempt management
- **Performance Optimization** - Speed vs accuracy balance

## 🔮 Future Enhancements

### Planned Features
- **Multiplayer Mode** - Compete with friends
- **Custom Ranges** - User-defined number ranges
- **Hint Levels** - Adjustable hint difficulty
- **Sound Effects** - Audio feedback integration
- **Leaderboards** - Global score comparison
- **Theme System** - Customizable visual themes

### Advanced Features
- **AI Opponent** - Computer player with different strategies
- **Tournament Mode** - Structured competition format
- **Statistics Export** - CSV/JSON data export
- **Achievement Sharing** - Social media integration

## 👨‍💻 Author

**Aravind M S**
- Email: aravindms046@gmail.com
- GitHub: heyitsaravind
- Project: OIBSIP Java Development Internship

## 📝 License

This project is created for educational purposes as part of the Oasis Infobyte internship program.

---

*Challenge your mind, improve your logic, and have fun! 🎯*