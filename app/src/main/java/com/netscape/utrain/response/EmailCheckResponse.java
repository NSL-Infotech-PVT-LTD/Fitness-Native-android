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
    private DataBean data;
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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * scalar : It is not available in database
         */

        private String scalar;

        public String getScalar() {
            return scalar;
        }

        public void setScalar(String scalar) {
            this.scalar = scalar;
        }
    }
}
