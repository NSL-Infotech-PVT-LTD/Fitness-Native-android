package com.netscape.utrain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
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
import com.netscape.utrain.response.OrgCreateEventResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PrefrenceConstant;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateTrainingSession extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private Retrofitinterface retrofitinterface;
    AppCompatSpinner spinnerLocation;
    MaterialTextView tvStartBsnsHour, tvEndBsnsHour, tvSelectDate;

    int mYear, mMonth, mDay, mHour, mMinute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_training_session);

        retrofitinterface= RetrofitInstance.getClient().create(Retrofitinterface.class);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);




//        tvStartBsnsHour.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Calendar c = Calendar.getInstance();
//                mHour = c.get(Calendar.HOUR_OF_DAY);
//                mMinute = c.get(Calendar.MINUTE);
//
//                TimePickerDialog timePickerDialog = new TimePickerDialog(CreateTrainingSession.this, new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
//
//                        tvStartBsnsHour.setText(hourOfDay + ":" + minute);
//                    }
//                },mHour,mMinute,true);
//                timePickerDialog.show();
//            }
//        });

//        tvEndBsnsHour.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Calendar c = Calendar.getInstance();
//                mHour = c.get(Calendar.HOUR_OF_DAY);
//                mMinute = c.get(Calendar.MINUTE);
//
//                TimePickerDialog timePickerDialog = new TimePickerDialog(CreateTrainingSession.this, new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
//
//                        tvEndBsnsHour.setText(hourOfDay + ":" + minute);
//                    }
//                }, mHour, mMinute,true);
//                timePickerDialog.show();
//            }
//        });

//        tvSelectDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Calendar c = Calendar.getInstance();
//                mYear = c.get(Calendar.YEAR);
//                mMonth = c.get(Calendar.MONTH);
//                mDay = c.get(Calendar.DAY_OF_MONTH);
//
//                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateTrainingSession.this, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
//                        tvSelectDate.setText(dayOfMonth + "-" + (month +1) + "-" + year);
//                    }
//                }, mDay, mMonth, mYear);
//                datePickerDialog.show();
//            }
//        });

//        spinnerLocation = findViewById(R.id.createTrainingSessionLocationSpinner);
//
//        List<String> list = new ArrayList<String>();
//        list.add("Select Location");
//        list.add("Texas");
//        list.add("America");
//        list.add("Australia");
//        list.add("England");
//        list.add("India");
//        list.add("Brazil");
//
//        ArrayAdapter adapter = new ArrayAdapter(CreateTrainingSession.this,android.R.layout.simple_spinner_item,list);
//
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerLocation.setAdapter(adapter);
//
//        spinnerLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                spinnerLocation.setSelection(i);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
    }
    private void hitCreateEventApi() {
        progressDialog.show();
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        requestBodyMap.put("name", RequestBody.create(MediaType.parse("multipart/form-data"), ""));
        requestBodyMap.put("description", RequestBody.create(MediaType.parse("multipart/form-data"), ""));
        requestBodyMap.put("business_hour", RequestBody.create(MediaType.parse("multipart/form-data"), ""));
        requestBodyMap.put("date", RequestBody.create(MediaType.parse("multipart/form-data"), ""));
        requestBodyMap.put("hourly_rate", RequestBody.create(MediaType.parse("multipart/form-data"), ""));
        requestBodyMap.put("phone", RequestBody.create(MediaType.parse("multipart/form-data"), ""));
        requestBodyMap.put("max_occupancy", RequestBody.create(MediaType.parse("multipart/form-data"), ""));
        requestBodyMap.put("Authorization", RequestBody.create(MediaType.parse("multipart/form-data"), CommonMethods.getPrefData(Constants.AUTH_TOKEN,getApplicationContext())));
        requestBodyMap.put("Content-Type", RequestBody.create(MediaType.parse("multipart/form-data"), Constants.CONTENT_TYPE));

        //        requestBodyMap.put("device_token", RequestBody.create(MediaType.parse("multipart/form-data"), Constants.DEVICE_TOKEN));
        Call<OrgCreateEventResponse> signUpAthlete = retrofitinterface.createSession(requestBodyMap, PortfolioImagesConstants.partOne, PortfolioImagesConstants.partTwo, PortfolioImagesConstants.partThree, PortfolioImagesConstants.partFour);
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
//                        Snackbar.make(binding.createEventLayout, response.body().getError().getError_message().getMessage().toString(), BaseTransientBottomBar.LENGTH_SHORT).show();
                    }
                } else {
                    progressDialog.dismiss();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);
//                        Snackbar.make(binding.createEventLayout, errorMessage, BaseTransientBottomBar.LENGTH_SHORT).show();
                    } catch (Exception e) {
//                        Snackbar.make(binding.createEventLayout, e.getMessage(), BaseTransientBottomBar.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<OrgCreateEventResponse> call, Throwable t) {
                progressDialog.dismiss();
//                Snackbar.make(binding.createEventLayout, "" + t, BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });
    }
}
