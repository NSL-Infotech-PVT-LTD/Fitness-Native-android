package com.netscape.utrain.activities;

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
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import com.netscape.utrain.BuildConfig;
import com.netscape.utrain.R;
import com.netscape.utrain.databinding.ActivityCoachLoginBinding;
import com.netscape.utrain.databinding.ActivityCoachSignupBinding;
import com.netscape.utrain.model.CoachSignUpModel;
import com.netscape.utrain.response.CoachSignUpResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.AppController;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.FileUtil;
import com.netscape.utrain.utils.ImageFilePath;

import java.io.File;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoachSignupActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityCoachSignupBinding binding;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Retrofitinterface retrofitinterface;
    CoachSignUpModel mModel;
    ProgressDialog progressDialog;
    public static final int REQUEST_CODE = 1;
    Uri imageUri;

    private double latitude = 0.0, longitude = 0.0;

    private AskPermission askPermObj;
    private AlertDialog dialogMultiOrder;
    private CoachSignupActivity activity;
    private File photoFile = null;
    private String currentPhotoFilePath = "", imageUrl = "";
    private GoogleApiClient googleApiClient;
    private Location mylocation;
    private final static int REQUEST_CHECK_SETTINGS_GPS = 0x1;







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
        setContentView(R.layout.activity_coach_signup);



        binding= DataBindingUtil.setContentView(this,R.layout.activity_coach_signup);
        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);
        progressDialog = new ProgressDialog(CoachSignupActivity.this);

        binding.coachEnterStartbsnsHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(CoachSignupActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        binding.coachEnterStartbsnsHour.setText(hourOfDay + ":" + minute);
                    }
                },mHour,mMinute,true);
                timePickerDialog.show();            }
        });
        binding.coachEnterendbsnsHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(CoachSignupActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {

                        binding.coachEnterendbsnsHour.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute,true);
                timePickerDialog.show();
            }
        });
        binding.coachUploadTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    if (ActivityCompat.checkSelfPermission(CoachSignupActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(CoachSignupActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
                    } else {
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(galleryIntent, REQUEST_CODE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        init();


        binding.coachNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // NextButtonCoding.....


                if (binding.coachNameEdt.getText().toString().isEmpty())
                {
                    binding.coachNameEdt.setError("Please enter name");
                } else if (binding.coachEmaiEdt.getText().toString().isEmpty())
                {
                    binding.coachEmaiEdt.setError("Please enter email");
                } else if (binding.coachPasswordEdt.getText().toString().isEmpty()) {
                    binding.coachPasswordEdt.setError("Please enter password");
                } else if (binding.coachPhoneEdt.getText().toString().isEmpty()){
                    binding.coachPhoneEdt.setError("Please enter password");
                } else if (binding.coachLocationEdt.getText().toString().isEmpty()){
                    binding.coachLocationEdt.setError("Please enter location");
                } else if (binding.coachBioEdt.getText().toString().isEmpty()) {
                    binding.coachBioEdt.setError("Please enter bio");
                } else if (binding.coachEnterExperienceDetailEdt.getText().toString().isEmpty()){
                    binding.coachEnterExperienceDetailEdt.setError("Please enter experience");
                } else if (binding.coachEnterHourlyRateEdt.getText().toString().isEmpty()){
                    binding.coachEnterHourlyRateEdt.setError("Please enter rate");
                } else{
                    // hit CoachRegisterApi
                    progressDialog.setMessage("Loading....");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    MultipartBody.Part userImg = null;
                    if (photoFile != null) {
                        userImg = MultipartBody.Part.createFormData("profile_image", photoFile.getName(), RequestBody.create(MediaType.parse("image/*"), photoFile));
                    }
                    Call<CoachSignUpResponse> call = retrofitinterface.coachSignUp(Constants.CONTENT_TYPE,
                                                                                             binding.coachNameEdt.getText().toString(),
                                                                                             binding.coachEmailTv.getText().toString(),
                                                                                             binding.coachPasswordEdt.getText().toString(),
                                                                                             binding.coachPhoneEdt.getText().toString(),
                                                                                             binding.coachLocationEdt.getText().toString(),
                                                                                            latitude+"",
                                                                                            longitude+"",
                                                                                             binding.coachEnterStartbsnsHour.getText().toString(),
                                                                                             binding.coachEnterendbsnsHour.getText().toString(),
                                                                                             binding.coachBioEdt.getText().toString(),
                                                                                            "[12,15]",
                                                                                             binding.coachEnterExperienceDetailEdt.getText().toString(),
                                                                                             binding.coachEnterHourlyRateEdt.getText().toString(),
                                                                                             Constants.DEVICE_TYPE,
                                                                                             Constants.DEVICE_TOKEN);                                                                                        );
                    call.enqueue(new Callback<CoachSignUpResponse>() {
                        @Override
                        public void onResponse(Call<CoachSignUpResponse> call, Response<CoachSignUpResponse> response) {
                            if (response.isSuccessful()){
                                progressDialog.dismiss();
                                if (response.body().isStatus())
                                {
                                    mModel = new CoachSignUpModel();
                                    mModel = response.body().getData();
                                    Toast.makeText(CoachSignupActivity.this,""+ response.body().getData().getName(),Toast.LENGTH_LONG).show();
                                } else

                                    Toast.makeText(CoachSignupActivity.this,""+ response.body().getError().getError_message(),Toast.LENGTH_LONG).show();

                            }
                            else
                            {
                                Toast.makeText(CoachSignupActivity.this,"Api not Successfull",Toast.LENGTH_LONG).show();

                            }
                        }

                        @Override
                        public void onFailure(Call<CoachSignUpResponse> call, Throwable t) {
                            progressDialog.dismiss();

                        }
                    });



                }


            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == requestCode && resultCode == RESULT_OK)
        {
//            imageUri = data.getData();
//
//            String[] filePathColumn = { MediaStore.Images.Media.DATA };
//
//            Cursor cursor = getContentResolver().query(imageUri,
//                    filePathColumn, null, null, null);
//            cursor.moveToFirst();
//
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            String picturePath = cursor.getString(columnIndex);
//            cursor.close();
//
//            binding.coachCoachProfileImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
//            binding.coachUploadTv.setVisibility(View.GONE);

            if (requestCode == Constants.REQUEST_CAMERA_CAPTURE) {
                if (resultCode == RESULT_OK) {
//                AppDelegate.Log("imageCaptured ", "result ok");
                    photoFile = new File(currentPhotoFilePath);
//                AppDelegate.Log("imageCaptured ", currentPhotoFilePath);
                    if (photoFile != null) {
                        imageUrl = photoFile.getPath();
                        Glide.with(this).load(photoFile.getPath()).into(binding.coachCoachProfileImage);
                    } else {
                        Toast.makeText(CoachSignupActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CoachSignupActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
//                AppDelegate.Log("imageCaptured ", "result failed");
                }
            } else if (requestCode == Constants.REQUEST_CODE_GALLERY && resultCode == RESULT_OK) {
                String realPath = ImageFilePath.getPath(this, data.getData());
                currentPhotoFilePath = realPath;
                photoFile = new File(realPath);
                if (photoFile != null)
                    Glide.with(this).load(photoFile.getPath()).into(binding.coachCoachProfileImage);
            }

        }
    }
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
        TextView cancel = (TextView) content.findViewById(R.id.cancelSelectionBtn);
        dialogMultiOrder = builder.create();
//        dialogMultiOrder.setCanceledOnTouchOutside(false);
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleGalleryImage();
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImageUsingCamera();
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
            isPermissionGranted(CoachSignupActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE,
                    Constants.MY_PERMISSION_GALLERY);
        } else {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            CoachSignupActivity.this.startActivityForResult(photoPickerIntent, Constants.REQUEST_CODE_GALLERY);
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
                            .requestLocationUpdates(googleApiClient, locationRequest, (LocationListener) this);
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

    private void init() {

    }

    @Override
    public void onClick(View view) {

    }
}
