package com.netscape.utrain.model;

public class AthletePlaceModel {

    /**
     * id : 11
     * name : City gym
     * images : ["1570112173882.jpg","1570112173313.png","1570112173146.jpg"]
     * description : Urban gym
     * price_hourly : 400
     * availability_week : mon-fri
     * organizer_id : 95
     * price_daily : 800
     */
    private int id;
    private String name;
    private String images;
    private String description;
    private int price_hourly;
    private String availability_week;
    private int organizer_id;
    private int price_daily;
    private String latitude;
    private String longitude;
    private String location;
    private String distance;

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
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

    public int getOrganizer_id() {
        return organizer_id;
    }

    public void setOrganizer_id(int organizer_id) {
        this.organizer_id = organizer_id;
    }

    public int getPrice_daily() {
        return price_daily;
    }

    public void setPrice_daily(int price_daily) {
        this.price_daily = price_daily;
    }
}
