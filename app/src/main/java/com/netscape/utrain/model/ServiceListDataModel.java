package com.netscape.utrain.model;

public class ServiceListDataModel {

    /**
     * id : 1
     * name : CARDIO FITNESS
     */

    private int id;
    private String name;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    private boolean isSelected=false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
