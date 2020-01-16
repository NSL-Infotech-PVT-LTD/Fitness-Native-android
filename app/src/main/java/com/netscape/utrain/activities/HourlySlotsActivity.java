package com.netscape.utrain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;
import android.widget.Toolbar;

import com.netscape.utrain.R;
import com.netscape.utrain.adapters.HourlySlotAdapter;
import com.netscape.utrain.model.AllBookingListModel;
import com.netscape.utrain.model.HourSelectedModel;
import com.netscape.utrain.model.SlotModels;
import com.netscape.utrain.response.SlotListResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CalendarDialog;
import com.netscape.utrain.utils.CalendarView;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.views.RobotoCalendarView;

import org.json.JSONObject;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HourlySlotsActivity extends AppCompatActivity implements RobotoCalendarView.RobotoCalendarListener {
    //    private ActivityHourlySlotsBinding binding;
    private Date strDate = null;
    private Date selectedDate = null;
    private Date oldDates = null;
    private String startDate = "", sDate = "";
    private ProgressDialog progressDialog;
    private CommonMethods commonMethods;
    private Retrofitinterface retrofitinterface;
    private ArrayList<SlotModels> slotListApi;
    private ArrayList<SlotListResponse.DataBean> slotList;
    private RecyclerView.LayoutManager layoutManager;
    private HourlySlotAdapter adapter;
    private String spaceId = "";
    private List<HourSelectedModel> hoursSelected;
    private RobotoCalendarView roboCalView;
    private String[] mShortMonths;
    private CalendarDialog mCalendarDialog;
    private RecyclerView hourlyRecycler;
    private AppCompatImageView backArrowClick;
    private String slotsForDate = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly_slots);
        mShortMonths = new DateFormatSymbols().getShortMonths();


//        binding = DataBindingUtil.setContentView(this, R.layout.activity_hourly_slots);
        roboCalView = findViewById(R.id.roboCalHours);
        hourlyRecycler = findViewById(R.id.hourRecyler);
        backArrowClick = findViewById(R.id.backArrowClick);
        init();

    }

    private void init() {
        if (getIntent().getExtras() != null) {
            spaceId = getIntent().getStringExtra("event_id");
        }
        commonMethods = new CommonMethods();
        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);
        layoutManager = new LinearLayoutManager(this);
        hourlyRecycler.setLayoutManager(layoutManager);
//        binding.dateLayout.setOnClickListener(this);
        hoursSelected = new ArrayList<>();
        for (int i = 1; i < 25; i++) {
            HourSelectedModel model = new HourSelectedModel();
//            if (i >= 12 && i< 24) {
            model.setHour(commonMethods.convertDate(i));
//            } else {
//                model.setHour(i+":00");
//            }
            model.setSelected(false);
            hoursSelected.add(model);
        }

        backArrowClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        roboCalView.setRobotoCalendarListener(HourlySlotsActivity.this);

        roboCalView.setShortWeekDays(false);

        roboCalView.showDateTitle(true);

        roboCalView.setDate(new Date());


    }


    private void hitSpaceDetailAPI(String date) {

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading..");
        progressDialog.show();
        Call<SlotListResponse> signUpAthlete = retrofitinterface.getTimeSlots(Constants.CONTENT_TYPE, "Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, HourlySlotsActivity.this), spaceId, date);
        signUpAthlete.enqueue(new Callback<SlotListResponse>() {
            @Override
            public void onResponse(Call<SlotListResponse> call, Response<SlotListResponse> response) {
                slotList = new ArrayList<>();
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().isStatus()) {
                        if (response.body().getData() != null && response.body().getData().getAvailable_slot().size() > 0) {
//                            binding.hourlyRecycler.removeAllViews();
                            hourlyRecycler.setVisibility(View.VISIBLE);
                            slotList.add(response.body().getData());
                            getSlotsFromArray();


                        } else{
                            hourlyRecycler.setVisibility(View.VISIBLE);
                            adapter = new HourlySlotAdapter(getApplicationContext(), hoursSelected, slotsForDate, spaceId);
                            hourlyRecycler.setAdapter(adapter);
                        }
                    }
                } else {
                    progressDialog.dismiss();
                    hourlyRecycler.setVisibility(View.GONE);
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);
//                        Snackbar.make(binding.maineventBooking, errorMessage.toString(), BaseTransientBottomBar.LENGTH_LONG).show();
                        Toast.makeText(HourlySlotsActivity.this, "" + errorMessage, Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        Toast.makeText(HourlySlotsActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
//                        Snackbar.make(binding.athleteLoginLayout,e.getMessage().toString(), BaseTransientBottomBar.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SlotListResponse> call, Throwable t) {
                progressDialog.dismiss();
                hourlyRecycler.setVisibility(View.GONE);
                Toast.makeText(HourlySlotsActivity.this, "" + getResources().getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
//                Snackbar.make(binding.maineventBooking, getResources().getString(R.string.something_went_wrong), BaseTransientBottomBar.LENGTH_LONG).show();
            }
        });
    }

    private void getSlotsFromArray() {
//        slotListApi = new ArrayList<>();

        if (slotList != null && slotList.size() > 0) {
            for (int i = 0; i < slotList.get(0).getAvailable_slot().size(); i++) {
                SlotModels models = new SlotModels();
//                models.setSlotStartTime(slotList.get(0).getAvailable_slot().get(i).get(0));
                String slotStartTime = commonMethods.getSplitedValue(slotList.get(0).getAvailable_slot().get(i).get(0), ":");
                String slotEndTime = commonMethods.getSplitedValue(slotList.get(0).getAvailable_slot().get(i).get(1), ":");
                if (Integer.parseInt(slotStartTime) <= Integer.parseInt(slotEndTime)) {
                    for (int j = Integer.parseInt(slotStartTime); j <= Integer.parseInt(slotEndTime); j++) {
                        HourSelectedModel model = new HourSelectedModel();
                        model.setSelected(true);
                        model.setHour(commonMethods.convertDate(j));
                        hoursSelected.set((j - 1), model);
//                        SlotModels hours = new SlotModels();
//                        hours .setSlotStartTime(j+":00");
//                        slotListApi.add(hours );
                    }

                }
//                models.setSlotStartTime(slotList.get(0).getAvailable_slot().get(i).get(0));
//                slotListApi.add(models);

            }
            adapter = new HourlySlotAdapter(getApplicationContext(), hoursSelected, slotsForDate, spaceId);
            hourlyRecycler.setAdapter(adapter);
        }
    }

    @Override
    public void onDayClick(Date date) {
        slotsForDate = commonMethods.getDateInStringFormat(date);
        if (date.getTime() > System.currentTimeMillis()) {
            hitSpaceDetailAPI(slotsForDate);
        }else {
            Toast.makeText(this, "Select valid date", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onDayLongClick(Date date) {
        Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRightButtonClick() {
        hourlyRecycler.setVisibility(View.GONE);
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onLeftButtonClick() {
        hourlyRecycler.setVisibility(View.GONE);
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();

    }

//    @Override
//    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.dateLayout:
////                getStartDate();
//                break;
////            case R.id.dateLayout:
////                break;
//        }
//
//    }


}
