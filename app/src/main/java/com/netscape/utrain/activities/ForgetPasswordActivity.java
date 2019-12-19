package com.netscape.utrain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.facebook.login.widget.LoginButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.athlete.AthleteLoginActivity;
import com.netscape.utrain.activities.athlete.AthleteSignupActivity;
import com.netscape.utrain.databinding.ActivityForgetPasswordBinding;
import com.netscape.utrain.model.ForgetDataModel;
import com.netscape.utrain.response.ForgetPasswordResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PrefrenceConstant;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordActivity extends AppCompatActivity implements View.OnClickListener{
    private ActivityForgetPasswordBinding binding;
    private ProgressDialog progressDialog;
    private Retrofitinterface retrofitinterface;
    private ForgetDataModel mDataModel;
    private String emailId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_forget_password);

        init();
    }

    private void init() {
        binding.forgetBackBtn.setOnClickListener(this);
        binding.forgetSubmitBtn.setOnClickListener(this);
        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.forgetSubmitBtn:
                getNewPassword();
                break;
            case R.id.forgetBackBtn:
                finish();
                break;
        }
    }

    private void getNewPassword() {
        emailId=binding.forgetEmailEdt.getText().toString().trim();
        if (binding.forgetEmailEdt.getText().toString().trim().isEmpty()){
            binding.forgetEmailEdt.setError(getResources().getString(R.string.enter_your_email));
            binding.forgetEmailEdt.requestFocus();
        }else if (!Patterns.EMAIL_ADDRESS.matcher(binding.forgetEmailEdt.getText().toString()).matches()) {
            binding.forgetEmailEdt.setError(getResources().getString(R.string.enter_valid_email));
            binding.forgetEmailEdt.requestFocus();
        }else {
            hitPasswordChangeApi();
        }
    }

    private void hitPasswordChangeApi() {
        progressDialog.show();
        Call<ForgetPasswordResponse> call = retrofitinterface.getForgetpassword(Constants.CONTENT_TYPE,emailId );
        call.enqueue(new Callback<ForgetPasswordResponse>() {
            @Override
            public void onResponse(Call<ForgetPasswordResponse> call, Response<ForgetPasswordResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData() != null) {
                            Toast.makeText(ForgetPasswordActivity.this, response.body().getData().getMessage().toString(), Toast.LENGTH_SHORT).show();
//                            Snackbar.make(binding.forgetLayout,response.body().getData().getMessage().toString(), BaseTransientBottomBar.LENGTH_SHORT).show();
                            Intent homeScreen= new Intent(getApplicationContext(), SignUpTypeActivity.class);
                            startActivity(homeScreen);
                        }
                    } else {
                        Snackbar.make(binding.forgetLayout,response.body().getError().getError_message().getMessage().toString(), BaseTransientBottomBar.LENGTH_SHORT).show();
                    }
                } else {
                    progressDialog.dismiss();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);
                        Snackbar.make(binding.forgetLayout,errorMessage, BaseTransientBottomBar.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Snackbar.make(binding.forgetLayout,e.getMessage(), BaseTransientBottomBar.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<ForgetPasswordResponse> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(binding.forgetLayout,"Something went wrong", BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });


    }
}
