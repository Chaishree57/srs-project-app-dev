package com.examly.springapp.dto;
import com.examly.springapp.model.User;

public class SignupRequest {
    private String username;
    private String email;
    private String password;
    private User.Role role;
    private String franchiseId;
    private String territory;
    
    // Default constructor
    public SignupRequest() {}
    
    public SignupRequest(String username, String email, String password, User.Role role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }
    
    // Getters and Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public User.Role getRole() { return role; }
    public void setRole(User.Role role) { this.role = role; }
    public String getFranchiseId() { return franchiseId; }
    public void setFranchiseId(String franchiseId) { this.franchiseId = franchiseId; }
    public String getTerritory() { return territory; }
    public void setTerritory(String territory) { this.territory = territory; }
}