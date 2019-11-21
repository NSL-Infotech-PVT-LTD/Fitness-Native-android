package com.netscape.utrain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.Glide;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textview.MaterialTextView;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.athlete.AthleteHomeScreen;
import com.netscape.utrain.activities.athlete.ChooseSportActivity;
import com.netscape.utrain.activities.organization.OrganizationSignUpActivity;
import com.netscape.utrain.databinding.ActivitySettingsBinding;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PrefrenceConstant;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    ActivitySettingsBinding binding;

    SwitchMaterial switchMaterial;
    MaterialTextView textViewOn, textViewOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_settings);
        binding = DataBindingUtil.setContentView(SettingsActivity.this, R.layout.activity_settings);

        switchMaterial = findViewById(R.id.settingsSwitch);
        textViewOn = findViewById(R.id.switchOnON);
        textViewOff = findViewById(R.id.switchOffOFF);
        setProfileImage();
        inIt();
        switchMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (switchMaterial.isChecked()) {
                    textViewOn.setVisibility(View.VISIBLE);
                    textViewOff.setVisibility(View.GONE);
                } else {
                    textViewOff.setVisibility(View.VISIBLE);
                    textViewOn.setVisibility(View.GONE);
                }

            }
        });
        binding.settingsBackImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
        binding.changePasswordTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // change password api hit here....
            }
        });
    }

    private void inIt() {

        binding.basicProfileClickImg.setOnClickListener(this);
        binding.chooseSportsClickImg.setOnClickListener(this);
        binding.chooseServicesClickimg.setOnClickListener(this);

    }

    private void setProfileImage() {

        String img = CommonMethods.getPrefData(PrefrenceConstant.PROFILE_IMAGE, SettingsActivity.this);
        if (!TextUtils.isEmpty(img)) {
            Glide.with(SettingsActivity.this).load(Constants.IMAGE_BASE_URL + img).thumbnail(Glide.with(SettingsActivity.this).load(Constants.IMAGE_BASE_URL + Constants.THUMBNAILS + img)).into(binding.settingsProfileImg);
//            Glide.with(SettingsActivity.this).load(Constants.IMAGE_BASE_URL + img).thumbnail(Glide.with(SettingsActivity.this).load(Constants.IMAGE_BASE_URL + Constants.THUMBNAILS + img)).into(headerImage);
//            navNameTv.setText(CommonMethods.getPrefData(PrefrenceConstant.USER_NAME, SettingsActivity.this));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.basicProfileClickImg:
                startActivity(new Intent(SettingsActivity.this, OrganizationSignUpActivity.class));
                break;
            case R.id.chooseSportsClickImg:
                startActivity(new Intent(SettingsActivity.this, ChooseSportActivity.class));
                break;
            case R.id.chooseServicesClickimg:
                startActivity(new Intent(SettingsActivity.this, ServicePriceActivity.class));
                break;
        }


    }
}
