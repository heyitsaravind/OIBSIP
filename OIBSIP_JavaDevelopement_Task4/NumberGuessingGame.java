/**
 * Number Guessing Game
 * A fun game where users guess a randomly generated number
 * Author: Aravind M S
 * Created for: OIBSIP Java Development Internship
 */

import java.util.*;

public class NumberGuessingGame {
    private static Scanner input = new Scanner(System.in);
    private static final int MIN_RANGE = 1;
    private static final int MAX_RANGE = 100;
    private static final int MAX_ATTEMPTS = 10;
    private static int totalScore = 0;
    private static int roundsPlayed = 0;
    
    public static void main(String[] args) {
        displayWelcomeBanner();
        
        boolean playAgain = true;
        
        while (playAgain) {
            playRound();
            playAgain = askPlayAgain();
        }
        
        displayFinalScore();
        System.out.println("\nThank you for playing!");
        System.out.println("Goodbye!");
    }
    
    private static void displayWelcomeBanner() {
        System.out.println("\n" + repeatChar('=', 55));
        System.out.println("          WELCOME TO NUMBER GUESSING GAME");
        System.out.println("              Test Your Guessing Skills!");
        System.out.println(repeatChar('=', 55));
        System.out.println("\nGame Rules:");
        System.out.println("- Guess a number between " + MIN_RANGE + " and " + MAX_RANGE);
        System.out.println("- You have " + MAX_ATTEMPTS + " attempts per round");
        System.out.println("- Points are awarded based on attempts used");
        System.out.println("- Fewer attempts = Higher score!");
        System.out.println(repeatChar('=', 55));
    }
    
    private static void playRound() {
        roundsPlayed++;
        Random random = new Random();
        int targetNumber = random.nextInt(MAX_RANGE - MIN_RANGE + 1) + MIN_RANGE;
        int attempts = 0;
        boolean guessedCorrectly = false;
        
        System.out.println("\n" + repeatChar('=', 55));
        System.out.println("ROUND " + roundsPlayed);
        System.out.println(repeatChar('=', 55));
        System.out.println("A number has been generated between " + MIN_RANGE + " and " + MAX_RANGE);
        System.out.println("You have " + MAX_ATTEMPTS + " attempts to guess it!");
        System.out.println(repeatChar('-', 55));
        
        while (attempts < MAX_ATTEMPTS && !guessedCorrectly) {
            attempts++;
            System.out.print("\nAttempt " + attempts + "/" + MAX_ATTEMPTS + " - Enter your guess: ");
            
            try {
                int userGuess = Integer.parseInt(input.nextLine().trim());
                
                if (userGuess < MIN_RANGE || userGuess > MAX_RANGE) {
                    System.out.println("Please enter a number between " + MIN_RANGE + " and " + MAX_RANGE);
                    attempts--; // Don't count invalid input as an attempt
                    continue;
                }
                
                if (userGuess == targetNumber) {
                    guessedCorrectly = true;
                    int roundScore = calculateScore(attempts);
                    totalScore += roundScore;
                    
                    System.out.println("\n" + repeatChar('=', 55));
                    System.out.println("CONGRATULATIONS! YOU GUESSED IT RIGHT!");
                    System.out.println(repeatChar('=', 55));
                    System.out.println("The number was: " + targetNumber);
                    System.out.println("You guessed it in " + attempts + " attempts!");
                    System.out.println("Round Score: " + roundScore + " points");
                    System.out.println("Total Score: " + totalScore + " points");
                    System.out.println(repeatChar('=', 55));
                    
                } else if (userGuess < targetNumber) {
                    System.out.println("Too low! The number is HIGHER than " + userGuess);
                    System.out.println("Attempts remaining: " + (MAX_ATTEMPTS - attempts));
                    
                } else {
                    System.out.println("Too high! The number is LOWER than " + userGuess);
                    System.out.println("Attempts remaining: " + (MAX_ATTEMPTS - attempts));
                }
                
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number.");
                attempts--; // Don't count invalid input as an attempt
            }
        }
        
        if (!guessedCorrectly) {
            System.out.println("\n" + repeatChar('=', 55));
            System.out.println("GAME OVER - OUT OF ATTEMPTS!");
            System.out.println(repeatChar('=', 55));
            System.out.println("The correct number was: " + targetNumber);
            System.out.println("Better luck next time!");
            System.out.println("Round Score: 0 points");
            System.out.println("Total Score: " + totalScore + " points");
            System.out.println(repeatChar('=', 55));
        }
    }
    
    private static int calculateScore(int attempts) {
        // Scoring system: fewer attempts = higher score
        // 1 attempt: 100 points
        // 2-3 attempts: 80 points
        // 4-5 attempts: 60 points
        // 6-7 attempts: 40 points
        // 8-9 attempts: 20 points
        // 10 attempts: 10 points
        
        if (attempts == 1) {
            return 100;
        } else if (attempts <= 3) {
            return 80;
        } else if (attempts <= 5) {
            return 60;
        } else if (attempts <= 7) {
            return 40;
        } else if (attempts <= 9) {
            return 20;
        } else {
            return 10;
        }
    }
    
    private static boolean askPlayAgain() {
        System.out.print("\nDo you want to play another round? (yes/no): ");
        String response = input.nextLine().trim().toLowerCase();
        return response.equals("yes") || response.equals("y");
    }
    
    private static void displayFinalScore() {
        System.out.println("\n" + repeatChar('=', 55));
        System.out.println("GAME SUMMARY");
        System.out.println(repeatChar('=', 55));
        System.out.println("Total Rounds Played: " + roundsPlayed);
        System.out.println("Final Score: " + totalScore + " points");
        
        if (roundsPlayed > 0) {
            double averageScore = (double) totalScore / roundsPlayed;
            System.out.println("Average Score per Round: " + String.format("%.2f", averageScore) + " points");
        }
        
        System.out.println(repeatChar('=', 55));
        
        // Performance rating
        if (totalScore >= 300) {
            System.out.println("Performance: EXCELLENT! You're a guessing master!");
        } else if (totalScore >= 200) {
            System.out.println("Performance: GREAT! You have good guessing skills!");
        } else if (totalScore >= 100) {
            System.out.println("Performance: GOOD! Keep practicing!");
        } else {
            System.out.println("Performance: NICE TRY! Practice makes perfect!");
        }
    }
    
    private static String repeatChar(char ch, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(ch);
        }
        return sb.toString();
    }
}
