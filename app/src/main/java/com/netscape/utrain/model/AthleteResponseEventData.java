package com.netscape.utrain.model;

import java.util.List;

public class AthleteResponseEventData {


    String current_page;
    private List<AthleteEventListModel> data;

    public String getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(String current_page) {
        this.current_page = current_page;
    }

    public List<AthleteEventListModel> getData() {
        return data;
    }

    public void setData(List<AthleteEventListModel> data) {
        this.data = data;
    }
}
