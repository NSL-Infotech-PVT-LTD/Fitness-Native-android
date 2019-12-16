package com.netscape.utrain.model;

public class SelectSpaceDaysModel {
    boolean isChecked;
    private String daySeleced;

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    private String dayName;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getDaySeleced() {
        return daySeleced;
    }

    public void setDaySeleced(String daySeleced) {
        this.daySeleced = daySeleced;
    }
}
