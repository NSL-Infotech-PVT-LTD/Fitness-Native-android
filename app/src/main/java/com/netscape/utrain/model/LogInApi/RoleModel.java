package com.netscape.utrain.model.LogInApi;

public class RoleModel {

    private String message;
    private String token;
    private ChildModel user;

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

    public ChildModel getUser() {
        return user;
    }

    public void setUser(ChildModel user) {
        this.user = user;
    }
}
