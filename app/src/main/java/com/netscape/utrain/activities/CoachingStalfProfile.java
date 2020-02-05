package com.netscape.utrain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.netscape.utrain.R;
import com.netscape.utrain.databinding.ActivityCoachingStalfProfileBinding;
import com.netscape.utrain.model.ViewCoachListDataModel;
import com.netscape.utrain.utils.Constants;

public class CoachingStalfProfile extends AppCompatActivity {
    private ActivityCoachingStalfProfileBinding binding;
    ViewCoachListDataModel data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_coaching_stalf_profile);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_coaching_stalf_profile);
        if (getIntent().getExtras()!=null){
            data= (ViewCoachListDataModel) getIntent().getSerializableExtra("stalfInfo");
        }
        setdata();
        binding.mpBookingBackImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void setdata() {
        Glide.with(getApplicationContext()).load(Constants.ORG_COACH_IMAGE_BASE_URL + data.getProfile_image()).thumbnail(Glide.with(getApplicationContext()).load(Constants.COACH_IMAGE_BASE_URL + Constants.THUMBNAILS + data.getProfile_image())).into(binding.myProfileImg);
        binding.userNameTv.setText(data.getName());
        binding.emailTv.setText(data.getBio());
        binding.phoneTv.setText(data.getProfession());
        binding.addressTv.setText(data.getTraining_service_detail());
        binding.experienceTv.setText(data.getExpertise_years()+"year");
        binding.achievementTv.setText("$ " +data.getHourly_rate());
    }
}
