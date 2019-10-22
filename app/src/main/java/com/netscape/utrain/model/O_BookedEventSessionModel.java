package com.netscape.utrain.model;

public class O_BookedEventSessionModel {
    /**
     * id : 7
     * name : Boxing session
     * description : best coach and have a great day too
     * business_hour : 18:46:00
     * date : 2019-10-25
     * hourly_rate : 75
     * images : ["1571656736223.jpeg","1571656736388.jpeg","1571656736595.jpeg"]
     * phone : 5664424353
     * location : D-210, Industrial Area, Sector 74, Sahibzada Ajit Singh Nagar, Punjab 140308, India
     * latitude : 30.707004653027173
     * longitude : 76.6940177232027
     * guest_allowed : 78
     * guest_allowed_left : 78
     * created_by : 215
     */

    private int id;
    private String name;
    private String description;
    private String business_hour;
    private String date;
    private int hourly_rate;
    private String images;
    private String phone;
    private String location;
    private String latitude;
    private String longitude;
    private int guest_allowed;
    private int guest_allowed_left;
    private int created_by;

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

    public int getCreated_by() {
        return created_by;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }

    /**
     * id : 5
     * name : Chess training
     * description : chess training session
     * business_hour : 13:14:00
     * date : 2019-10-25
     * hourly_rate : 80
     * images : ["1571643913692.jpeg"]
     * phone : 8794564515
     * max_occupancy : 40
     * created_by : 215
     */

}
