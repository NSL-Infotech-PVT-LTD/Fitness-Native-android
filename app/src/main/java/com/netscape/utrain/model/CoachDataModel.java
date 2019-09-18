package com.netscape.utrain.model;

public class CoachDataModel {
    private String Message;
    private String token;
    private CoachUserDataModel user;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
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
