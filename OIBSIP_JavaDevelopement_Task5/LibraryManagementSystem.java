/**
 * Digital Library Management System
 * A comprehensive library automation system
 * Author: Aravind M S
 * Created for: OIBSIP Java Development Internship
 */

import java.util.*;
import java.text.SimpleDateFormat;

public class LibraryManagementSystem {
    private static Scanner input = new Scanner(System.in);
    private static Map<String, Book> bookDatabase = new HashMap<>();
    private static Map<String, Member> memberDatabase = new HashMap<>();
    private static Map<String, IssuedBook> issuedBooks = new HashMap<>();
    private static User currentUser = null;
    private static int bookIdCounter = 1001;
    private static int memberIdCounter = 2001;
    
    public static void main(String[] args) {
        initializeData();
        displayWelcomeBanner();
        
        while (true) {
            if (currentUser == null) {
                handleLoginProcess();
            } else {
                if (currentUser.isAdmin()) {
                    displayAdminMenu();
                } else {
                    displayUserMenu();
                }
            }
        }
    }
    
    private static void initializeData() {
        // Initialize admin and users
        User admin = new User("admin", "admin123", "Administrator", true);
        User user1 = new User("user1", "pass123", "Aravind Kumar", false);
        User user2 = new User("user2", "pass456", "Priya Sharma", false);
        
        // Initialize sample books
        bookDatabase.put("B1001", new Book("B1001", "Java Programming", "James Gosling", "Programming", 5));
        bookDatabase.put("B1002", new Book("B1002", "Data Structures", "Robert Lafore", "Computer Science", 3));
        bookDatabase.put("B1003", new Book("B1003", "Clean Code", "Robert Martin", "Programming", 4));
        bookDatabase.put("B1004", new Book("B1004", "The Alchemist", "Paulo Coelho", "Fiction", 6));
        bookDatabase.put("B1005", new Book("B1005", "Rich Dad Poor Dad", "Robert Kiyosaki", "Finance", 2));
        
        // Initialize sample members
        memberDatabase.put("M2001", new Member("M2001", "Aravind Kumar", "aravind@email.com", "9876543210"));
        memberDatabase.put("M2002", new Member("M2002", "Priya Sharma", "priya@email.com", "9876543211"));
    }
    
    private static void displayWelcomeBanner() {
        System.out.println("\n" + repeatChar('=', 60));
        System.out.println("       WELCOME TO DIGITAL LIBRARY MANAGEMENT SYSTEM");
        System.out.println("           Your Gateway to Knowledge");
        System.out.println(repeatChar('=', 60));
    }
    
    private static void handleLoginProcess() {
        System.out.println("\nPlease authenticate to continue:");
        System.out.println("1. Login");
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
        
        if (username.equals("admin") && password.equals("admin123")) {
            currentUser = new User("admin", "admin123", "Administrator", true);
            System.out.println("\nLogin successful!");
            System.out.println("Welcome, Administrator!");
        } else if (username.equals("user1") && password.equals("pass123")) {
            currentUser = new User("user1", "pass123", "Aravind Kumar", false);
            System.out.println("\nLogin successful!");
            System.out.println("Welcome, Aravind Kumar!");
        } else if (username.equals("user2") && password.equals("pass456")) {
            currentUser = new User("user2", "pass456", "Priya Sharma", false);
            System.out.println("\nLogin successful!");
            System.out.println("Welcome, Priya Sharma!");
        } else {
            System.out.println("\nLogin failed!");
            System.out.println("Invalid username or password. Please try again.");
        }
    }
    
    private static void displaySampleCredentials() {
        System.out.println("\n" + repeatChar('=', 50));
        System.out.println("         SAMPLE CREDENTIALS");
        System.out.println(repeatChar('=', 50));
        System.out.println("Admin - Username: admin  |  Password: admin123");
        System.out.println("User  - Username: user1  |  Password: pass123");
        System.out.println("User  - Username: user2  |  Password: pass456");
        System.out.println(repeatChar('=', 50));
    }
    
