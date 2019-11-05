package com.netscape.utrain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.Glide;
import com.facebook.common.Common;
import com.netscape.utrain.R;
import com.netscape.utrain.databinding.ActivityMyProfileBinding;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PrefrenceConstant;

public class MyProfile extends AppCompatActivity {
    ActivityMyProfileBinding binding;
    String userName, roleType, email, phoneTv, addressTv, experienceTv, achievementTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_my_profile);
        binding = DataBindingUtil.setContentView(MyProfile.this, R.layout.activity_my_profile);


        // Getting value by shared Preferrence to display....

        userName = CommonMethods.getPrefData(PrefrenceConstant.USER_NAME, MyProfile.this);
        roleType = CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, MyProfile.this);
        email = CommonMethods.getPrefData(PrefrenceConstant.USER_EMAIL, MyProfile.this);
        phoneTv = CommonMethods.getPrefData(PrefrenceConstant.USER_PHONE, MyProfile.this);
        addressTv = CommonMethods.getPrefData(PrefrenceConstant.ADDRESS, MyProfile.this);
        experienceTv = CommonMethods.getPrefData(PrefrenceConstant.USER_EXPERIENCE, MyProfile.this);
        achievementTv = CommonMethods.getPrefData(PrefrenceConstant.USER_ACHIEVE, MyProfile.this);


        // Binding values to the views ....

        binding.profileNameTv.setText(userName);
        binding.userNameTv.setText(userName);
        binding.roleType.setText(roleType);
        binding.emailTv.setText(email);
        binding.phoneTv.setText(phoneTv);
        binding.addressTv.setText(addressTv);
        binding.experienceTv.setText(experienceTv);
        binding.achievementTv.setText(achievementTv);

        // EditProfile Will open the EditProfileActivity....
        binding.editProfileTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent editProfileActivity = new Intent(MyProfile.this, UpdateProfileActivity.class);
                startActivity(editProfileActivity);
            }
        });

        binding.mpBookingBackImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setProfileImage();


    }

    private void setProfileImage() {

        String img = CommonMethods.getPrefData(PrefrenceConstant.PROFILE_IMAGE, MyProfile.this);
        if (!TextUtils.isEmpty(img)) {
            Glide.with(MyProfile.this).load(Constants.IMAGE_BASE_URL + img).thumbnail(Glide.with(MyProfile.this).load(Constants.IMAGE_BASE_URL + Constants.THUMBNAILS + img)).into(binding.myProfileImg);

        }
    }
}
