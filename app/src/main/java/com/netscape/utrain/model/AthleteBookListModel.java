package com.netscape.utrain.model;

import java.util.List;

public class AthleteBookListModel {


    /**
     * status : true
     * code : 200
     * data : [{"id":4,"type":"event","target_id":8,"user_id":213,"tickets":3,"price":267,"user_details":{"name":"Ravi Sangelia","email":"ravi.singla06@gmail.com","phone":"7696669697","address":"304, Industrial Area, Sector 75, Sahibzada Ajit Singh Nagar, Punjab 160075, India","profile_image":"1571489574156.jpg","location":null,"business_hour_starts":null,"business_hour_ends":null,"bio":null,"expertise_years":null,"hourly_rate":null,"portfolio_image":null,"latitude":"30.6989534","longitude":"76.6917417","id":213,"roles":[{"name":"athlete"}]},"event":{"id":8,"name":"Zym","description":"zym body buildo event","start_date":"2019-10-22","end_date":"2019-10-24","start_time":"12:42:00","end_time":"16:42:00","price":89,"images":"[\"1571638376436.jpeg\"]","location":"85, Sector 76, Sahibzada Ajit Singh Nagar, Punjab 160055, India","latitude":"30.696295227517904","longitude":"76.69771112501621","created_by":215,"guest_allowed":80,"equipment_required":"no"}},{"id":5,"type":"event","target_id":6,"user_id":213,"tickets":2,"price":178,"user_details":{"name":"Ravi Sangelia","email":"ravi.singla06@gmail.com","phone":"7696669697","address":"304, Industrial Area, Sector 75, Sahibzada Ajit Singh Nagar, Punjab 160075, India","profile_image":"1571489574156.jpg","location":null,"business_hour_starts":null,"business_hour_ends":null,"bio":null,"expertise_years":null,"hourly_rate":null,"portfolio_image":null,"latitude":"30.6989534","longitude":"76.6917417","id":213,"roles":[{"name":"athlete"}]},"event":{"id":6,"name":"Football","description":"football event","start_date":"2019-10-21","end_date":"2019-10-21","start_time":"11:52:00","end_time":"11:59:00","price":89,"images":"[\"1571635416627.jpeg\"]","location":"D-210, Industrial Area, Sector 74, Sahibzada Ajit Singh Nagar, Punjab 140308, India","latitude":"30.70842695197211","longitude":"76.69303972274065","created_by":215,"guest_allowed":80,"equipment_required":"football"}},{"id":6,"type":"event","target_id":7,"user_id":213,"tickets":3,"price":267,"user_details":{"name":"Ravi Sangelia","email":"ravi.singla06@gmail.com","phone":"7696669697","address":"304, Industrial Area, Sector 75, Sahibzada Ajit Singh Nagar, Punjab 160075, India","profile_image":"1571489574156.jpg","location":null,"business_hour_starts":null,"business_hour_ends":null,"bio":null,"expertise_years":null,"hourly_rate":null,"portfolio_image":null,"latitude":"30.6989534","longitude":"76.6917417","id":213,"roles":[{"name":"athlete"}]},"event":{"id":7,"name":"Tennis","description":"tenis event","start_date":"2019-10-22","end_date":"2019-10-23","start_time":"12:30:00","end_time":"18:40:00","price":89,"images":"[\"1571638248946.jpeg\"]","location":"Unnamed Road, Industrial Area, Sector 75, Sahibzada Ajit Singh Nagar, Punjab 140308, India","latitude":"30.70746818526648","longitude":"76.69458266347647","created_by":215,"guest_allowed":10,"equipment_required":"Racket"}},{"id":10,"type":"event","target_id":18,"user_id":213,"tickets":3,"price":267,"user_details":{"name":"Ravi Sangelia","email":"ravi.singla06@gmail.com","phone":"7696669697","address":"304, Industrial Area, Sector 75, Sahibzada Ajit Singh Nagar, Punjab 160075, India","profile_image":"1571489574156.jpg","location":null,"business_hour_starts":null,"business_hour_ends":null,"bio":null,"expertise_years":null,"hourly_rate":null,"portfolio_image":null,"latitude":"30.6989534","longitude":"76.6917417","id":213,"roles":[{"name":"athlete"}]},"event":{"id":18,"name":"Employ yoga","description":"emp yoga class","start_date":"2019-10-23","end_date":"2019-10-26","start_time":"17:06:00","end_time":"17:06:00","price":89,"images":"[\"1571657831872.jpeg\",\"1571657831298.jpeg\",\"1571657831473.jpeg\"]","location":"Phase 8B, C-163, Industrial Area, Sector 74, Sahibzada Ajit Singh Nagar, Punjab 140308, India","latitude":"30.70689395843161","longitude":"76.68848935514688","created_by":215,"guest_allowed":80,"equipment_required":"no"}}]
     */

