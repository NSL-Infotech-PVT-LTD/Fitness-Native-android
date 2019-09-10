package com.netscape.utrain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.netscape.utrain.R;
import com.netscape.utrain.databinding.ActivityForgetPasswordBinding;
import com.netscape.utrain.model.ForgetDataModel;
import com.netscape.utrain.response.ForgetPasswordResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordActivity extends AppCompatActivity implements View.OnClickListener{
    private ActivityForgetPasswordBinding binding;

    Retrofitinterface api;
    ForgetDataModel mDataModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        api = RetrofitInstance.getClient().create(Retrofitinterface.class);

        binding= DataBindingUtil.setContentView(this,R.layout.activity_forget_password);
        init();
    }

    private void init() {
        binding.forgetBackBtn.setOnClickListener(this);
        binding.forgetSubmitBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.forgetSubmitBtn:
//                getNewPassword();
                Intent loginActivity=new Intent(ForgetPasswordActivity.this,LoginActivity.class);
                startActivity(loginActivity);
                break;
            case R.id.forgetBackBtn:
                finish();
                break;
        }
    }

    private void getNewPassword() {
        if (binding.forgetNewPassEdt.getText().toString().isEmpty()){
            binding.forgetNewPassEdt.setError(getString(R.string.enter_new_password));
        }else if (binding.forgetConfirmPassEdt.getText().toString().isEmpty()) {
            binding.forgetConfirmPassEdt.setError(getString(R.string.confirm_new_password));
        }else if (! binding.forgetConfirmPassEdt.getText().toString().equals(binding.forgetNewPassEdt.getText().toString())) {
            binding.forgetConfirmPassEdt.setError(getString(R.string.confirm_password_doesnt_match));
        }else {
            hitPasswordChangeApi();
        }
    }

    private void hitPasswordChangeApi() {

        Call<ForgetPasswordResponse> call = api.getForgetpassword("application/x-www-form-urlencoded",
                "niti1.prabha@netscapelabs.com");

        call.enqueue(new Callback<ForgetPasswordResponse>() {
            @Override
            public void onResponse(Call<ForgetPasswordResponse> call, Response<ForgetPasswordResponse> response) {

                if (response.isSuccessful())
                {
                    if (response.body().getCode() == 201)
                    {
                        Toast.makeText(ForgetPasswordActivity.this, ""+response.body().getData().getMessage(),Toast.LENGTH_SHORT).show();
                    } else
                    {
                        Toast.makeText(ForgetPasswordActivity.this, ""+response.body().getError().getError_message(),Toast.LENGTH_SHORT).show();
                    }
                } else
                    Toast.makeText(ForgetPasswordActivity.this,"Api not hit",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ForgetPasswordResponse> call, Throwable t) {


            }
        });


    }
}
