package com.netscape.utrain.model;

import java.util.List;

public class AthleteSpaceBookList {


    /**
     * status : true
     * code : 200
     * data : [{"id":12,"type":"space","target_id":6,"user_id":213,"tickets":3,"price":267,"user_details":{"name":"Ravi Sangelia","email":"ravi.singla06@gmail.com","phone":"7696669697","address":"304, Industrial Area, Sector 75, Sahibzada Ajit Singh Nagar, Punjab 160075, India","profile_image":"1571489574156.jpg","location":null,"business_hour_starts":null,"business_hour_ends":null,"bio":null,"expertise_years":null,"hourly_rate":null,"portfolio_image":null,"latitude":"30.6989534","longitude":"76.6917417","id":213,"roles":[{"name":"athlete"}]},"space":{"id":6,"name":"Jakhu shimla","images":"[\"1571655545835.jpeg\",\"1571655545795.jpeg\",\"1571655545195.jpeg\",\"1571655545629.jpeg\"]","description":"djdjdjffj got back in town tomorrow so I can come to your","price_hourly":45,"availability_week":"Saturday-Thursday","created_by":215,"price_daily":68}},{"id":13,"type":"space","target_id":6,"user_id":213,"tickets":3,"price":267,"user_details":{"name":"Ravi Sangelia","email":"ravi.singla06@gmail.com","phone":"7696669697","address":"304, Industrial Area, Sector 75, Sahibzada Ajit Singh Nagar, Punjab 160075, India","profile_image":"1571489574156.jpg","location":null,"business_hour_starts":null,"business_hour_ends":null,"bio":null,"expertise_years":null,"hourly_rate":null,"portfolio_image":null,"latitude":"30.6989534","longitude":"76.6917417","id":213,"roles":[{"name":"athlete"}]},"space":{"id":6,"name":"Jakhu shimla","images":"[\"1571655545835.jpeg\",\"1571655545795.jpeg\",\"1571655545195.jpeg\",\"1571655545629.jpeg\"]","description":"djdjdjffj got back in town tomorrow so I can come to your","price_hourly":45,"availability_week":"Saturday-Thursday","created_by":215,"price_daily":68}},{"id":14,"type":"space","target_id":6,"user_id":213,"tickets":3,"price":267,"user_details":{"name":"Ravi Sangelia","email":"ravi.singla06@gmail.com","phone":"7696669697","address":"304, Industrial Area, Sector 75, Sahibzada Ajit Singh Nagar, Punjab 160075, India","profile_image":"1571489574156.jpg","location":null,"business_hour_starts":null,"business_hour_ends":null,"bio":null,"expertise_years":null,"hourly_rate":null,"portfolio_image":null,"latitude":"30.6989534","longitude":"76.6917417","id":213,"roles":[{"name":"athlete"}]},"space":{"id":6,"name":"Jakhu shimla","images":"[\"1571655545835.jpeg\",\"1571655545795.jpeg\",\"1571655545195.jpeg\",\"1571655545629.jpeg\"]","description":"djdjdjffj got back in town tomorrow so I can come to your","price_hourly":45,"availability_week":"Saturday-Thursday","created_by":215,"price_daily":68}},{"id":15,"type":"space","target_id":6,"user_id":213,"tickets":3,"price":267,"user_details":{"name":"Ravi Sangelia","email":"ravi.singla06@gmail.com","phone":"7696669697","address":"304, Industrial Area, Sector 75, Sahibzada Ajit Singh Nagar, Punjab 160075, India","profile_image":"1571489574156.jpg","location":null,"business_hour_starts":null,"business_hour_ends":null,"bio":null,"expertise_years":null,"hourly_rate":null,"portfolio_image":null,"latitude":"30.6989534","longitude":"76.6917417","id":213,"roles":[{"name":"athlete"}]},"space":{"id":6,"name":"Jakhu shimla","images":"[\"1571655545835.jpeg\",\"1571655545795.jpeg\",\"1571655545195.jpeg\",\"1571655545629.jpeg\"]","description":"djdjdjffj got back in town tomorrow so I can come to your","price_hourly":45,"availability_week":"Saturday-Thursday","created_by":215,"price_daily":68}}]
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
         * id : 12
         * type : space
         * target_id : 6
         * user_id : 213
         * tickets : 3
         * price : 267
         * user_details : {"name":"Ravi Sangelia","email":"ravi.singla06@gmail.com","phone":"7696669697","address":"304, Industrial Area, Sector 75, Sahibzada Ajit Singh Nagar, Punjab 160075, India","profile_image":"1571489574156.jpg","location":null,"business_hour_starts":null,"business_hour_ends":null,"bio":null,"expertise_years":null,"hourly_rate":null,"portfolio_image":null,"latitude":"30.6989534","longitude":"76.6917417","id":213,"roles":[{"name":"athlete"}]}
         * space : {"id":6,"name":"Jakhu shimla","images":"[\"1571655545835.jpeg\",\"1571655545795.jpeg\",\"1571655545195.jpeg\",\"1571655545629.jpeg\"]","description":"djdjdjffj got back in town tomorrow so I can come to your","price_hourly":45,"availability_week":"Saturday-Thursday","created_by":215,"price_daily":68}
         */

        private int id;
        private String type;
        private int target_id;
        private int user_id;
        private int tickets;
        private int price;
        private UserDetailsBean user_details;
        private SpaceBean space;

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

        public SpaceBean getSpace() {
            return space;
        }

        public void setSpace(SpaceBean space) {
            this.space = space;
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

        public static class SpaceBean {
            /**
             * id : 6
             * name : Jakhu shimla
             * images : ["1571655545835.jpeg","1571655545795.jpeg","1571655545195.jpeg","1571655545629.jpeg"]
             * description : djdjdjffj got back in town tomorrow so I can come to your
             * price_hourly : 45
             * availability_week : Saturday-Thursday
             * created_by : 215
             * price_daily : 68
             */

            private int id;
            private String name;
            private String images;
            private String description;
            private int price_hourly;
            private String availability_week;
            private int created_by;
            private int price_daily;

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

            public String getImages() {
                return images;
            }

            public void setImages(String images) {
                this.images = images;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public int getPrice_hourly() {
                return price_hourly;
            }

            public void setPrice_hourly(int price_hourly) {
                this.price_hourly = price_hourly;
            }

            public String getAvailability_week() {
                return availability_week;
            }

            public void setAvailability_week(String availability_week) {
                this.availability_week = availability_week;
            }

            public int getCreated_by() {
                return created_by;
            }

            public void setCreated_by(int created_by) {
                this.created_by = created_by;
            }

            public int getPrice_daily() {
                return price_daily;
            }

            public void setPrice_daily(int price_daily) {
                this.price_daily = price_daily;
            }
        }
    }
}
