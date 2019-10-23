package com.netscape.utrain.response;

import com.netscape.utrain.model.ErrorModel;
import com.netscape.utrain.model.O_BookedEventDataModel;
import com.netscape.utrain.model.O_BookedSpacePagination;
import com.netscape.utrain.model.O_SpaceListDataModel;

import java.util.List;

public class O_BookedSpaceListResponse {
    private boolean status;
    private int code;
    private O_BookedSpacePagination data;
    private ErrorModel error;

    public O_BookedSpacePagination getData() {
        return data;
    }

    public void setData(O_BookedSpacePagination data) {
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
