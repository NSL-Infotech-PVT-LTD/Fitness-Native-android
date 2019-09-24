package com.netscape.utrain.activities.athlete;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.BottomNavigation;
import com.netscape.utrain.activities.LoginActivity;
import com.netscape.utrain.databinding.ActivityCoachLoginBinding;
import com.netscape.utrain.response.LoginResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PrefrenceConstant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginWithActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityCoachLoginBinding binding;

    private LinearLayout linearLayout;
    private CallbackManager callbackManager;



    private Retrofitinterface retrofitinterface;
    private String first_name, last_name, id, image_url, fb_email;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = DataBindingUtil.setContentView(this, R.layout.activity_coach_login);
        linearLayout = (LinearLayout) findViewById(R.id.fbLin);

        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);
        progressDialog = new ProgressDialog(LoginWithActivity.this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);

//        tvSignUp = findViewById(R.id.tv_SignUp);
//        tvSignUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(LoginWithActivity.this, BottomNavigation.class);
//                startActivity(intent);
//            }
//        });
//
////        btnFacaebookLogin = findViewById(R.id.btn_facebook);
////        callbackManager = CallbackManager.Factory.create();
////        btnFacaebookLogin.setReadPermissions("email", "public_profile");
////        btnFacaebookLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
////            @Override
////            public void onSuccess(LoginResult loginResult) {
////
////            }
////
////            @Override
////            public void onCancel() {
////
////            }
////
////            @Override
////            public void onError(FacebookException error) {
////
////            }
////        });
        binding.emailLoginBtn.setOnClickListener(this);
        binding.fbLin.setOnClickListener(this);
        binding.tvSignUp.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.emailLoginBtn:
                final Intent intent = new Intent(LoginWithActivity.this, AthleteLoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            case R.id.fbLin:
                callbackManager = CallbackManager.Factory.create();
                LoginManager.getInstance().logInWithReadPermissions(LoginWithActivity.this, Arrays.asList("email", "public_profile"));
                LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        Log.d("Success", "Login");
//                        loadUserProfile(loginResult.getAccessToken());


                    }

                    @Override
                    public void onCancel() {

                        Toast.makeText(LoginWithActivity.this, "Login Cancel", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException e) {
                        if (e instanceof FacebookAuthorizationException) {
                            if (AccessToken.getCurrentAccessToken() != null) {
                                LoginManager.getInstance().logOut();
                            }
                        }
                        Toast.makeText(LoginWithActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
                break;
            case R.id.tvSignUp:
                Intent signUpType = new Intent(LoginWithActivity.this, AthleteSignupActivity.class);
                signUpType.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(signUpType);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

    }

    AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            if (currentAccessToken == null) {

                Toast.makeText(LoginWithActivity.this, "User Logged Out ", Toast.LENGTH_SHORT).show();
            } else {
                loadUserProfile(currentAccessToken);
            }

        }
    };

    private void loadUserProfile(final AccessToken newAccessToken) {

        GraphRequest request = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                try {
                    first_name = object.getString("first_name");
                    last_name = object.getString("last_name");
                    fb_email = object.getString("email");
                    id = object.getString("id");
                    image_url = "https://graph.facebook.com/" + id + "/picture?type=normal";

//                    Intent fbintent = new Intent(LoginWithActivity.this,AthleteSignupActivity.class);
//                    fbintent.putExtra("name", first_name +" "+last_name);
//                    fbintent.putExtra("email", email);
//                    fbintent.putExtra("image_url", image_url);
//                    fbintent.putExtra("fb_id", id);

                    Constants.SocialProfile=image_url;
                    hitLoginApi(fb_email, id);
//                    CommonMethods.setPrefData("name", first_name + " " + last_name ,LoginWithActivity.this);
//                    CommonMethods.setPrefData("email", email,LoginWithActivity.this);
//                    CommonMethods.setPrefData("image_url", image_url, LoginWithActivity.this);
//                    CommonMethods.setPrefData();
//                    startActivity(fbintent);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name, last_name, email,id");
        request.setParameters(parameters);
        request.executeAsync();


    }

    private void hitLoginApi(String email, String password) {
        progressDialog.show();
        Call<LoginResponse> signUpAthlete = retrofitinterface.userLogin(email, password, Constants.DEVICE_TYPE, Constants.DEVICE_TOKEN, Constants.CONTENT_TYPE);
        signUpAthlete.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                progressDialog.dismiss();

                if (response.isSuccessful()) {
                    if (response.body().isStatus()) {
                        if (response.body().getData() != null) {
                            CommonMethods.setPrefData(PrefrenceConstant.USER_EMAIL, response.body().getData().getUser().getEmail(), LoginWithActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.USER_PHONE, response.body().getData().getUser().getPhone(), LoginWithActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.USER_NAME, response.body().getData().getUser().getName(), LoginWithActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.USER_ID, response.body().getData().getUser().getId() + "", LoginWithActivity.this);
                            Intent fbreg = new Intent(LoginWithActivity.this, BottomNavigation.class);
                            startActivity(fbreg);


                        }
                    } else {
                        Snackbar.make(binding.athleteLoginLayout, response.body().getError().getError_message().getMessage().toString(), BaseTransientBottomBar.LENGTH_LONG).show();
                    }
                } else {
                    progressDialog.dismiss();

                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);
                        Snackbar.make(binding.athleteLoginLayout, errorMessage.toString(), BaseTransientBottomBar.LENGTH_LONG).show();
                        Intent fbintent = new Intent(LoginWithActivity.this, AthleteSignupActivity.class);
                        fbintent.putExtra("name", first_name + " " + last_name);
                        fbintent.putExtra("email", fb_email);
                        fbintent.putExtra("image_url", image_url);
                        fbintent.putExtra("fb_id", id);
                        startActivity(fbintent);

                    } catch (Exception e) {
//                        Snackbar.make(binding.athleteLoginLayout,e.getMessage().toString(), BaseTransientBottomBar.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(binding.athleteLoginLayout, getResources().getString(R.string.something_went_wrong), BaseTransientBottomBar.LENGTH_LONG).show();

            }
        });
    }

}
