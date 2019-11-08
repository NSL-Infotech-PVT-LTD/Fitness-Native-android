package com.netscape.utrain.response;

import com.netscape.utrain.model.A_EventDataModel;
import com.netscape.utrain.model.C_EventDataListModel;
import com.netscape.utrain.model.C_EventDataModel;
import com.netscape.utrain.model.ErrorModel;

import java.util.List;

public class C_EventListResponse {

    private boolean status;
    private int code;
    private C_EventDataModel data;
    private ErrorModel error;

    public C_EventDataModel getData() {
        return data;
    }

    public void setData(C_EventDataModel data) {
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
