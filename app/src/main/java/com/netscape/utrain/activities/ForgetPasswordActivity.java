package com.netscape.utrain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import com.netscape.utrain.R;
import com.netscape.utrain.databinding.ActivityForgetPasswordBinding;

public class ForgetPasswordActivity extends AppCompatActivity implements View.OnClickListener{
    private ActivityForgetPasswordBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
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
                getNewPassword();
//                Intent loginActivity=new Intent(ForgetPasswordActivity.this,LoginActivity.class);
//                startActivity(loginActivity);
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
    }
}
