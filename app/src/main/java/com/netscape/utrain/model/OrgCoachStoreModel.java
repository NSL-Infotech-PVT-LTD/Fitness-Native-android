package com.netscape.utrain.model;

public class OrgCoachStoreModel {


    /**
     * status : true
     * code : 201
     * data : {"message":"Created Successfully","organiserCoach":{"name":"orgcoach","bio":"i am certified coach","sport_id":"[{\"id\":1,\"name\":\"cricket\"},{\"id\":2,\"name\":\"cricketsss\"}]","hourly_rate":"200","experience_detail":"I have worked in gold gym.","expertise_years":"4","profession":"cardio specialist","training_service_detail":"cardio specialist","profile_image":"1573458478624.jpeg","organisation_id":333,"updated_at":"2019-11-11 07:47:58","created_at":"2019-11-11 07:47:58","id":2}}
     */

    private boolean status;
    private int code;
    private DataBean data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * message : Created Successfully
         * organiserCoach : {"name":"orgcoach","bio":"i am certified coach","sport_id":"[{\"id\":1,\"name\":\"cricket\"},{\"id\":2,\"name\":\"cricketsss\"}]","hourly_rate":"200","experience_detail":"I have worked in gold gym.","expertise_years":"4","profession":"cardio specialist","training_service_detail":"cardio specialist","profile_image":"1573458478624.jpeg","organisation_id":333,"updated_at":"2019-11-11 07:47:58","created_at":"2019-11-11 07:47:58","id":2}
         */

        private String message;
        private OrganiserCoachBean organiserCoach;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public OrganiserCoachBean getOrganiserCoach() {
            return organiserCoach;
        }

        public void setOrganiserCoach(OrganiserCoachBean organiserCoach) {
            this.organiserCoach = organiserCoach;
        }

        public static class OrganiserCoachBean {
            /**
             * name : orgcoach
             * bio : i am certified coach
             * sport_id : [{"id":1,"name":"cricket"},{"id":2,"name":"cricketsss"}]
             * hourly_rate : 200
             * experience_detail : I have worked in gold gym.
             * expertise_years : 4
             * profession : cardio specialist
             * training_service_detail : cardio specialist
             * profile_image : 1573458478624.jpeg
             * organisation_id : 333
             * updated_at : 2019-11-11 07:47:58
             * created_at : 2019-11-11 07:47:58
             * id : 2
             */

            private String name;
            private String bio;
            private String sport_id;
            private String hourly_rate;
            private String experience_detail;
            private String expertise_years;
            private String profession;
            private String training_service_detail;
            private String profile_image;
            private int organisation_id;
            private String updated_at;
            private String created_at;
            private int id;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
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

            public String getProfile_image() {
                return profile_image;
            }

            public void setProfile_image(String profile_image) {
                this.profile_image = profile_image;
            }

            public int getOrganisation_id() {
                return organisation_id;
            }

            public void setOrganisation_id(int organisation_id) {
                this.organisation_id = organisation_id;
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
    }
}
