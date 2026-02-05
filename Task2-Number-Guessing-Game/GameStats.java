/**
 * GameStats Class - Tracks player statistics and achievements
 * Author: Aravind M S
 * Created for: OIBSIP Java Development Internship
 */

import java.util.*;

public class GameStats {
    private int totalGamesPlayed;
    private int totalGamesWon;
    private int totalScore;
    private int bestTime;
    private int bestAttempts;
    private List<String> achievements;
    private Map<String, Integer> difficultyWins;
    private Map<String, Integer> difficultyGames;
    
    public GameStats() {
        this.totalGamesPlayed = 0;
        this.totalGamesWon = 0;
        this.totalScore = 0;
        this.bestTime = Integer.MAX_VALUE;
        this.bestAttempts = Integer.MAX_VALUE;
        this.achievements = new ArrayList<>();
        this.difficultyWins = new HashMap<>();
        this.difficultyGames = new HashMap<>();
        
        // Initialize difficulty tracking
        for (String diff : Arrays.asList("EASY", "MEDIUM", "HARD", "EXPERT")) {
            difficultyWins.put(diff, 0);
            difficultyGames.put(diff, 0);
        }
    }
    
    public void addGame(boolean won, Object difficulty, int attempts, int timeTaken, int score) {
        totalGamesPlayed++;
        
        String diffName = difficulty.toString();
        difficultyGames.put(diffName, difficultyGames.get(diffName) + 1);
        
        if (won) {
            totalGamesWon++;
            totalScore += score;
            difficultyWins.put(diffName, difficultyWins.get(diffName) + 1);
            
            // Update best records
            if (timeTaken > 0 && timeTaken < bestTime) {
                bestTime = timeTaken;
            }
            
            if (attempts > 0 && attempts < bestAttempts) {
                bestAttempts = attempts;
            }
        }
    }
    
    // Getters
    public int getTotalGamesPlayed() {
        return totalGamesPlayed;
    }
    
    public int getTotalGamesWon() {
        return totalGamesWon;
    }
    
    public int getTotalGamesLost() {
        return totalGamesPlayed - totalGamesWon;
    }
    
    public int getTotalScore() {
        return totalScore;
    }
    
    public double getWinRate() {
        if (totalGamesPlayed == 0) return 0.0;
        return (totalGamesWon * 100.0) / totalGamesPlayed;
    }
    
    public double getAverageScore() {
        if (totalGamesWon == 0) return 0.0;
        return (double) totalScore / totalGamesWon;
    }
    
    public int getBestTime() {
        return bestTime == Integer.MAX_VALUE ? 0 : bestTime;
    }
    
    public int getBestAttempts() {
        return bestAttempts == Integer.MAX_VALUE ? 0 : bestAttempts;
    }
    
    public List<String> getAchievements() {
        return new ArrayList<>(achievements);
    }
    
    public int getWinsForDifficulty(Object difficulty) {
        return difficultyWins.getOrDefault(difficulty.toString(), 0);
    }
    
    public int getGamesForDifficulty(Object difficulty) {
        return difficultyGames.getOrDefault(difficulty.toString(), 0);
    }
    
    // Achievement management
    public void addAchievement(String achievement) {
        if (!achievements.contains(achievement)) {
            achievements.add(achievement);
        }
    }
    
    public void clearAchievements() {
        achievements.clear();
    }
    
    // Reset functionality
    public void reset() {
        totalGamesPlayed = 0;
        totalGamesWon = 0;
        totalScore = 0;
        bestTime = Integer.MAX_VALUE;
        bestAttempts = Integer.MAX_VALUE;
        achievements.clear();
        
        for (String diff : difficultyWins.keySet()) {
            difficultyWins.put(diff, 0);
            difficultyGames.put(diff, 0);
        }
    }
    
    @Override
    public String toString() {
        return String.format("GameStats{played=%d, won=%d, score=%d, winRate=%.1f%%}", 
                totalGamesPlayed, totalGamesWon, totalScore, getWinRate());
    }
}