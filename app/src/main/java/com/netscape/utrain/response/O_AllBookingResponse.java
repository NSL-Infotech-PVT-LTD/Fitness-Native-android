package com.netscape.utrain.response;

import com.netscape.utrain.model.O_AllBookingDataModel;

public class O_AllBookingResponse {

    /**
     * status : true
     * code : 200
     */

    private boolean status;
    private int code;
    private O_AllBookingDataModel data;

    public O_AllBookingDataModel getData() {
        return data;
    }

    public void setData(O_AllBookingDataModel data) {
        this.data = data;
    }

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
}
