package com.netscape.utrain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.google.android.material.textview.MaterialTextView;
import com.netscape.utrain.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class OfferSpaceActivity extends AppCompatActivity {

    AppCompatSpinner offerSpaceSelectStrtWeek, offerSpaceSelectEndWeek;
    MaterialTextView OfferSpaceAvailableStrtTv, OfferSpaceAvailableEndTv;

    int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_space);

//        tvStartBsnsHour = findViewById(R.id.offerSpaceSelectStartbsnsHourTv);
//        tvEndBsnsHour = findViewById(R.id.offerSpaceSelectEndbsnsHourTv);
//        tvSelectDate = findViewById(R.id.offerSpaceEnterDateTv);


        offerSpaceSelectStrtWeek = findViewById(R.id.offerSpaceSelectStrtWeek);
        offerSpaceSelectEndWeek = findViewById(R.id.offerSpaceSelectEndWeek);
        OfferSpaceAvailableStrtTv = findViewById(R.id.OfferSpaceAvailableStrtTv);
        OfferSpaceAvailableEndTv = findViewById(R.id.OfferSpaceAvailableEndTv);


        final List<String> startWeekList = new ArrayList<String>();
        startWeekList.add("Start Week");
        startWeekList.add("Sunday");
        startWeekList.add("Monday");
        startWeekList.add("Tuesday");
        startWeekList.add("Wednesday");
        startWeekList.add("Thursday");
        startWeekList.add("Friday");
        startWeekList.add("Saturday");

        ArrayAdapter startWeekadapter = new ArrayAdapter(OfferSpaceActivity.this,android.R.layout.simple_spinner_item,startWeekList);
        startWeekadapter .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        offerSpaceSelectStrtWeek.setAdapter(startWeekadapter );

        offerSpaceSelectStrtWeek.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                offerSpaceSelectStrtWeek.setSelection(i);
//                OfferSpaceAvailableStrtTv.setText(startWeekList.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final List<String> endWeekList = new ArrayList<String>();
        endWeekList.add("End Week");
        endWeekList.add("Sunday");
        endWeekList.add("Monday");
        endWeekList.add("Tuesday");
        endWeekList.add("Wednesday");
        endWeekList.add("Thursday");
        endWeekList.add("Friday");
        endWeekList.add("Saturday");

        ArrayAdapter endWeekAdapter = new ArrayAdapter(OfferSpaceActivity.this,android.R.layout.simple_spinner_item,endWeekList );
        endWeekAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        offerSpaceSelectEndWeek.setAdapter(endWeekAdapter);

        offerSpaceSelectEndWeek.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                offerSpaceSelectEndWeek.setSelection(i);
//                OfferSpaceAvailableEndTv.setText(endWeekList.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
