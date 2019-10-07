package com.netscape.utrain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.athlete.AthleteHomeScreen;
import com.netscape.utrain.activities.athlete.AthleteLoginActivity;
import com.netscape.utrain.activities.organization.OrgHomeScreen;
import com.netscape.utrain.activities.organization.OrganizationSignUpActivity;
import com.netscape.utrain.databinding.ActivityLoginBinding;
import com.netscape.utrain.model.LoginRoleModel;
import com.netscape.utrain.response.LoginResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PrefrenceConstant;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityLoginBinding binding;
    private String email, password;
    private Retrofitinterface retrofitinterface;
    private LoginRoleModel loginRoleModel;
    private ProgressDialog progressDialog;
    private boolean doubleBackToExitPressedOnce=false;
    private String activeUserType="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_login);
        if( getIntent().getExtras() != null)
        {
           activeUserType=getIntent().getStringExtra(Constants.ActiveUserType);
        }
        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading));
        init();
    }

    private void init() {
        binding.loginBtn.setOnClickListener(this);
        binding.loginForgetTv.setOnClickListener(this);
        binding.loginSignUpTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.loginBtn:
                getLoginData();
//                Intent homeScreen=new Intent(LoginActivity.this, AthleteHomeScreen.class);
//                startActivity(homeScreen);
                break;
            case R.id.loginForgetTv:
                Intent forgetActivity=new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                forgetActivity.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(forgetActivity);
                break;
            case R.id.loginSignUpTv:
                SelectedServiceList.getInstance().getList().clear();
                Constants.CHECKBOX_IS_CHECKED=0;
                if (activeUserType.equals(Constants.TypeCoach)){
                    Intent signUpActivity=new Intent(LoginActivity.this, OrganizationSignUpActivity.class);
                    signUpActivity.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    signUpActivity.putExtra(Constants.ActiveUserType,Constants.TypeCoach);
                    startActivity(signUpActivity);
                }
                if (activeUserType.equals(Constants.TypeOrganization)){
                    Intent signUpActivity=new Intent(LoginActivity.this, OrganizationSignUpActivity.class);
                    signUpActivity.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    signUpActivity.putExtra(Constants.ActiveUserType,Constants.TypeOrganization);
                    startActivity(signUpActivity);
                }
                break;
        }
    }

    private void getLoginData() {

        email = binding.loginEmailEdt.getText().toString();
        password = binding.loginPasswordEdt.getText().toString();


        if (binding.loginEmailEdt.getText().toString().isEmpty()) {
            binding.loginEmailEdt.setError(getResources().getString(R.string.enter_your_email));
            binding.loginEmailEdt.requestFocus();
        }else if (! Patterns.EMAIL_ADDRESS.matcher(binding.loginEmailEdt.getText().toString()).matches()){
            binding.loginEmailEdt.setError(getResources().getString(R.string.enter_valid_email));
            binding.loginEmailEdt.requestFocus();
        }else if (binding.loginPasswordEdt.getText().toString().isEmpty()) {
            binding.loginPasswordEdt.setError(getResources().getString(R.string.enter_your_password));
            binding.loginPasswordEdt.requestFocus();
        }else {
            hitLoginApi();
        }
    }

    private void hitLoginApi() {
        progressDialog.show();
        Call<LoginResponse> signUpAthlete = retrofitinterface.userLogin(email,password,Constants.DEVICE_TYPE,Constants.DEVICE_TOKEN,Constants.CONTENT_TYPE);
        signUpAthlete.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData() != null) {
                            CommonMethods.setPrefData(PrefrenceConstant.USER_EMAIL, response.body().getData().getUser().getEmail(), LoginActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.USER_PHONE, response.body().getData().getUser().getPhone(), LoginActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.USER_NAME, response.body().getData().getUser().getName(), LoginActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.USER_ID, response.body().getData().getUser().getId()+"", LoginActivity.this);
                            CommonMethods.setPrefData(Constants.AUTH_TOKEN, response.body().getData().getToken() + "", LoginActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.LOGED_IN_USER, PrefrenceConstant.ORG_LOG_IN,LoginActivity.this);
                            Intent homeScreen= new Intent(getApplicationContext(), OrgHomeScreen.class);
                            homeScreen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(homeScreen);
                        }
                    } else {
                        Snackbar.make(binding.loginLayout,response.body().getError().getError_message().getMessage().toString(), BaseTransientBottomBar.LENGTH_LONG).show();
                    }
                } else {
                    progressDialog.dismiss();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);
                        Snackbar.make(binding.loginLayout,errorMessage.toString(), BaseTransientBottomBar.LENGTH_LONG).show();

                    } catch (Exception e) {
                        Snackbar.make(binding.loginLayout,e.getMessage().toString(), BaseTransientBottomBar.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Snackbar.make(binding.loginLayout,getResources().getString(R.string.something_went_wrong), BaseTransientBottomBar.LENGTH_LONG).show();


            }
        });
    }

//    @Override
//    public void onBackPressed() {
//
//        if (doubleBackToExitPressedOnce) {
//            super.onBackPressed();
//            return;
//        }
//        this.doubleBackToExitPressedOnce = true;
//        Toast.makeText(this, getResources().getString(R.string.please_click_again_to_exit), Toast.LENGTH_SHORT).show();
//
//        new Handler().postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                doubleBackToExitPressedOnce = false;
//            }
//        }, 2000);
//    }



}
