package com.netscape.utrain.response;

import java.util.List;

public class EventDetailResponse {

    /**
     * status : true
     * code : 200
     * data : {"id":133,"type":"event","target_id":47,"user_id":415,"owner_id":450,"tickets":3,"price":24,"status":"pending","payment_details":"{\"id\":\"ch_1FrkbRBULVolCXCQWcxXxbER\",\"object\":\"charge\",\"amount\":2400,\"amount_refunded\":0,\"application\":null,\"application_fee\":null,\"application_fee_amount\":null,\"balance_transaction\":\"txn_1FrkbRBULVolCXCQ9WjgBZTD\",\"billing_details\":{\"address\":{\"city\":null,\"country\":null,\"line1\":null,\"line2\":null,\"postal_code\":null,\"state\":null},\"email\":null,\"name\":null,\"phone\":null},\"captured\":true,\"created\":1576844949,\"currency\":\"usd\",\"customer\":null,\"description\":\"Charge for the booking booked through utrain app\",\"destination\":null,\"dispute\":null,\"disputed\":false,\"failure_code\":null,\"failure_message\":null,\"fraud_details\":[],\"invoice\":null,\"livemode\":false,\"metadata\":[],\"on_behalf_of\":null,\"order\":null,\"outcome\":{\"network_status\":\"approved_by_network\",\"reason\":null,\"risk_level\":\"normal\",\"risk_score\":30,\"seller_message\":\"Payment complete.\",\"type\":\"authorized\"},\"paid\":true,\"payment_intent\":null,\"payment_method\":\"card_1FrkbPBULVolCXCQKkGiXZMn\",\"payment_method_details\":{\"card\":{\"brand\":\"visa\",\"checks\":{\"address_line1_check\":null,\"address_postal_code_check\":null,\"cvc_check\":\"pass\"},\"country\":\"US\",\"exp_month\":11,\"exp_year\":2020,\"fingerprint\":\"40ymjgQlhCRMugBF\",\"funding\":\"unknown\",\"installments\":null,\"last4\":\"1111\",\"network\":\"visa\",\"three_d_secure\":null,\"wallet\":null},\"type\":\"card\"},\"receipt_email\":null,\"receipt_number\":null,\"receipt_url\":\"https:\\/\\/pay.stripe.com\\/receipts\\/acct_1FEwmlBULVolCXCQ\\/ch_1FrkbRBULVolCXCQWcxXxbER\\/rcpt_GOXgE6KVBbK87fJXZi6Ihdas5tPbgLS\",\"refunded\":false,\"refunds\":{\"object\":\"list\",\"data\":[],\"has_more\":false,\"total_count\":0,\"url\":\"\\/v1\\/charges\\/ch_1FrkbRBULVolCXCQWcxXxbER\\/refunds\"},\"review\":null,\"shipping\":null,\"source\":{\"id\":\"card_1FrkbPBULVolCXCQKkGiXZMn\",\"object\":\"card\",\"address_city\":null,\"address_country\":null,\"address_line1\":null,\"address_line1_check\":null,\"address_line2\":null,\"address_state\":null,\"address_zip\":null,\"address_zip_check\":null,\"brand\":\"Visa\",\"country\":\"US\",\"customer\":null,\"cvc_check\":\"pass\",\"dynamic_last4\":null,\"exp_month\":11,\"exp_year\":2020,\"fingerprint\":\"40ymjgQlhCRMugBF\",\"funding\":\"unknown\",\"last4\":\"1111\",\"metadata\":[],\"name\":null,\"tokenization_method\":null},\"source_transfer\":null,\"statement_descriptor\":null,\"statement_descriptor_suffix\":null,\"status\":\"succeeded\",\"transfer_data\":null,\"transfer_group\":null}","payment_id":"ch_1FrkbRBULVolCXCQWcxXxbER","rating":"0","params":null,"state":"0","created_at":"2019-12-20 17:59:09","updated_at":"2019-12-20 17:59:10","deleted_at":null,"target_data":{"id":47,"name":"99event","description":"ahahhajjk","start_date":"2019-12-26","end_date":"2019-12-31","start_time":"08:00:00","end_time":"12:00:00","price":8,"images_1":"1576844146212.jpg","images_2":null,"images_3":null,"images_4":null,"images_5":null,"location":"flat no. 901 , Block A4 Purab Premium Apartments, Sector 85, Sahibzada Ajit Singh Nagar, Punjab 140307, India","latitude":"30.683431045270186","longitude":"76.69694803655148","created_by":450,"guest_allowed":20,"guest_allowed_left":10,"equipment_required":"no","images":"[\"1576844146212.jpg\"]"},"booking_date":{"start":"2019-12-26","end":"2019-12-31"},"user_details":{"name":"testathlete","email":"test1@gmail.com","phone":"3333586950","address":"4th Floor E-302, Vista Tower, Industrial Area, Sector 75, Sahibzada Ajit Singh Nagar, Punjab 140308, India","profile_image":"1576849824109.jpg","location":null,"business_hour_starts":null,"business_hour_ends":null,"bio":null,"expertise_years":null,"hourly_rate":null,"portfolio_image_1":null,"portfolio_image_2":null,"portfolio_image_3":null,"portfolio_image_4":null,"latitude":"30.6989037","longitude":"76.6917119","id":415,"roles":[{"name":"athlete"}],"portfolio_image":"[]"},"booking_details":[],"event":{"id":47,"name":"99event","description":"ahahhajjk","start_date":"2019-12-26","end_date":"2019-12-31","start_time":"08:00:00","end_time":"12:00:00","price":8,"images_1":"1576844146212.jpg","images_2":null,"images_3":null,"images_4":null,"images_5":null,"location":"flat no. 901 , Block A4 Purab Premium Apartments, Sector 85, Sahibzada Ajit Singh Nagar, Punjab 140307, India","latitude":"30.683431045270186","longitude":"76.69694803655148","created_by":450,"guest_allowed":20,"guest_allowed_left":10,"equipment_required":"no","images":"[\"1576844146212.jpg\"]"}}
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
         * id : 133
         * type : event
         * target_id : 47
         * user_id : 415
         * owner_id : 450
         * tickets : 3
         * price : 24
         * status : pending
         * payment_details : {"id":"ch_1FrkbRBULVolCXCQWcxXxbER","object":"charge","amount":2400,"amount_refunded":0,"application":null,"application_fee":null,"application_fee_amount":null,"balance_transaction":"txn_1FrkbRBULVolCXCQ9WjgBZTD","billing_details":{"address":{"city":null,"country":null,"line1":null,"line2":null,"postal_code":null,"state":null},"email":null,"name":null,"phone":null},"captured":true,"created":1576844949,"currency":"usd","customer":null,"description":"Charge for the booking booked through utrain app","destination":null,"dispute":null,"disputed":false,"failure_code":null,"failure_message":null,"fraud_details":[],"invoice":null,"livemode":false,"metadata":[],"on_behalf_of":null,"order":null,"outcome":{"network_status":"approved_by_network","reason":null,"risk_level":"normal","risk_score":30,"seller_message":"Payment complete.","type":"authorized"},"paid":true,"payment_intent":null,"payment_method":"card_1FrkbPBULVolCXCQKkGiXZMn","payment_method_details":{"card":{"brand":"visa","checks":{"address_line1_check":null,"address_postal_code_check":null,"cvc_check":"pass"},"country":"US","exp_month":11,"exp_year":2020,"fingerprint":"40ymjgQlhCRMugBF","funding":"unknown","installments":null,"last4":"1111","network":"visa","three_d_secure":null,"wallet":null},"type":"card"},"receipt_email":null,"receipt_number":null,"receipt_url":"https:\/\/pay.stripe.com\/receipts\/acct_1FEwmlBULVolCXCQ\/ch_1FrkbRBULVolCXCQWcxXxbER\/rcpt_GOXgE6KVBbK87fJXZi6Ihdas5tPbgLS","refunded":false,"refunds":{"object":"list","data":[],"has_more":false,"total_count":0,"url":"\/v1\/charges\/ch_1FrkbRBULVolCXCQWcxXxbER\/refunds"},"review":null,"shipping":null,"source":{"id":"card_1FrkbPBULVolCXCQKkGiXZMn","object":"card","address_city":null,"address_country":null,"address_line1":null,"address_line1_check":null,"address_line2":null,"address_state":null,"address_zip":null,"address_zip_check":null,"brand":"Visa","country":"US","customer":null,"cvc_check":"pass","dynamic_last4":null,"exp_month":11,"exp_year":2020,"fingerprint":"40ymjgQlhCRMugBF","funding":"unknown","last4":"1111","metadata":[],"name":null,"tokenization_method":null},"source_transfer":null,"statement_descriptor":null,"statement_descriptor_suffix":null,"status":"succeeded","transfer_data":null,"transfer_group":null}
         * payment_id : ch_1FrkbRBULVolCXCQWcxXxbER
         * rating : 0
         * params : null
         * state : 0
         * created_at : 2019-12-20 17:59:09
         * updated_at : 2019-12-20 17:59:10
         * deleted_at : null
         * target_data : {"id":47,"name":"99event","description":"ahahhajjk","start_date":"2019-12-26","end_date":"2019-12-31","start_time":"08:00:00","end_time":"12:00:00","price":8,"images_1":"1576844146212.jpg","images_2":null,"images_3":null,"images_4":null,"images_5":null,"location":"flat no. 901 , Block A4 Purab Premium Apartments, Sector 85, Sahibzada Ajit Singh Nagar, Punjab 140307, India","latitude":"30.683431045270186","longitude":"76.69694803655148","created_by":450,"guest_allowed":20,"guest_allowed_left":10,"equipment_required":"no","images":"[\"1576844146212.jpg\"]"}
         * booking_date : {"start":"2019-12-26","end":"2019-12-31"}
         * user_details : {"name":"testathlete","email":"test1@gmail.com","phone":"3333586950","address":"4th Floor E-302, Vista Tower, Industrial Area, Sector 75, Sahibzada Ajit Singh Nagar, Punjab 140308, India","profile_image":"1576849824109.jpg","location":null,"business_hour_starts":null,"business_hour_ends":null,"bio":null,"expertise_years":null,"hourly_rate":null,"portfolio_image_1":null,"portfolio_image_2":null,"portfolio_image_3":null,"portfolio_image_4":null,"latitude":"30.6989037","longitude":"76.6917119","id":415,"roles":[{"name":"athlete"}],"portfolio_image":"[]"}
         * booking_details : []
         * event : {"id":47,"name":"99event","description":"ahahhajjk","start_date":"2019-12-26","end_date":"2019-12-31","start_time":"08:00:00","end_time":"12:00:00","price":8,"images_1":"1576844146212.jpg","images_2":null,"images_3":null,"images_4":null,"images_5":null,"location":"flat no. 901 , Block A4 Purab Premium Apartments, Sector 85, Sahibzada Ajit Singh Nagar, Punjab 140307, India","latitude":"30.683431045270186","longitude":"76.69694803655148","created_by":450,"guest_allowed":20,"guest_allowed_left":10,"equipment_required":"no","images":"[\"1576844146212.jpg\"]"}
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
        private EventBean event;
        private List<?> booking_details;

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

        public EventBean getEvent() {
            return event;
        }

        public void setEvent(EventBean event) {
            this.event = event;
        }

        public List<?> getBooking_details() {
            return booking_details;
        }

        public void setBooking_details(List<?> booking_details) {
            this.booking_details = booking_details;
        }

        public static class TargetDataBean {
            /**
             * id : 47
             * name : 99event
             * description : ahahhajjk
             * start_date : 2019-12-26
             * end_date : 2019-12-31
             * start_time : 08:00:00
             * end_time : 12:00:00
             * price : 8
             * images_1 : 1576844146212.jpg
             * images_2 : null
             * images_3 : null
             * images_4 : null
             * images_5 : null
             * location : flat no. 901 , Block A4 Purab Premium Apartments, Sector 85, Sahibzada Ajit Singh Nagar, Punjab 140307, India
             * latitude : 30.683431045270186
             * longitude : 76.69694803655148
             * created_by : 450
             * guest_allowed : 20
             * guest_allowed_left : 10
             * equipment_required : no
             * images : ["1576844146212.jpg"]
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
            private Object images_2;
            private Object images_3;
            private Object images_4;
            private Object images_5;
            private String location;
            private String latitude;
            private String longitude;
            private int created_by;
            private int guest_allowed;
            private int guest_allowed_left;
            private String equipment_required;
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

            public String getImages() {
                return images;
            }

            public void setImages(String images) {
                this.images = images;
            }
        }

        public static class BookingDateBean {
            /**
             * start : 2019-12-26
             * end : 2019-12-31
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

        public static class EventBean {
            /**
             * id : 47
             * name : 99event
             * description : ahahhajjk
             * start_date : 2019-12-26
             * end_date : 2019-12-31
             * start_time : 08:00:00
             * end_time : 12:00:00
             * price : 8
             * images_1 : 1576844146212.jpg
             * images_2 : null
             * images_3 : null
             * images_4 : null
             * images_5 : null
             * location : flat no. 901 , Block A4 Purab Premium Apartments, Sector 85, Sahibzada Ajit Singh Nagar, Punjab 140307, India
             * latitude : 30.683431045270186
             * longitude : 76.69694803655148
             * created_by : 450
             * guest_allowed : 20
             * guest_allowed_left : 10
             * equipment_required : no
             * images : ["1576844146212.jpg"]
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
            private Object images_2;
            private Object images_3;
            private Object images_4;
            private Object images_5;
            private String location;
            private String latitude;
            private String longitude;
            private int created_by;
            private int guest_allowed;
            private int guest_allowed_left;
            private String equipment_required;
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

            public String getImages() {
                return images;
            }

            public void setImages(String images) {
                this.images = images;
            }
        }
    }
}
