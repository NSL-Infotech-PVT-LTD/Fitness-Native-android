package com.netscape.utrain.activities;

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
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.netscape.utrain.BuildConfig;
import com.netscape.utrain.PortfolioImagesConstants;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.athlete.AthleteHomeScreen;
import com.netscape.utrain.activities.athlete.AthleteLoginActivity;
import com.netscape.utrain.activities.organization.OrgHomeScreen;
import com.netscape.utrain.activities.organization.OrgMapFindAddressActivity;
import com.netscape.utrain.databinding.ActivityPortfolioBinding;
import com.netscape.utrain.model.OrgUserDataModel;
import com.netscape.utrain.model.ServiceIdModel;
import com.netscape.utrain.response.OrgSignUpResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.AppController;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.FileUtil;
import com.netscape.utrain.utils.ImageFilePath;
import com.netscape.utrain.utils.PrefrenceConstant;

import org.json.JSONArray;
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

public class PortfolioActivity extends AppCompatActivity implements View.OnClickListener {
    public static boolean getImages = false;
    MultipartBody.Part userImg = null;
    MultipartBody.Part portFolioImage1 = null, portFolioImage2 = null, portFolioImage3 = null, portFolioImage4 = null;
    private ActivityPortfolioBinding binding;
    private AskPermission askPermObj;
    private AlertDialog dialogMultiOrder;
    private String setImages;
    private String currentPhotoFilePath = "", imageUrl = "";
    private File photoFile = null;
    private ImageView imageView;
    private ImageView plus;
    private ProgressDialog progressDialog;
    private Retrofitinterface retrofitinterface;
    private List<File> imagesSelected = new ArrayList<>();
    private int position;
    private OrgUserDataModel orgDataModel;
    private List<MultipartBody.Part> imgPortfolio;
    private JSONArray selectedServices;
    private List<ServiceIdModel> servicesList = new ArrayList<>();


