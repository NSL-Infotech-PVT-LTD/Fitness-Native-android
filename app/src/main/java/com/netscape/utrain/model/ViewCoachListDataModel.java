package com.netscape.utrain.model;

public class ViewCoachListDataModel {


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

    private int id;
    private String name;
    private String profile_image;
    private String bio;
    private String sport_id;
    private int organisation_id;
    private int hourly_rate;
    private String experience_detail;
    private int expertise_years;
    private String profession;
    private String training_service_detail;

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

    public int getHourly_rate() {
        return hourly_rate;
    }

    public void setHourly_rate(int hourly_rate) {
        this.hourly_rate = hourly_rate;
    }

    public String getExperience_detail() {
        return experience_detail;
    }

    public void setExperience_detail(String experience_detail) {
        this.experience_detail = experience_detail;
    }

    public int getExpertise_years() {
        return expertise_years;
    }

    public void setExpertise_years(int expertise_years) {
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
