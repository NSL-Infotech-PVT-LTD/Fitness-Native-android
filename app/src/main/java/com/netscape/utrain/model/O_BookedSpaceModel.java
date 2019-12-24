package com.netscape.utrain.model;

import java.util.List;

public class O_BookedSpaceModel {
    /**
     * id : 6
     * name : Jakhu shimla
     * images : ["1571655545835.jpeg","1571655545795.jpeg","1571655545195.jpeg","1571655545629.jpeg"]
     * description : djdjdjffj got back in town tomorrow so I can come to your
     * price_hourly : 45
     * availability_week : Saturday-Thursday
     * location : Unnamed Road, Industrial Area, Sector 75, Sahibzada Ajit Singh Nagar, Punjab 140308, India
     * latitude : 30.706599060490753
     * longitude : 76.69311784207821
     * created_by : 215
     * price_daily : 68
     */

    private int id;
    private String name;
    private String images;
    private String description;
    private int price_hourly;
    private List<String> availability_week;
    private String location;
    private String latitude;
    private String longitude;
    private int created_by;
    private int price_daily;

    public List<String> getAvailability_week() {
        return availability_week;
    }

    public void setAvailability_week(List<String> availability_week) {
        this.availability_week = availability_week;
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

    public int getPrice_daily() {
        return price_daily;
    }

    public void setPrice_daily(int price_daily) {
        this.price_daily = price_daily;
    }

    /**
     * id : 6
     * name : Jakhu shimla
     * images : ["1571655545835.jpeg","1571655545795.jpeg","1571655545195.jpeg","1571655545629.jpeg"]
     * description : djdjdjffj got back in town tomorrow so I can come to your
     * price_hourly : 45
     * availability_week : Saturday-Thursday
     * created_by : 215
     * price_daily : 68
     */


}
