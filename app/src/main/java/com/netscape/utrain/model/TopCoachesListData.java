package com.netscape.utrain.model;

import java.util.List;

public class TopCoachesListData {
    String current_page;
    List<CoachListModel> data;

    public String getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(String current_page) {
        this.current_page = current_page;
    }

    public List<CoachListModel> getData() {
        return data;
    }

    public void setData(List<CoachListModel> data) {
        this.data = data;
    }
}
