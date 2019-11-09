package com.netscape.utrain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.athlete.EventDetail;
import com.netscape.utrain.databinding.ActivityEventBookingBinding;
import com.netscape.utrain.model.EventBookingModel;
import com.netscape.utrain.response.SessionDetailResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventBookingActivity extends AppCompatActivity {

    String ticket = "Ticket Price";
    private Retrofitinterface retrofitinterface;
    private ProgressDialog progressDialog;
    private EventBookingActivity activity;
    private ActivityEventBookingBinding binding;
    private int countVAlue = 1;
    private int ticketPrice;
    private int totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_event_booking);
        activity = this;
        binding = DataBindingUtil.setContentView(activity, R.layout.activity_event_booking);

        final String seatNo = getIntent().getStringExtra("seatLeft");

        if (getIntent().hasExtra("eventName"))
            binding.eventBookMarathonHeaderTv.setText(getIntent().getStringExtra("eventName"));
        else
            binding.eventBookMarathonHeaderTv.setText(getIntent().getStringExtra("Loading.."));
        if (getIntent().hasExtra("eventVenue"))
            binding.eventVanueDetailTv.setText(getIntent().getStringExtra("eventVenue"));
        else
            binding.eventVanueDetailTv.setText(getIntent().getStringExtra("Loading.."));
        if (getIntent().hasExtra("eventTime"))
            binding.eventTimeDetailTv.setText(getIntent().getStringExtra("eventTime"));
        else
            binding.eventTimeDetailTv.setText(getIntent().getStringExtra("Loading.."));
        if (getIntent().hasExtra("eventDate"))
            binding.eventDateDetailTv.setText(getIntent().getStringExtra("eventDate"));
        else
            binding.eventDateDetailTv.setText(getIntent().getStringExtra("Loading.."));
        if (getIntent().hasExtra("Array"))
            Glide.with(EventBookingActivity.this).load(getIntent().getStringExtra("Array")).into(binding.eventBookingImage);
        else
            Glide.with(EventBookingActivity.this).load(getResources().getDrawable(R.drawable.ic_place_holder)).into(binding.eventBookingImage);


        binding.eventBookingBackImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);


        String type = "";

        type = getIntent().getStringExtra("type");
        if (type.equalsIgnoreCase("space")) {
//                hitEventDetailAPI();
        }
        if (type.equalsIgnoreCase("event")) {
            hitEventDetailAPI();
        }
        if (type.equalsIgnoreCase("session")) {
            getSessionDetails();
        }
//


//        hitEventDetailAPI();
        binding.addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if (countVAlue < 5) {
                    if (countVAlue<Integer.parseInt(seatNo)){
                        countVAlue += 1;
                        totalPrice = ticketPrice * countVAlue;
                        binding.text1.setText((ticket + " * " + countVAlue) + "");
                        binding.eventPrice.setText("$" + totalPrice + "");
                        binding.countDisplay.setText(countVAlue + "");
                    } else {
                        Toast.makeText(EventBookingActivity.this, "Only "+seatNo+" Seat left", Toast.LENGTH_SHORT).show();

                    }


                } else {
                    Snackbar.make(binding.maineventBooking, getResources().getString(R.string.cannotExceed), BaseTransientBottomBar.LENGTH_LONG).show();
                }
            }
        });

        binding.substractImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (countVAlue > 1) {
                    countVAlue -= 1;
                    totalPrice = ticketPrice * countVAlue;
                    binding.text1.setText((ticket + " * " + countVAlue) + "");
                    binding.eventPrice.setText("$" + totalPrice + "");
                    binding.countDisplay.setText(countVAlue + "");
                } else {
                    Snackbar.make(binding.maineventBooking, getResources().getString(R.string.chooseCorrect), BaseTransientBottomBar.LENGTH_LONG).show();
                }
            }
        });

        binding.bottomConstraint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, PaymentActivity.class);
                intent.putExtra("type", getIntent().getStringExtra("type"));
                intent.putExtra("totalPrice", totalPrice);
                intent.putExtra("tickets", countVAlue);
                intent.putExtra("event_id", getIntent().getIntExtra("event_id", 0));
                startActivity(intent);
            }
        });

