package com.netscape.utrain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.netscape.utrain.R;
import com.netscape.utrain.adapters.HourlySlotAdapter;
import com.netscape.utrain.databinding.ActivityHourlySlotsBinding;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HourlySlotsActivity extends AppCompatActivity implements RobotoCalendarView.RobotoCalendarListener {
    private ActivityHourlySlotsBinding binding;
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
    private String spaceId="";
    private List<HourSelectedModel>hoursSelected;
    private RobotoCalendarView roboCalView;
    private String[] mShortMonths;
    private CalendarDialog mCalendarDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_hourly_slots);
        mShortMonths = new DateFormatSymbols().getShortMonths();


        binding = DataBindingUtil.setContentView(this, R.layout.activity_hourly_slots);
        roboCalView=findViewById(R.id.roboCalHours);
        init();

    }

    private void init() {
        if (getIntent().getExtras()!=null){
            spaceId=getIntent().getStringExtra("event_id");
        }
        commonMethods = new CommonMethods();
        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);
        layoutManager=new LinearLayoutManager(this);
        binding.hourRecyler.setLayoutManager(layoutManager);
//        binding.dateLayout.setOnClickListener(this);
        hoursSelected=new ArrayList<>();
        for (int i=1;i<25;i++){
            HourSelectedModel model=new HourSelectedModel();
            if (i >= 12 && i< 24) {
                model.setHour(i+"Pm");
            } else {
                model.setHour(i+"Am");
            }
            model.setSelected(false);
            hoursSelected.add(model);
        }





//        binding.calendarView.setOnItemClickedListener(new CalendarView.OnItemClickListener() {
//            @Override
//            public void onItemClicked(List<CalendarView.CalendarObject> calendarObjects,
//                                      Calendar previousDate,
//                                      Calendar selectedDate) {
////                if (calendarObjects.size() != 0) {
//                mCalendarDialog.setSelectedDate(selectedDate);
////                mCalendarDialog.show();
////                displaySupplierList(calendarObjects);
//            }
////                else {
////                    if (diffYMD(previousDate, selectedDate) == 0)
////                        createEvent(selectedDate);
////                }
////            }
//        });
//        backArrow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//        binding.calendarView.setOnMonthChangedListener(new CalendarView.OnMonthChangedListener() {
//            @Override
//            public void onMonthChanged(int month, int year) {
//                Toast.makeText(HourlySlotsActivity.this, month + "-" + year, Toast.LENGTH_SHORT).show();
//            }
//        });


//        if (getSupportActionBar() != null) {
//            int month = binding.calendarView.getCurrentDate().get(Calendar.MONTH);
//            int year = binding.calendarView.getCurrentDate().get(Calendar.YEAR);
//            getSupportActionBar().setTitle(mShortMonths[month]);
//            getSupportActionBar().setSubtitle(Integer.toString(year));
//        }


//        mCalendarDialog = CalendarDialog.Builder.instance(this)
//                .setOnItemClickListener(new CalendarDialog.OnCalendarDialogListener() {
//                    @Override
//                    public void onEventClick(AllBookingListModel.DataBeanX.DataBean event) {
////                         onEventSelected(event);
//                    }
//
//                    @Override
//                    public void onCreateEvent(Calendar calendar) {
////                        createEvent(calendar);
//                    }
//                })
//                .create();




        roboCalView.setRobotoCalendarListener(HourlySlotsActivity.this);

        roboCalView.setShortWeekDays(false);

        roboCalView.showDateTitle(true);

        roboCalView.setDate(new Date());


    }

