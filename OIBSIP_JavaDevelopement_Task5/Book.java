public class Book {
    private String bookId;
    private String title;
    private String author;
    private String category;
    private int totalCopies;
    private int availableCopies;
    
    public Book(String bookId, String title, String author, String category, int totalCopies) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.category = category;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
    }
    
    public String getBookId() {
        return bookId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public int getTotalCopies() {
        return totalCopies;
    }
    
    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
        if (this.availableCopies > totalCopies) {
            this.availableCopies = totalCopies;
        }
    }
    
    public int getAvailableCopies() {
        return availableCopies;
    }
    
    public void issueBook() {
        if (availableCopies > 0) {
            availableCopies--;
        }
    }
    
    public void returnBook() {
        if (availableCopies < totalCopies) {
            availableCopies++;
        }
    }
}
