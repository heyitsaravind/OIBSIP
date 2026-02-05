/**
 * Exam Class - Represents an examination with questions and settings
 * Author: Aravind M S
 * Created for: OIBSIP Java Development Internship
 */

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Exam {
    private String examId;
    private String title;
    private String description;
    private int durationMinutes;
    private int maxQuestions;
    private List<Question> questions;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    
    public Exam(String examId, String title, String description, int durationMinutes, int maxQuestions) {
        this.examId = examId;
        this.title = title;
        this.description = description;
        this.durationMinutes = durationMinutes;
        this.maxQuestions = maxQuestions;
        this.questions = new ArrayList<>();
        this.isActive = true;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.createdBy = "system";
    }
    
    // Getters
    public String getExamId() {
        return examId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public int getDurationMinutes() {
        return durationMinutes;
    }
    
    public int getMaxQuestions() {
        return maxQuestions;
    }
    
    public List<Question> getQuestions() {
        return new ArrayList<>(questions); // Return copy to prevent external modification
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public String getCreatedBy() {
        return createdBy;
    }
    
    // Setters
    public void setTitle(String title) {
        this.title = title;
        updateTimestamp();
    }
    
    public void setDescription(String description) {
        this.description = description;
        updateTimestamp();
    }
    
    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
        updateTimestamp();
    }
    
    public void setMaxQuestions(int maxQuestions) {
        this.maxQuestions = maxQuestions;
        updateTimestamp();
    }
    
    public void setActive(boolean active) {
        this.isActive = active;
        updateTimestamp();
    }
    
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    
    // Question management methods
    public boolean addQuestion(Question question) {
        if (questions.size() >= maxQuestions) {
            return false;
        }
        
        questions.add(question);
        updateTimestamp();
        return true;
    }
    
    public boolean removeQuestion(int index) {
        if (index < 0 || index >= questions.size()) {
            return false;
        }
        
        questions.remove(index);
        updateTimestamp();
        return true;
    }
    
    public boolean updateQuestion(int index, Question newQuestion) {
        if (index < 0 || index >= questions.size()) {
            return false;
        }
        
        questions.set(index, newQuestion);
        updateTimestamp();
        return true;
    }
    
    public Question getQuestion(int index) {
        if (index < 0 || index >= questions.size()) {
            return null;
        }
        return questions.get(index);
    }
    
    // Utility methods
    public int getQuestionCount() {
        return questions.size();
    }
    
    public boolean isComplete() {
        return questions.size() == maxQuestions;
    }
    
    public boolean canAddMoreQuestions() {
        return questions.size() < maxQuestions;
    }
    
    public int getRemainingQuestionSlots() {
        return maxQuestions - questions.size();
    }
    
    public boolean hasQuestions() {
        return !questions.isEmpty();
    }
    
    public double getCompletionPercentage() {
        if (maxQuestions == 0) return 0.0;
        return (questions.size() * 100.0) / maxQuestions;
    }
    
    public String getStatusText() {
        if (!isActive) return "Inactive";
        if (questions.isEmpty()) return "No Questions";
        if (!isComplete()) return "Incomplete (" + questions.size() + "/" + maxQuestions + ")";
        return "Ready";
    }
    
    public boolean isReadyForStudents() {
        return isActive && hasQuestions() && isComplete();
    }
    
    private void updateTimestamp() {
        this.updatedAt = LocalDateTime.now();
    }
    
    // Validation methods
    public boolean validateExamStructure() {
        if (title == null || title.trim().isEmpty()) return false;
        if (durationMinutes <= 0) return false;
        if (maxQuestions <= 0) return false;
        
        // Validate all questions
        for (Question question : questions) {
            if (!question.isValid()) return false;
        }
        
        return true;
    }
    
    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<>();
        
        if (title == null || title.trim().isEmpty()) {
            errors.add("Exam title is required");
        }
        
        if (durationMinutes <= 0) {
            errors.add("Duration must be greater than 0 minutes");
        }
        
        if (maxQuestions <= 0) {
            errors.add("Maximum questions must be greater than 0");
        }
        
        if (questions.isEmpty()) {
            errors.add("Exam must have at least one question");
        }
        
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            if (!question.isValid()) {
                errors.add("Question " + (i + 1) + " is invalid");
            }
        }
        
        return errors;
    }
    
    @Override
    public String toString() {
        return String.format("Exam{id='%s', title='%s', questions=%d/%d, duration=%d min, active=%s}", 
                examId, title, questions.size(), maxQuestions, durationMinutes, isActive);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Exam exam = (Exam) obj;
        return examId.equals(exam.examId);
    }
    
    @Override
    public int hashCode() {
        return examId.hashCode();
    }
}