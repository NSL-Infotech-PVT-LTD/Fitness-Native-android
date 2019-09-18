package com.netscape.utrain.model;

public class OnGoingSessionCoachBottomNavModel {

    String nameOrganizer;
    String Sport;

    public OnGoingSessionCoachBottomNavModel(String name, String sport) {
        this.nameOrganizer = name;
        Sport = sport;
    }

    public String getName() {
        return nameOrganizer;
    }

    public void setName(String name) {
        this.nameOrganizer = name;
    }

    public String getSport() {
        return Sport;
    }

    public void setSport(String sport) {
        Sport = sport;
    }
}
