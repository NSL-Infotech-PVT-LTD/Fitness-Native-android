package com.netscape.utrain.model;

public class C_SessionListModel {

    /**
     * id : 20
     * name : Outdoor training
     * description : Taking team fitness to a new level through strength training and conditioning focused on the athletes’ needs specific to their sport.
     * business_hour : 14:20:00
     * date : 2019-10-13
     * hourly_rate : 1200
     * images : ["1571483865522.jpg"]
     * phone : 34556778678678
     * max_occupancy : 50
     * created_by : 189
     */

    private int id;
    private String name;
    private String description;
    private String business_hour;
    private String date;
    private int hourly_rate;
    private String images;
    private String phone;
    private int max_occupancy;
    private int created_by;
    private String start_date;
    private String end_date;
    private String start_time;
    private String end_time;
    private String location;
    private String latitude;
    private String longitude;
    private String guest_allowed;
    private String guest_allowed_left;

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

    public String getGuest_allowed() {
        return guest_allowed;
    }

    public void setGuest_allowed(String guest_allowed) {
        this.guest_allowed = guest_allowed;
    }

    public String getGuest_allowed_left() {
        return guest_allowed_left;
    }

    public void setGuest_allowed_left(String guest_allowed_left) {
        this.guest_allowed_left = guest_allowed_left;
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

    public String getBusiness_hour() {
        return business_hour;
    }

    public void setBusiness_hour(String business_hour) {
        this.business_hour = business_hour;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getHourly_rate() {
        return hourly_rate;
    }

    public void setHourly_rate(int hourly_rate) {
        this.hourly_rate = hourly_rate;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getMax_occupancy() {
        return max_occupancy;
    }

    public void setMax_occupancy(int max_occupancy) {
        this.max_occupancy = max_occupancy;
    }

    public int getCreated_by() {
        return created_by;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }
}
