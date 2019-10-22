package com.netscape.utrain.response;

import com.netscape.utrain.model.ErrorModel;
import com.netscape.utrain.model.O_BookedEventDataModel;
import com.netscape.utrain.model.O_SpaceListDataModel;

import java.util.List;

public class O_BookedSpaceListResponse {
    private boolean status;
    private int code;
    private List<O_SpaceListDataModel> data;
    private ErrorModel error;

    public List<O_SpaceListDataModel> getData() {
        return data;
    }

    public void setData(List<O_SpaceListDataModel> data) {
        this.data = data;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
