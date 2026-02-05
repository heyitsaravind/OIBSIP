/**
 * EduExam Pro - Advanced Online Examination System
 * A comprehensive web-based examination platform with real-time assessment
 * Author: Aravind M S
 * Created for: OIBSIP Java Development Internship
 */

import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;

public class EduExamPro {
    private static Scanner scanner = new Scanner(System.in);
    private static Map<String, User> users = new HashMap<>();
    private static Map<String, Exam> exams = new HashMap<>();
    private static Map<String, ExamResult> results = new HashMap<>();
    private static User currentUser = null;
    private static int examCounter = 1001;
    
    public static void main(String[] args) {
        initializeSystemData();
        displayWelcomeScreen();
        
        while (true) {
            if (currentUser == null) {
                showLoginMenu();
            } else {
                if (currentUser.getRole().equals("ADMIN")) {
                    showAdminMenu();
                } else {
                    showStudentMenu();
                }
            }
        }
    }
    
    private static void initializeSystemData() {
        // Initialize admin user
        users.put("admin", new User("admin", "admin123", "System Administrator", "admin@eduexam.com", "ADMIN"));
        
        // Initialize sample students
        users.put("student1", new User("student1", "pass123", "Aravind Kumar", "aravind@student.com", "STUDENT"));
        users.put("student2", new User("student2", "pass456", "Priya Sharma", "priya@student.com", "STUDENT"));
        
        // Initialize sample exams
        createSampleExams();
    }
    
    private static void createSampleExams() {
        // Java Programming Exam
        Exam javaExam = new Exam("EXAM1001", "Java Programming Fundamentals", 
                                "Test your knowledge of Java programming concepts", 30, 10);
        
        javaExam.addQuestion(new Question("What is the main method signature in Java?",
                Arrays.asList("public static void main(String[] args)", 
                             "public void main(String[] args)",
                             "static void main(String[] args)",
                             "public main(String[] args)"), 0));
        
        javaExam.addQuestion(new Question("Which keyword is used to inherit a class in Java?",
                Arrays.asList("extends", "implements", "inherits", "super"), 0));
        
        javaExam.addQuestion(new Question("What is the size of int data type in Java?",
                Arrays.asList("32 bits", "16 bits", "64 bits", "8 bits"), 0));
        
        javaExam.addQuestion(new Question("Which collection class allows duplicate elements?",
                Arrays.asList("ArrayList", "HashSet", "TreeSet", "LinkedHashSet"), 0));
        
        javaExam.addQuestion(new Question("What does JVM stand for?",
                Arrays.asList("Java Virtual Machine", "Java Variable Method", 
                             "Java Verified Module", "Java Version Manager"), 0));
        
        exams.put("EXAM1001", javaExam);
        
        // Data Structures Exam
        Exam dsExam = new Exam("EXAM1002", "Data Structures & Algorithms", 
                              "Comprehensive test on DSA concepts", 45, 15);
        
        dsExam.addQuestion(new Question("What is the time complexity of binary search?",
                Arrays.asList("O(log n)", "O(n)", "O(n log n)", "O(1)"), 0));
        
        dsExam.addQuestion(new Question("Which data structure uses LIFO principle?",
                Arrays.asList("Stack", "Queue", "Array", "Linked List"), 0));
        
        dsExam.addQuestion(new Question("What is the worst-case time complexity of Quick Sort?",
                Arrays.asList("O(n²)", "O(n log n)", "O(n)", "O(log n)"), 0));
        
        dsExam.addQuestion(new Question("Which traversal visits root node first?",
                Arrays.asList("Preorder", "Inorder", "Postorder", "Level order"), 0));
        
        dsExam.addQuestion(new Question("What is a complete binary tree?",
                Arrays.asList("All levels filled except possibly the last", 
                             "All nodes have two children",
                             "Height is minimum possible",
                             "All leaves are at same level"), 0));
        
        exams.put("EXAM1002", dsExam);
        
        // Database Management Exam
        Exam dbExam = new Exam("EXAM1003", "Database Management Systems", 
                              "Test your DBMS knowledge and SQL skills", 40, 12);
        
        dbExam.addQuestion(new Question("What does ACID stand for in databases?",
                Arrays.asList("Atomicity, Consistency, Isolation, Durability",
                             "Association, Consistency, Integration, Dependency",
                             "Accuracy, Completeness, Integrity, Durability",
                             "Atomicity, Concurrency, Isolation, Dependency"), 0));
        
        dbExam.addQuestion(new Question("Which SQL command is used to retrieve data?",
                Arrays.asList("SELECT", "INSERT", "UPDATE", "DELETE"), 0));
        
        dbExam.addQuestion(new Question("What is a primary key?",
                Arrays.asList("Unique identifier for table rows", 
                             "Foreign key reference",
                             "Index for faster searches",
                             "Backup key for recovery"), 0));
        
        exams.put("EXAM1003", dbExam);
    }
    
