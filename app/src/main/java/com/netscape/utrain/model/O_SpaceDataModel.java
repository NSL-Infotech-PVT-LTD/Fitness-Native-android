package com.netscape.utrain.model;

public class O_SpaceDataModel {

    /**
     * id : 14
     * name : jfjfjfud
     * images : ["1570627842488.png"]
     * description : kvkfkfkcjf
     * price_hourly : 0
     * availability_week : Thursday-Thursday
     * created_by : 125
     * price_daily : 0
     */

    private int id;
    private String name;
    private String images;
    private String description;
    private int price_hourly;
    private String availability_week;
    private String location;
    private String latitude;
    private String longitude;
    private String open_hours_from;
    private String open_hours_to;
    private int created_by;
    private int price_daily;

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

    public String getOpen_hours_from() {
        return open_hours_from;
    }

    public void setOpen_hours_from(String open_hours_from) {
        this.open_hours_from = open_hours_from;
    }

    public String getOpen_hours_to() {
        return open_hours_to;
    }

    public void setOpen_hours_to(String open_hours_to) {
        this.open_hours_to = open_hours_to;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice_hourly() {
        return price_hourly;
    }

    public void setPrice_hourly(int price_hourly) {
        this.price_hourly = price_hourly;
    }

    public String getAvailability_week() {
        return availability_week;
    }

    public void setAvailability_week(String availability_week) {
        this.availability_week = availability_week;
    }

    public int getCreated_by() {
        return created_by;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }

    public int getPrice_daily() {
        return price_daily;
    }

    public void setPrice_daily(int price_daily) {
        this.price_daily = price_daily;
    }
}
