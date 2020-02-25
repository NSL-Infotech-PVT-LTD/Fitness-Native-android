package com.netscape.utrain.model;

import java.util.List;

public class CoachDetailAny {


    /**
     * status : true
     * code : 200
     * data : {"id":10,"name":"Optional(pooja)","email":"cccc@gmail.com","phone":"9579754686","location":"4th Floor E-302, Vista Tower, Industrial Area, Sector 75, Sahibzada Ajit Singh Nagar, Punjab 140308, India","latitude":"30.6988972","longitude":"30.6988972","profile_image":"1580714440238.jpg","business_hour_starts":"05:49:00","business_hour_ends":"21:49:00","bio":"jfjfjfj","expertise_years":6,"sport_id":"[{\"id\":1,\"isCheckekd\":true,\"name\":\"Soccer\"}]","hourly_rate":50,"service_ids":[{"isSelected":"true","name":"Zumba/ Bhagra","price":"50","id":5}],"profession":"Cricket coach","experience_detail":"I have five year expppppppp","training_service_detail":"best training for the first time in","police_doc":"1580714441616.jpg","rating":"0","roles":[{"id":2,"name":"coach"}],"portfolio_image":"[]"}
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
         * id : 10
         * name : Optional(pooja)
         * email : cccc@gmail.com
         * phone : 9579754686
         * location : 4th Floor E-302, Vista Tower, Industrial Area, Sector 75, Sahibzada Ajit Singh Nagar, Punjab 140308, India
         * latitude : 30.6988972
         * longitude : 30.6988972
         * profile_image : 1580714440238.jpg
         * business_hour_starts : 05:49:00
         * business_hour_ends : 21:49:00
         * bio : jfjfjfj
         * expertise_years : 6
         * sport_id : [{"id":1,"isCheckekd":true,"name":"Soccer"}]
         * hourly_rate : 50
         * service_ids : [{"isSelected":"true","name":"Zumba/ Bhagra","price":"50","id":5}]
         * profession : Cricket coach
         * experience_detail : I have five year expppppppp
         * training_service_detail : best training for the first time in
         * police_doc : 1580714441616.jpg
         * rating : 0
         * roles : [{"id":2,"name":"coach"}]
         * portfolio_image : []
         */

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
        private String sport_id;
        private int hourly_rate;
        private String profession;
        private String experience_detail;
        private String training_service_detail;
        private String police_doc;
        private String rating;
        private String portfolio_image;
        private List<ServiceIdsBean> service_ids;
        private List<RolesBean> roles;

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

        public String getSport_id() {
            return sport_id;
        }

        public void setSport_id(String sport_id) {
            this.sport_id = sport_id;
        }

        public int getHourly_rate() {
            return hourly_rate;
        }

        public void setHourly_rate(int hourly_rate) {
            this.hourly_rate = hourly_rate;
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

        public String getPolice_doc() {
            return police_doc;
        }

        public void setPolice_doc(String police_doc) {
            this.police_doc = police_doc;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public String getPortfolio_image() {
            return portfolio_image;
        }

        public void setPortfolio_image(String portfolio_image) {
            this.portfolio_image = portfolio_image;
        }

        public List<ServiceIdsBean> getService_ids() {
            return service_ids;
        }

        public void setService_ids(List<ServiceIdsBean> service_ids) {
            this.service_ids = service_ids;
        }

        public List<RolesBean> getRoles() {
            return roles;
        }

        public void setRoles(List<RolesBean> roles) {
            this.roles = roles;
        }

        public static class ServiceIdsBean {
            /**
             * isSelected : true
             * name : Zumba/ Bhagra
             * price : 50
             * id : 5
             */

            private String isSelected;
            private String name;
            private String price;
            private int id;
            private boolean isChecked;

            public boolean isChecked() {
                return isChecked;
            }

            public void setChecked(boolean checked) {
                isChecked = checked;
            }

            public String getIsSelected() {
                return isSelected;
            }

            public void setIsSelected(String isSelected) {
                this.isSelected = isSelected;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }

        public static class RolesBean {
            /**
             * id : 2
             * name : coach
             */

            private int id;
            private String name;

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
        }
    }
}
