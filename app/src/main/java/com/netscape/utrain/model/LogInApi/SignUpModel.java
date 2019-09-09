package com.netscape.utrain.model.LogInApi;

public class SignUpModel {


    private boolean status;
    private int code;
    private RoleModel data;

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

    public RoleModel getData() {
        return data;
    }

    public void setData(RoleModel data) {
        this.data = data;
    }
}