//        hitEventDetailAPI();
    }

    private void hitEventDetailAPI() {

        progressDialog = new ProgressDialog(activity);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading.........");
        progressDialog.show();
        Call<EventBookingModel> signUpAthlete = retrofitinterface.eventDetail("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, activity), Constants.CONTENT_TYPE, getIntent().getIntExtra("event_id", 0) + "");
        signUpAthlete.enqueue(new Callback<EventBookingModel>() {
            @Override
            public void onResponse(Call<EventBookingModel> call, Response<EventBookingModel> response) {
                progressDialog.dismiss();

                if (response.isSuccessful()) {
                    if (response.body().isStatus()) {
                        if (response.body().getData() != null) {

                            binding.eventBookMarathonHeaderTv.setText(response.body().getData().getName());
                            binding.eventVanueDetailTv.setText(response.body().getData().getLocation());
                            binding.eventTimeDetailTv.setText(response.body().getData().getStart_time());
                            binding.eventDateDetailTv.setText(response.body().getData().getStart_date());
                            binding.eventPrice.setText("$" + response.body().getData().getPrice() + "");
                            ticketPrice = response.body().getData().getPrice();
                            binding.text1.setText((ticket + " * " + countVAlue) + "");
                            try {
                                if (response.body().getData().getImages() != null) {
                                    JSONArray jsonArray = new JSONArray(response.body().getData().getImages());
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        Glide.with(activity).load(Constants.IMAGE_BASE_EVENT + jsonArray.get(i)).thumbnail(Glide.with(activity).load(Constants.IMAGE_BASE_EVENT + Constants.THUMBNAILS + jsonArray.get(i))).into(binding.eventBookingImage);

                                    }
                                }

                            } catch (JSONException e) {

                                Toast.makeText(activity, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

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
            public void onFailure(Call<EventBookingModel> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(binding.maineventBooking, getResources().getString(R.string.something_went_wrong), BaseTransientBottomBar.LENGTH_LONG).show();

            }
        });
    }

    private void getSessionDetails() {

        progressDialog = new ProgressDialog(activity);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading.........");
        progressDialog.show();
        Call<SessionDetailResponse> signUpAthlete = retrofitinterface.getSessionDetails("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, activity), Constants.CONTENT_TYPE, getIntent().getIntExtra("event_id", 0) + "");
        signUpAthlete.enqueue(new Callback<SessionDetailResponse>() {
            @Override
            public void onResponse(Call<SessionDetailResponse> call, Response<SessionDetailResponse> response) {
                progressDialog.dismiss();

                if (response.isSuccessful()) {
                    if (response.body().isStatus()) {
                        if (response.body().getData() != null) {
                            binding.eventBookMarathonHeaderTv.setText(response.body().getData().getName());
                            binding.eventVanueDetailTv.setText(response.body().getData().getLocation());
                            binding.eventTimeDetailTv.setText(response.body().getData().getBusiness_hour());
                            binding.eventDateDetailTv.setText(response.body().getData().getDate());
                            binding.eventPrice.setText("$" + response.body().getData().getHourly_rate() + "");
                            ticketPrice = response.body().getData().getHourly_rate();
                            binding.text1.setText((ticket + "*" + countVAlue) + "");
                            try {
                                if (response.body().getData().getImages() != null) {
                                    JSONArray jsonArray = new JSONArray(response.body().getData().getImages());
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        Glide.with(activity).load(Constants.IMAGE_BASE_SESSION + jsonArray.get(i)).thumbnail(Glide.with(activity).load(Constants.IMAGE_BASE_SESSION+Constants.THUMBNAILS + jsonArray.get(i))).into(binding.eventBookingImage);

                                    }
                                }

                            } catch (JSONException e) {

                                Toast.makeText(activity, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

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
            public void onFailure(Call<SessionDetailResponse> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(binding.maineventBooking, getResources().getString(R.string.something_went_wrong), BaseTransientBottomBar.LENGTH_LONG).show();

            }
        });
    }

}
