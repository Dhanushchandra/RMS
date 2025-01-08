package com.recipe.RMS.dto;

public class LoginResponse {

    private String email;
    private String username;
    private String token;
    private String message;

    public LoginResponse() {}

    public LoginResponse(String email, String username, String token, String message) {
        this.email = email;
        this.username = username;
        this.token = token;
        this.message = message;
    }

    // Getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}


