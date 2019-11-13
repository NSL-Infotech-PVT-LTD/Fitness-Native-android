package com.netscape.utrain.response;

import com.netscape.utrain.model.ViewCoachPageModel;

public class ViewCoachListResponse {


    private boolean status;
    private int code;
    private ViewCoachPageModel data;

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

    public ViewCoachPageModel getData() {
        return data;
    }

    public void setData(ViewCoachPageModel data) {
        this.data = data;
    }
}
