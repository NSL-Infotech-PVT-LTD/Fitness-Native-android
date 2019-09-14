package com.netscape.utrain.response;

import com.netscape.utrain.model.ServiceListDataModel;

import java.util.List;

public class ServiceListResponse {

    /**
     * status : true
     * code : 200
     */

    private boolean status;
    private int code;
    private List<ServiceListDataModel> data;

    public List<ServiceListDataModel> getData() {
        return data;
    }

    public void setData(List<ServiceListDataModel> data) {
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
