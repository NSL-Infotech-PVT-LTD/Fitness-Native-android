package com.netscape.utrain.activities.athlete;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.PortfolioActivity;
import com.netscape.utrain.activities.SelectServices;
import com.netscape.utrain.activities.SelectedServiceList;
import com.netscape.utrain.activities.ServicePriceActivity;
import com.netscape.utrain.activities.ViewCoachStaffListActivity;
import com.netscape.utrain.activities.organization.OrganizationSignUpActivity;
import com.netscape.utrain.adapters.DialogAdapter;
import com.netscape.utrain.adapters.SportsAdapter;
import com.netscape.utrain.databinding.ActivityChooseSportBinding;
import com.netscape.utrain.model.AthleteUserModel;
import com.netscape.utrain.model.OrgUserDataModel;
import com.netscape.utrain.model.ServiceIdModel;
import com.netscape.utrain.model.SportListModel;
import com.netscape.utrain.model.SportsIdModel;
import com.netscape.utrain.model.ViewCoachListDataModel;
import com.netscape.utrain.response.AthleteSignUpResponse;
import com.netscape.utrain.response.ViewCoachListResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PrefrenceConstant;

import org.json.JSONObject;

import java.io.File;
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
import retrofit2.Retrofit;

import static com.facebook.FacebookSdk.getApplicationContext;

public class ChooseSportActivity extends AppCompatActivity implements SportsAdapter.RecyclePosition {

    public static boolean coachActive = false;
    ActivityChooseSportBinding binding;
    SportsAdapter adapter;
    Retrofitinterface api;
    int mPosition;
    private String activeUserType = "";
    private String createCoachStaff = "";
    private OrgUserDataModel orgDataModel;
    private File mediaStorageDir;

    String mSport, athName, athEmail, athPhone, athAddress, athPwd, athExperience, athAchieve, latitude, longitude;
    //    String phone, address, experience, achievement,fbImage;       // Used to take intent from last page....
    JsonArray jsonArray;
    private List<SportListModel.DataBeanX.DataBean> sportsList = new ArrayList<>();
    private List<SportListModel.DataBeanX.DataBean> sportsListAll = new ArrayList<>();
    private AthleteUserModel athModel;
    private ProgressDialog progressDialog;

    private ViewCoachListDataModel viewCoachListDataModel;
//    private double latitude = 0.0, longitude = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_choose_sport);
        binding = DataBindingUtil.setContentView(ChooseSportActivity.this, R.layout.activity_choose_sport);
        binding.csRecyclerView.setLayoutManager(new LinearLayoutManager(ChooseSportActivity.this));
        api = RetrofitInstance.getClient().create(Retrofitinterface.class);


//        phone = getIntent().getStringExtra("phone");
//        address = getIntent().getStringExtra("address");
//        experience = getIntent().getStringExtra("experience");
//        achievement = getIntent().getStringExtra("achievement");
//        achievement = getIntent().getStringExtra("achievement");
//        fbImage = getIntent().getStringExtra("fbImage");
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        sportsListAll.clear();
        init();

        if (sportsListAll != null && sportsListAll.size() > 0) {
        } else {
            sportsListApi();
        }


        athName = CommonMethods.getPrefData("athleteName", ChooseSportActivity.this);
        athEmail = CommonMethods.getPrefData("athleteEmail", ChooseSportActivity.this);
        athPhone = CommonMethods.getPrefData("athletePhone", ChooseSportActivity.this);
        athAddress = CommonMethods.getPrefData("athleteAddress", ChooseSportActivity.this);
        latitude = CommonMethods.getPrefData("latitude", ChooseSportActivity.this);
        longitude = CommonMethods.getPrefData("longitude", ChooseSportActivity.this);
        athPwd = CommonMethods.getPrefData("athletePassword", ChooseSportActivity.this);
        athExperience = CommonMethods.getPrefData("athleteExperience", ChooseSportActivity.this);
        athAchieve = CommonMethods.getPrefData("athleteAchievement", ChooseSportActivity.this);


