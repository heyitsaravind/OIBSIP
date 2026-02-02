import java.util.Random;
import java.util.Scanner;

/**
 * Guess the Number Game
 * A console-based game where players try to guess a randomly generated number.
 * 
 * Features:
 * - Random number generation (1-100)
 * - Limited attempts (7 per round)
 * - Multiple rounds
 * - Scoring system based on attempts
 * - Game statistics
 * 
 * @author Aravind M S
 * @version 1.0
 */
public class GuessTheNumberGame {
    
    // Game constants
    private static final int MIN_NUMBER = 1;
    private static final int MAX_NUMBER = 100;
    private static final int MAX_ATTEMPTS = 7;
    
    // Game state variables
    private Scanner scanner;
    private Random random;
    private int totalScore;
    private int roundsPlayed;
    private int roundsWon;
    
    /**
     * Constructor - Initialize game components
     */
    public GuessTheNumberGame() {
        scanner = new Scanner(System.in);
        random = new Random();
        totalScore = 0;
        roundsPlayed = 0;
        roundsWon = 0;
    }
    
    /**
     * Main method - Entry point of the game
     */
    public static void main(String[] args) {
        GuessTheNumberGame game = new GuessTheNumberGame();
        game.startGame();
    }
    
    /**
     * Start the game and handle main game loop
     */
    public void startGame() {
        displayWelcomeMessage();
        
        boolean playAgain = true;
        while (playAgain) {
            playRound();
            playAgain = askPlayAgain();
        }
        
        displayFinalStatistics();
        scanner.close();
        System.out.println("Thanks for playing! Goodbye!");
    }
    
    /**
     * Display welcome message and game instructions
     */
    private void displayWelcomeMessage() {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║        GUESS THE NUMBER GAME         ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.println();
        System.out.println("🎯 Welcome to the Number Guessing Game!");
        System.out.println("📋 RULES:");
        System.out.println("   • I'll think of a number between " + MIN_NUMBER + " and " + MAX_NUMBER);
        System.out.println("   • You have " + MAX_ATTEMPTS + " attempts to guess it");
        System.out.println("   • I'll tell you if your guess is too high or too low");
        System.out.println("   • Fewer attempts = Higher score!");
        System.out.println();
        System.out.println("🏆 SCORING:");
        System.out.println("   • 1st attempt: 100 points");
        System.out.println("   • 2nd attempt: 85 points");
        System.out.println("   • 3rd attempt: 70 points");
        System.out.println("   • 4th attempt: 55 points");
        System.out.println("   • 5th attempt: 40 points");
        System.out.println("   • 6th attempt: 25 points");
        System.out.println("   • 7th attempt: 10 points");
        System.out.println();
        System.out.println("Let's begin! 🚀");
        System.out.println("═".repeat(50));
    }
    
    /**
     * Play a single round of the game
     */
    private void playRound() {
        roundsPlayed++;
        int targetNumber = random.nextInt(MAX_NUMBER - MIN_NUMBER + 1) + MIN_NUMBER;
        int attempts = 0;
        boolean hasWon = false;
        
        System.out.println("\n🎮 ROUND " + roundsPlayed);
        System.out.println("I'm thinking of a number between " + MIN_NUMBER + " and " + MAX_NUMBER + "...");
        System.out.println("You have " + MAX_ATTEMPTS + " attempts. Good luck!");
        System.out.println();
        
        while (attempts < MAX_ATTEMPTS && !hasWon) {
            attempts++;
            int guess = getValidGuess(attempts);
            
            if (guess == targetNumber) {
                hasWon = true;
                roundsWon++;
                int roundScore = calculateScore(attempts);
                totalScore += roundScore;
                
                displaySuccessMessage(attempts, roundScore);
            } else if (guess < targetNumber) {
                System.out.println("📈 Too low! Try a higher number.");
                displayAttemptsRemaining(attempts);
            } else {
                System.out.println("📉 Too high! Try a lower number.");
                displayAttemptsRemaining(attempts);
            }
        }
        
        if (!hasWon) {
            System.out.println("\n💔 Game Over! You've used all " + MAX_ATTEMPTS + " attempts.");
            System.out.println("🎯 The number was: " + targetNumber);
            System.out.println("💪 Better luck next time!");
        }
        
        displayRoundSummary();
    }
    
    /**
     * Get a valid guess from the user with input validation
     */
    private int getValidGuess(int attemptNumber) {
        int guess = -1;
        boolean validInput = false;
        
        while (!validInput) {
            System.out.print("🔢 Attempt " + attemptNumber + "/" + MAX_ATTEMPTS + " - Enter your guess: ");
            
            try {
                if (scanner.hasNextInt()) {
                    guess = scanner.nextInt();
                    if (guess >= MIN_NUMBER && guess <= MAX_NUMBER) {
                        validInput = true;
                    } else {
                        System.out.println("❌ Please enter a number between " + MIN_NUMBER + " and " + MAX_NUMBER + "!");
                    }
                } else {
                    System.out.println("❌ Please enter a valid number!");
                    scanner.next(); // Clear invalid input
                }
            } catch (Exception e) {
                System.out.println("❌ Invalid input! Please enter a number.");
                scanner.next(); // Clear invalid input
            }
        }
        
        return guess;
    }
    
