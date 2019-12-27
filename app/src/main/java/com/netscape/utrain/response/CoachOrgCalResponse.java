package com.netscape.utrain.response;

import com.netscape.utrain.model.CoachOrgCalDataModel;

public class CoachOrgCalResponse {
    private boolean status;
    private int code;

    public CoachOrgCalDataModel getData() {
        return data;
    }

    public void setData(CoachOrgCalDataModel data) {
        this.data = data;
    }

    private CoachOrgCalDataModel data;

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
