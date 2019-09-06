package com.netscape.utrain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.netscape.utrain.R;

import java.util.ArrayList;

public class OrganizationSignUpActivity extends AppCompatActivity {
    AppCompatSpinner spinnerTimeAMPM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_sign_up);

        spinnerTimeAMPM = findViewById(R.id.organization_timeSpinner);

        ArrayList<String> amPMlist = new ArrayList<>();
        amPMlist.add("am");
        amPMlist.add("pm");

        ArrayAdapter timeadapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,amPMlist);
        spinnerTimeAMPM.setAdapter(timeadapter);
        spinnerTimeAMPM.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerTimeAMPM.setSelection(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
