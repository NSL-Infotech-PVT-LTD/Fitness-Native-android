package com.netscape.utrain.response;

import com.netscape.utrain.model.ErrorModel;
import com.netscape.utrain.model.OrgCreateEventDataModel;

public class OrgCreateEventResponse {
    private boolean status;
    private int code;
    private OrgCreateEventDataModel data;
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

    public OrgCreateEventDataModel getData() {
        return data;
    }

    public void setData(OrgCreateEventDataModel data) {
        this.data = data;
    }

    public ErrorModel getError() {
        return error;
    }

    public void setError(ErrorModel error) {
        this.error = error;
    }
}
