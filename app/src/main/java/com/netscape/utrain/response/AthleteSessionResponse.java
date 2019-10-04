package com.netscape.utrain.response;

import com.netscape.utrain.model.AthleteSessionDataModel;
import com.netscape.utrain.model.AthleteSessionModel;
import com.netscape.utrain.model.ErrorModel;

import java.util.List;

public class AthleteSessionResponse {
    private boolean status;
    private int code;
    private ErrorModel error;

    private AthleteSessionDataModel data;

    public AthleteSessionDataModel getData() {
        return data;
    }

    public void setData(AthleteSessionDataModel data) {
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
