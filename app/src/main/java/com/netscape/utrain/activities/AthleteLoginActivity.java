package com.netscape.utrain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.netscape.utrain.R;
import com.netscape.utrain.databinding.ActivityAthleteLoginBinding;
import com.netscape.utrain.response.LoginResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PrefrenceConstant;

import org.json.JSONObject;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AthleteLoginActivity extends AppCompatActivity {



    TextInputEditText edtEmail, edtPassword;
    MaterialTextView tvSignUp, tvForgetPassword;
    MaterialButton btnLogin;
    String email,password;
    Retrofitinterface retrofitinterface;
    ProgressDialog progressDialog;
    ActivityAthleteLoginBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_athlete_login);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_athlete_login);
        tvSignUp = findViewById(R.id.athleteSignupTv);
        tvForgetPassword = findViewById(R.id.athlete_loginForgetTv);
        btnLogin = findViewById(R.id.athlete_LogInBtn);
        edtEmail = findViewById(R.id.athlete_enterEmailEdt);
        edtPassword = findViewById(R.id.athlete_enterPasswordEdt);
        progressDialog = new ProgressDialog(AthleteLoginActivity.this);
        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AthleteLoginActivity.this, AthleteSignupActivity.class);
                startActivity(intent);
            }
        });

        tvForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AthleteLoginActivity.this,ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // LoginButton Coding....

                email = edtEmail.getText().toString();
                password = edtPassword.getText().toString();


                if (email.equals("")) {
                    edtEmail.setError("Please enter email");
                } else if (!isValidEmailId(email)){     // Email Validation
                    edtEmail.setError("Please enter valid email!");
                } else if (password.equals("")) {
                    edtPassword.setError("Please enter password!");
                } else
                {
                    // hit login api here.....

                    hitLoginApi();

                }
            }
        });
    }

    private void hitLoginApi() {
        progressDialog.show();
        Call<LoginResponse> signUpAthlete = retrofitinterface.userLogin(email,password, Constants.DEVICE_TYPE,Constants.DEVICE_TOKEN,Constants.CONTENT_TYPE);
        signUpAthlete.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData() != null) {
                            CommonMethods.setPrefData(PrefrenceConstant.USER_EMAIL, response.body().getData().getUser().getEmail(), AthleteLoginActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.USER_PHONE, response.body().getData().getUser().getPhone(), AthleteLoginActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.USER_NAME, response.body().getData().getUser().getName(), AthleteLoginActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.USER_ID, response.body().getData().getUser().getId()+"", AthleteLoginActivity.this);
                            Intent homeScreen= new Intent(getApplicationContext(), BottomNavigation.class);
                            homeScreen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(homeScreen);
                        }
                    } else {
                        Snackbar.make(binding.athleteLoginLayout,response.body().getError().getError_message().getMessage().toString(), BaseTransientBottomBar.LENGTH_LONG).show();
                    }
                } else {
                    progressDialog.dismiss();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);
                        Snackbar.make(binding.athleteLoginLayout,errorMessage.toString(), BaseTransientBottomBar.LENGTH_LONG).show();

                    } catch (Exception e) {
                        Snackbar.make(binding.athleteLoginLayout,e.getMessage().toString(), BaseTransientBottomBar.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Snackbar.make(binding.athleteLoginLayout,getResources().getString(R.string.something_went_wrong), BaseTransientBottomBar.LENGTH_LONG).show();


            }
        });
    }

    private boolean isValidEmailId(String email){

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }
}
