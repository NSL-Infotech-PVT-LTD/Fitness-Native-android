package com.netscape.utrain.model;

import java.io.Serializable;

public class AthletePlaceModel implements Serializable {


    /**
     * id : 9
     * name : vista tower
     * images : ["1571832287955.jpg"]
     * description : Book for your events
     * price_hourly : 30
     * availability_week : Monday-Friday
     * open_hours_from : 11:20:00
     * open_hours_to : 17:42:00
     * created_by : 239
     * price_daily : 200
     * location : Lakhnaur Pind Rd, Industrial Area, Sector 76, Sahibzada Ajit Singh Nagar, Punjab 140308, India
     * latitude : 30.70021169005451
     * longitude : 76.69969227164985
     * distance : 0.48
     */

    private int id;
    private String name;
    private String images;
    private String description;
    private int price_hourly;
    private String availability_week;
    private String open_hours_from;
    private String open_hours_to;
    private int created_by;
    private int price_daily;
    private String location;
    private String latitude;
    private String longitude;
    private String distance;

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

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
