package com.netscape.utrain.model;

public class OrgCreateEventDataModel {
    private String message;
    private OrgCreateEventModel event;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public OrgCreateEventModel getEvent() {
        return event;
    }

    public void setEvent(OrgCreateEventModel event) {
        this.event = event;
    }
}
