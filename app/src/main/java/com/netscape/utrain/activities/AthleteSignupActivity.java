package com.netscape.utrain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.netscape.utrain.R;
import com.netscape.utrain.databinding.ActivityAthleteSignupBinding;

public class AthleteSignupActivity extends AppCompatActivity implements View.OnClickListener{
    private ActivityAthleteSignupBinding binding;
    TextInputEditText etName, etEmail, etPhone, etAddress, etPassword;
    MaterialButton btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_athlete_signup);

        etName = findViewById(R.id.athleteNameEdt);
        etEmail = findViewById(R.id.athleteEmailEdt);
        etPhone = findViewById(R.id.athletePhoneEdt);
        etAddress = findViewById(R.id.athleteAddressEdt);
        etPassword = findViewById(R.id.athletePasswordEdt);
        btnSignUp = findViewById(R.id.athleteSignUpBtn);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // SignupButton Coding....

                String name= etName.getText().toString();
                String email = etEmail.getText().toString();
                String phone = etPhone.getText().toString();
                String address = etAddress.getText().toString();
                String password = etPassword.getText().toString();

                if (name.equals(""))
                {
                    etName.setError("Please enter name");
                } else if (email.equals(""))
                {
                    etEmail.setError("Please enter email");
                } else if (phone.equals(""))
                {
                    etPhone.setError("Please enter phone number");
                } else if (address.equals(""))
                {
                    etAddress.setError("Please enter address");
                } else if (password.equals(""))
                {
                    etPassword.setError("Please enter password");
                } else
                {
                    callingRegistrationApi();
                }
            }
        });


        binding= DataBindingUtil.setContentView(this,R.layout.activity_athlete_signup);
        init();
    }

    private void callingRegistrationApi() {


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
