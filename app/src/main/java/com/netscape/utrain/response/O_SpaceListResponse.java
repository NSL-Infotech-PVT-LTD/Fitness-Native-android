package com.netscape.utrain.response;

import com.netscape.utrain.model.ErrorModel;
import com.netscape.utrain.model.O_EventDataModel;
import com.netscape.utrain.model.O_SpaceDataModel;

import java.util.List;

public class O_SpaceListResponse {
    private boolean status;
    private int code;
    private List<O_SpaceDataModel> data;
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

    public List<O_SpaceDataModel> getData() {
        return data;
    }

    public void setData(List<O_SpaceDataModel> data) {
        this.data = data;
    }

    public ErrorModel getError() {
        return error;
    }

    public void setError(ErrorModel error) {
        this.error = error;
    }
}
