package com.netscape.utrain.model;

import java.io.File;
import java.io.Serializable;

public class ViewCoachListDataModel implements Serializable {


    /**
     * id : 1
     * name : orgcoach22
     * profile_image : 1573301673903.png
     * bio : i am certified coach
     * sport_id : [{"id":1,"name":"cricket"},{"id":2,"name":"cricketsss"}]
     * organisation_id : 333
     * hourly_rate : 200
     * experience_detail : I have worked in gold gym.
     * expertise_years : 4
     * profession : cardio specialist
     * training_service_detail : cardio specialist
     */


    // below fields are in organizerCoachUpdate Api, We can add these parameters to below mention model for reusability.
//    "remember_token": null,
//            "params": null,
//            "state": "0",
//            "deleted_at": null


    private int id;
    private String name;
    private String profile_image;
    private String bio;
    private String sport_id;
    private int organisation_id;
    private String hourly_rate;
    private String experience_detail;
    private String expertise_years;
    private String profession;
    private String training_service_detail;
    private String updated_at;
    private String created_at;
    private File oCoachProfileImg;
    private File police_doc;
    private String latitude;
    private String longitude;

    public File getPolice_doc() {
        return police_doc;
    }

    public void setPolice_doc(File police_doc) {
        this.police_doc = police_doc;
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

    public File getoCoachProfileImg() {
        return oCoachProfileImg;
    }

    public void setoCoachProfileImg(File oCoachProfileImg) {
        this.oCoachProfileImg = oCoachProfileImg;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getSport_id() {
        return sport_id;
    }

    public void setSport_id(String sport_id) {
        this.sport_id = sport_id;
    }

    public int getOrganisation_id() {
        return organisation_id;
    }

    public void setOrganisation_id(int organisation_id) {
        this.organisation_id = organisation_id;
    }

    public String getHourly_rate() {
        return hourly_rate;
    }

    public void setHourly_rate(String hourly_rate) {
        this.hourly_rate = hourly_rate;
    }

    public String getExperience_detail() {
        return experience_detail;
    }

    public void setExperience_detail(String experience_detail) {
        this.experience_detail = experience_detail;
    }

    public String getExpertise_years() {
        return expertise_years;
    }

    public void setExpertise_years(String expertise_years) {
        this.expertise_years = expertise_years;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getTraining_service_detail() {
        return training_service_detail;
    }

    public void setTraining_service_detail(String training_service_detail) {
        this.training_service_detail = training_service_detail;
    }
}
