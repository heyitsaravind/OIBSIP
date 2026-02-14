/**
 * Online Examination System
 * A comprehensive exam platform with MCQ support and auto-submit
 * Author: Aravind M S
 * Created for: OIBSIP Java Development Internship
 */

import java.util.*;

public class ExamSystem {
    private static Scanner input = new Scanner(System.in);
    private static Map<String, UserProfile> userDatabase = new HashMap<>();
    private static UserProfile currentUser = null;
    private static ExamSession activeExam = null;
    
    public static void main(String[] args) {
        initializeSampleUsers();
        displayWelcomeBanner();
        
        while (true) {
            if (currentUser == null) {
                handleLoginProcess();
            } else {
                displayMainMenu();
            }
        }
    }
    
    private static void initializeSampleUsers() {
        userDatabase.put("student1", new UserProfile("student1", "pass123", "Aravind Kumar", "aravind@email.com"));
        userDatabase.put("student2", new UserProfile("student2", "pass456", "Priya Sharma", "priya@email.com"));
    }
    
    private static void displayWelcomeBanner() {
        System.out.println("\n" + repeatChar('=', 55));
        System.out.println("       WELCOME TO ONLINE EXAMINATION SYSTEM");
        System.out.println("           Learn, Practice, Excel");
        System.out.println(repeatChar('=', 55));
    }
    