    private static void displayWelcomeScreen() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("🎓  WELCOME TO EDUEXAM PRO - ONLINE EXAMINATION SYSTEM  🎓");
        System.out.println("         Advanced Learning Assessment Platform");
        System.out.println("=".repeat(70));
        System.out.println("📚 Take Exams • 📊 View Results • 🏆 Track Progress");
        System.out.println("🌟 Secure • Reliable • Comprehensive Assessment Platform");
        System.out.println("📅 Current Time: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm")));
        System.out.println("=".repeat(70));
    }
    
    private static void showLoginMenu() {
        System.out.println("\n🔐 AUTHENTICATION PORTAL");
        System.out.println("=".repeat(35));
        System.out.println("1. 🔑 Login to Account");
        System.out.println("2. 📝 Register as Student");
        System.out.println("3. 👀 View Demo Credentials");
        System.out.println("4. 📋 Browse Available Exams");
        System.out.println("5. ❌ Exit System");
        System.out.print("\n👉 Select option (1-5): ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            
            switch (choice) {
                case 1:
                    performLogin();
                    break;
                case 2:
                    registerStudent();
                    break;
                case 3:
                    showDemoCredentials();
                    break;
                case 4:
                    browseExamsAsGuest();
                    break;
                case 5:
                    exitSystem();
                    break;
                default:
                    System.out.println("❌ Invalid option! Please select 1-5.");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Please enter a valid number!");
        }
    }
    
    private static void performLogin() {
        System.out.println("\n🔑 LOGIN TO YOUR ACCOUNT");
        System.out.println("-".repeat(30));
        
        System.out.print("👤 Username: ");
        String username = scanner.nextLine().trim();
        
        System.out.print("🔐 Password: ");
        String password = scanner.nextLine().trim();
        
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            System.out.println("\n✅ Login Successful!");
            System.out.println("🎉 Welcome back, " + user.getName() + "!");
            System.out.println("👤 Role: " + user.getRole());
        } else {
            System.out.println("\n❌ Login Failed!");
            System.out.println("🚫 Invalid username or password. Please try again.");
        }
    }
    
    private static void registerStudent() {
        System.out.println("\n📝 STUDENT REGISTRATION");
        System.out.println("-".repeat(30));
        
        System.out.print("👤 Choose Username: ");
        String username = scanner.nextLine().trim();
        
        if (users.containsKey(username)) {
            System.out.println("❌ Username already exists! Please choose a different one.");
            return;
        }
        
        System.out.print("🔐 Create Password: ");
        String password = scanner.nextLine().trim();
        
        System.out.print("📛 Full Name: ");
        String name = scanner.nextLine().trim();
        
        System.out.print("📧 Email Address: ");
        String email = scanner.nextLine().trim();
        
        if (username.isEmpty() || password.isEmpty() || name.isEmpty() || email.isEmpty()) {
            System.out.println("❌ All fields are required!");
            return;
        }
        
        User newUser = new User(username, password, name, email, "STUDENT");
        users.put(username, newUser);
        
        System.out.println("\n✅ Registration Successful!");
        System.out.println("🎉 Welcome to EduExam Pro, " + name + "!");
        System.out.println("🔑 You can now login with your credentials.");
    }
    
    private static void showDemoCredentials() {
        System.out.println("\n👀 DEMO ACCOUNT CREDENTIALS");
        System.out.println("=".repeat(40));
        System.out.println("🔧 Admin Account:");
        System.out.println("   Username: admin");
        System.out.println("   Password: admin123");
        System.out.println();
        System.out.println("🎓 Student Account:");
        System.out.println("   Username: student1");
        System.out.println("   Password: pass123");
        System.out.println("=".repeat(40));
    }
    
    private static void browseExamsAsGuest() {
        System.out.println("\n📋 AVAILABLE EXAMINATIONS (GUEST VIEW)");
        displayAllExams();
        System.out.println("\n💡 Login as student to take exams and view results!");
    }
    
    private static void showStudentMenu() {
        System.out.println("\n🎓 STUDENT DASHBOARD");
        System.out.println("Welcome, " + currentUser.getName());
        System.out.println("=".repeat(45));
        System.out.println("1. 📋 View Available Exams");
        System.out.println("2. ✏️  Take Examination");
        System.out.println("3. 📊 View My Results");
        System.out.println("4. 🏆 Performance Analytics");
        System.out.println("5. 👤 My Profile");
        System.out.println("6. 🚪 Logout");
        System.out.print("\n👉 Select option (1-6): ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            
            switch (choice) {
                case 1:
                    displayAllExams();
                    break;
                case 2:
                    takeExamination();
                    break;
                case 3:
                    viewMyResults();
                    break;
                case 4:
                    showPerformanceAnalytics();
                    break;
                case 5:
                    viewProfile();
                    break;
                case 6:
                    logout();
                    break;
                default:
                    System.out.println("❌ Invalid option! Please select 1-6.");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Please enter a valid number!");
        }
    }
    
    private static void showAdminMenu() {
        System.out.println("\n🔧 ADMIN CONTROL PANEL");
        System.out.println("Administrator: " + currentUser.getName());
        System.out.println("=".repeat(50));
        System.out.println("1. 📋 Manage Examinations");
        System.out.println("2. ➕ Create New Exam");
        System.out.println("3. 👥 Manage Students");
        System.out.println("4. 📊 View All Results");
        System.out.println("5. 📈 System Analytics");
        System.out.println("6. ⚙️  System Settings");
        System.out.println("7. 🚪 Logout");
        System.out.print("\n👉 Select option (1-7): ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            
            switch (choice) {
                case 1:
                    manageExaminations();
                    break;
                case 2:
                    createNewExam();
                    break;
                case 3:
                    manageStudents();
                    break;
                case 4:
                    viewAllResults();
                    break;
                case 5:
                    showSystemAnalytics();
                    break;
                case 6:
                    systemSettings();
                    break;
                case 7:
                    logout();
                    break;
                default:
                    System.out.println("❌ Invalid option! Please select 1-7.");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Please enter a valid number!");
        }
    }
    
    private static void displayAllExams() {
        System.out.println("\n📋 AVAILABLE EXAMINATIONS");
        System.out.println("=".repeat(80));
        System.out.printf("%-12s %-30s %-15s %-10s %-10s%n", 
                "Exam ID", "Title", "Duration", "Questions", "Status");
        System.out.println("-".repeat(80));
        
        for (Exam exam : exams.values()) {
            System.out.printf("%-12s %-30s %-15s %-10d %-10s%n",
                    exam.getExamId(),
                    exam.getTitle(),
                    exam.getDurationMinutes() + " mins",
                    exam.getQuestions().size(),
                    exam.isActive() ? "Active" : "Inactive"
            );
        }
        System.out.println("=".repeat(80));
    }
    
    private static void takeExamination() {
        System.out.println("\n✏️  TAKE EXAMINATION");
        System.out.println("-".repeat(25));
        
        displayAllExams();
        
        System.out.print("\n📝 Enter Exam ID: ");
        String examId = scanner.nextLine().trim().toUpperCase();
        
        Exam exam = exams.get(examId);
        if (exam == null) {
            System.out.println("❌ Invalid Exam ID!");
            return;
        }
        
        if (!exam.isActive()) {
            System.out.println("❌ This exam is currently inactive!");
            return;
        }
        
        // Check if student already took this exam
        String resultKey = currentUser.getUsername() + "_" + examId;
        if (results.containsKey(resultKey)) {
            System.out.println("❌ You have already taken this exam!");
            System.out.println("💡 Check your results in 'View My Results' section.");
            return;
        }
        
        System.out.println("\n📋 EXAM INSTRUCTIONS");
        System.out.println("=".repeat(50));
        System.out.println("📚 Exam: " + exam.getTitle());
        System.out.println("⏱️  Duration: " + exam.getDurationMinutes() + " minutes");
        System.out.println("❓ Questions: " + exam.getQuestions().size());
        System.out.println("📝 Instructions:");
        System.out.println("   • Answer all questions carefully");
        System.out.println("   • Each question has 4 options (A, B, C, D)");
        System.out.println("   • You cannot go back to previous questions");
        System.out.println("   • Exam will auto-submit when time expires");
        System.out.println("=".repeat(50));
        
        System.out.print("\n🚀 Ready to start? (y/N): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        
        if (!confirm.equals("y") && !confirm.equals("yes")) {
            System.out.println("❌ Exam cancelled.");
            return;
        }
        
        // Start the exam
        conductExam(exam);
    }
    
    private static void conductExam(Exam exam) {
        LocalDateTime startTime = LocalDateTime.now();
        List<Integer> answers = new ArrayList<>();
        List<Question> questions = exam.getQuestions();
        
        System.out.println("\n🚀 EXAM STARTED!");
        System.out.println("⏰ Start Time: " + startTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        System.out.println("⏱️  Duration: " + exam.getDurationMinutes() + " minutes");
        System.out.println("=".repeat(60));
        
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            
            System.out.println("\n📝 Question " + (i + 1) + " of " + questions.size());
            System.out.println("-".repeat(40));
            System.out.println("❓ " + question.getQuestionText());
            System.out.println();
            
            List<String> options = question.getOptions();
            for (int j = 0; j < options.size(); j++) {
                char optionLetter = (char) ('A' + j);
                System.out.println(optionLetter + ") " + options.get(j));
            }
            
            System.out.print("\n👉 Your answer (A/B/C/D): ");
            String answer = scanner.nextLine().trim().toUpperCase();
            
            int answerIndex = -1;
            if (answer.equals("A")) answerIndex = 0;
            else if (answer.equals("B")) answerIndex = 1;
            else if (answer.equals("C")) answerIndex = 2;
            else if (answer.equals("D")) answerIndex = 3;
            else {
                System.out.println("⚠️  Invalid answer! Marking as unanswered.");
                answerIndex = -1;
            }
            
            answers.add(answerIndex);
            
            // Show progress
            int progress = ((i + 1) * 100) / questions.size();
            System.out.println("📊 Progress: " + progress + "% (" + (i + 1) + "/" + questions.size() + ")");
        }
        
        LocalDateTime endTime = LocalDateTime.now();
        Duration timeTaken = Duration.between(startTime, endTime);
        
        // Calculate score
        int correctAnswers = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (answers.get(i) == questions.get(i).getCorrectAnswer()) {
                correctAnswers++;
            }
        }
        
        double percentage = (correctAnswers * 100.0) / questions.size();
        String grade = calculateGrade(percentage);
        
        // Save result
        ExamResult result = new ExamResult(
            currentUser.getUsername(),
            exam.getExamId(),
            exam.getTitle(),
            correctAnswers,
            questions.size(),
            percentage,
            grade,
            timeTaken.toMinutes(),
            startTime
        );
        
        String resultKey = currentUser.getUsername() + "_" + exam.getExamId();
        results.put(resultKey, result);
        
        // Display results
        System.out.println("\n🎉 EXAM COMPLETED!");
        System.out.println("=".repeat(50));
        System.out.println("📚 Exam: " + exam.getTitle());
        System.out.println("⏰ Time Taken: " + timeTaken.toMinutes() + " minutes");
        System.out.println("✅ Correct Answers: " + correctAnswers + "/" + questions.size());
        System.out.println("📊 Score: " + String.format("%.1f", percentage) + "%");
        System.out.println("🏆 Grade: " + grade);
        System.out.println("📅 Completed: " + endTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm")));
        System.out.println("=".repeat(50));
        
        if (percentage >= 60) {
            System.out.println("🎉 Congratulations! You passed the exam!");
        } else {
            System.out.println("📚 Keep studying! You can retake the exam later.");
        }
    }
    
    private static String calculateGrade(double percentage) {
        if (percentage >= 90) return "A+";
        else if (percentage >= 80) return "A";
        else if (percentage >= 70) return "B+";
        else if (percentage >= 60) return "B";
        else if (percentage >= 50) return "C";
        else if (percentage >= 40) return "D";
        else return "F";
    }
    
    private static void viewMyResults() {
        System.out.println("\n📊 MY EXAMINATION RESULTS");
        System.out.println("Student: " + currentUser.getName());
        System.out.println("=".repeat(80));
        
        boolean hasResults = false;
        System.out.printf("%-12s %-25s %-8s %-8s %-8s %-6s %-15s%n", 
                "Exam ID", "Title", "Score", "Grade", "Time", "Status", "Date");
        System.out.println("-".repeat(80));
        
        for (ExamResult result : results.values()) {
            if (result.getStudentId().equals(currentUser.getUsername())) {
                System.out.printf("%-12s %-25s %-8.1f %-8s %-8d %-6s %-15s%n",
                        result.getExamId(),
                        result.getExamTitle(),
                        result.getPercentage(),
                        result.getGrade(),
                        result.getTimeTaken(),
                        result.getPercentage() >= 60 ? "PASS" : "FAIL",
                        result.getCompletedAt().format(DateTimeFormatter.ofPattern("dd/MM/yy HH:mm"))
                );
                hasResults = true;
            }
        }
        
        if (!hasResults) {
            System.out.println("📝 No exam results found.");
            System.out.println("💡 Take an exam to see your results here!");
        }
        System.out.println("=".repeat(80));
    }
    
    private static void showPerformanceAnalytics() {
        System.out.println("\n🏆 PERFORMANCE ANALYTICS");
        System.out.println("Student: " + currentUser.getName());
        System.out.println("=".repeat(50));
        
        List<ExamResult> myResults = new ArrayList<>();
        for (ExamResult result : results.values()) {
            if (result.getStudentId().equals(currentUser.getUsername())) {
                myResults.add(result);
            }
        }
        
        if (myResults.isEmpty()) {
            System.out.println("📝 No performance data available.");
            System.out.println("💡 Take some exams to see your analytics!");
            return;
        }
        
        // Calculate statistics
        double totalScore = 0;
        int passedExams = 0;
        long totalTime = 0;
        
        for (ExamResult result : myResults) {
            totalScore += result.getPercentage();
            if (result.getPercentage() >= 60) passedExams++;
            totalTime += result.getTimeTaken();
        }
        
        double averageScore = totalScore / myResults.size();
        double passRate = (passedExams * 100.0) / myResults.size();
        double averageTime = (double) totalTime / myResults.size();
        
        System.out.println("📊 Total Exams Taken: " + myResults.size());
        System.out.println("✅ Exams Passed: " + passedExams);
        System.out.println("❌ Exams Failed: " + (myResults.size() - passedExams));
        System.out.println("📈 Pass Rate: " + String.format("%.1f", passRate) + "%");
        System.out.println("🎯 Average Score: " + String.format("%.1f", averageScore) + "%");
        System.out.println("⏱️  Average Time: " + String.format("%.1f", averageTime) + " minutes");
        
        // Find best and worst performance
        ExamResult bestResult = myResults.stream()
                .max(Comparator.comparing(ExamResult::getPercentage))
                .orElse(null);
        
        ExamResult worstResult = myResults.stream()
                .min(Comparator.comparing(ExamResult::getPercentage))
                .orElse(null);
        
        if (bestResult != null) {
            System.out.println("🏆 Best Performance: " + bestResult.getExamTitle() + 
                             " (" + String.format("%.1f", bestResult.getPercentage()) + "%)");
        }
        
        if (worstResult != null && myResults.size() > 1) {
            System.out.println("📚 Needs Improvement: " + worstResult.getExamTitle() + 
                             " (" + String.format("%.1f", worstResult.getPercentage()) + "%)");
        }
        
        System.out.println("=".repeat(50));
    }
    
    private static void viewProfile() {
        System.out.println("\n👤 MY PROFILE");
        System.out.println("=".repeat(35));
        System.out.println("Username: " + currentUser.getUsername());
        System.out.println("Name: " + currentUser.getName());
        System.out.println("Email: " + currentUser.getEmail());
        System.out.println("Role: " + currentUser.getRole());
        
        // Count exams taken
        int examsTaken = 0;
        for (ExamResult result : results.values()) {
            if (result.getStudentId().equals(currentUser.getUsername())) {
                examsTaken++;
            }
        }
        
        System.out.println("Exams Taken: " + examsTaken);
        System.out.println("Member Since: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMM yyyy")));
        System.out.println("=".repeat(35));
    }
    
    // Admin methods
    private static void manageExaminations() {
        System.out.println("\n📋 EXAMINATION MANAGEMENT");
        displayAllExams();
        
        System.out.println("\n⚙️  Management Options:");
        System.out.println("1. ✏️  Edit Exam");
        System.out.println("2. 🗑️  Delete Exam");
        System.out.println("3. 🔄 Toggle Exam Status");
        System.out.println("4. 🔙 Back to Admin Menu");
        System.out.print("\n👉 Select option (1-4): ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            
            switch (choice) {
                case 1:
                    editExam();
                    break;
                case 2:
                    deleteExam();
                    break;
                case 3:
                    toggleExamStatus();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("❌ Invalid option!");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Please enter a valid number!");
        }
    }
    
    private static void createNewExam() {
        System.out.println("\n➕ CREATE NEW EXAMINATION");
        System.out.println("-".repeat(35));
        
        System.out.print("📚 Exam Title: ");
        String title = scanner.nextLine().trim();
        
        System.out.print("📝 Description: ");
        String description = scanner.nextLine().trim();
        
        System.out.print("⏱️  Duration (minutes): ");
        int duration;
        try {
            duration = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid duration!");
            return;
        }
        
        System.out.print("❓ Number of questions: ");
        int numQuestions;
        try {
            numQuestions = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid number!");
            return;
        }
        
        String examId = "EXAM" + (examCounter++);
        Exam newExam = new Exam(examId, title, description, duration, numQuestions);
        
        // Add questions
        for (int i = 0; i < numQuestions; i++) {
            System.out.println("\n📝 Question " + (i + 1) + ":");
            System.out.print("❓ Question text: ");
            String questionText = scanner.nextLine().trim();
            
            List<String> options = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                System.out.print("Option " + (char)('A' + j) + ": ");
                options.add(scanner.nextLine().trim());
            }
            
            System.out.print("✅ Correct answer (A/B/C/D): ");
            String correctStr = scanner.nextLine().trim().toUpperCase();
            int correctAnswer = correctStr.equals("A") ? 0 : 
                               correctStr.equals("B") ? 1 : 
                               correctStr.equals("C") ? 2 : 3;
            
            newExam.addQuestion(new Question(questionText, options, correctAnswer));
        }
        
        exams.put(examId, newExam);
        
        System.out.println("\n✅ Exam created successfully!");
        System.out.println("🆔 Exam ID: " + examId);
        System.out.println("📚 Title: " + title);
        System.out.println("❓ Questions: " + numQuestions);
    }
    
    private static void editExam() {
        System.out.print("\n✏️  Enter Exam ID to edit: ");
        String examId = scanner.nextLine().trim().toUpperCase();
        
        Exam exam = exams.get(examId);
        if (exam == null) {
            System.out.println("❌ Exam not found!");
            return;
        }
        
        System.out.println("📝 Editing: " + exam.getTitle());
        System.out.println("💡 Press Enter to keep current value");
        
        System.out.print("📚 New title [" + exam.getTitle() + "]: ");
        String newTitle = scanner.nextLine().trim();
        if (!newTitle.isEmpty()) {
            exam.setTitle(newTitle);
        }
        
        System.out.print("📝 New description [" + exam.getDescription() + "]: ");
        String newDescription = scanner.nextLine().trim();
        if (!newDescription.isEmpty()) {
            exam.setDescription(newDescription);
        }
        
        System.out.println("✅ Exam updated successfully!");
    }
    
    private static void deleteExam() {
        System.out.print("\n🗑️  Enter Exam ID to delete: ");
        String examId = scanner.nextLine().trim().toUpperCase();
        
        Exam exam = exams.get(examId);
        if (exam == null) {
            System.out.println("❌ Exam not found!");
            return;
        }
        
        System.out.print("⚠️  Are you sure you want to delete '" + exam.getTitle() + "'? (y/N): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        
        if (confirm.equals("y") || confirm.equals("yes")) {
            exams.remove(examId);
            System.out.println("✅ Exam deleted successfully!");
        } else {
            System.out.println("❌ Deletion cancelled.");
        }
    }
    
    private static void toggleExamStatus() {
        System.out.print("\n🔄 Enter Exam ID to toggle status: ");
        String examId = scanner.nextLine().trim().toUpperCase();
        
        Exam exam = exams.get(examId);
        if (exam == null) {
            System.out.println("❌ Exam not found!");
            return;
        }
        
        exam.setActive(!exam.isActive());
        System.out.println("✅ Exam status changed to: " + (exam.isActive() ? "Active" : "Inactive"));
    }
    
    private static void manageStudents() {
        System.out.println("\n👥 STUDENT MANAGEMENT");
        System.out.println("=".repeat(60));
        System.out.printf("%-15s %-20s %-25s %-10s%n", "Username", "Name", "Email", "Exams Taken");
        System.out.println("-".repeat(60));
        
        for (User user : users.values()) {
            if (user.getRole().equals("STUDENT")) {
                int examsTaken = 0;
                for (ExamResult result : results.values()) {
                    if (result.getStudentId().equals(user.getUsername())) {
                        examsTaken++;
                    }
                }
                
                System.out.printf("%-15s %-20s %-25s %-10d%n",
                        user.getUsername(),
                        user.getName(),
                        user.getEmail(),
                        examsTaken
                );
            }
        }
        System.out.println("=".repeat(60));
    }
    
    private static void viewAllResults() {
        System.out.println("\n📊 ALL EXAMINATION RESULTS");
        System.out.println("=".repeat(80));
        System.out.printf("%-15s %-12s %-20s %-8s %-6s %-15s%n", 
                "Student", "Exam ID", "Title", "Score", "Grade", "Date");
        System.out.println("-".repeat(80));
        
        for (ExamResult result : results.values()) {
            System.out.printf("%-15s %-12s %-20s %-8.1f %-6s %-15s%n",
                    result.getStudentId(),
                    result.getExamId(),
                    result.getExamTitle(),
                    result.getPercentage(),
                    result.getGrade(),
                    result.getCompletedAt().format(DateTimeFormatter.ofPattern("dd/MM/yy HH:mm"))
            );
        }
        System.out.println("=".repeat(80));
    }
    
    private static void showSystemAnalytics() {
        System.out.println("\n📈 SYSTEM ANALYTICS");
        System.out.println("=".repeat(40));
        
        int totalStudents = (int) users.values().stream()
                .filter(u -> u.getRole().equals("STUDENT"))
                .count();
        
        int totalExams = exams.size();
        int activeExams = (int) exams.values().stream()
                .filter(Exam::isActive)
                .count();
        
        int totalResults = results.size();
        
        double averageScore = results.values().stream()
                .mapToDouble(ExamResult::getPercentage)
                .average()
                .orElse(0.0);
        
        System.out.println("👥 Total Students: " + totalStudents);
        System.out.println("📚 Total Exams: " + totalExams);
        System.out.println("✅ Active Exams: " + activeExams);
        System.out.println("📝 Total Attempts: " + totalResults);
        System.out.println("📊 Average Score: " + String.format("%.1f", averageScore) + "%");
        System.out.println("=".repeat(40));
    }
    
    private static void systemSettings() {
        System.out.println("\n⚙️  SYSTEM SETTINGS");
        System.out.println("=".repeat(30));
        System.out.println("1. 🔄 Reset All Data");
        System.out.println("2. 📤 Export Results");
        System.out.println("3. 🔙 Back to Admin Menu");
        System.out.print("\n👉 Select option (1-3): ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            
            switch (choice) {
                case 1:
                    resetAllData();
                    break;
                case 2:
                    exportResults();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("❌ Invalid option!");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Please enter a valid number!");
        }
    }
    
    private static void resetAllData() {
        System.out.print("\n⚠️  Are you sure you want to reset ALL data? (y/N): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        
        if (confirm.equals("y") || confirm.equals("yes")) {
            results.clear();
            System.out.println("✅ All examination results have been cleared!");
        } else {
            System.out.println("❌ Reset cancelled.");
        }
    }
    
    private static void exportResults() {
        System.out.println("\n📤 RESULTS EXPORT");
        System.out.println("=".repeat(30));
        System.out.println("Export format: CSV-like display");
        System.out.println();
        
        System.out.println("Student,ExamID,Title,Score,Grade,Date");
        for (ExamResult result : results.values()) {
            System.out.printf("%s,%s,%s,%.1f,%s,%s%n",
                    result.getStudentId(),
                    result.getExamId(),
                    result.getExamTitle(),
                    result.getPercentage(),
                    result.getGrade(),
                    result.getCompletedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            );
        }
        
        System.out.println("\n💾 Results displayed above can be copied for external use!");
    }
    
    private static void logout() {
        System.out.println("\n🚪 LOGGING OUT...");
        System.out.println("👋 Thank you for using EduExam Pro, " + currentUser.getName() + "!");
        System.out.println("📚 Keep learning and growing!");
        currentUser = null;
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("🌟 Education is the key to success! 🌟");
        System.out.println("=".repeat(50));
    }
    
    private static void exitSystem() {
        System.out.println("\n👋 THANK YOU FOR USING EDUEXAM PRO!");
        System.out.println("🎓 Your learning journey continues...");
        System.out.println("📚 Knowledge is power. Keep exploring!");
        System.exit(0);
    }
}