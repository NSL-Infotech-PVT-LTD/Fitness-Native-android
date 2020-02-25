package com.netscape.utrain.model;

import java.io.Serializable;

public class ServiceIdModel implements Serializable {

    /**
     * id : 1
     * isSelected : true
     * name : CARDIO FITNESS
     * price : 23
     */

    private int id;
    private boolean isSelected;
    private String name;
    private String price;
    private boolean isChecked = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isIsSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    @Override
    public String toString() {
        return name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
