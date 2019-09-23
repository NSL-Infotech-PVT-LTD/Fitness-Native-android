package com.netscape.utrain.activities;

import com.netscape.utrain.model.ServiceListDataModel;

import java.util.ArrayList;

public class SelectedServiceList {
    private static final SelectedServiceList ourInstance = new SelectedServiceList();

    ArrayList<ServiceListDataModel> list = new ArrayList();
    public static SelectedServiceList getInstance() {
        return ourInstance;
    }

    private SelectedServiceList() {
    }

    public ArrayList<ServiceListDataModel> getList() {
        return list;
    }
}
