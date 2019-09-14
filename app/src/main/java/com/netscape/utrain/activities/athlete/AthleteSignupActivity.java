package com.netscape.utrain.activities.athlete;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.netscape.utrain.BuildConfig;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.AskPermission;
import com.netscape.utrain.activities.BottomNavigation;
import com.netscape.utrain.activities.LoginActivity;
import com.netscape.utrain.databinding.ActivityAthleteSignupBinding;
import com.netscape.utrain.response.AthleteSignUpResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.AppController;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.FileUtil;
import com.netscape.utrain.utils.ImageFilePath;
import com.netscape.utrain.utils.PrefrenceConstant;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AthleteSignupActivity extends AppCompatActivity implements View.OnClickListener, LocationListener, GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener {
    private ActivityAthleteSignupBinding binding;
    private GoogleApiClient googleApiClient;
    private AlertDialog dialogMultiOrder;
    private String currentPhotoFilePath = "", imageUrl = "";
    private File photoFile = null;
    private AskPermission askPermObj;
    private AthleteSignupActivity activity;
    private LocationManager mLocManager;
    private LocationListener mLocListener;
    private Retrofitinterface retrofitInterface;
    private ProgressDialog progressDialog;
    private final static int REQUEST_ID_MULTIPLE_PERMISSIONS = 0x2;
    private final static int REQUEST_CHECK_SETTINGS_GPS = 0x1;
    private Location mylocation;
    private String userName = "", userEmail = "", userPhone = "", userAddress = "", userPassword="";
    private String address = "";
    private double latitude = 0.0, longitude = 0.0;

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
        setContentView(R.layout.activity_athlete_signup);

        retrofitInterface = RetrofitInstance.getClient().create(Retrofitinterface.class);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_athlete_signup);
        init();
        binding.athleteAddressEdt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (binding.athleteAddressEdt.getRight() - binding.athleteAddressEdt.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (checkPermissions())
                            binding.athleteAddressEdt.setText(address);
                        return true;
                    }
                }
                return false;
            }
        });
    }


    private void init() {
        activity=this;
        binding.athleteSignUpBtn.setOnClickListener(this);
        binding.athleteSignInTv.setOnClickListener(this);
        binding.athleteprofileImageView.setOnClickListener(this);
        askPermObj = new AskPermission(getApplicationContext(), this);
        setUpGClient();

//         mLocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
//         mLocListener = new MyLocationListener();
//        mLocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocListener);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.athleteprofileImageView:
                if (!askPermObj.isPermissionGiven(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    askRequiredPermission();
                    return;
                }
                if (!askPermObj.isPermissionGiven(this, Manifest.permission.CAMERA)) {
                    askRequiredPermission();
                    return;
                }
                handleImageSelection();
                break;
            case R.id.athleteSignUpBtn:
                getSignUpData();
                break;
            case R.id.athleteSignInTv:
                Intent signInActivity = new Intent(AthleteSignupActivity.this, AthleteLoginActivity.class);
                signInActivity.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(signInActivity);
                break;
        }
    }

    private void getSignUpData() {

        if (binding.athleteNameEdt.getText().toString().isEmpty()) {
            binding.athleteNameEdt.setError(getString(R.string.enter_name));
            binding.athleteNameEdt.requestFocus();
        } else if (binding.athleteEmailEdt.getText().toString().isEmpty()) {
            binding.athleteEmailEdt.setError(getString(R.string.enter_your_email));
            binding.athleteEmailEdt.requestFocus();
        }else if (! Patterns.EMAIL_ADDRESS.matcher(binding.athleteEmailEdt.getText().toString()).matches()){
            binding.athleteEmailEdt.setError(getString(R.string.enter_valid_email));
            binding.athleteEmailEdt.requestFocus();
        } else if (binding.athletePhoneEdt.getText().toString().isEmpty()) {
            binding.athletePhoneEdt.setError(getString(R.string.enter_phone_number));
            binding.athletePhoneEdt.requestFocus();
        } else if (binding.athletePhoneEdt.getText().toString().length()<10) {
            binding.athletePhoneEdt.setError(getString(R.string.ente_ten_diget_phone_number));
            binding.athletePhoneEdt.requestFocus();
        } else if (binding.athleteAddressEdt.getText().toString().isEmpty()) {
            Toast.makeText(AthleteSignupActivity.this, getResources().getString(R.string.select_address), Toast.LENGTH_SHORT).show();
            binding.athleteAddressEdt.requestFocus();
        } else if (binding.athletePasswordEdt.getText().toString().isEmpty()) {
            binding.athletePasswordEdt.setError(getString(R.string.enter_password));
            binding.athletePasswordEdt.requestFocus();
        } else if (photoFile == null) {
            Toast.makeText(AthleteSignupActivity.this, getResources().getString(R.string.add_profile_image), Toast.LENGTH_SHORT).show();
        } else {
            athleteSignUpApi();
        }
    }

    private void hitAthleteSignUpApi() {
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

    @Override
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
            isPermissionGranted(AthleteSignupActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE,
                    Constants.MY_PERMISSION_GALLERY);
        } else {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            AthleteSignupActivity.this.startActivityForResult(photoPickerIntent, Constants.REQUEST_CODE_GALLERY);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_CAMERA_CAPTURE) {
            if (resultCode == RESULT_OK) {
//                AppDelegate.Log("imageCaptured ", "result ok");
                photoFile = new File(currentPhotoFilePath);
//                AppDelegate.Log("imageCaptured ", currentPhotoFilePath);
                if (photoFile != null) {
                    imageUrl = photoFile.getPath();
                    Glide.with(this).load(photoFile.getPath()).into(binding.athleProfileImg);
                } /*else {
//                    Toast.makeText(AthleteSignupActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }*/
//            } else {
//                Toast.makeText(AthleteSignupActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
////                AppDelegate.Log("imageCaptured ", "result failed");
            }
        } else if (requestCode == Constants.REQUEST_CODE_GALLERY && resultCode == RESULT_OK) {
            String realPath = ImageFilePath.getPath(this, data.getData());
            currentPhotoFilePath = realPath;
            photoFile = new File(realPath);
            if (photoFile != null)
                Glide.with(this).load(photoFile.getPath()).into(binding.athleProfileImg);
        }
    }

    private void athleteSignUpApi() {
//        CommonMethods.hideKeyboard(this);
        progressDialog.show();
        MultipartBody.Part userImg = null;
        if (photoFile != null) {
            userImg = MultipartBody.Part.createFormData("profile_image", photoFile.getName(), RequestBody.create(MediaType.parse("image/*"), photoFile));
        }
        Call<AthleteSignUpResponse> signUpAthlete = retrofitInterface.athleteSignUp(userImg,
                                                                                    binding.athleteNameEdt.getText().toString(),
                                                                                    binding.athleteEmailEdt.getText().toString(),
                                                                                    binding.athletePasswordEdt.getText().toString(),
                                                                                    binding.athletePhoneEdt.getText().toString(),
                                                                                    binding.athleteAddressEdt.getText().toString(),
                                                                                   latitude+"",longitude+"",
                                                                                    Constants.DEVICE_TYPE,
                                                                                    Constants.DEVICE_TOKEN,
                                                                                    Constants.CONTENT_TYPE);

        signUpAthlete.enqueue(new Callback<AthleteSignUpResponse>() {
            @Override
            public void onResponse(Call<AthleteSignUpResponse> call, Response<AthleteSignUpResponse> response) {


                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData() != null) {
                            CommonMethods.setPrefData(PrefrenceConstant.USER_EMAIL, response.body().getData().getUser().getEmail(), AthleteSignupActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.USER_PHONE, response.body().getData().getUser().getPhone(), AthleteSignupActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.USER_NAME, response.body().getData().getUser().getName(), AthleteSignupActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.USER_ID, response.body().getData().getUser().getId()+"", AthleteSignupActivity.this);
                            Intent homeScreen= new Intent(getApplicationContext(), BottomNavigation.class);
                            homeScreen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(homeScreen);
                        }
                    } else {
                        Snackbar.make(binding.layoutMain,response.body().getError().getError_message().getMessage().toString(), BaseTransientBottomBar.LENGTH_SHORT).show();
                    }
                } else {
                    progressDialog.dismiss();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);
                        Snackbar.make(binding.layoutMain,errorMessage, BaseTransientBottomBar.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Snackbar.make(binding.layoutMain,e.getMessage(), BaseTransientBottomBar.LENGTH_SHORT).show();
                    }
                }

            }
            @Override
            public void onFailure(Call<AthleteSignUpResponse> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(binding.layoutMain,getResources().getString(R.string.something_went_wrong), BaseTransientBottomBar.LENGTH_SHORT).show();


            }
        });
    }
    private synchronized void setUpGClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, 0, this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }
    @Override
    public void onLocationChanged(Location location) {
        mylocation = location;
        if (mylocation != null) {
            latitude = mylocation.getLatitude();
            longitude = mylocation.getLongitude();
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(this, Locale.getDefault());

            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                address = addresses.get(0).getAddressLine(0);
                // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        checkPermissions();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private boolean checkPermissions() {
        int permissionLocation = ContextCompat.checkSelfPermission(AthleteSignupActivity.this,
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

}



