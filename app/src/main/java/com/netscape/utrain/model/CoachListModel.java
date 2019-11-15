package com.netscape.utrain.model;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

public class CoachListModel implements Serializable {


    private int id;
    private String name;
    private String email;
    private String phone;
    private String location;
    private String latitude;
    private String longitude;
    private String profile_image;
    private String business_hour_starts;
    private String business_hour_ends;
    private String bio;
    private int expertise_years;
    private int hourly_rate;
    private String sport_id;
    private String profession;
    private String experience_detail;
    private String training_service_detail;
    private String portfolio_image;
    private List<RolesModel> roles;
    private List<ServiceIdModel> service_ids;
    private String rating;

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

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

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
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
}
