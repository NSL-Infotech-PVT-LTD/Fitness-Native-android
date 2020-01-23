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
import com.google.android.material.textview.MaterialTextView;
import com.netscape.utrain.PortfolioImagesConstants;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.organization.OrgMapFindAddressActivity;
import com.netscape.utrain.databinding.ActivityCreateTrainingSessionBinding;
import com.netscape.utrain.model.O_EventDataModel;
import com.netscape.utrain.model.O_SessionDataModel;
import com.netscape.utrain.model.SportListModel;
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
    public static boolean editSession = false;
    AppCompatSpinner spinnerLocation;
    MaterialTextView createTrainingDateTv;
    int mYear, mMonth, mDay, mHour, mMinute;
    DatePickerDialog datePickerDialog = null;
    String eventAddress = "";
    AppCompatSpinner createEventServiceSpinner;
    private ActivityCreateTrainingSessionBinding binding;
    private ProgressDialog progressDialog;
    private Retrofitinterface retrofitinterface;
    private String sessionName = "", sessionDescription = "", sessionPhone = "", sessionStartDate = "", eventStartTime = "", eventEndtime = "", sessionEndDate = "", sessionHourlyRate = "", sessionMaxOccupancy = "", businessHour = "";
    private int SESSIOM_IMAGE = 129;
    private SportListModel.DataBeanX.DataBean sport;
    private ArrayList<SportListModel.DataBeanX.DataBean> dropDownList = new ArrayList<>();
    private String dateNow = "";
    private String dateSend = "";
    private String startTime = "";
    private Date strDate = null;
    private Date sEndDate = null;
    private String enDate = null;
    private String eDate = null;
    private String endTime = "";
    private String timeNow = "";
    private Date now = null;
    private int ADDRESS_EVENT = 132;
    private String locationLat = "", locationLong = "";
    private O_SessionDataModel data;
    private String sportId = "";
    private ArrayList<String> sports = new ArrayList<>();

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_training_session);

        init();
        sportsListApi();


        binding.ctsBackArrowImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (editSession) {
            if (getIntent().getExtras() != null) {
                data = (O_SessionDataModel) getIntent().getSerializableExtra("sessionEdit");
                if (data != null) {
                    prepareDataSet();
                }
            }
        }

    }

    private void prepareDataSet() {
        binding.createTrainingSessionTv.setText("Update Training Session");
        binding.createTrainingSessionNameEdt.setText(data.getName());
        binding.createTrainingSessionDescEdt.setText(data.getDescription());
        binding.createTrainingSessionPhoneEdt.setText(data.getPhone());
        binding.getAddressTv.setText(data.getLocation());
        enDate = data.getEnd_date();
        dateSend = data.getStart_date();
        binding.createTrainingDateTv.setText(data.getStart_date());

        binding.EndDateTv.setText(data.getEnd_date());
        binding.createEvtnStartTimeTv.setText(data.getStart_time());
        binding.createEventEndTime.setText(data.getEnd_time());
        binding.createTrainingSessionHourRateEdt.setText(data.getHourly_rate() + "");
        binding.createTrainingSessionMaxOccuEdt.setText(data.getGuest_allowed() + "");

        locationLat = data.getLatitude() + "";
        locationLong = data.getLongitude() + "";

        binding.createSessionBtn.setText("Update");


    }

    private void init() {
        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        binding.getAddressTv.setOnClickListener(this);
        progressDialog.setCancelable(false);
        binding.createTrainingDateTv.setOnClickListener(this);
        binding.EndDateTv.setOnClickListener(this);
        binding.createTrainingSessionUploadTv.setOnClickListener(this);
        binding.createSessionBtn.setOnClickListener(this);
        binding.createEventEndTime.setOnClickListener(this);
        binding.createEvtnStartTimeTv.setOnClickListener(this);
    }

    private void setSportAdapter() {

        for (SportListModel.DataBeanX.DataBean details : dropDownList) {
            sports.add(details.getName());
        }
        createEventServiceSpinner = findViewById(R.id.createEventSportsSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(CreateTrainingSession.this, R.layout.spinner_view, R.id.sportNameTv, sports);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        createEventServiceSpinner.setAdapter(adapter);

        createEventServiceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                createEventServiceSpinner.setSelection(i);
                if (i == 0) {
                    sportId = "";
                } else {
                    sportId = String.valueOf(dropDownList.get(i).getId());
                    Toast.makeText(CreateTrainingSession.this, "" + sportId, Toast.LENGTH_SHORT).show();
                }

//                servicePrice = String.valueOf(dropDownList.get(i).getPrice());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void sportsListApi() {
        progressDialog.show();
        Call<SportListModel> call = retrofitinterface.getSportList(Constants.CONTENT_TYPE, "", "");
        call.enqueue(new Callback<SportListModel>() {
            @Override
            public void onResponse(Call<SportListModel> call, Response<SportListModel> response) {
                progressDialog.dismiss();
                if (response.body().isStatus()) {
                    sport = new SportListModel.DataBeanX.DataBean();
                    sport.setName("Select Sports");
                    dropDownList.add(sport);
                    if (response.body() != null) {
                        dropDownList.addAll(response.body().getData().getData());
                        setSportAdapter();
                    }
                }
            }

            @Override
            public void onFailure(Call<SportListModel> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    private void hitCreateSessionApi() {
        progressDialog.show();
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        requestBodyMap.put("name", RequestBody.create(MediaType.parse("multipart/form-data"), sessionName));
        requestBodyMap.put("description", RequestBody.create(MediaType.parse("multipart/form-data"), sessionDescription));
        requestBodyMap.put("end_date", RequestBody.create(MediaType.parse("multipart/form-data"), (enDate)));
        requestBodyMap.put("start_date", RequestBody.create(MediaType.parse("multipart/form-data"), dateSend));
        requestBodyMap.put("start_time", RequestBody.create(MediaType.parse("multipart/form-data"), startTime));
        requestBodyMap.put("end_time", RequestBody.create(MediaType.parse("multipart/form-data"), endTime));
        requestBodyMap.put("hourly_rate", RequestBody.create(MediaType.parse("multipart/form-data"), sessionHourlyRate));
        requestBodyMap.put("phone", RequestBody.create(MediaType.parse("multipart/form-data"), sessionPhone));
        requestBodyMap.put("guest_allowed", RequestBody.create(MediaType.parse("multipart/form-data"), sessionMaxOccupancy));

        requestBodyMap.put("location", RequestBody.create(MediaType.parse("multipart/form-data"), eventAddress));
        requestBodyMap.put("sport_id", RequestBody.create(MediaType.parse("multipart/form-data"), sportId));
        requestBodyMap.put("latitude", RequestBody.create(MediaType.parse("multipart/form-data"), locationLat));
        requestBodyMap.put("longitude", RequestBody.create(MediaType.parse("multipart/form-data"), locationLong));
        requestBodyMap.put("max_occupancy", RequestBody.create(MediaType.parse("multipart/form-data"), sessionMaxOccupancy));
        requestBodyMap.put("Content-Type", RequestBody.create(MediaType.parse("multipart/form-data"), Constants.CONTENT_TYPE));
        List<MultipartBody.Part> parts = new ArrayList<>();
        parts.add(PortfolioImagesConstants.partOne);
        parts.add(PortfolioImagesConstants.partTwo);
        parts.add(PortfolioImagesConstants.partThree);
        parts.add(PortfolioImagesConstants.partFour);
        //        requestBodyMap.put("device_token", RequestBody.create(MediaType.parse("multipart/form-data"), Constants.DEVICE_TOKEN));
        Call<OrgCreateEventResponse> signUpAthlete = retrofitinterface.createSession("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getApplicationContext()), requestBodyMap, parts);
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
                        JSONObject jsonObject=jObjError.getJSONObject("error");
                        String code=jsonObject.getString("code");
                        if (!TextUtils.isEmpty(code)) {
                            if (Integer.parseInt(code) == 401) {
                                CommonMethods.invalidAuthToken(CreateTrainingSession.this, CreateTrainingSession.this);
                            }
                        }
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
                break;
            case R.id.createTrainingDateTv:
                getTrainingDate();
                break;
            case R.id.EndDateTv:
                getEndDate();
                break;
            case R.id.createTrainingSessionUploadTv:
                if (editSession) {
                    Intent getImages = new Intent(CreateTrainingSession.this, PortfolioActivity.class);
                    getImages.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    PortfolioActivity.updateImages = true;
                    getImages.putExtra("updateEventImg", data.getImages());
                    getImages.putExtra("updateImgType", "sessionImgUpdate");
                    startActivityForResult(getImages, SESSIOM_IMAGE);
                }
                Intent getImages = new Intent(CreateTrainingSession.this, PortfolioActivity.class);
                getImages.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                PortfolioActivity.getImages = true;
                startActivityForResult(getImages, SESSIOM_IMAGE);
                break;
            case R.id.createSessionBtn:
                getDataFromEdt();
                break;
            case R.id.createEventEndTime:
                getEndTime();
                break;
            case R.id.createEvtnStartTimeTv:
                getStartTime();
                break;
        }
    }

//    private void getTrainingTime() {
//        Calendar c = Calendar.getInstance();
//        mHour = c.get(Calendar.HOUR_OF_DAY);
//        mMinute = c.get(Calendar.MINUTE);
//        TimePickerDialog timePickerDialog = new TimePickerDialog(CreateTrainingSession.this, new TimePickerDialog.OnTimeSetListener() {
//            @RequiresApi(api = Build.VERSION_CODES.O)
//            @Override
//            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
//                startTime=convertDate(hourOfDay) + ":" +convertDate( minute);
//                if (strDate!=null && now !=null) {
//                    if (strDate.compareTo(now) == 0) {
//                        if (LocalTime.parse(startTime).isAfter(LocalTime.now())) {
//                            binding.createTrainingTimeTv.setText(startTime);
//                        } else {
//                            binding.createTrainingTimeTv.setText("");
//                            binding.createTrainingTimeTv.setHint("Start time");
//                            Toast.makeText(CreateTrainingSession.this, "Select a valid time", Toast.LENGTH_SHORT).show();
//                        }
//                    } else {
//                        binding.createTrainingTimeTv.setText(startTime);
//                    }
//                }else {
//                    Toast.makeText(CreateTrainingSession.this, "Selecte Date First", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }, mHour, mMinute, true);
//        timePickerDialog.show();
//    }

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
                dateNow = convertDate(dayOfMonth) + "/" + convertDate((monthOfYear + 1)) + "/" + year;
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String currentDateandTime = sdf.format(new Date());
                try {
                    now = sdf.parse(currentDateandTime);
                    strDate = sdf.parse(dateNow);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (strDate.compareTo(now) > 0) {
                    binding.createTrainingDateTv.setText(dateNow);
                } else {
                    binding.createTrainingDateTv.setText("");
                    binding.createTrainingDateTv.setHint("Enter date");
                    Toast.makeText(CreateTrainingSession.this, "Can't create session for current date", Toast.LENGTH_SHORT).show();
                }
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    private void getEndDate() {
        Calendar cal = Calendar.getInstance();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(CreateTrainingSession.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                binding.EndDateTv.setPadding(20, 20, 20, 20);
                enDate = year + "-" + convertDate((monthOfYear + 1)) + "-" + convertDate(dayOfMonth);
                eDate = convertDate(dayOfMonth) + "/" + convertDate((monthOfYear + 1)) + "/" + year;
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    sEndDate = sdf.parse(eDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (strDate != null) {
                    if (sEndDate.compareTo(strDate) >= 0) {
                        Log.i("app", "Date1 is after Date2");
                        binding.EndDateTv.setText(eDate);
                        binding.createEvtnStartTimeTv.setText("");
                        binding.createEventEndTime.setText("");
                        binding.createEvtnStartTimeTv.setHint("Start time");
                        binding.createEventEndTime.setHint("End time");

                    } else {
                        Toast.makeText(CreateTrainingSession.this, "Select valid date", Toast.LENGTH_SHORT).show();
                        binding.EndDateTv.setText("");
                        binding.EndDateTv.setHint("Enter date");
                    }
                } else {
                    Toast.makeText(CreateTrainingSession.this, "Select start Date", Toast.LENGTH_SHORT).show();
                }
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    private void getStartTime() {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(CreateTrainingSession.this, new TimePickerDialog.OnTimeSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                startTime = convertDate(hourOfDay) + ":" + convertDate(minute);

                timeNow = convertDate(mHour) + ":" + convertDate(mMinute);


//                int currentTime = LocalTime.parse(startTime);

                binding.createEvtnStartTimeTv.setPadding(20, 0, 70, 0);

//                Date time = Calendar.getInstance().getTime();
//                SimpleDateFormat timeFormat=new SimpleDateFormat("HH:mm");
//
//                String timeNow=timeFormat.format(time);
                if (strDate != null && sEndDate != null) {
//                    if (strDate.compareTo(sEndDate) == 0) {
//                        if (LocalTime.parse(startTime).isAfter(LocalTime.now())) {
//                        if (formatTime(startTime).after(formatTime(timeNow))) {
                    binding.createEvtnStartTimeTv.setText(startTime);
                    binding.createEventEndTime.setText("");
                    binding.createEventEndTime.setHint("End time");
//                        } else {
//                            binding.createEvtnStartTimeTv.setText("");
//                            binding.createEvtnStartTimeTv.setHint("Start time");
//                            Toast.makeText(CreateTrainingSession.this, "Select a valid time", Toast.LENGTH_SHORT).show();
//                        }
//                    } else {
//                        binding.createEvtnStartTimeTv.setText(startTime);
//                    }
                } else {
                    Toast.makeText(CreateTrainingSession.this, "Selecte Date First", Toast.LENGTH_SHORT).show();
                }

            }
        }, mHour, mMinute, true);
        timePickerDialog.show();
    }

    private void getEndTime() {
        Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(CreateTrainingSession.this, new TimePickerDialog.OnTimeSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                endTime = convertDate(hourOfDay) + ":" + convertDate(minute);
                binding.createEventEndTime.setPadding(20, 0, 70, 0);
                if (strDate != null && sEndDate != null) {
                    if (sEndDate.compareTo(strDate) == 0) {
                        if (!startTime.isEmpty()) {
//                            if (LocalTime.parse(endTime).isAfter(LocalTime.parse(startTime))) {
                            if (formatTime(endTime).after(formatTime(startTime))) {
                                binding.createEventEndTime.setText(endTime);
                            } else {
                                binding.createEventEndTime.setText("");
                                binding.createEventEndTime.setHint("End time");
                                Toast.makeText(CreateTrainingSession.this, "Selecte valid time", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(CreateTrainingSession.this, "Selecte Start time", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        binding.createEventEndTime.setText(endTime);

                    }
                } else {
                    Toast.makeText(CreateTrainingSession.this, "Selecte Date First", Toast.LENGTH_SHORT).show();
                }

            }
        }, mHour, mMinute, true);
        timePickerDialog.show();
    }

    private Date formatTime(String time) {
        Date formated = null;
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        try {
            formated = timeFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formated;
    }


    private void getDataFromEdt() {
        sessionName = binding.createTrainingSessionNameEdt.getText().toString().trim();
        sessionDescription = binding.createTrainingSessionDescEdt.getText().toString().trim();
        sessionPhone = binding.createTrainingSessionPhoneEdt.getText().toString().trim();
        sessionStartDate = binding.createTrainingDateTv.getText().toString().trim();
        sessionEndDate = binding.EndDateTv.getText().toString().trim();
        eventStartTime = binding.createEvtnStartTimeTv.getText().toString().trim();
        eventEndtime = binding.createEventEndTime.getText().toString().trim();
        sessionHourlyRate = binding.createTrainingSessionHourRateEdt.getText().toString().trim();
        sessionMaxOccupancy = binding.createTrainingSessionMaxOccuEdt.getText().toString().trim();
        eventAddress = binding.getAddressTv.getText().toString().trim();
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

        } else if (sessionEndDate.isEmpty()) {
            Toast.makeText(this, "Enter session end date", Toast.LENGTH_SHORT).show();
        } else if (eventStartTime.isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.select_start_time), Toast.LENGTH_SHORT).show();

        } else if (eventEndtime.isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.select_end_time), Toast.LENGTH_SHORT).show();

        } else if (sessionHourlyRate.isEmpty()) {
            binding.createTrainingSessionHourRateEdt.setError(getResources().getString(R.string.enter_hourly_rate_session));
            binding.createTrainingSessionHourRateEdt.requestFocus();
        } else if ((Integer.parseInt(sessionHourlyRate)) <= 0) {
//            binding.createTrainingSessionHourRateEdt.setError(getResources().getString(R.string.enter_valid_session_price));
            Toast.makeText(this, getResources().getString(R.string.enter_valid_session_price), Toast.LENGTH_SHORT).show();
            binding.createTrainingSessionHourRateEdt.requestFocus();
        } else if (sessionMaxOccupancy.isEmpty()) {
            binding.createTrainingSessionMaxOccuEdt.setError(getResources().getString(R.string.enter_session_max_occupacy));
            binding.createTrainingSessionMaxOccuEdt.requestFocus();
        } else if ((Integer.parseInt(sessionMaxOccupancy)) <= 0) {
            Toast.makeText(this, getResources().getString(R.string.enter_valid_capacity), Toast.LENGTH_SHORT).show();
        } else {
            if (editSession) {
                startTime = data.getStart_time();
                endTime = data.getEnd_time();
                updateTrainingSession();
            } else {
                hitCreateSessionApi();
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
            } else if (requestCode == SESSIOM_IMAGE) {
                if (data != null && data.hasExtra(Constants.ADDRESS)) {
                    Toast.makeText(CreateTrainingSession.this, "Images Imported", Toast.LENGTH_SHORT).show();
                    binding.createTrainingSessionUploadTv.setText(PortfolioImagesConstants.numImages + " Images selected");
                }
            }
        } else {
//            Toast.makeText(this, "Unable to import Images", Toast.LENGTH_SHORT).show();
        }
    }

    public String convertDate(int input) {
        if (input >= 10) {
            return String.valueOf(input);
        } else {
            return "0" + input;
        }
    }

    @Override
    protected void onDestroy() {
        editSession = false;
        super.onDestroy();
    }

    private void updateTrainingSession() {
        progressDialog.show();
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        requestBodyMap.put("name", RequestBody.create(MediaType.parse("multipart/form-data"), sessionName));
        requestBodyMap.put("description", RequestBody.create(MediaType.parse("multipart/form-data"), sessionDescription));
        requestBodyMap.put("end_date", RequestBody.create(MediaType.parse("multipart/form-data"), (enDate)));
        requestBodyMap.put("start_date", RequestBody.create(MediaType.parse("multipart/form-data"), dateSend));
        requestBodyMap.put("start_time", RequestBody.create(MediaType.parse("multipart/form-data"), startTime));
        requestBodyMap.put("end_time", RequestBody.create(MediaType.parse("multipart/form-data"), endTime));
        requestBodyMap.put("hourly_rate", RequestBody.create(MediaType.parse("multipart/form-data"), sessionHourlyRate));
        requestBodyMap.put("phone", RequestBody.create(MediaType.parse("multipart/form-data"), sessionPhone));
        requestBodyMap.put("guest_allowed", RequestBody.create(MediaType.parse("multipart/form-data"), sessionMaxOccupancy));

        requestBodyMap.put("location", RequestBody.create(MediaType.parse("multipart/form-data"), eventAddress));
        requestBodyMap.put("latitude", RequestBody.create(MediaType.parse("multipart/form-data"), locationLat));
        requestBodyMap.put("longitude", RequestBody.create(MediaType.parse("multipart/form-data"), locationLong));
        requestBodyMap.put("id", RequestBody.create(MediaType.parse("multipart/form-data"), data.getId() + ""));
        requestBodyMap.put("max_occupancy", RequestBody.create(MediaType.parse("multipart/form-data"), sessionMaxOccupancy));
        requestBodyMap.put("Content-Type", RequestBody.create(MediaType.parse("multipart/form-data"), Constants.CONTENT_TYPE));
        List<MultipartBody.Part> parts = new ArrayList<>();
        if (PortfolioImagesConstants.partOne != null) {
            parts.add(PortfolioImagesConstants.partOne);
        }
        if (PortfolioImagesConstants.partTwo != null) {
            parts.add(PortfolioImagesConstants.partTwo);
        }
        if (PortfolioImagesConstants.partThree != null) {
            parts.add(PortfolioImagesConstants.partThree);
        }
        if (PortfolioImagesConstants.partFour != null) {
            parts.add(PortfolioImagesConstants.partFour);
        }
        //        requestBodyMap.put("device_token", RequestBody.create(MediaType.parse("multipart/form-data"), Constants.DEVICE_TOKEN));
        Call<OrgCreateEventResponse> signUpAthlete = retrofitinterface.updateSession("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getApplicationContext()), requestBodyMap, parts);
        signUpAthlete.enqueue(new Callback<OrgCreateEventResponse>() {
            @Override
            public void onResponse(Call<OrgCreateEventResponse> call, Response<OrgCreateEventResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData() != null) {
                            PortfolioActivity.clearFromConstants();
                            Constants.CHECKBOX_IS_CHECKED = 0;
//                            Toast.makeText(CreateTrainingSession.this, "" + response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(CreateTrainingSession.this, "Updated", Toast.LENGTH_SHORT).show();
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
}
