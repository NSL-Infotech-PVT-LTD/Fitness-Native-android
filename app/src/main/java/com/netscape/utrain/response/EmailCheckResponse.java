package com.netscape.utrain.response;

import com.netscape.utrain.model.ErrorModel;

public class EmailCheckResponse {

    /**
     * status : true
     * code : 200
     * data : {"scalar":"It is not available in database"}
     */

    private boolean status;
    private int code;
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    private ErrorModel error;

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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }



}
