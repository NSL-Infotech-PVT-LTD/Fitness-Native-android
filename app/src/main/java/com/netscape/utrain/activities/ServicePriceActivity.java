package com.netscape.utrain.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.athlete.AthleteHomeScreen;
import com.netscape.utrain.activities.coach.CoachDashboard;
import com.netscape.utrain.activities.organization.OrgHomeScreen;
import com.netscape.utrain.adapters.DialogAdapter;
import com.netscape.utrain.adapters.ServicePriceAdapter;
import com.netscape.utrain.databinding.ActivityServicePriceBinding;
import com.netscape.utrain.model.OrgUserDataModel;
import com.netscape.utrain.model.ServiceIdModel;
import com.netscape.utrain.model.ServiceListDataModel;
import com.netscape.utrain.response.CoachSignUpResponse;
import com.netscape.utrain.response.OrgSignUpResponse;
import com.netscape.utrain.response.ServiceListResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PrefrenceConstant;

import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Type;
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

import static com.facebook.FacebookSdk.getApplicationContext;

public class ServicePriceActivity extends AppCompatActivity implements View.OnClickListener, ServicePriceAdapter.ServicePriceInterface {
    MaterialTextView addService;
    RecyclerView.LayoutManager layoutManager;
    ServicePriceAdapter serviceAdapter;
    ArrayList<ServiceListDataModel> selectedService = new ArrayList<>();
    ArrayList<ServiceListDataModel> mList = new ArrayList<>();
    private ArrayList<ServiceListDataModel> sList = new ArrayList<>();
    ServiceListDataModel serviceModel;
    DialogAdapter dialogAdapter;
    AlertDialog alertDialog;
    MaterialButton btnDialogNext;
    int mPosition = 0;
    JsonArray jsonArray;
    public static boolean updateServices=false;
    private ActivityServicePriceBinding binding;
    private ProgressDialog progressDialog;
    private Retrofitinterface retrofitinterface;
    private OrgUserDataModel orgDataModel;
    private String activeUserType = "";
    private File photoFile = null;
    private boolean userPrice = false;
    private int SELECTED_SERVICES = 11;
    private List<ServiceIdModel> servicesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_service_price);
        orgDataModel=new OrgUserDataModel();
        layoutManager = new LinearLayoutManager(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);
