package com.netscape.utrain.model;

import java.io.File;

public class OrgUserDataModel {

    /**
     * name : organiserr19
     * email : organiserdvgg85@gmail.com
     * phone : 56774567567
     * location : delhi
     * latitude : 30
     * longitude : 25
     * bio : I am cerified .
     * service_ids : [12,15]
     * expertise_years : 3
     * hourly_rate : 500
     * business_hour_starts : 10:00
     * business_hour_ends : 15:00
     * profile_image : TFNe1AuvcS.png
     * portfolio_image : ["bh5drlQq7l.png","V4rLkgUNlN.png","2S6liYD91q.png","LKwO402Pt6.png"]
     * updated_at : 2019-09-14 07:27:17
     * created_at : 2019-09-14 07:27:17
     * id : 19
     */

    private String name;
    private String email;
    private String professionType;
    private String experienceDetail;
    private String trainingDetail;
    private String password;
    private String phone;
    private String location;
    private String latitude;
    private String longitude;
    private String bio;
    private String service_ids;
    private String expertise_years;
    private String hourly_rate;
    private String business_hour_starts;
    private String business_hour_ends;
    private String profile_image;
    private String portfolio_image;
    private String updated_at;
    private String created_at;
    private String port_folio_image1;
    private String port_folio_image2;
    private String port_folio_image3;
    private String port_folio_image4;
    private File profile_img;
    private int id;

    public File getProfile_img() {
        return profile_img;
    }

    public void setProfile_img(File profile_img) {
        this.profile_img = profile_img;
    }

    public String getExperienceDetail() {
        return experienceDetail;
    }

    public void setExperienceDetail(String experienceDetail) {
        this.experienceDetail = experienceDetail;
    }

    public String getTrainingDetail() {
        return trainingDetail;
    }

    public void setTrainingDetail(String trainingDetail) {
        this.trainingDetail = trainingDetail;
    }

    public String getProfessionType() {
        return professionType;
    }

    public void setProfessionType(String professionType) {
        this.professionType = professionType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPort_folio_image1() {
        return port_folio_image1;
    }

    public void setPort_folio_image1(String port_folio_image1) {
        this.port_folio_image1 = port_folio_image1;
    }

    public String getPort_folio_image2() {
        return port_folio_image2;
    }

    public void setPort_folio_image2(String port_folio_image2) {
        this.port_folio_image2 = port_folio_image2;
    }

    public String getPort_folio_image3() {
        return port_folio_image3;
    }

    public void setPort_folio_image3(String port_folio_image3) {
        this.port_folio_image3 = port_folio_image3;
    }

    public String getPort_folio_image4() {
        return port_folio_image4;
    }

    public void setPort_folio_image4(String port_folio_image4) {
        this.port_folio_image4 = port_folio_image4;
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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getService_ids() {
        return service_ids;
    }

    public void setService_ids(String service_ids) {
        this.service_ids = service_ids;
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

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getPortfolio_image() {
        return portfolio_image;
    }

    public void setPortfolio_image(String portfolio_image) {
        this.portfolio_image = portfolio_image;
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
