package com.netscape.utrain.StripeConnect;

public class StripeConnectModel {


    /**
     * status : true
     * code : 200
     * data : success
     */

    private boolean status;
    private int code;
    private String data;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
