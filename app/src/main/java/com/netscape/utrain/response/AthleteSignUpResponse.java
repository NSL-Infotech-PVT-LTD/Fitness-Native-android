package com.netscape.utrain.response;

import com.netscape.utrain.model.DataModel;
import com.netscape.utrain.model.ErrorModel;

public class AthleteSignUpResponse {

    private boolean status;
    private int code;
    private ErrorModel error;
    private DataModel data;

    public ErrorModel getError() {
        return error;
    }

    public void setError(ErrorModel error) {
        this.error = error;
    }

    public DataModel getData() {
        return data;
    }

    public void setData(DataModel data) {
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
