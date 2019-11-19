package com.netscape.utrain.model;

public class NotificationDatamodel {


    /**
     * id : 6
     * title : Hurray!
     * body : You Booking confirmed
     * data : {"target_id":47}
     * user_id : 283
     */

    private int id;
    private String title;
    private String body;
    private String data;
    private int user_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
