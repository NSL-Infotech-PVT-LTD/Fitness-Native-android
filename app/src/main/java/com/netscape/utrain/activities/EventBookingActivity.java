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
import com.netscape.utrain.databinding.ActivityEventBookingBinding;
import com.netscape.utrain.model.EventBookingModel;
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

        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);

        hitEventDetailAPI();
        binding.addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (countVAlue < 5) {
                    countVAlue += 1;
                    totalPrice = ticketPrice * countVAlue;
                    binding.text1.setText((ticketPrice + "  * " + countVAlue) + "");
                    binding.eventPrice.setText("$" + totalPrice + "");
                    binding.countDisplay.setText(countVAlue + "");
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
                    binding.text1.setText((ticketPrice + "  * " + countVAlue) + "");
                    binding.eventPrice.setText("$" + totalPrice + "");
                    binding.countDisplay.setText(countVAlue + "");
                } else {
                    Snackbar.make(binding.maineventBooking, getResources().getString(R.string.chooseCorrect), BaseTransientBottomBar.LENGTH_LONG).show();
                }
            }
        });

        binding.textContinueToPay.setOnClickListener(new View.OnClickListener() {
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
                            binding.text1.setText((ticketPrice + "*" + countVAlue) + "");
                            try {
                                if (response.body().getData().getImages() != null) {
                                    JSONArray jsonArray = new JSONArray(response.body().getData().getImages());
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        Glide.with(activity).load(Constants.IMAGE_BASE_EVENT + jsonArray.get(i)).into(binding.eventBookingImage);

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

}
