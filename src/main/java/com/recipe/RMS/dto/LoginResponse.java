package com.recipe.RMS.dto;

import java.util.UUID;

public class LoginResponse {

    private String email;
    private String token;
    private String message;
    private UUID uid;

    public LoginResponse() {}

    public LoginResponse(String email, String token, String message) {
        this.email = email;
        this.token = token;
        this.message = message;
    }


    // Getters and setters


    public UUID getUid() {
        return uid;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

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


