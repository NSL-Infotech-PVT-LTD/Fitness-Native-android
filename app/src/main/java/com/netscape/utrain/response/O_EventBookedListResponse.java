package com.netscape.utrain.response;

import com.netscape.utrain.model.A_BookedEventDataModel;
import com.netscape.utrain.model.ErrorModel;
import com.netscape.utrain.model.O_BookedEventDataModel;

import java.util.List;

public class O_EventBookedListResponse {
    private boolean status;
    private int code;
    private List<O_BookedEventDataModel> data;
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

    public List<O_BookedEventDataModel> getData() {
        return data;
    }

    public void setData(List<O_BookedEventDataModel> data) {
        this.data = data;
    }

    public ErrorModel getError() {
        return error;
    }

    public void setError(ErrorModel error) {
        this.error = error;
    }
}
