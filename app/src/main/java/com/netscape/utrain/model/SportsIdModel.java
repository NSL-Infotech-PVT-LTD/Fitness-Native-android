package com.netscape.utrain.model;

import java.io.Serializable;

public class SportsIdModel implements Serializable {


    private int id;
    private boolean isSelected;
    private String sportName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }
}
