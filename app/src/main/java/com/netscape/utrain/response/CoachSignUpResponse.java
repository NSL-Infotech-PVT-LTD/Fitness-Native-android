package com.netscape.utrain.response;

import com.netscape.utrain.model.CoachDataModel;
import com.netscape.utrain.model.ErrorModel;

public class CoachSignUpResponse {

    private boolean status;
    private int code;
    private ErrorModel error;

    public CoachDataModel getData() {
        return data;
    }

    public void setData(CoachDataModel data) {
        this.data = data;
    }

    private CoachDataModel data;

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

    public ErrorModel getError() {
        return error;
    }

    public void setError(ErrorModel error) {
        this.error = error;
    }

}
