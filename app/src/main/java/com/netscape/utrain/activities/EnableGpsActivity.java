package com.netscape.utrain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.netscape.utrain.R;
import com.netscape.utrain.activities.athlete.AthleteHomeScreen;
import com.netscape.utrain.databinding.ActivityEnableGpsBinding;

public class EnableGpsActivity extends AppCompatActivity implements View.OnClickListener{
    private ActivityEnableGpsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enable_gps);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_enable_gps);
        init();
    }

    private void init() {
        binding.gpsSwitch.setOnClickListener(this);
        binding.saveBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.gpsSwitch:

                if (binding.gpsSwitch.isChecked())
                {
                    binding.tvOn.setVisibility(View.VISIBLE);
                    binding.tvOff.setVisibility(View.GONE);


                } else
                {
                    binding.tvOff.setVisibility(View.VISIBLE);
                    binding.tvOn.setVisibility(View.GONE);
                }
                break;
            case R.id.saveBtn:
                Intent intent=new Intent(EnableGpsActivity.this, AthleteHomeScreen.class);
                startActivity(intent);
                break;
        }
    }
}
