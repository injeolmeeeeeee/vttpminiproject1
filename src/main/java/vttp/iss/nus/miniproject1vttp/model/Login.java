package vttp.iss.nus.miniproject1vttp.model;

import jakarta.validation.constraints.NotBlank;

public class Login {

    @NotBlank(message = "Username cannot be empty")
    private String username;

    // @NotBlank(message = "Password cannot be empty")
    // private String password;

    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}
    // public String getPassword() {return password;}
    // public void setPassword(String password) {this.password = password;}
    
}
