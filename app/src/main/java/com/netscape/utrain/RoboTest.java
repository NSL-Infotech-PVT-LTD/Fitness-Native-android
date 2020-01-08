package com.netscape.utrain;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.netscape.utrain.activities.HourlySlotsActivity;
import com.netscape.utrain.views.RobotoCalendarView;

import java.util.Date;

public class RoboTest extends AppCompatActivity implements RobotoCalendarView.RobotoCalendarListener {
    RobotoCalendarView robotoCalendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robo_test);
        robotoCalendarView=findViewById(R.id.roboTestCalHours);
        robotoCalendarView.setRobotoCalendarListener(RoboTest.this);

        robotoCalendarView.setShortWeekDays(false);

        robotoCalendarView.showDateTitle(true);

        robotoCalendarView.setDate(new Date());
    }

    @Override
    public void onDayClick(Date date) {

    }

    @Override
    public void onDayLongClick(Date date) {

    }

    @Override
    public void onRightButtonClick() {

    }

    @Override
    public void onLeftButtonClick() {

    }
}
