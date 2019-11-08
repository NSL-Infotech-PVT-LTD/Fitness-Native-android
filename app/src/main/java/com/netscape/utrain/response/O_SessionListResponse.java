package com.netscape.utrain.response;

import com.netscape.utrain.model.ErrorModel;
import com.netscape.utrain.model.O_EventDataModel;
import com.netscape.utrain.model.O_SessionDataModel;
import com.netscape.utrain.model.O_SessionPageDataModel;

import java.util.List;

public class O_SessionListResponse {
    private boolean status;
    private int code;

    public O_SessionPageDataModel getData() {
        return data;
    }

    private O_SessionPageDataModel data;
    private ErrorModel error;

    public void setData(O_SessionPageDataModel data) {
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
