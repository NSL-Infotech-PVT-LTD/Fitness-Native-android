package com.netscape.utrain.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.netscape.utrain.R;
import com.netscape.utrain.adapters.AddViewRecyclerAdapter;
import com.netscape.utrain.databinding.ActivitySpaceBookingBinding;
import com.netscape.utrain.model.ServiceListDataModel;
import com.netscape.utrain.model.SlotModel;
import com.netscape.utrain.response.SpaceDetailResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;

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

public class SpaceBookingActivity extends AppCompatActivity implements View.OnClickListener , AddViewRecyclerAdapter.removeTimeSlot {
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
    private String spaceId="",eventId = "", eventStartDate = "", eventEndDate = "", eventStartTime = "", eventEndtime = "", totalPrice = "";
    private ProgressDialog progressDialog;
    private Retrofitinterface retrofitinterface;
    private int pricePerday;
    private int SLOT_REQUEST=191;
    private SlotModel slotModel;

    private LinearLayout mLayout;
    private EditText mEditText;
    private Button mButton;
    private AddViewRecyclerAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<SlotModel> results = new ArrayList<>();
    private int totalHours=0;
    private JsonArray jsonArray;
    private Date removeDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_space_booking);

        init();
    }

    private void init() {
        ///
        layoutManager=new LinearLayoutManager(this);
//        results.add("Chet");
        adapter=new AddViewRecyclerAdapter(SpaceBookingActivity.this,results,SpaceBookingActivity.this);
        binding.addViewRecycler.setLayoutManager(layoutManager);
        binding.addViewRecycler.setAdapter(adapter);
        ///
        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);
        hitSpaceDetailAPI();
        if (getIntent().getExtras() != null) {
            binding.eventBookMarathonHeaderTv.setText(getIntent().getStringExtra("eventName"));
            binding.eventVanueDetailTv.setText(getIntent().getStringExtra("eventVenue"));
            binding.eventTimeDetailTv.setText(getIntent().getStringExtra("eventTime"));
            binding.eventDateDetailTv.setText(getIntent().getStringExtra("eventDate"));

        }
        binding.addTimeSlot.setOnClickListener(this);
        binding.eventBookingBackImg.setOnClickListener(this);
        binding.textContinueToPay.setOnClickListener(this);
