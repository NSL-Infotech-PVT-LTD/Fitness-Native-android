package com.netscape.utrain.model;

public class O_AllBookingDataListModel {

    /**
     * id : 5
     * type : event
     * target_id : 31
     * user_id : 283
     * tickets : 4
     * price : 320
     */

    private int id;
    private String type;
    private int target_id;
    private int user_id;
    private int tickets;
    private int price;
    private String payment_id;
    private O_AllBookingTargetDataModel target_data;
    private O_AllBookingUserDetailsModel user_details;
    private BookingDateModel booking_date;

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public BookingDateModel getBooking_date() {
        return booking_date;
    }

    public void setBooking_date(BookingDateModel booking_date) {
        this.booking_date = booking_date;
    }

    public O_AllBookingUserDetailsModel getUser_details() {
        return user_details;
    }

    public void setUser_details(O_AllBookingUserDetailsModel user_details) {
        this.user_details = user_details;
    }

    public O_AllBookingTargetDataModel getTarget_data() {
        return target_data;
    }

    public void setTarget_data(O_AllBookingTargetDataModel target_data) {
        this.target_data = target_data;
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
