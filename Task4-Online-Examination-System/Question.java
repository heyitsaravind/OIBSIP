/**
 * Question Class - Represents a multiple-choice question in an exam
 * Author: Aravind M S
 * Created for: OIBSIP Java Development Internship
 */

import java.util.ArrayList;
import java.util.List;

public class Question {
    private String questionText;
    private List<String> options;
    private int correctAnswer; // Index of correct option (0-3)
    private String explanation;
    private int difficulty; // 1-5 scale
    private String category;
    
    public Question(String questionText, List<String> options, int correctAnswer) {
        this.questionText = questionText;
        this.options = new ArrayList<>(options);
        this.correctAnswer = correctAnswer;
        this.explanation = "";
        this.difficulty = 1;
        this.category = "General";
    }
    
    public Question(String questionText, List<String> options, int correctAnswer, 
                   String explanation, int difficulty, String category) {
        this.questionText = questionText;
        this.options = new ArrayList<>(options);
        this.correctAnswer = correctAnswer;
        this.explanation = explanation != null ? explanation : "";
        this.difficulty = Math.max(1, Math.min(5, difficulty)); // Clamp between 1-5
        this.category = category != null ? category : "General";
    }
    
    // Getters
    public String getQuestionText() {
        return questionText;
    }
    
    public List<String> getOptions() {
        return new ArrayList<>(options); // Return copy to prevent external modification
    }
    
    public int getCorrectAnswer() {
        return correctAnswer;
    }
    
    public String getExplanation() {
        return explanation;
    }
    
    public int getDifficulty() {
        return difficulty;
    }
    
    public String getCategory() {
        return category;
    }
    
    // Setters
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }
    
    public void setOptions(List<String> options) {
        if (options != null && options.size() == 4) {
            this.options = new ArrayList<>(options);
        }
    }
    
    public void setCorrectAnswer(int correctAnswer) {
        if (correctAnswer >= 0 && correctAnswer < options.size()) {
            this.correctAnswer = correctAnswer;
        }
    }
    
    public void setExplanation(String explanation) {
        this.explanation = explanation != null ? explanation : "";
    }
    
    public void setDifficulty(int difficulty) {
        this.difficulty = Math.max(1, Math.min(5, difficulty));
    }
    
    public void setCategory(String category) {
        this.category = category != null ? category : "General";
    }
    
    // Utility methods
    public String getOption(int index) {
        if (index >= 0 && index < options.size()) {
            return options.get(index);
        }
        return null;
    }
    
    public boolean setOption(int index, String optionText) {
        if (index >= 0 && index < options.size() && optionText != null) {
            options.set(index, optionText);
            return true;
        }
        return false;
    }
    
    public String getCorrectOptionText() {
        if (correctAnswer >= 0 && correctAnswer < options.size()) {
            return options.get(correctAnswer);
        }
        return null;
    }
    
    public char getCorrectOptionLetter() {
        return (char) ('A' + correctAnswer);
    }
    
    public boolean isAnswerCorrect(int answerIndex) {
        return answerIndex == correctAnswer;
    }
    
    public boolean isAnswerCorrect(char answerLetter) {
        int answerIndex = Character.toUpperCase(answerLetter) - 'A';
        return isAnswerCorrect(answerIndex);
    }
    
    public boolean isValid() {
        // Check if question has all required components
        if (questionText == null || questionText.trim().isEmpty()) {
            return false;
        }
        
        if (options == null || options.size() != 4) {
            return false;
        }
        
        // Check if all options are non-empty
        for (String option : options) {
            if (option == null || option.trim().isEmpty()) {
                return false;
            }
        }
        
        // Check if correct answer is valid
        if (correctAnswer < 0 || correctAnswer >= options.size()) {
            return false;
        }
        
        return true;
    }
    
    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<>();
        
        if (questionText == null || questionText.trim().isEmpty()) {
            errors.add("Question text is required");
        }
        
        if (options == null || options.size() != 4) {
            errors.add("Question must have exactly 4 options");
        } else {
            for (int i = 0; i < options.size(); i++) {
                if (options.get(i) == null || options.get(i).trim().isEmpty()) {
                    errors.add("Option " + (char)('A' + i) + " cannot be empty");
                }
            }
        }
        
        if (correctAnswer < 0 || correctAnswer >= 4) {
            errors.add("Correct answer must be between A and D");
        }
        
        return errors;
    }
    
    public String getDifficultyText() {
        switch (difficulty) {
            case 1: return "Very Easy";
            case 2: return "Easy";
            case 3: return "Medium";
            case 4: return "Hard";
            case 5: return "Very Hard";
            default: return "Unknown";
        }
    }
    
    public String getFormattedQuestion() {
        StringBuilder sb = new StringBuilder();
        sb.append("Q: ").append(questionText).append("\n");
        
        for (int i = 0; i < options.size(); i++) {
            char optionLetter = (char) ('A' + i);
            sb.append(optionLetter).append(") ").append(options.get(i)).append("\n");
        }
        
        return sb.toString();
    }
    
    public String getFormattedQuestionWithAnswer() {
        StringBuilder sb = new StringBuilder();
        sb.append(getFormattedQuestion());
        sb.append("Correct Answer: ").append(getCorrectOptionLetter()).append("\n");
        
        if (!explanation.isEmpty()) {
            sb.append("Explanation: ").append(explanation).append("\n");
        }
        
        return sb.toString();
    }
    
    // Create a copy of this question
    public Question copy() {
        return new Question(
            this.questionText,
            new ArrayList<>(this.options),
            this.correctAnswer,
            this.explanation,
            this.difficulty,
            this.category
        );
    }
    
    @Override
    public String toString() {
        return String.format("Question{text='%s...', options=%d, correct=%c, difficulty=%d, category='%s'}", 
                questionText.length() > 30 ? questionText.substring(0, 30) : questionText,
                options.size(),
                getCorrectOptionLetter(),
                difficulty,
                category);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Question question = (Question) obj;
        return correctAnswer == question.correctAnswer &&
               difficulty == question.difficulty &&
               questionText.equals(question.questionText) &&
               options.equals(question.options) &&
               category.equals(question.category);
    }
    
    @Override
    public int hashCode() {
        int result = questionText.hashCode();
        result = 31 * result + options.hashCode();
        result = 31 * result + correctAnswer;
        result = 31 * result + category.hashCode();
        return result;
    }
}