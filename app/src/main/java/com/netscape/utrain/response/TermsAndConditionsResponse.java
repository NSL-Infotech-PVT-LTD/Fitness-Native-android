package com.netscape.utrain.response;

import com.netscape.utrain.model.TermsAndConditionsModel;

import java.util.List;

public class TermsAndConditionsResponse {


    private boolean status;
    private int code;
    private List<TermsAndConditionsModel> data;


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

    public List<TermsAndConditionsModel> getData() {
        return data;
    }

    public void setData(List<TermsAndConditionsModel> data) {
        this.data = data;
    }
}
