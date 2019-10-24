package com.netscape.utrain.model;

import java.util.List;

public class AthleteBookListModel {


    /**
     * status : true
     * code : 200
     * data : {"current_page":1,"data":[{"id":28,"type":"event","target_id":20,"user_id":216,"tickets":3,"price":150,"user_details":{"name":"athlete","email":"athlete@athlete.com","phone":"7897897897","address":"304, Industrial Area, Sector 75, Sahibzada Ajit Singh Nagar, Punjab 160075, India","profile_image":"1571494629889.jpeg","location":null,"business_hour_starts":null,"business_hour_ends":null,"bio":null,"expertise_years":null,"hourly_rate":null,"portfolio_image":null,"latitude":"30.6989501","longitude":"76.6917537","id":216,"roles":[{"name":"athlete"}]},"event":{"id":20,"name":"abs workout","description":"6 pack workout training","start_date":"2019-10-24","end_date":"2019-10-25","start_time":"05:00:00","end_time":"08:00:00","price":50,"images":"[\"1571833959932.jpg\"]","location":"jago heights #87, 12, Shahi Majra, Industrial Area, Sector 58, Sahibzada Ajit Singh Nagar, Punjab 160055, India","latitude":"30.71953307668","longitude":"76.70708242803812","created_by":236,"guest_allowed":12,"guest_allowed_left":3,"equipment_required":"yoga matt"}},{"id":31,"type":"event","target_id":18,"user_id":216,"tickets":4,"price":356,"user_details":{"name":"athlete","email":"athlete@athlete.com","phone":"7897897897","address":"304, Industrial Area, Sector 75, Sahibzada Ajit Singh Nagar, Punjab 160075, India","profile_image":"1571494629889.jpeg","location":null,"business_hour_starts":null,"business_hour_ends":null,"bio":null,"expertise_years":null,"hourly_rate":null,"portfolio_image":null,"latitude":"30.6989501","longitude":"76.6917537","id":216,"roles":[{"name":"athlete"}]},"event":{"id":18,"name":"Employ yoga","description":"emp yoga class","start_date":"2019-10-23","end_date":"2019-10-26","start_time":"17:06:00","end_time":"17:06:00","price":89,"images":"[\"1571657831872.jpeg\",\"1571657831298.jpeg\",\"1571657831473.jpeg\"]","location":"Phase 8B, C-163, Industrial Area, Sector 74, Sahibzada Ajit Singh Nagar, Punjab 140308, India","latitude":"30.70689395843161","longitude":"76.68848935514688","created_by":215,"guest_allowed":80,"guest_allowed_left":71,"equipment_required":"no"}},{"id":32,"type":"event","target_id":15,"user_id":216,"tickets":5,"price":445,"user_details":{"name":"athlete","email":"athlete@athlete.com","phone":"7897897897","address":"304, Industrial Area, Sector 75, Sahibzada Ajit Singh Nagar, Punjab 160075, India","profile_image":"1571494629889.jpeg","location":null,"business_hour_starts":null,"business_hour_ends":null,"bio":null,"expertise_years":null,"hourly_rate":null,"portfolio_image":null,"latitude":"30.6989501","longitude":"76.6917537","id":216,"roles":[{"name":"athlete"}]},"event":{"id":15,"name":"Weight lift","description":"weigh training","start_date":"2019-10-23","end_date":"2019-10-26","start_time":"18:07:00","end_time":"05:07:00","price":89,"images":"[\"1571650769198.jpeg\",\"1571650769802.jpeg\",\"1571650769971.jpeg\",\"1571650769252.jpeg\"]","location":"Hinudstan Times House, C 164-165, Phase VIII B,, Sahibzada Ajit Singh Nagar, Punjab, India","latitude":"30.706488365429784","longitude":"76.68711807578802","created_by":215,"guest_allowed":90,"guest_allowed_left":85,"equipment_required":"no"}},{"id":33,"type":"event","target_id":18,"user_id":216,"tickets":5,"price":445,"user_details":{"name":"athlete","email":"athlete@athlete.com","phone":"7897897897","address":"304, Industrial Area, Sector 75, Sahibzada Ajit Singh Nagar, Punjab 160075, India","profile_image":"1571494629889.jpeg","location":null,"business_hour_starts":null,"business_hour_ends":null,"bio":null,"expertise_years":null,"hourly_rate":null,"portfolio_image":null,"latitude":"30.6989501","longitude":"76.6917537","id":216,"roles":[{"name":"athlete"}]},"event":{"id":18,"name":"Employ yoga","description":"emp yoga class","start_date":"2019-10-23","end_date":"2019-10-26","start_time":"17:06:00","end_time":"17:06:00","price":89,"images":"[\"1571657831872.jpeg\",\"1571657831298.jpeg\",\"1571657831473.jpeg\"]","location":"Phase 8B, C-163, Industrial Area, Sector 74, Sahibzada Ajit Singh Nagar, Punjab 140308, India","latitude":"30.70689395843161","longitude":"76.68848935514688","created_by":215,"guest_allowed":80,"guest_allowed_left":71,"equipment_required":"no"}}],"first_page_url":"https://dev.netscapelabs.com/utrain/public/api/booking/athlete/list?page=1","from":1,"last_page":1,"last_page_url":"https://dev.netscapelabs.com/utrain/public/api/booking/athlete/list?page=1","next_page_url":null,"path":"https://dev.netscapelabs.com/utrain/public/api/booking/athlete/list","per_page":20,"prev_page_url":null,"to":4,"total":4}
     */

