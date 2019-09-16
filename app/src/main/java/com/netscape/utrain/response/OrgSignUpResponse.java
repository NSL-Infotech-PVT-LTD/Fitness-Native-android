package com.netscape.utrain.response;

import com.netscape.utrain.model.ErrorModel;
import com.netscape.utrain.model.OrgDataModel;

public class OrgSignUpResponse {

    /**
     * status : true
     * code : 201
     */

    private boolean status;
    private int code;
    private OrgDataModel data;
    private ErrorModel error;

    public ErrorModel getError() {
        return error;
    }

    public void setError(ErrorModel error) {
        this.error = error;
    }

    public OrgDataModel getData() {
        return data;
    }

    public void setData(OrgDataModel data) {
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
