package com.netscape.utrain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.athlete.AthleteHomeScreen;
import com.netscape.utrain.activities.coach.CoachDashboard;
import com.netscape.utrain.databinding.ActivityViewProfileBinding;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PrefrenceConstant;

public class ViewProfileActivity extends AppCompatActivity {
    private ActivityViewProfileBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_view_profile);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_view_profile);
        init();

    }

    private void init() {
        binding.coachNameTv.setText(CommonMethods.getPrefData(PrefrenceConstant.USER_NAME, ViewProfileActivity.this));
        binding.coachProfileNameTv.setText(CommonMethods.getPrefData(PrefrenceConstant.USER_NAME, ViewProfileActivity.this));
        binding.coachEmailTv.setText(CommonMethods.getPrefData(PrefrenceConstant.USER_EMAIL, ViewProfileActivity.this));
        binding.coachPhoneTv.setText(CommonMethods.getPrefData(PrefrenceConstant.USER_PHONE, ViewProfileActivity.this));
        binding.coachAddressTv.setText(CommonMethods.getPrefData(PrefrenceConstant.ADDRESS, ViewProfileActivity.this));
        binding.coachExperienceTv.setText(CommonMethods.getPrefData(PrefrenceConstant.USER_EXPERIENCE, ViewProfileActivity.this));
        binding.coachAchiveMentsTv.setText(CommonMethods.getPrefData(PrefrenceConstant.USER_TRAINING_DETAIL, ViewProfileActivity.this));
        Glide.with(ViewProfileActivity.this).load(CommonMethods.getPrefData(PrefrenceConstant.PROFILE_IMAGE, ViewProfileActivity.this)).into(binding.coachProfileImg);
        binding.coachProfileBackImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
