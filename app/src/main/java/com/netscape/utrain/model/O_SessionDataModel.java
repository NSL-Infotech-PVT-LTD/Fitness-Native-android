package com.netscape.utrain.model;

public class O_SessionDataModel {

    /**
     * id : 15
     * name : yoga
     * description : fkfkf kfkfkf fjgjsudu g fifidididussi
     * business_hour : 12:20:00
     * date : 0000-00-00
     * hourly_rate : 1234
     * images : ["1570690275617.jpg"]
     * phone : 9625632563
     * max_occupancy : 134
     * created_by : 125
     */

    private int id;
    private String name;
    private String description;
    private String business_hour;
    private String date;
    private int hourly_rate;
    private String images;
    private String phone;
    private int max_occupancy;
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

    public String getBusiness_hour() {
        return business_hour;
    }

    public void setBusiness_hour(String business_hour) {
        this.business_hour = business_hour;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getHourly_rate() {
        return hourly_rate;
    }

    public void setHourly_rate(int hourly_rate) {
        this.hourly_rate = hourly_rate;
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

    public int getMax_occupancy() {
        return max_occupancy;
    }

    public void setMax_occupancy(int max_occupancy) {
        this.max_occupancy = max_occupancy;
    }

    public int getCreated_by() {
        return created_by;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }
}