//        selectedService.clear();
        init();
        if (updateServices){
            binding.serviceSummaryTitle.setText("Update Services");
            binding.servicePriceNextBtn.setText(("Update"));
            String price=CommonMethods.getPrefData(PrefrenceConstant.PRICE,ServicePriceActivity.this);
            orgDataModel.setHourly_rate(price);

            getService();
        }
    }

    private void init() {
//        selectedService = CommonMethods.getListPrefrence(Constants.SELECTED_SERVICE, ServicePriceActivity.this);
        mList = CommonMethods.getListPrefrence(Constants.SERVICE_LIST, ServicePriceActivity.this);
        binding.addServiceBtn.setOnClickListener(this);
        binding.servicePriceNextBtn.setOnClickListener(this);
        binding.addServiceCenterBtn.setOnClickListener(this);
        binding.servicePriceBackArrowImg.setOnClickListener(this);

        if (getIntent().getExtras() != null) {
            orgDataModel = (OrgUserDataModel) getIntent().getSerializableExtra(Constants.OrgSignUpIntent);
            activeUserType = getIntent().getStringExtra(Constants.ActiveUserType);
            if (activeUserType.equals(Constants.TypeCoach)) {
                binding.servicePriceNextBtn.setText(getResources().getString(R.string.submit));
            }
            if (activeUserType.equals(Constants.TypeOrganization)) {
                binding.servicePriceNextBtn.setText(getResources().getString(R.string.one_more_step));
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addServiceBtn:
                passTheIntent();
                break;
            case R.id.addServiceCenterBtn:
                passTheIntent();
                break;
            case R.id.servicePriceNextBtn:
                if (SelectedServiceList.getInstance().getList() != null && SelectedServiceList.getInstance().getList().size() > 0) {
                    jsonArray = (JsonArray) new Gson().toJsonTree(SelectedServiceList.getInstance().getList());
                   if (updateServices){
                       if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, getApplicationContext()).equalsIgnoreCase(Constants.Organizer))
                           OrgServiceUpdateApi();
                       else if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, getApplicationContext()).equalsIgnoreCase(Constants.Coach))
                           CoachUpdateApi();
                   }
                    if (activeUserType.equalsIgnoreCase(Constants.TypeCoach)) {
                        CoachSignUpApi();
                    }
                    if (activeUserType.equalsIgnoreCase(Constants.TypeOrganization)) {
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
            case R.id.servicePriceBackArrowImg:
                finish();
                break;
        }
    }

    private void passTheIntent() {
        Intent intent = new Intent(ServicePriceActivity.this, SelectServices.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(Constants.OrgSignUpIntent, orgDataModel);
        Bundle args = new Bundle();
        args.putSerializable("SelectedService",sList);
        intent.putExtras(args);
        SelectServices.updateService=true;
        startActivity(intent);
    }

    private void CoachSignUpApi() {
//        CommonMethods.hideKeyboard(this);
        progressDialog.show();
        MultipartBody.Part userImg = null;
        if (orgDataModel.getProfile_img() != null) {
//            userImg = prepareFilePart("profile_image", photoFile.getName(), photoFile);
            userImg = MultipartBody.Part.createFormData("profile_image", orgDataModel.getProfile_img().getName(), RequestBody.create(MediaType.parse("image/*"), orgDataModel.getProfile_img()));
        }
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        requestBodyMap.put("name", RequestBody.create(MediaType.parse("multipart/form-data"), orgDataModel.getName()));
        requestBodyMap.put("email", RequestBody.create(MediaType.parse("multipart/form-data"), orgDataModel.getEmail()));
        requestBodyMap.put("password", RequestBody.create(MediaType.parse("multipart/form-data"), orgDataModel.getPassword()));
        requestBodyMap.put("phone", RequestBody.create(MediaType.parse("multipart/form-data"), orgDataModel.getPhone()));
        requestBodyMap.put("location", RequestBody.create(MediaType.parse("multipart/form-data"), orgDataModel.getLocation()));
        requestBodyMap.put("latitude", RequestBody.create(MediaType.parse("multipart/form-data"), orgDataModel.getLatitude()));
        requestBodyMap.put("longitude", RequestBody.create(MediaType.parse("multipart/form-data"), orgDataModel.getLatitude()));
        requestBodyMap.put("business_hour_starts", RequestBody.create(MediaType.parse("multipart/form-data"), orgDataModel.getBusiness_hour_starts()));
        requestBodyMap.put("business_hour_ends", RequestBody.create(MediaType.parse("multipart/form-data"), orgDataModel.getBusiness_hour_ends()));
        requestBodyMap.put("bio", RequestBody.create(MediaType.parse("multipart/form-data"), orgDataModel.getBio()));
        requestBodyMap.put("profession", RequestBody.create(MediaType.parse("multipart/form-data"), orgDataModel.getProfessionType()));
        requestBodyMap.put("experience_detail", RequestBody.create(MediaType.parse("multipart/form-data"), orgDataModel.getExperienceDetail()));
        requestBodyMap.put("training_service_detail", RequestBody.create(MediaType.parse("multipart/form-data"), orgDataModel.getTrainingDetail()));
        requestBodyMap.put("service_ids", RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(jsonArray)));
        requestBodyMap.put("expertise_years", RequestBody.create(MediaType.parse("multipart/form-data"), orgDataModel.getExpertise_years()));
        requestBodyMap.put("sport_id", RequestBody.create(MediaType.parse("multipart/form-data"), orgDataModel.getSport_id()));
        requestBodyMap.put("hourly_rate", RequestBody.create(MediaType.parse("multipart/form-data"), orgDataModel.getHourly_rate()));
        requestBodyMap.put("device_type", RequestBody.create(MediaType.parse("multipart/form-data"), Constants.DEVICE_TYPE));
        requestBodyMap.put("device_token", RequestBody.create(MediaType.parse("multipart/form-data"), CommonMethods.getPrefData(PrefrenceConstant.DEVICE_TOKEN, getApplicationContext())));
//        requestBodyMap.put("device_token", RequestBody.create(MediaType.parse("multipart/form-data"), CommonMethods.getPrefData(PrefrenceConstant.DEVICE_TOKEN, getApplicationContext())));
        Call<CoachSignUpResponse> signUpAthlete = retrofitinterface.registerCoach(requestBodyMap, userImg);
        signUpAthlete.enqueue(new Callback<CoachSignUpResponse>() {
            @Override
            public void onResponse(Call<CoachSignUpResponse> call, Response<CoachSignUpResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData() != null) {
                            CommonMethods.setPrefData(PrefrenceConstant.SPORT_NAME, "", getApplicationContext());
                            CommonMethods.setPrefData(PrefrenceConstant.ROLE_PLAY, Constants.Coach, ServicePriceActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.USER_EMAIL, response.body().getData().getUser().getEmail(), ServicePriceActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.USER_PHONE, response.body().getData().getUser().getPhone(), ServicePriceActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.USER_NAME, response.body().getData().getUser().getName(), ServicePriceActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.USER_ID, response.body().getData().getUser().getId() + "", ServicePriceActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.USER_EXPERIENCE, response.body().getData().getUser().getExperience_detail() + "", ServicePriceActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.USER_TRAINING_DETAIL, response.body().getData().getUser().getTraining_service_detail() + "", ServicePriceActivity.this);
                            CommonMethods.setPrefData(Constants.AUTH_TOKEN, response.body().getData().getToken() + "", ServicePriceActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.LOGED_IN_USER, PrefrenceConstant.COACH_LOG_IN, ServicePriceActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.PROFILE_IMAGE, Constants.COACH_IMAGE_BASE_URL + response.body().getData().getUser().getProfile_image() + "", ServicePriceActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.SPORTS_NAME, response.body().getData().getUser().getSport_id(), getApplicationContext());
                            CommonMethods.setPrefData(PrefrenceConstant.PRICE, response.body().getData().getUser().getHourly_rate() + "", ServicePriceActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.EXPERTISE_YEAR, response.body().getData().getUser().getExpertise_years() + "", ServicePriceActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.ADDRESS, response.body().getData().getUser().getLocation() + "", ServicePriceActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.BUSINESS_HOUR_START, response.body().getData().getUser().getBusiness_hour_starts() + "", ServicePriceActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.BIO, response.body().getData().getUser().getBio() + "", ServicePriceActivity.this);
                            servicesList.addAll(response.body().getData().getUser().getService_ids());
                            storeServiceIds(servicesList);
                            CommonMethods.setPrefData(PrefrenceConstant.USER_LAT, response.body().getData().getUser().getLatitude() + "", ServicePriceActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.USER_LONG, response.body().getData().getUser().getLongitude() + "", ServicePriceActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.BUSINESS_HOUR_ENDS, response.body().getData().getUser().getBusiness_hour_ends() + "", ServicePriceActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.ACHIVEMENTS, response.body().getData().getUser().getAchievements() + "", ServicePriceActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.PROFESSION, response.body().getData().getUser().getProfession() + "", ServicePriceActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.EXPERIENCE_DETAILS, response.body().getData().getUser().getExperience_detail() + "", ServicePriceActivity.this);

                            Intent homeScreen = new Intent(getApplicationContext(), CoachDashboard.class);
                            homeScreen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(homeScreen);
                        }
                    } else {
                        Snackbar.make(binding.serviceLayout, response.body().getError().getError_message().getMessage().toString(), BaseTransientBottomBar.LENGTH_SHORT).show();
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
                orgDataModel.getExperienceDetail(),
                orgDataModel.getTrainingDetail(),
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
                            Intent homeScreen = new Intent(getApplicationContext(), AthleteHomeScreen.class);
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
                            mList.addAll(response.body().getData().getData());
                            binding.serviceTv.setVisibility(View.GONE);
                            binding.rateTV.setVisibility(View.GONE);
                        }
                    } else {
                        Snackbar.make(binding.serviceLayout, getResources().getString(R.string.something_went_wrong), BaseTransientBottomBar.LENGTH_LONG).show();
                    }
                } else {
                    progressDialog.dismiss();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);
                        Snackbar.make(binding.serviceLayout, errorMessage, BaseTransientBottomBar.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Snackbar.make(binding.serviceLayout, e.getMessage(), BaseTransientBottomBar.LENGTH_LONG).show();
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

//    private void displayServiceDialog() {
//        RecyclerView mRecyclerView;
//        AlertDialog.Builder builder = new AlertDialog.Builder(ServicePriceActivity.this);
//        final LayoutInflater inflater = LayoutInflater.from(ServicePriceActivity.this);
//        final View content = inflater.inflate(R.layout.dialog_custom_design, null);
//        builder.setView(content);
//        mRecyclerView = (RecyclerView) content.findViewById(R.id.dialog_RecyclerView);
//        btnDialogNext = (MaterialButton) content.findViewById(R.id.dialog_btnNext);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(ServicePriceActivity.this));
////        dialogAdapter = new DialogAdapter(getApplicationContext(), mList, this);
//        mRecyclerView.setAdapter(dialogAdapter);
//        final AlertDialog dialog = builder.create();
//        dialog.show();
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        btnDialogNext.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (selectedService != null && selectedService.size() > 0) {
//                    binding.serviceRecyclerView.setVisibility(View.VISIBLE);
//                    binding.serviceTv.setVisibility(View.VISIBLE);
//                    binding.rateTV.setVisibility(View.VISIBLE);
//                    binding.noSelectedService.setVisibility(View.GONE);
//                    serviceAdapter = new ServicePriceAdapter(getApplicationContext(), selectedService, ServicePriceActivity.this);
//                    binding.serviceRecyclerView.setLayoutManager(layoutManager);
//                    binding.serviceRecyclerView.setAdapter(serviceAdapter);
//                } else {
//                    binding.serviceTv.setVisibility(View.GONE);
//                    binding.rateTV.setVisibility(View.GONE);
//                    binding.serviceRecyclerView.setVisibility(View.GONE);
//                    binding.noSelectedService.setVisibility(View.VISIBLE);
//                }
//                dialog.dismiss();
//            }
//        });
//    }

//    public void setDataToSelected() {
//        for (int s = 0; s < mList.size(); s++) {
//            if (selectedService!=null && selectedService.size()>0) {
//                if (mList.get(s).getId()==selectedService.get(s).getId())
//                selectedService.add(mList.get(s));
//                selectedService.get(s).setPrice(orgDataModel.getHourly_rate());
//            }
//        }
//        if (selectedService !=null && selectedService.size()>0){
//            for (int j=0;j<selectedService.size();j++){
//                for (int k=0;k<mList.size();k++){
//                    if (mList.get(k).isSelected()) {
//                        if (selectedService.get(j).getId() == mList.get(k).getId()) {
//
//                        }
//                    }
//                }
//            }
//
//        }else {
//            for (int s = 0; s < mList.size(); s++) {
//                selectedService.add(mList.get(s));
//                selectedService.get(s).setPrice(orgDataModel.getHourly_rate());
//            }
//        }
//
//        setTheAdapter();
////        serviceAdapter.notifyDataSetChanged();
//    }

    private void setTheAdapter() {
        if (SelectedServiceList.getInstance().getList() != null && SelectedServiceList.getInstance().getList().size() > 0) {
            binding.serviceRecyclerView.setVisibility(View.VISIBLE);
            binding.addServiceBtn.setVisibility(View.VISIBLE);
            binding.addServiceTv.setVisibility(View.VISIBLE);
            binding.addMoreServiceTv.setVisibility(View.VISIBLE);
            binding.serviceTv.setVisibility(View.VISIBLE);
            binding.rateTV.setVisibility(View.VISIBLE);
            binding.servicePriceNextBtn.setBackgroundColor(getResources().getColor(R.color.colorGreen));
            binding.servicePriceNextBtn.setTextColor(getResources().getColor(R.color.colorBlack));
            binding.addServiceCenterBtn.setVisibility(View.GONE);
            binding.addServiceCenterTv.setVisibility(View.GONE);
            binding.addMoreServiceCenterTv.setVisibility(View.GONE);
            serviceAdapter = new ServicePriceAdapter(getApplicationContext(), SelectedServiceList.getInstance().getList(), ServicePriceActivity.this);
            binding.serviceRecyclerView.setLayoutManager(layoutManager);
            binding.serviceRecyclerView.setAdapter(serviceAdapter);
        } else {
            binding.serviceTv.setVisibility(View.GONE);
            binding.rateTV.setVisibility(View.GONE);
            binding.servicePriceNextBtn.setBackgroundColor(getResources().getColor(R.color.lightGrayBtn));
            binding.servicePriceNextBtn.setTextColor(getResources().getColor(R.color.lightGrayFont));
            binding.serviceRecyclerView.setVisibility(View.GONE);
            binding.addServiceCenterBtn.setVisibility(View.VISIBLE);
            binding.addServiceCenterTv.setVisibility(View.VISIBLE);
            binding.addMoreServiceCenterTv.setVisibility(View.VISIBLE);
            binding.addServiceBtn.setVisibility(View.GONE);
            binding.addServiceTv.setVisibility(View.GONE);
            binding.addMoreServiceTv.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        setTheAdapter();
        super.onResume();
    }

    @Override
    public void getServicePrice(int postion, String servicePrice, int id) {
        if (Integer.parseInt(servicePrice)>5) {
            binding.servicePriceNextBtn.setClickable(true);
            SelectedServiceList.getInstance().getList().get(postion).setPrice(servicePrice);
            SelectedServiceList.getInstance().getList().get(postion).setId(id);
            binding.servicePriceNextBtn.setBackgroundColor(getResources().getColor(R.color.colorGreen));
        }else{
            binding.servicePriceNextBtn.setClickable(false);
            binding.servicePriceNextBtn.setBackgroundColor(getResources().getColor(R.color.lightGrayBtn));
            Toast.makeText(this, "Enter Price  more than  $5", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        updateServices=false;
//        CommonMethods.setLisstPrefData(Constants.SELECTED_SERVICE, selectedService, ServicePriceActivity.this);
        super.onDestroy();
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == SELECTED_SERVICES && resultCode == RESULT_OK && data != null) {
//            mList = (ArrayList<ServiceListDataModel>) data.getSerializableExtra("result");
//            if (mList != null && mList.size() > 0) {
//                setDataToSelected();
//            }
//        }
//
//    }

    private void storeServiceIds(List<ServiceIdModel> list) {
        Gson gson = new Gson();
        String listData = gson.toJson(list);
        CommonMethods.setPrefData(PrefrenceConstant.SERVICE_IDS, listData, getApplicationContext());
    }
    private void getService() {
        String serviceName = CommonMethods.getPrefData(PrefrenceConstant.SERVICE_IDS, getApplicationContext());
        Gson gson = new Gson();

        if (serviceName != null) {
            if (serviceName.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Service Not Found", Toast.LENGTH_SHORT).show();
            } else {
                Type type = new TypeToken<List<ServiceListDataModel>>() {
                }.getType();
                sList = gson.fromJson(serviceName, type);
                if (sList!=null && sList.size()>0){
                    SelectedServiceList.getInstance().getList().addAll(sList);
//                    CommonMethods.setLisstPrefData(Constants.SERVICE_LIST, sList, ServicePriceActivity.this);

                    setTheAdapter();
                }

                StringBuilder builder = new StringBuilder();
                for (ServiceListDataModel details : sList) {
                    builder.append(details.getName() + "\n");

                }

            }
        }
    }



    private void OrgServiceUpdateApi() {
        progressDialog.show();
        MultipartBody.Part userImg = null;
        if (orgDataModel.getProfile_img() != null) {
            userImg = MultipartBody.Part.createFormData("profile_image", orgDataModel.getProfile_img().getName(), RequestBody.create(MediaType.parse("image/*"), orgDataModel.getProfile_img()));
        }
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        requestBodyMap.put("service_ids", RequestBody.create(MediaType.parse("multipart/form-data"), jsonArray.toString()));
        requestBodyMap.put("device_type", RequestBody.create(MediaType.parse("multipart/form-data"), Constants.DEVICE_TYPE));
        requestBodyMap.put("device_token", RequestBody.create(MediaType.parse("multipart/form-data"), CommonMethods.getPrefData(PrefrenceConstant.DEVICE_TOKEN, getApplicationContext())));
        requestBodyMap.put("Content-Type", RequestBody.create(MediaType.parse("multipart/form-data"), Constants.CONTENT_TYPE));


        Call<OrgSignUpResponse> signUpAthlete = retrofitinterface.updateOrgBasicInfo("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getApplicationContext()),requestBodyMap , userImg);
        signUpAthlete.enqueue(new Callback<OrgSignUpResponse>() {
            @Override
            public void onResponse(Call<OrgSignUpResponse> call, Response<OrgSignUpResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData() != null) {

                            for (int i = 0; i < response.body().getData().getUser().getRoles().size(); i++) {
                                String role = response.body().getData().getUser().getRoles().get(i).getName();
                                if (Constants.Organizer.equalsIgnoreCase(role)) {
                                    CommonMethods.setPrefData(PrefrenceConstant.USER_NAME, response.body().getData().getUser().getName(), ServicePriceActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.USER_EMAIL, response.body().getData().getUser().getEmail(), ServicePriceActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.USER_PHONE, response.body().getData().getUser().getPhone(), ServicePriceActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.USER_ID, response.body().getData().getUser().getId() + "", ServicePriceActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.ADDRESS, response.body().getData().getUser().getLocation() + "", ServicePriceActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.PROFILE_IMAGE, Constants.ORG_IMAGE_BASE_URL + response.body().getData().getUser().getProfile_image() + "", ServicePriceActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.USER_LAT, response.body().getData().getUser().getLatitude() + "", ServicePriceActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.USER_LONG, response.body().getData().getUser().getLongitude() + "", ServicePriceActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.BUSINESS_HOUR_ENDS, response.body().getData().getUser().getBusiness_hour_ends() + "", ServicePriceActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.BUSINESS_HOUR_START, response.body().getData().getUser().getBusiness_hour_starts() + "", ServicePriceActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.BIO, response.body().getData().getUser().getBio() + "", ServicePriceActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.EXPERTISE_YEAR, response.body().getData().getUser().getExpertise_years(), ServicePriceActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.USER_EXPERIENCE, response.body().getData().getUser().getExperience_detail() + "", ServicePriceActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.PRICE, response.body().getData().getUser().getHourly_rate() + "", ServicePriceActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.PORT_FOLIO_IMAGES, response.body().getData().getUser().getPortfolio_image() + "", ServicePriceActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.ACHIVEMENTS, response.body().getData().getUser().getAchievements() + "", ServicePriceActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.PROFESSION, response.body().getData().getUser().getProfession() + "", ServicePriceActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.EXPERIENCE_DETAILS, response.body().getData().getUser().getExperience_detail() + "", ServicePriceActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.USER_TRAINING_DETAIL, response.body().getData().getUser().getTraining_service_detail() + "", ServicePriceActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.LOGED_IN_USER, PrefrenceConstant.ORG_LOG_IN, ServicePriceActivity.this);
                                    servicesList.addAll(response.body().getData().getUser().getService_ids());
                                    storeServiceIds(servicesList);

                                    Intent homeScreen = new Intent(getApplicationContext(), OrgHomeScreen.class);
                                    homeScreen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(homeScreen);
                                }
                            }
                        }else {
                            Snackbar.make(binding.serviceLayout, response.body().getError().getError_message().getMessage().toString(), BaseTransientBottomBar.LENGTH_SHORT).show();

                        }
                    } else {
                        Snackbar.make(binding.serviceLayout, response.body().getError().getError_message().getMessage().toString(), BaseTransientBottomBar.LENGTH_SHORT).show();
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
            public void onFailure(Call<OrgSignUpResponse> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(binding.serviceLayout, "" + t, BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });
    }
    private void CoachUpdateApi() {
        progressDialog.show();
        MultipartBody.Part userImg = null;
        if (orgDataModel.getProfile_img() != null) {
            userImg = MultipartBody.Part.createFormData("profile_image", orgDataModel.getProfile_img().getName(), RequestBody.create(MediaType.parse("image/*"), orgDataModel.getProfile_img()));
        }
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        requestBodyMap.put("service_ids", RequestBody.create(MediaType.parse("multipart/form-data"), jsonArray.toString()));
        requestBodyMap.put("device_type", RequestBody.create(MediaType.parse("multipart/form-data"), Constants.DEVICE_TYPE));
        requestBodyMap.put("device_token", RequestBody.create(MediaType.parse("multipart/form-data"), CommonMethods.getPrefData(PrefrenceConstant.DEVICE_TOKEN, getApplicationContext())));
        requestBodyMap.put("Content-Type", RequestBody.create(MediaType.parse("multipart/form-data"), Constants.CONTENT_TYPE));


        Call<CoachSignUpResponse> signUpAthlete = retrofitinterface.updateCoachBasicInfo("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getApplicationContext()),requestBodyMap , userImg);
        signUpAthlete.enqueue(new Callback<CoachSignUpResponse>() {
            @Override
            public void onResponse(Call<CoachSignUpResponse> call, Response<CoachSignUpResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData() != null) {
                            for (int i = 0; i < response.body().getData().getUser().getRoles().size(); i++) {
                                String role = response.body().getData().getUser().getRoles().get(i).getName();
                                if (Constants.Coach.equalsIgnoreCase(role)) {
                                    CommonMethods.setPrefData(PrefrenceConstant.USER_NAME, response.body().getData().getUser().getName(), ServicePriceActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.USER_EMAIL, response.body().getData().getUser().getEmail(), ServicePriceActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.USER_PHONE, response.body().getData().getUser().getPhone(), ServicePriceActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.USER_ID, response.body().getData().getUser().getId() + "", ServicePriceActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.ADDRESS, response.body().getData().getUser().getLocation() + "", ServicePriceActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.PROFILE_IMAGE, Constants.COACH_IMAGE_BASE_URL + response.body().getData().getUser().getProfile_image() + "", ServicePriceActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.USER_LAT, response.body().getData().getUser().getLatitude() + "", ServicePriceActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.USER_LONG, response.body().getData().getUser().getLongitude() + "", ServicePriceActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.BUSINESS_HOUR_ENDS, response.body().getData().getUser().getBusiness_hour_ends() + "", ServicePriceActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.BUSINESS_HOUR_START, response.body().getData().getUser().getBusiness_hour_starts() + "", ServicePriceActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.BIO, response.body().getData().getUser().getBio() + "", ServicePriceActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.EXPERTISE_YEAR, response.body().getData().getUser().getExpertise_years(), ServicePriceActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.USER_EXPERIENCE, response.body().getData().getUser().getExperience_detail() + "", ServicePriceActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.PRICE, response.body().getData().getUser().getHourly_rate() + "", ServicePriceActivity.this);
//                                    CommonMethods.setPrefData(PrefrenceConstant.PORT_FOLIO_IMAGES, response.body().getData().getUser().getPortfolio_image() + "", ServicePriceActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.ACHIVEMENTS, response.body().getData().getUser().getAchievements() + "", ServicePriceActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.PROFESSION, response.body().getData().getUser().getProfession() + "", ServicePriceActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.EXPERIENCE_DETAILS, response.body().getData().getUser().getExperience_detail() + "", ServicePriceActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.USER_TRAINING_DETAIL, response.body().getData().getUser().getTraining_service_detail() + "", ServicePriceActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.LOGED_IN_USER, PrefrenceConstant.ORG_LOG_IN, ServicePriceActivity.this);
                                    servicesList.addAll(response.body().getData().getUser().getService_ids());
                                    storeServiceIds(servicesList);

                                    Intent homeScreen = new Intent(getApplicationContext(), CoachDashboard.class);
                                    homeScreen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(homeScreen);
                                }
                            }
                        }else {
                            Snackbar.make(binding.serviceLayout, response.body().getError().getError_message().getMessage().toString(), BaseTransientBottomBar.LENGTH_SHORT).show();
                        }
                    } else {
                        Snackbar.make(binding.serviceLayout, response.body().getError().getError_message().getMessage().toString(), BaseTransientBottomBar.LENGTH_SHORT).show();
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
                Snackbar.make(binding.serviceLayout, "" + t, BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });
    }

}
