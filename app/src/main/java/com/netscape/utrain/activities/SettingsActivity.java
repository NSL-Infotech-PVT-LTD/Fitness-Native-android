package com.netscape.utrain.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.athlete.AthleteHomeScreen;
import com.netscape.utrain.activities.athlete.ChooseSportActivity;
import com.netscape.utrain.activities.organization.OrganizationSignUpActivity;
import com.netscape.utrain.databinding.ActivitySettingsBinding;

import com.netscape.utrain.model.ServiceListDataModel;

import com.netscape.utrain.model.ChangePasswordModel;
import com.netscape.utrain.response.ChangePasswordResponse;
import com.netscape.utrain.response.TermsAndConditionsResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;

import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PrefrenceConstant;


import java.util.ArrayList;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {
    ActivitySettingsBinding binding;
    private int IMAGE_GET = 166;
    String sOldPassword, sNewPassword, sConfirmPwd;
    TextInputEditText oldPwd, newPwd, confirmPwd;
    private SwitchMaterial switchMaterial;
    private MaterialTextView textViewOn, textViewOff;
    private Retrofitinterface retrofitinterface;
    private AlertDialog dialogMultiOrder;
    private ChangePasswordModel model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_settings);
        binding = DataBindingUtil.setContentView(SettingsActivity.this, R.layout.activity_settings);

        switchMaterial = findViewById(R.id.settingsSwitch);
        textViewOn = findViewById(R.id.switchOnON);
        textViewOff = findViewById(R.id.switchOffOFF);
//        setProfileImage();
        inIt();
        switchMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (switchMaterial.isChecked()) {
                    textViewOn.setVisibility(View.VISIBLE);
                    textViewOff.setVisibility(View.GONE);
                } else {
                    textViewOff.setVisibility(View.VISIBLE);
                    textViewOn.setVisibility(View.GONE);
                }

            }
        });
        binding.settingsBackImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        binding.changePasswordTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // change password api hit here....

                final AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                LayoutInflater inflater = LayoutInflater.from(SettingsActivity.this);
                View content = inflater.inflate(R.layout.change_password_layout, null);
                builder.setView(content);
                oldPwd = (TextInputEditText) content.findViewById(R.id.oldPasswordEnter);
                newPwd = (TextInputEditText) content.findViewById(R.id.newPasswordEnter);
                confirmPwd = (TextInputEditText) content.findViewById(R.id.confirmNewPasswordEnter);
                MaterialButton changePasswordBtn = (MaterialButton) content.findViewById(R.id.changePasswordBtn);
                AppCompatImageView cancel = (AppCompatImageView) content.findViewById(R.id.changePwdCancelImg);
                dialogMultiOrder = builder.create();
                dialogMultiOrder.setCancelable(false);


                changePasswordBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        sOldPassword = oldPwd.getText().toString();
                        sNewPassword = newPwd.getText().toString();
                        sConfirmPwd = confirmPwd.getText().toString();

                        if (sOldPassword.isEmpty()) {
                            oldPwd.setError(getResources().getString(R.string.enter_old_password));
                            oldPwd.requestFocus();
                        } else if (sNewPassword.isEmpty()) {
                            newPwd.setError(getResources().getString(R.string.enter_new_password));
                            newPwd.requestFocus();
                        } else if (sConfirmPwd.isEmpty()) {
                            confirmPwd.setError(getResources().getString(R.string.enter_confirm_password));
                            confirmPwd.requestFocus();

                        } else if (!sNewPassword.equals(sConfirmPwd)) {

                            confirmPwd.setError(getResources().getString(R.string.confirm_password_doesnt_match));
                            confirmPwd.requestFocus();
//                            if (sNewPassword.equals(sConfirmPwd)) {
//                                newPwd.setError("");
//                                confirmPwd.setError("");
//
//                            }
                        } else {
                            hitChangePasswordApi();
                        }
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogMultiOrder.dismiss();
                    }
                });

                dialogMultiOrder.show();
                dialogMultiOrder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            }
        });
    }

    private void inIt() {

        binding.basicProfileClickImg.setOnClickListener(this);
        binding.chooseSportsClickImg.setOnClickListener(this);
        binding.chooseServicesClickimg.setOnClickListener(this);
        binding.termAndConditionsTv.setOnClickListener(this);


        if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, SettingsActivity.this).equalsIgnoreCase(Constants.Athlete)) {
            binding.cAddedFieldLayout.setVisibility(View.GONE);
        } else if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, SettingsActivity.this).equalsIgnoreCase(Constants.Organizer)) {
            binding.chooseSportsTv.setText("Portfolio images");
//            binding.chooseSportsClickImg.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    startActivity(new Intent(SettingsActivity.this, PortfolioActivity.class));
//                }
//            });

        }


    }

