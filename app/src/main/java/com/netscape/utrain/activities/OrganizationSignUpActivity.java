package com.netscape.utrain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TimePicker;

import com.google.android.material.textview.MaterialTextView;
import com.netscape.utrain.R;
import com.netscape.utrain.adapters.SelectServiceSpinnerAdapter;
import com.netscape.utrain.model.OrgSpinnerCheckBoxModel;

import java.util.ArrayList;
import java.util.Calendar;

public class OrganizationSignUpActivity extends AppCompatActivity {

    MaterialTextView startTime, endTime;

    private int mYear, mMonth, mDay, mHour, mMinute;

    final String[] select_qualification = {
            "Select Qualification", "10th / Below", "12th", "Diploma", "UG",
            "PG", "Phd"};
    AppCompatSpinner selectServSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_sign_up);


        startTime = findViewById(R.id.organization_startTimeTv);
        endTime = findViewById(R.id.organization_endTimeTv);

        selectServSpinner = findViewById(R.id.organization_SelectServiceSpinner);

        ArrayList<OrgSpinnerCheckBoxModel> list = new ArrayList<>();
        for (int i = 0; i < select_qualification.length ; i++) {

            OrgSpinnerCheckBoxModel model = new OrgSpinnerCheckBoxModel();
            model.setTitle(select_qualification[i]);
            model.setSelected(false);
            list.add(model);
        }

        SelectServiceSpinnerAdapter adapter = new SelectServiceSpinnerAdapter(OrganizationSignUpActivity.this,0,list);
        selectServSpinner.setAdapter(adapter);

        selectServSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectServSpinner.setSelection(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // StartBusinessHours Textview Code....

                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(OrganizationSignUpActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {

                                startTime.setText(hourOfDay + ":" + minute );
                            }
                        }, mHour,mMinute,true);
                timePickerDialog.show();

            }
        });


        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // EndBusinessHours Textview Code....

                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(OrganizationSignUpActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {

                                endTime.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour,mMinute,true);
                timePickerDialog.show();



            }
        });

    }
}