    private static void displayAdminMenu() {
        System.out.println("\n" + repeatChar('=', 60));
        System.out.println("ADMIN PANEL");
        System.out.println("User: " + currentUser.getName());
        System.out.println(repeatChar('=', 60));
        System.out.println("1. Add New Book");
        System.out.println("2. Update Book");
        System.out.println("3. Delete Book");
        System.out.println("4. View All Books");
        System.out.println("5. Add New Member");
        System.out.println("6. Update Member");
        System.out.println("7. Delete Member");
        System.out.println("8. View All Members");
        System.out.println("9. View Issued Books Report");
        System.out.println("10. Logout");
        System.out.print("\nSelect an option: ");
        
        try {
            int choice = Integer.parseInt(input.nextLine().trim());
            
            switch (choice) {
                case 1: addNewBook(); break;
                case 2: updateBook(); break;
                case 3: deleteBook(); break;
                case 4: viewAllBooks(); break;
                case 5: addNewMember(); break;
                case 6: updateMember(); break;
                case 7: deleteMember(); break;
                case 8: viewAllMembers(); break;
                case 9: viewIssuedBooksReport(); break;
                case 10: performLogout(); break;
                default: System.out.println("Invalid option. Please select 1-10.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }
    
    private static void displayUserMenu() {
        System.out.println("\n" + repeatChar('=', 60));
        System.out.println("USER PANEL");
        System.out.println("User: " + currentUser.getName());
        System.out.println(repeatChar('=', 60));
        System.out.println("1. Browse Books by Category");
        System.out.println("2. Search Book");
        System.out.println("3. Issue Book");
        System.out.println("4. Return Book");
        System.out.println("5. View My Issued Books");
        System.out.println("6. Send Query Email");
        System.out.println("7. Logout");
        System.out.print("\nSelect an option: ");
        
        try {
            int choice = Integer.parseInt(input.nextLine().trim());
            
            switch (choice) {
                case 1: browseByCategory(); break;
                case 2: searchBook(); break;
                case 3: issueBook(); break;
                case 4: returnBook(); break;
                case 5: viewMyIssuedBooks(); break;
                case 6: sendQueryEmail(); break;
                case 7: performLogout(); break;
                default: System.out.println("Invalid option. Please select 1-7.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }
    
    // Admin Functions
    private static void addNewBook() {
        System.out.println("\n" + repeatChar('=', 60));
        System.out.println("ADD NEW BOOK");
        System.out.println(repeatChar('=', 60));
        
        String bookId = "B" + (bookIdCounter++);
        
        System.out.print("Enter Book Title: ");
        String title = input.nextLine().trim();
        
        System.out.print("Enter Author Name: ");
        String author = input.nextLine().trim();
        
        System.out.print("Enter Category: ");
        String category = input.nextLine().trim();
        
        System.out.print("Enter Number of Copies: ");
        try {
            int copies = Integer.parseInt(input.nextLine().trim());
            
            Book book = new Book(bookId, title, author, category, copies);
            bookDatabase.put(bookId, book);
            
            System.out.println("\nBook added successfully!");
            System.out.println("Book ID: " + bookId);
            System.out.println("Title: " + title);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number of copies!");
        }
    }
    
    private static void updateBook() {
        System.out.println("\n" + repeatChar('=', 60));
        System.out.println("UPDATE BOOK");
        System.out.println(repeatChar('=', 60));
        
        System.out.print("Enter Book ID to update: ");
        String bookId = input.nextLine().trim();
        
        Book book = bookDatabase.get(bookId);
        if (book == null) {
            System.out.println("Book not found!");
            return;
        }
        
        System.out.println("\nCurrent Details:");
        System.out.println("Title: " + book.getTitle());
        System.out.println("Author: " + book.getAuthor());
        System.out.println("Category: " + book.getCategory());
        System.out.println("Copies: " + book.getTotalCopies());
        
        System.out.print("\nEnter New Title (or press Enter to keep current): ");
        String title = input.nextLine().trim();
        if (!title.isEmpty()) book.setTitle(title);
        
        System.out.print("Enter New Author (or press Enter to keep current): ");
        String author = input.nextLine().trim();
        if (!author.isEmpty()) book.setAuthor(author);
        
        System.out.print("Enter New Category (or press Enter to keep current): ");
        String category = input.nextLine().trim();
        if (!category.isEmpty()) book.setCategory(category);
        
        System.out.print("Enter New Number of Copies (or press Enter to keep current): ");
        String copiesStr = input.nextLine().trim();
        if (!copiesStr.isEmpty()) {
            try {
                int copies = Integer.parseInt(copiesStr);
                book.setTotalCopies(copies);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number!");
            }
        }
        
        System.out.println("\nBook updated successfully!");
    }
    
    private static void deleteBook() {
        System.out.println("\n" + repeatChar('=', 60));
        System.out.println("DELETE BOOK");
        System.out.println(repeatChar('=', 60));
        
        System.out.print("Enter Book ID to delete: ");
        String bookId = input.nextLine().trim();
        
        Book book = bookDatabase.get(bookId);
        if (book == null) {
            System.out.println("Book not found!");
            return;
        }
        
        System.out.println("\nBook Details:");
        System.out.println("Title: " + book.getTitle());
        System.out.println("Author: " + book.getAuthor());
        
        System.out.print("\nConfirm deletion? (yes/no): ");
        String confirm = input.nextLine().trim().toLowerCase();
        
        if (confirm.equals("yes")) {
            bookDatabase.remove(bookId);
            System.out.println("Book deleted successfully!");
        } else {
            System.out.println("Deletion cancelled.");
        }
    }
    
    private static void viewAllBooks() {
        System.out.println("\n" + repeatChar('=', 90));
        System.out.println("ALL BOOKS IN LIBRARY");
        System.out.println(repeatChar('=', 90));
        
        if (bookDatabase.isEmpty()) {
            System.out.println("No books available.");
            return;
        }
        
        System.out.printf("%-10s %-25s %-20s %-15s %-10s%n", 
                         "Book ID", "Title", "Author", "Category", "Copies");
        System.out.println(repeatChar('-', 90));
        
        for (Book book : bookDatabase.values()) {
            System.out.printf("%-10s %-25s %-20s %-15s %-10d%n",
                book.getBookId(),
                truncate(book.getTitle(), 25),
                truncate(book.getAuthor(), 20),
                book.getCategory(),
                book.getAvailableCopies()
            );
        }
        System.out.println(repeatChar('=', 90));
    }
    
    private static void addNewMember() {
        System.out.println("\n" + repeatChar('=', 60));
        System.out.println("ADD NEW MEMBER");
        System.out.println(repeatChar('=', 60));
        
        String memberId = "M" + (memberIdCounter++);
        
        System.out.print("Enter Member Name: ");
        String name = input.nextLine().trim();
        
        System.out.print("Enter Email: ");
        String email = input.nextLine().trim();
        
        System.out.print("Enter Phone: ");
        String phone = input.nextLine().trim();
        
        Member member = new Member(memberId, name, email, phone);
        memberDatabase.put(memberId, member);
        
        System.out.println("\nMember added successfully!");
        System.out.println("Member ID: " + memberId);
        System.out.println("Name: " + name);
    }
    
    private static void updateMember() {
        System.out.println("\n" + repeatChar('=', 60));
        System.out.println("UPDATE MEMBER");
        System.out.println(repeatChar('=', 60));
        
        System.out.print("Enter Member ID to update: ");
        String memberId = input.nextLine().trim();
        
        Member member = memberDatabase.get(memberId);
        if (member == null) {
            System.out.println("Member not found!");
            return;
        }
        
        System.out.println("\nCurrent Details:");
        System.out.println("Name: " + member.getName());
        System.out.println("Email: " + member.getEmail());
        System.out.println("Phone: " + member.getPhone());
        
        System.out.print("\nEnter New Name (or press Enter to keep current): ");
        String name = input.nextLine().trim();
        if (!name.isEmpty()) member.setName(name);
        
        System.out.print("Enter New Email (or press Enter to keep current): ");
        String email = input.nextLine().trim();
        if (!email.isEmpty()) member.setEmail(email);
        
        System.out.print("Enter New Phone (or press Enter to keep current): ");
        String phone = input.nextLine().trim();
        if (!phone.isEmpty()) member.setPhone(phone);
        
        System.out.println("\nMember updated successfully!");
    }
    
    private static void deleteMember() {
        System.out.println("\n" + repeatChar('=', 60));
        System.out.println("DELETE MEMBER");
        System.out.println(repeatChar('=', 60));
        
        System.out.print("Enter Member ID to delete: ");
        String memberId = input.nextLine().trim();
        
        Member member = memberDatabase.get(memberId);
        if (member == null) {
            System.out.println("Member not found!");
            return;
        }
        
        System.out.println("\nMember Details:");
        System.out.println("Name: " + member.getName());
        System.out.println("Email: " + member.getEmail());
        
        System.out.print("\nConfirm deletion? (yes/no): ");
        String confirm = input.nextLine().trim().toLowerCase();
        
        if (confirm.equals("yes")) {
            memberDatabase.remove(memberId);
            System.out.println("Member deleted successfully!");
        } else {
            System.out.println("Deletion cancelled.");
        }
    }
    
    private static void viewAllMembers() {
        System.out.println("\n" + repeatChar('=', 80));
        System.out.println("ALL LIBRARY MEMBERS");
        System.out.println(repeatChar('=', 80));
        
        if (memberDatabase.isEmpty()) {
            System.out.println("No members registered.");
            return;
        }
        
        System.out.printf("%-12s %-25s %-30s %-15s%n", 
                         "Member ID", "Name", "Email", "Phone");
        System.out.println(repeatChar('-', 80));
        
        for (Member member : memberDatabase.values()) {
            System.out.printf("%-12s %-25s %-30s %-15s%n",
                member.getMemberId(),
                truncate(member.getName(), 25),
                truncate(member.getEmail(), 30),
                member.getPhone()
            );
        }
        System.out.println(repeatChar('=', 80));
    }
    
    private static void viewIssuedBooksReport() {
        System.out.println("\n" + repeatChar('=', 90));
        System.out.println("ISSUED BOOKS REPORT");
        System.out.println(repeatChar('=', 90));
        
        if (issuedBooks.isEmpty()) {
            System.out.println("No books currently issued.");
            return;
        }
        
        System.out.printf("%-10s %-25s %-20s %-15s %-15s%n", 
                         "Book ID", "Title", "Issued To", "Issue Date", "Due Date");
        System.out.println(repeatChar('-', 90));
        
        for (IssuedBook issued : issuedBooks.values()) {
            Book book = bookDatabase.get(issued.getBookId());
            if (book != null) {
                System.out.printf("%-10s %-25s %-20s %-15s %-15s%n",
                    issued.getBookId(),
                    truncate(book.getTitle(), 25),
                    truncate(issued.getUserName(), 20),
                    issued.getIssueDate(),
                    issued.getDueDate()
                );
            }
        }
        System.out.println(repeatChar('=', 90));
    }
    
    // User Functions
    private static void browseByCategory() {
        System.out.println("\n" + repeatChar('=', 60));
        System.out.println("BROWSE BOOKS BY CATEGORY");
        System.out.println(repeatChar('=', 60));
        
        // Get unique categories
        Set<String> categories = new HashSet<>();
        for (Book book : bookDatabase.values()) {
            categories.add(book.getCategory());
        }
        
        System.out.println("Available Categories:");
        int i = 1;
        List<String> categoryList = new ArrayList<>(categories);
        for (String category : categoryList) {
            System.out.println(i++ + ". " + category);
        }
        
        System.out.print("\nSelect category number: ");
        try {
            int choice = Integer.parseInt(input.nextLine().trim());
            if (choice < 1 || choice > categoryList.size()) {
                System.out.println("Invalid choice!");
                return;
            }
            
            String selectedCategory = categoryList.get(choice - 1);
            System.out.println("\n" + repeatChar('-', 90));
            System.out.println("Books in " + selectedCategory + " category:");
            System.out.println(repeatChar('-', 90));
            System.out.printf("%-10s %-30s %-25s %-10s%n", 
                             "Book ID", "Title", "Author", "Available");
            System.out.println(repeatChar('-', 90));
            
            for (Book book : bookDatabase.values()) {
                if (book.getCategory().equals(selectedCategory)) {
                    System.out.printf("%-10s %-30s %-25s %-10d%n",
                        book.getBookId(),
                        truncate(book.getTitle(), 30),
                        truncate(book.getAuthor(), 25),
                        book.getAvailableCopies()
                    );
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input!");
        }
    }
    
    private static void searchBook() {
        System.out.println("\n" + repeatChar('=', 60));
        System.out.println("SEARCH BOOK");
        System.out.println(repeatChar('=', 60));
        
        System.out.print("Enter book title or author to search: ");
        String searchTerm = input.nextLine().trim().toLowerCase();
        
        System.out.println("\n" + repeatChar('-', 90));
        System.out.println("Search Results:");
        System.out.println(repeatChar('-', 90));
        System.out.printf("%-10s %-30s %-25s %-15s %-10s%n", 
                         "Book ID", "Title", "Author", "Category", "Available");
        System.out.println(repeatChar('-', 90));
        
        boolean found = false;
        for (Book book : bookDatabase.values()) {
            if (book.getTitle().toLowerCase().contains(searchTerm) || 
                book.getAuthor().toLowerCase().contains(searchTerm)) {
                System.out.printf("%-10s %-30s %-25s %-15s %-10d%n",
                    book.getBookId(),
                    truncate(book.getTitle(), 30),
                    truncate(book.getAuthor(), 25),
                    book.getCategory(),
                    book.getAvailableCopies()
                );
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("No books found matching your search.");
        }
    }
    
    private static void issueBook() {
        System.out.println("\n" + repeatChar('=', 60));
        System.out.println("ISSUE BOOK");
        System.out.println(repeatChar('=', 60));
        
        System.out.print("Enter Book ID to issue: ");
        String bookId = input.nextLine().trim();
        
        Book book = bookDatabase.get(bookId);
        if (book == null) {
            System.out.println("Book not found!");
            return;
        }
        
        if (book.getAvailableCopies() <= 0) {
            System.out.println("Sorry, this book is currently not available.");
            return;
        }
        
        String issueId = currentUser.getUsername() + "_" + bookId;
        if (issuedBooks.containsKey(issueId)) {
            System.out.println("You have already issued this book!");
            return;
        }
        
        book.issueBook();
        IssuedBook issued = new IssuedBook(bookId, currentUser.getUsername(), currentUser.getName());
        issuedBooks.put(issueId, issued);
        
        System.out.println("\nBook issued successfully!");
        System.out.println("Title: " + book.getTitle());
        System.out.println("Issue Date: " + issued.getIssueDate());
        System.out.println("Due Date: " + issued.getDueDate());
        System.out.println("\nPlease return the book by the due date to avoid fine.");
    }
    
    private static void returnBook() {
        System.out.println("\n" + repeatChar('=', 60));
        System.out.println("RETURN BOOK");
        System.out.println(repeatChar('=', 60));
        
        System.out.print("Enter Book ID to return: ");
        String bookId = input.nextLine().trim();
        
        String issueId = currentUser.getUsername() + "_" + bookId;
        IssuedBook issued = issuedBooks.get(issueId);
        
        if (issued == null) {
            System.out.println("You have not issued this book!");
            return;
        }
        
        Book book = bookDatabase.get(bookId);
        if (book != null) {
            book.returnBook();
        }
        
        issuedBooks.remove(issueId);
        
        System.out.println("\nBook returned successfully!");
        System.out.println("Title: " + (book != null ? book.getTitle() : "Unknown"));
        System.out.println("Thank you for returning the book on time!");
    }
    
    private static void viewMyIssuedBooks() {
        System.out.println("\n" + repeatChar('=', 90));
        System.out.println("MY ISSUED BOOKS");
        System.out.println(repeatChar('=', 90));
        
        boolean hasBooks = false;
        System.out.printf("%-10s %-30s %-25s %-15s %-15s%n", 
                         "Book ID", "Title", "Author", "Issue Date", "Due Date");
        System.out.println(repeatChar('-', 90));
        
        for (IssuedBook issued : issuedBooks.values()) {
            if (issued.getUserName().equals(currentUser.getName())) {
                Book book = bookDatabase.get(issued.getBookId());
                if (book != null) {
                    System.out.printf("%-10s %-30s %-25s %-15s %-15s%n",
                        issued.getBookId(),
                        truncate(book.getTitle(), 30),
                        truncate(book.getAuthor(), 25),
                        issued.getIssueDate(),
                        issued.getDueDate()
                    );
                    hasBooks = true;
                }
            }
        }
        
        if (!hasBooks) {
            System.out.println("You have not issued any books.");
        }
        System.out.println(repeatChar('=', 90));
    }
    
    private static void sendQueryEmail() {
        System.out.println("\n" + repeatChar('=', 60));
        System.out.println("SEND QUERY EMAIL");
        System.out.println(repeatChar('=', 60));
        
        System.out.print("Enter your query: ");
        String query = input.nextLine().trim();
        
        if (query.isEmpty()) {
            System.out.println("Query cannot be empty!");
            return;
        }
        
        System.out.println("\nYour query has been sent to the library administrator.");
        System.out.println("You will receive a response within 24 hours.");
        System.out.println("\nQuery Details:");
        System.out.println("From: " + currentUser.getName());
        System.out.println("Query: " + query);
    }
    
    private static void performLogout() {
        System.out.println("\nLogging out...");
        System.out.println("Thank you for using Digital Library Management System, " + 
                         currentUser.getName() + "!");
        
        currentUser = null;
        
        System.out.println("\n" + repeatChar('=', 60));
        System.out.println("Session terminated successfully.");
        System.out.println("Have a great day!");
        System.out.println(repeatChar('=', 60));
    }
    
    private static void exitApplication() {
        System.out.println("\nThank you for using Digital Library Management System!");
        System.out.println("Goodbye!");
        System.exit(0);
    }
    
    private static String truncate(String str, int length) {
        if (str.length() <= length) return str;
        return str.substring(0, length - 3) + "...";
    }
    
    private static String repeatChar(char ch, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(ch);
        }
        return sb.toString();
    }
}
