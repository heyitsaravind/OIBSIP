public class User {
    private String loginId;
    private String password;
    private String name;
    
    public User(String loginId, String password, String name) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
    }
    
    public String getLoginId() {
        return loginId;
    }
    
    public String getPassword() {
        return password;
    }
    
    public String getName() {
        return name;
    }
}
