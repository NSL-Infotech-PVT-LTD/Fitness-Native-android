package com.netscape.utrain.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.netscape.utrain.R;
import com.netscape.utrain.StripeConnect.ApplicationData;
import com.netscape.utrain.StripeConnect.StripeActivity;
import com.netscape.utrain.StripeConnect.StripeApp;
import com.netscape.utrain.StripeConnect.StripeButton;
import com.netscape.utrain.StripeConnect.StripeConnectModel;
import com.netscape.utrain.activities.athlete.AthleteHomeScreen;
import com.netscape.utrain.activities.athlete.AthleteSignupActivity;
import com.netscape.utrain.activities.athlete.ChooseSportActivity;
import com.netscape.utrain.activities.organization.OrganizationSignUpActivity;
import com.netscape.utrain.databinding.ActivitySettingsBinding;

import com.netscape.utrain.model.NotifaicationStateResponse;
import com.netscape.utrain.model.ServiceListDataModel;

import com.netscape.utrain.model.ChangePasswordModel;
import com.netscape.utrain.response.ChangePasswordResponse;
import com.netscape.utrain.response.LogoutResponse;
import com.netscape.utrain.response.TermsAndConditionsResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;

import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PrefrenceConstant;


import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {
    ActivitySettingsBinding binding;
    String sOldPassword, sNewPassword, sConfirmPwd;
    TextInputEditText oldPwd, newPwd, confirmPwd;
    private int IMAGE_GET = 166;
    private SwitchMaterial switchMaterial;
    private MaterialTextView textViewOn, textViewOff;
    private Retrofitinterface retrofitinterface;
    private AlertDialog dialogMultiOrder;
    private ChangePasswordModel model;
    private ProgressDialog progressDialog;
    private String isNotify = "";
    private String notify = "";
    private int count = 0;


    private StripeButton stripButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_settings);
        binding = DataBindingUtil.setContentView(SettingsActivity.this, R.layout.activity_settings);
        switchMaterial = findViewById(R.id.settingsSwitch);
        isNotify = CommonMethods.getPrefData(PrefrenceConstant.IS_NOTIFY, SettingsActivity.this);
        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);


