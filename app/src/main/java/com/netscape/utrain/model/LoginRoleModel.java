package com.netscape.utrain.model;

public class LoginRoleModel {

    private String message;
    private String token;
    private LoginChildModel user;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LoginChildModel getUser() {
        return user;
    }

    public void setUser(LoginChildModel user) {
        this.user = user;
    }
}
