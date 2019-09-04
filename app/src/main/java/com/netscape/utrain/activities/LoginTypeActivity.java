package com.netscape.utrain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.netscape.utrain.R;
import com.netscape.utrain.databinding.ActivityLoginTypeBinding;

public class LoginTypeActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityLoginTypeBinding binding;
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
                binding.coachLayout.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                binding.organizationLayout.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                binding.athleteLayout.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                break;
            case R.id.coachCardView:
                binding.athleteLayout.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                binding.organizationLayout.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                binding.coachLayout.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                break;
            case R.id.organizationCardView:
                binding.organizationLayout.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                binding.coachLayout.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                binding.organizationLayout.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                break;
            case R.id.findCoachesBtn:
                Intent intent = new Intent(LoginTypeActivity.this, EnableGpsActivity.class);
                startActivity(intent);
                break;
        }
    }
}
