package com.netscape.utrain.response;

public class SpaceBookingResponse {


    /**
     * status : true
     * code : 201
     * data : {"message":"Created Successfully","booking":{"target_id":"15","price":"200","status":"pending","space_date_start":"2019-11-18 15:30","space_date_end":"2019-11-30 15:30","type":"space","user_id":287,"owner_id":297,"updated_at":"2019-11-11 11:44:35","created_at":"2019-11-11 11:44:35","id":22,"target_data":{"id":15,"name":"test space","images":"[\"1573458998106.jpg\"]","description":"available","price_hourly":58,"availability_week":"Thursday-Friday","location":"A-40 phase 8B, Industrial Area, Sahibzada Ajit Singh Nagar, Punjab, India","latitude":"30.706309638761212","longitude":"76.69791195541622","created_by":297,"price_daily":50},"booking_date":{"start":null,"end":null}}}
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
         * booking : {"target_id":"15","price":"200","status":"pending","space_date_start":"2019-11-18 15:30","space_date_end":"2019-11-30 15:30","type":"space","user_id":287,"owner_id":297,"updated_at":"2019-11-11 11:44:35","created_at":"2019-11-11 11:44:35","id":22,"target_data":{"id":15,"name":"test space","images":"[\"1573458998106.jpg\"]","description":"available","price_hourly":58,"availability_week":"Thursday-Friday","location":"A-40 phase 8B, Industrial Area, Sahibzada Ajit Singh Nagar, Punjab, India","latitude":"30.706309638761212","longitude":"76.69791195541622","created_by":297,"price_daily":50},"booking_date":{"start":null,"end":null}}
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
             * target_id : 15
             * price : 200
             * status : pending
             * space_date_start : 2019-11-18 15:30
             * space_date_end : 2019-11-30 15:30
             * type : space
             * user_id : 287
             * owner_id : 297
             * updated_at : 2019-11-11 11:44:35
             * created_at : 2019-11-11 11:44:35
             * id : 22
             * target_data : {"id":15,"name":"test space","images":"[\"1573458998106.jpg\"]","description":"available","price_hourly":58,"availability_week":"Thursday-Friday","location":"A-40 phase 8B, Industrial Area, Sahibzada Ajit Singh Nagar, Punjab, India","latitude":"30.706309638761212","longitude":"76.69791195541622","created_by":297,"price_daily":50}
             * booking_date : {"start":null,"end":null}
             */

            private String target_id;
            private String price;
            private String status;
            private String space_date_start;
            private String space_date_end;
            private String type;
            private int user_id;
            private int owner_id;
            private String updated_at;
            private String created_at;
            private int id;
            private TargetDataBean target_data;
            private BookingDateBean booking_date;

            public String getTarget_id() {
                return target_id;
            }

            public void setTarget_id(String target_id) {
                this.target_id = target_id;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getSpace_date_start() {
                return space_date_start;
            }

            public void setSpace_date_start(String space_date_start) {
                this.space_date_start = space_date_start;
            }

            public String getSpace_date_end() {
                return space_date_end;
            }

            public void setSpace_date_end(String space_date_end) {
                this.space_date_end = space_date_end;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
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

            public static class TargetDataBean {
                /**
                 * id : 15
                 * name : test space
                 * images : ["1573458998106.jpg"]
                 * description : available
                 * price_hourly : 58
                 * availability_week : Thursday-Friday
                 * location : A-40 phase 8B, Industrial Area, Sahibzada Ajit Singh Nagar, Punjab, India
                 * latitude : 30.706309638761212
                 * longitude : 76.69791195541622
                 * created_by : 297
                 * price_daily : 50
                 */

                private int id;
                private String name;
                private String images;
                private String description;
                private int price_hourly;
                private String availability_week;
                private String location;
                private String latitude;
                private String longitude;
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
        }
    }
}
