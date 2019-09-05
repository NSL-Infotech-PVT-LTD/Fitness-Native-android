package com.netscape.utrain.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textview.MaterialTextView;
import com.netscape.utrain.R;

public class SettingsActivity extends AppCompatActivity {

    SwitchMaterial switchMaterial;
    MaterialTextView textViewOn, textViewOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        switchMaterial = findViewById(R.id.settings_Switch);
        textViewOn = findViewById(R.id.switchOn_ON);
        textViewOff = findViewById(R.id.switchOff_OFF);

        switchMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (switchMaterial.isChecked())
                {
                    textViewOn.setVisibility(View.VISIBLE);
                    textViewOff.setVisibility(View.GONE);
                } else
                {
                    textViewOff.setVisibility(View.VISIBLE);
                    textViewOn.setVisibility(View.GONE);
                }

            }
        });


    }
}