        binding.athSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (sportsList != null && sportsList.size() == 0) {

                    Toast.makeText(ChooseSportActivity.this, "Please Select sport", Toast.LENGTH_SHORT).show();

                } else if (coachActive) {
                    orgDataModel.setSport_id(String.valueOf(jsonArray));
                    Intent intent = new Intent(ChooseSportActivity.this, ServicePriceActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra(Constants.OrgSignUpIntent, orgDataModel);
                    intent.putExtra(Constants.ActiveUserType, Constants.TypeCoach);
                    startActivity(intent);
                }  else {
                     if (createCoachStaff!=null && createCoachStaff.equalsIgnoreCase("orgstaffCreate")) {

                        orgCoachRegisterApi();

                    }
                     else {
                         athleteSignUpApi(athEmail, athPwd, athName, athPhone, athAddress, athExperience, athAchieve);
                     }
                }
            }
        });


    }

    private void init() {

        String sportName = CommonMethods.getPrefData(PrefrenceConstant.SPORT_NAME, getApplicationContext());
        Gson gson = new Gson();
        if (sportName != null) {
            if (sportName.isEmpty()) {
            } else {
                Type type = new TypeToken<List<SportListModel.DataBeanX.DataBean>>() {
                }.getType();
                sportsListAll = gson.fromJson(sportName, type);

                StringBuilder builder = new StringBuilder();
                for (SportListModel.DataBeanX.DataBean details : sportsListAll) {
                    builder.append(details.getName() + "\n");

                }

            }
        }
        if (sportsListAll != null && sportsListAll.size() > 0) {
            binding.csRecyclerView.setLayoutManager(new LinearLayoutManager(ChooseSportActivity.this));
            adapter = new SportsAdapter(sportsListAll, ChooseSportActivity.this, ChooseSportActivity.this);
            binding.csRecyclerView.setAdapter(adapter);
            for (int i = 0; i < sportsListAll.size(); i++) {
                if (sportsListAll.get(i).isCheckekd()) {
                    sportsList.add(sportsListAll.get(i));
                }
            }
        }

        if (coachActive) {
            if (getIntent().getExtras() != null) {
                orgDataModel = (OrgUserDataModel) getIntent().getSerializableExtra(Constants.OrgSignUpIntent);
                activeUserType = getIntent().getStringExtra(Constants.ActiveUserType);
                if (activeUserType.equals(Constants.TypeCoach)) {
                    binding.athSignUp.setText(getResources().getString(R.string.one_more_step));
                }
            }
        }

         mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "UtCompressed");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("App", "failed to create directory");
            }
        }

        if (getIntent().getExtras() != null) {
            viewCoachListDataModel = (ViewCoachListDataModel) getIntent().getSerializableExtra("orgCoachStaff");
            createCoachStaff = getIntent().getStringExtra("createStaffType");


            if (createCoachStaff != null)
            if (createCoachStaff.equals("orgstaffCreate")) {
                binding.athSignUp.setText(getResources().getString(R.string.create_coach));
            }
        }

    }

    private void sportsListApi() {
        progressDialog.show();
        Call<SportListModel> call = api.getSportList(Constants.CONTENT_TYPE, "", "");
        call.enqueue(new Callback<SportListModel>() {
            @Override
            public void onResponse(Call<SportListModel> call, Response<SportListModel> response) {
                progressDialog.dismiss();
                if (response.body().isStatus()) {
                    if (response.body() != null) {
                        binding.csRecyclerView.setLayoutManager(new LinearLayoutManager(ChooseSportActivity.this));
                        adapter = new SportsAdapter(response.body().getData().getData(), ChooseSportActivity.this, ChooseSportActivity.this);
                        binding.csRecyclerView.setAdapter(adapter);
                        sportsListAll.addAll(response.body().getData().getData());
                    }
                }
            }

            @Override
            public void onFailure(Call<SportListModel> call, Throwable t) {
                progressDialog.dismiss();

            }
        });
    }

    private void athleteSignUpApi(String email, String password, String name, String phone, String address, String experience, String achievement) {
//        CommonMethods.hideKeyboard(this);

        progressDialog.show();
        MultipartBody.Part userImg = null;
        File myFile = (File) getIntent().getSerializableExtra("image");
        if (myFile != null) {
            userImg = prepareFilePart("profile_image", myFile.getName(), myFile);
//            userImg = MultipartBody.Part.createFormData( "profile_image",photoFile.getName(), RequestBody.create(MediaType.parse("image/*"), photoFile));
        }
        Map<String, RequestBody> requestBodyMap = getDefaultParamsBody(this);
        requestBodyMap.put("name", RequestBody.create(MediaType.parse("multipart/form-data"), name));
        requestBodyMap.put("email", RequestBody.create(MediaType.parse("multipart/form-data"), email));
        requestBodyMap.put("password", RequestBody.create(MediaType.parse("multipart/form-data"), password));
        requestBodyMap.put("phone", RequestBody.create(MediaType.parse("multipart/form-data"), phone));
        requestBodyMap.put("address", RequestBody.create(MediaType.parse("multipart/form-data"), address));
        requestBodyMap.put("experience_detail", RequestBody.create(MediaType.parse("multipart/form-data"), experience));
        requestBodyMap.put("achievements", RequestBody.create(MediaType.parse("multipart/form-data"), achievement));
        requestBodyMap.put("sport_id", RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(jsonArray)));
        requestBodyMap.put("latitude", RequestBody.create(MediaType.parse("multipart/form-data"), latitude));
        requestBodyMap.put("longitude", RequestBody.create(MediaType.parse("multipart/form-data"), longitude));
        requestBodyMap.put("device_type", RequestBody.create(MediaType.parse("multipart/form-data"), Constants.DEVICE_TYPE));
        requestBodyMap.put("device_token", RequestBody.create(MediaType.parse("multipart/form-data"), CommonMethods.getPrefData(PrefrenceConstant.DEVICE_TOKEN, ChooseSportActivity.this)));
        requestBodyMap.put("Content-Type", RequestBody.create(MediaType.parse("multipart/form-data"), Constants.CONTENT_TYPE));
        Call<AthleteSignUpResponse> signUpAthlete = api.registerAthlete(requestBodyMap, userImg);
        signUpAthlete.enqueue(new Callback<AthleteSignUpResponse>() {
            @Override
            public void onResponse(Call<AthleteSignUpResponse> call, Response<AthleteSignUpResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData() != null) {
                            File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "UtCompressed");
                            CommonMethods.deleteDirectory(mediaStorageDir);

                            CommonMethods.setPrefData(PrefrenceConstant.SPORT_NAME, "", getApplicationContext());
                            CommonMethods.setPrefData(PrefrenceConstant.ROLE_PLAY, Constants.Athlete, ChooseSportActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.USER_EMAIL, response.body().getData().getUser().getEmail(), ChooseSportActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.USER_PHONE, response.body().getData().getUser().getPhone(), ChooseSportActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.USER_NAME, response.body().getData().getUser().getName(), ChooseSportActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.USER_ID, response.body().getData().getUser().getId() + "", ChooseSportActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.USER_EXPERIENCE, response.body().getData().getUser().getExperience_detail() + "", ChooseSportActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.USER_ACHIEVE, response.body().getData().getUser().getAchievements() + "", ChooseSportActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.SPORTS_NAME, response.body().getData().getUser().getSport_id() + "", ChooseSportActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.PROFILE_IMAGE, response.body().getData().getUser().getProfile_image() + "", ChooseSportActivity.this);
                            CommonMethods.setPrefData(Constants.AUTH_TOKEN, response.body().getData().getToken() + "", ChooseSportActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.LOGED_IN_USER, PrefrenceConstant.ATHLETE_LOG_IN, ChooseSportActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.ADDRESS, response.body().getData().getUser().getAddress(), ChooseSportActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.PRICE, "90", ChooseSportActivity.this);
                            Intent homeScreen = new Intent(ChooseSportActivity.this, AthleteHomeScreen.class);
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
            public void onFailure(Call<AthleteSignUpResponse> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(binding.serviceLayout, getResources().getString(R.string.something_went_wrong), BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });
    }


    private void orgCoachRegisterApi() {
//        CommonMethods.hideKeyboard(this);

        progressDialog.show();
        MultipartBody.Part userImg = null;
        if (viewCoachListDataModel.getoCoachProfileImg() != null) {
            userImg = MultipartBody.Part.createFormData("profile_image", viewCoachListDataModel.getoCoachProfileImg().getName(), RequestBody.create(MediaType.parse("image/*"), viewCoachListDataModel.getoCoachProfileImg()));
//            userImg = MultipartBody.Part.createFormData( "profile_image",photoFile.getName(), RequestBody.create(MediaType.parse("image/*"), photoFile));
        }
        Map<String, RequestBody> requestBodyMap = getDefaultParamsBody(this);
        requestBodyMap.put("name", RequestBody.create(MediaType.parse("multipart/form-data"), viewCoachListDataModel.getName()));
        requestBodyMap.put("bio", RequestBody.create(MediaType.parse("multipart/form-data"), viewCoachListDataModel.getBio()));
        requestBodyMap.put("profession", RequestBody.create(MediaType.parse("multipart/form-data"), viewCoachListDataModel.getProfession()));
        requestBodyMap.put("expertise_years", RequestBody.create(MediaType.parse("multipart/form-data"), viewCoachListDataModel.getExpertise_years()));
        requestBodyMap.put("experience_detail", RequestBody.create(MediaType.parse("multipart/form-data"), viewCoachListDataModel.getExperience_detail()));
        requestBodyMap.put("training_service_detail", RequestBody.create(MediaType.parse("multipart/form-data"), viewCoachListDataModel.getTraining_service_detail()));
        requestBodyMap.put("hourly_rate", RequestBody.create(MediaType.parse("multipart/form-data"), viewCoachListDataModel.getHourly_rate()));
        requestBodyMap.put("sport_id", RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(jsonArray)));
        requestBodyMap.put("device_type", RequestBody.create(MediaType.parse("multipart/form-data"), Constants.DEVICE_TYPE));
        requestBodyMap.put("Content-Type", RequestBody.create(MediaType.parse("multipart/form-data"), Constants.CONTENT_TYPE));
        Call<ViewCoachListResponse> orgCoachSignUp = api.getOrgCoachRegister(userImg, "Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, ChooseSportActivity.this),requestBodyMap);
        orgCoachSignUp.enqueue(new Callback<ViewCoachListResponse>() {
            @Override
            public void onResponse(Call<ViewCoachListResponse> call, Response<ViewCoachListResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData() != null) {


                            Intent homeScreen = new Intent(ChooseSportActivity.this, ViewCoachStaffListActivity.class);
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
            public void onFailure(Call<ViewCoachListResponse> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(binding.serviceLayout, getResources().getString(R.string.something_went_wrong), BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });
    }


    protected MultipartBody.Part prepareFilePart(String partName, String fileName, File file) {
        if (file == null || !file.exists())
            return null;

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        if (TextUtils.isEmpty(fileName))
            fileName = file.getName();

        return MultipartBody.Part.createFormData(partName, fileName, requestFile);
    }

    protected Map<String, RequestBody> getDefaultParamsBody(Context context) {
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
//		requestBodyMap.put("platform", RequestBody.create(MediaType.parse("multipart/form-data"), "Android"));
//		requestBodyMap.put("device_id", RequestBody.create(MediaType.parse("multipart/form-data"), StringHelper.getDeviceId(context)));
        return requestBodyMap;
    }

    public void position(int position) {
        mPosition = position;
    }

    @Override
    public void sportPosition(int pos, boolean ischecked, SportListModel.DataBeanX.DataBean list) {

        if (ischecked) {
            sportsList.add(list);
            sportsListAll.get(pos).setCheckekd(ischecked);
        } else {
            sportsListAll.get(pos).setCheckekd(ischecked);
            for (int i = 0; i < sportsList.size(); i++) {
                if (list.getId() == sportsList.get(i).getId()) {
                    sportsList.remove(i);
                }
            }
        }
//        if (sportsList != null && sportsList.size() > 0) {
        jsonArray = (JsonArray) new Gson().toJsonTree(sportsList);


//            Toast.makeText(this, ""+jsonArray, Toast.LENGTH_SHORT).show();\
//        }
    }
    //if (SelectedServiceList.getInstance().getList() != null && SelectedServiceList.getInstance().getList().size() > 0) {
    //                    jsonArray = (JsonArray) new Gson().toJsonTree(SelectedServiceList.getInstance().getList());

    private void storeServiceIds(List<SportListModel.DataBeanX.DataBean> list) {
        Gson gson = new Gson();
        String listData = gson.toJson(list);
        CommonMethods.setPrefData(PrefrenceConstant.SPORT_NAME, listData, getApplicationContext());
    }

    @Override
    protected void onDestroy() {
        storeServiceIds(sportsListAll);
        super.onDestroy();
    }


}
