package com.netscape.utrain.response;

import com.netscape.utrain.model.C_EventDataListModel;
import com.netscape.utrain.model.C_SessionListModel;
import com.netscape.utrain.model.ErrorModel;

import java.util.List;

public class C_SessionListResponse {
    private boolean status;
    private int code;
    private List<C_SessionListModel> data;
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

    public List<C_SessionListModel> getData() {
        return data;
    }

    public void setData(List<C_SessionListModel> data) {
        this.data = data;
    }

    public ErrorModel getError() {
        return error;
    }

    public void setError(ErrorModel error) {
        this.error = error;
    }
}