    private boolean status;
    private int code;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 4
         * type : event
         * target_id : 8
         * user_id : 213
         * tickets : 3
         * price : 267
         * user_details : {"name":"Ravi Sangelia","email":"ravi.singla06@gmail.com","phone":"7696669697","address":"304, Industrial Area, Sector 75, Sahibzada Ajit Singh Nagar, Punjab 160075, India","profile_image":"1571489574156.jpg","location":null,"business_hour_starts":null,"business_hour_ends":null,"bio":null,"expertise_years":null,"hourly_rate":null,"portfolio_image":null,"latitude":"30.6989534","longitude":"76.6917417","id":213,"roles":[{"name":"athlete"}]}
         * event : {"id":8,"name":"Zym","description":"zym body buildo event","start_date":"2019-10-22","end_date":"2019-10-24","start_time":"12:42:00","end_time":"16:42:00","price":89,"images":"[\"1571638376436.jpeg\"]","location":"85, Sector 76, Sahibzada Ajit Singh Nagar, Punjab 160055, India","latitude":"30.696295227517904","longitude":"76.69771112501621","created_by":215,"guest_allowed":80,"equipment_required":"no"}
         */

        private int id;
        private String type;
        private int target_id;
        private int user_id;
        private int tickets;
        private int price;
        private UserDetailsBean user_details;
        private EventBean event;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getTarget_id() {
            return target_id;
        }

        public void setTarget_id(int target_id) {
            this.target_id = target_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getTickets() {
            return tickets;
        }

        public void setTickets(int tickets) {
            this.tickets = tickets;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public UserDetailsBean getUser_details() {
            return user_details;
        }

        public void setUser_details(UserDetailsBean user_details) {
            this.user_details = user_details;
        }

        public EventBean getEvent() {
            return event;
        }

        public void setEvent(EventBean event) {
            this.event = event;
        }

        public static class UserDetailsBean {
            /**
             * name : Ravi Sangelia
             * email : ravi.singla06@gmail.com
             * phone : 7696669697
             * address : 304, Industrial Area, Sector 75, Sahibzada Ajit Singh Nagar, Punjab 160075, India
             * profile_image : 1571489574156.jpg
             * location : null
             * business_hour_starts : null
             * business_hour_ends : null
             * bio : null
             * expertise_years : null
             * hourly_rate : null
             * portfolio_image : null
             * latitude : 30.6989534
             * longitude : 76.6917417
             * id : 213
             * roles : [{"name":"athlete"}]
             */

            private String name;
            private String email;
            private String phone;
            private String address;
            private String profile_image;
            private Object location;
            private Object business_hour_starts;
            private Object business_hour_ends;
            private Object bio;
            private Object expertise_years;
            private Object hourly_rate;
            private Object portfolio_image;
            private String latitude;
            private String longitude;
            private int id;
            private List<RolesBean> roles;

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

            public Object getLocation() {
                return location;
            }

            public void setLocation(Object location) {
                this.location = location;
            }

            public Object getBusiness_hour_starts() {
                return business_hour_starts;
            }

            public void setBusiness_hour_starts(Object business_hour_starts) {
                this.business_hour_starts = business_hour_starts;
            }

            public Object getBusiness_hour_ends() {
                return business_hour_ends;
            }

            public void setBusiness_hour_ends(Object business_hour_ends) {
                this.business_hour_ends = business_hour_ends;
            }

            public Object getBio() {
                return bio;
            }

            public void setBio(Object bio) {
                this.bio = bio;
            }

            public Object getExpertise_years() {
                return expertise_years;
            }

            public void setExpertise_years(Object expertise_years) {
                this.expertise_years = expertise_years;
            }

            public Object getHourly_rate() {
                return hourly_rate;
            }

            public void setHourly_rate(Object hourly_rate) {
                this.hourly_rate = hourly_rate;
            }

            public Object getPortfolio_image() {
                return portfolio_image;
            }

            public void setPortfolio_image(Object portfolio_image) {
                this.portfolio_image = portfolio_image;
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

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public List<RolesBean> getRoles() {
                return roles;
            }

            public void setRoles(List<RolesBean> roles) {
                this.roles = roles;
            }

            public static class RolesBean {
                /**
                 * name : athlete
                 */

                private String name;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }
        }

        public static class EventBean {
            /**
             * id : 8
             * name : Zym
             * description : zym body buildo event
             * start_date : 2019-10-22
             * end_date : 2019-10-24
             * start_time : 12:42:00
             * end_time : 16:42:00
             * price : 89
             * images : ["1571638376436.jpeg"]
             * location : 85, Sector 76, Sahibzada Ajit Singh Nagar, Punjab 160055, India
             * latitude : 30.696295227517904
             * longitude : 76.69771112501621
             * created_by : 215
             * guest_allowed : 80
             * equipment_required : no
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
            private int created_by;
            private int guest_allowed;
            private String equipment_required;

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
        }
    }
}
