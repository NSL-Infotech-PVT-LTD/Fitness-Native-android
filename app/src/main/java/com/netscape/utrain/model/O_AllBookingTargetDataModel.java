package com.netscape.utrain.model;

import java.util.List;

public class O_AllBookingTargetDataModel {

    /**
     * id : 31
     * name : Snow
     * description : Snow Event
     * start_date : 2019-11-09
     * end_date : 2019-11-22
     * start_time : 19:45:00
     * end_time : 19:45:00
     * price : 80
     * images : ["1573222584209.jpg","1573222584595.jpg"]
     * location : NH5, Phase 8, Industrial Area, Sector 71, Sahibzada Ajit Singh Nagar, Punjab 140308, India
     * latitude : 30.705226317448748
     * longitude : 76.69756595045327
     * created_by : 297
     * guest_allowed : 50
     * guest_allowed_left : 46
     * equipment_required : no
     */

    private int id;
    private String name;
    private String description;
    private String start_date;
    private String end_date;
    private String start_time;
    private String end_time;
    private List<String> availability_week;
    private String price_daily;
    private int price;
    private String images;
    private String location;
    private String latitude;
    private String longitude;
    private int created_by;
    private int guest_allowed;
    private int guest_allowed_left;
    private String equipment_required;

    public List<String> getAvailability_week() {
        return availability_week;
    }

    public void setAvailability_week(List<String> availability_week) {
        this.availability_week = availability_week;
    }

    public String getPrice_daily() {
        return price_daily;
    }

    public void setPrice_daily(String price_daily) {
        this.price_daily = price_daily;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public int getCreated_by() {
        return created_by;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }

    public int getGuest_allowed() {
        return guest_allowed;
    }

    public void setGuest_allowed(int guest_allowed) {
        this.guest_allowed = guest_allowed;
    }

    public int getGuest_allowed_left() {
        return guest_allowed_left;
    }

    public void setGuest_allowed_left(int guest_allowed_left) {
        this.guest_allowed_left = guest_allowed_left;
    }

    public String getEquipment_required() {
        return equipment_required;
    }

    public void setEquipment_required(String equipment_required) {
        this.equipment_required = equipment_required;
    }
}