    private static void handleLoginProcess() {
        System.out.println("\nPlease authenticate to continue:");
        System.out.println("1. Login to your account");
        System.out.println("2. View sample credentials");
        System.out.println("3. Exit application");
        System.out.print("\nEnter your choice: ");
        
        try {
            int option = Integer.parseInt(input.nextLine().trim());
            
            switch (option) {
                case 1:
                    performLogin();
                    break;
                case 2:
                    displaySampleCredentials();
                    break;
                case 3:
                    exitApplication();
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1, 2, or 3.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }
    
    private static void performLogin() {
        System.out.println("\n" + repeatChar('-', 40));
        System.out.println("           USER LOGIN");
        System.out.println(repeatChar('-', 40));
        
        System.out.print("Enter Username: ");
        String username = input.nextLine().trim();
        
        System.out.print("Enter Password: ");
        String password = input.nextLine().trim();
        
        if (validateCredentials(username, password)) {
            currentUser = userDatabase.get(username);
            System.out.println("\nLogin successful!");
            System.out.println("Welcome, " + currentUser.getFullName());
        } else {
            System.out.println("\nLogin failed!");
            System.out.println("Invalid username or password. Please try again.");
        }
    }
    
    private static boolean validateCredentials(String username, String password) {
        UserProfile user = userDatabase.get(username);
        return user != null && user.getPassword().equals(password);
    }
    
    private static void displaySampleCredentials() {
        System.out.println("\n" + repeatChar('=', 50));
        System.out.println("         SAMPLE USER CREDENTIALS");
        System.out.println(repeatChar('=', 50));
        System.out.println("Username: student1  |  Password: pass123");
        System.out.println("Username: student2  |  Password: pass456");
        System.out.println(repeatChar('=', 50));
    }
    
    private static void displayMainMenu() {
        System.out.println("\n" + repeatChar('=', 50));
        System.out.println("EXAMINATION SYSTEM MENU");
        System.out.println("User: " + currentUser.getUsername() + 
                         " | " + currentUser.getFullName());
        System.out.println(repeatChar('=', 50));
        System.out.println("1. Update Profile and Password");
        System.out.println("2. Start Examination");
        System.out.println("3. Logout");
        System.out.print("\nSelect an option: ");
        
        try {
            int choice = Integer.parseInt(input.nextLine().trim());
            
            switch (choice) {
                case 1:
                    updateProfileMenu();
                    break;
                case 2:
                    startExamination();
                    break;
                case 3:
                    performLogout();
                    break;
                default:
                    System.out.println("Invalid option. Please select 1-3.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }
    
    private static void updateProfileMenu() {
        System.out.println("\n" + repeatChar('-', 40));
        System.out.println("         UPDATE PROFILE");
        System.out.println(repeatChar('-', 40));
        System.out.println("1. Update Full Name");
        System.out.println("2. Update Email");
        System.out.println("3. Change Password");
        System.out.println("4. Back to Main Menu");
        System.out.print("\nSelect an option: ");
        
        try {
            int choice = Integer.parseInt(input.nextLine().trim());
            
            switch (choice) {
                case 1:
                    updateFullName();
                    break;
                case 2:
                    updateEmail();
                    break;
                case 3:
                    changePassword();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid option. Please select 1-4.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }
    
    private static void updateFullName() {
        System.out.print("\nCurrent Name: " + currentUser.getFullName());
        System.out.print("\nEnter new full name: ");
        String newName = input.nextLine().trim();
        
        if (newName.isEmpty()) {
            System.out.println("Name cannot be empty!");
            return;
        }
        
        currentUser.setFullName(newName);
        System.out.println("\nProfile updated successfully!");
        System.out.println("New name: " + currentUser.getFullName());
    }
    
    private static void updateEmail() {
        System.out.print("\nCurrent Email: " + currentUser.getEmail());
        System.out.print("\nEnter new email: ");
        String newEmail = input.nextLine().trim();
        
        if (!newEmail.contains("@")) {
            System.out.println("Invalid email format!");
            return;
        }
        
        currentUser.setEmail(newEmail);
        System.out.println("\nEmail updated successfully!");
        System.out.println("New email: " + currentUser.getEmail());
    }
    
    private static void changePassword() {
        System.out.print("\nEnter current password: ");
        String currentPassword = input.nextLine().trim();
        
        if (!currentPassword.equals(currentUser.getPassword())) {
            System.out.println("Incorrect current password!");
            return;
        }
        
        System.out.print("Enter new password: ");
        String newPassword = input.nextLine().trim();
        
        if (newPassword.length() < 6) {
            System.out.println("Password must be at least 6 characters!");
            return;
        }
        
        System.out.print("Confirm new password: ");
        String confirmPassword = input.nextLine().trim();
        
        if (!newPassword.equals(confirmPassword)) {
            System.out.println("Passwords do not match!");
            return;
        }
        
        currentUser.setPassword(newPassword);
        System.out.println("\nPassword changed successfully!");
        System.out.println("Please use your new password for next login.");
    }
    
    private static void startExamination() {
        System.out.println("\n" + repeatChar('=', 50));
        System.out.println("STARTING EXAMINATION");
        System.out.println(repeatChar('=', 50));
        System.out.println("Subject: Java Programming Fundamentals");
        System.out.println("Total Questions: 5");
        System.out.println("Time Limit: 5 minutes");
        System.out.println("Each question carries 1 mark");
        System.out.println(repeatChar('=', 50));
        
        System.out.print("\nAre you ready to start? (yes/no): ");
        String response = input.nextLine().trim().toLowerCase();
        
        if (!response.equals("yes")) {
            System.out.println("Exam cancelled. Returning to main menu.");
            return;
        }
        
        activeExam = new ExamSession(currentUser.getUsername());
        conductExam();
    }
    
    private static void conductExam() {
        System.out.println("\n" + repeatChar('=', 60));
        System.out.println("EXAMINATION IN PROGRESS");
        System.out.println("Timer: 5 minutes | Auto-submit enabled");
        System.out.println(repeatChar('=', 60));
        
        List<Question> questions = generateQuestions();
        long startTime = System.currentTimeMillis();
        long timeLimit = 5 * 60 * 1000; // 5 minutes in milliseconds
        
        for (int i = 0; i < questions.size(); i++) {
            long elapsedTime = System.currentTimeMillis() - startTime;
            long remainingTime = timeLimit - elapsedTime;
            
            if (remainingTime <= 0) {
                System.out.println("\n" + repeatChar('=', 50));
                System.out.println("TIME'S UP! Auto-submitting your exam...");
                System.out.println(repeatChar('=', 50));
                break;
            }
            
            displayQuestion(questions.get(i), i + 1, remainingTime);
            
            System.out.print("\nYour answer (1-4) or 0 to skip: ");
            try {
                int answer = Integer.parseInt(input.nextLine().trim());
                
                if (answer >= 1 && answer <= 4) {
                    activeExam.recordAnswer(i, answer);
                    if (answer == questions.get(i).getCorrectAnswer()) {
                        activeExam.incrementScore();
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Question skipped.");
            }
        }
        
        submitExam(questions);
    }
    
    private static void displayQuestion(Question q, int number, long remainingTime) {
        int minutes = (int) (remainingTime / 60000);
        int seconds = (int) ((remainingTime % 60000) / 1000);
        
        System.out.println("\n" + repeatChar('-', 60));
        System.out.println("Question " + number + " of 5 | Time Remaining: " + 
                         minutes + "m " + seconds + "s");
        System.out.println(repeatChar('-', 60));
        System.out.println(q.getQuestionText());
        System.out.println("\n1. " + q.getOption1());
        System.out.println("2. " + q.getOption2());
        System.out.println("3. " + q.getOption3());
        System.out.println("4. " + q.getOption4());
    }
    
    private static List<Question> generateQuestions() {
        List<Question> questions = new ArrayList<>();
        
        questions.add(new Question(
            "What is the size of int data type in Java?",
            "16 bits", "32 bits", "64 bits", "8 bits", 2
        ));
        
        questions.add(new Question(
            "Which keyword is used to inherit a class in Java?",
            "implements", "extends", "inherits", "super", 2
        ));
        
        questions.add(new Question(
            "What is the default value of boolean variable in Java?",
            "true", "false", "null", "0", 2
        ));
        
        questions.add(new Question(
            "Which method is the entry point of a Java program?",
            "start()", "main()", "run()", "execute()", 2
        ));
        
        questions.add(new Question(
            "What is the parent class of all classes in Java?",
            "System", "String", "Object", "Class", 3
        ));
        
        return questions;
    }
    
    private static void submitExam(List<Question> questions) {
        activeExam.setEndTime();
        
        System.out.println("\n" + repeatChar('=', 50));
        System.out.println("EXAMINATION COMPLETED");
        System.out.println(repeatChar('=', 50));
        System.out.println("Your exam has been submitted successfully!");
        System.out.println("Total Questions: " + questions.size());
        System.out.println("Questions Attempted: " + activeExam.getAttemptedCount());
        System.out.println("Correct Answers: " + activeExam.getScore());
        System.out.println("Score: " + activeExam.getScore() + "/" + questions.size());
        System.out.println(repeatChar('=', 50));
        
        displayCorrectAnswers(questions);
        
        System.out.println("\nThank you for taking the exam!");
        activeExam = null;
    }
    
    private static void displayCorrectAnswers(List<Question> questions) {
        System.out.println("\n" + repeatChar('=', 70));
        System.out.println("ANSWER KEY");
        System.out.println(repeatChar('=', 70));
        
        Map<Integer, Integer> userAnswers = activeExam.getAnswers();
        
        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            int correctAnswer = q.getCorrectAnswer();
            Integer userAnswer = userAnswers.get(i);
            
            System.out.println("\nQuestion " + (i + 1) + ": " + q.getQuestionText());
            
            if (userAnswer != null) {
                System.out.print("Your Answer: " + userAnswer + ". " + getOptionText(q, userAnswer));
                if (userAnswer == correctAnswer) {
                    System.out.println(" [CORRECT]");
                } else {
                    System.out.println(" [INCORRECT]");
                    System.out.println("Correct Answer: " + correctAnswer + ". " + getOptionText(q, correctAnswer));
                }
            } else {
                System.out.println("Your Answer: Not attempted");
                System.out.println("Correct Answer: " + correctAnswer + ". " + getOptionText(q, correctAnswer));
            }
        }
        System.out.println("\n" + repeatChar('=', 70));
    }
    
    private static String getOptionText(Question q, int optionNumber) {
        switch (optionNumber) {
            case 1: return q.getOption1();
            case 2: return q.getOption2();
            case 3: return q.getOption3();
            case 4: return q.getOption4();
            default: return "";
        }
    }
    
    private static void performLogout() {
        System.out.println("\nLogging out...");
        System.out.println("Thank you for using our examination system, " + 
                         currentUser.getFullName() + "!");
        
        currentUser = null;
        activeExam = null;
        
        System.out.println("\n" + repeatChar('=', 50));
        System.out.println("Session terminated successfully.");
        System.out.println("Have a great day!");
        System.out.println(repeatChar('=', 50));
    }
    
    private static void exitApplication() {
        System.out.println("\nThank you for using our Online Examination System!");
        System.out.println("Goodbye!");
        System.exit(0);
    }
    
    private static String repeatChar(char ch, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(ch);
        }
        return sb.toString();
    }
}
