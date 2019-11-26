package com.netscape.utrain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import com.netscape.utrain.model.ServiceListDataModel;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PrefrenceConstant;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {
    ActivitySettingsBinding binding;
    SwitchMaterial switchMaterial;
    MaterialTextView textViewOn, textViewOff;
    private int IMAGE_GET = 166;

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


        if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, SettingsActivity.this).equalsIgnoreCase(Constants.Athlete)) {
            binding.cAddedFieldLayout.setVisibility(View.GONE);
        } else if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, SettingsActivity.this).equalsIgnoreCase(Constants.Organizer)) {
            binding.chooseSportsTv.setText("Portfolio images");
//            binding.chooseSportsClickImg.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    startActivity(new Intent(SettingsActivity.this, PortfolioActivity.class));
//                }
//            });

        }


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
                OrganizationSignUpActivity.update = true;
                startActivity(new Intent(SettingsActivity.this, OrganizationSignUpActivity.class));
                break;
            case R.id.chooseSportsClickImg:
                if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, SettingsActivity.this).equalsIgnoreCase(Constants.Organizer)) {
                    PortfolioActivity.clearFromConstants();
                    Intent getImages = new Intent(SettingsActivity.this, PortfolioActivity.class);
                    getImages.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    PortfolioActivity.updateImages = true;
                    getImages.putExtra("updateEventImg", CommonMethods.getPrefData(PrefrenceConstant.PORT_FOLIO_IMAGES, SettingsActivity.this));
                    getImages.putExtra("updateImgType", "portfolioImages");
                    startActivityForResult(getImages, IMAGE_GET);
                } else {
                    CommonMethods.setPrefData(PrefrenceConstant.SPORT_NAME, "", getApplicationContext());
                    ChooseSportActivity.athUpdate = true;
                    startActivity(new Intent(SettingsActivity.this, ChooseSportActivity.class));
                }
                break;
            case R.id.chooseServicesClickimg:
                SelectedServiceList.getInstance().getList().clear();
                ArrayList<ServiceListDataModel> list = new ArrayList<>();
                CommonMethods.setLisstPrefData(Constants.SERVICE_LIST, list, SettingsActivity.this);
                ServicePriceActivity.updateServices = true;
                startActivity(new Intent(SettingsActivity.this, ServicePriceActivity.class));
                break;
        }


    }
}
