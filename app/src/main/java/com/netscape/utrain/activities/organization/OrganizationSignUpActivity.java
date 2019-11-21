package com.netscape.utrain.activities.organization;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
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
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
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
import com.google.android.material.textview.MaterialTextView;
import com.iceteck.silicompressorr.SiliCompressor;
import com.netscape.utrain.BuildConfig;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.AskPermission;
import com.netscape.utrain.activities.LoginActivity;
import com.netscape.utrain.activities.PortfolioActivity;
import com.netscape.utrain.activities.SelectedServiceList;
import com.netscape.utrain.activities.ServicePriceActivity;
import com.netscape.utrain.activities.ViewCoachStaffListActivity;
import com.netscape.utrain.activities.athlete.AthleteSignupActivity;
import com.netscape.utrain.activities.athlete.ChooseSportActivity;
import com.netscape.utrain.activities.coach.CoachSignupActivity;
import com.netscape.utrain.adapters.SelectServiceSpinnerAdapter;
import com.netscape.utrain.databinding.ActivityOrganizationSignUpBinding;
import com.netscape.utrain.model.OrgSpinnerCheckBoxModel;
import com.netscape.utrain.model.OrgUserDataModel;
import com.netscape.utrain.model.ViewCoachListDataModel;
import com.netscape.utrain.response.OrgSignUpResponse;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.text.DecimalFormat;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrganizationSignUpActivity extends AppCompatActivity implements View.OnClickListener, LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private final static int REQUEST_ID_MULTIPLE_PERMISSIONS = 0x2;
    private final static int REQUEST_CHECK_SETTINGS_GPS = 0x1;
    private ActivityOrganizationSignUpBinding binding;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private String orgName = "", orgEmail = "", orgPhone = "", orgPasswod = "", orgBio = "", orgAddress = "",
            orgServiceDetails = "", orgStartTime = "", orgEndTime = "", orgProfessionType = "", orgExpertise = "", orgExpDetail = "", orgTrainingDetail = "", orgHourlyRate = "";
    private AskPermission askPermObj;
    private AlertDialog dialogMultiOrder;
    private File photoFile = null;
    private String currentPhotoFilePath = "", imageUrl = "", imgRealPath = "";
    private String address = "";
    private double latitude = 0.0, longitude = 0.0;
    private LocationManager mLocManager;
    private LocationListener mLocListener;
    private GoogleApiClient googleApiClient;
    private OrganizationSignUpActivity activity;
    private Location mylocation;
    private OrgUserDataModel orgDataModel;
    private ViewCoachListDataModel viewCoachListDataModel;
    private String activeUserType = "";
    private File mediaStorageDir;
    public static boolean update=false;
    private ProgressDialog progressDialog;
    private Retrofitinterface retrofitinterface;
    private String latUpdate="";
    private String longUpdate="";


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

        binding = DataBindingUtil.setContentView(this, R.layout.activity_organization_sign_up);
        init();
        retrofitinterface= RetrofitInstance.getClient().create(Retrofitinterface.class);
        PortfolioActivity.clearFromConstants();
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading..");

        mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "UtCompressed");

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("App", "failed to create directory");
            }
        }

        if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, OrganizationSignUpActivity.this).equals(Constants.Organizer)) {
            orgViewCoachStaffView();
        }

        // For update to CoachStaff receive intent below....

        viewCoachListDataModel = (ViewCoachListDataModel) getIntent().getSerializableExtra("updateCoach");
        if (viewCoachListDataModel != null) {
            binding.orgNameEdt.setText(viewCoachListDataModel.getName());
            binding.orgBioEdt.setText(viewCoachListDataModel.getBio());
            binding.orgProfessionType.setText(viewCoachListDataModel.getProfession());
            binding.orgExperienceEdt.setText(viewCoachListDataModel.getExpertise_years());
            binding.orgExperienceDetailEdt.setText(viewCoachListDataModel.getExperience_detail());
            binding.orgTrainingDetailEdt.setText(viewCoachListDataModel.getTraining_service_detail());
            binding.orgHourlyRateEdt.setText(viewCoachListDataModel.getHourly_rate());
        }
        if (update){
            binding.orgNameEdt.setVisibility(View.GONE);
            binding.organizationNameTv.setVisibility(View.GONE);
            binding.orgEmailEdt.setVisibility(View.GONE);
            binding.organizationEmailTv.setVisibility(View.GONE);
            binding.orgPasswordEdt.setVisibility(View.GONE);
            binding.orgPasswordEdtLayout.setVisibility(View.GONE);
            binding.organizationPassTv.setVisibility(View.GONE);

            binding.signUpType.setText("Organization");
            binding.orgNameEdt.setText(CommonMethods.getPrefData(PrefrenceConstant.USER_NAME,OrganizationSignUpActivity.this));
            binding.orgEmailEdt.setText(CommonMethods.getPrefData(PrefrenceConstant.USER_EMAIL,OrganizationSignUpActivity.this));
            binding.orgPhoneEdt.setText(CommonMethods.getPrefData(PrefrenceConstant.USER_PHONE,OrganizationSignUpActivity.this));
            binding.orgAddressEdt.setText(CommonMethods.getPrefData(PrefrenceConstant.ADDRESS,OrganizationSignUpActivity.this));
            binding.orgPasswordEdt.setText("1234");
            binding.orgBioEdt.setText(CommonMethods.getPrefData(PrefrenceConstant.BIO,OrganizationSignUpActivity.this));
            binding.orgStartTimeTv.setText(CommonMethods.getPrefData(PrefrenceConstant.BUSINESS_HOUR_START,OrganizationSignUpActivity.this));
            binding.orgEndTimeTv.setText(CommonMethods.getPrefData(PrefrenceConstant.BUSINESS_HOUR_ENDS,OrganizationSignUpActivity.this));
            binding.orgProfessionType.setText(CommonMethods.getPrefData(PrefrenceConstant.PROFESSION,OrganizationSignUpActivity.this));
            binding.orgExperienceEdt.setText(CommonMethods.getPrefData(PrefrenceConstant.USER_EXPERIENCE,OrganizationSignUpActivity.this));
            binding.orgExperienceDetailEdt.setText(CommonMethods.getPrefData(PrefrenceConstant.EXPERIENCE_DETAILS,OrganizationSignUpActivity.this));
            binding.orgTrainingDetailEdt.setText(CommonMethods.getPrefData(PrefrenceConstant.USER_TRAINING_DETAIL,OrganizationSignUpActivity.this));
            binding.orgHourlyRateEdt.setText(CommonMethods.getPrefData(PrefrenceConstant.PRICE,OrganizationSignUpActivity.this));
            binding.orgNextBtn.setText("Update");

            latUpdate = CommonMethods.getPrefData(PrefrenceConstant.USER_LATITUDE, OrganizationSignUpActivity.this);
            longUpdate = CommonMethods.getPrefData(PrefrenceConstant.USER_LONGITUDE, OrganizationSignUpActivity.this);
            if (!TextUtils.isEmpty(latUpdate)) {
                latitude = Double.parseDouble(latUpdate);
            }
            if (!TextUtils.isEmpty(longUpdate)) {
                longitude = Double.parseDouble(longUpdate);
            }
            Glide.with(OrganizationSignUpActivity.this).load(Constants.ORG_IMAGE_BASE_URL + CommonMethods.getPrefData(PrefrenceConstant.PROFILE_IMAGE, this)).into(binding.orgProfileImg);






        }

    }

    private void orgViewCoachStaffView() {

        binding.organizationEmailTv.setVisibility(View.GONE);
        binding.orgEmailEdt.setVisibility(View.GONE);
        binding.organizationPhoneNoTv.setVisibility(View.GONE);
        binding.orgPhoneEdt.setVisibility(View.GONE);
        binding.organizationLocationTv.setVisibility(View.GONE);
        binding.orgAddressEdt.setVisibility(View.GONE);
        binding.organizationPassTv.setVisibility(View.GONE);
        binding.orgPasswordEdtLayout.setVisibility(View.GONE);
        binding.organizationBusinessHourstartTv.setVisibility(View.GONE);
        binding.organizationBusinessHourendTv.setVisibility(View.GONE);
        binding.orgStartTimeTv.setVisibility(View.GONE);
        binding.orgEndTimeTv.setVisibility(View.GONE);


    }

    private void validateViewCoachFields() {

        // ViewCoachStaff Validations with api

        viewCoachListDataModel = new ViewCoachListDataModel();
        orgName = binding.orgNameEdt.getText().toString();
        viewCoachListDataModel.setName(orgName);
        orgBio = binding.orgBioEdt.getText().toString();
        viewCoachListDataModel.setBio(orgBio);
        orgProfessionType = binding.orgProfessionType.getText().toString();
        viewCoachListDataModel.setProfession(orgProfessionType);
        orgExpertise = binding.orgExperienceEdt.getText().toString();
        viewCoachListDataModel.setExpertise_years(orgExpertise);
        orgExpDetail = binding.orgExperienceDetailEdt.getText().toString();
        viewCoachListDataModel.setExperience_detail(orgExpDetail);
        orgTrainingDetail = binding.orgTrainingDetailEdt.getText().toString();
        viewCoachListDataModel.setTraining_service_detail(orgTrainingDetail);
        orgHourlyRate = binding.orgHourlyRateEdt.getText().toString();
        viewCoachListDataModel.setHourly_rate(orgHourlyRate);

        if (orgName.isEmpty()) {
            binding.orgNameEdt.setError(getResources().getString(R.string.enter_name));
            binding.orgNameEdt.requestFocus();
        } else if (orgBio.isEmpty()) {
            binding.orgBioEdt.setError(getResources().getString(R.string.enter_your_bio));
            binding.orgBioEdt.requestFocus();
        } else if (orgProfessionType.isEmpty()) {
            binding.orgProfessionType.setError(getResources().getString(R.string.enter_profession_type));
            binding.orgProfessionType.requestFocus();
        } else if (orgExpertise.isEmpty()) {
            binding.orgExperienceEdt.setError(getResources().getString(R.string.enter_your_experites));
            binding.orgExperienceEdt.requestFocus();
//            String expertise = binding.orgExperienceEdt.getText().toString();
//            Double number = Double.parseDouble(expertise);
        } else if (orgExpDetail.isEmpty()) {
            binding.orgExperienceDetailEdt.setError(getResources().getString(R.string.enter_your_experience_details));
            binding.orgExperienceDetailEdt.requestFocus();
        } else if (orgTrainingDetail.isEmpty()) {
            binding.orgTrainingDetailEdt.setError(getResources().getString(R.string.enter_org_service_details));
            binding.orgTrainingDetailEdt.requestFocus();
        } else if (orgHourlyRate.isEmpty()) {
            binding.orgHourlyRateEdt.setError(getResources().getString(R.string.enter_hourly_rate_text));
            binding.orgHourlyRateEdt.requestFocus();
        } else if (Integer.parseInt(binding.orgHourlyRateEdt.getText().toString()) < 4) {
            binding.orgHourlyRateEdt.setError("Hourly rate should not less than 4");

        } else if (photoFile == null) {
            Toast.makeText(OrganizationSignUpActivity.this, getResources().getString(R.string.add_profile_image), Toast.LENGTH_SHORT).show();
        } else {
            viewCoachListDataModel.setoCoachProfileImg(photoFile);
//            viewCoachListDataModel.setLatitude(String.valueOf(latitude));
//            viewCoachListDataModel.setLongitude(String.valueOf(longitude));
            Intent viewCoachAct = new Intent(OrganizationSignUpActivity.this, ChooseSportActivity.class);
            viewCoachAct.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            viewCoachAct.putExtra("orgCoachStaff", viewCoachListDataModel);
            viewCoachAct.putExtra(Constants.ActiveUserType, Constants.TypeOrganization);
            viewCoachAct.putExtra("createStaffType", "orgstaffCreate");
            startActivity(viewCoachAct);
        }

    }


    private void init() {
        clearPrefrences();
        activity = this;
        askPermObj = new AskPermission(getApplicationContext(), this);
        binding.orgProfileImg.setOnClickListener(this);
        binding.orgStartTimeTv.setOnClickListener(this);
        binding.orgEndTimeTv.setOnClickListener(this);
        binding.orgNextBtn.setOnClickListener(this);
        binding.orgAddressEdt.setOnClickListener(this);
        if (getIntent().getExtras() != null) {
            activeUserType = getIntent().getStringExtra(Constants.ActiveUserType);
            if (activeUserType != null)
                if (activeUserType.equals(Constants.TypeCoach)) {
                    binding.signUpType.setText(getResources().getString(R.string.coach));
                    binding.orgNextBtn.setText(getResources().getString(R.string.two_more_step));
                }
            if (activeUserType != null)
                if (activeUserType.equals(Constants.TypeOrganization)) {
                    binding.signUpType.setText(getResources().getString(R.string.organization));
                    binding.orgNextBtn.setText(getResources().getString(R.string.two_more_step));
                }
            if (activeUserType != null)
                if (activeUserType.equals((Constants.TypeOrgCoach))) {
                    binding.signUpType.setText(getResources().getString(R.string.coach));
                    binding.orgNextBtn.setText(getResources().getString(R.string.one_more_step));
                }
        }

        setUpGClient();

        binding.orgAddressEdt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (event.getRawX() >= (binding.orgAddressEdt.getRight() - binding.orgAddressEdt.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (checkPermissions())
                            binding.orgAddressEdt.setText(address);
                        return true;
                    }
                }
                return false;
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.orgProfileImg:
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
            case R.id.orgStartTimeTv:
                getStartTime();
                break;
            case R.id.orgEndTimeTv:
                getEndTime();
                break;
            case R.id.orgNextBtn:
//                Intent in=new Intent(OrganizationSignUpActivity.this,ServicePriceActivity.class);
//                startActivity(in);
                if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, OrganizationSignUpActivity.this).equals(Constants.Organizer)) {
                    validateViewCoachFields();
                } else {
                    validateEditTextData();
                }


                break;
            case R.id.orgAddressEdt:
                break;

        }
    }

    private void askRequiredPermission() {
        if (!askPermObj.isPermissionGiven(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            askPermObj.askPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Constants.WRITE_PERMISSION);
        }
        if (!askPermObj.isPermissionGiven(this, Manifest.permission.CAMERA)) {
            if (!askPermObj.isPermissionGiven(this, Manifest.permission.CAMERA)) {
                askPermObj.askPermission(Manifest.permission.CAMERA, Constants.CAMERA_PERMISSION);
                return;
            }
            return;
        } else {
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


    public void getImageFromStorage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            isPermissionGranted(OrganizationSignUpActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE,
                    Constants.MY_PERMISSION_GALLERY);
        } else {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            OrganizationSignUpActivity.this.startActivityForResult(photoPickerIntent, Constants.REQUEST_CODE_GALLERY);
        }
    }

    private void validateEditTextData() {
        orgDataModel = new OrgUserDataModel();
        orgName = binding.orgNameEdt.getText().toString();
        orgDataModel.setName(orgName);
        orgEmail = binding.orgEmailEdt.getText().toString();
        orgDataModel.setEmail(orgEmail);
        orgPhone = binding.orgPhoneEdt.getText().toString();
        orgDataModel.setPhone(orgPhone);
        orgAddress = binding.orgAddressEdt.getText().toString();
        orgDataModel.setLocation(orgAddress);
        orgPasswod = binding.orgPasswordEdt.getText().toString();
        orgDataModel.setPassword(orgPasswod);
        orgBio = binding.orgBioEdt.getText().toString();
        orgDataModel.setBio(orgBio);
        orgStartTime = binding.orgStartTimeTv.getText().toString();
        orgDataModel.setBusiness_hour_starts(orgStartTime);
        orgEndTime = binding.orgEndTimeTv.getText().toString();
        orgDataModel.setBusiness_hour_ends(orgEndTime);
        orgProfessionType = binding.orgProfessionType.getText().toString();
        orgDataModel.setProfessionType(orgProfessionType);
        orgExpertise = binding.orgExperienceEdt.getText().toString();
        orgDataModel.setExpertise_years(orgExpertise);
        orgExpDetail = binding.orgExperienceDetailEdt.getText().toString();
        orgDataModel.setExperienceDetail(orgExpDetail);
        orgTrainingDetail = binding.orgTrainingDetailEdt.getText().toString();
        orgDataModel.setTrainingDetail(orgTrainingDetail);
        orgHourlyRate = binding.orgHourlyRateEdt.getText().toString();
        orgDataModel.setHourly_rate(orgHourlyRate);

        if (orgName.isEmpty()) {
            binding.orgNameEdt.setError(getResources().getString(R.string.enter_name));
            binding.orgNameEdt.requestFocus();
        } else if (orgEmail.isEmpty()) {
            binding.orgEmailEdt.setError(getResources().getString(R.string.enter_email));
            binding.orgEmailEdt.requestFocus();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(orgEmail).matches()) {
            binding.orgEmailEdt.setError(getResources().getString(R.string.enter_valid_email));
            binding.orgEmailEdt.requestFocus();
        } else if (orgPhone.isEmpty()) {
            binding.orgPhoneEdt.setError(getResources().getString(R.string.enter_phone_number));
            binding.orgPhoneEdt.requestFocus();
        } else if (orgPhone.length() < 6) {
            binding.orgPhoneEdt.setError(getResources().getString(R.string.enter_six_diget_phone_number));
            binding.orgPhoneEdt.requestFocus();
        } else if (orgPhone.length() > 10) {
            binding.orgPhoneEdt.setError(getResources().getString(R.string.enter_ten_diget_phone_number));
            binding.orgPhoneEdt.requestFocus();
        } else if (orgAddress.isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.select_address), Toast.LENGTH_SHORT).show();
            binding.orgAddressEdt.requestFocus();
        } else if (orgPasswod.isEmpty()) {
            binding.orgPasswordEdt.setError(getResources().getString(R.string.enter_password));
            binding.orgPasswordEdt.requestFocus();
            binding.orgPasswordEdt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if (b)
                        binding.orgPasswordEdtLayout.setEnabled(false);

                }
            });
        } else if (orgBio.isEmpty()) {
            binding.orgBioEdt.setError(getResources().getString(R.string.enter_your_bio));
            binding.orgBioEdt.requestFocus();
        } else if (orgStartTime.isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.select_business_start_hour), Toast.LENGTH_SHORT).show();
            binding.orgStartTimeTv.requestFocus();
        } else if (orgEndTime.isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.select_business_end_hour), Toast.LENGTH_SHORT).show();
            binding.orgNameEdt.requestFocus();
        } else if (orgProfessionType.isEmpty()) {
            binding.orgProfessionType.setError(getResources().getString(R.string.enter_profession_type));
            binding.orgProfessionType.requestFocus();
        } else if (orgExpertise.isEmpty()) {
            binding.orgExperienceEdt.setError(getResources().getString(R.string.enter_your_experites));
            binding.orgExperienceEdt.requestFocus();
            String expertise = binding.orgExperienceEdt.getText().toString();
            Double number = Double.parseDouble(expertise);
        } else if (orgExpDetail.isEmpty()) {
            binding.orgExperienceDetailEdt.setError(getResources().getString(R.string.enter_your_experience_details));
            binding.orgExperienceDetailEdt.requestFocus();
        } else if (orgTrainingDetail.isEmpty()) {
            binding.orgTrainingDetailEdt.setError(getResources().getString(R.string.enter_org_service_details));
            binding.orgTrainingDetailEdt.requestFocus();
        } else if (orgHourlyRate.isEmpty()) {
            binding.orgHourlyRateEdt.setError(getResources().getString(R.string.enter_hourly_rate));
            binding.orgHourlyRateEdt.requestFocus();
        } else if (Integer.parseInt(binding.orgHourlyRateEdt.getText().toString()) < 4) {
            binding.orgHourlyRateEdt.setError("Hourly rate should not less than 4");

        } else if (photoFile == null) {
            if (update) {
                updateOrgBasicProfile();
            }else {
                Toast.makeText(OrganizationSignUpActivity.this, getResources().getString(R.string.add_profile_image), Toast.LENGTH_SHORT).show();

            }
        } else {
            if (update) {
                updateOrgBasicProfile();
            }else {
                orgDataModel.setProfile_img(photoFile);
                orgDataModel.setLatitude(String.valueOf(latitude));
                orgDataModel.setLongitude(String.valueOf(longitude));
                hitOrgSignUpApi();
            }
        }
    }

    private void updateOrgBasicProfile() {

    }

    // int result = 0;
    //    result = Integer.parseInt(binding.orgHourlyRateEdt.getText().toString());
    //
    //                    if (result <=39)
    //            binding.orgHourlyRateEdt.setError("Hourly rate should grater than 40");
    //                    else
    //                            binding.orgHourlyRateEdt.setError("");

    private void hitOrgSignUpApi() {
        if (activeUserType.equals(Constants.TypeOrganization)) {
            Intent intent = new Intent(OrganizationSignUpActivity.this, ServicePriceActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra(Constants.OrgSignUpIntent, orgDataModel);
            intent.putExtra(Constants.ActiveUserType, Constants.TypeOrganization);
            startActivity(intent);
        }
        if (activeUserType.equals(Constants.TypeCoach)) {
            Intent intent = new Intent(OrganizationSignUpActivity.this, ChooseSportActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra(Constants.OrgSignUpIntent, orgDataModel);
            intent.putExtra(Constants.ActiveUserType, Constants.TypeCoach);
            ChooseSportActivity.coachActive = true;
            startActivity(intent);
        }

    }

    public void getStartTime() {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(OrganizationSignUpActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        binding.orgStartTimeTv.setText(convertDate(hourOfDay) + ":" + convertDate(minute));
                    }
                }, mHour, mMinute, true);
        timePickerDialog.show();

    }

    public String convertDate(int input) {
        if (input >= 10) {
            return String.valueOf(input);
        } else {
            return "0" + String.valueOf(input);
        }
    }

    public void getEndTime() {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(OrganizationSignUpActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        binding.orgEndTimeTv.setText(convertDate(hourOfDay) + ":" + convertDate(minute));
                    }
                }, mHour, mMinute, true);
        timePickerDialog.show();
    }

    private boolean checkPermissions() {
        int permissionLocation = ContextCompat.checkSelfPermission(OrganizationSignUpActivity.this,
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

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_CAMERA_CAPTURE) {
            if (resultCode == RESULT_OK) {
//                AppDelegate.Log("imageCaptured ", "result ok");
                String filePath = SiliCompressor.with(getApplicationContext()).compress(currentPhotoFilePath, mediaStorageDir);

                photoFile = new File(filePath);
//                AppDelegate.Log("imageCaptured ", currentPhotoFilePath);
                if (photoFile != null) {
//                    plus.setVisibility(View.GONE);
                    Glide.with(this).load(photoFile.getPath()).into(binding.orgProfileImg);
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
            String filePath = SiliCompressor.with(getApplicationContext()).compress(realPath, mediaStorageDir);

            if (filePath != null) {
                photoFile = new File(filePath);
            }
            if (photoFile != null)
//                plus.setVisibility(View.GONE);
                Glide.with(this).load(photoFile.getPath()).into(binding.orgProfileImg);

        }
    }

    @Override
    protected void onDestroy() {
        clearPrefrences();
        update=false;
        super.onDestroy();
    }

    public void clearPrefrences() {
        CommonMethods.clearKeyPrefData(Constants.SELECTED_SERVICE, OrganizationSignUpActivity.this);
        CommonMethods.clearKeyPrefData(Constants.SERVICE_LIST, OrganizationSignUpActivity.this);

    }
    // Organization viewCoachStaff Activity code below....
    private void OrgSignUpApi() {
        progressDialog.show();
        MultipartBody.Part userImg = null;
        if (orgDataModel.getProfile_img() != null) {
            userImg = MultipartBody.Part.createFormData("profile_image", orgDataModel.getProfile_img().getName(), RequestBody.create(MediaType.parse("image/*"), orgDataModel.getProfile_img()));
        }
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        requestBodyMap.put("phone", RequestBody.create(MediaType.parse("multipart/form-data"), orgDataModel.getPhone()));
        requestBodyMap.put("location", RequestBody.create(MediaType.parse("multipart/form-data"), orgDataModel.getLocation()));
        requestBodyMap.put("latitude", RequestBody.create(MediaType.parse("multipart/form-data"), orgDataModel.getLatitude()));
        requestBodyMap.put("longitude", RequestBody.create(MediaType.parse("multipart/form-data"), orgDataModel.getLatitude()));
        requestBodyMap.put("business_hour_starts", RequestBody.create(MediaType.parse("multipart/form-data"), orgDataModel.getBusiness_hour_starts()));
        requestBodyMap.put("business_hour_ends", RequestBody.create(MediaType.parse("multipart/form-data"), orgDataModel.getBusiness_hour_ends()));
        requestBodyMap.put("bio", RequestBody.create(MediaType.parse("multipart/form-data"), orgDataModel.getBio()));
        requestBodyMap.put("expertise_years", RequestBody.create(MediaType.parse("multipart/form-data"), orgDataModel.getExpertise_years()));
        requestBodyMap.put("experience_detail", RequestBody.create(MediaType.parse("multipart/form-data"), orgDataModel.getExperienceDetail()));
        requestBodyMap.put("training_service_detail", RequestBody.create(MediaType.parse("multipart/form-data"), orgDataModel.getTrainingDetail()));
        requestBodyMap.put("hourly_rate", RequestBody.create(MediaType.parse("multipart/form-data"), orgDataModel.getHourly_rate()));
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
                                    CommonMethods.setPrefData(PrefrenceConstant.USER_NAME, response.body().getData().getUser().getName(), OrganizationSignUpActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.USER_EMAIL, response.body().getData().getUser().getEmail(), OrganizationSignUpActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.USER_PHONE, response.body().getData().getUser().getPhone(), OrganizationSignUpActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.USER_ID, response.body().getData().getUser().getId() + "", OrganizationSignUpActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.ADDRESS, response.body().getData().getUser().getLocation() + "", OrganizationSignUpActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.PROFILE_IMAGE, response.body().getData().getUser().getProfile_image() + "", OrganizationSignUpActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.USER_LAT, response.body().getData().getUser().getLatitude() + "", OrganizationSignUpActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.USER_LONG, response.body().getData().getUser().getLongitude() + "", OrganizationSignUpActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.BUSINESS_HOUR_ENDS, response.body().getData().getUser().getBio() + "", OrganizationSignUpActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.BUSINESS_HOUR_START, response.body().getData().getUser().getBusiness_hour_starts() + "", OrganizationSignUpActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.BIO, response.body().getData().getUser().getBio() + "", OrganizationSignUpActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.EXPERTISE_YEAR, response.body().getData().getUser().getExpertise_years(), OrganizationSignUpActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.USER_EXPERIENCE, response.body().getData().getUser().getExperience_detail() + "", OrganizationSignUpActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.PRICE, response.body().getData().getUser().getHourly_rate() + "", OrganizationSignUpActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.PORT_FOLIO_IMAGES, response.body().getData().getUser().getPortfolio_image() + "", OrganizationSignUpActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.ACHIVEMENTS, response.body().getData().getUser().getAchievements() + "", OrganizationSignUpActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.PROFESSION, response.body().getData().getUser().getProfession() + "", OrganizationSignUpActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.EXPERIENCE_DETAILS, response.body().getData().getUser().getExperience_detail() + "", OrganizationSignUpActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.USER_TRAINING_DETAIL, response.body().getData().getUser().getTraining_service_detail() + "", OrganizationSignUpActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.LOGED_IN_USER, PrefrenceConstant.ORG_LOG_IN, OrganizationSignUpActivity.this);
                                    Intent homeScreen = new Intent(getApplicationContext(), OrgHomeScreen.class);
                                    homeScreen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(homeScreen);
                                }
                            }
                        }else {
                            Snackbar.make(binding.orgSnackBar, response.body().getError().getError_message().getMessage().toString(), BaseTransientBottomBar.LENGTH_SHORT).show();

                        }
                    } else {
                        Snackbar.make(binding.orgSnackBar, response.body().getError().getError_message().getMessage().toString(), BaseTransientBottomBar.LENGTH_SHORT).show();
                    }
                } else {
                    progressDialog.dismiss();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);
                        Snackbar.make(binding.orgSnackBar, errorMessage, BaseTransientBottomBar.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Snackbar.make(binding.orgSnackBar, e.getMessage(), BaseTransientBottomBar.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<OrgSignUpResponse> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(binding.orgSnackBar, "" + t, BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });
    }


}
