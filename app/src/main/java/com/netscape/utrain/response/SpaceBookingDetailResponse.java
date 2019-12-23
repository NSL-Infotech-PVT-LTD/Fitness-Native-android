package com.netscape.utrain.response;

import java.util.List;

public class SpaceBookingDetailResponse {

    /**
     * status : true
     * code : 200
     * data : {"id":143,"type":"space","target_id":36,"user_id":415,"owner_id":451,"tickets":1,"price":24,"status":"pending","payment_details":"{\"id\":\"ch_1FrnE8BULVolCXCQYXXhqqGX\",\"object\":\"charge\",\"amount\":2400,\"amount_refunded\":0,\"application\":null,\"application_fee\":null,\"application_fee_amount\":null,\"balance_transaction\":\"txn_1FrnE9BULVolCXCQmSZbnio9\",\"billing_details\":{\"address\":{\"city\":null,\"country\":null,\"line1\":null,\"line2\":null,\"postal_code\":null,\"state\":null},\"email\":null,\"name\":null,\"phone\":null},\"captured\":true,\"created\":1576855036,\"currency\":\"usd\",\"customer\":null,\"description\":\"Charge for the booking booked through utrain app\",\"destination\":null,\"dispute\":null,\"disputed\":false,\"failure_code\":null,\"failure_message\":null,\"fraud_details\":[],\"invoice\":null,\"livemode\":false,\"metadata\":[],\"on_behalf_of\":null,\"order\":null,\"outcome\":{\"network_status\":\"approved_by_network\",\"reason\":null,\"risk_level\":\"normal\",\"risk_score\":50,\"seller_message\":\"Payment complete.\",\"type\":\"authorized\"},\"paid\":true,\"payment_intent\":null,\"payment_method\":\"card_1FrnE7BULVolCXCQFdFlBrKC\",\"payment_method_details\":{\"card\":{\"brand\":\"visa\",\"checks\":{\"address_line1_check\":null,\"address_postal_code_check\":null,\"cvc_check\":\"pass\"},\"country\":\"US\",\"exp_month\":11,\"exp_year\":2025,\"fingerprint\":\"40ymjgQlhCRMugBF\",\"funding\":\"unknown\",\"installments\":null,\"last4\":\"1111\",\"network\":\"visa\",\"three_d_secure\":null,\"wallet\":null},\"type\":\"card\"},\"receipt_email\":null,\"receipt_number\":null,\"receipt_url\":\"https:\\/\\/pay.stripe.com\\/receipts\\/acct_1FEwmlBULVolCXCQ\\/ch_1FrnE8BULVolCXCQYXXhqqGX\\/rcpt_GOaOMniDBcc2mFKTa5Xf6RpG1b3uvQK\",\"refunded\":false,\"refunds\":{\"object\":\"list\",\"data\":[],\"has_more\":false,\"total_count\":0,\"url\":\"\\/v1\\/charges\\/ch_1FrnE8BULVolCXCQYXXhqqGX\\/refunds\"},\"review\":null,\"shipping\":null,\"source\":{\"id\":\"card_1FrnE7BULVolCXCQFdFlBrKC\",\"object\":\"card\",\"address_city\":null,\"address_country\":null,\"address_line1\":null,\"address_line1_check\":null,\"address_line2\":null,\"address_state\":null,\"address_zip\":null,\"address_zip_check\":null,\"brand\":\"Visa\",\"country\":\"US\",\"customer\":null,\"cvc_check\":\"pass\",\"dynamic_last4\":null,\"exp_month\":11,\"exp_year\":2025,\"fingerprint\":\"40ymjgQlhCRMugBF\",\"funding\":\"unknown\",\"last4\":\"1111\",\"metadata\":[],\"name\":null,\"tokenization_method\":null},\"source_transfer\":null,\"statement_descriptor\":null,\"statement_descriptor_suffix\":null,\"status\":\"succeeded\",\"transfer_data\":null,\"transfer_group\":null}","payment_id":"ch_1FrnE8BULVolCXCQYXXhqqGX","rating":"0","params":null,"state":"0","created_at":"2019-12-20 20:47:16","updated_at":"2019-12-20 20:47:17","deleted_at":null,"target_data":{"id":36,"name":"omg01","images_1":"1576854113368.jpg","images_2":null,"images_3":null,"images_4":null,"images_5":null,"description":"sdfhjkk","price_hourly":12,"availability_week":["1","2","3","4","5","6","7"],"location":"Unnamed Road, Sector 91, Sahibzada Ajit Singh Nagar, Punjab 140307, India","latitude":"30.697834443640826","longitude":"76.68409790843725","created_by":451,"price_daily":240,"images":"[\"1576854113368.jpg\"]"},"booking_date":{"start":"2019-12-23","end":"2019-12-23"},"user_details":{"name":"testathlete","email":"test1@gmail.com","phone":"3333586950","address":"4th Floor E-302, Vista Tower, Industrial Area, Sector 75, Sahibzada Ajit Singh Nagar, Punjab 140308, India","profile_image":"1576849824109.jpg","location":null,"business_hour_starts":null,"business_hour_ends":null,"bio":null,"expertise_years":null,"hourly_rate":null,"portfolio_image_1":null,"portfolio_image_2":null,"portfolio_image_3":null,"portfolio_image_4":null,"latitude":"30.6989037","longitude":"76.6917119","id":415,"roles":[{"name":"athlete"}],"portfolio_image":"[]"},"booking_details":[{"id":49,"booking_id":143,"booking_date":"2019-12-23","from_time":"09:00:00","to_time":"11:00:00"}],"space":{"id":36,"name":"omg01","images_1":"1576854113368.jpg","images_2":null,"images_3":null,"images_4":null,"images_5":null,"description":"sdfhjkk","price_hourly":12,"availability_week":["1","2","3","4","5","6","7"],"location":"Unnamed Road, Sector 91, Sahibzada Ajit Singh Nagar, Punjab 140307, India","latitude":"30.697834443640826","longitude":"76.68409790843725","created_by":451,"price_daily":240,"images":"[\"1576854113368.jpg\"]"}}
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
         * id : 143
         * type : space
         * target_id : 36
         * user_id : 415
         * owner_id : 451
         * tickets : 1
         * price : 24
         * status : pending
         * payment_details : {"id":"ch_1FrnE8BULVolCXCQYXXhqqGX","object":"charge","amount":2400,"amount_refunded":0,"application":null,"application_fee":null,"application_fee_amount":null,"balance_transaction":"txn_1FrnE9BULVolCXCQmSZbnio9","billing_details":{"address":{"city":null,"country":null,"line1":null,"line2":null,"postal_code":null,"state":null},"email":null,"name":null,"phone":null},"captured":true,"created":1576855036,"currency":"usd","customer":null,"description":"Charge for the booking booked through utrain app","destination":null,"dispute":null,"disputed":false,"failure_code":null,"failure_message":null,"fraud_details":[],"invoice":null,"livemode":false,"metadata":[],"on_behalf_of":null,"order":null,"outcome":{"network_status":"approved_by_network","reason":null,"risk_level":"normal","risk_score":50,"seller_message":"Payment complete.","type":"authorized"},"paid":true,"payment_intent":null,"payment_method":"card_1FrnE7BULVolCXCQFdFlBrKC","payment_method_details":{"card":{"brand":"visa","checks":{"address_line1_check":null,"address_postal_code_check":null,"cvc_check":"pass"},"country":"US","exp_month":11,"exp_year":2025,"fingerprint":"40ymjgQlhCRMugBF","funding":"unknown","installments":null,"last4":"1111","network":"visa","three_d_secure":null,"wallet":null},"type":"card"},"receipt_email":null,"receipt_number":null,"receipt_url":"https:\/\/pay.stripe.com\/receipts\/acct_1FEwmlBULVolCXCQ\/ch_1FrnE8BULVolCXCQYXXhqqGX\/rcpt_GOaOMniDBcc2mFKTa5Xf6RpG1b3uvQK","refunded":false,"refunds":{"object":"list","data":[],"has_more":false,"total_count":0,"url":"\/v1\/charges\/ch_1FrnE8BULVolCXCQYXXhqqGX\/refunds"},"review":null,"shipping":null,"source":{"id":"card_1FrnE7BULVolCXCQFdFlBrKC","object":"card","address_city":null,"address_country":null,"address_line1":null,"address_line1_check":null,"address_line2":null,"address_state":null,"address_zip":null,"address_zip_check":null,"brand":"Visa","country":"US","customer":null,"cvc_check":"pass","dynamic_last4":null,"exp_month":11,"exp_year":2025,"fingerprint":"40ymjgQlhCRMugBF","funding":"unknown","last4":"1111","metadata":[],"name":null,"tokenization_method":null},"source_transfer":null,"statement_descriptor":null,"statement_descriptor_suffix":null,"status":"succeeded","transfer_data":null,"transfer_group":null}
         * payment_id : ch_1FrnE8BULVolCXCQYXXhqqGX
         * rating : 0
         * params : null
         * state : 0
         * created_at : 2019-12-20 20:47:16
         * updated_at : 2019-12-20 20:47:17
         * deleted_at : null
         * target_data : {"id":36,"name":"omg01","images_1":"1576854113368.jpg","images_2":null,"images_3":null,"images_4":null,"images_5":null,"description":"sdfhjkk","price_hourly":12,"availability_week":["1","2","3","4","5","6","7"],"location":"Unnamed Road, Sector 91, Sahibzada Ajit Singh Nagar, Punjab 140307, India","latitude":"30.697834443640826","longitude":"76.68409790843725","created_by":451,"price_daily":240,"images":"[\"1576854113368.jpg\"]"}
         * booking_date : {"start":"2019-12-23","end":"2019-12-23"}
         * user_details : {"name":"testathlete","email":"test1@gmail.com","phone":"3333586950","address":"4th Floor E-302, Vista Tower, Industrial Area, Sector 75, Sahibzada Ajit Singh Nagar, Punjab 140308, India","profile_image":"1576849824109.jpg","location":null,"business_hour_starts":null,"business_hour_ends":null,"bio":null,"expertise_years":null,"hourly_rate":null,"portfolio_image_1":null,"portfolio_image_2":null,"portfolio_image_3":null,"portfolio_image_4":null,"latitude":"30.6989037","longitude":"76.6917119","id":415,"roles":[{"name":"athlete"}],"portfolio_image":"[]"}
         * booking_details : [{"id":49,"booking_id":143,"booking_date":"2019-12-23","from_time":"09:00:00","to_time":"11:00:00"}]
         * space : {"id":36,"name":"omg01","images_1":"1576854113368.jpg","images_2":null,"images_3":null,"images_4":null,"images_5":null,"description":"sdfhjkk","price_hourly":12,"availability_week":["1","2","3","4","5","6","7"],"location":"Unnamed Road, Sector 91, Sahibzada Ajit Singh Nagar, Punjab 140307, India","latitude":"30.697834443640826","longitude":"76.68409790843725","created_by":451,"price_daily":240,"images":"[\"1576854113368.jpg\"]"}
         */

