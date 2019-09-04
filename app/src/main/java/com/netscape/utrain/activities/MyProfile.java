package com.netscape.utrain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.netscape.utrain.R;
import com.netscape.utrain.databinding.ActivityMyProfileBinding;

public class MyProfile extends AppCompatActivity {
    ActivityMyProfileBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_my_profile);
    }
}
