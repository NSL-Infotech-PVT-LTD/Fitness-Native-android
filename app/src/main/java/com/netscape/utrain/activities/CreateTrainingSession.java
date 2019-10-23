package com.netscape.utrain.activities;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.databinding.DataBindingUtil;

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
import com.netscape.utrain.databinding.ActivityCreateTrainingSessionBinding;
import com.netscape.utrain.response.OrgCreateEventResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PrefrenceConstant;

import org.json.JSONObject;

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

public class CreateTrainingSession extends AppCompatActivity implements View.OnClickListener {
    AppCompatSpinner spinnerLocation;
    MaterialTextView createTrainingDateTv;
    int mYear, mMonth, mDay, mHour, mMinute;
    DatePickerDialog datePickerDialog = null;
    private ActivityCreateTrainingSessionBinding binding;
    private ProgressDialog progressDialog;
    private Retrofitinterface retrofitinterface;
    private String sessionName = "", sessionDescription = "", sessionPhone = "", sessionStartDate = "", sessionStartTime = "", sessionHourlyRate = "", sessionMaxOccupancy = "", businessHour = "";
    private int SESSIOM_IMAGE = 129;
    private String dateNow = "";
    private String dateSend = "";
    private String startTime = "";
    private Date strDate = null;
    private Date now = null;
    private int ADDRESS_EVENT = 132;
    String eventAddress = "";
    private String locationLat = "", locationLong = "";

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_training_session);

        binding.ctsBackArrowImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        init();

    }

    private void init() {
        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        binding.getAddressTv.setOnClickListener(this);
        progressDialog.setCancelable(false);
        binding.createTrainingDateTv.setOnClickListener(this);
        binding.createTrainingTimeTv.setOnClickListener(this);
        binding.createTrainingSessionUploadTv.setOnClickListener(this);
        binding.createSessionBtn.setOnClickListener(this);
    }

    private void hitCreateSessionApi() {
        progressDialog.show();
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        requestBodyMap.put("name", RequestBody.create(MediaType.parse("multipart/form-data"), sessionName));
        requestBodyMap.put("description", RequestBody.create(MediaType.parse("multipart/form-data"), sessionDescription));
        requestBodyMap.put("business_hour", RequestBody.create(MediaType.parse("multipart/form-data"),(dateSend+" "+sessionStartTime)));
        requestBodyMap.put("date", RequestBody.create(MediaType.parse("multipart/form-data"), dateSend));
        requestBodyMap.put("hourly_rate", RequestBody.create(MediaType.parse("multipart/form-data"), sessionHourlyRate));
        requestBodyMap.put("phone", RequestBody.create(MediaType.parse("multipart/form-data"), sessionPhone));
        requestBodyMap.put("guest_allowed", RequestBody.create(MediaType.parse("multipart/form-data"), sessionMaxOccupancy));

        requestBodyMap.put("location", RequestBody.create(MediaType.parse("multipart/form-data"), eventAddress));
        requestBodyMap.put("latitude", RequestBody.create(MediaType.parse("multipart/form-data"), locationLat));
        requestBodyMap.put("longitude", RequestBody.create(MediaType.parse("multipart/form-data"), locationLong));   requestBodyMap.put("max_occupancy", RequestBody.create(MediaType.parse("multipart/form-data"), sessionMaxOccupancy));
        requestBodyMap.put("Content-Type", RequestBody.create(MediaType.parse("multipart/form-data"), Constants.CONTENT_TYPE));
        List<MultipartBody.Part> parts = new ArrayList<>();
        parts.add(PortfolioImagesConstants.partOne);
        parts.add(PortfolioImagesConstants.partTwo);
        parts.add(PortfolioImagesConstants.partThree);
        parts.add(PortfolioImagesConstants.partFour);
        //        requestBodyMap.put("device_token", RequestBody.create(MediaType.parse("multipart/form-data"), Constants.DEVICE_TOKEN));
        Call<OrgCreateEventResponse> signUpAthlete = retrofitinterface.createSession("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getApplicationContext()), requestBodyMap, parts );
        signUpAthlete.enqueue(new Callback<OrgCreateEventResponse>() {
            @Override
            public void onResponse(Call<OrgCreateEventResponse> call, Response<OrgCreateEventResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData() != null) {
                            PortfolioActivity.clearFromConstants();
                            Constants.CHECKBOX_IS_CHECKED = 0;
                            Toast.makeText(CreateTrainingSession.this, "" + response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    } else {
                        Snackbar.make(binding.createTrainingSessionLayout, response.body().getError().getError_message().getMessage().toString(), BaseTransientBottomBar.LENGTH_SHORT).show();
                    }
                } else {
                    progressDialog.dismiss();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);
                        Snackbar.make(binding.createTrainingSessionLayout, errorMessage, BaseTransientBottomBar.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Snackbar.make(binding.createTrainingSessionLayout, e.getMessage(), BaseTransientBottomBar.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<OrgCreateEventResponse> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(binding.createTrainingSessionLayout, "" + t, BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.getAddressTv:
                Intent getAddress = new Intent(CreateTrainingSession.this, OrgMapFindAddressActivity.class);
                getAddress.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityForResult(getAddress, ADDRESS_EVENT);
                break; case R.id.createTrainingDateTv:
                getTrainingDate();
                break;
            case R.id.createTrainingTimeTv:
                getTrainingTime();
                break;
            case R.id.createTrainingSessionUploadTv:
                Intent getImages = new Intent(CreateTrainingSession.this, PortfolioActivity.class);
                getImages.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                PortfolioActivity.getImages = true;
                startActivityForResult(getImages, SESSIOM_IMAGE);
                break;
            case R.id.createSessionBtn:
                getDataFromEdt();
                break;
        }
    }

    private void getTrainingTime() {
        Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(CreateTrainingSession.this, new TimePickerDialog.OnTimeSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                startTime=convertDate(hourOfDay) + ":" +convertDate( minute);
                if (strDate!=null && now !=null) {
                    if (strDate.compareTo(now) == 0) {
                        if (LocalTime.parse(startTime).isAfter(LocalTime.now())) {
                            binding.createTrainingTimeTv.setText(startTime);
                        } else {
                            binding.createTrainingTimeTv.setText("");
                            binding.createTrainingTimeTv.setHint("Start time");
                            Toast.makeText(CreateTrainingSession.this, "Select a valid time", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        binding.createTrainingTimeTv.setText(startTime);
                    }
                }else {
                    Toast.makeText(CreateTrainingSession.this, "Selecte Date First", Toast.LENGTH_SHORT).show();
                }
            }
        }, mHour, mMinute, true);
        timePickerDialog.show();
    }

    private void getTrainingDate() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(CreateTrainingSession.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                binding.createTrainingDateTv.setPadding(20, 20, 20, 20);
                dateSend = year + "-" + convertDate((monthOfYear + 1)) + "-" + convertDate(dayOfMonth);
                dateNow = convertDate(dayOfMonth) + "/" + convertDate((monthOfYear + 1)) + "/" +year ;
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String currentDateandTime = sdf.format(new Date());
                try {
                    now=sdf.parse(currentDateandTime);
                    strDate = sdf.parse(dateNow);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (strDate.compareTo(now) >= 0) {
                    binding.createTrainingDateTv.setText(dateNow);
                } else {
                    Toast.makeText(CreateTrainingSession.this, "Please select valid date", Toast.LENGTH_SHORT).show();
                }
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    private void getDataFromEdt() {
        sessionName = binding.createTrainingSessionNameEdt.getText().toString();
        sessionDescription = binding.createTrainingSessionDescEdt.getText().toString();
        sessionPhone = binding.createTrainingSessionPhoneEdt.getText().toString();
        sessionStartDate = binding.createTrainingDateTv.getText().toString();
        sessionStartTime = binding.createTrainingTimeTv.getText().toString();
        sessionHourlyRate = binding.createTrainingSessionHourRateEdt.getText().toString();
        sessionMaxOccupancy = binding.createTrainingSessionMaxOccuEdt.getText().toString();
        eventAddress = binding.getAddressTv.getText().toString();
        if (sessionName.isEmpty()) {
            binding.createTrainingSessionNameEdt.setError(getResources().getString(R.string.enter_session_name));
            binding.createTrainingSessionNameEdt.requestFocus();

        } else if (sessionDescription.isEmpty()) {
            binding.createTrainingSessionDescEdt.setError(getResources().getString(R.string.enter_session_description));
            binding.createTrainingSessionDescEdt.requestFocus();

        } else if (sessionPhone.isEmpty()) {
            binding.createTrainingSessionPhoneEdt.setError(getResources().getString(R.string.enter_phone_number));
            binding.createTrainingSessionPhoneEdt.requestFocus();
        } else if (eventAddress.isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.selecte_event_address), Toast.LENGTH_SHORT).show();
        } else if (sessionStartDate.isEmpty()) {
            Toast.makeText(this, "Enter session start date", Toast.LENGTH_SHORT).show();

        } else if (sessionStartTime.isEmpty()) {
            Toast.makeText(this, "Enter session end date", Toast.LENGTH_SHORT).show();

        } else if (sessionHourlyRate.isEmpty()) {
            binding.createTrainingSessionHourRateEdt.setError(getResources().getString(R.string.enter_hourly_rate_session));
            binding.createTrainingSessionHourRateEdt.requestFocus();
        } else if (sessionMaxOccupancy.isEmpty()) {
            binding.createTrainingSessionMaxOccuEdt.setError(getResources().getString(R.string.enter_session_max_occupacy));
            binding.createTrainingSessionMaxOccuEdt.requestFocus();
        } else {
            hitCreateSessionApi();
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
              else if (requestCode == SESSIOM_IMAGE) {
                if (data != null && data.hasExtra(Constants.ADDRESS)) {
                    Toast.makeText(CreateTrainingSession.this, "Images Imported", Toast.LENGTH_SHORT).show();
                    binding.createTrainingSessionUploadTv.setText(PortfolioImagesConstants.numImages + " Images selected");
                }
            }
        } else {
            Toast.makeText(this, "Unable to import Images", Toast.LENGTH_SHORT).show();
        }
    }
    public String convertDate(int input) {
        if (input >= 10) {
            return String.valueOf(input);
        } else {
            return "0" + String.valueOf(input);
        }
    }
}
