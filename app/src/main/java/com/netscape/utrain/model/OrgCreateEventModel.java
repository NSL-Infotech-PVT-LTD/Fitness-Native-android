package com.netscape.utrain.model;

public class OrgCreateEventModel {


    /**
     * name : In-house workshopsss
     * description : In-house workshops
     * start_date : 2019-09-18
     * start_time : 01:00
     * end_date : 2019-10-18
     * end_time : 04:00
     * location : delhi
     * latitude : 30.33
     * longitude : 76.23
     * service_id : 1
     * guest_allowed : 30
     * equipment_required : 15
     * price : 400
     * organizer_id : 125
     * images : ["1570528818691.jpg"]
     * updated_at : 2019-10-08 10:00:18
     * created_at : 2019-10-08 10:00:18
     * id : 13
     */

    private String name;
    private String description;
    private String start_date;
    private String start_time;
    private String end_date;
    private String end_time;
    private String location;
    private String latitude;
    private String longitude;
    private String service_id;
    private String guest_allowed;
    private String equipment_required;
    private String price;
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

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
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

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getGuest_allowed() {
        return guest_allowed;
    }

    public void setGuest_allowed(String guest_allowed) {
        this.guest_allowed = guest_allowed;
    }

    public String getEquipment_required() {
        return equipment_required;
    }

    public void setEquipment_required(String equipment_required) {
        this.equipment_required = equipment_required;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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