//        binding.createEventEndDatetv.setOnClickListener(this);
//        binding.createEventStartDateTv.setOnClickListener(this);
//        binding.createEventEndTime.setOnClickListener(this);
//        binding.createEvtnStartTimeTv.setOnClickListener(this);
//        binding.addLayoutBtn.setOnClickListener(this);
//    }
//
//    private void getStartTime() {
//        final Calendar c = Calendar.getInstance();
//        mHour = c.get(Calendar.HOUR_OF_DAY);
//        mMinute = c.get(Calendar.MINUTE);
//        TimePickerDialog timePickerDialog = new TimePickerDialog(SpaceBookingActivity.this, new TimePickerDialog.OnTimeSetListener() {
//            @RequiresApi(api = Build.VERSION_CODES.O)
//            @Override
//            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
//                startTime = convertDate(hourOfDay) + ":" + convertDate(minute);
//
//                timeNow = convertDate(mHour) + ":" + convertDate(mMinute);
//
//
////                int currentTime = LocalTime.parse(startTime);
//
//                binding.createEvtnStartTimeTv.setPadding(20, 0, 70, 0);
//
//                Date time = Calendar.getInstance().getTime();
//                if (stDate != null && endDate != null) {
////                    if (endDate.compareTo(stDate) == 0) {
////                        if (LocalTime.parse(startTime).isAfter(LocalTime.now())) {
//                    binding.createEvtnStartTimeTv.setText(startTime);
//                    binding.createEventEndTime.setText("");
//                    binding.createEventEndTime.setHint("End time");
////                        } else {
////                            binding.createEvtnStartTimeTv.setText("");
////                            binding.createEvtnStartTimeTv.setHint("Start time");
////                            Toast.makeText(SpaceBookingActivity.this, "Select a valid time", Toast.LENGTH_SHORT).show();
////                        }
////                    } else {
////                        binding.createEvtnStartTimeTv.setText(startTime);
////                    }
//                } else {
//                    Toast.makeText(SpaceBookingActivity.this, "Selecte Date First", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        }, mHour, mMinute, true);
//        timePickerDialog.show();
//    }
//
//    private void getEndTime() {
//        Calendar c = Calendar.getInstance();
//        mHour = c.get(Calendar.HOUR_OF_DAY);
//        mMinute = c.get(Calendar.MINUTE);
//        TimePickerDialog timePickerDialog = new TimePickerDialog(SpaceBookingActivity.this, new TimePickerDialog.OnTimeSetListener() {
//            @RequiresApi(api = Build.VERSION_CODES.O)
//            @Override
//            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
//                endTime = convertDate(hourOfDay) + ":" + convertDate(minute);
//                binding.createEventEndTime.setPadding(20, 0, 70, 0);
//                if (stDate != null && endDate != null) {
//                    if (endDate.compareTo(stDate) == 0) {
//                        if (!startTime.isEmpty()) {
//                            if (LocalTime.parse(endTime).isAfter(LocalTime.parse(startTime))) {
//                                binding.createEventEndTime.setText(endTime);
//                            } else {
//                                binding.createEventEndTime.setText("");
//                                binding.createEventEndTime.setHint("End time");
//                                Toast.makeText(SpaceBookingActivity.this, "Selecte valid time", Toast.LENGTH_SHORT).show();
//                            }
//                        } else {
//                            Toast.makeText(SpaceBookingActivity.this, "Selecte Start time", Toast.LENGTH_SHORT).show();
//                        }
//                    } else {
//                        binding.createEventEndTime.setText(endTime);
//
//                    }
//                } else {
//                    Toast.makeText(SpaceBookingActivity.this, "Selecte Date First", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        }, mHour, mMinute, true);
//        timePickerDialog.show();
//    }
//
//    private void getStartDate() {
//        Calendar cal = Calendar.getInstance();
//        mYear = cal.get(Calendar.YEAR);
//        mMonth = cal.get(Calendar.MONTH);
//        mDay = cal.get(Calendar.DAY_OF_MONTH);
//        DatePickerDialog datePickerDialog = new DatePickerDialog(SpaceBookingActivity.this, new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
//                binding.createEventStartDateTv.setPadding(20, 20, 20, 20);
//                sDate = year + "-" + convertDate((monthOfYear + 1)) + "-" + convertDate(dayOfMonth);
//                startDate = year + "/" + convertDate((monthOfYear + 1)) + "/" + convertDate(dayOfMonth);
//                stDate = formatDate(startDate);
//                if (stDate.getTime() > System.currentTimeMillis()) {
//                    binding.createEventStartDateTv.setText(startDate);
//                    binding.createEventEndDatetv.setText("");
//                    binding.createEventEndDatetv.setHint("End date");
//                } else {
//                    binding.createEventStartDateTv.setText("");
//                    binding.createEventStartDateTv.setHint("Start date");
//                    Toast.makeText(SpaceBookingActivity.this, "Can't book for current date", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        }, mYear, mMonth, mDay);
//        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
//        datePickerDialog.show();
//    }
//
//    private void getEndDate() {
//        Calendar c = Calendar.getInstance();
//        mYear = c.get(Calendar.YEAR);
//        mMonth = c.get(Calendar.MONTH);
//        mDay = c.get(Calendar.DAY_OF_MONTH);
//        DatePickerDialog datePickerDialog = new DatePickerDialog(SpaceBookingActivity.this, new DatePickerDialog.OnDateSetListener() {
//            @RequiresApi(api = Build.VERSION_CODES.O)
//            @Override
//            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
//                binding.createEventEndDatetv.setPadding(20, 20, 20, 20);
//                enDate = year + "-" + convertDate((monthOfYear + 1)) + "-" + convertDate(dayOfMonth);
//                eDate = year + "/" + convertDate((monthOfYear + 1)) + "/" + convertDate(dayOfMonth);
//                endDate = formatDate(eDate);
//                if (stDate != null) {
//                    if (endDate.compareTo(stDate) >= 0) {
//                        Log.i("app", "Date1 is after Date2");
//                        binding.createEventEndDatetv.setText(eDate);
//                        try {
//                            Long days = CommonMethods.betweenDates(stDate, endDate);
//                            setTotalPrice(days);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    } else {
//                        Toast.makeText(SpaceBookingActivity.this, "Select valid date", Toast.LENGTH_SHORT).show();
//                        binding.createEventEndDatetv.setText("");
//                        binding.createEventEndDatetv.setHint("End date");
//                    }
//                } else {
//                    Toast.makeText(SpaceBookingActivity.this, "Select start Date", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }, mYear, mMonth, mDay);
//        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
//        datePickerDialog.show();
//    }


