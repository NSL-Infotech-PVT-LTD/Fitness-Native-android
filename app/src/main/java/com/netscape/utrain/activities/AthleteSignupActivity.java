package com.netscape.utrain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;
import com.netscape.utrain.R;
import com.netscape.utrain.databinding.ActivityAthleteSignupBinding;

public class AthleteSignupActivity extends AppCompatActivity implements View.OnClickListener{
    private ActivityAthleteSignupBinding binding;
    TextInputEditText etName, etEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_athlete_signup);

        etName = findViewById(R.id.athleteNameEdt);

        binding= DataBindingUtil.setContentView(this,R.layout.activity_athlete_signup);
        init();
    }

    private void init() {
        binding.athleteSignUpBtn.setOnClickListener(this);
        binding.athleteSignInTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.athleteSignUpBtn:
                getSignUpData();
                Intent mainActivity=new Intent(AthleteSignupActivity.this,BottomNavigation.class);
                startActivity(mainActivity);
                break;
            case R.id.athleteSignInTv:
                Intent signInActivity=new Intent(AthleteSignupActivity.this,LoginActivity.class);
                startActivity(signInActivity);
                break;
        }
    }

    private void getSignUpData() {
        if (binding.athleteNameEdt.getText().toString().isEmpty()){
            binding.athleteNameEdt.setError(getString(R.string.enter_name));
        }else if (binding.athleteEmailEdt.getText().toString().isEmpty()){
                  binding.athleteEmailEdt.setError(getString(R.string.enter_your_email));
        }else if (binding.athletePhoneEdt.getText().toString().isEmpty()){
                  binding.athletePhoneEdt.setError(getString(R.string.enter_phone_number));
        }else if (binding.athletePasswordEdt.getText().toString().isEmpty()){
                  binding.athletePasswordEdt.setError(getString(R.string.enter_password));
        }else {
            hitAthleteSignUpApi();
        }
    }

    private void hitAthleteSignUpApi() {
    }
}
