package com.netscape.utrain.response;

import com.netscape.utrain.model.AboutUsModel;

public class AboutUsResponse {


    private boolean status;
    private int code;
    private AboutUsModel data;

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

    public AboutUsModel getData() {
        return data;
    }

    public void setData(AboutUsModel data) {
        this.data = data;
    }
}
