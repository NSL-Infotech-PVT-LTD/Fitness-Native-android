package com.netscape.utrain.model;

public class CoachDataModel {
    private String message;
    private String token;
    private CoachUserDataModel user;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public CoachUserDataModel getUser() {
        return user;
    }

    public void setUser(CoachUserDataModel user) {
        this.user = user;
    }
}
