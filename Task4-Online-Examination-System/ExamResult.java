/**
 * ExamResult Class - Represents the result of a completed examination
 * Author: Aravind M S
 * Created for: OIBSIP Java Development Internship
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExamResult {
    private String studentId;
    private String examId;
    private String examTitle;
    private int correctAnswers;
    private int totalQuestions;
    private double percentage;
    private String grade;
    private long timeTaken; // in minutes
    private LocalDateTime completedAt;
    private boolean isPassed;
    private String resultId;
    
    public ExamResult(String studentId, String examId, String examTitle, 
                     int correctAnswers, int totalQuestions, double percentage, 
                     String grade, long timeTaken, LocalDateTime completedAt) {
        this.studentId = studentId;
        this.examId = examId;
        this.examTitle = examTitle;
        this.correctAnswers = correctAnswers;
        this.totalQuestions = totalQuestions;
        this.percentage = percentage;
        this.grade = grade;
        this.timeTaken = timeTaken;
        this.completedAt = completedAt;
        this.isPassed = percentage >= 60.0; // 60% passing grade
        this.resultId = generateResultId();
    }
    
    // Getters
    public String getStudentId() {
        return studentId;
    }
    
    public String getExamId() {
        return examId;
    }
    
    public String getExamTitle() {
        return examTitle;
    }
    
    public int getCorrectAnswers() {
        return correctAnswers;
    }
    
    public int getTotalQuestions() {
        return totalQuestions;
    }
    
    public double getPercentage() {
        return percentage;
    }
    
    public String getGrade() {
        return grade;
    }
    
    public long getTimeTaken() {
        return timeTaken;
    }
    
    public LocalDateTime getCompletedAt() {
        return completedAt;
    }
    
    public boolean isPassed() {
        return isPassed;
    }
    
    public String getResultId() {
        return resultId;
    }
    
    // Setters (limited for data integrity)
    public void setGrade(String grade) {
        this.grade = grade;
    }
    
    public void setExamTitle(String examTitle) {
        this.examTitle = examTitle;
    }
    
    // Utility methods
    public int getIncorrectAnswers() {
        return totalQuestions - correctAnswers;
    }
    
    public double getAccuracyRate() {
        if (totalQuestions == 0) return 0.0;
        return (correctAnswers * 100.0) / totalQuestions;
    }
    
    public String getPassStatus() {
        return isPassed ? "PASS" : "FAIL";
    }
    
    public String getFormattedPercentage() {
        return String.format("%.1f%%", percentage);
    }
    
    public String getFormattedTimeTaken() {
        if (timeTaken < 60) {
            return timeTaken + " minutes";
        } else {
            long hours = timeTaken / 60;
            long minutes = timeTaken % 60;
            return hours + "h " + minutes + "m";
        }
    }
    
    public String getFormattedCompletedAt() {
        return completedAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
    
    public String getFormattedCompletedDate() {
        return completedAt.format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
    }
    
    public String getFormattedCompletedTime() {
        return completedAt.format(DateTimeFormatter.ofPattern("HH:mm"));
    }
    
    public boolean isExcellent() {
        return percentage >= 90.0;
    }
    
    public boolean isGood() {
        return percentage >= 80.0 && percentage < 90.0;
    }
    
    public boolean isSatisfactory() {
        return percentage >= 70.0 && percentage < 80.0;
    }
    
    public boolean isAverage() {
        return percentage >= 60.0 && percentage < 70.0;
    }
    
    public boolean isPoor() {
        return percentage < 60.0;
    }
    
    public String getPerformanceLevel() {
        if (isExcellent()) return "Excellent";
        else if (isGood()) return "Good";
        else if (isSatisfactory()) return "Satisfactory";
        else if (isAverage()) return "Average";
        else return "Needs Improvement";
    }
    
    public String getPerformanceEmoji() {
        if (isExcellent()) return "🏆";
        else if (isGood()) return "🥇";
        else if (isSatisfactory()) return "🥈";
        else if (isAverage()) return "🥉";
        else return "📚";
    }
    
    public boolean isRecentResult() {
        LocalDateTime oneDayAgo = LocalDateTime.now().minusDays(1);
        return completedAt.isAfter(oneDayAgo);
    }
    
    public boolean isThisWeek() {
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusWeeks(1);
        return completedAt.isAfter(oneWeekAgo);
    }
    
    public boolean isThisMonth() {
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
        return completedAt.isAfter(oneMonthAgo);
    }
    
    public long getDaysAgo() {
        return java.time.Duration.between(completedAt, LocalDateTime.now()).toDays();
    }
    
    public String getTimeAgoText() {
        long daysAgo = getDaysAgo();
        
        if (daysAgo == 0) {
            return "Today";
        } else if (daysAgo == 1) {
            return "Yesterday";
        } else if (daysAgo < 7) {
            return daysAgo + " days ago";
        } else if (daysAgo < 30) {
            long weeksAgo = daysAgo / 7;
            return weeksAgo + (weeksAgo == 1 ? " week ago" : " weeks ago");
        } else {
            long monthsAgo = daysAgo / 30;
            return monthsAgo + (monthsAgo == 1 ? " month ago" : " months ago");
        }
    }
    
    private String generateResultId() {
        // Generate a unique result ID based on student, exam, and timestamp
        long timestamp = System.currentTimeMillis();
        return "RES" + studentId.hashCode() + examId.hashCode() + String.valueOf(timestamp).substring(8);
    }
    
    // Comparison methods
    public boolean isBetterThan(ExamResult other) {
        if (other == null) return true;
        return this.percentage > other.percentage;
    }
    
    public boolean isSameExam(ExamResult other) {
        if (other == null) return false;
        return this.examId.equals(other.examId);
    }
    
    public boolean isSameStudent(ExamResult other) {
        if (other == null) return false;
        return this.studentId.equals(other.studentId);
    }
    
    // Statistical methods
    public static double calculateAverageScore(java.util.List<ExamResult> results) {
        if (results == null || results.isEmpty()) return 0.0;
        
        double totalScore = results.stream()
                .mapToDouble(ExamResult::getPercentage)
                .sum();
        
        return totalScore / results.size();
    }
    
    public static ExamResult getBestResult(java.util.List<ExamResult> results) {
        if (results == null || results.isEmpty()) return null;
        
        return results.stream()
                .max((r1, r2) -> Double.compare(r1.getPercentage(), r2.getPercentage()))
                .orElse(null);
    }
    
    public static ExamResult getWorstResult(java.util.List<ExamResult> results) {
        if (results == null || results.isEmpty()) return null;
        
        return results.stream()
                .min((r1, r2) -> Double.compare(r1.getPercentage(), r2.getPercentage()))
                .orElse(null);
    }
    
    // Display methods
    public String getSummaryText() {
        return String.format("%s: %d/%d (%.1f%%) - %s", 
                examTitle, correctAnswers, totalQuestions, percentage, grade);
    }
    
    public String getDetailedSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== EXAM RESULT SUMMARY ===\n");
        sb.append("Student: ").append(studentId).append("\n");
        sb.append("Exam: ").append(examTitle).append(" (").append(examId).append(")\n");
        sb.append("Score: ").append(correctAnswers).append("/").append(totalQuestions);
        sb.append(" (").append(String.format("%.1f", percentage)).append("%)\n");
        sb.append("Grade: ").append(grade).append("\n");
        sb.append("Status: ").append(getPassStatus()).append("\n");
        sb.append("Time Taken: ").append(getFormattedTimeTaken()).append("\n");
        sb.append("Completed: ").append(getFormattedCompletedAt()).append("\n");
        sb.append("Performance: ").append(getPerformanceLevel()).append(" ").append(getPerformanceEmoji()).append("\n");
        
        return sb.toString();
    }
    
    @Override
    public String toString() {
        return String.format("ExamResult{student='%s', exam='%s', score=%d/%d (%.1f%%), grade='%s', passed=%s}", 
                studentId, examId, correctAnswers, totalQuestions, percentage, grade, isPassed);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        ExamResult that = (ExamResult) obj;
        return resultId.equals(that.resultId);
    }
    
    @Override
    public int hashCode() {
        return resultId.hashCode();
    }
}