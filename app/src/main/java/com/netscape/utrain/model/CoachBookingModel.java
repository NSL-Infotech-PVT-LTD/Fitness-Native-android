package com.netscape.utrain.model;

import java.util.List;

public class CoachBookingModel {


    /**
     * status : true
     * code : 201
     * data : {"message":"Booked Successfully","booking":{"coach_id":"6","service_id":["1","2","3"],"price":"200","athlete_id":2,"updated_at":"2020-02-25 13:20:28","created_at":"2020-02-25 13:20:28","id":17,"payment_details":"{\"id\":\"ch_1GFyBUBULVolCXCQOKZ3951l\",\"object\":\"charge\",\"amount\":20000,\"amount_refunded\":0,\"application\":null,\"application_fee\":null,\"application_fee_amount\":null,\"balance_transaction\":\"txn_1GFyBUBULVolCXCQwhuNelj5\",\"billing_details\":{\"address\":{\"city\":null,\"country\":null,\"line1\":null,\"line2\":null,\"postal_code\":\"42424\",\"state\":null},\"email\":null,\"name\":null,\"phone\":null},\"captured\":true,\"created\":1582617028,\"currency\":\"usd\",\"customer\":null,\"description\":\"Charge for the booking booked through utrain app\",\"destination\":null,\"dispute\":null,\"disputed\":false,\"failure_code\":null,\"failure_message\":null,\"fraud_details\":[],\"invoice\":null,\"livemode\":false,\"metadata\":[],\"on_behalf_of\":null,\"order\":null,\"outcome\":{\"network_status\":\"approved_by_network\",\"reason\":null,\"risk_level\":\"normal\",\"risk_score\":48,\"seller_message\":\"Payment complete.\",\"type\":\"authorized\"},\"paid\":true,\"payment_intent\":null,\"payment_method\":\"card_1GFy66BULVolCXCQuxVqFBqv\",\"payment_method_details\":{\"card\":{\"brand\":\"visa\",\"checks\":{\"address_line1_check\":null,\"address_postal_code_check\":\"pass\",\"cvc_check\":\"pass\"},\"country\":\"US\",\"exp_month\":4,\"exp_year\":2024,\"fingerprint\":\"83hlIrCR9y2sfmjQ\",\"funding\":\"credit\",\"installments\":null,\"last4\":\"4242\",\"network\":\"visa\",\"three_d_secure\":null,\"wallet\":null},\"type\":\"card\"},\"receipt_email\":null,\"receipt_number\":null,\"receipt_url\":\"https:\\/\\/pay.stripe.com\\/receipts\\/acct_1FEwmlBULVolCXCQ\\/ch_1GFyBUBULVolCXCQOKZ3951l\\/rcpt_GnZKJkwT9kUwdpf4aLgr4rEv6gsHSOO\",\"refunded\":false,\"refunds\":{\"object\":\"list\",\"data\":[],\"has_more\":false,\"total_count\":0,\"url\":\"\\/v1\\/charges\\/ch_1GFyBUBULVolCXCQOKZ3951l\\/refunds\"},\"review\":null,\"shipping\":null,\"source\":{\"id\":\"card_1GFy66BULVolCXCQuxVqFBqv\",\"object\":\"card\",\"address_city\":null,\"address_country\":null,\"address_line1\":null,\"address_line1_check\":null,\"address_line2\":null,\"address_state\":null,\"address_zip\":\"42424\",\"address_zip_check\":\"pass\",\"brand\":\"Visa\",\"country\":\"US\",\"customer\":null,\"cvc_check\":\"pass\",\"dynamic_last4\":null,\"exp_month\":4,\"exp_year\":2024,\"fingerprint\":\"83hlIrCR9y2sfmjQ\",\"funding\":\"credit\",\"last4\":\"4242\",\"metadata\":[],\"name\":null,\"tokenization_method\":null},\"source_transfer\":null,\"statement_descriptor\":null,\"statement_descriptor_suffix\":null,\"status\":\"succeeded\",\"transfer_data\":null,\"transfer_group\":null}","payment_id":"ch_1GFyBUBULVolCXCQOKZ3951l"}}
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
         * message : Booked Successfully
         * booking : {"coach_id":"6","service_id":["1","2","3"],"price":"200","athlete_id":2,"updated_at":"2020-02-25 13:20:28","created_at":"2020-02-25 13:20:28","id":17,"payment_details":"{\"id\":\"ch_1GFyBUBULVolCXCQOKZ3951l\",\"object\":\"charge\",\"amount\":20000,\"amount_refunded\":0,\"application\":null,\"application_fee\":null,\"application_fee_amount\":null,\"balance_transaction\":\"txn_1GFyBUBULVolCXCQwhuNelj5\",\"billing_details\":{\"address\":{\"city\":null,\"country\":null,\"line1\":null,\"line2\":null,\"postal_code\":\"42424\",\"state\":null},\"email\":null,\"name\":null,\"phone\":null},\"captured\":true,\"created\":1582617028,\"currency\":\"usd\",\"customer\":null,\"description\":\"Charge for the booking booked through utrain app\",\"destination\":null,\"dispute\":null,\"disputed\":false,\"failure_code\":null,\"failure_message\":null,\"fraud_details\":[],\"invoice\":null,\"livemode\":false,\"metadata\":[],\"on_behalf_of\":null,\"order\":null,\"outcome\":{\"network_status\":\"approved_by_network\",\"reason\":null,\"risk_level\":\"normal\",\"risk_score\":48,\"seller_message\":\"Payment complete.\",\"type\":\"authorized\"},\"paid\":true,\"payment_intent\":null,\"payment_method\":\"card_1GFy66BULVolCXCQuxVqFBqv\",\"payment_method_details\":{\"card\":{\"brand\":\"visa\",\"checks\":{\"address_line1_check\":null,\"address_postal_code_check\":\"pass\",\"cvc_check\":\"pass\"},\"country\":\"US\",\"exp_month\":4,\"exp_year\":2024,\"fingerprint\":\"83hlIrCR9y2sfmjQ\",\"funding\":\"credit\",\"installments\":null,\"last4\":\"4242\",\"network\":\"visa\",\"three_d_secure\":null,\"wallet\":null},\"type\":\"card\"},\"receipt_email\":null,\"receipt_number\":null,\"receipt_url\":\"https:\\/\\/pay.stripe.com\\/receipts\\/acct_1FEwmlBULVolCXCQ\\/ch_1GFyBUBULVolCXCQOKZ3951l\\/rcpt_GnZKJkwT9kUwdpf4aLgr4rEv6gsHSOO\",\"refunded\":false,\"refunds\":{\"object\":\"list\",\"data\":[],\"has_more\":false,\"total_count\":0,\"url\":\"\\/v1\\/charges\\/ch_1GFyBUBULVolCXCQOKZ3951l\\/refunds\"},\"review\":null,\"shipping\":null,\"source\":{\"id\":\"card_1GFy66BULVolCXCQuxVqFBqv\",\"object\":\"card\",\"address_city\":null,\"address_country\":null,\"address_line1\":null,\"address_line1_check\":null,\"address_line2\":null,\"address_state\":null,\"address_zip\":\"42424\",\"address_zip_check\":\"pass\",\"brand\":\"Visa\",\"country\":\"US\",\"customer\":null,\"cvc_check\":\"pass\",\"dynamic_last4\":null,\"exp_month\":4,\"exp_year\":2024,\"fingerprint\":\"83hlIrCR9y2sfmjQ\",\"funding\":\"credit\",\"last4\":\"4242\",\"metadata\":[],\"name\":null,\"tokenization_method\":null},\"source_transfer\":null,\"statement_descriptor\":null,\"statement_descriptor_suffix\":null,\"status\":\"succeeded\",\"transfer_data\":null,\"transfer_group\":null}","payment_id":"ch_1GFyBUBULVolCXCQOKZ3951l"}
         */

