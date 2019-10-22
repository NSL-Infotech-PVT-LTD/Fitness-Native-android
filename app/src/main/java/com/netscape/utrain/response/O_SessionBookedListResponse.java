package com.netscape.utrain.response;

import com.netscape.utrain.model.ErrorModel;
import com.netscape.utrain.model.O_BookedEventDataModel;
import com.netscape.utrain.model.O_BookedSessionDataModel;

import java.util.List;

public class O_SessionBookedListResponse {

    private boolean status;
    private int code;
    private List<O_BookedSessionDataModel> data;
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

    public List<O_BookedSessionDataModel> getData() {
        return data;
    }

    public void setData(List<O_BookedSessionDataModel> data) {
        this.data = data;
    }

    public ErrorModel getError() {
        return error;
    }

    public void setError(ErrorModel error) {
        this.error = error;
    }
}