        private int id;
        private String type;
        private int target_id;
        private int user_id;
        private int owner_id;
        private int tickets;
        private int price;
        private String status;
        private String payment_details;
        private String payment_id;
        private String rating;
        private Object params;
        private String state;
        private String created_at;
        private String updated_at;
        private Object deleted_at;
        private TargetDataBean target_data;
        private BookingDateBean booking_date;
        private UserDetailsBean user_details;
        private SpaceBean space;
        private List<BookingDetailsBean> booking_details;

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

        public int getOwner_id() {
            return owner_id;
        }

        public void setOwner_id(int owner_id) {
            this.owner_id = owner_id;
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPayment_details() {
            return payment_details;
        }

        public void setPayment_details(String payment_details) {
            this.payment_details = payment_details;
        }

        public String getPayment_id() {
            return payment_id;
        }

        public void setPayment_id(String payment_id) {
            this.payment_id = payment_id;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
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

        public List<BookingDetailsBean> getBooking_details() {
            return booking_details;
        }

        public void setBooking_details(List<BookingDetailsBean> booking_details) {
            this.booking_details = booking_details;
        }

        public static class TargetDataBean {
            /**
             * id : 36
             * name : omg01
             * images_1 : 1576854113368.jpg
             * images_2 : null
             * images_3 : null
             * images_4 : null
             * images_5 : null
             * description : sdfhjkk
             * price_hourly : 12
             * availability_week : ["1","2","3","4","5","6","7"]
             * location : Unnamed Road, Sector 91, Sahibzada Ajit Singh Nagar, Punjab 140307, India
             * latitude : 30.697834443640826
             * longitude : 76.68409790843725
             * created_by : 451
             * price_daily : 240
             * images : ["1576854113368.jpg"]
             */

            private int id;
            private String name;
            private String images_1;
            private Object images_2;
            private Object images_3;
            private Object images_4;
            private Object images_5;
            private String description;
            private int price_hourly;
            private String location;
            private String latitude;
            private String longitude;
            private int created_by;
            private int price_daily;
            private String images;
            private List<String> availability_week;

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

            public String getImages_1() {
                return images_1;
            }

            public void setImages_1(String images_1) {
                this.images_1 = images_1;
            }

            public Object getImages_2() {
                return images_2;
            }

            public void setImages_2(Object images_2) {
                this.images_2 = images_2;
            }

            public Object getImages_3() {
                return images_3;
            }

            public void setImages_3(Object images_3) {
                this.images_3 = images_3;
            }

            public Object getImages_4() {
                return images_4;
            }

            public void setImages_4(Object images_4) {
                this.images_4 = images_4;
            }

            public Object getImages_5() {
                return images_5;
            }

            public void setImages_5(Object images_5) {
                this.images_5 = images_5;
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

            public String getImages() {
                return images;
            }

            public void setImages(String images) {
                this.images = images;
            }

            public List<String> getAvailability_week() {
                return availability_week;
            }

            public void setAvailability_week(List<String> availability_week) {
                this.availability_week = availability_week;
            }
        }

        public static class BookingDateBean {
            /**
             * start : 2019-12-23
             * end : 2019-12-23
             */

            private String start;
            private String end;

            public String getStart() {
                return start;
            }

            public void setStart(String start) {
                this.start = start;
            }

            public String getEnd() {
                return end;
            }

            public void setEnd(String end) {
                this.end = end;
            }
        }

        public static class UserDetailsBean {
            /**
             * name : testathlete
             * email : test1@gmail.com
             * phone : 3333586950
             * address : 4th Floor E-302, Vista Tower, Industrial Area, Sector 75, Sahibzada Ajit Singh Nagar, Punjab 140308, India
             * profile_image : 1576849824109.jpg
             * location : null
             * business_hour_starts : null
             * business_hour_ends : null
             * bio : null
             * expertise_years : null
             * hourly_rate : null
             * portfolio_image_1 : null
             * portfolio_image_2 : null
             * portfolio_image_3 : null
             * portfolio_image_4 : null
             * latitude : 30.6989037
             * longitude : 76.6917119
             * id : 415
             * roles : [{"name":"athlete"}]
             * portfolio_image : []
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
            private Object portfolio_image_1;
            private Object portfolio_image_2;
            private Object portfolio_image_3;
            private Object portfolio_image_4;
            private String latitude;
            private String longitude;
            private int id;
            private String portfolio_image;
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

            public Object getPortfolio_image_1() {
                return portfolio_image_1;
            }

            public void setPortfolio_image_1(Object portfolio_image_1) {
                this.portfolio_image_1 = portfolio_image_1;
            }

            public Object getPortfolio_image_2() {
                return portfolio_image_2;
            }

            public void setPortfolio_image_2(Object portfolio_image_2) {
                this.portfolio_image_2 = portfolio_image_2;
            }

            public Object getPortfolio_image_3() {
                return portfolio_image_3;
            }

            public void setPortfolio_image_3(Object portfolio_image_3) {
                this.portfolio_image_3 = portfolio_image_3;
            }

            public Object getPortfolio_image_4() {
                return portfolio_image_4;
            }

            public void setPortfolio_image_4(Object portfolio_image_4) {
                this.portfolio_image_4 = portfolio_image_4;
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

            public String getPortfolio_image() {
                return portfolio_image;
            }

            public void setPortfolio_image(String portfolio_image) {
                this.portfolio_image = portfolio_image;
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
             * id : 36
             * name : omg01
             * images_1 : 1576854113368.jpg
             * images_2 : null
             * images_3 : null
             * images_4 : null
             * images_5 : null
             * description : sdfhjkk
             * price_hourly : 12
             * availability_week : ["1","2","3","4","5","6","7"]
             * location : Unnamed Road, Sector 91, Sahibzada Ajit Singh Nagar, Punjab 140307, India
             * latitude : 30.697834443640826
             * longitude : 76.68409790843725
             * created_by : 451
             * price_daily : 240
             * images : ["1576854113368.jpg"]
             */

            private int id;
            private String name;
            private String images_1;
            private Object images_2;
            private Object images_3;
            private Object images_4;
            private Object images_5;
            private String description;
            private int price_hourly;
            private String location;
            private String latitude;
            private String longitude;
            private int created_by;
            private int price_daily;
            private String images;
            private List<String> availability_week;

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

            public String getImages_1() {
                return images_1;
            }

            public void setImages_1(String images_1) {
                this.images_1 = images_1;
            }

            public Object getImages_2() {
                return images_2;
            }

            public void setImages_2(Object images_2) {
                this.images_2 = images_2;
            }

            public Object getImages_3() {
                return images_3;
            }

            public void setImages_3(Object images_3) {
                this.images_3 = images_3;
            }

            public Object getImages_4() {
                return images_4;
            }

            public void setImages_4(Object images_4) {
                this.images_4 = images_4;
            }

            public Object getImages_5() {
                return images_5;
            }

            public void setImages_5(Object images_5) {
                this.images_5 = images_5;
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

            public String getImages() {
                return images;
            }

            public void setImages(String images) {
                this.images = images;
            }

            public List<String> getAvailability_week() {
                return availability_week;
            }

            public void setAvailability_week(List<String> availability_week) {
                this.availability_week = availability_week;
            }
        }

        public static class BookingDetailsBean {
            /**
             * id : 49
             * booking_id : 143
             * booking_date : 2019-12-23
             * from_time : 09:00:00
             * to_time : 11:00:00
             */

            private int id;
            private int booking_id;
            private String booking_date;
            private String from_time;
            private String to_time;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getBooking_id() {
                return booking_id;
            }

            public void setBooking_id(int booking_id) {
                this.booking_id = booking_id;
            }

            public String getBooking_date() {
                return booking_date;
            }

            public void setBooking_date(String booking_date) {
                this.booking_date = booking_date;
            }

            public String getFrom_time() {
                return from_time;
            }

            public void setFrom_time(String from_time) {
                this.from_time = from_time;
            }

            public String getTo_time() {
                return to_time;
            }

            public void setTo_time(String to_time) {
                this.to_time = to_time;
            }
        }
    }
}
