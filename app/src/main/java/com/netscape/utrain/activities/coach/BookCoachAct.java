package com.netscape.utrain.activities.coach;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.HourlySlotsActivity;
import com.netscape.utrain.activities.PaymentActivity;
import com.netscape.utrain.activities.TopCoachOrgDetailActivity;
import com.netscape.utrain.activities.ViewCoachStaffListActivity;
import com.netscape.utrain.activities.athlete.AllEventsMapAct;
import com.netscape.utrain.adapters.HourlySlotAdapter;
import com.netscape.utrain.adapters.ServicesBottomSheetAdapter;
import com.netscape.utrain.databinding.ActivityBookCoachBinding;
import com.netscape.utrain.model.CoachDetailAny;
import com.netscape.utrain.model.CoachListModel;
import com.netscape.utrain.model.SlotModel;
import com.netscape.utrain.model.SportListModel;
import com.netscape.utrain.response.SlotListResponse;
import com.netscape.utrain.response.ViewCoachListResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookCoachAct extends AppCompatActivity implements View.OnClickListener {
    ChipGroup chipGroup;

    ActivityBookCoachBinding binding;

    BookCoachAct act;
    private int type;
    private Retrofitinterface retrofitinterface;

    private ImageView backArrow;
    private TextView title;
    private String coachId;
    private int totalPrice = 0;
    private List<String> serviceList = new ArrayList<>();
    private JsonArray jsonArray, bookingJson;
    private List<SlotModel> bookingSlotDateTime = new ArrayList<>();

    private int totalHour, startingHour, endHour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_book_coach);
        chipGroup = new ChipGroup(this);
        chipGroup.setSingleSelection(false);

        binding.proceedToPay.setOnClickListener(this);
        act = this;
        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);


        init();

        binding.viewOrgBtnLayout.setVisibility(View.GONE);
//                binding.view2.setVisibility(View.GONE);
        binding.btnView.setVisibility(View.GONE);
        String slit[] = getIntent().getStringExtra("end").split(":");
        String slit1[] = getIntent().getStringExtra("start").split(":");
        endHour = Integer.parseInt(slit[0]);
        startingHour = Integer.parseInt(slit1[0]);

        totalHour = endHour - startingHour;
        binding.toTimeTv.setText(endHour + ":00");

        binding.viewEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                getEndTime();
