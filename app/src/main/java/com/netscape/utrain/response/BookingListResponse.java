package com.netscape.utrain.response;

import com.netscape.utrain.model.BookingListDataModel;
import com.netscape.utrain.model.ErrorModel;

import java.util.List;

public class BookingListResponse {

    private boolean status;
    private int code;
    private List<BookingListDataModel> data;
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

    public List<BookingListDataModel> getData() {
        return data;
    }

    public void setData(List<BookingListDataModel> data) {
        this.data = data;
    }

    public ErrorModel getError() {
        return error;
    }

    public void setError(ErrorModel error) {
        this.error = error;
    }

}