    public static boolean isPermissionGranted(Activity activity, String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(activity, permission)
                != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.requestPermissions(new String[]{permission}, requestCode);
            }
            return false;
        }
        return true;
    }

    public static void clearFromConstants() {
        PortfolioImagesConstants.imageOne = "";
        PortfolioImagesConstants.imageTwo = "";
        PortfolioImagesConstants.imageThree = "";
        PortfolioImagesConstants.imageFour = "";
        PortfolioImagesConstants.partOne = null;
        PortfolioImagesConstants.partTwo = null;
        PortfolioImagesConstants.partThree = null;
        PortfolioImagesConstants.partFour = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_portfolio);

        binding.portfolioBackArrowImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        init();
    }

    private void init() {
        if (getImages) {
            binding.portfolioTitleTv.setText("Select Images");
            binding.noteTv.setText("Select at least one image");
        }

        if (getIntent().getExtras() != null) {
            orgDataModel = (OrgUserDataModel) getIntent().getSerializableExtra(Constants.OrgSignUpIntent);
        }
        setImagesFromConstant();
        binding.addImageOne.setOnClickListener(this);
        binding.addImageTwo.setOnClickListener(this);
        binding.addImageThree.setOnClickListener(this);
        binding.addImageFour.setOnClickListener(this);
        binding.addImageSubmitBtn.setOnClickListener(this);
        binding.imagePlusone.setOnClickListener(this);
        binding.imgPlusTwo.setOnClickListener(this);
        binding.imgPlusThree.setOnClickListener(this);
        binding.imgPlusFour.setOnClickListener(this);
        binding.portfolioBackArrowImg.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        askPermObj = new AskPermission(getApplicationContext(), this);
        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imagePlusone:
                addFirstImage();
                break;
            case R.id.imgPlusTwo:
                addSecondImage();
                break;
            case R.id.imgPlusThree:
                addThirdImage();
                break;
            case R.id.imgPlusFour:
                addFourthImage();
                break;
            case R.id.addImageOne:
                addFirstImage();
                break;
            case R.id.addImageTwo:
                addSecondImage();
                break;
            case R.id.addImageThree:
                addThirdImage();
                break;
            case R.id.addImageFour:
                addFourthImage();
                break;
            case R.id.addImageSubmitBtn:
                imgPortfolio = new ArrayList<>();
                if (portFolioImage1 != null) {
                    imgPortfolio.add(portFolioImage1);
                }
                if (portFolioImage2 != null) {
                    imgPortfolio.add(portFolioImage2);
                }
                if (portFolioImage3 != null) {
                    imgPortfolio.add(portFolioImage3);
                }
                if (portFolioImage4 != null) {
                    imgPortfolio.add(portFolioImage4);
                }

                if (imgPortfolio != null && imgPortfolio.size() >= 1) {
                    if (getImages) {
                        PortfolioImagesConstants.numImages = String.valueOf(imgPortfolio.size());
                        sendDataToIntent();
                    } else {

                        OrgSignUpApi();
                    }
                } else {
                    Snackbar.make(binding.portFolioLayout, getResources().getString(R.string.select_portfolio_images), BaseTransientBottomBar.LENGTH_SHORT).show();
                }
                break;

            case R.id.portfolioBackArrowImg:
                finish();
                break;
        }
    }

    private void sendDataToIntent() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        PortfolioActivity.this.finish();
    }

    public void addFirstImage() {
        position = 1;
        imageView = binding.addImageOne;
        plus = binding.imagePlusone;
        checkForPermissions();
    }

    public void addSecondImage() {
        imageView = binding.addImageTwo;
        plus = binding.imgPlusTwo;
        position = 2;
        checkForPermissions();
    }

    public void addThirdImage() {
        position = 3;
        plus = binding.imgPlusThree;
        imageView = binding.addImageThree;
        checkForPermissions();

    }

    public void addFourthImage() {
        position = 4;
        plus = binding.imgPlusFour;
        imageView = binding.addImageFour;
        checkForPermissions();
    }

    private void checkForPermissions() {
        if (!askPermObj.isPermissionGiven(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            askRequiredPermission();
            return;
        }
        if (!askPermObj.isPermissionGiven(this, Manifest.permission.CAMERA)) {
            askRequiredPermission();
            return;
        }
        handleImageSelection();
    }

    private void askRequiredPermission() {
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
            } //9465878595
        }
    }

    private void handleGalleryImage() {
        if (!askPermObj.isPermissionGiven(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            askPermObj.askPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Constants.WRITE_PERMISSION);
            return;
        }
        getImageFromStorage();
    }

    public void getImageFromStorage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            isPermissionGranted(PortfolioActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE,
                    Constants.MY_PERMISSION_GALLERY);
        } else {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            PortfolioActivity.this.startActivityForResult(photoPickerIntent, Constants.REQUEST_CODE_GALLERY);
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
                    plus.setVisibility(View.GONE);
                    Glide.with(this).load(photoFile.getPath()).into(imageView);
                    if (getImages) {
                        userImg = MultipartBody.Part.createFormData("images_" + position, photoFile.getName(), RequestBody.create(MediaType.parse("image/*"), photoFile));
                        setImages = photoFile.getPath();
                    } else {
                        userImg = MultipartBody.Part.createFormData("portfolio_image_" + position, photoFile.getName(), RequestBody.create(MediaType.parse("image/*"), photoFile));
                        setImages = photoFile.getPath();
                    }
                    setPortfolioImages();

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
                currentPhotoFilePath = realPath;
                photoFile = new File(realPath);
            }

            if (photoFile != null)
                plus.setVisibility(View.GONE);
            Glide.with(this).load(photoFile.getPath()).into(imageView);
            setImages = photoFile.getPath();
            if (getImages) {
                userImg = MultipartBody.Part.createFormData("images_" + position, photoFile.getName(), RequestBody.create(MediaType.parse("image/*"), photoFile));

            } else {
                userImg = MultipartBody.Part.createFormData("portfolio_image_" + position, photoFile.getName(), RequestBody.create(MediaType.parse("image/*"), photoFile));
            }
            setPortfolioImages();
        } else if (requestCode == Constants.REQUEST_CODE_GALLERY) {

        }
    }

    private void OrgSignUpApi() {
        progressDialog.show();
        MultipartBody.Part userImg = null;
        if (orgDataModel.getProfile_img() != null) {
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
        requestBodyMap.put("service_ids", RequestBody.create(MediaType.parse("multipart/form-data"), orgDataModel.getSelectedServices()));
        requestBodyMap.put("expertise_years", RequestBody.create(MediaType.parse("multipart/form-data"), orgDataModel.getExpertise_years()));
        requestBodyMap.put("hourly_rate", RequestBody.create(MediaType.parse("multipart/form-data"), orgDataModel.getHourly_rate()));
        requestBodyMap.put("device_type", RequestBody.create(MediaType.parse("multipart/form-data"), Constants.DEVICE_TYPE));
        requestBodyMap.put("device_token", RequestBody.create(MediaType.parse("multipart/form-data"), CommonMethods.getPrefData(PrefrenceConstant.DEVICE_TOKEN, getApplicationContext())));
//        requestBodyMap.put("device_token", RequestBody.create(MediaType.parse("multipart/form-data"), Constants.DEVICE_TOKEN));
        Call<OrgSignUpResponse> signUpAthlete = retrofitinterface.registerOrganization(requestBodyMap, userImg, portFolioImage1, portFolioImage2, portFolioImage3, portFolioImage4);
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
                                    CommonMethods.setPrefData(PrefrenceConstant.ROLE_PLAY, role, PortfolioActivity.this);
                                    clearFromConstants();
                                    Constants.CHECKBOX_IS_CHECKED = 0;
                                    SelectedServiceList.getInstance().getList().clear();
                                    CommonMethods.setPrefData(PrefrenceConstant.USER_EMAIL, response.body().getData().getUser().getEmail(), PortfolioActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.USER_PHONE, response.body().getData().getUser().getPhone(), PortfolioActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.USER_NAME, response.body().getData().getUser().getName(), PortfolioActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.USER_ID, response.body().getData().getUser().getId() + "", PortfolioActivity.this);
                                    CommonMethods.setPrefData(Constants.AUTH_TOKEN, response.body().getData().getToken() + "", PortfolioActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.LOGED_IN_USER, PrefrenceConstant.ORG_LOG_IN, PortfolioActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.PROFILE_IMAGE, response.body().getData().getUser().getProfile_image() + "", PortfolioActivity.this);
                                    CommonMethods.setPrefData(PrefrenceConstant.PRICE, response.body().getData().getUser().getHourly_rate() + "", PortfolioActivity.this);
                                    servicesList.addAll(response.body().getData().getUser().getService_ids());
                                    storeServiceIds(servicesList);
                                    Intent homeScreen = new Intent(getApplicationContext(), OrgHomeScreen.class);
                                    homeScreen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(homeScreen);
                                }
                            }
                        }
                    } else {
                        Snackbar.make(binding.portFolioLayout, response.body().getError().getError_message().getMessage().toString(), BaseTransientBottomBar.LENGTH_SHORT).show();
                    }
                } else {
                    progressDialog.dismiss();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);
                        Snackbar.make(binding.portFolioLayout, errorMessage, BaseTransientBottomBar.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Snackbar.make(binding.portFolioLayout, e.getMessage(), BaseTransientBottomBar.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<OrgSignUpResponse> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(binding.portFolioLayout, "" + t, BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });
    }

    private void orginizationSignUpApi() {
        //        CommonMethods.hideKeyboard(this);
        progressDialog.show();
//        photoFile = new File(orgDataModel.getProfile_img());
        MultipartBody.Part profileImg = null;
        if (orgDataModel.getProfile_img() != null) {
            profileImg = MultipartBody.Part.createFormData("profile_image", photoFile.getName(), RequestBody.create(MediaType.parse("image/*"), photoFile));
        }
        Call<OrgSignUpResponse> siguUpOrganization = retrofitinterface.orgSignup(profileImg,
                portFolioImage1,
                portFolioImage2,
                portFolioImage3,
                portFolioImage4,
                orgDataModel.getName(),
                orgDataModel.getEmail(),
                orgDataModel.getPassword(),
                orgDataModel.getPhone(),
                orgDataModel.getLocation(),
                orgDataModel.getLatitude(),
                orgDataModel.getLongitude(),
                orgDataModel.getBio(),
                selectedServices,
                orgDataModel.getExpertise_years(),
                orgDataModel.getHourly_rate(),
                orgDataModel.getBusiness_hour_starts(),
                orgDataModel.getBusiness_hour_ends(),
                Constants.DEVICE_TYPE,
                Constants.DEVICE_TOKEN,
                Constants.CONTENT_TYPE);
        siguUpOrganization.enqueue(new Callback<OrgSignUpResponse>() {
            @Override
            public void onResponse(Call<OrgSignUpResponse> call, Response<OrgSignUpResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData() != null) {
                            CommonMethods.setPrefData(PrefrenceConstant.USER_EMAIL, response.body().getData().getUser().getEmail(), PortfolioActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.USER_PHONE, response.body().getData().getUser().getPhone(), PortfolioActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.USER_NAME, response.body().getData().getUser().getName(), PortfolioActivity.this);
                            CommonMethods.setPrefData(PrefrenceConstant.USER_ID, response.body().getData().getUser().getId() + "", PortfolioActivity.this);

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
                        Snackbar.make(binding.portFolioLayout, errorMessage, BaseTransientBottomBar.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Snackbar.make(binding.portFolioLayout, e.getMessage(), BaseTransientBottomBar.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<OrgSignUpResponse> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(binding.portFolioLayout, getResources().getString(R.string.something_went_wrong), BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });
    }

    public void setPortfolioImages() {
        if (position == 1) {

            portFolioImage1 = userImg;
            PortfolioImagesConstants.partOne = portFolioImage1;
            PortfolioImagesConstants.imageOne = setImages;
        }
        if (position == 2) {

            portFolioImage2 = userImg;
            PortfolioImagesConstants.partTwo = portFolioImage2;
            PortfolioImagesConstants.imageTwo = setImages;
        }
        if (position == 3) {

            portFolioImage3 = userImg;
            PortfolioImagesConstants.partThree = portFolioImage3;
            PortfolioImagesConstants.imageThree = setImages;
        }
        if (position == 4) {
            portFolioImage4 = userImg;
            PortfolioImagesConstants.partFour = portFolioImage4;
            PortfolioImagesConstants.imageFour = setImages;
        }
    }

    public void setImagesFromConstant() {
        if (PortfolioImagesConstants.partOne != null) {
            binding.imagePlusone.setVisibility(View.GONE);
            portFolioImage1 = PortfolioImagesConstants.partOne;
            Glide.with(this).load(PortfolioImagesConstants.imageOne).into(binding.addImageOne);
        }
        if (PortfolioImagesConstants.partTwo != null) {
            binding.imgPlusTwo.setVisibility(View.GONE);
            portFolioImage2 = PortfolioImagesConstants.partTwo;
            Glide.with(this).load(PortfolioImagesConstants.imageTwo).into(binding.addImageTwo);
        }
        if (PortfolioImagesConstants.partThree != null) {
            binding.imgPlusThree.setVisibility(View.GONE);
            portFolioImage3 = PortfolioImagesConstants.partThree;
            Glide.with(this).load(PortfolioImagesConstants.imageThree).into(binding.addImageThree);
        }
        if (PortfolioImagesConstants.partFour != null) {
            binding.imgPlusFour.setVisibility(View.GONE);
            portFolioImage4 = PortfolioImagesConstants.partFour;
            Glide.with(this).load(PortfolioImagesConstants.imageFour).into(binding.addImageFour);
        }

    }

    private void storeServiceIds(List<ServiceIdModel> list) {
        Gson gson = new Gson();
        String listData = gson.toJson(list);
        CommonMethods.setPrefData(PrefrenceConstant.SERVICE_IDS, listData, getApplicationContext());
    }
}
