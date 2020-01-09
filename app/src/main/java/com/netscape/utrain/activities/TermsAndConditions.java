package com.netscape.utrain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.athlete.AthleteHomeScreen;
import com.netscape.utrain.databinding.ActivityTermsAndConditionsBinding;
import com.netscape.utrain.response.TermsAndConditionsResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PrefrenceConstant;

import org.json.JSONArray;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TermsAndConditions extends AppCompatActivity {

    ActivityTermsAndConditionsBinding binding;
    private ProgressDialog progressDialog;
    private Retrofitinterface retrofitinterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_terms_and_conditions);
        binding = DataBindingUtil.setContentView(TermsAndConditions.this, R.layout.activity_terms_and_conditions);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading..");
        termsAndConditionsApi();
        backImgCode();

    }


    private void termsAndConditionsApi() {
        progressDialog.show();
        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);
        Call<TermsAndConditionsResponse> termsAndConditionsResponseCall =
                retrofitinterface.termsConditions(Constants.CONTENT_TYPE, "Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, TermsAndConditions.this));

        termsAndConditionsResponseCall.enqueue(new Callback<TermsAndConditionsResponse>() {
            @Override
            public void onResponse(Call<TermsAndConditionsResponse> call, Response<TermsAndConditionsResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    if (response.body() != null)
                        if (response.body().isStatus()) {
                            if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, TermsAndConditions.this).equalsIgnoreCase(Constants.Athlete)) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    binding.termAndConditionsText.setText(Html.fromHtml(response.body().getData() + "", Html.FROM_HTML_MODE_COMPACT));
                                } else {
                                    binding.termAndConditionsText.setText(Html.fromHtml(response.body().getData() + ""));
                                }
                            }
                        }
                } else {
                    progressDialog.dismiss();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        JSONArray errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message");
                        String errorMsg = errorMessage.getJSONObject(0).getString("message");
                        Toast.makeText(TermsAndConditions.this, "" + errorMsg, Toast.LENGTH_SHORT).show();
//                        Snackbar.make(binding.aboutUs, errorMsg, BaseTransientBottomBar.LENGTH_LONG).show();

                    } catch (Exception e) {
                        Toast.makeText(TermsAndConditions.this, "" + e, Toast.LENGTH_SHORT).show();

//                        Snackbar.make(binding.aboutUs, e.getMessage(), BaseTransientBottomBar.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<TermsAndConditionsResponse> call, Throwable t) {
                Toast.makeText(TermsAndConditions.this, "" + t, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void backImgCode() {

        binding.tncBackArrowImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
