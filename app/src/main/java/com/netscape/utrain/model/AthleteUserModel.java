package com.netscape.utrain.model;

public class AthleteUserModel {

    /**
     * name : athlete1
     * email : athlete1291@gmail.com
     * phone : 12234534865
     * address : mohali
     * latitude : 30
     * longitude : 25
     * profile_image : RDsMTzABPj.png
     * updated_at : 2019-09-09 13:07:30
     * created_at : 2019-09-09 13:07:30
     * id : 10
     */

    private String name;
    private String email;
    private String phone;
    private String address;
    private String latitude;
    private String longitude;
    private String profile_image;
    private String updated_at;
    private String created_at;
    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
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
