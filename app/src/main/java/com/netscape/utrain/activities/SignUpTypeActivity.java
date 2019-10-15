package com.netscape.utrain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.netscape.utrain.R;
import com.netscape.utrain.activities.athlete.LoginWithActivity;
import com.netscape.utrain.activities.coach.CoachSignupActivity;
import com.netscape.utrain.activities.organization.OrganizationSignUpActivity;
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
        setDefaultSelected();
    }
    public void setDefaultSelected(){
        binding.findCoachesBtn.setText(getResources().getString(R.string.log_in_signup_as_athlete));
        signUpAs= Constants.Athlete;
        binding.coachLayout.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        binding.organizationLayout.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        binding.athleteLayout.setBackground(getResources().getDrawable(R.drawable.mask_athlete));
        binding.frameAthlete.setBackgroundColor(getResources().getColor(R.color.colorGreen));
        binding.iconAthlete.setImageResource(R.drawable.athlete_white);
        binding.iconCoach.setBackground(getResources().getDrawable(R.drawable.coach));
        binding.iconOrganization.setBackground(getResources().getDrawable(R.drawable.company));
        binding.athleteTv.setTextColor(getResources().getColor(R.color.colorWhite));
        binding.coachTv.setTextColor(getResources().getColor(R.color.colorBlack));
        binding.orgTv.setTextColor(getResources().getColor(R.color.colorBlack));
    }


    @Override
    protected void onResume() {
        super.onResume();
        Constants.ROLE_PLAY = "";


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.athleteCardView:
                binding.findCoachesBtn.setText(getResources().getString(R.string.log_in_signup_as_athlete));
                signUpAs= Constants.Athlete;
                Constants.ROLE_PLAY=Constants.Athlete;
                binding.coachLayout.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                binding.organizationLayout.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                binding.athleteLayout.setBackground(getResources().getDrawable(R.drawable.mask_athlete));
                binding.frameAthlete.setBackgroundColor(getResources().getColor(R.color.colorGreen));
//                binding.iconAthlete.setBackground(getResources().getDrawable(R.drawable.athlete_white));
                binding.iconCoach.setBackground(getResources().getDrawable(R.drawable.coach));
                binding.iconOrganization.setBackground(getResources().getDrawable(R.drawable.company));
                binding.athleteTv.setTextColor(getResources().getColor(R.color.colorWhite));
                binding.coachTv.setTextColor(getResources().getColor(R.color.colorBlack));
                binding.orgTv.setTextColor(getResources().getColor(R.color.colorBlack));
                binding.iconAthlete.setImageResource(R.drawable.athlete_white);


                break;
            case R.id.coachCardView:
                signUpAs=Constants.Coach;
                Constants.ROLE_PLAY=Constants.Coach;
                binding.athleteLayout.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                binding.organizationLayout.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                binding.coachLayout.setBackground(getResources().getDrawable(R.drawable.mask_coach));
                binding.frameCoach.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                binding.iconCoach.setBackground(getResources().getDrawable(R.drawable.coach_white));
//                binding.iconAthlete.setBackground(getResources().getDrawable(R.drawable.athlete));
                binding.iconOrganization.setBackground(getResources().getDrawable(R.drawable.company));
                binding.coachTv.setTextColor(getResources().getColor(R.color.colorWhite));
                binding.athleteTv.setTextColor(getResources().getColor(R.color.colorBlack));
                binding.orgTv.setTextColor(getResources().getColor(R.color.colorBlack));
                binding.iconAthlete.setImageResource(R.drawable.athlete_two);
                binding.findCoachesBtn.setText(getResources().getString(R.string.log_in_signup_as_coach));
                break;
            case R.id.organizationCardView:
                signUpAs=Constants.Organization;
                Constants.ROLE_PLAY=Constants.Organization;
                binding.athleteLayout.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                binding.coachLayout.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                binding.organizationLayout.setBackground(getResources().getDrawable(R.drawable.mask_org));
                binding.frameOrg.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                binding.iconOrganization.setBackground(getResources().getDrawable(R.drawable.company_white));
//                binding.iconAthlete.setBackground(getResources().getDrawable(R.drawable.athlete));
                binding.iconCoach.setBackground(getResources().getDrawable(R.drawable.coach));
                binding.orgTv.setTextColor(getResources().getColor(R.color.colorWhite));
                binding.iconAthlete.setImageResource(R.drawable.athlete_two);
                binding.coachTv.setTextColor(getResources().getColor(R.color.colorBlack));
                binding.athleteTv.setTextColor(getResources().getColor(R.color.colorBlack));
                binding.findCoachesBtn.setText(getResources().getString(R.string.log_in_signup_as_org));
                break;
            case R.id.findCoachesBtn:
                if (signUpAs.equals(Constants.Athlete)){
                    Intent athleteSignUp = new Intent(SignUpTypeActivity.this, LoginWithActivity.class);
                    athleteSignUp.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(athleteSignUp);
                }
                if (signUpAs.equals(Constants.Coach)){
                    Intent coachSignUp = new Intent(SignUpTypeActivity.this, LoginActivity.class);
                    coachSignUp.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    coachSignUp.putExtra(Constants.ActiveUserType,Constants.TypeCoach);
                    startActivity(coachSignUp);
                }
                if (signUpAs.equals(Constants.Organization)){
                    Intent organizationSignUp = new Intent(SignUpTypeActivity.this, LoginActivity.class);
                    organizationSignUp.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    organizationSignUp.putExtra(Constants.ActiveUserType,Constants.TypeOrganization);
                    startActivity(organizationSignUp);
                }
                break;
        }
    }
}