//    private void setProfileImage() {
//
//        String img = CommonMethods.getPrefData(PrefrenceConstant.PROFILE_IMAGE, SettingsActivity.this);
//        if (!TextUtils.isEmpty(img)) {
////            Glide.with(SettingsActivity.this).load(Constants.IMAGE_BASE_URL + img).thumbnail(Glide.with(SettingsActivity.this).load(Constants.IMAGE_BASE_URL + Constants.THUMBNAILS + img)).into(binding.settingsProfileImg);
////            Glide.with(SettingsActivity.this).load(Constants.IMAGE_BASE_URL + img).thumbnail(Glide.with(SettingsActivity.this).load(Constants.IMAGE_BASE_URL + Constants.THUMBNAILS + img)).into(headerImage);
////            navNameTv.setText(CommonMethods.getPrefData(PrefrenceConstant.USER_NAME, SettingsActivity.this));
//        }
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.basicProfileClickImg:
                OrganizationSignUpActivity.update = true;
                startActivity(new Intent(SettingsActivity.this, OrganizationSignUpActivity.class));
                break;
            case R.id.chooseSportsClickImg:
                if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, SettingsActivity.this).equalsIgnoreCase(Constants.Organizer)) {
                    PortfolioActivity.clearFromConstants();
                    Intent getImages = new Intent(SettingsActivity.this, PortfolioActivity.class);
                    getImages.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    PortfolioActivity.updateImages = true;
                    getImages.putExtra("updateEventImg", CommonMethods.getPrefData(PrefrenceConstant.PORT_FOLIO_IMAGES, SettingsActivity.this));
                    getImages.putExtra("updateImgType", "portfolioImages");
                    startActivityForResult(getImages, IMAGE_GET);
                } else {
                    CommonMethods.setPrefData(PrefrenceConstant.SPORT_NAME, "", getApplicationContext());
                    ChooseSportActivity.athUpdate = true;
                    startActivity(new Intent(SettingsActivity.this, ChooseSportActivity.class));
                }
                break;
            case R.id.chooseServicesClickimg:
                SelectedServiceList.getInstance().getList().clear();
                ArrayList<ServiceListDataModel> list = new ArrayList<>();
                CommonMethods.setLisstPrefData(Constants.SERVICE_LIST, list, SettingsActivity.this);
                ServicePriceActivity.updateServices = true;
                startActivity(new Intent(SettingsActivity.this, ServicePriceActivity.class));
                break;
            case R.id.termAndConditionsTv:
                startActivity(new Intent(SettingsActivity.this, TermsAndConditions.class));
        }


    }

    private void hitChangePasswordApi() {

        ProgressDialog progressDialog = new ProgressDialog(SettingsActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);
        Call<ChangePasswordResponse> changePasswordCall = retrofitinterface.changePassword(Constants.CONTENT_TYPE,
                "Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, SettingsActivity.this),
                sOldPassword, sNewPassword, sConfirmPwd);

        changePasswordCall.enqueue(new Callback<ChangePasswordResponse>() {
            @Override
            public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().isStatus()) {
                        if (response.body() != null) {

                            model = response.body().getData();
                            Toast.makeText(SettingsActivity.this, "" + response.body().getData().getScalar(), Toast.LENGTH_SHORT).show();
                            dialogMultiOrder.dismiss();
                        }
                    } else {
                        Toast.makeText(SettingsActivity.this, "" + response.body().getError().getError_message(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressDialog.dismiss();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);
                        oldPwd.setError("Please use valid old password");
                        oldPwd.requestFocus();

                        Toast.makeText(getApplicationContext(), "" + errorMessage, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {

                    }
                }

            }

            @Override
            public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {

                progressDialog.dismiss();

            }
        });
    }
}

