package com.netscape.utrain.response;

import com.netscape.utrain.model.A_BookedEventDataModel;
import com.netscape.utrain.model.A_EventDataModel;
import com.netscape.utrain.model.ErrorModel;

import java.util.List;

public class A_BookedEventResponse {
    private boolean status;
    private int code;
    private List<A_BookedEventDataModel> data;
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

    public List<A_BookedEventDataModel> getData() {
        return data;
    }

    public void setData(List<A_BookedEventDataModel> data) {
        this.data = data;
    }

    public ErrorModel getError() {
        return error;
    }

    public void setError(ErrorModel error) {
        this.error = error;
    }
}
