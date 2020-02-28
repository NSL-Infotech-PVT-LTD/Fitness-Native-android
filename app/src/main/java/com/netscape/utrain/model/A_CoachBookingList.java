package com.netscape.utrain.model;

import java.util.List;

public class A_CoachBookingList {


        /**
     * status : true
     * code : 200
     * data : [{"id":23,"coach_id":35,"athlete_id":38,"service_id":["1","2"],"price":160,"payment_details":"{\"id\":\"ch_1GHAyDBULVolCXCQBesezAbQ\",\"object\":\"charge\",\"amount\":16000,\"amount_refunded\":0,\"application\":null,\"application_fee\":null,\"application_fee_amount\":null,\"balance_transaction\":\"txn_1GHAyEBULVolCXCQ5UE10JE4\",\"billing_details\":{\"address\":{\"city\":null,\"country\":null,\"line1\":null,\"line2\":null,\"postal_code\":null,\"state\":null},\"email\":null,\"name\":null,\"phone\":null},\"captured\":true,\"created\":1582904505,\"currency\":\"usd\",\"customer\":null,\"description\":\"Charge for the booking booked through utrain app\",\"destination\":null,\"dispute\":null,\"disputed\":false,\"failure_code\":null,\"failure_message\":null,\"fraud_details\":[],\"invoice\":null,\"livemode\":false,\"metadata\":[],\"on_behalf_of\":null,\"order\":null,\"outcome\":{\"network_status\":\"approved_by_network\",\"reason\":null,\"risk_level\":\"normal\",\"risk_score\":2,\"seller_message\":\"Payment complete.\",\"type\":\"authorized\"},\"paid\":true,\"payment_intent\":null,\"payment_method\":\"card_1GHAyCBULVolCXCQc50PIKae\",\"payment_method_details\":{\"card\":{\"brand\":\"visa\",\"checks\":{\"address_line1_check\":null,\"address_postal_code_check\":null,\"cvc_check\":\"pass\"},\"country\":\"US\",\"exp_month\":12,\"exp_year\":2025,\"fingerprint\":\"83hlIrCR9y2sfmjQ\",\"funding\":\"credit\",\"installments\":null,\"last4\":\"4242\",\"network\":\"visa\",\"three_d_secure\":null,\"wallet\":null},\"type\":\"card\"},\"receipt_email\":null,\"receipt_number\":null,\"receipt_url\":\"https:\\/\\/pay.stripe.com\\/receipts\\/acct_1FEwmlBULVolCXCQ\\/ch_1GHAyDBULVolCXCQBesezAbQ\\/rcpt_GoobEAvikCTxM8plDzHae0LKUYtRJjX\",\"refunded\":false,\"refunds\":{\"object\":\"list\",\"data\":[],\"has_more\":false,\"total_count\":0,\"url\":\"\\/v1\\/charges\\/ch_1GHAyDBULVolCXCQBesezAbQ\\/refunds\"},\"review\":null,\"shipping\":null,\"source\":{\"id\":\"card_1GHAyCBULVolCXCQc50PIKae\",\"object\":\"card\",\"address_city\":null,\"address_country\":null,\"address_line1\":null,\"address_line1_check\":null,\"address_line2\":null,\"address_state\":null,\"address_zip\":null,\"address_zip_check\":null,\"brand\":\"Visa\",\"country\":\"US\",\"customer\":null,\"cvc_check\":\"pass\",\"dynamic_last4\":null,\"exp_month\":12,\"exp_year\":2025,\"fingerprint\":\"83hlIrCR9y2sfmjQ\",\"funding\":\"credit\",\"last4\":\"4242\",\"metadata\":[],\"name\":null,\"tokenization_method\":null},\"source_transfer\":null,\"statement_descriptor\":null,\"statement_descriptor_suffix\":null,\"status\":\"succeeded\",\"transfer_data\":null,\"transfer_group\":null}","payment_id":"ch_1GHAyDBULVolCXCQBesezAbQ","coach_details":{"name":"Pardeep coach","email":"coach@coach.coach","phone":"1234554321","address":null,"profile_image":"1582268664405.jpg","location":"304, Industrial Area, Sector 75, Sahibzada Ajit Singh Nagar, Punjab 160075, India","business_hour_starts":"06:00:00","business_hour_ends":"23:00:00","bio":"about me","expertise_years":46,"hourly_rate":20,"portfolio_image_1":null,"portfolio_image_2":null,"portfolio_image_3":null,"portfolio_image_4":null,"latitude":"30.6992701","longitude":"76.6913907","id":35,"roles":[{"id":2,"name":"coach"}],"portfolio_image":"[]"}}]
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
         * id : 23
         * coach_id : 35
         * athlete_id : 38
         * service_id : ["1","2"]
         * price : 160
         * payment_details : {"id":"ch_1GHAyDBULVolCXCQBesezAbQ","object":"charge","amount":16000,"amount_refunded":0,"application":null,"application_fee":null,"application_fee_amount":null,"balance_transaction":"txn_1GHAyEBULVolCXCQ5UE10JE4","billing_details":{"address":{"city":null,"country":null,"line1":null,"line2":null,"postal_code":null,"state":null},"email":null,"name":null,"phone":null},"captured":true,"created":1582904505,"currency":"usd","customer":null,"description":"Charge for the booking booked through utrain app","destination":null,"dispute":null,"disputed":false,"failure_code":null,"failure_message":null,"fraud_details":[],"invoice":null,"livemode":false,"metadata":[],"on_behalf_of":null,"order":null,"outcome":{"network_status":"approved_by_network","reason":null,"risk_level":"normal","risk_score":2,"seller_message":"Payment complete.","type":"authorized"},"paid":true,"payment_intent":null,"payment_method":"card_1GHAyCBULVolCXCQc50PIKae","payment_method_details":{"card":{"brand":"visa","checks":{"address_line1_check":null,"address_postal_code_check":null,"cvc_check":"pass"},"country":"US","exp_month":12,"exp_year":2025,"fingerprint":"83hlIrCR9y2sfmjQ","funding":"credit","installments":null,"last4":"4242","network":"visa","three_d_secure":null,"wallet":null},"type":"card"},"receipt_email":null,"receipt_number":null,"receipt_url":"https:\/\/pay.stripe.com\/receipts\/acct_1FEwmlBULVolCXCQ\/ch_1GHAyDBULVolCXCQBesezAbQ\/rcpt_GoobEAvikCTxM8plDzHae0LKUYtRJjX","refunded":false,"refunds":{"object":"list","data":[],"has_more":false,"total_count":0,"url":"\/v1\/charges\/ch_1GHAyDBULVolCXCQBesezAbQ\/refunds"},"review":null,"shipping":null,"source":{"id":"card_1GHAyCBULVolCXCQc50PIKae","object":"card","address_city":null,"address_country":null,"address_line1":null,"address_line1_check":null,"address_line2":null,"address_state":null,"address_zip":null,"address_zip_check":null,"brand":"Visa","country":"US","customer":null,"cvc_check":"pass","dynamic_last4":null,"exp_month":12,"exp_year":2025,"fingerprint":"83hlIrCR9y2sfmjQ","funding":"credit","last4":"4242","metadata":[],"name":null,"tokenization_method":null},"source_transfer":null,"statement_descriptor":null,"statement_descriptor_suffix":null,"status":"succeeded","transfer_data":null,"transfer_group":null}
         * payment_id : ch_1GHAyDBULVolCXCQBesezAbQ
         * coach_details : {"name":"Pardeep coach","email":"coach@coach.coach","phone":"1234554321","address":null,"profile_image":"1582268664405.jpg","location":"304, Industrial Area, Sector 75, Sahibzada Ajit Singh Nagar, Punjab 160075, India","business_hour_starts":"06:00:00","business_hour_ends":"23:00:00","bio":"about me","expertise_years":46,"hourly_rate":20,"portfolio_image_1":null,"portfolio_image_2":null,"portfolio_image_3":null,"portfolio_image_4":null,"latitude":"30.6992701","longitude":"76.6913907","id":35,"roles":[{"id":2,"name":"coach"}],"portfolio_image":"[]"}
         */

