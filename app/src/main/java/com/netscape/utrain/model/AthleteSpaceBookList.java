package com.netscape.utrain.model;

import java.util.List;

public class AthleteSpaceBookList {


    /**
     * status : true
     * code : 200
     * data : {"current_page":1,"data":[{"id":23,"type":"space","target_id":6,"user_id":283,"tickets":1,"price":204,"target_data":{"id":6,"name":"Jakhu shimla","images":"[\"1571655545835.jpeg\",\"1571655545795.jpeg\",\"1571655545195.jpeg\",\"1571655545629.jpeg\"]","description":"djdjdjffj got back in town tomorrow so I can come to your","price_hourly":45,"availability_week":"Saturday-Thursday","location":"Unnamed Road, Industrial Area, Sector 75, Sahibzada Ajit Singh Nagar, Punjab 140308, India","latitude":"30.706599060490753","longitude":"76.69311784207821","created_by":215,"price_daily":68},"booking_date":{"start":null,"end":null},"user_details":{"name":"adam","email":"adam@gmail.com","phone":"3215642864","address":"304, Industrial Area, Sector 75, Sahibzada Ajit Singh Nagar, Punjab 160075, India","profile_image":"1572869556500.jpg","location":null,"business_hour_starts":null,"business_hour_ends":null,"bio":null,"expertise_years":null,"hourly_rate":null,"portfolio_image":null,"latitude":"30.6989543","longitude":"76.6917761","id":283,"roles":[{"name":"athlete"}]},"space":{"id":6,"name":"Jakhu shimla","images":"[\"1571655545835.jpeg\",\"1571655545795.jpeg\",\"1571655545195.jpeg\",\"1571655545629.jpeg\"]","description":"djdjdjffj got back in town tomorrow so I can come to your","price_hourly":45,"availability_week":"Saturday-Thursday","location":"Unnamed Road, Industrial Area, Sector 75, Sahibzada Ajit Singh Nagar, Punjab 140308, India","latitude":"30.706599060490753","longitude":"76.69311784207821","created_by":215,"price_daily":68}},{"id":24,"type":"space","target_id":16,"user_id":283,"tickets":1,"price":276,"target_data":{"id":16,"name":"Cricket pitch","images":"[\"1573478076599.jpeg\",\"1573478076461.jpeg\",\"1573478076853.jpg\"]","description":"cricket training pitch","price_hourly":58,"availability_week":"Saturday-Sunday","location":"A-40 phase 8B, Industrial Area, Sahibzada Ajit Singh Nagar, Punjab, India","latitude":"30.706162044875605","longitude":"76.69611185789108","created_by":297,"price_daily":69},"booking_date":{"start":null,"end":null},"user_details":{"name":"adam","email":"adam@gmail.com","phone":"3215642864","address":"304, Industrial Area, Sector 75, Sahibzada Ajit Singh Nagar, Punjab 160075, India","profile_image":"1572869556500.jpg","location":null,"business_hour_starts":null,"business_hour_ends":null,"bio":null,"expertise_years":null,"hourly_rate":null,"portfolio_image":null,"latitude":"30.6989543","longitude":"76.6917761","id":283,"roles":[{"name":"athlete"}]},"space":{"id":16,"name":"Cricket pitch","images":"[\"1573478076599.jpeg\",\"1573478076461.jpeg\",\"1573478076853.jpg\"]","description":"cricket training pitch","price_hourly":58,"availability_week":"Saturday-Sunday","location":"A-40 phase 8B, Industrial Area, Sahibzada Ajit Singh Nagar, Punjab, India","latitude":"30.706162044875605","longitude":"76.69611185789108","created_by":297,"price_daily":69}}],"first_page_url":"https://dev.netscapelabs.com/utrain/public/api/booking/athlete/list?page=1","from":1,"last_page":1,"last_page_url":"https://dev.netscapelabs.com/utrain/public/api/booking/athlete/list?page=1","next_page_url":null,"path":"https://dev.netscapelabs.com/utrain/public/api/booking/athlete/list","per_page":20,"prev_page_url":null,"to":2,"total":2}
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
         * data : [{"id":23,"type":"space","target_id":6,"user_id":283,"tickets":1,"price":204,"target_data":{"id":6,"name":"Jakhu shimla","images":"[\"1571655545835.jpeg\",\"1571655545795.jpeg\",\"1571655545195.jpeg\",\"1571655545629.jpeg\"]","description":"djdjdjffj got back in town tomorrow so I can come to your","price_hourly":45,"availability_week":"Saturday-Thursday","location":"Unnamed Road, Industrial Area, Sector 75, Sahibzada Ajit Singh Nagar, Punjab 140308, India","latitude":"30.706599060490753","longitude":"76.69311784207821","created_by":215,"price_daily":68},"booking_date":{"start":null,"end":null},"user_details":{"name":"adam","email":"adam@gmail.com","phone":"3215642864","address":"304, Industrial Area, Sector 75, Sahibzada Ajit Singh Nagar, Punjab 160075, India","profile_image":"1572869556500.jpg","location":null,"business_hour_starts":null,"business_hour_ends":null,"bio":null,"expertise_years":null,"hourly_rate":null,"portfolio_image":null,"latitude":"30.6989543","longitude":"76.6917761","id":283,"roles":[{"name":"athlete"}]},"space":{"id":6,"name":"Jakhu shimla","images":"[\"1571655545835.jpeg\",\"1571655545795.jpeg\",\"1571655545195.jpeg\",\"1571655545629.jpeg\"]","description":"djdjdjffj got back in town tomorrow so I can come to your","price_hourly":45,"availability_week":"Saturday-Thursday","location":"Unnamed Road, Industrial Area, Sector 75, Sahibzada Ajit Singh Nagar, Punjab 140308, India","latitude":"30.706599060490753","longitude":"76.69311784207821","created_by":215,"price_daily":68}},{"id":24,"type":"space","target_id":16,"user_id":283,"tickets":1,"price":276,"target_data":{"id":16,"name":"Cricket pitch","images":"[\"1573478076599.jpeg\",\"1573478076461.jpeg\",\"1573478076853.jpg\"]","description":"cricket training pitch","price_hourly":58,"availability_week":"Saturday-Sunday","location":"A-40 phase 8B, Industrial Area, Sahibzada Ajit Singh Nagar, Punjab, India","latitude":"30.706162044875605","longitude":"76.69611185789108","created_by":297,"price_daily":69},"booking_date":{"start":null,"end":null},"user_details":{"name":"adam","email":"adam@gmail.com","phone":"3215642864","address":"304, Industrial Area, Sector 75, Sahibzada Ajit Singh Nagar, Punjab 160075, India","profile_image":"1572869556500.jpg","location":null,"business_hour_starts":null,"business_hour_ends":null,"bio":null,"expertise_years":null,"hourly_rate":null,"portfolio_image":null,"latitude":"30.6989543","longitude":"76.6917761","id":283,"roles":[{"name":"athlete"}]},"space":{"id":16,"name":"Cricket pitch","images":"[\"1573478076599.jpeg\",\"1573478076461.jpeg\",\"1573478076853.jpg\"]","description":"cricket training pitch","price_hourly":58,"availability_week":"Saturday-Sunday","location":"A-40 phase 8B, Industrial Area, Sahibzada Ajit Singh Nagar, Punjab, India","latitude":"30.706162044875605","longitude":"76.69611185789108","created_by":297,"price_daily":69}}]
         * first_page_url : https://dev.netscapelabs.com/utrain/public/api/booking/athlete/list?page=1
         * from : 1
         * last_page : 1
         * last_page_url : https://dev.netscapelabs.com/utrain/public/api/booking/athlete/list?page=1
         * next_page_url : null
         * path : https://dev.netscapelabs.com/utrain/public/api/booking/athlete/list
         * per_page : 20
         * prev_page_url : null
         * to : 2
         * total : 2
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
             * id : 23
             * type : space
             * target_id : 6
             * user_id : 283
             * tickets : 1
             * price : 204
             * target_data : {"id":6,"name":"Jakhu shimla","images":"[\"1571655545835.jpeg\",\"1571655545795.jpeg\",\"1571655545195.jpeg\",\"1571655545629.jpeg\"]","description":"djdjdjffj got back in town tomorrow so I can come to your","price_hourly":45,"availability_week":"Saturday-Thursday","location":"Unnamed Road, Industrial Area, Sector 75, Sahibzada Ajit Singh Nagar, Punjab 140308, India","latitude":"30.706599060490753","longitude":"76.69311784207821","created_by":215,"price_daily":68}
             * booking_date : {"start":null,"end":null}
             * user_details : {"name":"adam","email":"adam@gmail.com","phone":"3215642864","address":"304, Industrial Area, Sector 75, Sahibzada Ajit Singh Nagar, Punjab 160075, India","profile_image":"1572869556500.jpg","location":null,"business_hour_starts":null,"business_hour_ends":null,"bio":null,"expertise_years":null,"hourly_rate":null,"portfolio_image":null,"latitude":"30.6989543","longitude":"76.6917761","id":283,"roles":[{"name":"athlete"}]}
             * space : {"id":6,"name":"Jakhu shimla","images":"[\"1571655545835.jpeg\",\"1571655545795.jpeg\",\"1571655545195.jpeg\",\"1571655545629.jpeg\"]","description":"djdjdjffj got back in town tomorrow so I can come to your","price_hourly":45,"availability_week":"Saturday-Thursday","location":"Unnamed Road, Industrial Area, Sector 75, Sahibzada Ajit Singh Nagar, Punjab 140308, India","latitude":"30.706599060490753","longitude":"76.69311784207821","created_by":215,"price_daily":68}
             */

