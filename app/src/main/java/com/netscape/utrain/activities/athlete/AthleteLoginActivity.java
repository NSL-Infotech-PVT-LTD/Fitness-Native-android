package com.netscape.utrain.activities.athlete;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.ForgetPasswordActivity;
import com.netscape.utrain.activities.organization.OrgHomeScreen;
import com.netscape.utrain.databinding.ActivityAthleteLoginBinding;
import com.netscape.utrain.model.SportListModel;
import com.netscape.utrain.response.LoginResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PrefrenceConstant;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AthleteLoginActivity extends AppCompatActivity implements View.OnClickListener {
    TextInputEditText edtEmail, edtPassword;
    MaterialTextView tvSignUp, tvForgetPassword;
    MaterialButton btnLogin;
    String email, password, selectedSport;
    Retrofitinterface retrofitinterface;
    ProgressDialog progressDialog;
    ActivityAthleteLoginBinding binding;

    ArrayList<SportListModel.DataBeanX.DataBean> sportList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_athlete_login);
        init();
    }

    private void init() {
        binding.athleteForgetTv.setOnClickListener(this);
        binding.athleteLogInBtn.setOnClickListener(this);
        binding.athleteSignUpTv.setOnClickListener(this);
        progressDialog = new ProgressDialog(AthleteLoginActivity.this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);
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

                            for (int i = 0; i < response.body().getData().getUser().getRoles().size(); i++) {
                                String role = response.body().getData().getUser().getRoles().get(i).getName();
                                if (Constants.Athlete.equalsIgnoreCase(role)) {
                                    CommonMethods.setPrefData(PrefrenceConstant.ROLE_PLAY, role, AthleteLoginActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.USER_EMAIL, response.body().getData().getUser().getEmail(), AthleteLoginActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.USER_PHONE, response.body().getData().getUser().getPhone(), AthleteLoginActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.USER_NAME, response.body().getData().getUser().getName(), AthleteLoginActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.USER_ID, response.body().getData().getUser().getId() + "", AthleteLoginActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.PROFILE_IMAGE, response.body().getData().getUser().getProfile_image() + "", AthleteLoginActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.USER_EXPERIENCE, response.body().getData().getUser().getExperience_detail() + "", AthleteLoginActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.USER_ACHIEVE, response.body().getData().getUser().getAchievements() + "", AthleteLoginActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.SPORTS_NAME, response.body().getData().getUser().getSport_id(), getApplicationContext());
                                    CommonMethods.setPrefData(Constants.AUTH_TOKEN, response.body().getData().getToken() + "", AthleteLoginActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.LOGED_IN_USER, PrefrenceConstant.ATHLETE_LOG_IN, AthleteLoginActivity.this);
                                    Intent homeScreen = null;

                                    homeScreen = new Intent(getApplicationContext(), AthleteHomeScreen.class);
                                    homeScreen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(homeScreen);
                                } else
                                    Toast.makeText(AthleteLoginActivity.this, "You can't access this", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Snackbar.make(binding.athleteLoginLayout, response.body().getError().getError_message().getMessage().toString(), BaseTransientBottomBar.LENGTH_LONG).show();
                    }
                } else {
                    progressDialog.dismiss();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);
                        Snackbar.make(binding.athleteLoginLayout, errorMessage.toString(), BaseTransientBottomBar.LENGTH_LONG).show();

                    } catch (Exception e) {
                        Snackbar.make(binding.athleteLoginLayout, e.getMessage().toString(), BaseTransientBottomBar.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Snackbar.make(binding.athleteLoginLayout, getResources().getString(R.string.something_went_wrong), BaseTransientBottomBar.LENGTH_LONG).show();
                progressDialog.dismiss();

            }
        });
    }

    private void storeSportList(List<SportListModel.DataBeanX.DataBean> list) {
        Gson gson = new Gson();
        String listData = gson.toJson(list);
        CommonMethods.setPrefData(PrefrenceConstant.SPORTS_NAME, listData, getApplicationContext());
    }

    private boolean isValidEmailId(String email) {

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.athleteLogInBtn:
                validateEdt();
                break;
            case R.id.athleteForgetTv:
                Intent forgetPass = new Intent(AthleteLoginActivity.this, ForgetPasswordActivity.class);
                forgetPass.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(forgetPass);
                break;
            case R.id.athleteSignUpTv:
                Intent signUp = new Intent(AthleteLoginActivity.this, AthleteSignupActivity.class);
                signUp.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(signUp);
                break;
        }
    }

    private void validateEdt() {
        email = binding.athleteEmailEdt.getText().toString();
        password = binding.athletePasswordEdt.getText().toString();
        if (email.isEmpty()) {
//            Snackbar.make(binding.athleteLoginLayout,getResources().getString(R.string.enter_email), BaseTransientBottomBar.LENGTH_LONG).show();
            binding.athleteEmailEdt.setError(getResources().getString(R.string.enter_email));
            binding.athleteEmailEdt.requestFocus();
        } else if (!isValidEmailId(email)) {     // Email Validation
//            Snackbar.make(binding.athleteLoginLayout,getResources().getString(R.string.enter_valid_email), BaseTransientBottomBar.LENGTH_LONG).show();
            binding.athleteEmailEdt.setError(getResources().getString(R.string.enter_valid_email));
            binding.athleteEmailEdt.requestFocus();
        } else if (password.equals("")) {
            binding.athletePasswordEdt.setError(getResources().getString(R.string.enter_password));
//            Snackbar.make(binding.athleteLoginLayout,getResources().getString(R.string.enter_password), BaseTransientBottomBar.LENGTH_LONG).show();

            binding.athletePasswordEdt.requestFocus();
        } else {
            hitLoginApi();
        }
    }
}
