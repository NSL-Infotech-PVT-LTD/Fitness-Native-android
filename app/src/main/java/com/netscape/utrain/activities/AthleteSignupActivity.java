package com.netscape.utrain.activities;

import androidx.annotation.NonNull;
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
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.netscape.utrain.BuildConfig;
import com.netscape.utrain.R;
import com.netscape.utrain.databinding.ActivityAthleteSignupBinding;
import com.netscape.utrain.response.AthleteSignUpResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.AppController;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.FileUtil;
import com.netscape.utrain.utils.ImageFilePath;

import org.json.JSONObject;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AthleteSignupActivity extends AppCompatActivity implements View.OnClickListener {
    TextInputEditText etName, etEmail;
    private ActivityAthleteSignupBinding binding;
    private AlertDialog dialogMultiOrder;
    private String currentPhotoFilePath = "", imageUrl = "";
    private File photoFile = null;
    private AskPermission askPermObj;
    private LocationManager mLocManager;
    private LocationListener mLocListener;
    private Retrofitinterface retrofitInterface;
    private ProgressDialog progressDialog;
    private String userName = "", userEmail = "", userPhone = "", userAddress = "", userPassword;

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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_athlete_signup);
        init();
    }

    private void init() {
        binding.athleteSignUpBtn.setOnClickListener(this);
        binding.athleteSignInTv.setOnClickListener(this);
        binding.athleteprofileImageView.setOnClickListener(this);
        askPermObj = new AskPermission(getApplicationContext(), this);
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
//                Intent mainActivity = new Intent(AthleteSignupActivity.this, BottomNavigation.class);
//                startActivity(mainActivity);
                break;
            case R.id.athleteSignInTv:
                Intent signInActivity = new Intent(AthleteSignupActivity.this, LoginActivity.class);
                startActivity(signInActivity);
                break;
        }
    }

    private void getSignUpData() {

        if (binding.athleteNameEdt.getText().toString().isEmpty()) {
            binding.athleteNameEdt.setError(getString(R.string.enter_name));
        } else if (binding.athleteEmailEdt.getText().toString().isEmpty()) {
            binding.athleteEmailEdt.setError(getString(R.string.enter_your_email));
        } else if (binding.athletePhoneEdt.getText().toString().isEmpty()) {
            binding.athletePhoneEdt.setError(getString(R.string.enter_phone_number));
        } else if (binding.athletePasswordEdt.getText().toString().isEmpty()) {
            binding.athletePasswordEdt.setError(getString(R.string.enter_password));
        } else {
            loginApiMethod();
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
                } else {
                    Toast.makeText(AthleteSignupActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(AthleteSignupActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
//                AppDelegate.Log("imageCaptured ", "result failed");
            }
        } else if (requestCode == Constants.REQUEST_CODE_GALLERY && resultCode == RESULT_OK) {
            String realPath = ImageFilePath.getPath(this, data.getData());
            currentPhotoFilePath = realPath;
            photoFile = new File(realPath);
            if (photoFile != null)
                Glide.with(this).load(photoFile.getPath()).into(binding.athleProfileImg);
        }
//        } else {
//            if (requestCode == Constants.REQUEST_CODE_GET_ADDRESS) {
//                if (resultCode == RESULT_OK) {
//                    if (data != null && data.hasExtra(Constants.ADDRESS)) {
//                        binding.addressEdt.setText(data.getStringExtra(Constants.ADDRESS));
//                        binding.addressEdt.setError(null);
//                        locationLatLng = data.getStringExtra(Constants.LONGITUDE);
//
//                    }
//                }
//            } else if (callbackManager.onActivityResult(requestCode, resultCode, data)) {
//                return;
//            }
    }

    private void loginApiMethod() {
//        CommonMethods.hideKeyboard(this);
//        ProgressDialog progressDialog;
        progressDialog.show();
        MultipartBody.Part userImg = null;
        if (photoFile != null) {
            userImg = MultipartBody.Part.createFormData("profile_image", photoFile.getName(), RequestBody.create(MediaType.parse("image/*"), photoFile));
        }

        Call<AthleteSignUpResponse> signUpAthlete = retrofitInterface.athleteSignUp(userImg, binding.athleteNameEdt.getText().toString(), binding.athleteEmailEdt.getText().toString(), binding.athletePasswordEdt.getText().toString(), binding.athletePhoneEdt.getText().toString(), binding.athleteAddressEdt.getText().toString(), "92.0", "91.3", Constants.DEVICE_TYPE, Constants.DEVICE_TOKEN, Constants.CONTENT_TYPE);
        signUpAthlete.enqueue(new Callback<AthleteSignUpResponse>() {
            @Override
            public void onResponse(Call<AthleteSignUpResponse> call, Response<AthleteSignUpResponse> response) {


                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData() != null) {
                            Toast.makeText(AthleteSignupActivity.this, "" + response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
                            Intent service = new Intent(getApplicationContext(), BottomNavigation.class);
//                            service.putExtra(PrefrenceConstant.USER_MOBILE, phoneNum);
//                            service.putExtra(PrefrenceConstant.USER_OTP, response.body().getData().getOtp());
                            startActivity(service);
                        }
                    } else {
                        Toast.makeText(AthleteSignupActivity.this, "" + response.body().getError().getError_message(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressDialog.dismiss();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);
                        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<AthleteSignUpResponse> call, Throwable t) {

            }
        });
    }

}




