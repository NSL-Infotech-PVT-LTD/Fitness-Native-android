package com.netscape.utrain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.netscape.utrain.R;
import com.netscape.utrain.databinding.ActivityCoachLoginBinding;
import com.netscape.utrain.databinding.ActivityCoachSignupBinding;

public class CoachSignupActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityCoachSignupBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_signup);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_coach_signup);
        init();
    }

    private void init() {

    }

    @Override
    public void onClick(View view) {

    }
}
