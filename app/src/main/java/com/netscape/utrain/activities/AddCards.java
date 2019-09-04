package com.netscape.utrain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.netscape.utrain.R;
import com.netscape.utrain.databinding.ActivityAddCardsBinding;

public class AddCards extends AppCompatActivity {
    ActivityAddCardsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_add_cards);
    }
}
