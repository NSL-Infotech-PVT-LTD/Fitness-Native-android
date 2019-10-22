package com.netscape.utrain.model;

public class O_BookedEventSessionModel {

    /**
     * id : 5
     * name : Chess training
     * description : chess training session
     * business_hour : 13:14:00
     * date : 2019-10-25
     * hourly_rate : 80
     * images : ["1571643913692.jpeg"]
     * phone : 8794564515
     * max_occupancy : 40
     * created_by : 215
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