    private boolean status;
    private int code;
    private DataBeanX data;

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

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * current_page : 1
         * data : [{"id":28,"type":"event","target_id":20,"user_id":216,"tickets":3,"price":150,"user_details":{"name":"athlete","email":"athlete@athlete.com","phone":"7897897897","address":"304, Industrial Area, Sector 75, Sahibzada Ajit Singh Nagar, Punjab 160075, India","profile_image":"1571494629889.jpeg","location":null,"business_hour_starts":null,"business_hour_ends":null,"bio":null,"expertise_years":null,"hourly_rate":null,"portfolio_image":null,"latitude":"30.6989501","longitude":"76.6917537","id":216,"roles":[{"name":"athlete"}]},"event":{"id":20,"name":"abs workout","description":"6 pack workout training","start_date":"2019-10-24","end_date":"2019-10-25","start_time":"05:00:00","end_time":"08:00:00","price":50,"images":"[\"1571833959932.jpg\"]","location":"jago heights #87, 12, Shahi Majra, Industrial Area, Sector 58, Sahibzada Ajit Singh Nagar, Punjab 160055, India","latitude":"30.71953307668","longitude":"76.70708242803812","created_by":236,"guest_allowed":12,"guest_allowed_left":3,"equipment_required":"yoga matt"}},{"id":31,"type":"event","target_id":18,"user_id":216,"tickets":4,"price":356,"user_details":{"name":"athlete","email":"athlete@athlete.com","phone":"7897897897","address":"304, Industrial Area, Sector 75, Sahibzada Ajit Singh Nagar, Punjab 160075, India","profile_image":"1571494629889.jpeg","location":null,"business_hour_starts":null,"business_hour_ends":null,"bio":null,"expertise_years":null,"hourly_rate":null,"portfolio_image":null,"latitude":"30.6989501","longitude":"76.6917537","id":216,"roles":[{"name":"athlete"}]},"event":{"id":18,"name":"Employ yoga","description":"emp yoga class","start_date":"2019-10-23","end_date":"2019-10-26","start_time":"17:06:00","end_time":"17:06:00","price":89,"images":"[\"1571657831872.jpeg\",\"1571657831298.jpeg\",\"1571657831473.jpeg\"]","location":"Phase 8B, C-163, Industrial Area, Sector 74, Sahibzada Ajit Singh Nagar, Punjab 140308, India","latitude":"30.70689395843161","longitude":"76.68848935514688","created_by":215,"guest_allowed":80,"guest_allowed_left":71,"equipment_required":"no"}},{"id":32,"type":"event","target_id":15,"user_id":216,"tickets":5,"price":445,"user_details":{"name":"athlete","email":"athlete@athlete.com","phone":"7897897897","address":"304, Industrial Area, Sector 75, Sahibzada Ajit Singh Nagar, Punjab 160075, India","profile_image":"1571494629889.jpeg","location":null,"business_hour_starts":null,"business_hour_ends":null,"bio":null,"expertise_years":null,"hourly_rate":null,"portfolio_image":null,"latitude":"30.6989501","longitude":"76.6917537","id":216,"roles":[{"name":"athlete"}]},"event":{"id":15,"name":"Weight lift","description":"weigh training","start_date":"2019-10-23","end_date":"2019-10-26","start_time":"18:07:00","end_time":"05:07:00","price":89,"images":"[\"1571650769198.jpeg\",\"1571650769802.jpeg\",\"1571650769971.jpeg\",\"1571650769252.jpeg\"]","location":"Hinudstan Times House, C 164-165, Phase VIII B,, Sahibzada Ajit Singh Nagar, Punjab, India","latitude":"30.706488365429784","longitude":"76.68711807578802","created_by":215,"guest_allowed":90,"guest_allowed_left":85,"equipment_required":"no"}},{"id":33,"type":"event","target_id":18,"user_id":216,"tickets":5,"price":445,"user_details":{"name":"athlete","email":"athlete@athlete.com","phone":"7897897897","address":"304, Industrial Area, Sector 75, Sahibzada Ajit Singh Nagar, Punjab 160075, India","profile_image":"1571494629889.jpeg","location":null,"business_hour_starts":null,"business_hour_ends":null,"bio":null,"expertise_years":null,"hourly_rate":null,"portfolio_image":null,"latitude":"30.6989501","longitude":"76.6917537","id":216,"roles":[{"name":"athlete"}]},"event":{"id":18,"name":"Employ yoga","description":"emp yoga class","start_date":"2019-10-23","end_date":"2019-10-26","start_time":"17:06:00","end_time":"17:06:00","price":89,"images":"[\"1571657831872.jpeg\",\"1571657831298.jpeg\",\"1571657831473.jpeg\"]","location":"Phase 8B, C-163, Industrial Area, Sector 74, Sahibzada Ajit Singh Nagar, Punjab 140308, India","latitude":"30.70689395843161","longitude":"76.68848935514688","created_by":215,"guest_allowed":80,"guest_allowed_left":71,"equipment_required":"no"}}]
         * first_page_url : https://dev.netscapelabs.com/utrain/public/api/booking/athlete/list?page=1
         * from : 1
         * last_page : 1
         * last_page_url : https://dev.netscapelabs.com/utrain/public/api/booking/athlete/list?page=1
         * next_page_url : null
         * path : https://dev.netscapelabs.com/utrain/public/api/booking/athlete/list
         * per_page : 20
         * prev_page_url : null
         * to : 4
         * total : 4
         */

