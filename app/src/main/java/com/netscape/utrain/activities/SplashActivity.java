package com.netscape.utrain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.netscape.utrain.R;
import com.netscape.utrain.databinding.ActivitySplashBinding;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.PrefrenceConstant;

public class SplashActivity extends AppCompatActivity {
    private ActivitySplashBinding binding;
    private int SPLASH_DISPLAY_LENGTH=2000;
    private String userEmail="",userMobile="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_splash);
        userEmail = CommonMethods.getPrefData(PrefrenceConstant.USER_EMAIL, getApplicationContext());
        userMobile = CommonMethods.getPrefData(PrefrenceConstant.USER_PHONE, getApplicationContext());

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                if (!TextUtils.isEmpty(userEmail) && !TextUtils.isEmpty(userMobile)) {
                    Intent intent = new Intent(getApplicationContext(), BottomNavigation.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent mainIntent = new Intent(SplashActivity.this, SignUpTypeActivity.class);
                    startActivity(mainIntent);
                    finish();
                }

            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
