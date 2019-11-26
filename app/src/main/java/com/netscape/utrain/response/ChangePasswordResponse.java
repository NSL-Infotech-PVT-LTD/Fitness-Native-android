package com.netscape.utrain.response;

import com.netscape.utrain.model.ChangePasswordModel;
import com.netscape.utrain.model.ErrorModel;

public class ChangePasswordResponse {


    private boolean status;
    private int code;
    private ChangePasswordModel data;
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

    public ChangePasswordModel getData() {
        return data;
    }

    public void setData(ChangePasswordModel data) {
        this.data = data;
    }

    public ErrorModel getError() {
        return error;
    }

    public void setError(ErrorModel error) {
        this.error = error;
    }
}
