package com.netscape.utrain.model;

public class O_SessionDataModel {

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
    private Object start_time;
    private Object end_time;
    private int hourly_rate;
    private String location;
    private String latitude;
    private String longitude;
    private String images;
    private String phone;
    private int guest_allowed;
    private int guest_allowed_left;
    private int created_by;

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

    public Object getStart_time() {
        return start_time;
    }

    public void setStart_time(Object start_time) {
        this.start_time = start_time;
    }

    public Object getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Object end_time) {
        this.end_time = end_time;
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
