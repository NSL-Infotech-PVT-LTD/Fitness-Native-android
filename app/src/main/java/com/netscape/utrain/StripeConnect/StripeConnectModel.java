package com.netscape.utrain.StripeConnect;

public class StripeConnectModel {


    /**
     * status : true
     * code : 200
     * data : {"stripeDetails":{"id":14,"account_id":"acct_1GJZvHBDPMZdor6m","customer_id":null,"user_id":2,"created_at":"2020-03-06 12:20:36","updated_at":"2020-03-06 12:20:36"}}
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
         * stripeDetails : {"id":14,"account_id":"acct_1GJZvHBDPMZdor6m","customer_id":null,"user_id":2,"created_at":"2020-03-06 12:20:36","updated_at":"2020-03-06 12:20:36"}
         */

        private StripeDetailsBean stripeDetails;

        public StripeDetailsBean getStripeDetails() {
            return stripeDetails;
        }

        public void setStripeDetails(StripeDetailsBean stripeDetails) {
            this.stripeDetails = stripeDetails;
        }

        public static class StripeDetailsBean {
            /**
             * id : 14
             * account_id : acct_1GJZvHBDPMZdor6m
             * customer_id : null
             * user_id : 2
             * created_at : 2020-03-06 12:20:36
             * updated_at : 2020-03-06 12:20:36
             */

            private int id;
            private String account_id;
            private Object customer_id;
            private int user_id;
            private String created_at;
            private String updated_at;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getAccount_id() {
                return account_id;
            }

            public void setAccount_id(String account_id) {
                this.account_id = account_id;
            }

            public Object getCustomer_id() {
                return customer_id;
            }

            public void setCustomer_id(Object customer_id) {
                this.customer_id = customer_id;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
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
        }
    }
}
