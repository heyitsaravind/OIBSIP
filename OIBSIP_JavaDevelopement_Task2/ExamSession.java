import java.util.*;

public class ExamSession {
    private String username;
    private Date startTime;
    private Date endTime;
    private int score;
    private Map<Integer, Integer> answers;
    
    public ExamSession(String username) {
        this.username = username;
        this.startTime = new Date();
        this.score = 0;
        this.answers = new HashMap<>();
    }
    
    public void recordAnswer(int questionNumber, int answer) {
        answers.put(questionNumber, answer);
    }
    
    public void incrementScore() {
        score++;
    }
    
    public void setEndTime() {
        this.endTime = new Date();
    }
    
    public int getScore() {
        return score;
    }
    
    public int getAttemptedCount() {
        return answers.size();
    }
    
    public Map<Integer, Integer> getAnswers() {
        return answers;
    }
}
