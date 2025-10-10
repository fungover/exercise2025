package org.example.users;

// Event class
public class UserCreatedEvent {
    private String username;
    public UserCreatedEvent(String username) { this.username = username; }
    public String getUsername() { return username; }
    public void setUsername(String username) {this.username = username;}
}
