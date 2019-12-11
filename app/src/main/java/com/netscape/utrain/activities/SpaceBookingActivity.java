package com.netscape.utrain.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.legacy.widget.Space;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.netscape.utrain.R;
import com.netscape.utrain.databinding.ActivitySpaceBookingBinding;
import com.netscape.utrain.model.EventBookingModel;
import com.netscape.utrain.response.SpaceDetailResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpaceBookingActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivitySpaceBookingBinding binding;
    private String sDate = "";
    private String startDate = "";
    private String eDate = "";
    private String enDate = "";
    private String startTime = "";
    private String timeNow = "";
    private String endTime = "";
    private Date strDate = null;
    private Date stDate = null;
    private Date endDate = null;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private String eventId = "", eventStartDate = "", eventEndDate = "", eventStartTime = "", eventEndtime = "", totalPrice = "";
    private ProgressDialog progressDialog;
    private Retrofitinterface retrofitinterface;
    private int pricePerday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_space_booking);
        init();
    }

    private void init() {
        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);
        hitSpaceDetailAPI();
        if (getIntent().getExtras() != null) {
            binding.eventBookMarathonHeaderTv.setText(getIntent().getStringExtra("eventName"));
            binding.eventVanueDetailTv.setText(getIntent().getStringExtra("eventVenue"));
            binding.eventTimeDetailTv.setText(getIntent().getStringExtra("eventTime"));
            binding.eventDateDetailTv.setText(getIntent().getStringExtra("eventDate"));

        }
        binding.eventBookingBackImg.setOnClickListener(this);
        binding.textContinueToPay.setOnClickListener(this);
        binding.createEventEndDatetv.setOnClickListener(this);
        binding.createEventStartDateTv.setOnClickListener(this);
        binding.createEventEndTime.setOnClickListener(this);
        binding.createEvtnStartTimeTv.setOnClickListener(this);
    }

    private void getStartTime() {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(SpaceBookingActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
//                            Toast.makeText(SpaceBookingActivity.this, "Select a valid time", Toast.LENGTH_SHORT).show();
//                        }
//                    } else {
//                        binding.createEvtnStartTimeTv.setText(startTime);
//                    }
                } else {
                    Toast.makeText(SpaceBookingActivity.this, "Selecte Date First", Toast.LENGTH_SHORT).show();
                }

            }
        }, mHour, mMinute, true);
        timePickerDialog.show();
    }

    private void getEndTime() {
        Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(SpaceBookingActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
                                Toast.makeText(SpaceBookingActivity.this, "Selecte valid time", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(SpaceBookingActivity.this, "Selecte Start time", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        binding.createEventEndTime.setText(endTime);

                    }
                } else {
                    Toast.makeText(SpaceBookingActivity.this, "Selecte Date First", Toast.LENGTH_SHORT).show();
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
        DatePickerDialog datePickerDialog = new DatePickerDialog(SpaceBookingActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                    Toast.makeText(SpaceBookingActivity.this, "Can't book for current date", Toast.LENGTH_SHORT).show();
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
        DatePickerDialog datePickerDialog = new DatePickerDialog(SpaceBookingActivity.this, new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
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
                        try {
                            Long days = CommonMethods.betweenDates(stDate, endDate);
                            setTotalPrice(days);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(SpaceBookingActivity.this, "Select valid date", Toast.LENGTH_SHORT).show();
                        binding.createEventEndDatetv.setText("");
                        binding.createEventEndDatetv.setHint("End date");
                    }
                } else {
                    Toast.makeText(SpaceBookingActivity.this, "Select start Date", Toast.LENGTH_SHORT).show();
                }
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    private void setTotalPrice(Long days) {
        days = days + 1;
        totalPrice = String.valueOf((days * pricePerday));
        binding.totlaPriceTv.setText("Price per day * " + days + " days");
        binding.eventPrice.setText("$ " + String.valueOf((days * pricePerday)));
    }

    public String convertDate(int input) {
        if (input >= 10) {
            return String.valueOf(input);
        } else {
            return "0" + input;
        }
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
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
            case R.id.textContinueToPay:
                getDataFromEdtText();
                break;
            case R.id.eventBookingBackImg:
                finish();
                break;
        }
    }

    private void getDataFromEdtText() {
        eventStartDate = binding.createEventStartDateTv.getText().toString();
        eventEndDate = binding.createEventEndDatetv.getText().toString();
        eventStartTime = binding.createEvtnStartTimeTv.getText().toString();
        eventEndtime = binding.createEventEndTime.getText().toString();
        if (eventStartDate.isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.select_start_date), Toast.LENGTH_SHORT).show();
        } else if (eventEndDate.isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.select_end_date), Toast.LENGTH_SHORT).show();

        } else if (eventStartTime.isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.select_start_time), Toast.LENGTH_SHORT).show();

        } else if (eventEndtime.isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.select_end_time), Toast.LENGTH_SHORT).show();
        } else {
//            hitCreateEventApi();
            Intent intent = new Intent(SpaceBookingActivity.this, PaymentActivity.class);
            intent.putExtra("type", getIntent().getStringExtra("type"));
            intent.putExtra("totalPrice", totalPrice);
            intent.putExtra("startDate", sDate + " " + startTime);
            intent.putExtra("endDate", enDate + " " + endTime);
//                intent.putExtra("tickets", countVAlue);
            intent.putExtra("event_id", getIntent().getStringExtra("event_id"));
            startActivity(intent);

        }

    }

    private void hitSpaceDetailAPI() {

        progressDialog = new ProgressDialog(SpaceBookingActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading.........");
        progressDialog.show();
        Call<SpaceDetailResponse> signUpAthlete = retrofitinterface.spaceDetail("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, SpaceBookingActivity.this), Constants.CONTENT_TYPE, getIntent().getStringExtra("event_id"));
        signUpAthlete.enqueue(new Callback<SpaceDetailResponse>() {
            @Override
            public void onResponse(Call<SpaceDetailResponse> call, Response<SpaceDetailResponse> response) {
                progressDialog.dismiss();

                if (response.isSuccessful()) {
                    if (response.body().isStatus()) {
                        if (response.body().getData() != null) {

                            binding.eventBookMarathonHeaderTv.setText(response.body().getData().getName());
                            binding.eventVanueDetailTv.setText(response.body().getData().getLocation());
                            binding.eventTimeDetailTv.setText(response.body().getData().getOpen_hours_to());
                            binding.eventDateDetailTv.setText(response.body().getData().getAvailability_week());
                            binding.pricePerdayTv.setText("(Price per day $" + response.body().getData().getPrice_daily() + ")");
                            binding.totlaPriceTv.setText("Price per day * day");
                            pricePerday = response.body().getData().getPrice_daily();
//                            ticketPrice = response.body().getData().getPrice();
//                            binding.text1.setText((ticket + " * " + countVAlue) + "");
                            try {
                                if (response.body().getData().getImages() != null) {
                                    JSONArray jsonArray = new JSONArray(response.body().getData().getImages());
                                    if (jsonArray != null && jsonArray.length() > 0) {
                                        Glide.with(SpaceBookingActivity.this).load(Constants.IMAGE_BASE_PLACE + jsonArray.get(0)).thumbnail(Glide.with(SpaceBookingActivity.this).load(Constants.IMAGE_BASE_PLACE + Constants.THUMBNAILS + jsonArray.get(0))).into(binding.eventBookingImage);
                                    }

                                }

                            } catch (JSONException e) {

                                Toast.makeText(SpaceBookingActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                            }

                        }
                    }
                } else {
//                    progressDialog.dismiss();

                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);
                        Snackbar.make(binding.maineventBooking, errorMessage.toString(), BaseTransientBottomBar.LENGTH_LONG).show();


                    } catch (Exception e) {
//                        Snackbar.make(binding.athleteLoginLayout,e.getMessage().toString(), BaseTransientBottomBar.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SpaceDetailResponse> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(binding.maineventBooking, getResources().getString(R.string.something_went_wrong), BaseTransientBottomBar.LENGTH_LONG).show();

            }
        });
    }


}
