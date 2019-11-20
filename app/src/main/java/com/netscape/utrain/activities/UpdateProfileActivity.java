package com.netscape.utrain.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.common.Common;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.netscape.utrain.BuildConfig;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.athlete.ChooseSportActivity;
import com.netscape.utrain.databinding.ActivityUpdateProfileBinding;
import com.netscape.utrain.model.AthleteUserModel;
import com.netscape.utrain.model.SportListModel;
import com.netscape.utrain.response.AthleteSignUpResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.AppController;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.FileUtil;
import com.netscape.utrain.utils.ImageFilePath;
import com.netscape.utrain.utils.PrefrenceConstant;

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

import static com.facebook.FacebookSdk.getApplicationContext;

public class UpdateProfileActivity extends AppCompatActivity implements View.OnClickListener, LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    private final static int REQUEST_ID_MULTIPLE_PERMISSIONS = 0x2;
    private final static int REQUEST_CHECK_SETTINGS_GPS = 0x1;
    ActivityUpdateProfileBinding binding;
    String uNameTv, uEmailTv, uPhoneEdt, uAddressEdt, uExperienceEdt, uAchievementEdt, latitude, longitude, password; // these variable to store sharedPrefValues....
    String name = "", email = "", phoneNo = "", address = "", expDetail = "", achievementDetail = "";
    Retrofitinterface retrofitinterface;
    JsonArray jsonArray;
    AthleteUserModel model;
    private ProgressDialog progressDialog;
    private AlertDialog dialogMultiOrder;
    private AskPermission askPermObj;
    private File photoFile = null;
    private String currentPhotoFilePath = "", imageUrl = "";
    private UpdateProfileActivity activity;
    private GoogleApiClient googleApiClient;
    private Location mylocation;
    private ArrayList<SportListModel.DataBeanX.DataBean> sportList = new ArrayList<>();

    public static boolean isPermissionGranted(Activity activity, String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(activity, permission)
                != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.requestPermissions(new String[]{permission},
                        requestCode);
            }
            return false;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_update_profile);
        binding = DataBindingUtil.setContentView(UpdateProfileActivity.this, R.layout.activity_update_profile);

        // storing entered values to variables....


        // Getting value by shared Preferrence to display....

        uNameTv = CommonMethods.getPrefData(PrefrenceConstant.USER_NAME, UpdateProfileActivity.this);
        uEmailTv = CommonMethods.getPrefData(PrefrenceConstant.USER_EMAIL, UpdateProfileActivity.this);
        uPhoneEdt = CommonMethods.getPrefData(PrefrenceConstant.USER_PHONE, UpdateProfileActivity.this);
        uAddressEdt = CommonMethods.getPrefData(PrefrenceConstant.ADDRESS, UpdateProfileActivity.this);
        uExperienceEdt = CommonMethods.getPrefData(PrefrenceConstant.USER_EXPERIENCE, UpdateProfileActivity.this);
        uAchievementEdt = CommonMethods.getPrefData(PrefrenceConstant.USER_ACHIEVE, UpdateProfileActivity.this);
        latitude = CommonMethods.getPrefData("latitude", UpdateProfileActivity.this);
        longitude = CommonMethods.getPrefData("longitude", UpdateProfileActivity.this);


        // Binding values to the views ....
        binding.uNameTv.setText(uNameTv);
        binding.uEmailTv.setText(uEmailTv);
        binding.uPhoneEdt.setText(uPhoneEdt);
        binding.uAddressEdt.setText(uAddressEdt);
        binding.uExperienceEdt.setText(uExperienceEdt);
        binding.uAchievementEdt.setText(uAchievementEdt);
        Glide.with(UpdateProfileActivity.this).load(CommonMethods.getPrefData(PrefrenceConstant.PROFILE_IMAGE, this)).into(binding.uProfileImg);
        setProfileImage();
        getSportsIds();
        binding.uBookingBackImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                photoFile = new File(CommonMethods.getPrefData(PrefrenceConstant.PROFILE_IMAGE, UpdateProfileActivity.this));
                // checking validation on update btn....
                name = binding.uNameTv.getText().toString();
                email = binding.uEmailTv.getText().toString();
                phoneNo = binding.uPhoneEdt.getText().toString();
                if (phoneNo.equals(""))
                    binding.uPhoneEdt.setError(getResources().getString(R.string.enter_phone_number));
                binding.uPhoneEdt.requestFocus();
                address = binding.uAddressEdt.getText().toString();
                if (address.equals(""))
                    binding.uAddressEdt.setError(getResources().getString(R.string.select_address));
                binding.uAddressEdt.requestFocus();
                expDetail = binding.uExperienceEdt.getText().toString();
                if (expDetail.equals(""))
                    binding.uExperienceEdt.setError(getResources().getString(R.string.enter_your_experience_details));
                binding.uExperienceEdt.requestFocus();
                achievementDetail = binding.uAchievementEdt.getText().toString();
                if (achievementDetail.equals("")) {
                    binding.uAchievementEdt.setError(getResources().getString(R.string.enter_your_achievements_details));
                    binding.uAchievementEdt.requestFocus();
                } else {

                    // update profile api hit here....
                    Intent chooseSport = new Intent(UpdateProfileActivity.this, ChooseSportActivity.class);
                    chooseSport.putExtra("update","updateProfile");
                    chooseSport.putExtra("name",name);
                    chooseSport.putExtra("email", email);
                    chooseSport.putExtra("phone", phoneNo);
                    chooseSport.putExtra("address", address);
                    chooseSport.putExtra("experience", uExperienceEdt);
                    chooseSport.putExtra("achievement", achievementDetail);
                    chooseSport.putExtra("photo", photoFile);
                    startActivity(chooseSport);
                }

            }
        });

        binding.uAddressEdt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (binding.uAddressEdt.getRight() - binding.uAddressEdt.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (checkPermissions())
                            binding.uAddressEdt.setText(address);
                        return true;
                    }
                }
                return false;
            }
        });


    }

    private boolean checkPermissions() {
        int permissionLocation = ContextCompat.checkSelfPermission(UpdateProfileActivity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(this,
                        listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            }
            return false;
        } else {
            getMyLocation();
            return true;
        }
    }

    private void hitUpdateAthleteDetailApi(final String email, final String name, String phone, final String address, final String experience, String achievement) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);
        MultipartBody.Part userImg = null;
        if (photoFile != null) {
            userImg = prepareFilePart("profile_image", photoFile.getName(), photoFile);
//            userImg = MultipartBody.Part.createFormData( "profile_image",photoFile.getName(), RequestBody.create(MediaType.parse("image/*"), photoFile));
        }
        Map<String, RequestBody> requestBodyMap = getDefaultParamsBody(this);
        requestBodyMap.put("name", RequestBody.create(MediaType.parse("multipart/form-data"), name));
        requestBodyMap.put("email", RequestBody.create(MediaType.parse("multipart/form-data"), email));
        requestBodyMap.put("phone", RequestBody.create(MediaType.parse("multipart/form-data"), phone));
        requestBodyMap.put("address", RequestBody.create(MediaType.parse("multipart/form-data"), address));
        requestBodyMap.put("experience_detail", RequestBody.create(MediaType.parse("multipart/form-data"), experience));
        requestBodyMap.put("achievements", RequestBody.create(MediaType.parse("multipart/form-data"), achievement));
        requestBodyMap.put("sport_id", RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(jsonArray)));
        requestBodyMap.put("latitude", RequestBody.create(MediaType.parse("multipart/form-data"), latitude));
        requestBodyMap.put("longitude", RequestBody.create(MediaType.parse("multipart/form-data"), longitude));
        requestBodyMap.put("device_type", RequestBody.create(MediaType.parse("multipart/form-data"), Constants.DEVICE_TYPE));
        requestBodyMap.put("device_token", RequestBody.create(MediaType.parse("multipart/form-data"), CommonMethods.getPrefData(PrefrenceConstant.DEVICE_TOKEN, getApplicationContext())));
        requestBodyMap.put("Content-Type", RequestBody.create(MediaType.parse("multipart/form-data"), Constants.CONTENT_TYPE));

        Call<AthleteSignUpResponse> updateDetail = retrofitinterface.updateProfile("",userImg, requestBodyMap);
        updateDetail.enqueue(new Callback<AthleteSignUpResponse>() {
            @Override
            public void onResponse(Call<AthleteSignUpResponse> call, Response<AthleteSignUpResponse> response) {
                progressDialog.dismiss();

                if (response.isSuccessful())
                    if (response.body().isStatus()) {
                        if (response.body() != null) {
                            Toast.makeText(UpdateProfileActivity.this, "Detail updated successfully", Toast.LENGTH_LONG).show();
                            Intent updatedDetail = new Intent(UpdateProfileActivity.this, MyProfile.class);
                            updatedDetail.putExtra("uname", name);
                            updatedDetail.putExtra("uemail", email);
                            updatedDetail.putExtra("uphoneNumber", phoneNo);
                            updatedDetail.putExtra("uaddress", address);
                            updatedDetail.putExtra("uexperience", expDetail);
                            updatedDetail.putExtra("uachievement", expDetail);
                            updatedDetail.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(updatedDetail);
                        } else {
                            Toast.makeText(UpdateProfileActivity.this, "" + response.errorBody(), Toast.LENGTH_LONG).show();
                        }
                    }
                // isStatus error message here....

            }

            @Override
            public void onFailure(Call<AthleteSignUpResponse> call, Throwable t) {
                progressDialog.dismiss();

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


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.REQUEST_CAMERA_CAPTURE) {
            if (resultCode == RESULT_OK) {
//                AppDelegate.Log("imageCaptured ", "result ok");
                photoFile = new File(currentPhotoFilePath);
//                AppDelegate.Log("imageCaptured ", currentPhotoFilePath);
                if (photoFile != null) {
//                    plus.setVisibility(View.GONE);
                    Glide.with(this).load(photoFile.getPath()).into(binding.uProfileImg);
//                    imagesSelected.add(position,photoFile);

                } /*else {
//                    Toast.makeText(AthleteSignupActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }*/
//            } else {
//                Toast.makeText(AthleteSignupActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
////                AppDelegate.Log("imageCaptured ", "result failed");
            }
        } else if (requestCode == Constants.REQUEST_CODE_GALLERY && resultCode == RESULT_OK) {
            String realPath = ImageFilePath.getPath(this, data.getData());
            if (realPath != null) {
                photoFile = new File(realPath);
            }
            if (photoFile != null)
//                plus.setVisibility(View.GONE);
                Glide.with(this).load(photoFile.getPath()).into(binding.uProfileImg);

        }

    }


    /*asking for the required permission*/
    private void askRequiredPermission() {
        /*asking for the required permission*/
        if (!askPermObj.isPermissionGiven(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            askPermObj.askPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Constants.WRITE_PERMISSION);
            return;
        }
        if (!askPermObj.isPermissionGiven(this, Manifest.permission.CAMERA)) {
            if (!askPermObj.isPermissionGiven(this, Manifest.permission.CAMERA)) {
                askPermObj.askPermission(Manifest.permission.CAMERA, Constants.CAMERA_PERMISSION);
                return;
            }
            return;
        } else {
            /*if both permission given than
             * launching camera to capture image*/
            getImageUsingCamera();
        }
    }

    public void handleImageSelection() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View content = inflater.inflate(R.layout.get_image_dialog, null);
        builder.setView(content);
        TextView gallery = (TextView) content.findViewById(R.id.gallerySellectionBtn);
        TextView camera = (TextView) content.findViewById(R.id.cameraSelectionBtn);
        ImageView cancel = (ImageView) content.findViewById(R.id.closeDialogImg);
        dialogMultiOrder = builder.create();
        dialogMultiOrder.setCancelable(false);
//        dialogMultiOrder.setCanceledOnTouchOutside(false);
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleGalleryImage();
                dialogMultiOrder.dismiss();
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImageUsingCamera();
                dialogMultiOrder.dismiss();
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

    private void handleGalleryImage() {
        if (!askPermObj.isPermissionGiven(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            askPermObj.askPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Constants.WRITE_PERMISSION);
            return;
        }
        getImageFromStorage();
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        int permissionLocation = ContextCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
            getMyLocation();
        }
        if (requestCode == Constants.WRITE_PERMISSION) {
            /*detects whether write permission is given*/
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                /*if permission than takes permission for camera to capture*/
                askRequiredPermission();
            }
        } else if (requestCode == Constants.CAMERA_PERMISSION) {
            /*detects whether camera permission given*/
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                handleImageSelection();
            }
        }
    }

    public void getImageFromStorage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            isPermissionGranted(UpdateProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE,
                    Constants.MY_PERMISSION_GALLERY);
        } else {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            UpdateProfileActivity.this.startActivityForResult(photoPickerIntent, Constants.REQUEST_CODE_GALLERY);
        }
    }

    public void getImageUsingCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            FileUtil fileUtil = new FileUtil();
            String fileName = "img_" + System.currentTimeMillis() + ".jpg";
            photoFile = fileUtil.createFile(getApplicationContext(), null, fileName);
            // Continue only if the File was successfully created
            Uri photoURI;
            if (photoFile != null) {
                currentPhotoFilePath = photoFile.getPath();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    photoURI = FileProvider.getUriForFile(AppController.getInstance(),
                            BuildConfig.APPLICATION_ID + ".fileprovider",
                            photoFile);
                } else {
                    photoURI = Uri.fromFile(photoFile);
                }
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, Constants.REQUEST_CAMERA_CAPTURE);
            }
        }
    }


    private void getMyLocation() {
        if (googleApiClient != null) {
            if (googleApiClient.isConnected()) {
                int permissionLocation = ContextCompat.checkSelfPermission(activity,
                        Manifest.permission.ACCESS_FINE_LOCATION);
                if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                    mylocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                    LocationRequest locationRequest = new LocationRequest();
                    locationRequest.setInterval(3000);
                    locationRequest.setFastestInterval(3000);
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                            .addLocationRequest(locationRequest);
                    builder.setAlwaysShow(true);
                    LocationServices.FusedLocationApi
                            .requestLocationUpdates(googleApiClient, locationRequest, this);
                    PendingResult<LocationSettingsResult> result =
                            LocationServices.SettingsApi
                                    .checkLocationSettings(googleApiClient, builder.build());
                    result.setResultCallback(new ResultCallback<LocationSettingsResult>() {

                        @Override
                        public void onResult(LocationSettingsResult result) {
                            final Status status = result.getStatus();
                            switch (status.getStatusCode()) {
                                case LocationSettingsStatusCodes.SUCCESS:
                                    // All location settings are satisfied.
                                    // You can initialize location requests here.
                                    int permissionLocation = ContextCompat
                                            .checkSelfPermission(activity,
                                                    Manifest.permission.ACCESS_FINE_LOCATION);
                                    if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                                        mylocation = LocationServices.FusedLocationApi
                                                .getLastLocation(googleApiClient);
                                    }
                                    break;
                                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                    // Location settings are not satisfied.
                                    // But could be fixed by showing the user a dialog.
                                    try {
                                        // Show the dialog by calling startResolutionForResult(),
                                        // and check the result in onActivityResult().
                                        // Ask to turn on GPS automatically
                                        status.startResolutionForResult(activity,
                                                REQUEST_CHECK_SETTINGS_GPS);
                                    } catch (IntentSender.SendIntentException e) {
                                        // Ignore the error.
                                    }
                                    break;
                                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                    // Location settings are not satisfied.
                                    // However, we have no way
                                    // to fix the
                                    // settings so we won't show the dialog.
                                    // finish();
                                    break;
                            }
                        }
                    });
                }
            }
        }
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    private void setProfileImage() {

        String img = CommonMethods.getPrefData(PrefrenceConstant.PROFILE_IMAGE, UpdateProfileActivity.this);
        if (!TextUtils.isEmpty(img)) {
            Glide.with(UpdateProfileActivity.this).load(Constants.IMAGE_BASE_URL + img).thumbnail(Glide.with(UpdateProfileActivity.this).load(Constants.IMAGE_BASE_URL + Constants.THUMBNAILS + img)).into(binding.uProfileImg);

        }
    }

    private void getSportsIds() {
        String sportName = CommonMethods.getPrefData(PrefrenceConstant.SPORTS_NAME, getApplicationContext());
        Gson gson = new Gson();

        if (sportName != null) {
            if (sportName.isEmpty()) {
                Toast.makeText(UpdateProfileActivity.this, "Service Not Found", Toast.LENGTH_SHORT).show();
            } else {
                Type type = new TypeToken<List<SportListModel.DataBeanX.DataBean>>() {
                }.getType();
                sportList = gson.fromJson(sportName, type);

                StringBuilder builder = new StringBuilder();
                for (SportListModel.DataBeanX.DataBean details : sportList) {
                    builder.append(details.getName() + "\n");

                }

//                aSportsNameTv.setText(builder.toString());
            }
        } else {
//            aSportsText.setVisibility(View.GONE);
//            aSportsNameTv.setVisibility(View.GONE);

        }
    }

}
