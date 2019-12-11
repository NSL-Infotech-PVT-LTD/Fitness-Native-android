package com.netscape.utrain.model;

public class EventBookingModel {


    /**
     * status : true
     * code : 200
     * data : {"id":7,"name":"Charity fundraising\n","description":"In-house workshops","start_date":"2019-09-18","end_date":"2019-10-08","start_time":"03:00:00","end_time":"08:00:00","price":400,"images":"[\"1570110626201.jpg\"]","location":"Vancouver","latitude":"49.246292","longitude":"-123.116226","service_id":1,"organizer_id":95,"guest_allowed":30,"equipment_required":"15","params":null,"state":"0","created_at":"2019-10-03 13:50:26","updated_at":"2019-10-15 06:59:27","deleted_at":null}
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
         * id : 7
         * name : Charity fundraising
         * description : In-house workshops
         * start_date : 2019-09-18
         * end_date : 2019-10-08
         * start_time : 03:00:00
         * end_time : 08:00:00
         * price : 400
         * images : ["1570110626201.jpg"]
         * location : Vancouver
         * latitude : 49.246292
         * longitude : -123.116226
         * service_id : 1
         * organizer_id : 95
         * guest_allowed : 30
         * equipment_required : 15
         * params : null
         * state : 0
         * created_at : 2019-10-03 13:50:26
         * updated_at : 2019-10-15 06:59:27
         * deleted_at : null
         */

        private int id;
        private String name;
        private String description;
        private String start_date;
        private String end_date;
        private String start_time;
        private String end_time;
        private int price;
        private String images;
        private String location;
        private String latitude;
        private String longitude;
        private int service_id;
        private int organizer_id;
        private int guest_allowed;
        private int guest_allowed_left;
        private String equipment_required;
        private Object params;
        private String state;
        private String created_at;
        private String updated_at;
        private Object deleted_at;

        public int getGuest_allowed_left() {
            return guest_allowed_left;
        }

        public void setGuest_allowed_left(int guest_allowed_left) {
            this.guest_allowed_left = guest_allowed_left;
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

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
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

        public int getService_id() {
            return service_id;
        }

        public void setService_id(int service_id) {
            this.service_id = service_id;
        }

        public int getOrganizer_id() {
            return organizer_id;
        }

        public void setOrganizer_id(int organizer_id) {
            this.organizer_id = organizer_id;
        }

        public int getGuest_allowed() {
            return guest_allowed;
        }

        public void setGuest_allowed(int guest_allowed) {
            this.guest_allowed = guest_allowed;
        }

        public String getEquipment_required() {
            return equipment_required;
        }

        public void setEquipment_required(String equipment_required) {
            this.equipment_required = equipment_required;
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
