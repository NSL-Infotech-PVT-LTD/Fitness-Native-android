package com.netscape.utrain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.netscape.utrain.R;
import com.netscape.utrain.databinding.ActivityLoginTypeBinding;
import com.netscape.utrain.utils.Constants;

public class SignUpTypeActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityLoginTypeBinding binding;
    private String signUpAs="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_login_type);
        init();

    }

    private void init() {
        binding.coachCardView.setOnClickListener(this);
        binding.athleteCardView.setOnClickListener(this);
        binding.organizationCardView.setOnClickListener(this);
        binding.findCoachesBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.athleteCardView:
                signUpAs= Constants.Athlete;
                binding.coachLayout.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                binding.organizationLayout.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                binding.athleteLayout.setBackground(getResources().getDrawable(R.drawable.mask_athlete));
                binding.frameAthlete.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                break;
            case R.id.coachCardView:
                signUpAs=Constants.Coach;
                binding.athleteLayout.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                binding.organizationLayout.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                binding.coachLayout.setBackground(getResources().getDrawable(R.drawable.mask_coach));
                binding.frameCoach.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                break;
            case R.id.organizationCardView:
                signUpAs=Constants.Organization;
                binding.organizationLayout.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                binding.coachLayout.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                binding.organizationLayout.setBackground(getResources().getDrawable(R.drawable.mask_org));
                binding.frameOrg.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                break;
            case R.id.findCoachesBtn:
                if (signUpAs.equals(Constants.Athlete)){
                    Intent athleteSignUp = new Intent(SignUpTypeActivity.this, AthleteLoginActivity.class);
                    startActivity(athleteSignUp);
                }
                if (signUpAs.equals(Constants.Coach)){
                    Intent coachSignUp = new Intent(SignUpTypeActivity.this, CoachSignupActivity.class);
                    startActivity(coachSignUp);
                }
                if (signUpAs.equals(Constants.Organization)){
                    Intent organizationSignUp = new Intent(SignUpTypeActivity.this, OrganizationSignUpActivity.class);
                    startActivity(organizationSignUp);
                }

                break;
        }
    }
}