    /**
     * Calculate score based on number of attempts
     */
    private int calculateScore(int attempts) {
        switch (attempts) {
            case 1: return 100;
            case 2: return 85;
            case 3: return 70;
            case 4: return 55;
            case 5: return 40;
            case 6: return 25;
            case 7: return 10;
            default: return 0;
        }
    }
    
    /**
     * Display success message when player wins
     */
    private void displaySuccessMessage(int attempts, int score) {
        System.out.println("\n🎉 CONGRATULATIONS! 🎉");
        System.out.println("✅ You guessed it in " + attempts + " attempt" + (attempts == 1 ? "" : "s") + "!");
        System.out.println("🏆 Round Score: " + score + " points");
        
        if (attempts == 1) {
            System.out.println("🌟 AMAZING! First try - you're a mind reader!");
        } else if (attempts <= 3) {
            System.out.println("🌟 EXCELLENT! Great guessing skills!");
        } else if (attempts <= 5) {
            System.out.println("👍 GOOD JOB! Nice work!");
        } else {
            System.out.println("😅 PHEW! Made it just in time!");
        }
    }
    
    /**
     * Display remaining attempts
     */
    private void displayAttemptsRemaining(int usedAttempts) {
        int remaining = MAX_ATTEMPTS - usedAttempts;
        if (remaining > 0) {
            System.out.println("⏳ Attempts remaining: " + remaining);
            System.out.println();
        }
    }
    
    /**
     * Display round summary
     */
    private void displayRoundSummary() {
        System.out.println("\n" + "─".repeat(30));
        System.out.println("📊 ROUND SUMMARY");
        System.out.println("─".repeat(30));
        System.out.println("🎯 Rounds Played: " + roundsPlayed);
        System.out.println("🏆 Rounds Won: " + roundsWon);
        System.out.println("💯 Total Score: " + totalScore);
        if (roundsPlayed > 0) {
            double winRate = (double) roundsWon / roundsPlayed * 100;
            System.out.println("📈 Win Rate: " + String.format("%.1f", winRate) + "%");
        }
        System.out.println("─".repeat(30));
    }
    
    /**
     * Ask if player wants to play another round
     */
    private boolean askPlayAgain() {
        System.out.println("\n🎮 Would you like to play another round?");
        System.out.print("💭 Enter 'y' for Yes or 'n' for No: ");
        
        String response = scanner.next().toLowerCase().trim();
        while (!response.equals("y") && !response.equals("n") && 
               !response.equals("yes") && !response.equals("no")) {
            System.out.print("❌ Please enter 'y' for Yes or 'n' for No: ");
            response = scanner.next().toLowerCase().trim();
        }
        
        return response.equals("y") || response.equals("yes");
    }
    
    /**
     * Display final game statistics
     */
    private void displayFinalStatistics() {
        System.out.println("\n" + "═".repeat(50));
        System.out.println("🏁 FINAL GAME STATISTICS");
        System.out.println("═".repeat(50));
        System.out.println("🎯 Total Rounds Played: " + roundsPlayed);
        System.out.println("🏆 Total Rounds Won: " + roundsWon);
        System.out.println("💔 Rounds Lost: " + (roundsPlayed - roundsWon));
        System.out.println("💯 Final Score: " + totalScore);
        
        if (roundsPlayed > 0) {
            double winRate = (double) roundsWon / roundsPlayed * 100;
            double averageScore = (double) totalScore / roundsPlayed;
            
            System.out.println("📈 Win Rate: " + String.format("%.1f", winRate) + "%");
            System.out.println("📊 Average Score per Round: " + String.format("%.1f", averageScore));
            
            // Performance rating
            System.out.println("\n🌟 PERFORMANCE RATING:");
            if (winRate >= 80 && averageScore >= 70) {
                System.out.println("🏆 MASTER GUESSER - Outstanding performance!");
            } else if (winRate >= 60 && averageScore >= 50) {
                System.out.println("🥈 SKILLED PLAYER - Great job!");
            } else if (winRate >= 40) {
                System.out.println("🥉 GOOD EFFORT - Keep practicing!");
            } else {
                System.out.println("💪 BEGINNER - Don't give up, you'll improve!");
            }
        }
        
        System.out.println("═".repeat(50));
        System.out.println("🎮 Thank you for playing the Guess the Number Game!");
        System.out.println("👨‍💻 Created by: Aravind M S");
        System.out.println("📧 Contact: aravindms046@gmail.com");
        System.out.println("═".repeat(50));
    }
}