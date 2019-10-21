package com.netscape.utrain.activities;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;
import com.netscape.utrain.PortfolioImagesConstants;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.organization.OrgMapFindAddressActivity;
import com.netscape.utrain.databinding.ActivityOfferSpaceBinding;
import com.netscape.utrain.response.OrgCreateEventResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PrefrenceConstant;

import org.json.JSONObject;

import java.text.DateFormat;
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
    private ActivityOfferSpaceBinding binding;
    private int OFFER_IMAGE = 333;
    private List<String> startWeekList = new ArrayList<String>();
    private String spaceName = "",eventAddress = "", eventStartTime = "", eventEndtime = "", spaceHourlyPrice = "", spaceWeeklyPrice = "", availStart = "", availEnd = "", spaceDescription = "", availValue = "";

    private int ADDRESS_EVENT = 132;

    private int mYear, mMonth, mDay, mHour, mMinute;

    private String locationLat = "", locationLong = "";

    private String startTime = "";
    private String timeNow = "";
    private String endTime = "";
    private Date stDate = null;
    private Date endDate = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_offer_space);
        init();
        addDataToList();

        ArrayAdapter startWeekadapter = new ArrayAdapter(OfferSpaceActivity.this, android.R.layout.simple_spinner_item, startWeekList);
        startWeekadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.offerSpaceSelectStrtWeek.setAdapter(startWeekadapter);

        binding.offerSpaceSelectStrtWeek.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                binding.offerSpaceSelectStrtWeek.setSelection(i);
                availStart = startWeekList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        ArrayAdapter endWeekAdapter = new ArrayAdapter(OfferSpaceActivity.this, android.R.layout.simple_spinner_item, startWeekList);
        endWeekAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.offerSpaceSelectEndWeek.setAdapter(endWeekAdapter);

        binding.offerSpaceSelectEndWeek.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                binding.offerSpaceSelectEndWeek.setSelection(i);
                availEnd = startWeekList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void addDataToList() {
        startWeekList.add("Start Week");
        startWeekList.add("Sunday");
        startWeekList.add("Monday");
        startWeekList.add("Tuesday");
        startWeekList.add("Wednesday");
        startWeekList.add("Thursday");
        startWeekList.add("Friday");
        startWeekList.add("Saturday");
    }

    private void init() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);
        binding.offerSpaceBtnPost.setOnClickListener(this);
        binding.offerSpaceUploadTv.setOnClickListener(this);
        binding.createEventEndTime.setOnClickListener(this);
        binding.createEvtnStartTimeTv.setOnClickListener(this);
        binding.getAddressTv.setOnClickListener(this);
    }


    private void createSpace() {
        progressDialog.show();
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        requestBodyMap.put("name", RequestBody.create(MediaType.parse("multipart/form-data"), spaceName));
        requestBodyMap.put("description", RequestBody.create(MediaType.parse("multipart/form-data"), spaceDescription));
        requestBodyMap.put("price_hourly", RequestBody.create(MediaType.parse("multipart/form-data"), spaceHourlyPrice));
        requestBodyMap.put("availability_week", RequestBody.create(MediaType.parse("multipart/form-data"), availValue));
        requestBodyMap.put("price_daily", RequestBody.create(MediaType.parse("multipart/form-data"), spaceWeeklyPrice));
        requestBodyMap.put("Content-Type", RequestBody.create(MediaType.parse("text/plain"), Constants.CONTENT_TYPE));


        requestBodyMap.put("start_time", RequestBody.create(MediaType.parse("multipart/form-data"), eventStartTime));
        requestBodyMap.put("end_time", RequestBody.create(MediaType.parse("multipart/form-data"), eventEndtime));
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
                startActivityForResult(getAddress, ADDRESS_EVENT); case R.id.offerSpaceBtnPost:
                getData();
                break;
            case R.id.offerSpaceUploadTv:
                Intent getImages = new Intent(OfferSpaceActivity.this, PortfolioActivity.class);
                getImages.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
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
                startTime = convertDate(hourOfDay) + ":" + convertDate(minute);

                timeNow = convertDate(mHour) + ":" + convertDate(mMinute);


//                int currentTime = LocalTime.parse(startTime);

                binding.createEvtnStartTimeTv.setPadding(20, 0, 70, 0);

                Date time = Calendar.getInstance().getTime();
                if (stDate != null && endDate != null) {
                    if (endDate.compareTo(stDate) == 0) {
                        if (LocalTime.parse(startTime).isAfter(LocalTime.now())) {
                            binding.createEvtnStartTimeTv.setText(startTime);
                            binding.createEventEndTime.setText("");
                            binding.createEventEndTime.setHint("End time");
                        } else {
                            binding.createEvtnStartTimeTv.setText("");
                            binding.createEvtnStartTimeTv.setHint("Start time");
                            Toast.makeText(OfferSpaceActivity.this, "Select a valid time", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        binding.createEvtnStartTimeTv.setText(startTime);
                    }
                } else {
                    Toast.makeText(OfferSpaceActivity.this, "Selecte Date First", Toast.LENGTH_SHORT).show();
                }

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
                endTime = convertDate(hourOfDay) + ":" + convertDate(minute);
                binding.createEventEndTime.setPadding(20, 0, 70, 0);
                if (stDate != null && endDate != null) {
                    if (endDate.compareTo(stDate) == 0) {
                        if (!startTime.isEmpty()) {
                            if (LocalTime.parse(endTime).isAfter(LocalTime.parse(startTime))) {
                                binding.createEventEndTime.setText(endTime);
                            } else {
                                binding.createEventEndTime.setText("");
                                binding.createEventEndTime.setHint("End time");
                                Toast.makeText(OfferSpaceActivity.this, "Selecte valid time", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(OfferSpaceActivity.this, "Selecte Start time", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        binding.createEventEndTime.setText(endTime);

                    }
                } else {
                    Toast.makeText(OfferSpaceActivity.this, "Selecte Date First", Toast.LENGTH_SHORT).show();
                }

            }
        }, mHour, mMinute, true);
        timePickerDialog.show();
    }

    private void getData() {
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
            Toast.makeText(this, getResources().getString(R.string.enter_hourly_rate), Toast.LENGTH_SHORT).show();

        } else if (spaceWeeklyPrice.isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.enter_weekly_price), Toast.LENGTH_SHORT).show();

        } else if (availStart.isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.selecte_availability), Toast.LENGTH_SHORT).show();

        } else if (availEnd.isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.selecte_availability_to), Toast.LENGTH_SHORT).show();

        } else if (spaceDescription.isEmpty()) {
            binding.offerSpaceDescriptionEnterTv.setError(getResources().getString(R.string.enter_space_name));
            binding.offerSpaceDescriptionEnterTv.requestFocus();
        } else {
            availValue = availStart + "-" + availEnd;
            createSpace();
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
                binding.offerSpaceUploadTv.setText(PortfolioImagesConstants.numImages.toString());
            }
        } else {
            Toast.makeText(this, "Unable to get images", Toast.LENGTH_SHORT).show();
        }
    }
}
