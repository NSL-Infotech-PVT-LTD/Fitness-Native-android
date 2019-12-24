package com.netscape.utrain.activities;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;

import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.JsonArray;
import com.netscape.utrain.PortfolioImagesConstants;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.organization.OrgMapFindAddressActivity;
import com.netscape.utrain.databinding.ActivityOfferSpaceBinding;
import com.netscape.utrain.model.O_SessionDataModel;
import com.netscape.utrain.model.O_SpaceDataModel;
import com.netscape.utrain.model.SelectSpaceDaysModel;
import com.netscape.utrain.model.SlotModel;
import com.netscape.utrain.response.OrgCreateEventResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PrefrenceConstant;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OfferSpaceActivity extends AppCompatActivity implements View.OnClickListener {
    private Retrofitinterface retrofitinterface;
    private ProgressDialog progressDialog;
    public static boolean spaceEdit=false;
    private ActivityOfferSpaceBinding binding;
    private int OFFER_IMAGE = 333;
    private List<SelectSpaceDaysModel> startWeekList = new ArrayList<>();
    private String spaceName = "",eventAddress = "", eventStartTime = "", eventEndtime = "", spaceHourlyPrice = "", spaceWeeklyPrice = "", availStart = "", availEnd = "", spaceDescription = "", availValue = "";

    private int ADDRESS_EVENT = 132;

    private int mYear, mMonth, mDay, mHour, mMinute;

    private String locationLat = "", locationLong = "";

    private String startTime = "";
    private String timeNow = "";
    private String endTime = "";
    private Date stDate = null;
    private Date endDate = null;
    private O_SpaceDataModel data;
    private String startWeek;
    private String endWeek;
    ChipGroup chipSpaceGroup;
    private boolean selected=false;
    private Chip chipSelected;
    SelectSpaceDaysModel selectSpaceDaysModel;
    private ArrayList<SelectSpaceDaysModel> selectedDays=new ArrayList<>();
    private JsonArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_offer_space);

        addDataToList();
        init();

//        ArrayAdapter startWeekadapter = new ArrayAdapter(OfferSpaceActivity.this, android.R.layout.simple_spinner_item, startWeekList);
//        startWeekadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        binding.offerSpaceSelectStrtWeek.setAdapter(startWeekadapter);

        binding.osBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

//        binding.offerSpaceSelectStrtWeek.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if (i==0){
//                    availStart="";
//                }else {
//                    binding.offerSpaceSelectStrtWeek.setSelection(i);
//                    availStart = i+"";
//                }
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//        chipSpaceGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(ChipGroup group, int checkedId) {
//                if (selected){
//                    if (chipSelected !=null) {
//                        chipSelected.setChipBackgroundColor(ColorStateList.valueOf(
//                                ContextCompat.getColor(OfferSpaceActivity.this, R.color.lightGrayFont)));
////                        timeSlotSelected="";
//                    }
//                }
//                chipSelected = chipSpaceGroup.findViewById(checkedId);
//                if (chipSelected !=null){
//                    chipSelected.setChipBackgroundColor( ColorStateList.valueOf(
//                            ContextCompat.getColor(OfferSpaceActivity.this, R.color.colorAccent)));
//                    selected=true;
////                    timeSlotSelected=chipSelected.getText().toString();
//                    Toast.makeText(OfferSpaceActivity.this, ""+chipSelected.getText().toString(), Toast.LENGTH_SHORT).show();
//                }else{
//                    selected=false;
//                    Toast.makeText(OfferSpaceActivity.this, "Chip Un Selected", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });



