package com.netscape.utrain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import com.netscape.utrain.R;
import com.netscape.utrain.databinding.ActivityTermsAndConditionsBinding;
import com.netscape.utrain.model.TermsAndConditionsModel;
import com.netscape.utrain.response.TermsAndConditionsResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PrefrenceConstant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TermsAndConditions extends AppCompatActivity {

    ActivityTermsAndConditionsBinding binding;

    private Retrofitinterface retrofitinterface;
    private TermsAndConditionsModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_terms_and_conditions);
        binding = DataBindingUtil.setContentView(TermsAndConditions.this, R.layout.activity_terms_and_conditions);
        termsAndConditionsApi();
        backImgCode();

    }


    private void termsAndConditionsApi() {

        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);
        Call<TermsAndConditionsResponse> termsAndConditionsResponseCall =
                retrofitinterface.termsAndConditions(CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, TermsAndConditions.this));

        termsAndConditionsResponseCall.enqueue(new Callback<TermsAndConditionsResponse>() {
            @Override
            public void onResponse(Call<TermsAndConditionsResponse> call, Response<TermsAndConditionsResponse> response) {
                if (response.isSuccessful())
                    if (response.body() != null)
                        if (response.body().isStatus()) {
                            if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, TermsAndConditions.this).equalsIgnoreCase(Constants.Athlete)) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    binding.termAndConditionsText.setText(Html.fromHtml(response.body().getData().get(0).getTerms_and_conditions_athlete() + "", Html.FROM_HTML_MODE_COMPACT));
                                } else {
                                    binding.termAndConditionsText.setText(Html.fromHtml(response.body().getData().get(0).getTerms_and_conditions_athlete() + ""));
                                }
                            } else if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, TermsAndConditions.this).equalsIgnoreCase(Constants.Coach)) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    binding.termAndConditionsText.setText(Html.fromHtml(response.body().getData().get(0).getTerms_and_conditions_coach() + "", Html.FROM_HTML_MODE_COMPACT));
                                } else {
                                    binding.termAndConditionsText.setText(Html.fromHtml(response.body().getData().get(0).getTerms_and_conditions_coach() + ""));
                                }
                            } else if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, TermsAndConditions.this).equalsIgnoreCase(Constants.Organizer)) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    binding.termAndConditionsText.setText(Html.fromHtml(response.body().getData().get(0).getTerms_and_conditions_organiser() + "", Html.FROM_HTML_MODE_COMPACT));
                                } else {
                                    binding.termAndConditionsText.setText(Html.fromHtml(response.body().getData().get(0).getTerms_and_conditions_organiser() + ""));
                                }

                            }
                        }
            }

            @Override
            public void onFailure(Call<TermsAndConditionsResponse> call, Throwable t) {

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
