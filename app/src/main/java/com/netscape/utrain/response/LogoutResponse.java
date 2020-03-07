package com.netscape.utrain.response;

import com.google.gson.annotations.SerializedName;
import com.netscape.utrain.model.ErrorModel;

public class LogoutResponse {
    /**
     * status : true
     * code : 200
     * data : {"scalar":"Logout Successfully"}
     */



    private boolean status;
    private int code;
    private ErrorModel error;
    /**
     * data : Logout Successfully
     */

    @SerializedName("data")
    private String data;

    public ErrorModel getError() {
        return error;
    }

    public void setError(ErrorModel error) {
        this.error = error;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
