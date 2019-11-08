package com.netscape.utrain.response;

import com.netscape.utrain.model.BookingListDataModel;
import com.netscape.utrain.model.ErrorModel;
import com.netscape.utrain.model.O_EventDataModel;
import com.netscape.utrain.model.O_EventPageDataModel;

import java.util.List;

public class O_EventListResponse {
    private boolean status;
    private int code;
    private O_EventPageDataModel data;
    private ErrorModel error;

    public O_EventPageDataModel getData() {
        return data;
    }

    public void setData(O_EventPageDataModel data) {
        this.data = data;
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

    public ErrorModel getError() {
        return error;
    }

    public void setError(ErrorModel error) {
        this.error = error;
    }
}
