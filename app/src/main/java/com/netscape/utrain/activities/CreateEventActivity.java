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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netscape.utrain.PortfolioImagesConstants;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.organization.OrgHomeScreen;
import com.netscape.utrain.activities.organization.OrgMapFindAddressActivity;
import com.netscape.utrain.databinding.ActivityCreateEventBinding;

import com.netscape.utrain.model.ServiceIdModel;
import com.netscape.utrain.model.ServiceListDataModel;
import com.netscape.utrain.response.OrgCreateEventResponse;
import com.netscape.utrain.response.OrgCreateEventResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PrefrenceConstant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
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

import static com.netscape.utrain.activities.PortfolioActivity.clearFromConstants;


public class CreateEventActivity extends AppCompatActivity implements View.OnClickListener {
    AppCompatSpinner spinnerLocation;
    MaterialTextView startBusinessHourTv, endBusinessHourTv, textViewDate, createEventStartDateTv, createEventEndDatetv;
    AppCompatSpinner createEventServiceSpinner;

    TextInputEditText tvEnterCapicity;
    private ProgressDialog progressDialog;
    private ActivityCreateEventBinding binding;
    private int ADDRESS_EVENT = 132;
    private int IMAGE_GET = 166;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private String locationLat = "", locationLong = "";
    private Retrofitinterface retrofitinterface;
    private String eventName = "", eventDescription = "", eventAddress = "", eventStartDate = "", eventEndDate = "", eventStartTime = "", eventEndtime = "", eventEquipments = "", eventCapacity = "";
    private ArrayList<ServiceIdModel> selectedServices = new ArrayList<>();
    private String serviIds = "";
    private String servicePrice = "";
    private String startDate = "";
    private String sDate = "";
    private String eDate = "";
    private String enDate = "";
    private String startTime = "";
    private String timeNow = "";
    private String endTime = "";
    private Date strDate = null;
    private Date stDate = null;
    private Date endDate = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_event);
        binding.ceBackArrowImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getServiceIds();
        createEventServiceSpinner = findViewById(R.id.createEventServiceSpinner);
        ArrayAdapter<ServiceIdModel> adapter = new ArrayAdapter<ServiceIdModel>(CreateEventActivity.this, android.R.layout.simple_spinner_item, selectedServices);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        createEventServiceSpinner.setAdapter(adapter);

        createEventServiceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                createEventServiceSpinner.setSelection(i);
                serviIds = String.valueOf(selectedServices.get(i).getId());
                servicePrice = String.valueOf(selectedServices.get(i).getPrice());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        init();
    }

    private void getServiceIds() {
        String serviceIds = CommonMethods.getPrefData(PrefrenceConstant.SERVICE_IDS, getApplicationContext());
        Gson gson = new Gson();
        if (serviceIds != null) {
            if (serviceIds.isEmpty()) {
                Toast.makeText(this, "Service Not Found", Toast.LENGTH_SHORT).show();
            } else {
                Type type = new TypeToken<List<ServiceIdModel>>() {
                }.getType();
                selectedServices = gson.fromJson(serviceIds, type);
            }
        }
    }

    private void init() {
        binding.getAddressTv.setOnClickListener(this);
        binding.createEventBtn.setOnClickListener(this);
        binding.createEventImages.setOnClickListener(this);

        binding.createEventEndDatetv.setOnClickListener(this);
        binding.createEventStartDateTv.setOnClickListener(this);
        binding.createEventEndTime.setOnClickListener(this);
        binding.createEvtnStartTimeTv.setOnClickListener(this);


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.getAddressTv:
                Intent getAddress = new Intent(CreateEventActivity.this, OrgMapFindAddressActivity.class);
                getAddress.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityForResult(getAddress, ADDRESS_EVENT);
                break;
            case R.id.createEventImages:
                Intent getImages = new Intent(CreateEventActivity.this, PortfolioActivity.class);
                getImages.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                PortfolioActivity.getImages = true;
                startActivityForResult(getImages, IMAGE_GET);
                break;
            case R.id.createEventEndDatetv:
                getEndDate();
                break;
            case R.id.createEventStartDateTv:
                getStartDate();
                break;
            case R.id.createEventEndTime:
                getEndTime();
                break;
            case R.id.createEvtnStartTimeTv:
                getStartTime();
                break;
            case R.id.createEventBtn:
                getDataFromEdtText();
                break;
        }
    }

    private void getStartTime() {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(CreateEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                startTime = convertDate(hourOfDay) + ":" + convertDate(minute);

                timeNow = convertDate(mHour) + ":" + convertDate(mMinute);


//                int currentTime = LocalTime.parse(startTime);

                binding.createEvtnStartTimeTv.setPadding(20, 0, 70, 0);

                Date time = Calendar.getInstance().getTime();
                if (stDate != null && endDate != null) {
//                    if (endDate.compareTo(stDate) == 0) {
//                        if (LocalTime.parse(startTime).isAfter(LocalTime.now())) {
                    binding.createEvtnStartTimeTv.setText(startTime);
                    binding.createEventEndTime.setText("");
                    binding.createEventEndTime.setHint("End time");
//                        } else {
//                            binding.createEvtnStartTimeTv.setText("");
//                            binding.createEvtnStartTimeTv.setHint("Start time");
//                            Toast.makeText(CreateEventActivity.this, "Select a valid time", Toast.LENGTH_SHORT).show();
//                        }
//                    } else {
//                        binding.createEvtnStartTimeTv.setText(startTime);
//                    }
                } else {
                    Toast.makeText(CreateEventActivity.this, "Selecte Date First", Toast.LENGTH_SHORT).show();
                }

            }
        }, mHour, mMinute, true);
        timePickerDialog.show();
    }

    private void getEndTime() {
        Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(CreateEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
                                Toast.makeText(CreateEventActivity.this, "Selecte valid time", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(CreateEventActivity.this, "Selecte Start time", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        binding.createEventEndTime.setText(endTime);

                    }
                } else {
                    Toast.makeText(CreateEventActivity.this, "Selecte Date First", Toast.LENGTH_SHORT).show();
                }

            }
        }, mHour, mMinute, true);
        timePickerDialog.show();
    }

    private void getStartDate() {
        Calendar cal = Calendar.getInstance();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(CreateEventActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                binding.createEventStartDateTv.setPadding(20, 20, 20, 20);
                sDate = year + "-" + convertDate((monthOfYear + 1)) + "-" + convertDate(dayOfMonth);
                startDate = year + "/" + convertDate((monthOfYear + 1)) + "/" + convertDate(dayOfMonth);
                stDate = formatDate(startDate);
                if (stDate.getTime() > System.currentTimeMillis()) {
                    binding.createEventStartDateTv.setText(startDate);
                    binding.createEventEndDatetv.setText("");
                    binding.createEventEndDatetv.setHint("End date");
                } else {
                    binding.createEventStartDateTv.setText("");
                    binding.createEventStartDateTv.setHint("Start date");
                    Toast.makeText(CreateEventActivity.this, "Can't create event for current date", Toast.LENGTH_SHORT).show();
                }

            }
        }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    private void getEndDate() {
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(CreateEventActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                binding.createEventEndDatetv.setPadding(20, 20, 20, 20);
                enDate = year + "-" + convertDate((monthOfYear + 1)) + "-" + convertDate(dayOfMonth);
                eDate = year + "/" + convertDate((monthOfYear + 1)) + "/" + convertDate(dayOfMonth);
                endDate = formatDate(eDate);
                if (stDate != null) {
                    if (endDate.compareTo(stDate) >= 0) {
                        Log.i("app", "Date1 is after Date2");
                        binding.createEventEndDatetv.setText(eDate);
                    } else {
                        Toast.makeText(CreateEventActivity.this, "Select valid date", Toast.LENGTH_SHORT).show();
                        binding.createEventEndDatetv.setText("");
                        binding.createEventEndDatetv.setHint("End date");
                    }
                } else {
                    Toast.makeText(CreateEventActivity.this, "Select start Date", Toast.LENGTH_SHORT).show();
                }
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public Date formatDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date now = new Date(System.currentTimeMillis()); // 2016-03-10 22:06:10
        try {
            strDate = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return strDate;
    }

    private void getDataFromEdtText() {
        eventName = binding.createEventNameEnterTv.getText().toString();
        eventDescription = binding.createEventDescriptionEnterTv.getText().toString();
        eventAddress = binding.getAddressTv.getText().toString();
        eventStartDate = binding.createEventStartDateTv.getText().toString();
        eventEndDate = binding.createEventEndDatetv.getText().toString();
        eventStartTime = binding.createEvtnStartTimeTv.getText().toString();
        eventEndtime = binding.createEventEndTime.getText().toString();
        eventEquipments = binding.createEventEquipmentEdt.getText().toString();
        eventCapacity = binding.createEventCapicityEdt.getText().toString();
        binding.createEventCapicityEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.createEventCapicityEdt.requestFocusFromTouch();
            }
        });

        if (eventName.isEmpty()) {
            binding.createEventNameEnterTv.setError(getResources().getString(R.string.enter_event_name));
            binding.createEventNameEnterTv.requestFocus();
        } else if (eventDescription.isEmpty()) {
            binding.createEventDescriptionEnterTv.setError(getResources().getString(R.string.enter_event_descriptions));
            binding.createEventDescriptionEnterTv.requestFocus();
        } else if (eventAddress.isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.selecte_event_address), Toast.LENGTH_SHORT).show();
        } else if (eventStartDate.isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.select_start_date), Toast.LENGTH_SHORT).show();
        } else if (eventEndDate.isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.select_end_date), Toast.LENGTH_SHORT).show();

        } else if (eventStartTime.isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.select_start_time), Toast.LENGTH_SHORT).show();

        } else if (eventEndtime.isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.select_end_time), Toast.LENGTH_SHORT).show();

        } else if (eventEquipments.isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.enter_equipments_required), Toast.LENGTH_SHORT).show();

        } else if (serviIds.isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.select_services), Toast.LENGTH_SHORT).show();


        } else if (eventCapacity.isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.enter_numbe_of_guest_allowed), Toast.LENGTH_SHORT).show();
        } else if (eventCapacity.equalsIgnoreCase("0") || eventCapacity.equalsIgnoreCase("00")) {
            Toast.makeText(this, getResources().getString(R.string.enter_valid_capacity), Toast.LENGTH_SHORT).show();
        } else {
            hitCreateEventApi();

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
            } else if (requestCode == IMAGE_GET) {
//                if (data != null && data.hasExtra(Constants.ADDRESS)) {
                Toast.makeText(CreateEventActivity.this, "Images Imported", Toast.LENGTH_SHORT).show();
                binding.createEventImages.setText(PortfolioImagesConstants.numImages + " Selected");
//                }
            }
        } else {
            Toast.makeText(CreateEventActivity.this, "Unable to get Address", Toast.LENGTH_SHORT).show();
        }
    }

    private void hitCreateEventApi() {
        progressDialog.show();
        Map<String, RequestBody> requestBodyMap = new HashMap<>();

        requestBodyMap.put("name", RequestBody.create(MediaType.parse("multipart/form-data"), eventName));
        requestBodyMap.put("description", RequestBody.create(MediaType.parse("multipart/form-data"), eventDescription));
        requestBodyMap.put("start_date", RequestBody.create(MediaType.parse("multipart/form-data"), sDate));
        requestBodyMap.put("start_time", RequestBody.create(MediaType.parse("multipart/form-data"), eventStartTime));
        requestBodyMap.put("end_date", RequestBody.create(MediaType.parse("multipart/form-data"), enDate));
        requestBodyMap.put("end_time", RequestBody.create(MediaType.parse("multipart/form-data"), eventEndtime));
        requestBodyMap.put("location", RequestBody.create(MediaType.parse("multipart/form-data"), eventAddress));
        requestBodyMap.put("latitude", RequestBody.create(MediaType.parse("multipart/form-data"), locationLat));
        requestBodyMap.put("longitude", RequestBody.create(MediaType.parse("multipart/form-data"), locationLong));
        requestBodyMap.put("service_id", RequestBody.create(MediaType.parse("multipart/form-data"), serviIds));
        requestBodyMap.put("guest_allowed", RequestBody.create(MediaType.parse("multipart/form-data"), eventCapacity));
        requestBodyMap.put("equipment_required", RequestBody.create(MediaType.parse("multipart/form-data"), eventEquipments));
        requestBodyMap.put("device_type", RequestBody.create(MediaType.parse("multipart/form-data"), Constants.DEVICE_TYPE));
        requestBodyMap.put("device_token", RequestBody.create(MediaType.parse("multipart/form-data"), Constants.DEVICE_TOKEN));
        requestBodyMap.put("price", RequestBody.create(MediaType.parse("multipart/form-data"), servicePrice));
//        requestBodyMap.put("Authorization", RequestBody.create(MediaType.parse("text/plain"),));
        requestBodyMap.put("Content-Type", RequestBody.create(MediaType.parse("text/plain"), Constants.CONTENT_TYPE));

        List<MultipartBody.Part> parts = new ArrayList<>();
        parts.add(PortfolioImagesConstants.partOne);
        parts.add(PortfolioImagesConstants.partTwo);
        parts.add(PortfolioImagesConstants.partThree);
        parts.add(PortfolioImagesConstants.partFour);

        //        requestBodyMap.put("device_token", RequestBody.create(MediaType.parse("multipart/form-data"), Constants.DEVICE_TOKEN));
        Call<OrgCreateEventResponse> signUpAthlete = retrofitinterface.createEvent("Bearer" + " " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getApplicationContext()), requestBodyMap, parts);
        signUpAthlete.enqueue(new Callback<OrgCreateEventResponse>() {
            @Override
            public void onResponse(Call<OrgCreateEventResponse> call, Response<OrgCreateEventResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData() != null) {
                            PortfolioActivity.clearFromConstants();
                            Constants.CHECKBOX_IS_CHECKED = 0;
                            Toast.makeText(CreateEventActivity.this, "" + response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    } else {
                        Snackbar.make(binding.createEventLayout, response.body().getError().getError_message().getMessage().toString(), BaseTransientBottomBar.LENGTH_SHORT).show();
                    }
                } else {
                    progressDialog.dismiss();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);
                        Snackbar.make(binding.createEventLayout, errorMessage, BaseTransientBottomBar.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Snackbar.make(binding.createEventLayout, e.getMessage(), BaseTransientBottomBar.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<OrgCreateEventResponse> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(binding.createEventLayout, "" + t, BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });
    }

    public String convertDate(int input) {
        if (input >= 10) {
            return String.valueOf(input);
        } else {
            return "0" + String.valueOf(input);
        }
    }
}
