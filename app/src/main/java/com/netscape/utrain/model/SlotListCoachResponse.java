package com.netscape.utrain.model;

import java.util.List;

public class SlotListCoachResponse {


    /**
     * status : true
     * code : 200
     * data : {"available_slot":[["06:00","07:00"],["10:00","23:00"]],"event_slot":[{"id":30,"name":"my first event","description":"eyeirieijdi","start_date":"2020-03-01","end_date":"2020-03-02","start_time":"11:00:00","end_time":"14:00:00","price":55,"images_1":"1582885660844.jpg","images_2":"1582885660188.jpg","images_3":"1582885660858.jpg","images_4":"1582885660950.jpg","images_5":null,"location":"8A, Industrial Area, Sector 75, Sahibzada Ajit Singh Nagar, Punjab 160055, India","latitude":"30.699725062586367","longitude":"76.69164715334773","service_id":null,"created_by":35,"guest_allowed":100,"equipment_required":"yeye","guest_allowed_left":100,"sport_id":2,"images":"[\"1582885660844.jpg\",\"1582885660188.jpg\",\"1582885660858.jpg\",\"1582885660950.jpg\"]"}]}
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
        private List<List<String>> available_slot;
        private List<EventSlotBean> event_slot;

        public List<List<String>> getAvailable_slot() {
            return available_slot;
        }

        public void setAvailable_slot(List<List<String>> available_slot) {
            this.available_slot = available_slot;
        }

        public List<EventSlotBean> getEvent_slot() {
            return event_slot;
        }

        public void setEvent_slot(List<EventSlotBean> event_slot) {
            this.event_slot = event_slot;
        }

        public static class EventSlotBean {
            /**
             * id : 30
             * name : my first event
             * description : eyeirieijdi
             * start_date : 2020-03-01
             * end_date : 2020-03-02
             * start_time : 11:00:00
             * end_time : 14:00:00
             * price : 55
             * images_1 : 1582885660844.jpg
             * images_2 : 1582885660188.jpg
             * images_3 : 1582885660858.jpg
             * images_4 : 1582885660950.jpg
             * images_5 : null
             * location : 8A, Industrial Area, Sector 75, Sahibzada Ajit Singh Nagar, Punjab 160055, India
             * latitude : 30.699725062586367
             * longitude : 76.69164715334773
             * service_id : null
             * created_by : 35
             * guest_allowed : 100
             * equipment_required : yeye
             * guest_allowed_left : 100
             * sport_id : 2
             * images : ["1582885660844.jpg","1582885660188.jpg","1582885660858.jpg","1582885660950.jpg"]
             */

            private int id;
            private String name;
            private String description;
            private String start_date;
            private String end_date;
            private String start_time;
            private String end_time;
            private int price;
            private String images_1;
            private String images_2;
            private String images_3;
            private String images_4;
            private Object images_5;
            private String location;
            private String latitude;
            private String longitude;
            private Object service_id;
            private int created_by;
            private int guest_allowed;
            private String equipment_required;
            private int guest_allowed_left;
            private int sport_id;
            private String images;

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

            public String getImages_1() {
                return images_1;
            }

            public void setImages_1(String images_1) {
                this.images_1 = images_1;
            }

            public String getImages_2() {
                return images_2;
            }

            public void setImages_2(String images_2) {
                this.images_2 = images_2;
            }

            public String getImages_3() {
                return images_3;
            }

            public void setImages_3(String images_3) {
                this.images_3 = images_3;
            }

            public String getImages_4() {
                return images_4;
            }

            public void setImages_4(String images_4) {
                this.images_4 = images_4;
            }

            public Object getImages_5() {
                return images_5;
            }

            public void setImages_5(Object images_5) {
                this.images_5 = images_5;
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

            public Object getService_id() {
                return service_id;
            }

            public void setService_id(Object service_id) {
                this.service_id = service_id;
            }

            public int getCreated_by() {
                return created_by;
            }

            public void setCreated_by(int created_by) {
                this.created_by = created_by;
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

            public int getGuest_allowed_left() {
                return guest_allowed_left;
            }

            public void setGuest_allowed_left(int guest_allowed_left) {
                this.guest_allowed_left = guest_allowed_left;
            }

            public int getSport_id() {
                return sport_id;
            }

            public void setSport_id(int sport_id) {
                this.sport_id = sport_id;
            }

            public String getImages() {
                return images;
            }

            public void setImages(String images) {
                this.images = images;
            }
        }
    }
}