//    private void getStartDate() {
////        binding.constraintChipGroup.setVisibility(View.GONE);
////        binding.selectTimeSlotText.setVisibility(View.GONE);
////        binding.editTimeLaout.setVisibility(View.GONE);
//        Calendar cal = Calendar.getInstance();
//        int mYear = cal.get(Calendar.YEAR);
//        int mMonth = cal.get(Calendar.MONTH);
//        int mDay = cal.get(Calendar.DAY_OF_MONTH);
//        DatePickerDialog datePickerDialog = new DatePickerDialog(HourlySlotsActivity.this, new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
//                binding.selectDate.setPadding(20, 20, 20, 20);
//                sDate = year + "-" + commonMethods.convertDate((monthOfYear + 1)) + "-" + commonMethods.convertDate(dayOfMonth);
//                startDate = year + "/" + commonMethods.convertDate((monthOfYear + 1)) + "/" + commonMethods.convertDate(dayOfMonth);
////                selected = false;
//                selectedDate = commonMethods.formatDate(startDate);
//                if (selectedDate.getTime() > System.currentTimeMillis()) {
//                    binding.selectDate.setText(startDate);
//                    hitSpaceDetailAPI();
//                } else {
//                    binding.selectDate.setText("");
//                    binding.selectDate.setHint(getResources().getString(R.string.selecte_date_of_service));
//                    Toast.makeText(HourlySlotsActivity.this, "Can't create event for current date", Toast.LENGTH_SHORT).show();
//                }
//
////                    binding.createEventEndDatetv.setText("");
////                    binding.createEventEndDatetv.setHint("End date");
////                } else {
////                    binding.createEventStartDateTv.setText("");
////                    binding.createEventStartDateTv.setHint("Start date");
////                    Toast.makeText(SelectTimeSlot.this, "Can't create event for current date", Toast.LENGTH_SHORT).show();
////                }
//
//            }
//        }, mYear, mMonth, mDay);
//        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
//        datePickerDialog.show();
//    }

    private void hitSpaceDetailAPI() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading..");
        progressDialog.show();
        Call<SlotListResponse> signUpAthlete = retrofitinterface.getTimeSlots(Constants.CONTENT_TYPE, "Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, HourlySlotsActivity.this), spaceId, sDate);
        signUpAthlete.enqueue(new Callback<SlotListResponse>() {
            @Override
            public void onResponse(Call<SlotListResponse> call, Response<SlotListResponse> response) {
                slotList = new ArrayList<>();
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().isStatus()) {
                        if (response.body().getData() != null && response.body().getData().getAvailable_slot().size() > 0) {
//                            binding.hourlyRecycler.removeAllViews();
                            slotList.add(response.body().getData());
                            getSlotsFromArray();


                        }
                    }
                } else {
                    progressDialog.dismiss();
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
                Toast.makeText(HourlySlotsActivity.this, "" + getResources().getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
//                Snackbar.make(binding.maineventBooking, getResources().getString(R.string.something_went_wrong), BaseTransientBottomBar.LENGTH_LONG).show();
            }
        });
    }

    private void getSlotsFromArray() {
        slotListApi = new ArrayList<>();

        if (slotList != null && slotList.size() > 0) {
            for (int i = 0; i < slotList.get(0).getAvailable_slot().size(); i++) {
                SlotModels models = new SlotModels();
//                models.setSlotStartTime(slotList.get(0).getAvailable_slot().get(i).get(0));
                String slotStartTime=commonMethods.getSplitedValue(slotList.get(0).getAvailable_slot().get(i).get(0).toString(),":");
                String slotEndTime=commonMethods.getSplitedValue(slotList.get(0).getAvailable_slot().get(i).get(1).toString(),":");
                if (Integer.parseInt(slotStartTime)<= Integer.parseInt(slotEndTime)) {
                    for (int j=Integer.parseInt(slotStartTime);j<=Integer.parseInt(slotEndTime);j++){
                        HourSelectedModel model=new HourSelectedModel();
                        model.setSelected(true);
                        model.setHour(j+"Am");
                        hoursSelected.set((j-1),model);
//                        SlotModels hours = new SlotModels();
//                        hours .setSlotStartTime(j+":00");
//                        slotListApi.add(hours );
                    }

                }
//                models.setSlotStartTime(slotList.get(0).getAvailable_slot().get(i).get(0));
//                slotListApi.add(models);

            }
            adapter=new HourlySlotAdapter(getApplicationContext(),hoursSelected);
            binding.hourRecyler.setAdapter(adapter);
        }
    }

    @Override
    public void onDayClick(Date date) {
        Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDayLongClick(Date date) {
        Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRightButtonClick() {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onLeftButtonClick() {
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