//        textViewOn = findViewById(R.id.switchOnON);
//        textViewOff = findViewById(R.id.switchOffOFF);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading..");
//        setProfileImage();
        inIt();
        if (!TextUtils.isEmpty(isNotify)) {
            if (isNotify.equalsIgnoreCase("1")) {
//                switchMaterial.setOnCheckedChangeListener (null);
                switchMaterial.setChecked(true);
//                textViewOn.setVisibility(View.VISIBLE);
//                textViewOff.setVisibility(View.GONE);
//                switchMaterial.setOnCheckedChangeListener (this);
            } else {
//                switchMaterial.setOnCheckedChangeListener (null);
                switchMaterial.setChecked(false);
//                textViewOff.setVisibility(View.VISIBLE);
//                textViewOn.setVisibility(View.GONE);
            }
        }
        switchMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (switchMaterial.isChecked()) {
                    notify = "1";
                    ChangeNotificationState();
//                    Toast.makeText(SettingsActivity.this, "Checked", Toast.LENGTH_SHORT).show();
//                    textViewOn.setVisibility(View.VISIBLE);
//                    textViewOff.setVisibility(View.GONE);
                } else {
                    notify = "0";
                    ChangeNotificationState();
//                    Toast.makeText(SettingsActivity.this, "Un checked", Toast.LENGTH_SHORT).show();
//                    textViewOff.setVisibility(View.VISIBLE);
//                    textViewOn.setVisibility(View.GONE);
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
                if (count == 0) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                    LayoutInflater inflater = LayoutInflater.from(SettingsActivity.this);
                    View content = inflater.inflate(R.layout.change_password_layout, null);
                    builder.setView(content);
                    oldPwd = content.findViewById(R.id.oldPasswordEnter);
                    newPwd = content.findViewById(R.id.newPasswordEnter);
                    confirmPwd = content.findViewById(R.id.confirmNewPasswordEnter);
                    MaterialButton changePasswordBtn = content.findViewById(R.id.changePasswordBtn);
                    AppCompatImageView cancel = content.findViewById(R.id.changePwdCancelImg);
                    dialogMultiOrder = builder.create();
                    dialogMultiOrder.setCancelable(false);


                    changePasswordBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            sOldPassword = oldPwd.getText().toString().trim();
                            sNewPassword = newPwd.getText().toString().trim();
                            sConfirmPwd = confirmPwd.getText().toString().trim();

                            if (sOldPassword.isEmpty()) {
                                oldPwd.setError(getResources().getString(R.string.enter_old_password));
                                oldPwd.requestFocus();
                            } else if (sNewPassword.isEmpty()) {
                                newPwd.setError(getResources().getString(R.string.enter_new_password));
                                newPwd.requestFocus();
                            } else if (sNewPassword.length() < 6) {
                                Toast.makeText(SettingsActivity.this, getResources().getString(R.string.password_length), Toast.LENGTH_SHORT).show();
                                newPwd.requestFocus();
                            } else if (sNewPassword.length() > 8) {
                                Toast.makeText(SettingsActivity.this, getResources().getString(R.string.password_length), Toast.LENGTH_SHORT).show();
                                newPwd.requestFocus();
                            } else if (sConfirmPwd.isEmpty()) {
                                confirmPwd.setError(getResources().getString(R.string.enter_confirm_password));
                                confirmPwd.requestFocus();
                            } else if (sConfirmPwd.length() < 6) {
                                Toast.makeText(SettingsActivity.this, getResources().getString(R.string.password_length), Toast.LENGTH_SHORT).show();
                                confirmPwd.requestFocus();
                            } else if (sConfirmPwd.length() > 8) {
                                Toast.makeText(SettingsActivity.this, getResources().getString(R.string.password_length), Toast.LENGTH_SHORT).show();
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
                                count = 0;
                            }
                        }
                    });
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            count = 0;
                            dialogMultiOrder.dismiss();

                        }
                    });
                    count = 1;
                    dialogMultiOrder.show();
                    dialogMultiOrder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    private void inIt() {

        binding.basicProfileClickImg.setOnClickListener(this);
        binding.chooseSportsClickImg.setOnClickListener(this);
        binding.chooseServicesClickimg.setOnClickListener(this);
        binding.termAndConditionsTv.setOnClickListener(this);
        binding.helpSupportClickImg.setOnClickListener(this);
        binding.orgPortFolioViewSelect.setOnClickListener(this);
        binding.getConnectedWithStrip.setOnClickListener(this);

        if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, SettingsActivity.this).equalsIgnoreCase(Constants.Athlete)) {
            binding.cAddedFieldLayout.setVisibility(View.GONE);
            binding.getConnectedWithStrip.setVisibility(View.GONE);
            binding.stripeArrow.setVisibility(View.GONE);
            binding.connectStripe.setVisibility(View.GONE);
            binding.view7.setVisibility(View.GONE);
        } else if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, SettingsActivity.this).equalsIgnoreCase(Constants.Organizer)) {
            binding.orgPortFolioTv.setVisibility(View.VISIBLE);
            binding.orgPortFolioViewSelect.setVisibility(View.VISIBLE);
            binding.orgPortFolioIcon.setVisibility(View.VISIBLE);
            binding.orgPortFolioView.setVisibility(View.VISIBLE);
//            binding.chooseSportsTv.setText("Portfolio images");
//            binding.chooseSportsClickImg.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    startActivity(new Intent(SettingsActivity.this, PortfolioActivity.class));
//                }
//            });

        }
        if ((!CommonMethods.getPrefData(Constants.ACCOUNTID, SettingsActivity.this).isEmpty())) {
            binding.getConnectedWithStrip.setText("You are Connected");
            binding.stripeArrow.setImageDrawable(getDrawable(R.drawable.ic_ti_confirm));
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (resultCode == StripeApp.RESULT_CONNECTED) {
                if (data.getExtras() != null) {
                    String value = (String) data.getExtras().getString("stripCode");

                    getconnecteStripe(value);
                }

        }
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
                Intent chooProfile = new Intent(SettingsActivity.this, OrganizationSignUpActivity.class);
                chooProfile.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(chooProfile);
                break;
            case R.id.chooseSportsClickImg:
//                if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, SettingsActivity.this).equalsIgnoreCase(Constants.Organizer)) {
//                    PortfolioActivity.clearFromConstants();
//                    Intent getImages = new Intent(SettingsActivity.this, PortfolioActivity.class);
//                    getImages.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                    PortfolioActivity.updateImages = true;
//                    getImages.putExtra("updateEventImg", CommonMethods.getPrefData(PrefrenceConstant.PORT_FOLIO_IMAGES, SettingsActivity.this));
//                    getImages.putExtra("updateImgType", "portfolioImages");
//                    startActivityForResult(getImages, IMAGE_GET);
//                } else {
                Intent chooseSport = new Intent(SettingsActivity.this, ChooseSportActivity.class);
                CommonMethods.setPrefData(PrefrenceConstant.SPORT_NAME, "", getApplicationContext());
                ChooseSportActivity.athUpdate = true;
                chooseSport.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(chooseSport);
//                }
                break;
            case R.id.orgPortFolioViewSelect:
                PortfolioActivity.clearFromConstants();
                Intent getImages = new Intent(SettingsActivity.this, PortfolioActivity.class);
                getImages.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                PortfolioActivity.updateImages = true;
                getImages.putExtra("updateEventImg", CommonMethods.getPrefData(PrefrenceConstant.PORT_FOLIO_IMAGES, SettingsActivity.this));
                getImages.putExtra("updateImgType", "portfolioImages");
                startActivityForResult(getImages, IMAGE_GET);
                break;
            case R.id.chooseServicesClickimg:
                SelectedServiceList.getInstance().getList().clear();
                ArrayList<ServiceListDataModel> list = new ArrayList<>();
                CommonMethods.setLisstPrefData(Constants.SERVICE_LIST, list, SettingsActivity.this);
                ServicePriceActivity.updateServices = true;
                Intent servicePrice = new Intent(SettingsActivity.this, ServicePriceActivity.class);
                servicePrice.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(servicePrice);
                break;
            case R.id.termAndConditionsTv:
                Intent portFoliImages = new Intent(SettingsActivity.this, TermsAndConditions.class);
                portFoliImages.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(portFoliImages);
                break;
            case R.id.helpSupportClickImg:
                Intent helpAndSupport = new Intent(SettingsActivity.this, HelpAndSupport.class);
                PortfolioActivity.clearFromConstants();
                helpAndSupport.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(helpAndSupport);
                break;

            case R.id.getConnectedWithStrip:

                if ((CommonMethods.getPrefData(Constants.ACCOUNTID, SettingsActivity.this) == null) || (CommonMethods.getPrefData(Constants.ACCOUNTID, SettingsActivity.this).isEmpty()))
                    performStripe();
                else Toast.makeText(this, "Already Connected", Toast.LENGTH_SHORT).show();
                break;

        }


    }

    private void getconnecteStripe(String code) {
        ProgressDialog progressDialog = new ProgressDialog(SettingsActivity.this);
        progressDialog.setMessage("Connecting with stripe....");
        progressDialog.show();
        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);
        Call<StripeConnectModel> call = retrofitinterface.getConnectedStripe("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getApplicationContext()), Constants.CONTENT_TYPE, code);
        call.enqueue(new Callback<StripeConnectModel>() {
            @Override
            public void onResponse(Call<StripeConnectModel> call, Response<StripeConnectModel> response) {
                progressDialog.dismiss();
                if (response.body() != null) {

                    if (response.body().isStatus()) {
                        binding.getConnectedWithStrip.setText("You are Connected");
                        binding.stripeArrow.setImageDrawable(getDrawable(R.drawable.ic_ti_confirm));
                        CommonMethods.setPrefData(Constants.ACCOUNTID, response.body().getData().getStripeDetails().getAccount_id() + "", SettingsActivity.this);
                    }
                } else {
//                    progressDialog.dismiss();

                    try {
                        JSONObject jObjError = null;
                        if (response.errorBody() != null) {
                            jObjError = new JSONObject(response.errorBody().string());

                            JSONArray errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message");
                            String errorMsg = errorMessage.getString(0);
                            Toast.makeText(SettingsActivity.this, "" + errorMsg, Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(SettingsActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();


                    }
                }

            }

            @Override
            public void onFailure(Call<StripeConnectModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onClickStripPerform() {
        StripeApp mApp2;
        StripeButton mStripeButton2;
        mApp2 = new StripeApp(this, "StripeAccount", ApplicationData.CLIENT_ID,
                ApplicationData.SECRET_KEY, ApplicationData.CALLBACK_URL);
        mStripeButton2 = (StripeButton) findViewById(R.id.stripButton);
        mStripeButton2.setStripeApp(mApp2);
        mStripeButton2.setConnectMode(StripeApp.CONNECT_MODE.ACTIVITY);
        mStripeButton2.performClick();

        String apiKey = mApp2.getAccessToken();
    }

    private void performStripe() {
        new AlertDialog.Builder(this)
                .setTitle("Connect with Stripe")
                .setMessage("You have to connect your with your payment gateway, in order to receive the payments")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        onClickStripPerform();
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void hitChangePasswordApi() {

        ProgressDialog progressDialog = new ProgressDialog(SettingsActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

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
                            Toast.makeText(SettingsActivity.this, "" + response.body().getData() + "", Toast.LENGTH_SHORT).show();
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

    private void ChangeNotificationState() {
        progressDialog.show();
        Call<NotifaicationStateResponse> signUpAthlete = retrofitinterface.ChangeNotificationSetting("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, SettingsActivity.this));
        signUpAthlete.enqueue(new Callback<NotifaicationStateResponse>() {
            @Override
            public void onResponse(Call<NotifaicationStateResponse> call, Response<NotifaicationStateResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData() != null) {
                            Toast.makeText(getApplicationContext(), response.body().getData() + "", Toast.LENGTH_SHORT).show();
                            CommonMethods.setPrefData(PrefrenceConstant.IS_NOTIFY, notify, SettingsActivity.this);
//                            LoginManager.getInstance().logOut();
//                            CommonMethods.clearPrefData(SettingsActivity.this);
//                            Intent intent = new Intent(SettingsActivity.this, SignUpTypeActivity.class);
//                            startActivity(intent);
//                            finish();
                        }
                    } else {
                        Snackbar.make(binding.settingsContainer, response.body().getError().getError_message().getMessage().toString(), BaseTransientBottomBar.LENGTH_LONG).show();
                    }
                } else {
                    progressDialog.dismiss();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);
                        Snackbar.make(binding.settingsContainer, errorMessage, BaseTransientBottomBar.LENGTH_LONG).show();

                    } catch (Exception e) {
                        Snackbar.make(binding.settingsContainer, e.getMessage(), BaseTransientBottomBar.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<NotifaicationStateResponse> call, Throwable t) {
                Snackbar.make(binding.settingsContainer, getResources().getString(R.string.something_went_wrong), BaseTransientBottomBar.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
    }
}