        private int current_page;
        private String first_page_url;
        private int from;
        private int last_page;
        private String last_page_url;
        private Object next_page_url;
        private String path;
        private int per_page;
        private Object prev_page_url;
        private int to;
        private int total;
        private List<DataBean> data;

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

        public String getFirst_page_url() {
            return first_page_url;
        }

        public void setFirst_page_url(String first_page_url) {
            this.first_page_url = first_page_url;
        }

        public int getFrom() {
            return from;
        }

        public void setFrom(int from) {
            this.from = from;
        }

        public int getLast_page() {
            return last_page;
        }

        public void setLast_page(int last_page) {
            this.last_page = last_page;
        }

        public String getLast_page_url() {
            return last_page_url;
        }

        public void setLast_page_url(String last_page_url) {
            this.last_page_url = last_page_url;
        }

        public Object getNext_page_url() {
            return next_page_url;
        }

        public void setNext_page_url(Object next_page_url) {
            this.next_page_url = next_page_url;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public int getPer_page() {
            return per_page;
        }

        public void setPer_page(int per_page) {
            this.per_page = per_page;
        }

        public Object getPrev_page_url() {
            return prev_page_url;
        }

        public void setPrev_page_url(Object prev_page_url) {
            this.prev_page_url = prev_page_url;
        }

        public int getTo() {
            return to;
        }

        public void setTo(int to) {
            this.to = to;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * id : 28
             * type : event
             * target_id : 20
             * user_id : 216
             * tickets : 3
             * price : 150
             * user_details : {"name":"athlete","email":"athlete@athlete.com","phone":"7897897897","address":"304, Industrial Area, Sector 75, Sahibzada Ajit Singh Nagar, Punjab 160075, India","profile_image":"1571494629889.jpeg","location":null,"business_hour_starts":null,"business_hour_ends":null,"bio":null,"expertise_years":null,"hourly_rate":null,"portfolio_image":null,"latitude":"30.6989501","longitude":"76.6917537","id":216,"roles":[{"name":"athlete"}]}
             * event : {"id":20,"name":"abs workout","description":"6 pack workout training","start_date":"2019-10-24","end_date":"2019-10-25","start_time":"05:00:00","end_time":"08:00:00","price":50,"images":"[\"1571833959932.jpg\"]","location":"jago heights #87, 12, Shahi Majra, Industrial Area, Sector 58, Sahibzada Ajit Singh Nagar, Punjab 160055, India","latitude":"30.71953307668","longitude":"76.70708242803812","created_by":236,"guest_allowed":12,"guest_allowed_left":3,"equipment_required":"yoga matt"}
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
                 * name : athlete
                 * email : athlete@athlete.com
                 * phone : 7897897897
                 * address : 304, Industrial Area, Sector 75, Sahibzada Ajit Singh Nagar, Punjab 160075, India
                 * profile_image : 1571494629889.jpeg
                 * location : null
                 * business_hour_starts : null
                 * business_hour_ends : null
                 * bio : null
                 * expertise_years : null
                 * hourly_rate : null
                 * portfolio_image : null
                 * latitude : 30.6989501
                 * longitude : 76.6917537
                 * id : 216
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
                 * id : 20
                 * name : abs workout
                 * description : 6 pack workout training
                 * start_date : 2019-10-24
                 * end_date : 2019-10-25
                 * start_time : 05:00:00
                 * end_time : 08:00:00
                 * price : 50
                 * images : ["1571833959932.jpg"]
                 * location : jago heights #87, 12, Shahi Majra, Industrial Area, Sector 58, Sahibzada Ajit Singh Nagar, Punjab 160055, India
                 * latitude : 30.71953307668
                 * longitude : 76.70708242803812
                 * created_by : 236
                 * guest_allowed : 12
                 * guest_allowed_left : 3
                 * equipment_required : yoga matt
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
                private int guest_allowed_left;
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

                public int getGuest_allowed_left() {
                    return guest_allowed_left;
                }

                public void setGuest_allowed_left(int guest_allowed_left) {
                    this.guest_allowed_left = guest_allowed_left;
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
}
