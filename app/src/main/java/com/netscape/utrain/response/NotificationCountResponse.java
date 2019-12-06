package com.netscape.utrain.response;

import com.netscape.utrain.model.ErrorModel;

public class NotificationCountResponse {

    /**
     * status : true
     * code : 200
     * data : {"notification_count":0}
     */

    private boolean status;
    private int code;
    private DataBean data;

    public ErrorModel getError() {
        return error;
    }

    public void setError(ErrorModel error) {
        this.error = error;
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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * notification_count : 0
         */

        private int notification_count;

        public int getNotification_count() {
            return notification_count;
        }

        public void setNotification_count(int notification_count) {
            this.notification_count = notification_count;
        }
    }
}
