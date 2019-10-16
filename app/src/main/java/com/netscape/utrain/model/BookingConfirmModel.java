package com.netscape.utrain.model;

public class BookingConfirmModel {


    /**
     * status : true
     * code : 201
     * data : {"message":"Created Successfully","booking":{"type":"event","target_id":"7","tickets":"1","price":"200","user_id":97,"updated_at":"2019-10-16 13:18:16","created_at":"2019-10-16 13:18:16","id":2}}
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
         * message : Created Successfully
         * booking : {"type":"event","target_id":"7","tickets":"1","price":"200","user_id":97,"updated_at":"2019-10-16 13:18:16","created_at":"2019-10-16 13:18:16","id":2}
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
             * type : event
             * target_id : 7
             * tickets : 1
             * price : 200
             * user_id : 97
             * updated_at : 2019-10-16 13:18:16
             * created_at : 2019-10-16 13:18:16
             * id : 2
             */

            private String type;
            private String target_id;
            private String tickets;
            private String price;
            private int user_id;
            private String updated_at;
            private String created_at;
            private int id;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getTarget_id() {
                return target_id;
            }

            public void setTarget_id(String target_id) {
                this.target_id = target_id;
            }

            public String getTickets() {
                return tickets;
            }

            public void setTickets(String tickets) {
                this.tickets = tickets;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
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
        }
    }
}
