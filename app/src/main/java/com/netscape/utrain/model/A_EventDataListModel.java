package com.netscape.utrain.model;

public class A_EventDataListModel {

    /**
     * id : 32
     * name : my first event
     * description : it will ttr
     * start_date : 0000-00-00
     * end_date : 0000-00-00
     * start_time : 14:27:00
     * end_time : 00:00:00
     * price : 20
     * images : ["1571382624998.png"]
     * location : 3273, Lakhnaur Pind Rd, Sector 71, Sahibzada Ajit Singh Nagar, Punjab 140308, India
     * latitude : 30.70344160698188
     * longitude : 76.70087546110153
     * service_id : 2
     * created_by : 193
     * guest_allowed : 20
     * equipment_required : dghhv
     * distance : 3054.78
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
    private int service_id;
    private int created_by;
    private int guest_allowed;
    private String equipment_required;
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

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
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

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
