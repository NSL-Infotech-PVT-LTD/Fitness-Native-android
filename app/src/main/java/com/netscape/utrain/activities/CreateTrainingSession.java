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

import com.google.android.material.textview.MaterialTextView;
import com.netscape.utrain.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CreateTrainingSession extends AppCompatActivity {

    AppCompatSpinner spinnerLocation;

    MaterialTextView tvStartBsnsHour, tvEndBsnsHour, tvSelectDate;

    int mYear, mMonth, mDay, mHour, mMinute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_training_session);

        tvStartBsnsHour = findViewById(R.id.createTrainingSessionSelectStartbsnsHourTv);
        tvEndBsnsHour = findViewById(R.id.createTrainingSessionSelectEndbsnsHourTv);
        tvSelectDate = findViewById(R.id.createTrainingSessionEnterDateTv);


        tvStartBsnsHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(CreateTrainingSession.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {

                        tvStartBsnsHour.setText(hourOfDay + ":" + minute);
                    }
                },mHour,mMinute,true);
                timePickerDialog.show();
            }
        });

        tvEndBsnsHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(CreateTrainingSession.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {

                        tvEndBsnsHour.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute,true);
                timePickerDialog.show();
            }
        });

        tvSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateTrainingSession.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        tvSelectDate.setText(dayOfMonth + "-" + (month +1) + "-" + year);
                    }
                }, mDay, mMonth, mYear);
                datePickerDialog.show();
            }
        });

        spinnerLocation = findViewById(R.id.createTrainingSessionLocationSpinner);

        List<String> list = new ArrayList<String>();
        list.add("Select Location");
        list.add("Texas");
        list.add("America");
        list.add("Australia");
        list.add("England");
        list.add("India");
        list.add("Brazil");

        ArrayAdapter adapter = new ArrayAdapter(CreateTrainingSession.this,android.R.layout.simple_spinner_item,list);

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
