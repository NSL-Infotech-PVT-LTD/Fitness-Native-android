package com.netscape.utrain.model;

public class OrgCreateSpaceDataModel {
   private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public OrgCreateSpaceModel getSpace() {
        return space;
    }

    public void setSpace(OrgCreateSpaceModel space) {
        this.space = space;
    }

    private OrgCreateSpaceModel space;
}
