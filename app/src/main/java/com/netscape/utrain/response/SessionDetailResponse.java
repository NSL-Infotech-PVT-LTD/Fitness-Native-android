package com.netscape.utrain.response;

import com.netscape.utrain.model.ErrorModel;

public class SessionDetailResponse {

    /**
     * status : true
     * code : 200
     * data : {"id":7,"name":"Boxing session","description":"best coach and have a great day too","business_hour":"18:46:00","date":"2019-10-25","hourly_rate":75,"location":"D-210, Industrial Area, Sector 74, Sahibzada Ajit Singh Nagar, Punjab 140308, India","latitude":"30.707004653027173","longitude":"76.6940177232027","images":"[\"1571656736223.jpeg\",\"1571656736388.jpeg\",\"1571656736595.jpeg\"]","phone":"5664424353","guest_allowed":78,"guest_allowed_left":78,"created_by":215,"params":null,"state":"0","created_at":"2019-10-21 11:18:56","updated_at":"2019-10-22 06:07:58","deleted_at":null}
     */

    private boolean status;
    private int code;
    private DataBean data;
    private ErrorModel error;

    public ErrorModel getError() {
        return error;
    }

    public void setError(ErrorModel error) {
        this.error = error;
    }

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
         * id : 7
         * name : Boxing session
         * description : best coach and have a great day too
         * business_hour : 18:46:00
         * date : 2019-10-25
         * hourly_rate : 75
         * location : D-210, Industrial Area, Sector 74, Sahibzada Ajit Singh Nagar, Punjab 140308, India
         * latitude : 30.707004653027173
         * longitude : 76.6940177232027
         * images : ["1571656736223.jpeg","1571656736388.jpeg","1571656736595.jpeg"]
         * phone : 5664424353
         * guest_allowed : 78
         * guest_allowed_left : 78
         * created_by : 215
         * params : null
         * state : 0
         * created_at : 2019-10-21 11:18:56
         * updated_at : 2019-10-22 06:07:58
         * deleted_at : null
         */

        private int id;
        private String name;
        private String description;
        private String business_hour;
        private String start_date;
        private String end_date;
        private String start_time;
        private String end_time;
        private String distance;
        private int hourly_rate;
        private String location;
        private String latitude;
        private String longitude;
        private String images;
        private String phone;
        private int guest_allowed;
        private int guest_allowed_left;
        private int created_by;
        private Object params;
        private String state;
        private String created_at;
        private String updated_at;
        private Object deleted_at;

        public String getStart_date() {
            return start_date;
        }

        public void setStart_date(String start_date) {
            this.start_date = start_date;
        }

        public String getEnd_date() {
            return end_date;
        }

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getBusiness_hour() {
            return business_hour;
        }

        public void setBusiness_hour(String business_hour) {
            this.business_hour = business_hour;
        }

        public int getHourly_rate() {
            return hourly_rate;
        }

        public void setHourly_rate(int hourly_rate) {
            this.hourly_rate = hourly_rate;
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

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getGuest_allowed() {
            return guest_allowed;
        }

        public void setGuest_allowed(int guest_allowed) {
            this.guest_allowed = guest_allowed;
        }

        public int getGuest_allowed_left() {
            return guest_allowed_left;
        }

        public void setGuest_allowed_left(int guest_allowed_left) {
            this.guest_allowed_left = guest_allowed_left;
        }

        public int getCreated_by() {
            return created_by;
        }

        public void setCreated_by(int created_by) {
            this.created_by = created_by;
        }

        public Object getParams() {
            return params;
        }

        public void setParams(Object params) {
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

        public Object getDeleted_at() {
            return deleted_at;
        }

        public void setDeleted_at(Object deleted_at) {
            this.deleted_at = deleted_at;
        }
    }
}
