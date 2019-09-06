package com.netscape.utrain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.netscape.utrain.MainActivity;
import com.netscape.utrain.R;
import com.netscape.utrain.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_login);
        init();
    }

    private void init() {
        binding.loginBtn.setOnClickListener(this);
        binding.loginForgetTv.setOnClickListener(this);
        binding.loginSignUpTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.loginBtn:
                getLoginData();
                Intent homeScreen=new Intent(LoginActivity.this, BottomNavigation.class);
                startActivity(homeScreen);
                overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
                break;
            case R.id.loginForgetTv:
                Intent forgetActivity=new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(forgetActivity);
                overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
                break;
            case R.id.loginSignUpTv:
                Intent signUpActivity=new Intent(LoginActivity.this, SignUpTypeActivity.class);
                startActivity(signUpActivity);
                overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
                break;
        }
    }

    private void getLoginData() {
        if (binding.loginEmailEdt.getText().toString().isEmpty()){
            binding.loginEmailEdt.setError(getString(R.string.enter_your_email));
        }else if (binding.loginPasswordEdt.getText().toString().isEmpty()) {
            binding.loginEmailEdt.setError(getString(R.string.enter_your_email));
        }else {
            hitLoginApi();
        }
    }

    private void hitLoginApi() {
    }
}
