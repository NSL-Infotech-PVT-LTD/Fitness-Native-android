package com.netscape.utrain.response;

import com.netscape.utrain.model.A_EventDataModel;
import com.netscape.utrain.model.A_SpaceDataModel;

public class A_SpaceListResponse {
    private boolean status;
    private int code;
    private A_SpaceDataModel data;

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

    public A_SpaceDataModel getData() {
        return data;
    }

    public void setData(A_SpaceDataModel data) {
        this.data = data;
    }
}
