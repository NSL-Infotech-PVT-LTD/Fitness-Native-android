package com.netscape.utrain.model;

public class
OrgCreateSpaceModel {

    /**
     * name : City gym
     * description : Urban gym
     * price_hourly : 400
     * availability_week : mon-fri
     * price_daily : 800
     * organizer_id : 95
     * images : ["1570615416565.jpg"]
     * updated_at : 2019-10-09 10:03:36
     * created_at : 2019-10-09 10:03:36
     * id : 13
     */

    private String name;
    private String description;
    private String price_hourly;
    private String availability_week;
    private String price_daily;
    private int organizer_id;
    private String images;
    private String updated_at;
    private String created_at;
    private int id;

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

    public String getPrice_hourly() {
        return price_hourly;
    }

    public void setPrice_hourly(String price_hourly) {
        this.price_hourly = price_hourly;
    }

    public String getAvailability_week() {
        return availability_week;
    }

    public void setAvailability_week(String availability_week) {
        this.availability_week = availability_week;
    }

    public String getPrice_daily() {
        return price_daily;
    }

    public void setPrice_daily(String price_daily) {
        this.price_daily = price_daily;
    }

    public int getOrganizer_id() {
        return organizer_id;
    }

    public void setOrganizer_id(int organizer_id) {
        this.organizer_id = organizer_id;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
