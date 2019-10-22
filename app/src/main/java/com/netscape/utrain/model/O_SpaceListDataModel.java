package com.netscape.utrain.model;

public class O_SpaceListDataModel {

    /**
     * id : 12
     * type : space
     * target_id : 6
     * user_id : 213
     * tickets : 3
     * price : 267
     */

    private int id;
    private String type;
    private int target_id;
    private int user_id;
    private int tickets;
    private int price;
    private O_BookedUserDetailsModel user_details;
    private O_BookedSpaceModel space;

    public O_BookedUserDetailsModel getUser_details() {
        return user_details;
    }

    public void setUser_details(O_BookedUserDetailsModel user_details) {
        this.user_details = user_details;
    }

    public O_BookedSpaceModel getSpace() {
        return space;
    }

    public void setSpace(O_BookedSpaceModel space) {
        this.space = space;
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
