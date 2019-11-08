package com.netscape.utrain.model;

import java.util.List;

public class O_AllBookingUserDetailsModel {

    /**
     * name : adam
     * email : adam@gmail.com
     * phone : 3215642864
     * address : 304, Industrial Area, Sector 75, Sahibzada Ajit Singh Nagar, Punjab 160075, India
     * profile_image : 1572869556500.jpg
     * location : null
     * business_hour_starts : null
     * business_hour_ends : null
     * bio : null
     * expertise_years : null
     * hourly_rate : null
     * portfolio_image : null
     * latitude : 30.6989543
     * longitude : 76.6917761
     * id : 283
     */

    private String name;
    private String email;
    private String phone;
    private String address;
    private String profile_image;
    private Object location;
    private Object business_hour_starts;
    private Object business_hour_ends;
    private Object bio;
    private Object expertise_years;
    private Object hourly_rate;
    private Object portfolio_image;
    private String latitude;
    private String longitude;
    private int id;
    private List<RolesModel> roles;

    public List<RolesModel> getRoles() {
        return roles;
    }

    public void setRoles(List<RolesModel> roles) {
        this.roles = roles;
    }

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

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public Object getLocation() {
        return location;
    }

    public void setLocation(Object location) {
        this.location = location;
    }

    public Object getBusiness_hour_starts() {
        return business_hour_starts;
    }

    public void setBusiness_hour_starts(Object business_hour_starts) {
        this.business_hour_starts = business_hour_starts;
    }

    public Object getBusiness_hour_ends() {
        return business_hour_ends;
    }

    public void setBusiness_hour_ends(Object business_hour_ends) {
        this.business_hour_ends = business_hour_ends;
    }

    public Object getBio() {
        return bio;
    }

    public void setBio(Object bio) {
        this.bio = bio;
    }

    public Object getExpertise_years() {
        return expertise_years;
    }

    public void setExpertise_years(Object expertise_years) {
        this.expertise_years = expertise_years;
    }

    public Object getHourly_rate() {
        return hourly_rate;
    }

    public void setHourly_rate(Object hourly_rate) {
        this.hourly_rate = hourly_rate;
    }

    public Object getPortfolio_image() {
        return portfolio_image;
    }

    public void setPortfolio_image(Object portfolio_image) {
        this.portfolio_image = portfolio_image;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