//
//    public String convertDate(int input) {
//        if (input >= 10) {
//            return String.valueOf(input);
//        } else {
//            return "0" + input;
//        }
//    }
//

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
    private void setTotalPrice(int hours) {
        totalPrice = String.valueOf((hours * pricePerday));
//        binding.totlaPriceTv.setText("Price per Hour * " + hours + " Hours");
        binding.eventPrice.setText("$ " + totalPrice);
    }

        @Override
        public void onClick (View view){
            switch (view.getId()) {
//            case R.id.createEventEndDatetv:
//                getEndDate();
//                break;
//            case R.id.createEventStartDateTv:
//                getStartDate();
//                break;
//            case R.id.createEventEndTime:
//                getEndTime();
//                break;
//            case R.id.createEvtnStartTimeTv:
//                getStartTime();
//                break;
            case R.id.textContinueToPay:
                sendDataToPayment();

//
                break;
                case R.id.eventBookingBackImg:
                    finish();
                    break;

//                case R.id.addView:
////                TOTAL_PAGES = response.body().getData().getLast_page();
////                getItemPerPage = response.body().getData().getPer_page();
//
//                    adapter.add(slotModel);
//                    break;
                case R.id.addTimeSlot:
//                TOTAL_PAGES = response.body().getData().getLast_page();
//                getItemPerPage = response.body().getData().getPer_page();

                    Intent intent=new Intent(SpaceBookingActivity.this, SelectTimeSlot.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("bookSpaceId",getIntent().getStringExtra("event_id"));
                    startActivityForResult(intent,SLOT_REQUEST);
                    break;
            }

        }

    private void sendDataToPayment() {
        if (results !=null && results.size()>0 && Integer.parseInt(totalPrice)>0) {
            Intent payment = new Intent(SpaceBookingActivity.this, PaymentActivity.class);
            jsonArray = (JsonArray) new Gson().toJsonTree(results);
            payment.putExtra("type", getIntent().getStringExtra("type"));
            payment.putExtra("totalPrice", totalPrice);
            payment.putExtra("totalSlots", jsonArray.toString());
            payment.putExtra("event_id", getIntent().getStringExtra("event_id"));
            startActivity(payment);
        }else {
            Toast.makeText(this, "Select At least One Slot", Toast.LENGTH_SHORT).show();
        }
    }
//    private TextView createNewTextView(String text) {
//        final ViewGroup.LayoutParams lparams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        final TextView textView = new TextView(this);
//        textView.setLayoutParams(lparams);
//        textView.setText("New text: " + text);
//        return textView;
//    }

//    private void getDataFromEdtText() {
//        eventStartDate = binding.createEventStartDateTv.getText().toString();
//        eventEndDate = binding.createEventEndDatetv.getText().toString();
//        eventStartTime = binding.createEvtnStartTimeTv.getText().toString();
//        eventEndtime = binding.createEventEndTime.getText().toString();
//        if (eventStartDate.isEmpty()) {
//            Toast.makeText(this, getResources().getString(R.string.select_start_date), Toast.LENGTH_SHORT).show();
//        } else if (eventEndDate.isEmpty()) {
//            Toast.makeText(this, getResources().getString(R.string.select_end_date), Toast.LENGTH_SHORT).show();
//
//        } else if (eventStartTime.isEmpty()) {
//            Toast.makeText(this, getResources().getString(R.string.select_start_time), Toast.LENGTH_SHORT).show();
//
//        } else if (eventEndtime.isEmpty()) {
//            Toast.makeText(this, getResources().getString(R.string.select_end_time), Toast.LENGTH_SHORT).show();
//        } else {
////            hitCreateEventApi();
//            Intent intent = new Intent(SpaceBookingActivity.this, PaymentActivity.class);
//            intent.putExtra("type", getIntent().getStringExtra("type"));
//            intent.putExtra("totalPrice", totalPrice);
//            intent.putExtra("startDate", sDate + " " + startTime);
//            intent.putExtra("endDate", enDate + " " + endTime);
////                intent.putExtra("tickets", countVAlue);
//            intent.putExtra("event_id", getIntent().getStringExtra("event_id"));
//            startActivity(intent);
//
//        }
//
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==SLOT_REQUEST && resultCode==RESULT_OK && data!=null){
            slotModel=new SlotModel();
            slotModel.setFrom_time(data.getStringExtra(Constants.SLOT_START_TIME));
            slotModel.setTo_time(data.getStringExtra(Constants.SLOT_END_TIME));
            slotModel.setBooking_date(data.getStringExtra(Constants.SLOT_DATE));
//            results.add(slotModel);
            ServiceListDataModel serviceListDataModel=new ServiceListDataModel();
            serviceListDataModel.setName(data.getStringExtra(Constants.SLOT_DATE));
            SelectedServiceList.getInstance().getList().add(serviceListDataModel);
            adapter.add(slotModel);
            binding.noSlotCalendar.setVisibility(View.GONE);
            binding.noSlotText.setVisibility(View.GONE);
            binding.bookintText.setVisibility(View.VISIBLE);
            binding.bottomConstraint.setVisibility(View.VISIBLE);
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            Date date1=null;
            Date date2=null;
            try {
                date1=format.parse(data.getStringExtra(Constants.SLOT_START_TIME));
                date2=format.parse(data.getStringExtra(Constants.SLOT_END_TIME));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            long hour = CommonMethods.getDiffrenceTwoTimes(date1,date2);
//            Toast.makeText(this, "hour="+hour, Toast.LENGTH_SHORT).show();

//            String firstWord = data.getStringExtra(Constants.SELECTED_SLOT);
//            if(firstWord.contains(" ")){
//                firstWord= firstWord.substring(0, firstWord.indexOf(" "));
//
//            }
            totalHours=totalHours+(int)hour;
//            Toast.makeText(this, ""+totalHours, Toast.LENGTH_SHORT).show();
            setTotalPrice(totalHours);
        }else{
            Toast.makeText(this, "No Slot Created", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
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
                            binding.eventTimeDetailTv.setText(response.body().getData().getOpen_hours_from());
//                            binding.eventDateDetailTv.setText(response.body().getData().getAvailability_week());
                            binding.pricePerdayTv.setText("(Total Hours * " + response.body().getData().getPrice_hourly() + ")");
                            binding.eventEndTime.setText( response.body().getData().getOpen_hours_to());

//                            binding.totlaPriceTv.setText("Price per Hour * Hours");
                            pricePerday = response.body().getData().getPrice_hourly();
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


    @Override
    public void removeSlot(int position) {
        removeDate=formatDate(results.get(position).getBooking_date());
        if (SelectedServiceList.getInstance().getList()!=null && SelectedServiceList.getInstance().getList().size()>0){
            for (int i=0;i<SelectedServiceList.getInstance().getList().size();i++){
                if (removeDate.compareTo(formatDate(SelectedServiceList.getInstance().getList().get(i).getName())) == 0){
                    SelectedServiceList.getInstance().getList().remove(i);
                    break;
                }
            }
        }
        results.remove(position);
        adapter.notifyDataSetChanged();
    }

}
