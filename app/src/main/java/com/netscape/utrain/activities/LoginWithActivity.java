package com.netscape.utrain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.netscape.utrain.R;
import com.netscape.utrain.databinding.ActivityCoachLoginBinding;

public class LoginWithActivity extends AppCompatActivity implements View.OnClickListener{
    private ActivityCoachLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_coach_login);
        init();

//        tvSignUp = findViewById(R.id.tv_SignUp);
//        tvSignUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(LoginWithActivity.this, BottomNavigation.class);
//                startActivity(intent);
//            }
//        });
//
////        btnFacaebookLogin = findViewById(R.id.btn_facebook);
////        callbackManager = CallbackManager.Factory.create();
////        btnFacaebookLogin.setReadPermissions("email", "public_profile");
////        btnFacaebookLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
////            @Override
////            public void onSuccess(LoginResult loginResult) {
////
////            }
////
////            @Override
////            public void onCancel() {
////
////            }
////
////            @Override
////            public void onError(FacebookException error) {
////
////            }
////        });

    }

    private void init() {
    binding.emailLoginBtn.setOnClickListener(this);
    binding.fbLin.setOnClickListener(this);
    binding.tvSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.emailLoginBtn:
                Intent intent = new Intent(LoginWithActivity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);

                break;
            case R.id.fbLin:
                break;
            case R.id.tvSignUp:
                Intent signUpType = new Intent(LoginWithActivity.this, SignUpTypeActivity.class);
                startActivity(signUpType);
                overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
                break;
        }
    }
}
