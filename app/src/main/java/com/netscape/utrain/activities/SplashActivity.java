package com.netscape.utrain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.netscape.utrain.R;
import com.netscape.utrain.activities.athlete.AthleteHomeScreen;
import com.netscape.utrain.databinding.ActivitySplashBinding;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.PrefrenceConstant;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
                    Intent intent = new Intent(getApplicationContext(), AthleteHomeScreen.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent mainIntent = new Intent(SplashActivity.this, SignUpTypeActivity.class);
                    startActivity(mainIntent);
                    finish();
                }

            }
        }, SPLASH_DISPLAY_LENGTH);
        haskey();


    }

    private void haskey(){
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    getPackageName()    , PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures){
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
