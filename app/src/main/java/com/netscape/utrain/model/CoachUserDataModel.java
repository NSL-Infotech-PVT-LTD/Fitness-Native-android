package com.netscape.utrain.model;

import java.util.List;

public class CoachUserDataModel {

    /**
     * name : coach0985
     * email : coachdfsd6fgf0e5@gmail.com
     * phone : 6578548664
     * location : chd
     * latitude : 30
     * longitude : 50
     * business_hour_starts : 10:00
     * business_hour_ends : 15:00
     * bio : i am a certified coach
     * service_ids : [{"id":1,"isSelected":true,"name":"CARDIO FITNESS","price":"23"},{"id":2,"isSelected":true,"name":"PRE NATAL EXERCISE","price":"12"},{"id":3,"isSelected":true,"name":"SENIOR FITNESS TRAINING","price":"45"},{"id":4,"isSelected":true,"name":"STRENGTH TRAINING","price":"50"}]
     * expertise_years : 3
     * hourly_rate : 500
     * profile_image : SuQf9fBy4V.png
     * updated_at : 2019-09-17 10:04:57
     * created_at : 2019-09-17 10:04:57
     * id : 38
     */

    private String name;
    private String email;
    private String phone;
    private String location;
    private String latitude;
    private String longitude;
    private String business_hour_starts;
    private String business_hour_ends;
    private String bio;
    private String expertise_years;
    private String experience_detail;
    private String training_service_detail;
    private String hourly_rate;
    private String profile_image;
    private String updated_at;
    private String sport_id;

    public String getExperience_detail() {
        return experience_detail;
    }

    public void setExperience_detail(String experience_detail) {
        this.experience_detail = experience_detail;
    }

    public String getTraining_service_detail() {
        return training_service_detail;
    }

    public void setTraining_service_detail(String training_service_detail) {
        this.training_service_detail = training_service_detail;
    }

    private String created_at;
    private int id;
    private List<ServiceIdModel> service_ids;
    private List<RolesModel> roles;

    public String getSport_id() {
        return sport_id;
    }

    public void setSport_id(String sport_id) {
        this.sport_id = sport_id;
    }

    public List<ServiceIdModel> getService_ids() {
        return service_ids;
    }

    public void setService_ids(List<ServiceIdModel> service_ids) {
        this.service_ids = service_ids;
    }

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

    public String getBusiness_hour_starts() {
        return business_hour_starts;
    }

    public void setBusiness_hour_starts(String business_hour_starts) {
        this.business_hour_starts = business_hour_starts;
    }

    public String getBusiness_hour_ends() {
        return business_hour_ends;
    }

    public void setBusiness_hour_ends(String business_hour_ends) {
        this.business_hour_ends = business_hour_ends;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getExpertise_years() {
        return expertise_years;
    }

    public void setExpertise_years(String expertise_years) {
        this.expertise_years = expertise_years;
    }

    public String getHourly_rate() {
        return hourly_rate;
    }

    public void setHourly_rate(String hourly_rate) {
        this.hourly_rate = hourly_rate;
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
