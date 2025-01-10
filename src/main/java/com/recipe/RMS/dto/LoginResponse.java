package com.recipe.RMS.dto;

public class LoginResponse {

    private String email;
    private String token;
    private String message;

    public LoginResponse() {}

    public LoginResponse(String email, String token, String message) {
        this.email = email;
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


