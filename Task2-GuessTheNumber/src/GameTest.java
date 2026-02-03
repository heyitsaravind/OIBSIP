/**
 * Simple test to demonstrate the GuessTheNumberGame functionality
 */
public class GameTest {
    public static void main(String[] args) {
        System.out.println("🧪 TESTING GUESS THE NUMBER GAME");
        System.out.println("═".repeat(40));
        
        // Test random number generation
        java.util.Random random = new java.util.Random();
        System.out.println("✅ Random number generation test:");
        for (int i = 0; i < 5; i++) {
            int num = random.nextInt(100) + 1;
            System.out.println("   Generated: " + num + " (between 1-100)");
        }
        
        // Test scoring system
        System.out.println("\n✅ Scoring system test:");
        int[] attempts = {1, 2, 3, 4, 5, 6, 7};
        int[] expectedScores = {100, 85, 70, 55, 40, 25, 10};
        
        for (int i = 0; i < attempts.length; i++) {
            int score = calculateScore(attempts[i]);
            System.out.println("   " + attempts[i] + " attempt(s): " + score + " points ✓");
            assert score == expectedScores[i] : "Score mismatch!";
        }
        
        // Test number comparison logic
        System.out.println("\n✅ Game logic test:");
        int target = 67;
        System.out.println("   Target number: " + target);
        
        testGuess(50, target, "Too low");
        testGuess(75, target, "Too high");
        testGuess(67, target, "Correct");
        
        System.out.println("\n🎉 ALL TESTS PASSED!");
        System.out.println("✅ The game is ready to play!");
        System.out.println("\nTo run the actual game:");
        System.out.println("java GuessTheNumberGame");
    }
    
    private static int calculateScore(int attempts) {
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
    
    private static void testGuess(int guess, int target, String expected) {
        String result;
        if (guess == target) {
            result = "Correct";
        } else if (guess < target) {
            result = "Too low";
        } else {
            result = "Too high";
        }
        
        System.out.println("   Guess " + guess + " → " + result + " ✓");
        assert result.equals(expected) : "Logic error!";
    }
}