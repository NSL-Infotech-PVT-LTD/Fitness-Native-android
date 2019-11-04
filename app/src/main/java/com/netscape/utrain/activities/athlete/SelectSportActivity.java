package com.netscape.utrain.activities.athlete;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;
import com.netscape.utrain.R;
import com.netscape.utrain.adapters.SportsAdapter;
import com.netscape.utrain.databinding.ActivitySelectSportBinding;
import com.netscape.utrain.model.AthleteUserModel;
import com.netscape.utrain.model.DataModel;
import com.netscape.utrain.model.SportListModel;
import com.netscape.utrain.model.SportsIdModel;
import com.netscape.utrain.response.AthleteSignUpResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PrefrenceConstant;

import org.json.JSONObject;

import java.io.File;
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

public class SelectSportActivity extends AppCompatActivity {


    ActivitySelectSportBinding binding;
    MaterialTextView addService;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    SportsAdapter adapter;
    private AthleteUserModel athModel;
    private List<SportsIdModel> sportList = new ArrayList<>();
    Retrofitinterface api;
    private ProgressDialog progressDialog;
    private File photoFile = null;
    private Retrofitinterface retrofitInterface;
    private double latitude = 0.0, longitude = 0.0;
    private SelectSportActivity activity;
    private List<SportListModel.DataBeanX.DataBean> sportsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_select_sport);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_sport);


        layoutManager = new LinearLayoutManager(this);
        binding.sportsRecyclerView.setLayoutManager(layoutManager);


        api = RetrofitInstance.getClient().create(Retrofitinterface.class);
//        Call<AthleteUserModel> athleteSignUp = api.athleteSignUp()


        binding.addSportsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passTheIntent();
            }
        });

    }


    private void athleteSignUpApi(String email, String password, String name, String phone, String address) {
//        CommonMethods.hideKeyboard(this);
        progressDialog.show();
        MultipartBody.Part userImg = null;
        if (photoFile != null) {
            userImg = prepareFilePart("profile_image", photoFile.getName(), photoFile);
//            userImg = MultipartBody.Part.createFormData( "profile_image",photoFile.getName(), RequestBody.create(MediaType.parse("image/*"), photoFile));
        }
        Map<String, RequestBody> requestBodyMap = getDefaultParamsBody(this);
        requestBodyMap.put("name", RequestBody.create(MediaType.parse("multipart/form-data"), name));
        requestBodyMap.put("email", RequestBody.create(MediaType.parse("multipart/form-data"), email));
        requestBodyMap.put("password", RequestBody.create(MediaType.parse("multipart/form-data"), password));
        requestBodyMap.put("phone", RequestBody.create(MediaType.parse("multipart/form-data"), phone));
        requestBodyMap.put("address", RequestBody.create(MediaType.parse("multipart/form-data"), address));
        requestBodyMap.put("latitude", RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(latitude)));
        requestBodyMap.put("longitude", RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(longitude)));
        requestBodyMap.put("device_type", RequestBody.create(MediaType.parse("multipart/form-data"), Constants.DEVICE_TYPE));
        requestBodyMap.put("device_token", RequestBody.create(MediaType.parse("multipart/form-data"), CommonMethods.getPrefData(PrefrenceConstant.DEVICE_TOKEN, getApplicationContext())));
        requestBodyMap.put("Content-Type", RequestBody.create(MediaType.parse("multipart/form-data"), Constants.CONTENT_TYPE));
        Call<AthleteSignUpResponse> signUpAthlete = retrofitInterface.registerAthlete(requestBodyMap, userImg);
        signUpAthlete.enqueue(new Callback<AthleteSignUpResponse>() {
            @Override
            public void onResponse(Call<AthleteSignUpResponse> call, Response<AthleteSignUpResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData() != null) {

                            sportList = response.body().getData().getUser().getSport_id();
//                            adapter = new SportsAdapter(sportsList, SelectSportActivity.this);
                            binding.sportsRecyclerView.setAdapter(adapter);
                            CommonMethods.setPrefData(PrefrenceConstant.ROLE_PLAY, Constants.Athlete, activity);
                            CommonMethods.setPrefData(PrefrenceConstant.USER_EMAIL, response.body().getData().getUser().getEmail(), SelectSportActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.USER_PHONE, response.body().getData().getUser().getPhone(), SelectSportActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.USER_NAME, response.body().getData().getUser().getName(), SelectSportActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.USER_ID, response.body().getData().getUser().getId() + "", SelectSportActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.PROFILE_IMAGE, response.body().getData().getUser().getProfile_image() + "", SelectSportActivity.this);
                            CommonMethods.setPrefData(Constants.AUTH_TOKEN, response.body().getData().getToken() + "", SelectSportActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.LOGED_IN_USER, PrefrenceConstant.ATHLETE_LOG_IN, SelectSportActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.PRICE, "90", SelectSportActivity.this);


                            Intent homeScreen = new Intent(getApplicationContext(), AthleteHomeScreen.class);
                            homeScreen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(homeScreen);
                        }
                    } else {
                        Snackbar.make(binding.sportsLayout, response.body().getError().getError_message().getMessage().toString(), BaseTransientBottomBar.LENGTH_SHORT).show();
                    }
                } else {
                    progressDialog.dismiss();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);
                        Snackbar.make(binding.sportsLayout, errorMessage, BaseTransientBottomBar.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Snackbar.make(binding.sportsLayout, e.getMessage(), BaseTransientBottomBar.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<AthleteSignUpResponse> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(binding.sportsLayout, getResources().getString(R.string.something_went_wrong), BaseTransientBottomBar.LENGTH_SHORT).show();


            }
        });
    }

    private void passTheIntent() {
        Intent intent = new Intent(SelectSportActivity.this, ChooseSportActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(Constants.AthSignUpIntent, athModel);
        startActivity(intent);
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
}
