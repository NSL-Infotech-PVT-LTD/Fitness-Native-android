package com.netscape.utrain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.athlete.AthleteHomeScreen;
import com.netscape.utrain.activities.athlete.AthleteLoginActivity;
import com.netscape.utrain.activities.coach.CoachDashboard;
import com.netscape.utrain.activities.organization.OrgHomeScreen;
import com.netscape.utrain.activities.organization.OrganizationSignUpActivity;
import com.netscape.utrain.databinding.ActivityLoginBinding;
import com.netscape.utrain.model.LoginChildModel;
import com.netscape.utrain.model.LoginRoleModel;
import com.netscape.utrain.model.ServiceIdModel;
import com.netscape.utrain.response.LoginResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PrefrenceConstant;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityLoginBinding binding;
    private String email, password;
    private Retrofitinterface retrofitinterface;
    private LoginRoleModel loginRoleModel;
    private ProgressDialog progressDialog;
    private boolean doubleBackToExitPressedOnce = false;
    private String activeUserType = "";
    private List<ServiceIdModel> servicesList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        if (getIntent().getExtras() != null) {
            activeUserType = getIntent().getStringExtra(Constants.ActiveUserType);
        }
        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);
        progressDialog = new ProgressDialog(this);
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
        switch (view.getId()) {
            case R.id.loginBtn:
                getLoginData();
//                Intent homeScreen=new Intent(LoginActivity.this, AthleteHomeScreen.class);
//                startActivity(homeScreen);
                break;
            case R.id.loginForgetTv:
                Intent forgetActivity = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                forgetActivity.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(forgetActivity);
                break;
            case R.id.loginSignUpTv:
                PortfolioActivity.clearFromConstants();
                SelectedServiceList.getInstance().getList().clear();
                Constants.CHECKBOX_IS_CHECKED = 0;
                if (activeUserType.equals(Constants.TypeCoach)) {
                    CommonMethods.setPrefData(PrefrenceConstant.SPORT_NAME, "", getApplicationContext());
                    Intent signUpActivity = new Intent(LoginActivity.this, OrganizationSignUpActivity.class);
                    signUpActivity.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    signUpActivity.putExtra(Constants.ActiveUserType, Constants.TypeCoach);
                    startActivity(signUpActivity);
                }
                if (activeUserType.equals(Constants.TypeOrganization)) {

                    Intent signUpActivity = new Intent(LoginActivity.this, OrganizationSignUpActivity.class);
                    signUpActivity.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    signUpActivity.putExtra(Constants.ActiveUserType, Constants.TypeOrganization);
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
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.loginEmailEdt.getText().toString()).matches()) {
            binding.loginEmailEdt.setError(getResources().getString(R.string.enter_valid_email));
            binding.loginEmailEdt.requestFocus();
        } else if (binding.loginPasswordEdt.getText().toString().isEmpty()) {
            binding.loginPasswordEdt.setError(getResources().getString(R.string.enter_your_password));
            binding.loginPasswordEdt.requestFocus();
        } else {
            hitLoginApi();
        }
    }

    private void hitLoginApi() {
        progressDialog.show();
        Call<LoginResponse> signUpAthlete = retrofitinterface.userLogin(email, password, Constants.DEVICE_TYPE, CommonMethods.getPrefData(PrefrenceConstant.DEVICE_TOKEN, getApplicationContext()), Constants.CONTENT_TYPE);
        signUpAthlete.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData() != null) {
                            Intent homeScreen = null;
                            for (int i = 0; i < response.body().getData().getUser().getRoles().size(); i++) {
                                String role = response.body().getData().getUser().getRoles().get(i).getName();
                                if (role.equalsIgnoreCase(Constants.Organizer)) {
                                    CommonMethods.setPrefData(PrefrenceConstant.ROLE_PLAY, role, LoginActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.USER_EMAIL, response.body().getData().getUser().getEmail(), LoginActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.USER_PHONE, response.body().getData().getUser().getPhone(), LoginActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.USER_NAME, response.body().getData().getUser().getName(), LoginActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.USER_ID, response.body().getData().getUser().getId() + "", LoginActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.ADDRESS, response.body().getData().getUser().getLocation() + "", LoginActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.EXPERTISE_YEAR, response.body().getData().getUser().getExpertise_years() + "", LoginActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.USER_EXPERIENCE, response.body().getData().getUser().getExperience_detail() + "", LoginActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.USER_TRAINING_DETAIL, response.body().getData().getUser().getTraining_service_detail() + "", LoginActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.BUSINESS_HOUR_START, response.body().getData().getUser().getBusiness_hour_starts() + "", LoginActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.PROFILE_IMAGE, Constants.ORG_IMAGE_BASE_URL + response.body().getData().getUser().getProfile_image() + "", LoginActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.BIO, response.body().getData().getUser().getBio() + "", LoginActivity.this);
                                    CommonMethods.setPrefData(Constants.AUTH_TOKEN, response.body().getData().getToken(), LoginActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.LOGED_IN_USER, PrefrenceConstant.ORG_LOG_IN, LoginActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.PRICE, response.body().getData().getUser().getHourly_rate() + "", LoginActivity.this);
                                    servicesList.addAll(response.body().getData().getUser().getService_ids());
                                    storeServiceIds(servicesList);
                                    homeScreen = new Intent(getApplicationContext(), OrgHomeScreen.class);
                                    homeScreen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(homeScreen);
                                } else if (role.equalsIgnoreCase(Constants.Coach)) {
                                    CommonMethods.setPrefData(PrefrenceConstant.ROLE_PLAY, role, LoginActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.USER_EMAIL, response.body().getData().getUser().getEmail(), LoginActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.USER_PHONE, response.body().getData().getUser().getPhone(), LoginActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.USER_NAME, response.body().getData().getUser().getName(), LoginActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.USER_ID, response.body().getData().getUser().getId() + "", LoginActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.EXPERTISE_YEAR, response.body().getData().getUser().getExpertise_years() + "", LoginActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.ADDRESS, response.body().getData().getUser().getLocation() + "", LoginActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.PROFILE_IMAGE, Constants.COACH_IMAGE_BASE_URL + response.body().getData().getUser().getProfile_image() + "", LoginActivity.this);
                                    CommonMethods.setPrefData(Constants.AUTH_TOKEN, response.body().getData().getToken(), LoginActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.USER_EXPERIENCE, response.body().getData().getUser().getExperience_detail() + "", LoginActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.USER_TRAINING_DETAIL, response.body().getData().getUser().getTraining_service_detail() + "", LoginActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.BUSINESS_HOUR_START, response.body().getData().getUser().getBusiness_hour_starts() + "", LoginActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.LOGED_IN_USER, PrefrenceConstant.COACH_LOG_IN, LoginActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.BIO, response.body().getData().getUser().getBio() + "", LoginActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.SPORTS_NAME, response.body().getData().getUser().getSport_id(), getApplicationContext());
                                    CommonMethods.setPrefData(PrefrenceConstant.PRICE, response.body().getData().getUser().getHourly_rate() + "", LoginActivity.this);
                                    servicesList.addAll(response.body().getData().getUser().getService_ids());
                                    storeServiceIds(servicesList);

                                    homeScreen = new Intent(getApplicationContext(), CoachDashboard.class);
                                    homeScreen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(homeScreen);
                                } else
                                    Toast.makeText(LoginActivity.this, "You can't access this", Toast.LENGTH_SHORT).show();

                            }
                        }
                    } else {
                        Snackbar.make(binding.loginLayout, response.body().getError().getError_message().getMessage().toString(), BaseTransientBottomBar.LENGTH_LONG).show();
                    }
                } else {
                    progressDialog.dismiss();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        JSONArray errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message");
                        String errorMsg = errorMessage.getJSONObject(0).getString("message");
                        Snackbar.make(binding.loginLayout, errorMsg, BaseTransientBottomBar.LENGTH_LONG).show();

                    } catch (Exception e) {
                        Snackbar.make(binding.loginLayout, e.getMessage(), BaseTransientBottomBar.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Snackbar.make(binding.loginLayout, getResources().getString(R.string.something_went_wrong), BaseTransientBottomBar.LENGTH_LONG).show();
                progressDialog.dismiss();

            }
        });
    }

    private void storeServiceIds(List<ServiceIdModel> list) {
        Gson gson = new Gson();
        String listData = gson.toJson(list);
        CommonMethods.setPrefData(PrefrenceConstant.SERVICE_IDS, listData, getApplicationContext());
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