        private String message;
        private BookingBean booking;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public BookingBean getBooking() {
            return booking;
        }

        public void setBooking(BookingBean booking) {
            this.booking = booking;
        }

        public static class BookingBean {
            /**
             * coach_id : 6
             * service_id : ["1","2","3"]
             * price : 200
             * athlete_id : 2
             * updated_at : 2020-02-25 13:20:28
             * created_at : 2020-02-25 13:20:28
             * id : 17
             * payment_details : {"id":"ch_1GFyBUBULVolCXCQOKZ3951l","object":"charge","amount":20000,"amount_refunded":0,"application":null,"application_fee":null,"application_fee_amount":null,"balance_transaction":"txn_1GFyBUBULVolCXCQwhuNelj5","billing_details":{"address":{"city":null,"country":null,"line1":null,"line2":null,"postal_code":"42424","state":null},"email":null,"name":null,"phone":null},"captured":true,"created":1582617028,"currency":"usd","customer":null,"description":"Charge for the booking booked through utrain app","destination":null,"dispute":null,"disputed":false,"failure_code":null,"failure_message":null,"fraud_details":[],"invoice":null,"livemode":false,"metadata":[],"on_behalf_of":null,"order":null,"outcome":{"network_status":"approved_by_network","reason":null,"risk_level":"normal","risk_score":48,"seller_message":"Payment complete.","type":"authorized"},"paid":true,"payment_intent":null,"payment_method":"card_1GFy66BULVolCXCQuxVqFBqv","payment_method_details":{"card":{"brand":"visa","checks":{"address_line1_check":null,"address_postal_code_check":"pass","cvc_check":"pass"},"country":"US","exp_month":4,"exp_year":2024,"fingerprint":"83hlIrCR9y2sfmjQ","funding":"credit","installments":null,"last4":"4242","network":"visa","three_d_secure":null,"wallet":null},"type":"card"},"receipt_email":null,"receipt_number":null,"receipt_url":"https:\/\/pay.stripe.com\/receipts\/acct_1FEwmlBULVolCXCQ\/ch_1GFyBUBULVolCXCQOKZ3951l\/rcpt_GnZKJkwT9kUwdpf4aLgr4rEv6gsHSOO","refunded":false,"refunds":{"object":"list","data":[],"has_more":false,"total_count":0,"url":"\/v1\/charges\/ch_1GFyBUBULVolCXCQOKZ3951l\/refunds"},"review":null,"shipping":null,"source":{"id":"card_1GFy66BULVolCXCQuxVqFBqv","object":"card","address_city":null,"address_country":null,"address_line1":null,"address_line1_check":null,"address_line2":null,"address_state":null,"address_zip":"42424","address_zip_check":"pass","brand":"Visa","country":"US","customer":null,"cvc_check":"pass","dynamic_last4":null,"exp_month":4,"exp_year":2024,"fingerprint":"83hlIrCR9y2sfmjQ","funding":"credit","last4":"4242","metadata":[],"name":null,"tokenization_method":null},"source_transfer":null,"statement_descriptor":null,"statement_descriptor_suffix":null,"status":"succeeded","transfer_data":null,"transfer_group":null}
             * payment_id : ch_1GFyBUBULVolCXCQOKZ3951l
             */

            private String coach_id;
            private String price;
            private int athlete_id;
            private String updated_at;
            private String created_at;
            private int id;
            private String payment_details;
            private String payment_id;
            private List<String> service_id;

            public String getCoach_id() {
                return coach_id;
            }

            public void setCoach_id(String coach_id) {
                this.coach_id = coach_id;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public int getAthlete_id() {
                return athlete_id;
            }

            public void setAthlete_id(int athlete_id) {
                this.athlete_id = athlete_id;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(String updated_at) {
                this.updated_at = updated_at;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
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

            public List<String> getService_id() {
                return service_id;
            }

            public void setService_id(List<String> service_id) {
                this.service_id = service_id;
            }
        }
    }
}
