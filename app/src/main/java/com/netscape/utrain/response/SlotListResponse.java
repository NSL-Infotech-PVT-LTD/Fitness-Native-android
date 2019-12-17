package com.netscape.utrain.response;

import com.netscape.utrain.model.ErrorModel;

import java.util.List;

public class SlotListResponse {

    /**
     * status : true
     * code : 200
     * data : {"available_slot":[["14:47:00","15:47:00"],["16:47:00","17:47:00"],["17:47:00","18:47:00"],["18:47:00","19:47:00"],["19:47:00","20:47:00"],["20:47:00","21:47:00"]]}
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
        private List<List<String>> available_slot;

        public List<List<String>> getAvailable_slot() {
            return available_slot;
        }

        public void setAvailable_slot(List<List<String>> available_slot) {
            this.available_slot = available_slot;
        }
    }
}
