package com.netscape.utrain.response;

import com.netscape.utrain.model.A_EventDataModel;
import com.netscape.utrain.model.A_SessionDataModel;

public class A_SessionResponse {
    private boolean status;
    private int code;
    private A_SessionDataModel data;

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

    public A_SessionDataModel getData() {
        return data;
    }

    public void setData(A_SessionDataModel data) {
        this.data = data;
    }
}
