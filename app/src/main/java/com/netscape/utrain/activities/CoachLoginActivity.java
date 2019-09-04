package com.netscape.utrain.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.material.textview.MaterialTextView;
import com.netscape.utrain.R;

public class CoachLoginActivity extends AppCompatActivity {
    MaterialTextView tvSignUp;
    private LoginButton btnFacaebookLogin;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_login);

        tvSignUp = findViewById(R.id.tv_SignUp);
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CoachLoginActivity.this, BottomNavigation.class);
                startActivity(intent);
            }
        });

        btnFacaebookLogin = findViewById(R.id.btn_facebook);
        callbackManager = CallbackManager.Factory.create();
        btnFacaebookLogin.setReadPermissions("email", "public_profile");
        btnFacaebookLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

    }
}
