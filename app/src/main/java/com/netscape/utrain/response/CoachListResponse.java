package com.netscape.utrain.response;

import com.netscape.utrain.model.CoachListModel;
import com.netscape.utrain.model.ErrorModel;

import java.util.List;

public class CoachListResponse {


    private boolean status;
    private int code;
    private ErrorModel error;
    private List<CoachListModel> data;

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

    public List<CoachListModel> getData() {
        return data;
    }

    public void setData(List<CoachListModel> data) {
        this.data = data;
    }
}
