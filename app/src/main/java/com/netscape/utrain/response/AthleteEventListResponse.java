package com.netscape.utrain.response;

import com.netscape.utrain.model.AthleteEventListModel;
import com.netscape.utrain.model.AthleteResponseEventData;
import com.netscape.utrain.model.ErrorModel;

import java.util.List;

public class AthleteEventListResponse {


    private boolean status;
    private int code;
    private ErrorModel error;
    private AthleteResponseEventData data;

    public AthleteResponseEventData getData() {
        return data;
    }

    public void setData(AthleteResponseEventData data) {
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
