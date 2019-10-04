package com.netscape.utrain.model;

import java.util.List;

public class AthletePlaceDataModel {
    private List<AthletePlaceModel> data;

    String current_page;

    public String getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(String current_page) {
        this.current_page = current_page;
    }

    public List<AthletePlaceModel> getData() {
        return data;
    }

    public void setData(List<AthletePlaceModel> data) {
        this.data = data;
    }
}
