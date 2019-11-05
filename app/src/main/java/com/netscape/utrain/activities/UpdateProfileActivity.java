package com.netscape.utrain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.netscape.utrain.R;
import com.netscape.utrain.databinding.ActivityUpdateProfileBinding;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.PrefrenceConstant;

public class UpdateProfileActivity extends AppCompatActivity {

    ActivityUpdateProfileBinding binding;
    String uNameTv,uEmailTv,uPhoneEdt, uAddressEdt,uExperienceEdt,uAchievementEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_update_profile);
        binding = DataBindingUtil.setContentView(UpdateProfileActivity.this, R.layout.activity_update_profile);


        // Getting value by shared Preferrence to display....

        uNameTv = CommonMethods.getPrefData(PrefrenceConstant.USER_NAME,UpdateProfileActivity.this);
        uEmailTv = CommonMethods.getPrefData(PrefrenceConstant.USER_EMAIL,UpdateProfileActivity.this);
        uPhoneEdt = CommonMethods.getPrefData(PrefrenceConstant.USER_PHONE, UpdateProfileActivity.this);
        uAddressEdt = CommonMethods.getPrefData(PrefrenceConstant.ADDRESS, UpdateProfileActivity.this);
        uExperienceEdt = CommonMethods.getPrefData(PrefrenceConstant.USER_EXPERIENCE, UpdateProfileActivity.this);
        uAchievementEdt = CommonMethods.getPrefData(PrefrenceConstant.USER_ACHIEVE, UpdateProfileActivity.this);





        // Binding values to the views ....
        binding.uNameTv.setText(uNameTv);
        binding.uEmailTv.setText(uEmailTv);
        binding.uPhoneEdt.setText(uPhoneEdt);
        binding.uAddressEdt.setText(uAddressEdt);
        binding.uExperienceEdt.setText(uExperienceEdt);
        binding.uAchievementEdt.setText(uAchievementEdt);



    }
}
