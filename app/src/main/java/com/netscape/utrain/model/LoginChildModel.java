package com.netscape.utrain.model;

import java.util.List;

public class LoginChildModel {


    private int id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String profile_image;
    private String location;
    private String latitude;
    private String longitude;
    private int business_hour;
    private String bio;
    private int expertise_years;
    private int hourly_rate;
    private String portfolio_image;
    private String params;
    private String state;
    private String profession;
    private String created_at;
    private String updated_at;
    private String deleted_at;
    private String token;
    private String business_hour_starts;
    private String business_hour_ends;
    private List<RolesModel> roles;
    private String experience_detail;
    private String training_service_detail;
    private String achievements;
    private String sport_id;
    private String is_notify;
    private List<ServiceIdModel> service_ids;

    public String getIs_notify() {
        return is_notify;
    }

    public void setIs_notify(String is_notify) {
        this.is_notify = is_notify;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
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

    public String getTraining_service_detail() {
        return training_service_detail;
    }

    public void setTraining_service_detail(String training_service_detail) {
        this.training_service_detail = training_service_detail;
    }

    public String getSport_id() {
        return sport_id;
    }

    public void setSport_id(String sport_id) {
        this.sport_id = sport_id;
    }

    public String getExperience_detail() {
        return experience_detail;
    }

    public void setExperience_detail(String experience_detail) {
        this.experience_detail = experience_detail;
    }

    public String getAchievements() {
        return achievements;
    }

    public void setAchievements(String achievements) {
        this.achievements = achievements;
    }


    public List<RolesModel> getRoles() {
        return roles;
    }

    public void setRoles(List<RolesModel> roles) {
        this.roles = roles;
    }

    public List<ServiceIdModel> getService_ids() {
        return service_ids;
    }

    public void setService_ids(List<ServiceIdModel> service_ids) {
        this.service_ids = service_ids;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public int getBusiness_hour() {
        return business_hour;
    }

    public void setBusiness_hour(int business_hour) {
        this.business_hour = business_hour;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }


    public int getExpertise_years() {
        return expertise_years;
    }

    public void setExpertise_years(int expertise_years) {
        this.expertise_years = expertise_years;
    }

    public int getHourly_rate() {
        return hourly_rate;
    }

    public void setHourly_rate(int hourly_rate) {
        this.hourly_rate = hourly_rate;
    }

    public String getPortfolio_image() {
        return portfolio_image;
    }

    public void setPortfolio_image(String portfolio_image) {
        this.portfolio_image = portfolio_image;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }
}
