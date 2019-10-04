package com.netscape.utrain.model;

public class AthleteEventListModel {


    /**
     * id : 4
     * name : In-house workshops
     * description : In-house workshops
     * start_at : 2019-09-18 01:00:15
     * end_at : 2019-10-18 04:00:15
     * price : 456
     * location : delhi
     * latitude : 30.33
     * longitude : 76.23
     * service_id : 1
     * organizer_id : 95
     * guest_allowed : 30
     * equipment_required : 15
     * distance : 37.49103753648836
     */

    private int id;
    private String name;
    private String description;
    private String start_at;
    private String end_at;
    private int price;
    private String location;
    private String latitude;
    private String longitude;
    private int service_id;
    private int organizer_id;
    private int guest_allowed;
    private String equipment_required;
    private double distance;
    private String images;

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
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

    public String getStart_at() {
        return start_at;
    }

    public void setStart_at(String start_at) {
        this.start_at = start_at;
    }

    public String getEnd_at() {
        return end_at;
    }

    public void setEnd_at(String end_at) {
        this.end_at = end_at;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public int getOrganizer_id() {
        return organizer_id;
    }

    public void setOrganizer_id(int organizer_id) {
        this.organizer_id = organizer_id;
    }

    public int getGuest_allowed() {
        return guest_allowed;
    }

    public void setGuest_allowed(int guest_allowed) {
        this.guest_allowed = guest_allowed;
    }

    public String getEquipment_required() {
        return equipment_required;
    }

    public void setEquipment_required(String equipment_required) {
        this.equipment_required = equipment_required;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
