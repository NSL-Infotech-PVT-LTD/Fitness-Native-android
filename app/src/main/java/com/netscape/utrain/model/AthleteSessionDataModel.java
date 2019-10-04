package com.netscape.utrain.model;

import java.util.List;

public class AthleteSessionDataModel {

    String current_page;
    private List<AthleteSessionModel> data;

    public String getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(String current_page) {
        this.current_page = current_page;
    }

    public List<AthleteSessionModel> getData() {
        return data;
    }

    public void setData(List<AthleteSessionModel> data) {
        this.data = data;
    }
}
