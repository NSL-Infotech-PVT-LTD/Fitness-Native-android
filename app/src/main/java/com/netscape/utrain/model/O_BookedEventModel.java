package com.netscape.utrain.model;

public class O_BookedEventModel {

    /**
     * id : 18
     * name : Employ yoga
     * description : emp yoga class
     * start_date : 2019-10-23
     * end_date : 2019-10-26
     * start_time : 17:06:00
     * end_time : 17:06:00
     * price : 89
     * images : ["1571657831872.jpeg","1571657831298.jpeg","1571657831473.jpeg"]
     * location : Phase 8B, C-163, Industrial Area, Sector 74, Sahibzada Ajit Singh Nagar, Punjab 140308, India
     * latitude : 30.70689395843161
     * longitude : 76.68848935514688
     * created_by : 215
     * guest_allowed : 80
     * equipment_required : no
     */

    private int id;
    private String name;
    private String description;
    private String start_date;
    private String end_date;
    private String start_time;
    private String end_time;
    private int price;
    private String images;
    private String location;
    private String latitude;
    private String longitude;
    private int created_by;
    private int guest_allowed;
    private String equipment_required;

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
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
}
