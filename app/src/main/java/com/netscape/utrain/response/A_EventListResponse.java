package com.netscape.utrain.response;

import com.netscape.utrain.model.A_EventDataModel;
import com.netscape.utrain.model.ErrorModel;
import com.netscape.utrain.model.O_EventDataModel;

import java.util.List;

public class A_EventListResponse {

    /**
     * status : true
     * code : 200
     */

    private boolean status;
    private int code;
    private A_EventDataModel data;

    public A_EventDataModel getData() {
        return data;
    }

    public void setData(A_EventDataModel data) {
        this.data = data;
    }

    public ErrorModel getError() {
        return error;
    }

    public void setError(ErrorModel error) {
        this.error = error;
    }

    private ErrorModel error;


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