        private int id;
        private int coach_id;
        private int athlete_id;
        private int price;
        private String payment_details;
        private String payment_id;
        private CoachDetailsBean coach_details;
        private List<String> service_id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCoach_id() {
            return coach_id;
        }

        public void setCoach_id(int coach_id) {
            this.coach_id = coach_id;
        }

        public int getAthlete_id() {
            return athlete_id;
        }

        public void setAthlete_id(int athlete_id) {
            this.athlete_id = athlete_id;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
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

        public CoachDetailsBean getCoach_details() {
            return coach_details;
        }

        public void setCoach_details(CoachDetailsBean coach_details) {
            this.coach_details = coach_details;
        }

        public List<String> getService_id() {
            return service_id;
        }

        public void setService_id(List<String> service_id) {
            this.service_id = service_id;
        }

        public static class CoachDetailsBean {
            /**
             * name : Pardeep coach
             * email : coach@coach.coach
             * phone : 1234554321
             * address : null
             * profile_image : 1582268664405.jpg
             * location : 304, Industrial Area, Sector 75, Sahibzada Ajit Singh Nagar, Punjab 160075, India
             * business_hour_starts : 06:00:00
             * business_hour_ends : 23:00:00
             * bio : about me
             * expertise_years : 46
             * hourly_rate : 20
             * portfolio_image_1 : null
             * portfolio_image_2 : null
             * portfolio_image_3 : null
             * portfolio_image_4 : null
             * latitude : 30.6992701
             * longitude : 76.6913907
             * id : 35
             * roles : [{"id":2,"name":"coach"}]
             * portfolio_image : []
             */

            private String name;
            private String email;
            private String phone;
            private Object address;
            private String profile_image;
            private String location;
            private String business_hour_starts;
            private String business_hour_ends;
            private String bio;
            private int expertise_years;
            private int hourly_rate;
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

            public Object getAddress() {
                return address;
            }

            public void setAddress(Object address) {
                this.address = address;
            }

            public String getProfile_image() {
                return profile_image;
            }

            public void setProfile_image(String profile_image) {
                this.profile_image = profile_image;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public String getBusiness_hour_starts() {
                return business_hour_starts;
            }

            public void setBusiness_hour_starts(String business_hour_starts) {
                this.business_hour_starts = business_hour_starts;
            }

            public String getBusiness_hour_ends() {
                return business_hour_ends;
            }

            public void setBusiness_hour_ends(String business_hour_ends) {
                this.business_hour_ends = business_hour_ends;
            }

            public String getBio() {
                return bio;
            }

            public void setBio(String bio) {
                this.bio = bio;
            }

            public int getExpertise_years() {
                return expertise_years;
            }

            public void setExpertise_years(int expertise_years) {
                this.expertise_years = expertise_years;
            }

            public int getHourly_rate() {
                return hourly_rate;
            }

            public void setHourly_rate(int hourly_rate) {
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
                 * id : 2
                 * name : coach
                 */

                private int id;
                private String name;

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
            }
        }
    }
}