//        ArrayAdapter endWeekAdapter = new ArrayAdapter(OfferSpaceActivity.this, android.R.layout.simple_spinner_item, startWeekList);
//        endWeekAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        binding.offerSpaceSelectEndWeek.setAdapter(endWeekAdapter);
//
//        binding.offerSpaceSelectEndWeek.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if (i==0){
//                    availEnd="";
//                }else {
//                    binding.offerSpaceSelectEndWeek.setSelection(i);
//                    availEnd = String.valueOf(i);
//                }
//                }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

        if (spaceEdit){
            if (getIntent().getExtras()!=null){
                data= (O_SpaceDataModel) getIntent().getSerializableExtra("spaceEdit");
                if (data !=null){
                    prepareDataSet();
                }
            }
        }

    }

    private void prepareDataSet() {
        binding.createTrainingSessionTv.setText("Update Space");
        binding.offerSpaceNameEdt.setText(data.getName());
        binding.getAddressTv.setText(data.getLocation());
        binding.createEvtnStartTimeTv.setText(data.getOpen_hours_from());
        binding.createEventEndTime.setText(data.getOpen_hours_to());
        binding.offerSpaceHourlyPrice.setText(data.getPrice_hourly()+"");
        binding.offerSpaceWeeklyPrice.setText(data.getPrice_daily()+"");

//        binding.offerSpaceSelectStrtWeek.setText(data.getName());
//        binding.offerSpaceSelectEndWeek.setText(data.getName());
        binding.offerSpaceDescriptionEnterTv.setText(data.getDescription());
        binding.offerSpaceBtnPost.setText("Update");

        locationLat=data.getLatitude()+"";
        locationLong=data.getLongitude()+"";
        for (int i=0;i<data.getAvailability_week().size();i++){
           for (int j=0;j<startWeekList.size();j++){
               if (data.getAvailability_week().get(i).toString().equalsIgnoreCase(startWeekList.get(j).getDaySeleced().toString())){
                   startWeekList.get(i).setChecked(true);
                   break;
               }
           }
        }
        setChips();


//        String currentString = data.getAvailability_week();
//        String[] separated = currentString.split("-");
//        if (separated.length>=2) {
//             startWeek = separated[0];
//             endWeek = separated[1];
//        }
//        if (! startWeek.isEmpty()) {
//            for (int i = 0; i < startWeekList.size(); i++) {
//                if (startWeek.equalsIgnoreCase(startWeekList.get(i).toString())){
////                    binding.offerSpaceSelectStrtWeek.setSelection(i);
//                    availStart = i+"";
//                }
//            }
//        }
//        if (! endWeek.isEmpty()) {
//            for (int i = 0; i < startWeekList.size(); i++) {
//                if (endWeek.equalsIgnoreCase(startWeekList.get(i).toString())){
////                    binding.offerSpaceSelectEndWeek.setSelection(i);
//                    availEnd = i+"";
//                }
//            }
//        }
    }

    private void addDataToList() {
        startWeekList=CommonMethods.getWeekDaysList();
//        selectSpaceDaysModel=new SelectSpaceDaysModel();
//        selectSpaceDaysModel.setChecked(false);
//        selectSpaceDaysModel.setDaySeleced("1");
//        selectSpaceDaysModel.setDayName("Monday");
//        startWeekList.add(selectSpaceDaysModel);
//        selectSpaceDaysModel=new SelectSpaceDaysModel();
//        selectSpaceDaysModel.setChecked(false);
//        selectSpaceDaysModel.setDaySeleced("2");
//        selectSpaceDaysModel.setDayName("Tuesday");
//        startWeekList.add(selectSpaceDaysModel);
//        selectSpaceDaysModel=new SelectSpaceDaysModel();
//        selectSpaceDaysModel.setChecked(false);
//        selectSpaceDaysModel.setDaySeleced("3");
//        selectSpaceDaysModel.setDayName("Wednesday");
//        startWeekList.add(selectSpaceDaysModel);
//        selectSpaceDaysModel=new SelectSpaceDaysModel();
//        selectSpaceDaysModel.setChecked(false);
//        selectSpaceDaysModel.setDaySeleced("4");
//        selectSpaceDaysModel.setDayName("Thursday");
//        startWeekList.add(selectSpaceDaysModel);
//        selectSpaceDaysModel=new SelectSpaceDaysModel();
//        selectSpaceDaysModel.setChecked(false);
//        selectSpaceDaysModel.setDaySeleced("5");
//        selectSpaceDaysModel.setDayName("Friday");
//        startWeekList.add(selectSpaceDaysModel);
//        selectSpaceDaysModel=new SelectSpaceDaysModel();
//        selectSpaceDaysModel.setChecked(false);
//        selectSpaceDaysModel.setDaySeleced("6");
//        selectSpaceDaysModel.setDayName("Saturday");
//        startWeekList.add(selectSpaceDaysModel);
//        selectSpaceDaysModel=new SelectSpaceDaysModel();
//        selectSpaceDaysModel.setChecked(false);
//        selectSpaceDaysModel.setDaySeleced("7");
//        selectSpaceDaysModel.setDayName("Sunday");
//        startWeekList.add(selectSpaceDaysModel);

    }

    private void init() {
//        jsonArray=new JsonArray();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);
        binding.offerSpaceBtnPost.setOnClickListener(this);
        binding.offerSpaceUploadTv.setOnClickListener(this);
        binding.createEventEndTime.setOnClickListener(this);
        binding.createEvtnStartTimeTv.setOnClickListener(this);
        binding.getAddressTv.setOnClickListener(this);

        setChips();
    }

    private void setChips() {
        binding.spaceDaysLayout.removeAllViews();
        chipSpaceGroup = new ChipGroup(this);
        chipSpaceGroup.setSingleSelection(false);
        for (SelectSpaceDaysModel selectDays : startWeekList) {
            Chip chip = new Chip(this);
            chip.setEnabled(true);
            ChipDrawable chipDrawable = ChipDrawable.createFromAttributes(this, null, 0, R.style.Widget_MaterialComponents_Chip_Filter);
            chip.setChipDrawable(chipDrawable);
            chip.setTextColor(getResources().getColor(R.color.colorWhite));
//            chip.setMaxWidth(200);
            chip.setText(selectDays.getDayName());
            chip.setTag(selectDays.getDaySeleced());
            chip.setChipBackgroundColorResource(R.color.lightGrayFont);
            if (selectDays.isChecked()){
                chip.setChipBackgroundColorResource(R.color.colorGreen);
                chip.setChecked(true);
            }
            chip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String id=view.getId()+"";

                    selectDays.setChecked(!selectDays.isChecked());
                                if (selectDays.isChecked()) {
                                    chip.setChipBackgroundColorResource(R.color.colorGreen);
                                } else {
                                    chip.setChipBackgroundColorResource(R.color.lightGrayFont);
                                }

                }
            });
            chipSpaceGroup.addView(chip);
        }
        chipSpaceGroup.setEnabled(true);
        chipSpaceGroup.setChipSpacingVertical(20);
        binding.spaceDaysLayout.addView(chipSpaceGroup);
    }


    private void createSpace() {
        progressDialog.show();
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        requestBodyMap.put("name", RequestBody.create(MediaType.parse("multipart/form-data"), spaceName));
        requestBodyMap.put("description", RequestBody.create(MediaType.parse("multipart/form-data"), spaceDescription));
        requestBodyMap.put("price_hourly", RequestBody.create(MediaType.parse("multipart/form-data"), spaceHourlyPrice));
        requestBodyMap.put("availability_week", RequestBody.create(MediaType.parse("multipart/form-data"), jsonArray.toString()));
        requestBodyMap.put("price_daily", RequestBody.create(MediaType.parse("multipart/form-data"), spaceWeeklyPrice));
        requestBodyMap.put("Content-Type", RequestBody.create(MediaType.parse("text/plain"), Constants.CONTENT_TYPE));


        requestBodyMap.put("open_hours_from", RequestBody.create(MediaType.parse("multipart/form-data"), eventStartTime));
        requestBodyMap.put("open_hours_to", RequestBody.create(MediaType.parse("multipart/form-data"), eventEndtime));
        requestBodyMap.put("location", RequestBody.create(MediaType.parse("multipart/form-data"), eventAddress));
        requestBodyMap.put("latitude", RequestBody.create(MediaType.parse("multipart/form-data"), locationLat));
        requestBodyMap.put("longitude", RequestBody.create(MediaType.parse("multipart/form-data"), locationLong));

        List<MultipartBody.Part> parts = new ArrayList<>();
        parts.add(PortfolioImagesConstants.partOne);
        parts.add(PortfolioImagesConstants.partTwo);
        parts.add(PortfolioImagesConstants.partThree);
        parts.add(PortfolioImagesConstants.partFour);

        //        requestBodyMap.put("device_token", RequestBody.create(MediaType.parse("multipart/form-data"), Constants.DEVICE_TOKEN));
        Call<OrgCreateEventResponse> signUpAthlete = retrofitinterface.createSpace("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getApplicationContext()),requestBodyMap, parts) ;
        signUpAthlete.enqueue(new Callback<OrgCreateEventResponse>() {
            @Override
            public void onResponse(Call<OrgCreateEventResponse> call, Response<OrgCreateEventResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData() != null) {
                            PortfolioActivity.clearFromConstants();
                            Constants.CHECKBOX_IS_CHECKED = 0;
                            Toast.makeText(OfferSpaceActivity.this, "" + response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    } else {
                        Snackbar.make(binding.offerSpaceLayout, response.body().getError().getError_message().getMessage().toString(), BaseTransientBottomBar.LENGTH_SHORT).show();
                    }
                } else {
                    progressDialog.dismiss();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);
                        Snackbar.make(binding.offerSpaceLayout, errorMessage, BaseTransientBottomBar.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Snackbar.make(binding.offerSpaceLayout, e.getMessage(), BaseTransientBottomBar.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<OrgCreateEventResponse> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(binding.offerSpaceLayout, "" + t, BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.getAddressTv:
                Intent getAddress = new Intent(OfferSpaceActivity.this, OrgMapFindAddressActivity.class);
                getAddress.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityForResult(getAddress, ADDRESS_EVENT);
            case R.id.offerSpaceBtnPost:
                getData();
                break;
            case R.id.offerSpaceUploadTv:
                if (spaceEdit){
                    Intent getImages = new Intent(OfferSpaceActivity.this, PortfolioActivity.class);
                    getImages.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    PortfolioActivity.updateImages = true;
                    getImages.putExtra("updateEventImg",data.getImages());
                    getImages.putExtra("updateImgType","spaceImgUpdate");
                    startActivityForResult(getImages, OFFER_IMAGE);
                }
                Intent getImages = new Intent(OfferSpaceActivity.this, PortfolioActivity.class);
                getImages.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                PortfolioActivity.getImages = true;
                startActivityForResult(getImages, OFFER_IMAGE);
                break;
            case R.id.createEventEndTime:
                getEndTime();
                break;
            case R.id.createEvtnStartTimeTv:
                getStartTime();
                break;
        }
    }
    private void getStartTime() {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(OfferSpaceActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                startTime = convertDate(hourOfDay) + ":" + "00";
                binding.createEvtnStartTimeTv.setText(startTime);
                binding.createEvtnStartTimeTv.setPadding(20, 0, 70, 0);
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
    private void getEndTime() {
        Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(OfferSpaceActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                endTime = convertDate(hourOfDay) + ":" + "00";
                binding.createEventEndTime.setPadding(20, 0, 70, 0);
                if (formatTime(endTime).after(formatTime(startTime))) {
                    binding.createEventEndTime.setText(endTime);
                }else {
                    binding.createEventEndTime.setText("");
                    binding.createEventEndTime.setText("End time");
                    Toast.makeText(OfferSpaceActivity.this, "Select valid time", Toast.LENGTH_SHORT).show();
                }

            }
        }, mHour, mMinute, true);
        timePickerDialog.show();
    }
    private Date formatTime(String time){
        Date formated=null;
        SimpleDateFormat timeFormat=new SimpleDateFormat("HH:mm");
        try {
            formated=timeFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formated;
    }

    private void getData() {
        jsonArray=new JsonArray();
        for (int i=0;i<startWeekList.size();i++){
            if (startWeekList.get(i).isChecked()){
                jsonArray.add(startWeekList.get(i).getDaySeleced());
            }
        }
        spaceName = binding.offerSpaceNameEdt.getText().toString();
        spaceHourlyPrice = binding.offerSpaceHourlyPrice.getText().toString();
        spaceWeeklyPrice = binding.offerSpaceWeeklyPrice.getText().toString();
        spaceDescription = binding.offerSpaceDescriptionEnterTv.getText().toString();
        eventStartTime = binding.createEvtnStartTimeTv.getText().toString();
        eventEndtime = binding.createEventEndTime.getText().toString();
        eventAddress = binding.getAddressTv.getText().toString();
        if (spaceName.isEmpty()) {
            binding.offerSpaceNameEdt.setError(getResources().getString(R.string.enter_space_name));
            binding.offerSpaceNameEdt.requestFocus();

        } else if (spaceHourlyPrice.isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.enter_price), Toast.LENGTH_SHORT).show();
        } else if (! (Integer.parseInt(spaceHourlyPrice)>0)) {
            Toast.makeText(this, getResources().getString(R.string.enter_valid_weekly_price), Toast.LENGTH_SHORT).show();
        } else if (spaceWeeklyPrice.isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.enter_weekly_price), Toast.LENGTH_SHORT).show();
        } else if (! (Integer.parseInt(spaceWeeklyPrice)>0)) {
            Toast.makeText(this, getResources().getString(R.string.enter_valid_weekly_price), Toast.LENGTH_SHORT).show();
//        } else if (availStart.isEmpty()) {
//            Toast.makeText(this, getResources().getString(R.string.selecte_week_day_start), Toast.LENGTH_SHORT).show();

//        } else if (availEnd.isEmpty()) {
//            Toast.makeText(this, getResources().getString(R.string.select_week_day_to), Toast.LENGTH_SHORT).show();

        } else if (spaceDescription.isEmpty()) {
            binding.offerSpaceDescriptionEnterTv.setError(getResources().getString(R.string.enter_description));
            binding.offerSpaceDescriptionEnterTv.requestFocus();
        } else if (jsonArray !=null && jsonArray.size()<=0) {
            Toast.makeText(this, ""+getResources().getString(R.string.selecte_availability), Toast.LENGTH_SHORT).show();
        } else {
            if (spaceEdit){
//                availValue=data.getAvailability_week();
            updateSpace();
            }else {
                availValue = availStart + "-" + availEnd;
                createSpace();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == ADDRESS_EVENT) {
                if (data != null && data.hasExtra(Constants.ADDRESS)) {
                    binding.getAddressTv.setText(data.getStringExtra(Constants.ADDRESS));
                    binding.getAddressTv.setError(null);
                    locationLat = data.getStringExtra(Constants.LOCATION_LAT);
                    locationLong = data.getStringExtra(Constants.LOCATION_LONG);
                }
            }

            else if (requestCode == OFFER_IMAGE && data != null) {
//                binding.offerSpaceUploadTv.setText(PortfolioImagesConstants.numImages.toString());
            }
        } else {
            Toast.makeText(this, "Unable to get images", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        spaceEdit=false;
        super.onDestroy();
    }

    private void updateSpace() {
        progressDialog.show();
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        requestBodyMap.put("name", RequestBody.create(MediaType.parse("multipart/form-data"), spaceName));
        requestBodyMap.put("description", RequestBody.create(MediaType.parse("multipart/form-data"), spaceDescription));
        requestBodyMap.put("price_hourly", RequestBody.create(MediaType.parse("multipart/form-data"), spaceHourlyPrice));
        requestBodyMap.put("availability_week", RequestBody.create(MediaType.parse("multipart/form-data"),jsonArray.toString()));
        requestBodyMap.put("price_daily", RequestBody.create(MediaType.parse("multipart/form-data"), spaceWeeklyPrice));
        requestBodyMap.put("Content-Type", RequestBody.create(MediaType.parse("text/plain"), Constants.CONTENT_TYPE));


        requestBodyMap.put("open_hours_from", RequestBody.create(MediaType.parse("multipart/form-data"), eventStartTime));
        requestBodyMap.put("open_hours_to", RequestBody.create(MediaType.parse("multipart/form-data"), eventEndtime));
        requestBodyMap.put("location", RequestBody.create(MediaType.parse("multipart/form-data"), eventAddress));
        requestBodyMap.put("latitude", RequestBody.create(MediaType.parse("multipart/form-data"), locationLat));
        requestBodyMap.put("longitude", RequestBody.create(MediaType.parse("multipart/form-data"), locationLong));
        requestBodyMap.put("id", RequestBody.create(MediaType.parse("multipart/form-data"), data.getId()+""));

        List<MultipartBody.Part> parts = new ArrayList<>();
        if (PortfolioImagesConstants.partOne !=null){
            parts.add(PortfolioImagesConstants.partOne);
        }
        if (PortfolioImagesConstants.partTwo !=null){
        parts.add(PortfolioImagesConstants.partTwo);
    }
        if (PortfolioImagesConstants.partThree !=null){
        parts.add(PortfolioImagesConstants.partThree);
    }
        if (PortfolioImagesConstants.partFour !=null){
        parts.add(PortfolioImagesConstants.partFour);
    }

        //        requestBodyMap.put("device_token", RequestBody.create(MediaType.parse("multipart/form-data"), Constants.DEVICE_TOKEN));
        Call<OrgCreateEventResponse> signUpAthlete = retrofitinterface.updateSpace("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getApplicationContext()),requestBodyMap, parts) ;
        signUpAthlete.enqueue(new Callback<OrgCreateEventResponse>() {
            @Override
            public void onResponse(Call<OrgCreateEventResponse> call, Response<OrgCreateEventResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData() != null) {
                            PortfolioActivity.clearFromConstants();
                            Constants.CHECKBOX_IS_CHECKED = 0;
                            Toast.makeText(OfferSpaceActivity.this, "" + response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    } else {
                        Snackbar.make(binding.offerSpaceLayout, response.body().getError().getError_message().getMessage().toString(), BaseTransientBottomBar.LENGTH_SHORT).show();
                    }
                } else {
                    progressDialog.dismiss();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);
                        Snackbar.make(binding.offerSpaceLayout, errorMessage, BaseTransientBottomBar.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Snackbar.make(binding.offerSpaceLayout, e.getMessage(), BaseTransientBottomBar.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<OrgCreateEventResponse> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(binding.offerSpaceLayout, "" + t, BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });
    }



}
