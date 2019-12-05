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
import android.os.Handler;
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
import com.netscape.utrain.activities.MyProfile;
import com.netscape.utrain.activities.PortfolioActivity;
import com.netscape.utrain.activities.SelectServices;
import com.netscape.utrain.activities.SelectedServiceList;
import com.netscape.utrain.activities.ServicePriceActivity;
import com.netscape.utrain.activities.UpdateProfileActivity;
import com.netscape.utrain.activities.ViewCoachStaffListActivity;
import com.netscape.utrain.activities.coach.CoachDashboard;
import com.netscape.utrain.activities.organization.OrgHomeScreen;
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
import com.netscape.utrain.response.CoachSignUpResponse;
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

public class ChooseSportActivity extends AppCompatActivity implements SportsAdapter.RecyclePosition, View.OnClickListener {

    public static boolean coachActive = false;
    public static boolean athUpdate = false;
    ActivityChooseSportBinding binding;
    SportsAdapter adapter;
    Retrofitinterface api;
    int mPosition;
    String mSport, athName, athEmail, athPhone, athAddress, athPwd, athExperience, athAchieve, latitude, longitude;
    //    String phone, address, experience, achievement,fbImage;       // Used to take intent from last page....
    JsonArray jsonArray;
    private String activeUserType = "";
    private String createCoachStaff = "";
    private OrgUserDataModel orgDataModel;
    private File mediaStorageDir;
    private List<SportListModel.DataBeanX.DataBean> sportsList = new ArrayList<>();
    private List<SportListModel.DataBeanX.DataBean> sportsListAll = new ArrayList<>();
    private List<SportListModel.DataBeanX.DataBean> selecteSports;
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
        inIt();


//        phone = getIntent().getStringExtra("phone");
//        address = getIntent().getStringExtra("address");
//        experience = getIntent().getStringExtra("experience");
//        achievement = getIntent().getStringExtra("achievement");
//        achievement = getIntent().getStringExtra("achievement");
//        fbImage = getIntent().getStringExtra("fbImage");
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.setCancelable(false);
        sportsListAll.clear();
        init();
        if (athUpdate) {

            binding.athSignUp.setText("Update");
            if (sportsListAll != null && sportsListAll.size() > 0) {
            } else {
                sportsListApi();
            }
        } else {


            if (sportsListAll != null && sportsListAll.size() > 0) {
            } else {
                sportsListApi();
            }
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
                setClikFalse();


                if (sportsList != null && sportsList.size() == 0) {

                    Toast.makeText(ChooseSportActivity.this, "Please Select sport", Toast.LENGTH_SHORT).show();

                } else if (coachActive) {
                    orgDataModel.setSport_id(String.valueOf(jsonArray));
                    Intent intent = new Intent(ChooseSportActivity.this, ServicePriceActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra(Constants.OrgSignUpIntent, orgDataModel);
                    intent.putExtra(Constants.ActiveUserType, Constants.TypeCoach);
                    startActivity(intent);
                } else if (athUpdate) {
                    if (sportsList != null && sportsList.size() > 0) {
                         if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, getApplicationContext()).equalsIgnoreCase(Constants.Coach)){
                             CoachUpdateApi();
                         }else {
                             hitUpdateAthleteDetailApi();
                         }
                    } else {
                        Toast.makeText(ChooseSportActivity.this, "Choose At least one sport", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (createCoachStaff != null && createCoachStaff.equalsIgnoreCase("orgstaffCreate")) {

                        orgCoachRegisterApi();

                    } else {
                        athleteSignUpApi(athEmail, athPwd, athName, athPhone, athAddress, athExperience, athAchieve);
                    }
                }
            }
        });
        athleteUpdate();


    }

    private void athleteUpdate() {

        if (getIntent().hasExtra("update"))
            binding.athSignUp.setText(getResources().getString(R.string.update));
        String name = getIntent().getStringExtra("name");
        String email = getIntent().getStringExtra("email");
        String phone = getIntent().getStringExtra("phone");
        String address = getIntent().getStringExtra("address");
        String experience = getIntent().getStringExtra("experience");
        String achievement = getIntent().getStringExtra("achievement");
//        File photo = getIntent().getSerializableExtra("photo"); // pending....... 19-11-19....

    }


    private void init() {
        getSelectedList();

        if (sportsListAll != null && sportsListAll.size() > 0) {
            binding.csRecyclerView.setLayoutManager(new LinearLayoutManager(ChooseSportActivity.this));
            adapter = new SportsAdapter(sportsListAll, ChooseSportActivity.this, ChooseSportActivity.this);
            binding.csRecyclerView.setAdapter(adapter);
            for (int i = 0; i < sportsListAll.size(); i++) {
                if (sportsListAll.get(i).isCheckekd()) {
                    sportsList.add(sportsListAll.get(i));
                    jsonArray = (JsonArray) new Gson().toJsonTree(sportsList);

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

    private void getSelectedList() {
        String sportName = CommonMethods.getPrefData(PrefrenceConstant.SPORT_NAME, getApplicationContext());
        Gson gson = new Gson();
        if (sportName != null) {
            if (sportName.isEmpty()) {
            } else {
                Type type = new TypeToken<List<SportListModel.DataBeanX.DataBean>>() {
                }.getType();
//                if (athUpdate) {
//                    selecteSports = new ArrayList<>();
//                    selecteSports= gson.fromJson(sportName, type);
//                    sportsList=selecteSports;
//                    jsonArray = (JsonArray) new Gson().toJsonTree(sportsList);
//                    if (selecteSports !=null && selecteSports.size()>0){
//                        if (sportsListAll != null && sportsListAll.size() > 0) {
//                            for (int i = 0; i < selecteSports.size(); i++) {
//                                for (int j=0;j<sportsListAll.size();j++){
//                                    if (sportsListAll.get(j).getId()==sportsList.get(i).getId()) {
//                                        sportsListAll.get(j).setCheckekd(true);
//                                    }
//                                }
//                            }
//                            binding.csRecyclerView.setLayoutManager(new LinearLayoutManager(ChooseSportActivity.this));
//                            adapter = new SportsAdapter(sportsListAll, ChooseSportActivity.this, ChooseSportActivity.this);
//                            binding.csRecyclerView.setAdapter(adapter);
//                        }
//                    }
//                }else {
                sportsListAll = gson.fromJson(sportName, type);
//                }
                StringBuilder builder = new StringBuilder();
                for (SportListModel.DataBeanX.DataBean details : sportsListAll) {
                    builder.append(details.getName() + "\n");

                }

            }
        }
    }

    private void getAthSelectedSports() {
        String sportName = CommonMethods.getPrefData(PrefrenceConstant.SPORTS_NAME, getApplicationContext());
        Gson gson = new Gson();
        if (sportName != null) {
            if (sportName.isEmpty()) {
            } else {
                Type type = new TypeToken<List<SportListModel.DataBeanX.DataBean>>() {
                }.getType();
                if (athUpdate) {
                    selecteSports = new ArrayList<>();
                    selecteSports = gson.fromJson(sportName, type);
                    if (selecteSports != null && selecteSports.size() > 0) {
                        sportsList = selecteSports;
                        jsonArray = (JsonArray) new Gson().toJsonTree(sportsList);
                        if (sportsListAll != null && sportsListAll.size() > 0) {
                            for (int i = 0; i < selecteSports.size(); i++) {
                                for (int j = 0; j < sportsListAll.size(); j++) {
                                    if (sportsListAll.get(j).getId() == sportsList.get(i).getId()) {
                                        sportsListAll.get(j).setCheckekd(true);
                                    }
                                }
                            }
                            binding.csRecyclerView.setLayoutManager(new LinearLayoutManager(ChooseSportActivity.this));
                            adapter = new SportsAdapter(sportsListAll, ChooseSportActivity.this, ChooseSportActivity.this);
                            binding.csRecyclerView.setAdapter(adapter);
                        }
                    }
                } else {
                    sportsListAll = gson.fromJson(sportName, type);
                }
                StringBuilder builder = new StringBuilder();
                for (SportListModel.DataBeanX.DataBean details : sportsListAll) {
                    builder.append(details.getName() + "\n");

                }

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
                        if (athUpdate) {
                            getAthSelectedSports();
                        }
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
                            CommonMethods.setPrefData(PrefrenceConstant.USER_LATITUDE, response.body().getData().getUser().getLatitude() + "", ChooseSportActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.USER_LONGITUDE, response.body().getData().getUser().getLongitude() + "", ChooseSportActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.USER_ACHIEVE, response.body().getData().getUser().getAchievements() + "", ChooseSportActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.SPORTS_NAME, response.body().getData().getUser().getSport_id() + "", ChooseSportActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.PROFILE_IMAGE, response.body().getData().getUser().getProfile_image() + "", ChooseSportActivity.this);
                            CommonMethods.setPrefData(Constants.AUTH_TOKEN, response.body().getData().getToken() + "", ChooseSportActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.LOGED_IN_USER, PrefrenceConstant.ATHLETE_LOG_IN, ChooseSportActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.ADDRESS, response.body().getData().getUser().getAddress(), ChooseSportActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.PRICE, "90", ChooseSportActivity.this);
                            Intent homeScreen = new Intent(ChooseSportActivity.this, AthleteHomeScreen.class);
                            homeScreen.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
        Call<ViewCoachListResponse> orgCoachSignUp = api.getOrgCoachRegister(userImg, "Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, ChooseSportActivity.this), requestBodyMap);
        orgCoachSignUp.enqueue(new Callback<ViewCoachListResponse>() {
            @Override
            public void onResponse(Call<ViewCoachListResponse> call, Response<ViewCoachListResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData() != null) {


                            Intent homeScreen = new Intent(ChooseSportActivity.this, ViewCoachStaffListActivity.class);
                            homeScreen.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
        athUpdate = false;
        storeServiceIds(sportsListAll);
        super.onDestroy();
    }

    private void hitUpdateAthleteDetailApi() {
        progressDialog.show();
        MultipartBody.Part userImg = null;
        File myFile = (File) getIntent().getSerializableExtra("image");
        if (myFile != null) {
            userImg = prepareFilePart("profile_image", myFile.getName(), myFile);
//            userImg = MultipartBody.Part.createFormData( "profile_image",photoFile.getName(), RequestBody.create(MediaType.parse("image/*"), photoFile));
        }
        Map<String, RequestBody> requestBodyMap = getDefaultParamsBody(this);
        requestBodyMap.put("name", RequestBody.create(MediaType.parse("multipart/form-data"), athName));
        requestBodyMap.put("email", RequestBody.create(MediaType.parse("multipart/form-data"), athEmail));
        requestBodyMap.put("phone", RequestBody.create(MediaType.parse("multipart/form-data"), athPhone));
        requestBodyMap.put("address", RequestBody.create(MediaType.parse("multipart/form-data"), athAddress));
        requestBodyMap.put("experience_detail", RequestBody.create(MediaType.parse("multipart/form-data"), athExperience));
        requestBodyMap.put("achievements", RequestBody.create(MediaType.parse("multipart/form-data"), athAchieve));
        requestBodyMap.put("sport_id", RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(jsonArray)));
        requestBodyMap.put("latitude", RequestBody.create(MediaType.parse("multipart/form-data"), latitude));
        requestBodyMap.put("longitude", RequestBody.create(MediaType.parse("multipart/form-data"), longitude));
        requestBodyMap.put("device_type", RequestBody.create(MediaType.parse("multipart/form-data"), Constants.DEVICE_TYPE));
        requestBodyMap.put("device_token", RequestBody.create(MediaType.parse("multipart/form-data"), CommonMethods.getPrefData(PrefrenceConstant.DEVICE_TOKEN, getApplicationContext())));
        requestBodyMap.put("Content-Type", RequestBody.create(MediaType.parse("multipart/form-data"), Constants.CONTENT_TYPE));

        Call<AthleteSignUpResponse> updateDetail = api.updateProfile("Bearer" + " " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getApplicationContext()), userImg, requestBodyMap);
        updateDetail.enqueue(new Callback<AthleteSignUpResponse>() {
            @Override
            public void onResponse(Call<AthleteSignUpResponse> call, Response<AthleteSignUpResponse> response) {
                progressDialog.dismiss();

                if (response.isSuccessful())
                    if (response.body().isStatus()) {
                        if (response.body() != null) {
                            Toast.makeText(ChooseSportActivity.this, "Detail updated successfully", Toast.LENGTH_LONG).show();
                            File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "UtCompressed");
                            CommonMethods.deleteDirectory(mediaStorageDir);
                            CommonMethods.setPrefData(PrefrenceConstant.SPORT_NAME, "", getApplicationContext());
                            CommonMethods.setPrefData(PrefrenceConstant.ROLE_PLAY, Constants.Athlete, ChooseSportActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.USER_EMAIL, response.body().getData().getUser().getEmail(), ChooseSportActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.USER_PHONE, response.body().getData().getUser().getPhone(), ChooseSportActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.USER_NAME, response.body().getData().getUser().getName(), ChooseSportActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.USER_ID, response.body().getData().getUser().getId() + "", ChooseSportActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.USER_EXPERIENCE, response.body().getData().getUser().getExperience_detail() + "", ChooseSportActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.USER_LATITUDE, response.body().getData().getUser().getLatitude() + "", ChooseSportActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.USER_LONGITUDE, response.body().getData().getUser().getLongitude() + "", ChooseSportActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.USER_ACHIEVE, response.body().getData().getUser().getAchievements() + "", ChooseSportActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.SPORTS_NAME, response.body().getData().getUser().getSport_id() + "", ChooseSportActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.PROFILE_IMAGE, response.body().getData().getUser().getProfile_image() + "", ChooseSportActivity.this);
//                            CommonMethods.setPrefData(Constants.AUTH_TOKEN, response.body().getData().getToken() + "", ChooseSportActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.LOGED_IN_USER, PrefrenceConstant.ATHLETE_LOG_IN, ChooseSportActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.ADDRESS, response.body().getData().getUser().getAddress(), ChooseSportActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.PRICE, "90", ChooseSportActivity.this);

                            Intent updatedDetail = new Intent(ChooseSportActivity.this, AthleteHomeScreen.class);
                            updatedDetail.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(updatedDetail);
                        } else {
                            Toast.makeText(ChooseSportActivity.this, "" + response.errorBody(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(ChooseSportActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                // isStatus error message here....

            }

            @Override
            public void onFailure(Call<AthleteSignUpResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ChooseSportActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });


    }

    private void inIt() {

        binding.csBackArrowImg.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.csBackArrowImg:
                finish();
        }
    }



    private void CoachUpdateApi() {
        progressDialog.show();
        MultipartBody.Part userImg = null;
//        if (orgDataModel.getProfile_img() != null) {
//            userImg = MultipartBody.Part.createFormData("profile_image", orgDataModel.getProfile_img().getName(), RequestBody.create(MediaType.parse("image/*"), orgDataModel.getProfile_img()));
//        }
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        requestBodyMap.put("sport_id", RequestBody.create(MediaType.parse("multipart/form-data"), jsonArray.toString()));
        requestBodyMap.put("device_type", RequestBody.create(MediaType.parse("multipart/form-data"), Constants.DEVICE_TYPE));
        requestBodyMap.put("device_token", RequestBody.create(MediaType.parse("multipart/form-data"), CommonMethods.getPrefData(PrefrenceConstant.DEVICE_TOKEN, getApplicationContext())));
        requestBodyMap.put("Content-Type", RequestBody.create(MediaType.parse("multipart/form-data"), Constants.CONTENT_TYPE));


        Call<CoachSignUpResponse> signUpAthlete = api.updateCoachBasicInfo("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getApplicationContext()),requestBodyMap , userImg);
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
                                    CommonMethods.setPrefData(PrefrenceConstant.SPORTS_NAME, response.body().getData().getUser().getSport_id(), ChooseSportActivity.this);

//

                                    Intent homeScreen = new Intent(getApplicationContext(), CoachDashboard.class);
                                    homeScreen.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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

    @Override
    protected void onResume() {

        super.onResume();
    }
    private void setClikFalse(){
        binding.athSignUp.setClickable(false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.athSignUp.setClickable(true);

            }
        }, 5000);

    }
}
