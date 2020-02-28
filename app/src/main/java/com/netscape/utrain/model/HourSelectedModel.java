package com.netscape.utrain.model;

public class HourSelectedModel {
    String hour;
    boolean isSelected;
    boolean isEvent;
    int eventPosition;

    public int getEventPosition() {
        return eventPosition;
    }

    public void setEventPosition(int eventPosition) {
        this.eventPosition = eventPosition;
    }

    public boolean isEvent() {
        return isEvent;
    }

    public void setEvent(boolean event) {
        isEvent = event;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
