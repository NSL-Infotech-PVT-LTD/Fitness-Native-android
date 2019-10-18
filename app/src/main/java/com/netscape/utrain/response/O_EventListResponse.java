package com.netscape.utrain.response;

import com.netscape.utrain.model.BookingListDataModel;
import com.netscape.utrain.model.ErrorModel;
import com.netscape.utrain.model.O_EventDataModel;

import java.util.List;

public class O_EventListResponse {
    private boolean status;
    private int code;
    private List<O_EventDataModel> data;
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

    public List<O_EventDataModel> getData() {
        return data;
    }

    public void setData(List<O_EventDataModel> data) {
        this.data = data;
    }

    public ErrorModel getError() {
        return error;
    }

    public void setError(ErrorModel error) {
        this.error = error;
    }
}
