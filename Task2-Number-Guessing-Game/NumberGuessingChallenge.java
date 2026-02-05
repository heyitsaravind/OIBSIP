/**
 * Interactive Number Guessing Challenge
 * A fun and engaging console-based guessing game with multiple difficulty levels
 * Author: Aravind M S
 * Created for: OIBSIP Java Development Internship
 */

import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NumberGuessingChallenge {
    private static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random();
    private static GameStats playerStats = new GameStats();
    
    // Game difficulty settings
    private enum Difficulty {
        EASY(1, 50, 10, 100),
        MEDIUM(1, 100, 7, 150),
        HARD(1, 200, 5, 200),
        EXPERT(1, 500, 3, 300);
        
        final int min, max, attempts, points;
        
        Difficulty(int min, int max, int attempts, int points) {
            this.min = min;
            this.max = max;
            this.attempts = attempts;
            this.points = points;
        }
    }
    
    public static void main(String[] args) {
        displayWelcomeScreen();
        
        while (true) {
            showMainMenu();
        }
    }
    
    private static void displayWelcomeScreen() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("🎯  WELCOME TO NUMBER GUESSING CHALLENGE  🎯");
        System.out.println("    Test Your Intuition & Logical Thinking!");
        System.out.println("=".repeat(60));
        System.out.println("🎮 Ready to challenge your mind?");
        System.out.println("🏆 Compete for high scores and achievements!");
        System.out.println("📊 Track your progress and improve your skills!");
        System.out.println("=".repeat(60));
    }
    
    private static void showMainMenu() {
        System.out.println("\n🎮 GAME MENU");
        System.out.println("=".repeat(30));
        System.out.println("1. 🎯 Start New Game");
        System.out.println("2. 📊 View Statistics");
        System.out.println("3. 🏆 Achievements");
        System.out.println("4. ❓ How to Play");
        System.out.println("5. ⚙️  Settings");
        System.out.println("6. 🚪 Exit Game");
        System.out.print("\n👉 Choose option (1-6): ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            
            switch (choice) {
                case 1:
                    startNewGame();
                    break;
                case 2:
                    showStatistics();
                    break;
                case 3:
                    showAchievements();
                    break;
                case 4:
                    showInstructions();
                    break;
                case 5:
                    showSettings();
                    break;
                case 6:
                    exitGame();
                    break;
                default:
                    System.out.println("❌ Invalid choice! Please select 1-6.");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Please enter a valid number!");
        }
    }
    
    private static void startNewGame() {
        System.out.println("\n🎯 SELECT DIFFICULTY LEVEL");
        System.out.println("=".repeat(40));
        System.out.println("1. 🟢 EASY   (1-50,  10 attempts, 100 pts)");
        System.out.println("2. 🟡 MEDIUM (1-100, 7 attempts,  150 pts)");
        System.out.println("3. 🟠 HARD   (1-200, 5 attempts,  200 pts)");
        System.out.println("4. 🔴 EXPERT (1-500, 3 attempts,  300 pts)");
        System.out.println("5. 🔙 Back to Main Menu");
        System.out.print("\n👉 Select difficulty (1-5): ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            
            if (choice >= 1 && choice <= 4) {
                Difficulty difficulty = Difficulty.values()[choice - 1];
                playGame(difficulty);
            } else if (choice == 5) {
                return; // Back to main menu
            } else {
                System.out.println("❌ Invalid choice! Please select 1-5.");
                startNewGame();
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Please enter a valid number!");
            startNewGame();
        }
    }
    
    private static void playGame(Difficulty difficulty) {
        int targetNumber = random.nextInt(difficulty.max - difficulty.min + 1) + difficulty.min;
        int attemptsLeft = difficulty.attempts;
        int totalAttempts = 0;
        boolean gameWon = false;
        long startTime = System.currentTimeMillis();
        
        System.out.println("\n🎮 GAME STARTED - " + difficulty.name() + " MODE");
        System.out.println("=".repeat(45));
        System.out.println("🎯 I'm thinking of a number between " + difficulty.min + " and " + difficulty.max);
        System.out.println("🎲 You have " + difficulty.attempts + " attempts to guess it!");
        System.out.println("💡 Hint: Use logical reasoning to narrow down the range!");
        System.out.println("=".repeat(45));
        
        while (attemptsLeft > 0 && !gameWon) {
            System.out.print("\n🤔 Attempt " + (totalAttempts + 1) + "/" + difficulty.attempts + 
                           " - Enter your guess: ");
            
            try {
                int guess = Integer.parseInt(scanner.nextLine().trim());
                totalAttempts++;
                attemptsLeft--;
                
                if (guess < difficulty.min || guess > difficulty.max) {
                    System.out.println("⚠️  Number must be between " + difficulty.min + 
                                     " and " + difficulty.max + "!");
                    attemptsLeft++; // Don't count invalid guesses
                    totalAttempts--;
                    continue;
                }
                
                if (guess == targetNumber) {
                    gameWon = true;
                    long endTime = System.currentTimeMillis();
                    int timeTaken = (int) ((endTime - startTime) / 1000);
                    
                    System.out.println("\n🎉 CONGRATULATIONS! YOU WON! 🎉");
                    System.out.println("=".repeat(40));
                    System.out.println("✅ Correct Number: " + targetNumber);
                    System.out.println("🎯 Attempts Used: " + totalAttempts + "/" + difficulty.attempts);
                    System.out.println("⏱️  Time Taken: " + timeTaken + " seconds");
                    
                    int score = calculateScore(difficulty, totalAttempts, timeTaken);
                    System.out.println("🏆 Score Earned: " + score + " points");
                    
                    playerStats.addGame(true, difficulty, totalAttempts, timeTaken, score);
                    checkForAchievements(difficulty, totalAttempts, timeTaken);
                    
                } else if (guess < targetNumber) {
                    System.out.println("📈 Too LOW! The number is HIGHER than " + guess);
                    if (attemptsLeft > 0) {
                        System.out.println("💪 " + attemptsLeft + " attempts remaining. You can do this!");
                        provideHint(guess, targetNumber, difficulty);
                    }
                } else {
                    System.out.println("📉 Too HIGH! The number is LOWER than " + guess);
                    if (attemptsLeft > 0) {
                        System.out.println("💪 " + attemptsLeft + " attempts remaining. Keep trying!");
                        provideHint(guess, targetNumber, difficulty);
                    }
                }
                
            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid number!");
                attemptsLeft++; // Don't count invalid input
                totalAttempts--;
            }
        }
        
        if (!gameWon) {
            System.out.println("\n💔 GAME OVER!");
            System.out.println("=".repeat(25));
            System.out.println("😔 You've run out of attempts!");
            System.out.println("🎯 The correct number was: " + targetNumber);
            System.out.println("💡 Better luck next time! Practice makes perfect!");
            
            playerStats.addGame(false, difficulty, totalAttempts, 0, 0);
        }
        
        askForReplay();
    }
    
    private static void provideHint(int guess, int target, Difficulty difficulty) {
        int difference = Math.abs(guess - target);
        int range = difficulty.max - difficulty.min;
        
        if (difference <= range * 0.05) {
            System.out.println("🔥 You're VERY CLOSE! Almost there!");
        } else if (difference <= range * 0.15) {
            System.out.println("🎯 Getting WARMER! You're on the right track!");
        } else if (difference <= range * 0.3) {
            System.out.println("🌡️  WARM! You're in the right neighborhood!");
        } else {
            System.out.println("❄️  COLD! Try a different range!");
        }
    }
    
    private static int calculateScore(Difficulty difficulty, int attempts, int timeTaken) {
        int baseScore = difficulty.points;
        
        // Bonus for fewer attempts
        int attemptBonus = (difficulty.attempts - attempts) * 20;
        
        // Bonus for speed (max 50 points)
        int speedBonus = Math.max(0, 50 - timeTaken);
        
        return baseScore + attemptBonus + speedBonus;
    }
    
    private static void checkForAchievements(Difficulty difficulty, int attempts, int timeTaken) {
        List<String> newAchievements = new ArrayList<>();
        
        if (attempts == 1) {
            newAchievements.add("🎯 FIRST SHOT - Guessed correctly on first try!");
        }
        
        if (timeTaken <= 10) {
            newAchievements.add("⚡ LIGHTNING FAST - Completed in under 10 seconds!");
        }
        
        if (difficulty == Difficulty.EXPERT) {
            newAchievements.add("🏆 EXPERT CONQUEROR - Mastered the hardest difficulty!");
        }
        
        if (playerStats.getTotalGamesWon() == 1) {
            newAchievements.add("🌟 FIRST VICTORY - Won your first game!");
        }
        
        if (playerStats.getTotalGamesWon() == 10) {
            newAchievements.add("🔥 VETERAN PLAYER - Won 10 games!");
        }
        
        if (!newAchievements.isEmpty()) {
            System.out.println("\n🏆 NEW ACHIEVEMENTS UNLOCKED!");
            System.out.println("=".repeat(35));
            for (String achievement : newAchievements) {
                System.out.println(achievement);
                playerStats.addAchievement(achievement);
            }
        }
    }
    
    private static void askForReplay() {
        System.out.println("\n🎮 PLAY AGAIN?");
        System.out.println("1. 🔄 Play Same Difficulty");
        System.out.println("2. 🎯 Choose New Difficulty");
        System.out.println("3. 🏠 Return to Main Menu");
        System.out.print("\n👉 Your choice (1-3): ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            
            switch (choice) {
                case 1:
                    // This would require storing the last difficulty
                    startNewGame();
                    break;
                case 2:
                    startNewGame();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("❌ Invalid choice! Returning to main menu.");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid input! Returning to main menu.");
        }
    }
    
    private static void showStatistics() {
        System.out.println("\n📊 YOUR GAME STATISTICS");
        System.out.println("=".repeat(40));
        System.out.println("🎮 Total Games Played: " + playerStats.getTotalGamesPlayed());
        System.out.println("🏆 Games Won: " + playerStats.getTotalGamesWon());
        System.out.println("💔 Games Lost: " + playerStats.getTotalGamesLost());
        System.out.println("📈 Win Rate: " + String.format("%.1f%%", playerStats.getWinRate()));
        System.out.println("🏅 Total Score: " + playerStats.getTotalScore());
        System.out.println("⭐ Average Score: " + String.format("%.1f", playerStats.getAverageScore()));
        System.out.println("⚡ Best Time: " + playerStats.getBestTime() + " seconds");
        System.out.println("🎯 Best Attempts: " + playerStats.getBestAttempts());
        System.out.println("=".repeat(40));
        
        if (playerStats.getTotalGamesPlayed() > 0) {
            System.out.println("\n📈 DIFFICULTY BREAKDOWN:");
            System.out.println("-".repeat(30));
            for (Difficulty diff : Difficulty.values()) {
                int wins = playerStats.getWinsForDifficulty(diff);
                int total = playerStats.getGamesForDifficulty(diff);
                if (total > 0) {
                    System.out.printf("%-8s: %d/%d wins (%.1f%%)%n", 
                        diff.name(), wins, total, (wins * 100.0 / total));
                }
            }
        }
    }
    
    private static void showAchievements() {
        System.out.println("\n🏆 YOUR ACHIEVEMENTS");
        System.out.println("=".repeat(35));
        
        List<String> achievements = playerStats.getAchievements();
        if (achievements.isEmpty()) {
            System.out.println("🎯 No achievements yet! Start playing to unlock them!");
            System.out.println("\n💡 Available Achievements:");
            System.out.println("🎯 FIRST SHOT - Guess correctly on first try");
            System.out.println("⚡ LIGHTNING FAST - Complete in under 10 seconds");
            System.out.println("🏆 EXPERT CONQUEROR - Win on Expert difficulty");
            System.out.println("🌟 FIRST VICTORY - Win your first game");
            System.out.println("🔥 VETERAN PLAYER - Win 10 games");
        } else {
            for (String achievement : achievements) {
                System.out.println(achievement);
            }
            System.out.println("\n🎉 Total Achievements: " + achievements.size());
        }
        System.out.println("=".repeat(35));
    }
    
    private static void showInstructions() {
        System.out.println("\n❓ HOW TO PLAY");
        System.out.println("=".repeat(50));
        System.out.println("🎯 OBJECTIVE:");
        System.out.println("   Guess the secret number within the given attempts!");
        System.out.println();
        System.out.println("🎮 GAMEPLAY:");
        System.out.println("   1. Choose your difficulty level");
        System.out.println("   2. Enter your guess when prompted");
        System.out.println("   3. Use the hints to narrow down your next guess");
        System.out.println("   4. Win by guessing the correct number!");
        System.out.println();
        System.out.println("🏆 SCORING:");
        System.out.println("   • Base points depend on difficulty");
        System.out.println("   • Bonus points for fewer attempts");
        System.out.println("   • Speed bonus for quick completion");
        System.out.println();
        System.out.println("💡 TIPS:");
        System.out.println("   • Use binary search strategy for efficiency");
        System.out.println("   • Pay attention to the hint messages");
        System.out.println("   • Start with middle values to eliminate ranges");
        System.out.println("=".repeat(50));
    }
    
    private static void showSettings() {
        System.out.println("\n⚙️  GAME SETTINGS");
        System.out.println("=".repeat(30));
        System.out.println("1. 🔄 Reset Statistics");
        System.out.println("2. 🏆 Clear Achievements");
        System.out.println("3. 📊 Export Statistics");
        System.out.println("4. 🔙 Back to Main Menu");
        System.out.print("\n👉 Choose option (1-4): ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            
            switch (choice) {
                case 1:
                    resetStatistics();
                    break;
                case 2:
                    clearAchievements();
                    break;
                case 3:
                    exportStatistics();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("❌ Invalid choice!");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Please enter a valid number!");
        }
    }
    
    private static void resetStatistics() {
        System.out.print("\n⚠️  Are you sure you want to reset all statistics? (y/N): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        
        if (confirm.equals("y") || confirm.equals("yes")) {
            playerStats.reset();
            System.out.println("✅ Statistics have been reset!");
        } else {
            System.out.println("❌ Reset cancelled.");
        }
    }
    
    private static void clearAchievements() {
        System.out.print("\n⚠️  Are you sure you want to clear all achievements? (y/N): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        
        if (confirm.equals("y") || confirm.equals("yes")) {
            playerStats.clearAchievements();
            System.out.println("✅ Achievements have been cleared!");
        } else {
            System.out.println("❌ Clear cancelled.");
        }
    }
    
    private static void exportStatistics() {
        System.out.println("\n📊 STATISTICS EXPORT");
        System.out.println("=".repeat(30));
        System.out.println("Generated on: " + LocalDateTime.now().format(
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        System.out.println();
        showStatistics();
        System.out.println("\n💾 Statistics displayed above can be copied for your records!");
    }
    
    private static void exitGame() {
        System.out.println("\n👋 THANKS FOR PLAYING!");
        System.out.println("=".repeat(35));
        System.out.println("🎮 Final Stats:");
        System.out.println("   Games Played: " + playerStats.getTotalGamesPlayed());
        System.out.println("   Games Won: " + playerStats.getTotalGamesWon());
        System.out.println("   Total Score: " + playerStats.getTotalScore());
        System.out.println();
        System.out.println("🌟 Keep challenging your mind!");
        System.out.println("🎯 Come back anytime for more fun!");
        System.out.println("=".repeat(35));
        System.exit(0);
    }
}