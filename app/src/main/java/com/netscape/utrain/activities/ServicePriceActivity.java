package com.netscape.utrain.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.databinding.BindingMethod;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.athlete.AthleteLoginActivity;
import com.netscape.utrain.activities.athlete.AthleteSignupActivity;
import com.netscape.utrain.adapters.DialogAdapter;
import com.netscape.utrain.adapters.ServicePriceAdapter;
import com.netscape.utrain.databinding.ActivityServicePriceBinding;
import com.netscape.utrain.model.OrgUserDataModel;
import com.netscape.utrain.model.ServiceListDataModel;
import com.netscape.utrain.model.ServicePriceModel;
import com.netscape.utrain.response.CoachSignUpResponse;
import com.netscape.utrain.response.CoachSignUpResponse;
import com.netscape.utrain.response.CoachSignUpResponse;
import com.netscape.utrain.response.ServiceListResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PrefrenceConstant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServicePriceActivity extends AppCompatActivity implements View.OnClickListener, DialogAdapter.SelectedServicesInterface, ServicePriceAdapter.ServicePriceInterface {
    MaterialTextView addService;
    RecyclerView.LayoutManager layoutManager;
    ServicePriceAdapter serviceAdapter;
    ArrayList<ServiceListDataModel> selectedService=new ArrayList<>();
    ArrayList<ServiceListDataModel> mList = new ArrayList<>();
    ServiceListDataModel serviceModel;
    DialogAdapter dialogAdapter;
    AlertDialog alertDialog;
    MaterialButton btnDialogNext;
    int mPosition = 0;
    JsonArray jsonArray;
    private ActivityServicePriceBinding binding;
    private ProgressDialog progressDialog;
    private Retrofitinterface retrofitinterface;
    private OrgUserDataModel orgDataModel;
    private String activeUserType = "";
    private File photoFile = null;
    private boolean userPrice=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_service_price);

        layoutManager = new LinearLayoutManager(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);
        mList.clear();
        selectedService.clear();
        init();
        if (mList!=null && mList.size()>0){

        }else {
            hitServiceListApi();
        }



    }

    private void init() {
         selectedService=CommonMethods.getListPrefrence(Constants.SELECTED_SERVICE,ServicePriceActivity.this);
         mList=CommonMethods.getListPrefrence(Constants.SERVICE_LIST,ServicePriceActivity.this);
        if (selectedService!=null && selectedService.size()>0){
            binding.serviceRecyclerView.setVisibility(View.VISIBLE);
            binding.serviceTv.setVisibility(View.VISIBLE);
            binding.rateTV.setVisibility(View.VISIBLE);
//            binding.noSelectedService.setVisibility(View.GONE);
            serviceAdapter = new ServicePriceAdapter(getApplicationContext(), selectedService, ServicePriceActivity.this);
            binding.serviceRecyclerView.setLayoutManager(layoutManager);
            binding.serviceRecyclerView.setAdapter(serviceAdapter);
        }
         binding.addServiceBtn.setOnClickListener(this);
        binding.servicePriceNextBtn.setOnClickListener(this);

        if (getIntent().getExtras() != null) {
            orgDataModel = (OrgUserDataModel) getIntent().getSerializableExtra(Constants.OrgSignUpIntent);
            activeUserType = getIntent().getStringExtra(Constants.ActiveUserType);
            if (activeUserType.equals(Constants.TypeCoach)) {
                binding.servicePriceNextBtn.setText(getResources().getString(R.string.submit));
            }
            if (activeUserType.equals(Constants.TypeOrganization)) {
                binding.servicePriceNextBtn.setText(getResources().getString(R.string.next));
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addServiceBtn:
                displayServiceDialog();
                break;
            case R.id.servicePriceNextBtn:
                if (selectedService != null && selectedService.size() > 0) {
                    jsonArray = (JsonArray) new Gson().toJsonTree(selectedService);
                    if (activeUserType.equals(Constants.TypeCoach)) {
                        CoachSignUpApi();
                    }
                    if (activeUserType.equals(Constants.TypeOrganization)) {
                        orgDataModel.setSelectedServices(jsonArray.toString());
                        Intent portfolio = new Intent(ServicePriceActivity.this, PortfolioActivity.class);
                        portfolio.putExtra(Constants.OrgSignUpIntent, orgDataModel);
//                        portfolio.putExtra(Constants.JsonArrayIntent, jsonArray.toString());
                        startActivity(portfolio);
                    }
                } else {
                    Snackbar.make(binding.serviceLayout, getResources().getString(R.string.select_services), BaseTransientBottomBar.LENGTH_SHORT).show();
                }
                break;
        }
    }
    private void CoachSignUpApi() {
//        CommonMethods.hideKeyboard(this);
        progressDialog.show();
        MultipartBody.Part userImg = null;
        if (orgDataModel.getProfile_img() != null) {
//            userImg = prepareFilePart("profile_image", photoFile.getName(), photoFile);
            userImg = MultipartBody.Part.createFormData( "profile_image",orgDataModel.getProfile_img().getName(), RequestBody.create(MediaType.parse("image/*"), orgDataModel.getProfile_img()));
        }
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        requestBodyMap.put("name", RequestBody.create(MediaType.parse("multipart/form-data"), orgDataModel.getName()));
        requestBodyMap.put("email", RequestBody.create(MediaType.parse("multipart/form-data"),orgDataModel.getEmail() ));
        requestBodyMap.put("password", RequestBody.create(MediaType.parse("multipart/form-data"),orgDataModel.getPassword())); 
        requestBodyMap.put("phone", RequestBody.create(MediaType.parse("multipart/form-data"), orgDataModel.getPhone()));
        requestBodyMap.put("location", RequestBody.create(MediaType.parse("multipart/form-data"), orgDataModel.getLocation()));
        requestBodyMap.put("latitude", RequestBody.create(MediaType.parse("multipart/form-data"),orgDataModel.getLatitude()));
        requestBodyMap.put("longitude", RequestBody.create(MediaType.parse("multipart/form-data"), orgDataModel.getLatitude()));
        requestBodyMap.put("business_hour_starts", RequestBody.create(MediaType.parse("multipart/form-data"),orgDataModel.getBusiness_hour_starts()));
        requestBodyMap.put("business_hour_ends", RequestBody.create(MediaType.parse("multipart/form-data"),orgDataModel.getBusiness_hour_ends()));
        requestBodyMap.put("bio", RequestBody.create(MediaType.parse("multipart/form-data"),orgDataModel.getBio()));
        requestBodyMap.put("service_ids", RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(jsonArray)));
        requestBodyMap.put("expertise_years", RequestBody.create(MediaType.parse("multipart/form-data"),orgDataModel.getExpertise_years()));
        requestBodyMap.put("hourly_rate", RequestBody.create(MediaType.parse("multipart/form-data"), orgDataModel.getHourly_rate()));
        requestBodyMap.put("device_type", RequestBody.create(MediaType.parse("multipart/form-data"), Constants.DEVICE_TYPE));
        requestBodyMap.put("device_token", RequestBody.create(MediaType.parse("multipart/form-data"),Constants.DEVICE_TOKEN));
        requestBodyMap.put("device_token", RequestBody.create(MediaType.parse("multipart/form-data"),Constants.DEVICE_TOKEN));
        Call<CoachSignUpResponse> signUpAthlete = retrofitinterface.registerCoach(requestBodyMap,userImg);
        signUpAthlete.enqueue(new Callback<CoachSignUpResponse>() {
            @Override
            public void onResponse(Call<CoachSignUpResponse> call, Response<CoachSignUpResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData() != null) {
                            CommonMethods.setPrefData(PrefrenceConstant.USER_EMAIL, response.body().getData().getUser().getEmail(), ServicePriceActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.USER_PHONE, response.body().getData().getUser().getPhone(), ServicePriceActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.USER_NAME, response.body().getData().getUser().getName(), ServicePriceActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.USER_ID, response.body().getData().getUser().getId()+"", ServicePriceActivity.this);
                            Intent homeScreen= new Intent(getApplicationContext(), BottomNavigation.class);
                            homeScreen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(homeScreen);
                        }
                    } else {
                        Snackbar.make(binding.serviceLayout,response.body().getError().getError_message().getMessage().toString(), BaseTransientBottomBar.LENGTH_SHORT).show();
                    }
                } else {
                    progressDialog.dismiss();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);
                        Snackbar.make(binding.serviceLayout,errorMessage, BaseTransientBottomBar.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Snackbar.make(binding.serviceLayout,e.getMessage(), BaseTransientBottomBar.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<CoachSignUpResponse> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(binding.serviceLayout,getResources().getString(R.string.something_went_wrong), BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });
    }
    private void coachSignUpApi() {
        //        CommonMethods.hideKeyboard(this);
        progressDialog.show();
        MultipartBody.Part profileImg = null;
        if (orgDataModel.getProfile_img() != null) {
//            photoFile = new File(orgDataModel.getProfile_img());
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), photoFile);
            profileImg = MultipartBody.Part.createFormData(photoFile.getName(), "profile_image", requestBody);
        }
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), photoFile.getName());
        Call<CoachSignUpResponse> siguUpCoach = retrofitinterface.coachSignup(Constants.CONTENT_TYPE,
                orgDataModel.getName(),
                orgDataModel.getEmail(),
                orgDataModel.getPassword(),
                orgDataModel.getPhone(),
                orgDataModel.getLocation(),
                orgDataModel.getLatitude(),
                orgDataModel.getLongitude(),
                orgDataModel.getBusiness_hour_starts(),
                orgDataModel.getBusiness_hour_ends(),
                orgDataModel.getBio(),
                jsonArray.toString(),
                orgDataModel.getExpertise_years(),
                orgDataModel.getHourly_rate(),
                Constants.DEVICE_TYPE,
                Constants.DEVICE_TOKEN,
                profileImg, filename);
        siguUpCoach.enqueue(new Callback<CoachSignUpResponse>() {
            @Override
            public void onResponse(Call<CoachSignUpResponse> call, Response<CoachSignUpResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData() != null) {
                            CommonMethods.setPrefData(PrefrenceConstant.USER_EMAIL, response.body().getData().getUser().getEmail(), ServicePriceActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.USER_PHONE, response.body().getData().getUser().getPhone(), ServicePriceActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.USER_NAME, response.body().getData().getUser().getName(), ServicePriceActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.USER_ID, response.body().getData().getUser().getId() + "", ServicePriceActivity.this);
                            Intent homeScreen = new Intent(getApplicationContext(), BottomNavigation.class);
                            homeScreen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(homeScreen);
                        }
                    } else {
//                        Snackbar.make(binding.portFolioLayout, response.body().getError().getError_message().getMessage().toString(), BaseTransientBottomBar.LENGTH_SHORT).show();
                    }
                } else {
                    progressDialog.dismiss();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);
                        Snackbar.make(binding.serviceLayout, errorMessage, BaseTransientBottomBar.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Snackbar.make(binding.serviceLayout, e.getMessage(), BaseTransientBottomBar.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<CoachSignUpResponse> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(binding.serviceLayout, getResources().getString(R.string.something_went_wrong), BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });
    }

    private void hitServiceListApi() {
        progressDialog.show();
        Call<ServiceListResponse> signUpAthlete = retrofitinterface.getServiceList(Constants.CONTENT_TYPE);
        signUpAthlete.enqueue(new Callback<ServiceListResponse>() {
            @Override
            public void onResponse(Call<ServiceListResponse> call, Response<ServiceListResponse> response) {
                if (response.isSuccessful()) {

                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData() != null) {
                            mList.addAll(response.body().getData());
                            binding.serviceTv.setVisibility(View.GONE);
                            binding.rateTV.setVisibility(View.GONE);
//                            binding.noSelectedService.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Snackbar.make(binding.serviceLayout, getResources().getString(R.string.something_went_wrong), BaseTransientBottomBar.LENGTH_LONG).show();
                    }
                } else {
                    progressDialog.dismiss();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);
                        Snackbar.make(binding.serviceLayout, errorMessage.toString(), BaseTransientBottomBar.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Snackbar.make(binding.serviceLayout, e.getMessage().toString(), BaseTransientBottomBar.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<ServiceListResponse> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(binding.serviceLayout, getResources().getString(R.string.something_went_wrong), BaseTransientBottomBar.LENGTH_LONG).show();
            }
        });
    }

    private void displayServiceDialog() {
        RecyclerView mRecyclerView;
        AlertDialog.Builder builder = new AlertDialog.Builder(ServicePriceActivity.this);
        final LayoutInflater inflater = LayoutInflater.from(ServicePriceActivity.this);
        final View content = inflater.inflate(R.layout.dialog_custom_design, null);
        builder.setView(content);
        mRecyclerView = (RecyclerView) content.findViewById(R.id.dialog_RecyclerView);
        btnDialogNext = (MaterialButton) content.findViewById(R.id.dialog_btnNext);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ServicePriceActivity.this));
        dialogAdapter = new DialogAdapter(getApplicationContext(), mList, this);
        mRecyclerView.setAdapter(dialogAdapter);
        final AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        btnDialogNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedService != null && selectedService.size() > 0) {
                    binding.serviceRecyclerView.setVisibility(View.VISIBLE);
                    binding.serviceTv.setVisibility(View.VISIBLE);
                    binding.rateTV.setVisibility(View.VISIBLE);
//                    binding.noSelectedService.setVisibility(View.GONE);
                    serviceAdapter = new ServicePriceAdapter(getApplicationContext(), selectedService, ServicePriceActivity.this);
                    binding.serviceRecyclerView.setLayoutManager(layoutManager);
                    binding.serviceRecyclerView.setAdapter(serviceAdapter);
                } else {
                    binding.serviceTv.setVisibility(View.GONE);
                    binding.rateTV.setVisibility(View.GONE);
                    binding.serviceRecyclerView.setVisibility(View.GONE);
//                    binding.noSelectedService.setVisibility(View.VISIBLE);
                }
                dialog.dismiss();
            }
        });

    }

    @Override
    public void position(int pos, boolean isChecked,ServiceListDataModel serviceListDataModel) {
        if (isChecked){
            serviceListDataModel.setPrice(orgDataModel.getHourly_rate());
            selectedService.add(serviceListDataModel);
        }else {
            for(int i = 0 ; i < selectedService.size() ; i++){
                if(serviceListDataModel.getId()==selectedService.get(i).getId()){
                    selectedService.remove(i);
                }
            }
        }
        mList.get(pos).setSelected(isChecked);
//        serviceAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void getServicePrice(int postion, String servicePrice ,int id) {
        selectedService.get(postion).setPrice(servicePrice);
        selectedService.get(postion).setId(id);
    }

    @Override
    protected void onDestroy() {
        CommonMethods.setLisstPrefData(Constants.SELECTED_SERVICE,selectedService,ServicePriceActivity.this);
        CommonMethods.setLisstPrefData(Constants.SERVICE_LIST,mList,ServicePriceActivity.this);
        super.onDestroy();
    }
}
