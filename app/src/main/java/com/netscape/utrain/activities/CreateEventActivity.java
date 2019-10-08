package com.netscape.utrain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.netscape.utrain.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CreateEventActivity extends AppCompatActivity {


    AppCompatSpinner spinnerLocation;
    MaterialTextView startBusinessHourTv, endBusinessHourTv, textViewDate,createEventStartDateTv,createEventEndDatetv;
    TextInputEditText tvEnterCapicity;

    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        textViewDate = findViewById(R.id.createEvent_enterDateTv);
        tvEnterCapicity = findViewById(R.id.createEventEnterCapicityEdt);

        createEventStartDateTv = findViewById(R.id.createEventStartDateTv);
        createEventEndDatetv = findViewById(R.id.createEventEndDatetv);

        startBusinessHourTv = findViewById(R.id.createEvent_selectstrtbsnshourTv);
        endBusinessHourTv = findViewById(R.id.createEvent_selectendbsnshourtv);
        startBusinessHourTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);


                TimePickerDialog timePickerDialog = new TimePickerDialog(CreateEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {

                            startBusinessHourTv.setText(hourOfDay+ ":"+ minute);
                            startBusinessHourTv.setPadding(20,0,70,0);
                    }
                }, mHour,mMinute,true);
                timePickerDialog.show();
            }
        });

        endBusinessHourTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(CreateEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        endBusinessHourTv.setText(hourOfDay + ":" + minute);
                        endBusinessHourTv.setPadding(20,0,70,0);
                    }
                },mHour,mMinute,true);
                timePickerDialog.show();
            }
        });

        textViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
//                Calendar c = Calendar.getInstance();
//                mYear = c.get(Calendar.YEAR);
//                mMonth = c.get(Calendar.MONTH);
//                mDay = c.get(Calendar.DAY_OF_MONTH);
//
//                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateEventActivity.this, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
//                        textViewDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
//                    }
//                },mYear,mMonth,mDay);
//                datePickerDialog.show();
            }
        });

        createEventStartDateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateEventActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        createEventStartDateTv.setText(dayOfMonth + "-" + (monthOfYear+1) + "-" + year  );
                        createEventStartDateTv.setPadding(20,0,20,0);
                    }
                },mYear,mMonth,mDay);
                datePickerDialog.show();


            }
        });


        createEventEndDatetv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateEventActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        createEventEndDatetv.setText(dayOfMonth + "-" + (monthOfYear+1) + "-" + year);
                        createEventEndDatetv.setPadding(20,0,20,0);
                    }
                },mYear,mMonth,mDay);
                datePickerDialog.show();

            }
        });



        spinnerLocation = findViewById(R.id.createEvent_LocationSpinner);

        List<String> list = new ArrayList<String>();
        list.add("Select Location");
        list.add("Texas");
        list.add("California");
        list.add("India");
        list.add("Canada");
        list.add("Australia");
        list.add("Brazil");

        ArrayAdapter adapter = new ArrayAdapter(CreateEventActivity.this,android.R.layout.simple_spinner_item,list);
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
}
