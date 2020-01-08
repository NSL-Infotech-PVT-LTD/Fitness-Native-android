package com.netscape.utrain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.netscape.utrain.PortfolioImagesConstants;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.coach.CoachDashboard;
import com.netscape.utrain.activities.organization.OrgHomeScreen;
import com.netscape.utrain.databinding.ActivityHelpAndSupportBinding;
import com.netscape.utrain.response.HelpAndSupportResponse;
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

public class HelpAndSupport extends AppCompatActivity implements View.OnClickListener {
    private ActivityHelpAndSupportBinding binding;
    private ProgressDialog progressDialog;
    private Retrofitinterface retrofitinterface;
    private String messageText = "";
    private int IMAGE_GET = 166;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_help_and_support);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_help_and_support);
        init();
    }

    private void init() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);
        binding.submitBtn.setOnClickListener(this);
        binding.eventBookingBackImg.setOnClickListener(this);
        binding.imageUploadText.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submitBtn:
                messageText = binding.requestEdiText.getText().toString().trim();
                if (messageText.isEmpty()) {
                    binding.requestEdiText.requestFocus();
                    Toast.makeText(this, "" + getResources().getString(R.string.enter_some_text), Toast.LENGTH_SHORT).show();
                } else {
                    hitHelpAndSupportApi();
                }
                break;
            case R.id.eventBookingBackImg:
                finish();
                break;
            case R.id.imageUploadText:
                Intent getImages = new Intent(HelpAndSupport.this, PortfolioActivity.class);
                getImages.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                PortfolioActivity.getSingleImage = true;
                startActivityForResult(getImages, IMAGE_GET);
                break;
        }
    }

    private void hitHelpAndSupportApi() {
        progressDialog.show();
        Call<HelpAndSupportResponse> signUpAthlete = retrofitinterface.helpAndSupport("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getApplicationContext()), messageText, PortfolioImagesConstants.partFour);
        signUpAthlete.enqueue(new Callback<HelpAndSupportResponse>() {
            @Override
            public void onResponse(Call<HelpAndSupportResponse> call, Response<HelpAndSupportResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData() != null) {
                            Toast.makeText(HelpAndSupport.this, "" + response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
                                finish();
                        } else {
//                            Toast.makeText(HelpAndSupport.this, ""+response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        Toast.makeText(HelpAndSupport.this, "" + response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();

//                        Snackbar.make(binding.loginLayout, response.body().getError().getError_message().getMessage().toString(), Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    progressDialog.dismiss();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
//                        JSONArray errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message");
//                        String errorMsg = errorMessage.getJSONObject(0).getString("message");
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);
//                        Snackbar.make(binding.loginLayout, errorMessage, Snackbar.LENGTH_LONG).show();
                        Toast.makeText(HelpAndSupport.this, "" + errorMessage, Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
//                        Snackbar.make(binding.loginLayout, e.getMessage(), Snackbar.LENGTH_LONG).show();
                        Toast.makeText(HelpAndSupport.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }

            }

            @Override
            public void onFailure(Call<HelpAndSupportResponse> call, Throwable t) {
//                Snackbar.make(binding.loginLayout, getResources().getString(R.string.something_went_wrong), Snackbar.LENGTH_LONG).show();
                Toast.makeText(HelpAndSupport.this, "" + getResources().getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();

                progressDialog.dismiss();

            }
        });
    }
}
