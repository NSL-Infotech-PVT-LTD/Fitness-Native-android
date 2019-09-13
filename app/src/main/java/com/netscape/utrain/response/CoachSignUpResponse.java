package com.netscape.utrain.response;

import com.netscape.utrain.model.CoachSignUpModel;
import com.netscape.utrain.model.ErrorModel;

public class CoachSignUpResponse {

    private boolean status;
    private int code;
    private ErrorModel error;
    private CoachSignUpModel data;

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

    public CoachSignUpModel getData() {
        return data;
    }

    public void setData(CoachSignUpModel data) {
        this.data = data;
    }
}
