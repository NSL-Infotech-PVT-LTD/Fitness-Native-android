package com.netscape.utrain.response;

import com.netscape.utrain.model.ErrorModel;
import com.netscape.utrain.model.O_BookedEventDataModel;
import com.netscape.utrain.model.O_BookedEventPaginationModel;
import com.netscape.utrain.model.O_BookedSessionDataModel;
import com.netscape.utrain.model.O_BookedSessionPaginationModel;

import java.util.List;

public class O_SessionBookedListResponse {

    private boolean status;
    private int code;

    private O_BookedSessionPaginationModel data;

    public O_BookedSessionPaginationModel getData() {
        return data;
    }

    public void setData(O_BookedSessionPaginationModel data) {
        this.data = data;
    }

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



    public ErrorModel getError() {
        return error;
    }

    public void setError(ErrorModel error) {
        this.error = error;
    }
}
