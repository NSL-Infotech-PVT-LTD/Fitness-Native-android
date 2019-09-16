package com.netscape.utrain.model;

public class ServiceListDataModel {

    /**
     * id : 1
     * name : CARDIO FITNESS
     */

    private int id;
    private String name;
    private String price;
    private boolean isSelected = false;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

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
