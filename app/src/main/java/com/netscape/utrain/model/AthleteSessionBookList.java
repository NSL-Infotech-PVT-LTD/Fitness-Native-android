package com.netscape.utrain.model;

import java.util.List;

public class AthleteSessionBookList {


    /**
     * status : true
     * code : 200
     * data : [{"id":11,"type":"session","target_id":8,"user_id":213,"tickets":4,"price":356,"user_details":{"name":"Ravi Sangelia","email":"ravi.singla06@gmail.com","phone":"7696669697","address":"304, Industrial Area, Sector 75, Sahibzada Ajit Singh Nagar, Punjab 160075, India","profile_image":"1571489574156.jpg","location":null,"business_hour_starts":null,"business_hour_ends":null,"bio":null,"expertise_years":null,"hourly_rate":null,"portfolio_image":null,"latitude":"30.6989534","longitude":"76.6917417","id":213,"roles":[{"name":"athlete"}]},"session":{"id":8,"name":"22 oct session","description":"Taking team fitness to a new level through strength training and conditioning focused on the athletes\u2019 needs specific to their sport.","business_hour":"14:20:00","date":"2019-10-23","hourly_rate":1200,"images":"[\"1571721118626.jpeg\"]","phone":"7576567","guest_allowed":50,"created_by":226}},{"id":20,"type":"session","target_id":9,"user_id":213,"tickets":4,"price":1600,"user_details":{"name":"Ravi Sangelia","email":"ravi.singla06@gmail.com","phone":"7696669697","address":"304, Industrial Area, Sector 75, Sahibzada Ajit Singh Nagar, Punjab 160075, India","profile_image":"1571489574156.jpg","location":null,"business_hour_starts":null,"business_hour_ends":null,"bio":null,"expertise_years":null,"hourly_rate":null,"portfolio_image":null,"latitude":"30.6989534","longitude":"76.6917417","id":213,"roles":[{"name":"athlete"}]},"session":{"id":9,"name":"yoga","description":"session for senior citizen","business_hour":"11:30:00","date":"2019-10-23","hourly_rate":58,"images":"[\"1571724061832.jpeg\",\"1571724061683.jpeg\",\"1571724061296.jpeg\"]","phone":"9625856235","guest_allowed":90,"created_by":215}}]
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
         * id : 11
         * type : session
         * target_id : 8
         * user_id : 213
         * tickets : 4
         * price : 356
         * user_details : {"name":"Ravi Sangelia","email":"ravi.singla06@gmail.com","phone":"7696669697","address":"304, Industrial Area, Sector 75, Sahibzada Ajit Singh Nagar, Punjab 160075, India","profile_image":"1571489574156.jpg","location":null,"business_hour_starts":null,"business_hour_ends":null,"bio":null,"expertise_years":null,"hourly_rate":null,"portfolio_image":null,"latitude":"30.6989534","longitude":"76.6917417","id":213,"roles":[{"name":"athlete"}]}
         * session : {"id":8,"name":"22 oct session","description":"Taking team fitness to a new level through strength training and conditioning focused on the athletes\u2019 needs specific to their sport.","business_hour":"14:20:00","date":"2019-10-23","hourly_rate":1200,"images":"[\"1571721118626.jpeg\"]","phone":"7576567","guest_allowed":50,"created_by":226}
         */

        private int id;
        private String type;
        private int target_id;
        private int user_id;
        private int tickets;
        private int price;
        private UserDetailsBean user_details;
        private SessionBean session;

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

        public SessionBean getSession() {
            return session;
        }

        public void setSession(SessionBean session) {
            this.session = session;
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

        public static class SessionBean {
            /**
             * id : 8
             * name : 22 oct session
             * description : Taking team fitness to a new level through strength training and conditioning focused on the athletes’ needs specific to their sport.
             * business_hour : 14:20:00
             * date : 2019-10-23
             * hourly_rate : 1200
             * images : ["1571721118626.jpeg"]
             * phone : 7576567
             * guest_allowed : 50
             * created_by : 226
             */

            private int id;
            private String name;
            private String description;
            private String business_hour;
            private String date;
            private int hourly_rate;
            private String images;
            private String phone;
            private int guest_allowed;
            private int created_by;

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

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public int getHourly_rate() {
                return hourly_rate;
            }

            public void setHourly_rate(int hourly_rate) {
                this.hourly_rate = hourly_rate;
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

            public int getCreated_by() {
                return created_by;
            }

            public void setCreated_by(int created_by) {
                this.created_by = created_by;
            }
        }
    }
}
