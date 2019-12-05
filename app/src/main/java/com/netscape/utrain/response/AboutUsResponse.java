package com.netscape.utrain.response;

import com.netscape.utrain.model.AboutUsModel;
import com.netscape.utrain.model.ErrorModel;

import java.util.List;

public class AboutUsResponse {


    private boolean status;
    private int code;
    private List<AboutUsModel> data;
    private ErrorModel error;

    public List<AboutUsModel> getData() {
        return data;
    }

    public void setData(List<AboutUsModel> data) {
        this.data = data;
    }

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


}
