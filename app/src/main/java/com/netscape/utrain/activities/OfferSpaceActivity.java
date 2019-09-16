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

public class OfferSpaceActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    AppCompatSpinner spinnerLocation;
    MaterialTextView tvStartBsnsHour, tvEndBsnsHour, tvSelectDate;

    int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_space);

        tvStartBsnsHour = findViewById(R.id.offerSpaceSelectStartbsnsHourTv);
        tvEndBsnsHour = findViewById(R.id.offerSpaceSelectEndbsnsHourTv);
        tvSelectDate = findViewById(R.id.offerSpaceEnterDateTv);

        tvStartBsnsHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(OfferSpaceActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfday, int minute) {
                        tvStartBsnsHour.setText(hourOfday + ":" + minute);
                    }
                }, mHour, mMinute, true);
                timePickerDialog.show();



            }
        });

        tvEndBsnsHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(OfferSpaceActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        tvEndBsnsHour.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, true);
                timePickerDialog.show();
            }
        });

        tvSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                DialogFragment datePicker = new DialogPickerFragment();
//                datePicker.show(getSupportFragmentManager(), "date picker");

                Calendar c = Calendar.getInstance();
                c.get(Calendar.YEAR);
                c.get(Calendar.MONTH);
                c.get(Calendar.DAY_OF_MONTH);
                final DatePickerDialog datePickerDialog = new DatePickerDialog(OfferSpaceActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        tvSelectDate.setText(dayOfMonth + "-" + month + "-" + year);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        spinnerLocation = findViewById(R.id.offerSpaceLocationSpinner);


        List<String> list = new ArrayList<String>();
        list.add("Select Location");
        list.add("Texas");
        list.add("America");
        list.add("Australia");
        list.add("England");
        list.add("India");
        list.add("Brazil");

        ArrayAdapter adapter = new ArrayAdapter(OfferSpaceActivity.this,android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLocation.setAdapter(adapter);

        spinnerLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerLocation.setSelection(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String currentDateString = DateFormat.getDateInstance(DateFormat.MEDIUM).format(c.getTime());
        tvSelectDate.setText(currentDateString);




    }
}
