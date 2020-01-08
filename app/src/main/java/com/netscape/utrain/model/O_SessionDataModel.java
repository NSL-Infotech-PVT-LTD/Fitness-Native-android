package com.netscape.utrain.model;

import java.io.Serializable;

public class O_SessionDataModel implements Serializable {

    /**
     * id : 18
     * name : Snow sesion
     * description : snow session
     * start_date : 2019-11-09
     * end_date : 2019-11-11
     * start_time : null
     * end_time : null
     * hourly_rate : 90
     * location : 8B, Industrial Area, Sector 74, Sahibzada Ajit Singh Nagar, Punjab 160071, India
     * latitude : 30.707881269023268
     * longitude : 76.69546611607075
     * images : ["1573222826870.jpg"]
     * phone : 8568568545
     * guest_allowed : 20
     * guest_allowed_left : 18
     * created_by : 297
     */

    private int id;
    private String name;
    private String description;
    private String start_date;
    private String end_date;
    private String start_time;
    private String end_time;
    private int hourly_rate;
    private String location;
    private String latitude;
    private String longitude;
    private String images;
    private boolean IsBooked;
    private String phone;
    private int guest_allowed;
    private int guest_allowed_left;
    private int created_by;
    private String business_hour;
    private String date;
    private int max_occupancy;
    private String equipment_required;
    private int service_id;
    private int price;

    public boolean isBooked() {
        return IsBooked;
    }

    public void setBooked(boolean booked) {
        IsBooked = booked;
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

    public int getMax_occupancy() {
        return max_occupancy;
    }

    public void setMax_occupancy(int max_occupancy) {
        this.max_occupancy = max_occupancy;
    }

    public String getEquipment_required() {
        return equipment_required;
    }

    public void setEquipment_required(String equipment_required) {
        this.equipment_required = equipment_required;
    }

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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

    public int getHourly_rate() {
        return hourly_rate;
    }

    public void setHourly_rate(int hourly_rate) {
        this.hourly_rate = hourly_rate;
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
}
