package com.netscape.utrain.response;

import com.netscape.utrain.model.ServiceDataModel;
import com.netscape.utrain.model.ServiceListDataModel;

import java.util.List;

public class ServiceListResponse {

    /**
     * status : true
     * code : 200
     */

    private boolean status;
    private int code;
    private ServiceDataModel data;

    public ServiceDataModel getData() {
        return data;
    }

    public void setData(ServiceDataModel data) {
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
