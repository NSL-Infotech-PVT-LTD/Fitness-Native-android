package com.netscape.utrain.response;

import com.netscape.utrain.model.NotificationPageModel;

public class NotificationResponse {

    private boolean status;
    private int code;
    private NotificationPageModel data;

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

    public NotificationPageModel getData() {
        return data;
    }

    public void setData(NotificationPageModel data) {
        this.data = data;
    }
}
