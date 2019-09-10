package com.netscape.utrain.response;

import com.netscape.utrain.model.ErrorModel;
import com.netscape.utrain.model.LoginRoleModel;

public class LoginResponse {

    private boolean status;
    private int code;
    private LoginRoleModel data;
    private ErrorModel error;

    public ErrorModel getError() {
        return error;
    }

    public void setError(ErrorModel error) {
        this.error = error;
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

    public LoginRoleModel getData() {
        return data;
    }

    public void setData(LoginRoleModel data) {
        this.data = data;
    }
}
