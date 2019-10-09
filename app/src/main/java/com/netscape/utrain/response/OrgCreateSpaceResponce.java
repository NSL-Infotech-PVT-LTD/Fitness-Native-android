package com.netscape.utrain.response;

import com.netscape.utrain.model.OrgCreateSpaceDataModel;

public class OrgCreateSpaceResponce {
    private boolean status;
    private int code;
    private OrgCreateSpaceDataModel data;

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

    public OrgCreateSpaceDataModel getData() {
        return data;
    }

    public void setData(OrgCreateSpaceDataModel data) {
        this.data = data;
    }
}
