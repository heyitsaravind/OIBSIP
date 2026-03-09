public class User {
    private String username;
    private String password;
    private String name;
    private boolean isAdmin;
    
    public User(String username, String password, String name, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.isAdmin = isAdmin;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public String getName() {
        return name;
    }
    
    public boolean isAdmin() {
        return isAdmin;
    }
}
