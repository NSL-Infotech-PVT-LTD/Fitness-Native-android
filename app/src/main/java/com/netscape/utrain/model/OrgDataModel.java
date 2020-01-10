package com.netscape.utrain.model;

public class OrgDataModel {
    private String message;
    private String token;
    private OrgUserModel user;

    public OrgUserModel getUser() {
        return user;
    }

    public void setUser(OrgUserModel user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String Message) {
        this.message = Message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
