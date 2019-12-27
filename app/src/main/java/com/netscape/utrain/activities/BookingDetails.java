package com.netscape.utrain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.netscape.utrain.R;
import com.netscape.utrain.adapters.A_EventListAdapter;
import com.netscape.utrain.adapters.A_SessionListAdapter;
import com.netscape.utrain.adapters.A_SpaceListAdapter;
import com.netscape.utrain.databinding.ActivityNotificationBinding;
import com.netscape.utrain.fragments.O_UpcEventFragment;
import com.netscape.utrain.model.AthleteBookListModel;
import com.netscape.utrain.model.AthleteSessionBookList;
import com.netscape.utrain.model.AthleteSpaceBookList;
import com.netscape.utrain.model.SelectSpaceDaysModel;
import com.netscape.utrain.response.EventDetailResponse;
import com.netscape.utrain.response.SessionBookingDetails;
import com.netscape.utrain.response.SpaceBookingDetailResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PrefrenceConstant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingDetails extends AppCompatActivity {
    ChipGroup chipSpaceGroup;
    private ActivityNotificationBinding binding;
    private ProgressDialog progressDialog;
    private Retrofitinterface retrofitinterface;
    private EventDetailResponse.DataBean eventData;
    private SessionBookingDetails.DataBean sessionData;
    private SpaceBookingDetailResponse.DataBean spaceData;
    private String jobId = "", jobType = "", status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_notification);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification);
        init();
        binding.backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void init() {


        if (getIntent().getExtras() != null) {
            jobType = getIntent().getStringExtra(Constants.SELECTED_TYPE);
            jobId = getIntent().getStringExtra(Constants.SELECTED_ID);
            status = getIntent().getStringExtra(Constants.STATUS);
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);
        jobTypeFilter();
    }

    private void jobTypeFilter() {
        if (jobType.equalsIgnoreCase(Constants.EVENT)) {
            getEventDetail();
        }
        if (jobType.equalsIgnoreCase(Constants.SESSION)) {
            getSessionDetail();
        }
        if (jobType.equalsIgnoreCase(Constants.SPACE)) {
            getSpaceDetails();
        }


    }


    public void getEventDetail() {
        progressDialog.show();
        Call<EventDetailResponse> call = retrofitinterface.getBookingEvent("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getApplicationContext()), Constants.CONTENT_TYPE, jobId);
        call.enqueue(new Callback<EventDetailResponse>() {
            @Override
            public void onResponse(Call<EventDetailResponse> call, Response<EventDetailResponse> response) {
                if (response.body() != null) {
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body() != null) {
                            binding.noBookingDetailImg.setVisibility(View.GONE);
                            eventData = (response.body().getData());
                            setEventData();
                        } else {
                            binding.noBookingDetailImg.setVisibility(View.VISIBLE);

                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();
                        binding.noBookingDetailImg.setVisibility(View.VISIBLE);

                    }
                } else {
                    binding.noBookingDetailImg.setVisibility(View.VISIBLE);

                    progressDialog.dismiss();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);

                        Toast.makeText(getApplicationContext(), "" + errorMessage, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {

                    }
                }

            }

            @Override
            public void onFailure(Call<EventDetailResponse> call, Throwable t) {
                binding.noBookingDetailImg.setVisibility(View.VISIBLE);
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setEventData() {
//        Glide.with(getContext()).load(Constants.IMAGE_BASE_URL + list.getUser_details().getProfile_image()).thumbnail(Glide.with(getContext()).load(Constants.IMAGE_BASE_URL + Constants.THUMBNAILS + list.getUser_details().getProfile_image())).into(customerImage);

        try {
            if (eventData.getTarget_data().getImages() != null) {
                JSONArray jsonArray = new JSONArray(eventData.getTarget_data().getImages());
                if (jsonArray != null && jsonArray.length() > 0) {
                    Glide.with(getApplicationContext()).load(Constants.IMAGE_BASE_EVENT + jsonArray.get(0)).thumbnail(Glide.with(getApplicationContext()).load(Constants.IMAGE_BASE_EVENT + Constants.THUMBNAILS + jsonArray.get(0))).into(binding.customerImage);
                }
            }

        } catch (JSONException e) {

            Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();

        }

        binding.userName.setText(eventData.getUser_details().getName());
        binding.bookingIdText.setText("Booking ID : " + eventData.getId());
        binding.bookingPlaceName.setText(eventData.getEvent().getName());
        binding.eventText.setText("Event");
        String currentStringEnd = eventData.getEvent().getStart_date();


        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        final SimpleDateFormat sdfs = new SimpleDateFormat("hh:mm aa");
        Date dt = null, dtEnd;

        try {

            dt = sdf.parse(eventData.getEvent().getStart_time());

            String value = null;
            if (dt != null) {
                value = CommonMethods.parseDateToddMMyyyy(currentStringEnd) + " | " + sdfs.format(dt);
            }
            binding.bookingDateText.setText(value);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        binding.tiLocationText.setText(eventData.getEvent().getLocation());
        binding.tiBookingTicket.setText(eventData.getTickets() + " Attendies & Tickets (1 per person)");
        binding.tiTotalTicketPrice.setText(eventData.getTickets() + " Tickets @ $" + eventData.getEvent().getPrice() + " each");
        binding.tiTotalPrice.setText("$" + eventData.getPrice() + ".00");
        binding.tiTax.setText("$0.00");
        binding.totalAmount.setText("$" + eventData.getPrice() + ".00");

//        bottomSheetUpDown_address();
    }

    public void getSessionDetail() {
        progressDialog.show();
        Call<SessionBookingDetails> call = retrofitinterface.getBookingSession("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getApplicationContext()), Constants.CONTENT_TYPE, jobId);
        call.enqueue(new Callback<SessionBookingDetails>() {
            @Override
            public void onResponse(Call<SessionBookingDetails> call, Response<SessionBookingDetails> response) {
                if (response.body() != null) {
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData() != null) {
                            binding.noBookingDetailImg.setVisibility(View.GONE);
                            sessionData = response.body().getData();
                            setSessionData();

                        } else {
                            binding.noBookingDetailImg.setVisibility(View.VISIBLE);

                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();
                        binding.noBookingDetailImg.setVisibility(View.VISIBLE);

                    }
                } else {
                    binding.noBookingDetailImg.setVisibility(View.VISIBLE);

                    progressDialog.dismiss();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);

                        Toast.makeText(getApplicationContext(), "" + errorMessage, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {

                    }
                }

            }

            @Override
            public void onFailure(Call<SessionBookingDetails> call, Throwable t) {
                binding.noBookingDetailImg.setVisibility(View.VISIBLE);

                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setSessionData() {
//        Glide.with(getContext()).load(Constants.IMAGE_BASE_URL + sessionData.getUser_details().getProfile_image()).thumbnail(Glide.with(getContext()).load(Constants.IMAGE_BASE_URL + Constants.THUMBNAILS + sessionData.getUser_details().getProfile_image())).into(customerImage);
        try {
            if (sessionData.getSession().getImages() != null) {
                JSONArray jsonArray = new JSONArray(sessionData.getSession().getImages());
                if (jsonArray != null && jsonArray.length() > 0) {
                    Glide.with(getApplicationContext()).load(Constants.IMAGE_BASE_SESSION + jsonArray.get(0)).thumbnail(Glide.with(getApplicationContext()).load(Constants.IMAGE_BASE_SESSION + Constants.THUMBNAILS + jsonArray.get(0))).into(binding.customerImage);
                }
            }

        } catch (JSONException e) {

            Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();

        }

        binding.userName.setText(sessionData.getUser_details().getName());
        binding.bookingIdText.setText("Booking ID : " + sessionData.getId());
        binding.bookingPlaceName.setText(sessionData.getSession().getName());
        binding.eventText.setText("Session");
        String currentStringEnd = sessionData.getTarget_data().getStart_date();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        final SimpleDateFormat sdfs = new SimpleDateFormat("hh:mm aa");
        Date dt = null, dtEnd;


        try {
            dt = sdf.parse(sessionData.getTarget_data().getStart_time());
            String value = null;
            if (dt != null) {
                value = CommonMethods.parseDateToddMMyyyy(currentStringEnd) + " | " + sdfs.format(dt);
            }
            binding.bookingDateText.setText(value);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        binding.tiLocationText.setText(sessionData.getSession().getLocation());
        binding.tiBookingTicket.setText(sessionData.getTickets() + " Attendies & Tickets (1 per person)");
        binding.tiTotalTicketPrice.setText(sessionData.getTickets() + " Tickets @ $" + sessionData.getSession().getHourly_rate() + " each");
        binding.tiTotalPrice.setText("$" + sessionData.getPrice() + ".00");
        binding.tiTax.setText("$0.00");
        binding.totalAmount.setText("$" + sessionData.getPrice() + ".00");
//        bottomSheetUpDown_address();
    }

    public void getSpaceDetails() {
        progressDialog.show();
        Call<SpaceBookingDetailResponse> call = retrofitinterface.getBookingSpace("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getApplicationContext()), Constants.CONTENT_TYPE, jobId);
        call.enqueue(new Callback<SpaceBookingDetailResponse>() {
            @Override
            public void onResponse(Call<SpaceBookingDetailResponse> call, Response<SpaceBookingDetailResponse> response) {
                if (response.body() != null) {
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData() != null) {
                            binding.noBookingDetailImg.setVisibility(View.GONE);
                            spaceData = response.body().getData();
                            getSpaceAmount();
                        } else {
                            binding.noBookingDetailImg.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();
                        binding.noBookingDetailImg.setVisibility(View.VISIBLE);
                    }
                } else {
                    binding.noBookingDetailImg.setVisibility(View.VISIBLE);
                    progressDialog.dismiss();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);
                        Toast.makeText(getApplicationContext(), "" + errorMessage, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {

                    }
                }

            }

            @Override
            public void onFailure(Call<SpaceBookingDetailResponse> call, Throwable t) {
                binding.noBookingDetailImg.setVisibility(View.VISIBLE);
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getSpaceAmount() {
        if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, getApplicationContext()).equalsIgnoreCase(Constants.Coach) || CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, getApplicationContext()).equalsIgnoreCase(Constants.Organizer)) {
//            Glide.with(getContext()).load(Constants.IMAGE_BASE_URL + spaceData.getUser_details().getProfile_image()).thumbnail(Glide.with(getContext()).load(Constants.IMAGE_BASE_URL + Constants.THUMBNAILS + spaceData.getUser_details().getProfile_image())).into(customerImage);
            try {
                if (spaceData.getTarget_data().getImages() != null) {
                    JSONArray jsonArray = new JSONArray(spaceData.getTarget_data().getImages());
                    if (jsonArray != null && jsonArray.length() > 0) {
                        Glide.with(getApplicationContext()).load(Constants.IMAGE_BASE_PLACE + jsonArray.get(0)).thumbnail(Glide.with(getApplicationContext()).load(Constants.IMAGE_BASE_PLACE + Constants.THUMBNAILS + jsonArray.get(0))).into(binding.customerImage);
                    }
                }

            } catch (JSONException e) {

                Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();

            }

            binding.userName.setText(spaceData.getUser_details().getName());
            binding.bookingIdText.setText("Booking ID : " + spaceData.getId());
            binding.bookingPlaceName.setText(spaceData.getTarget_data().getName());
            binding.eventText.setText("Space");

            binding.tiLocationText.setText(spaceData.getTarget_data().getLocation());
            binding.tiBookingTicket.setVisibility(View.GONE);
            binding.tiTickets.setVisibility(View.GONE);
            binding.tiDate.setVisibility(View.GONE);

//        ti_Booking_Ticket.setText(spaceData.getTickets() + " Attendies & Tickets (1 per person)");
            binding.tiTotalTicketPrice.setText("Space @ $" + spaceData.getTarget_data().getPrice_hourly() + " /hour");
            binding.tiTotalPrice.setText("$" + spaceData.getPrice() + ".00");
            binding.tiTax.setText("$0.00");
            binding.totalAmount.setText("$" + spaceData.getPrice() + ".00");
            setChips();
//            binding.bottomSheetUpDown_address();
        } else {
//        Glide.with(getContext()).load(Constants.IMAGE_BASE_URL + spaceData.getUser_details().getProfile_image()).thumbnail(Glide.with(getContext()).load(Constants.IMAGE_BASE_URL + Constants.THUMBNAILS + spaceData.getUser_details().getProfile_image())).into(customerImage);

            try {
                if (spaceData.getSpace().getImages() != null) {
                    JSONArray jsonArray = new JSONArray(spaceData.getSpace().getImages());
                    if (jsonArray != null && jsonArray.length() > 0) {
                        Glide.with(getApplicationContext()).load(Constants.IMAGE_BASE_PLACE + jsonArray.get(0)).thumbnail(Glide.with(getApplicationContext()).load(Constants.IMAGE_BASE_PLACE + Constants.THUMBNAILS + jsonArray.get(0))).into(binding.customerImage);
                    }
                }

            } catch (JSONException e) {

                Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();

            }

            binding.userName.setText(spaceData.getUser_details().getName());
            binding.bookingIdText.setText("Booking ID : " + spaceData.getId());
            binding.bookingPlaceName.setText(spaceData.getSpace().getName());
            binding.eventText.setText("Space");

            binding.tiLocationText.setText(spaceData.getSpace().getLocation());
            binding.tiBookingTicket.setVisibility(View.GONE);
            binding.tiTickets.setVisibility(View.GONE);
            binding.tiDate.setVisibility(View.GONE);

//        ti_Booking_Ticket.setText(spaceData.getTickets() + " Attendies & Tickets (1 per person)");
            binding.tiTotalTicketPrice.setText("Space @ $" + spaceData.getSpace().getPrice_hourly() + " /hour");
            binding.tiTotalPrice.setText("$" + spaceData.getPrice() + ".00");
            binding.tiTax.setText("$0.00");
            binding.totalAmount.setText("$" + spaceData.getPrice() + ".00");
            setChips();
//            bottomSheetUpDown_address();
        }
    }

    private void setChips() {
        binding.spaceDaysLayout.removeAllViews();
        chipSpaceGroup = new ChipGroup(this);
        for (SpaceBookingDetailResponse.DataBean.BookingDetailsBean selectDays : spaceData.getBooking_details()) {
            Chip chip = new Chip(this);
//            chip.setEnabled(true);
            ChipDrawable chipDrawable = ChipDrawable.createFromAttributes(this, null, 0, R.style.Widget_MaterialComponents_Chip_Filter);
            chip.setChipDrawable(chipDrawable);
            chip.setTextColor(getResources().getColor(R.color.colorWhite));
//            chip.setMaxWidth(200);
            String currentStringEnd = selectDays.getBooking_date();
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
            final SimpleDateFormat sdfs = new SimpleDateFormat("hh:mm aa");
            Date dt = null, dtEnd;


            try {
                dt = sdf.parse(selectDays.getTo_time());
                String value = null;
                if (dt != null) {
                    value = CommonMethods.parseDateToddMMyyyy(currentStringEnd) + " | " + sdfs.format(dt);
                }
                chip.setText(value);
//                binding.bookingDateText.setText(value);
            } catch (ParseException e) {
                e.printStackTrace();
            }
//            chip.setText(selectDays.getBooking_date()+"\n "+"Time:"+selectDays.getFrom_time()+" To "+selectDays.getTo_time());
            chip.setTag(selectDays.getId());
            chip.setChipBackgroundColorResource(R.color.gradientDarkColor);
            chip.setCheckable(false);
//            chip.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    String id=view.getId()+"";
//
//                    selectDays.setChecked(!selectDays.isChecked());
//                    if (selectDays.isChecked()) {
//                        chip.setChipBackgroundColorResource(R.color.colorGreen);
//                    } else {
//                        chip.setChipBackgroundColorResource(R.color.lightGrayFont);
//                    }
//
//                }
//            });
            chipSpaceGroup.addView(chip);
        }
//        chipSpaceGroup.setEnabled(true);
        chipSpaceGroup.setChipSpacingVertical(20);
        binding.spaceDaysLayout.addView(chipSpaceGroup);
    }

}
