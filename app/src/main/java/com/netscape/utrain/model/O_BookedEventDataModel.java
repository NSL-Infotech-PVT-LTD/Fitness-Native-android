package com.netscape.utrain.model;

public class O_BookedEventDataModel {

    /**
     * id : 7
     * type : event
     * target_id : 18
     * user_id : 229
     * tickets : 3
     * price : 267
     */

    private int id;
    private String type;
    private int target_id;
    private int user_id;
    private int tickets;
    private int price;
    private O_BookedEventModel event;
    private O_BookedUserDetailsModel user_details;

    public O_BookedEventModel getEvent() {
        return event;
    }

    public void setEvent(O_BookedEventModel event) {
        this.event = event;
    }

    public O_BookedUserDetailsModel getUser_details() {
        return user_details;
    }

    public void setUser_details(O_BookedUserDetailsModel user_details) {
        this.user_details = user_details;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTarget_id() {
        return target_id;
    }

    public void setTarget_id(int target_id) {
        this.target_id = target_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getTickets() {
        return tickets;
    }

    public void setTickets(int tickets) {
        this.tickets = tickets;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
