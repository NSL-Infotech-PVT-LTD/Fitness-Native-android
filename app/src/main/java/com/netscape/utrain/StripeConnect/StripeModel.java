package com.netscape.utrain.StripeConnect;

public class StripeModel {


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
