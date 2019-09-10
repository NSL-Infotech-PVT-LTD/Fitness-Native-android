package com.netscape.utrain.response;

import com.netscape.utrain.model.DataModel;
import com.netscape.utrain.model.ErrorModel;
import com.netscape.utrain.model.ForgetDataModel;

public class ForgetPasswordResponse {

    private boolean status;
    private int code;
    private ErrorModel error;
    private ForgetDataModel data;

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

    public ForgetDataModel getData() {
        return data;
    }

    public void setData(ForgetDataModel data) {
        this.data = data;
    }
}