            private int id;
            private String type;
            private int target_id;
            private int user_id;
            private int tickets;
            private int price;
            private String status;
            private String start_date;
            private String end_date;
            private String start_time;
            private String end_time;
            private String rating;
            private String payment_id;
            private TargetDataBean target_data;
            private BookingDateBean booking_date;
            private UserDetailsBean user_details;
            private SpaceBean space;

            public String getPayment_id() {
                return payment_id;
            }

            public void setPayment_id(String payment_id) {
                this.payment_id = payment_id;
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

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getRating() {
                return rating;
            }

            public void setRating(String rating) {
                this.rating = rating;
            }

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

            public TargetDataBean getTarget_data() {
                return target_data;
            }

            public void setTarget_data(TargetDataBean target_data) {
                this.target_data = target_data;
            }

            public BookingDateBean getBooking_date() {
                return booking_date;
            }

            public void setBooking_date(BookingDateBean booking_date) {
                this.booking_date = booking_date;
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

            public static class TargetDataBean {
                /**
                 * id : 6
                 * name : Jakhu shimla
                 * images : ["1571655545835.jpeg","1571655545795.jpeg","1571655545195.jpeg","1571655545629.jpeg"]
                 * description : djdjdjffj got back in town tomorrow so I can come to your
                 * price_hourly : 45
                 * availability_week : Saturday-Thursday
                 * location : Unnamed Road, Industrial Area, Sector 75, Sahibzada Ajit Singh Nagar, Punjab 140308, India
                 * latitude : 30.706599060490753
                 * longitude : 76.69311784207821
                 * created_by : 215
                 * price_daily : 68
                 */

                private int id;
                private String name;
                private String images;
                private String description;
                private int price_hourly;
                private List<String> availability_week;
                private String location;
                private String latitude;
                private String longitude;
                private int created_by;
                private int price_daily;

                public List<String> getAvailability_week() {
                    return availability_week;
                }

                public void setAvailability_week(List<String> availability_week) {
                    this.availability_week = availability_week;
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

                public int getPrice_daily() {
                    return price_daily;
                }

                public void setPrice_daily(int price_daily) {
                    this.price_daily = price_daily;
                }
            }

            public static class BookingDateBean {
                /**
                 * start : null
                 * end : null
                 */

                private Object start;
                private Object end;

                public Object getStart() {
                    return start;
                }

                public void setStart(Object start) {
                    this.start = start;
                }

                public Object getEnd() {
                    return end;
                }

                public void setEnd(Object end) {
                    this.end = end;
                }
            }

            public static class UserDetailsBean {
                /**
                 * name : adam
                 * email : adam@gmail.com
                 * phone : 3215642864
                 * address : 304, Industrial Area, Sector 75, Sahibzada Ajit Singh Nagar, Punjab 160075, India
                 * profile_image : 1572869556500.jpg
                 * location : null
                 * business_hour_starts : null
                 * business_hour_ends : null
                 * bio : null
                 * expertise_years : null
                 * hourly_rate : null
                 * portfolio_image : null
                 * latitude : 30.6989543
                 * longitude : 76.6917761
                 * id : 283
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
                 * location : Unnamed Road, Industrial Area, Sector 75, Sahibzada Ajit Singh Nagar, Punjab 140308, India
                 * latitude : 30.706599060490753
                 * longitude : 76.69311784207821
                 * created_by : 215
                 * price_daily : 68
                 */

                private int id;
                private String name;
                private String images;
                private String description;
                private int price_hourly;
                private List<String> availability_week;
                private String location;
                private String latitude;
                private String longitude;
                private int created_by;
                private int price_daily;

                public List<String> getAvailability_week() {
                    return availability_week;
                }

                public void setAvailability_week(List<String> availability_week) {
                    this.availability_week = availability_week;
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

                public int getPrice_daily() {
                    return price_daily;
                }

                public void setPrice_daily(int price_daily) {
                    this.price_daily = price_daily;
                }
            }
        }
    }
}
