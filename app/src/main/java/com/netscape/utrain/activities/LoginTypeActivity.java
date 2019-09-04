package com.netscape.utrain.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.netscape.utrain.R;

public class LoginTypeActivity extends AppCompatActivity {
    MaterialButton btnScreenTwo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_type);

        btnScreenTwo = findViewById(R.id.btn_material_screenTwo);
        btnScreenTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginTypeActivity.this, BottomNavigation.class);
                startActivity(intent);
            }
        });
    }

}
