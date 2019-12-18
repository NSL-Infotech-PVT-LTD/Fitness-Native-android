package com.netscape.utrain.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.athlete.EventDetail;
import com.netscape.utrain.activities.organization.OrgMapFindAddressActivity;
import com.netscape.utrain.databinding.ActivitySelecteTimeSlotBinding;
import com.netscape.utrain.model.ServiceListDataModel;
import com.netscape.utrain.model.SlotModels;
import com.netscape.utrain.response.SlotListResponse;
import com.netscape.utrain.response.SpaceDetailResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PrefrenceConstant;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class SelecteTimeSlot extends AppCompatActivity implements View.OnClickListener{
    private ActivitySelecteTimeSlotBinding binding;
    private ArrayList<ServiceListDataModel> sList = new ArrayList<>();
    private ArrayList<String> timeSlotList = new ArrayList<>();
    ChipGroup chipGroup;
    private String selectedSlot="";
    private String sDate="",startDate="",timeSlotSelected="";
    private ProgressDialog progressDialog;
    private Retrofitinterface retrofitinterface;
    private String spaceId="";
    private Chip chipSelected;
    private boolean selected=false;
    private ArrayList<SlotListResponse.DataBean>slotList;
    private ArrayList<SlotModels>slotListApi;
    private String previewSlot="";
    private int mYear, mMonth, mDay, mHour, mMinute;
    private String endTime = "";
    private String startTime = "";
    private String chipStartTime="",chipEndtime="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_selecte_time_slot);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_selecte_time_slot);
        retrofitinterface= RetrofitInstance.getClient().create(Retrofitinterface.class);
        spaceId=getIntent().getStringExtra("bookSpaceId");
        chipGroup = new ChipGroup(this);
        chipGroup.setSingleSelection(true);
        binding.dateLayout.setOnClickListener(this);
        binding.bottomConstraint.setOnClickListener(this);
        binding.closeSlotDialog.setOnClickListener(this);
        binding.startHour.setOnClickListener(this);
        binding.endHour.setOnClickListener(this);
        init();
        getService();
        hitSpaceDetailAPI(spaceId);
//        setChips();
    }

    private void init() {
//        timeSlotList.add("Select No. of Hours");
//        timeSlotList.add("1 Hour");
//        timeSlotList.add("2 Hour");
//        timeSlotList.add("3 Hour");
//        timeSlotList.add("4 Hour");
//        timeSlotList.add("5 Hour");
//        timeSlotList.add("6 Hour");
//        timeSlotList.add("7 Hour");
//        timeSlotList.add("8 Hour");
//        timeSlotList.add("9 Hour");
//        timeSlotList.add("10 Hour");
//        ArrayAdapter endWeekAdapter = new ArrayAdapter(SelecteTimeSlot.this, android.R.layout.simple_spinner_item, timeSlotList);
//        endWeekAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        binding.timeSlotDropDown.setAdapter(endWeekAdapter);
//
//        binding.timeSlotDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if (i==0){
//                    selectedSlot="";
//                }else {
//                    binding.timeSlotDropDown.setSelection(i);
//                    selectedSlot = timeSlotList.get(i);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });






        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                if (selected){
                    if (chipSelected !=null) {
                        chipSelected.setChipBackgroundColor(ColorStateList.valueOf(
                                ContextCompat.getColor(SelecteTimeSlot.this, R.color.lightGrayFont)));
                        timeSlotSelected="";
                    }
                }
                chipSelected = chipGroup.findViewById(checkedId);
                if (chipSelected !=null){
                    chipSelected.setChipBackgroundColor( ColorStateList.valueOf(
                            ContextCompat.getColor(SelecteTimeSlot.this, R.color.colorAccent)));
                    selected=true;
                    timeSlotSelected=chipSelected.getText().toString();
                    Toast.makeText(SelecteTimeSlot.this, ""+chipSelected.getText().toString(), Toast.LENGTH_SHORT).show();
                    chipStartTime=getSlotValue(chipSelected.getText().toString(),false);
                    chipEndtime=getSlotValue(chipSelected.getText().toString(),true);
                    setValue();
                }else{
                    selected=false;
                    chipEndtime="";
                    chipStartTime="";
                    startTime="";
                    endTime="";
//                    Toast.makeText(SelecteTimeSlot.this, ""+chipSelected.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setValue() {
        startTime=chipStartTime;
        endTime=chipEndtime;
        binding.startHour.setText(chipStartTime);
        binding.endHour.setText(chipEndtime);
    }

    private void getService() {
        String serviceName = CommonMethods.getPrefData(PrefrenceConstant.SERVICE_IDS, getApplicationContext());
        Gson gson = new Gson();

        if (serviceName != null) {
            if (serviceName.isEmpty()) {
                Toast.makeText(SelecteTimeSlot.this, "Service Not Found", Toast.LENGTH_SHORT).show();
            } else {
                Type type = new TypeToken<List<ServiceListDataModel>>() {
                }.getType();
                sList = gson.fromJson(serviceName, type);
                sList.addAll(sList);
                sList.addAll(sList);

                StringBuilder builder = new StringBuilder();
                for (ServiceListDataModel details : sList) {
                    builder.append(details.getName() + "\n");

                }

            }
        }
    }
    private void setChips() {
        for (SlotModels slots : slotListApi) {
            final Chip chip = new Chip(this);
            chip.setEnabled(true);
            ChipDrawable chipDrawable = ChipDrawable.createFromAttributes(this, null, 0, R.style.Widget_MaterialComponents_Chip_Filter);
            chip.setChipDrawable(chipDrawable);
            chip.setTextColor(getResources().getColor(R.color.colorWhite));
//            chip.setMaxWidth(200);
            chip.setText(slots.getSlotStartTime()+" To "+slots.getSlotEndTime());
//            chip.setTag(slotListApi.get(i).getId());
            chip.setChipBackgroundColorResource(R.color.lightGrayFont);
//            chip.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    String id=view.getId()+"";
//                    Chip chips=chipGroup.findViewById(view.getId());
//                    String chipText=chips.getChipText().toString();
//                    String startSlot=getSlotValue(chipText,false);
//                    String endSlot=getSlotValue(chipText,true);
//
//
//                    if (!TextUtils.isEmpty(previewSlot)){
//                        if ((Integer.parseInt(previewSlot.trim()))==(Integer.parseInt(startSlot.trim()))){
//                            chip.setChipBackgroundColorResource(R.color.colorGreen);
//                            previewSlot=endSlot;
//                        }else {
//                            chip.setCheckable(false);
//                            chip.setChipBackgroundColorResource(R.color.lightGrayFont);
//                            Toast.makeText(SelecteTimeSlot.this, "Cant selecte this slot", Toast.LENGTH_SHORT).show();
//                        }
//
//
//                    }else {
//                        previewSlot=endSlot;
//                        chip.setChipBackgroundColorResource(R.color.colorGreen);
//
//                    }
//                    }
//
////
////                    slots.setChecked(!slots.isChecked());
////                    if (slots.isChecked()) {
////                        previewSlot=timeSlot;
////                        chip.setChipBackgroundColorResource(R.color.colorGreen);
////                    } else {
////                        previewSlot=timeSlot;
////                        chip.setChipBackgroundColorResource(R.color.lightGrayFont);
////                    }
////                    }
//
//
//
//            });
            chipGroup.addView(chip);
        }
        chipGroup.setEnabled(true);
        chipGroup.setChipSpacingVertical(20);
        binding.constraintChipGroup.addView(chipGroup);
    }
    private String getSlotValue(String value,boolean endSlot){
        StringTokenizer tokenss=null;
        StringTokenizer tokens = new StringTokenizer(value, "To");
        String first = tokens.nextToken();// this will contain "Fruit"
        String second = tokens.nextToken();
        if (endSlot) {
             return second;
        }else {
            return first;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.dateLayout:
                getStartDate();
                break;
            case R.id.closeSlotDialog:
                finish();
                break;
            case R.id.bottomConstraint:
                getDataFromFields();
            break;
            case R.id.startHour:
                getStartTime();
                break;
            case R.id.endHour:
                getEndTime();
                break;
        }
    }

    private void getDataFromFields() {
        startTime=binding.startHour.getText().toString().trim();
        endTime=binding.endHour.getText().toString().trim();
    if (startDate.isEmpty()){
        Toast.makeText(this, "Select Date", Toast.LENGTH_SHORT).show();
    }else if (startTime.isEmpty()){
        Toast.makeText(this, "Select start hour ", Toast.LENGTH_SHORT).show();
    }else if (endTime.isEmpty()){
        Toast.makeText(this, "Select end hour ", Toast.LENGTH_SHORT).show();
    }else {
        Intent intent = new Intent();
        intent.putExtra(Constants.SLOT_DATE, startDate);
        intent.putExtra(Constants.SLOT_START_TIME, startTime);
        intent.putExtra(Constants.SLOT_END_TIME, endTime);
        setResult(RESULT_OK, intent);
        SelecteTimeSlot.this.finish();
    }

    }

    private void getStartDate() {
        Calendar cal = Calendar.getInstance();
        int mYear = cal.get(Calendar.YEAR);
        int mMonth = cal.get(Calendar.MONTH);
        int mDay = cal.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(SelecteTimeSlot.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                binding.selectDate.setPadding(20, 20, 20, 20);
                sDate = year + "-" + convertDate((monthOfYear + 1)) + "-" + convertDate(dayOfMonth);
                startDate = year + "/" + convertDate((monthOfYear + 1)) + "/" + convertDate(dayOfMonth);
//                stDate = formatDate(startDate);
//                if (stDate.getTime() > System.currentTimeMillis()) {
                    selected=false;
                    binding.selectDate.setText(startDate);
//                    binding.createEventEndDatetv.setText("");
//                    binding.createEventEndDatetv.setHint("End date");
//                } else {
//                    binding.createEventStartDateTv.setText("");
//                    binding.createEventStartDateTv.setHint("Start date");
//                    Toast.makeText(SelecteTimeSlot.this, "Can't create event for current date", Toast.LENGTH_SHORT).show();
//                }

            }
        }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }
    public String convertDate(int input) {
        if (input >= 10) {
            return String.valueOf(input);
        } else {
            return "0" + String.valueOf(input);
        }
    }
    private void hitSpaceDetailAPI(String id) {

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading.........");
        progressDialog.show();
        Call<SlotListResponse> signUpAthlete = retrofitinterface.getTimeSlots(Constants.CONTENT_TYPE,"Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, SelecteTimeSlot.this),  "23","2019-12-18");
        signUpAthlete.enqueue(new Callback<SlotListResponse>() {
            @Override
            public void onResponse(Call<SlotListResponse> call, Response<SlotListResponse> response) {
                progressDialog.dismiss();
                slotList=new ArrayList<>();
                if (response.isSuccessful()) {
                    if (response.body().isStatus()) {
                        if (response.body().getData() != null) {
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
                        Toast.makeText(SelecteTimeSlot.this, ""+errorMessage, Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        Toast.makeText(SelecteTimeSlot.this, ""+e.getMessage().toString(), Toast.LENGTH_SHORT).show();

//                        Snackbar.make(binding.athleteLoginLayout,e.getMessage().toString(), BaseTransientBottomBar.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SlotListResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(SelecteTimeSlot.this, ""+getResources().getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();

//                Snackbar.make(binding.maineventBooking, getResources().getString(R.string.something_went_wrong), BaseTransientBottomBar.LENGTH_LONG).show();

            }
        });
    }

    private void getSlotsFromArray() {
        slotListApi=new ArrayList<>();
        if (slotList !=null && slotList.size()>0){
            for (int i=0;i<slotList.get(0).getAvailable_slot().size();i++){
                SlotModels models=new SlotModels();
                models.setSlotStartTime(slotList.get(0).getAvailable_slot().get(i).get(0));
                models.setSlotEndTime(slotList.get(0).getAvailable_slot().get(i).get(1));
                slotListApi.add(models);
            }
            setChips();
        }
    }
    private void getStartTime() {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(SelecteTimeSlot.this, new TimePickerDialog.OnTimeSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                startTime = convertDate(hourOfDay) + ":" + convertDate(minute);
                binding.startHour.setPadding(20, 0, 70, 0);
                if (formatTime(startTime).after(formatTime(chipStartTime)) && formatTime(startTime).before(formatTime(chipEndtime))){
                    binding.startHour.setText(startTime);
                }else {
                    binding.startHour.setText("");
                    binding.startHour.setText("Start time");
                    Toast.makeText(SelecteTimeSlot.this, "Select valid time", Toast.LENGTH_SHORT).show();
                }


            }
        }, mHour, mMinute, true);
        timePickerDialog.show();
    }
    private void getEndTime() {
        Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(SelecteTimeSlot.this, new TimePickerDialog.OnTimeSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                endTime = convertDate(hourOfDay) + ":" + convertDate(minute);
                binding.endHour.setPadding(20, 0, 70, 0);
                if (!TextUtils.isEmpty(startTime)) {
                    if (formatTime(endTime).before(formatTime(chipEndtime)) && formatTime(endTime).after(formatTime(startTime))) {
                        binding.endHour.setText(endTime);
                    } else {
                        binding.endHour.setText("");
                        binding.endHour.setText("End time");
                        Toast.makeText(SelecteTimeSlot.this, "Select valid time", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(SelecteTimeSlot.this, "Change start hour first", Toast.LENGTH_SHORT).show();

                }

            }
        }, mHour, mMinute, true);
        timePickerDialog.show();
    }
    private Date formatTime(String time){
        Date formated=null;
        SimpleDateFormat timeFormat=new SimpleDateFormat("HH:mm");
        try {
            formated=timeFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formated;
    }
}

