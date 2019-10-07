package com.netscape.utrain.model;

public class OrgDataModel {
    private String Message;
    private String token;
    private OrgUserModel user;

    public OrgUserModel getUser() {
        return user;
    }

    public void setUser(OrgUserModel user) {
        this.user = user;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