//                Intent intent = new Intent(act, AllEventsMapAct.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                intent.putExtra("from", "1");
//                intent.putExtra("coach_id", getIntent().getStringExtra("coachID") + "");
//                startActivity(intent);
            }
        });


    }

    private void getEndTime() {
        Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(act, new TimePickerDialog.OnTimeSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                endHour = hourOfDay;

                if (startingHour < endHour) {
                    totalHour = endHour - startingHour;
                    binding.toTimeTv.setText(endHour + ":00");
                    setDataToAdapter(getIntent());

                    binding.bsProductPriceTv.setText("$" + (totalPrice*totalHour));
                    binding.totalPriceTv.setText("$" + (totalPrice*totalHour));
                    binding.bsGstTv.setText("$0" + "");
                } else
                    Toast.makeText(act, "Please Select Valid Time", Toast.LENGTH_SHORT).show();


            }
        }, mHour, mMinute, true);
        timePickerDialog.show();
    }


    private void init() {

        title = findViewById(R.id.topDetailsTitleTv);
        backArrow = findViewById(R.id.detailBackArrow);

        setData();
        setDataToAdapter(getIntent());
        backArrow.setOnClickListener(this);
//        binding.viewBookings.setOnClickListener(this);
    }

    private void setData() {


        getCochDetail(getIntent().getStringExtra("coachID"));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.detailBackArrow:
                finish();
                break;
            case R.id.proceedToPay:
                Intent intent = new Intent(act, PaymentActivity.class);
                intent.putExtra("price", (totalPrice*totalHour) + "");
                intent.putExtra("services", jsonArray.toString());
                intent.putExtra("slot", bookingJson.toString());
                intent.putExtra("coachID", coachId);
                intent.putExtra("type", "coachBook");
                startActivity(intent);
                break;

        }

    }


    private void getCochDetail(String id) {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading..");
        progressDialog.show();
        Call<CoachDetailAny> signUpAthlete = retrofitinterface.getCoachDetail(Constants.CONTENT_TYPE, id);
        signUpAthlete.enqueue(new Callback<CoachDetailAny>() {
            @Override
            public void onResponse(Call<CoachDetailAny> call, Response<CoachDetailAny> response) {

                progressDialog.dismiss();

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().isStatus()) {
                            coachId = response.body().getData().getId() + "";
                            binding.cYearsOfExpTv.setText(response.body().getData().getExpertise_years() + " Year ");
                            binding.detailUserBioTv.setText(response.body().getData().getBio());
                            binding.detailPriceTv.setText("$" + response.body().getData().getHourly_rate() + "");
                            binding.eventTimeDetailTv.setText(getIntent().getStringExtra("start"));
                            binding.eventDateDetailTv.setText(response.body().getData().getExpertise_years() + " Year");
                            binding.detailUserName.setText(response.body().getData().getName());
                            binding.discoverRating.setRating(Float.parseFloat(response.body().getData().getRating()));
                            if (response.body().getData().getRating() != null) {
                                if (response.body().getData().getRating().equalsIgnoreCase("0")) {
                                    binding.noRatingText.setVisibility(View.VISIBLE);
                                    binding.discoverRating.setVisibility(View.GONE);
                                } else {
                                    binding.discoverRating.setVisibility(View.VISIBLE);
                                    binding.noRatingText.setVisibility(View.GONE);
                                    binding.discoverRating.setRating(Float.parseFloat(response.body().getData().getRating()));
                                }
                            }

                            Glide.with(act).load(Constants.COACH_IMAGE_BASE_URL + response.body().getData().getProfile_image()).thumbnail(Glide.with(act).load(Constants.COACH_IMAGE_BASE_URL + Constants.THUMBNAILS + response.body().getData().getProfile_image())).into(binding.detailImage);

                            binding.discoverRating.setRating(Float.parseFloat(response.body().getData().getRating()));


                            for (int i = 0; i < response.body().getData().getService_ids().size(); i++) {
                                final Chip chip = new Chip(act);
                                chip.setEnabled(true);
                                ChipDrawable chipDrawable = ChipDrawable.createFromAttributes(act, null, 0, R.style.Widget_MaterialComponents_Chip_Filter);
                                chip.setChipDrawable(chipDrawable);
                                chip.setTextColor(getResources().getColor(R.color.colorWhite));
                                chip.setText(response.body().getData().getService_ids().get(i).getName() + " $" + response.body().getData().getService_ids().get(i).getPrice());
                                chip.setTag(response.body().getData().getService_ids().get(i).getId());
                                if (response.body().getData().getService_ids().get(i).isChecked()) {
                                    chip.setChipBackgroundColor(getResources().getColorStateList(R.color.colorGreen));
                                    chip.setChecked(true);

                                }
                                int finalI = i;
                                chip.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        response.body().getData().getService_ids().get(finalI).setChecked((!response.body().getData().getService_ids().get(finalI).isChecked()));

//
                                        if (response.body().getData().getService_ids().get(finalI).isChecked()) {

                                            chip.setChipBackgroundColor(getResources().getColorStateList(R.color.colorGreen));
                                            totalPrice += (Integer.parseInt(response.body().getData().getService_ids().get(finalI).getPrice()));
                                            serviceList.add(response.body().getData().getService_ids().get(finalI).getId() + "");
                                        } else {
                                            totalPrice -= (Integer.parseInt(response.body().getData().getService_ids().get(finalI).getPrice()));
                                            chip.setChipBackgroundColor(getResources().getColorStateList(R.color.colorLightGray));
                                            serviceList.remove(response.body().getData().getService_ids().get(finalI).getId() + "");
                                        }

                                        binding.bsProductPriceTv.setText("$" + (totalPrice*totalHour));
                                        binding.totalPriceTv.setText("$" + (totalPrice*totalHour));
                                        binding.bsGstTv.setText("$0" + "");

                                        if (totalPrice == 0) {
                                            binding.proceedToPay.setVisibility(View.GONE);
                                        } else {
                                            binding.proceedToPay.setVisibility(View.VISIBLE);

                                        }

                                        jsonArray = (JsonArray) new Gson().toJsonTree(serviceList);

                                    }
                                });
                                chipGroup.addView(chip);
                            }
                            chipGroup.setEnabled(false);
                            chipGroup.setChipSpacingVertical(20);
                            binding.constraintChipGroup.addView(chipGroup);
                        }
                    }
                } else {
                    progressDialog.dismiss();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);
//                        Snackbar.make(binding.maineventBooking, errorMessage.toString(), BaseTransientBottomBar.LENGTH_LONG).show();
                        Toast.makeText(act, "" + errorMessage, Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        Toast.makeText(act, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
//                        Snackbar.make(binding.athleteLoginLayout,e.getMessage().toString(), BaseTransientBottomBar.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CoachDetailAny> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(act, "" + getResources().getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
//                Snackbar.make(binding.maineventBooking, getResources().getString(R.string.something_went_wrong), BaseTransientBottomBar.LENGTH_LONG).show();
            }
        });
    }

    public void setDataToAdapter(Intent data) {
        SlotModel slotModel = new SlotModel();
        slotModel.setFrom_time(data.getStringExtra("start"));
        slotModel.setTo_time(endHour + ":00");
        String date = (data.getStringExtra("date")).replace("-", "/");
        slotModel.setBooking_date(date);
        bookingSlotDateTime.add(slotModel);
        bookingJson = (JsonArray) new Gson().toJsonTree(bookingSlotDateTime);


    }
//   if (coachListModel.getService_ids().get(i).isChecked()) {
//                        chip.setChipBackgroundColor(getResources().getColorStateList(R.color.colorGreen));
//                        chip.setChecked(true);
//
//                    }
//                    int finalI = i;
//                    chip.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//
//                            coachListModel.getService_ids().get(finalI).setChecked((!coachListModel.getService_ids().get(finalI).isChecked()));
//
////
//                            if (coachListModel.getService_ids().get(finalI).isChecked()) {
//                                chip.setChipBackgroundColor(getResources().getColorStateList(R.color.colorGreen));
//                                Toast.makeText(TopCoachOrgDetailActivity.this, "" + coachListModel.getService_ids().get(finalI).getPrice(), Toast.LENGTH_SHORT).show();
//                            } else {
//                                chip.setChipBackgroundColor(getResources().getColorStateList(R.color.grayColor));
//                            }
//                        }
//                    });

}



