package com.netscape.utrain.activities;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.all.All;
import com.facebook.internal.Utility;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.netscape.utrain.R;
import com.netscape.utrain.adapters.CalendarEventListAdapter;
import com.netscape.utrain.model.AllBookingListModel;
import com.netscape.utrain.model.O_AllBookingDataListModel;
import com.netscape.utrain.model.O_AllBookingDataModel;
import com.netscape.utrain.model.O_AllBookingTargetDataModel;
import com.netscape.utrain.response.O_AllBookingResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CalendarDialog;
import com.netscape.utrain.utils.CalendarView;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.Event;
import com.netscape.utrain.utils.PrefrenceConstant;
import com.netscape.utrain.views.RobotoCalendarView;

import org.json.JSONObject;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CalendarViewWithNotesActivity extends AppCompatActivity implements RobotoCalendarView.RobotoCalendarListener {
    private final static int CREATE_EVENT_REQUEST_CODE = 100;
    List<AllBookingListModel.DataBeanX.DataBean> mEventList = new ArrayList<>();
    List<O_AllBookingDataListModel> orgEventList = new ArrayList<>();
    ArrayList<CalendarView.CalendarObject> calendarObjectArrayList = new ArrayList();
    HashMap<String, ArrayList<O_AllBookingDataListModel>> eventsMap = new HashMap<>();
    private RobotoCalendarView robotoCalendarView;
    private String[] mShortMonths;
    private CalendarView mCalendarView;
    private CalendarDialog mCalendarDialog;
    private int colour1;
    private int colour2;
    private ProgressDialog progressDialog;
    private RecyclerView mRecyclerView;
    private AppCompatImageView calNoDataImage;
    private ImageView backArrow;
    private Retrofitinterface retrofitinterface;
    private ArrayList<O_AllBookingDataListModel> selectedDateEvents;
    private RecyclerView.LayoutManager layoutManager;

    public static Intent makeIntent(Context context) {
        return new Intent(context, CalendarViewWithNotesActivity.class);
    }

    public static int diffYMD(Calendar date1, Calendar date2) {
        if (date1.get(Calendar.YEAR) == date2.get(Calendar.YEAR) &&
                date1.get(Calendar.MONTH) == date2.get(Calendar.MONTH) &&
                date1.get(Calendar.DAY_OF_MONTH) == date2.get(Calendar.DAY_OF_MONTH))
            return 0;

        return date1.before(date2) ? -1 : 1;
    }

//    private static CalendarView.CalendarObject parseCalendarObject(AllBookingListModel event, boolean value) {
//        return new CalendarView.CalendarObject(
//                event.getData().getData().get(0).getId(),
//                event.getData().getData().get(0).getTarget_data().getStart_time(),
//                event.getData().getData().get(0).getId(),
//                value ? Color.TRANSPARENT : Color.RED);
//    }

//    private void onEventSelected(Event event) {
//        Activity context = CalendarViewWithNotesActivity.this;
//        Intent intent = CreateEventActivity.makeIntent(context, event);
//
//        startActivityForResult(intent, CREATE_EVENT_REQUEST_CODE);
//        overridePendingTransition( R.anim.slide_in_up, R.anim.stay );
//    }

//    private void createEvent(Calendar selectedDate) {
//        Activity context = CalendarViewWithNotesActivity.this;
//        Intent intent = CreateEventActivity.makeIntent(context, selectedDate);
//
//        startActivityForResult(intent, CREATE_EVENT_REQUEST_CODE);
//        overridePendingTransition( R.anim.slide_in_up, R.anim.stay );
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mShortMonths = new DateFormatSymbols().getShortMonths();

        initializeUI();
//        if (getIntent().getExtras()!=null){
//            if (getIntent().getStringExtra("fromCalendar").equalsIgnoreCase("Coach")){
//                getCoachBooking();
//            }
//            if (getIntent().getStringExtra("fromCalendar").equalsIgnoreCase("Organization")){
//               getOrgBooking();
//            }
//        }else {

        getDataFromApi();

//        if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, CalendarViewWithNotesActivity.this).equalsIgnoreCase(Constants.Organizer)) {
//            getOrgBooking();
//        } else if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, CalendarViewWithNotesActivity.this).equalsIgnoreCase(Constants.Athlete)) {
//            getBookingList();
//        } else if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, CalendarViewWithNotesActivity.this).equalsIgnoreCase(Constants.Coach)) {
//            getCoachBooking();
//        }
//        }
    }

    private void initializeUI() {

//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        LayoutInflater inflater = LayoutInflater.from(this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        final View content = inflater.inflate(R.layout.calander_item_list_dialog_view, null);
//        builder.setView(content);
        setContentView(R.layout.activity_calendar_view_with_notes);
        backArrow = findViewById(R.id.backArrowCal);
        calNoDataImage = findViewById(R.id.noCalendarImg);
        mRecyclerView = findViewById(R.id.calendarItem);
        calNoDataImage.setVisibility(View.VISIBLE);
        layoutManager = new LinearLayoutManager(CalendarViewWithNotesActivity.this);
        robotoCalendarView = findViewById(R.id.robotoCalendarPicker);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading.....");
        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mCalendarView = findViewById(R.id.calendarView);
        mCalendarView.setOnMonthChangedListener(new CalendarView.OnMonthChangedListener() {
            @Override
            public void onMonthChanged(int month, int year) {
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(mShortMonths[month]);
                    getSupportActionBar().setSubtitle(Integer.toString(year));
                }
            }
        });
        mCalendarView.setOnItemClickedListener(new CalendarView.OnItemClickListener() {
            @Override
            public void onItemClicked(List<CalendarView.CalendarObject> calendarObjects,
                                      Calendar previousDate,
                                      Calendar selectedDate) {
//                if (calendarObjects.size() != 0) {
                mCalendarDialog.setSelectedDate(selectedDate);
//                mCalendarDialog.show();
//                displaySupplierList(calendarObjects);
            }
//                else {
//                    if (diffYMD(previousDate, selectedDate) == 0)
//                        createEvent(selectedDate);
//                }
//            }
        });
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        if (getSupportActionBar() != null) {
            int month = mCalendarView.getCurrentDate().get(Calendar.MONTH);
            int year = mCalendarView.getCurrentDate().get(Calendar.YEAR);
            getSupportActionBar().setTitle(mShortMonths[month]);
            getSupportActionBar().setSubtitle(Integer.toString(year));
        }


        mCalendarDialog = CalendarDialog.Builder.instance(this)
                .setEventList(mEventList)
                .setOnItemClickListener(new CalendarDialog.OnCalendarDialogListener() {
                    @Override
                    public void onEventClick(AllBookingListModel.DataBeanX.DataBean event) {
//                         onEventSelected(event);
                    }

                    @Override
                    public void onCreateEvent(Calendar calendar) {
//                        createEvent(calendar);
                    }
                })
                .create();
        // Set listener, in this case, the same activity
        robotoCalendarView.setRobotoCalendarListener(this);

        robotoCalendarView.setShortWeekDays(false);

        robotoCalendarView.showDateTitle(true);

        robotoCalendarView.setDate(new Date());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_toolbar_calendar_view, menu);

        return true;
    }

    public void displaySupplierList() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        LayoutInflater inflater = LayoutInflater.from(this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        final View content = inflater.inflate(R.layout.calander_item_list_dialog_view, null);
//        builder.setView(content);
//        mRecyclerView = content.findViewById(R.id.customeDialogRecycler);
//        MaterialButton noBtn = content.findViewById(R.id.closeBtn);
        mRecyclerView.setLayoutManager(layoutManager);
        CalendarEventListAdapter adapter = new CalendarEventListAdapter(this, selectedDateEvents);
        mRecyclerView.setAdapter(adapter);
//        final AlertDialog dialog = builder.create();
//        dialog.show();
//         Change the alert dialog background color
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        noBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//        dialog.show();
    }

    private void getBookingList() {
        progressDialog.show();
        Call<O_AllBookingResponse> call = retrofitinterface.getAllBooking("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getApplicationContext()), Constants.CONTENT_TYPE, "");
        call.enqueue(new Callback<O_AllBookingResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<O_AllBookingResponse> call, Response<O_AllBookingResponse> response) {
                if (response.body() != null) {
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        orgEventList = response.body().getData().getData();
                        if (orgEventList.size() > 0) {

                            for (O_AllBookingDataListModel e : orgEventList) {

                                String startDate = e.getBooking_date().getStart();
                                String endDate = e.getBooking_date().getEnd();
                                try {
                                    Date date1 = null;
                                    Date date2 = null;
                                    if (startDate != null) {
                                        date1 = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
                                    }
                                    if (endDate != null) {
                                        date2 = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
                                    }

                                    List<Date> list = CommonMethods.getDatesBetweenUsingJava7(date1, date2);
                                    Log.e("", "list" + list);


                                    if (list != null & list.size() > 0) {
                                        for (Date date : list) {
                                            Calendar calendar = Calendar.getInstance();
                                            calendar.setTime(date);
                                            CharSequence s = android.text.format.DateFormat.format("yyyy-MM-dd", date);
//                                            robotoCalendarView.markCircleImage1(calendar.getTime());
                                            setCircle(e, calendar);
                                            if (eventsMap.get(s) != null) {
                                                eventsMap.get(s).add(e);
                                            } else {
                                                ArrayList eventList = new ArrayList();
                                                eventList.add(e);
                                                eventsMap.put(s.toString(), eventList);
                                            }

//                                            calendarObjectArrayList.add((new CalendarView.CalendarObject(
//                                                    e.getId() + "",
//                                                    calendar,
//                                                    colour1, colour2, e.getTarget_data().getName(), type)));
//                                            mCalendarView.addCalendarObject(new CalendarView.CalendarObject(
//                                                    e.getId() + "",
//                                                    calendar,
//                                                    colour1, colour2, e.getTarget_data().getName(), type));
                                        }
                                        Calendar calendar = Calendar.getInstance();
                                        calendar.setTime(date2);
//                                        robotoCalendarView.markCircleImage1(calendar.getTime());
                                        setCircle(e, calendar);

                                        CharSequence s = android.text.format.DateFormat.format("yyyy-MM-dd", date2);

                                        if (eventsMap.get(s) != null) {
                                            eventsMap.get(s).add(e);
                                        } else {
                                            ArrayList eventList = new ArrayList();
                                            eventList.add(e);
                                            eventsMap.put(s.toString(), eventList);
                                        }

//                                        calendarObjectArrayList.add((new CalendarView.CalendarObject(
//                                                e.getId() + "",
//                                                calendar,
//                                                colour1, colour2, e.getTarget_data().getName(), type)));
//                                        mCalendarView.addCalendarObject(new CalendarView.CalendarObject(
//                                                e.getId() + "",
//                                                calendar,
//                                                colour1, colour2, e.getTarget_data().getName(), type));
                                    } else {

                                        Calendar calendar = Calendar.getInstance();
                                        calendar.setTime(date2);
//                                        calendarObjectArrayList.add((new CalendarView.CalendarObject(
//                                                e.getId() + "",
//                                                calendar,
//                                                colour1, colour2, e.getTarget_data().getName(), type)));
//                                        robotoCalendarView.markCircleImage1(calendar.getTime());
                                        setCircle(e, calendar);
                                        CharSequence s = android.text.format.DateFormat.format("yyyy-MM-dd", date2);

                                        if (eventsMap.get(s) != null) {
                                            eventsMap.get(s).add(e);
                                        } else {
                                            ArrayList eventList = new ArrayList();
                                            eventList.add(e);
                                            eventsMap.put(endDate, eventList);
                                        }

//                                        mCalendarView.addCalendarObject(new CalendarView.CalendarObject(
//                                                e.getId() + "",
//                                                calendar,
//                                                colour1, colour2, e.getTarget_data().getName(), type));
                                    }


                                } catch (ParseException ex) {
                                    ex.printStackTrace();
                                }

                            }
                            for (CalendarView.CalendarObject calendarObject : calendarObjectArrayList) {
                                mCalendarView.addCalendarObject(calendarObject);

                            }
                        } else {
                        }
                    }
                } else {
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
            public void onFailure(Call<O_AllBookingResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void setCircle(O_AllBookingDataListModel e, Calendar calendar) {
        String type = e.getType();
        if (e.getType().equalsIgnoreCase("event")) {
            robotoCalendarView.markCircleImage1(calendar.getTime());
        }
        if (e.getType().equalsIgnoreCase("session")) {
            robotoCalendarView.markCircleImage2(calendar.getTime());
        }
        if (e.getType().equalsIgnoreCase("space")) {
            robotoCalendarView.markCircleImage3(calendar.getTime());
        }
    }

    private void getOrgBooking() {
        progressDialog.show();
        Call call = retrofitinterface.getAllBookingOrg("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getApplicationContext()), Constants.CONTENT_TYPE, "");
        call.enqueue(new Callback<O_AllBookingResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<O_AllBookingResponse> call, Response<O_AllBookingResponse> response) {
                if (response.body() != null) {
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        orgEventList.addAll(response.body().getData().getData());
                        if (orgEventList.size() > 0) {

                            for (O_AllBookingDataListModel e : orgEventList) {

                                String startDate = e.getBooking_date().getStart();
                                String endDate = e.getBooking_date().getEnd();
                                try {
                                    Date date1 = null;
                                    Date date2 = null;
                                    if (startDate != null) {
                                        date1 = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
                                    }
                                    if (endDate != null) {
                                        date2 = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
                                    }

                                    List<Date> list = CommonMethods.getDatesBetweenUsingJava7(date1, date2);
                                    Log.e("", "list" + list);
                                    if (list != null & list.size() > 0) {
                                        for (Date date : list) {
                                            Calendar calendar = Calendar.getInstance();
                                            calendar.setTime(date);
                                            CharSequence s = android.text.format.DateFormat.format("yyyy-MM-dd", date);
//                                            robotoCalendarView.markCircleImage1(calendar.getTime());
                                            setCircle(e, calendar);
                                            if (eventsMap.get(s) != null) {
                                                eventsMap.get(s).add(e);
                                            } else {
                                                ArrayList eventList = new ArrayList();
                                                eventList.add(e);
                                                eventsMap.put(s.toString(), eventList);
                                            }


//                                            mCalendarView.addCalendarObject(new CalendarView.CalendarObject(
//                                                    e.getId() + "",
//                                                    calendar,
//                                                    colour1, colour2, e.getTarget_data().getName(), type));
                                        }
                                        Calendar calendar = Calendar.getInstance();
                                        calendar.setTime(date2);
                                        setCircle(e, calendar);

                                        CharSequence s = android.text.format.DateFormat.format("yyyy-MM-dd", date2);

                                        if (eventsMap.get(s) != null) {
                                            eventsMap.get(s).add(e);
                                        } else {
                                            ArrayList eventList = new ArrayList();
                                            eventList.add(e);
                                            eventsMap.put(s.toString(), eventList);
                                        }

//                                        mCalendarView.addCalendarObject(new CalendarView.CalendarObject(
//                                                e.getId() + "",
//                                                calendar,
//                                                colour1, colour2, e.getTarget_data().getName(), type));
                                    } else {
                                        Calendar calendar = Calendar.getInstance();
                                        calendar.setTime(date2);


                                        setCircle(e, calendar);
                                        CharSequence s = android.text.format.DateFormat.format("yyyy-MM-dd", date2);

                                        if (eventsMap.get(s) != null) {
                                            eventsMap.get(s).add(e);
                                        } else {
                                            ArrayList eventList = new ArrayList();
                                            eventList.add(e);
                                            eventsMap.put(endDate, eventList);
                                        }

//                                        mCalendarView.addCalendarObject(new CalendarView.CalendarObject(
//                                                e.getId() + "",
//                                                calendar,
//                                                colour1, colour2, e.getTarget_data().getName(), type));
                                    }
                                } catch (ParseException ex) {
                                    ex.printStackTrace();
                                }

                            }
                        } else {
                        }
                    }
                } else {
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
            public void onFailure(Call<O_AllBookingResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void getCoachBooking() {
        progressDialog.show();
        Call call = retrofitinterface.getAllBookingCoach("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getApplicationContext()), Constants.CONTENT_TYPE, "");
        call.enqueue(new Callback<O_AllBookingResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<O_AllBookingResponse> call, Response<O_AllBookingResponse> response) {
                if (response.body() != null) {
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        orgEventList.addAll(response.body().getData().getData());
                        if (orgEventList.size() > 0) {

                            for (O_AllBookingDataListModel e : orgEventList) {
                                String startDate = e.getBooking_date().getStart();
                                String endDate = e.getBooking_date().getEnd();
                                try {

                                    Date date1 = null;
                                    Date date2 = null;
                                    if (startDate != null) {
                                        date1 = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
                                    }
                                    if (endDate != null) {
                                        date2 = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
                                    }
                                    List<Date> list = CommonMethods.getDatesBetweenUsingJava7(date1, date2);
                                    Log.e("", "list" + list);

                                    if (list != null & list.size() > 0) {
                                        for (Date date : list) {
                                            Calendar calendar = Calendar.getInstance();
                                            calendar.setTime(date);
                                            CharSequence s = android.text.format.DateFormat.format("yyyy-MM-dd", date);
//                                            robotoCalendarView.markCircleImage1(calendar.getTime());
                                            setCircle(e, calendar);
                                            if (eventsMap.get(s) != null) {
                                                eventsMap.get(s).add(e);
                                            } else {
                                                ArrayList eventList = new ArrayList();
                                                eventList.add(e);
                                                eventsMap.put(s.toString(), eventList);
                                            }


//                                            mCalendarView.addCalendarObject(new CalendarView.CalendarObject(
//                                                    e.getId() + "",
//                                                    calendar,
//                                                    colour1, colour2, e.getTarget_data().getName(), type));
                                        }
                                        Calendar calendar = Calendar.getInstance();
                                        calendar.setTime(date2);
                                        setCircle(e, calendar);

                                        CharSequence s = android.text.format.DateFormat.format("yyyy-MM-dd", date2);

                                        if (eventsMap.get(s) != null) {
                                            eventsMap.get(s).add(e);
                                        } else {
                                            ArrayList eventList = new ArrayList();
                                            eventList.add(e);
                                            eventsMap.put(s.toString(), eventList);
                                        }

//                                        mCalendarView.addCalendarObject(new CalendarView.CalendarObject(
//                                                e.getId() + "",
//                                                calendar,
//                                                colour1, colour2, e.getTarget_data().getName(), type));
                                    } else {
                                        Calendar calendar = Calendar.getInstance();
                                        calendar.setTime(date2);
                                        setCircle(e, calendar);
                                        CharSequence s = android.text.format.DateFormat.format("yyyy-MM-dd", date2);

                                        if (eventsMap.get(s) != null) {
                                            eventsMap.get(s).add(e);
                                        } else {
                                            ArrayList eventList = new ArrayList();
                                            eventList.add(e);
                                            eventsMap.put(endDate, eventList);
                                        }


//                                        mCalendarView.addCalendarObject(new CalendarView.CalendarObject(
//                                                e.getId() + "",
//                                                calendar,
//                                                colour1, colour2, e.getTarget_data().getName(), type));
                                    }
                                } catch (ParseException ex) {
                                    ex.printStackTrace();
                                }

                            }
                        } else {
                        }
                    }
                } else {
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
            public void onFailure(Call<O_AllBookingResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


    //    private static CalendarView.CalendarObject parseCalendarObject(AllBookingListModel.DataBeanX e) {
//        return new CalendarView.CalendarObject(
//                event.getID(),
//                event.getDate(),
//                event.getTitle(),
//                event.isCompleted() ? Color.TRANSPARENT : Color.RED);
//    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.action_today: {
//                mCalendarView.setSelectedDate(Calendar.getInstance());
//                return true;
//            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CREATE_EVENT_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
//                int action = CreateEventActivity.extractActionFromIntent(data);
//                Event event = CreateEventActivity.extractEventFromIntent(data);
                AllBookingListModel.DataBeanX oldEvent = null;
                for (O_AllBookingDataListModel e : orgEventList) {
//                    if (Objects.equals(e.getData().get(0).getId(), e.getData().get(0).getId())) {
//                        oldEvent = e;
//                        break;
//                    }
                }
//                switch (action) {
//                    case CreateEventActivity.ACTION_CREATE: {
//                        mEventList.add(event);
//                        mCalendarView.addCalendarObject(parseCalendarObject(event));
//                        mCalendarDialog.setEventList(mEventList);
//                        break;
//                    }
//                    case CreateEventActivity.ACTION_EDIT: {
//                        Event oldEvent = null;
//                        for (Event e : mEventList) {
//                            if (Objects.equals(event.getID(), e.getID())) {
//                                oldEvent = e;
//                                break;
//                            }
//                        }
//                        if (oldEvent != null) {
//                            mEventList.remove(oldEvent);
//                            mEventList.add(event);
//
//                            mCalendarView.removeCalendarObjectByID(parseCalendarObject(oldEvent));
//                            mCalendarView.addCalendarObject(parseCalendarObject(event));
//                            mCalendarDialog.setEventList(mEventList);
//                        }
//                        break;
//                    }
//                    case CreateEventActivity.ACTION_DELETE: {
//                        Event oldEvent = null;
//                        for (Event e : mEventList) {
//                            if (Objects.equals(event.getID(), e.getID())) {
//                                oldEvent = e;
//                                break;
//                            }
//                        }
//                        if (oldEvent != null) {
//                            mEventList.remove(oldEvent);
//                            mCalendarView.removeCalendarObjectByID(parseCalendarObject(oldEvent));
//
//                            mCalendarDialog.setEventList(mEventList);
//                        }
//                        break;
//                    }
//                }
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onDayClick(Date date) {
//        Toast.makeText(this, "onDayClick: " + date, Toast.LENGTH_SHORT).show();
        CharSequence s = android.text.format.DateFormat.format("yyyy-MM-dd", date);
        mRecyclerView.setVisibility(View.VISIBLE);
        selectedDateEvents = new ArrayList<>();
        Iterator myVeryOwnIterator = eventsMap.keySet().iterator();
        while (myVeryOwnIterator.hasNext()) {
            String key = (String) myVeryOwnIterator.next();
            if (s.toString().equalsIgnoreCase(key)) {
                selectedDateEvents = (eventsMap.get(s.toString()));
            }
        }
        if (selectedDateEvents != null && selectedDateEvents.size() > 0) {
            calNoDataImage.setVisibility(View.GONE);
            displaySupplierList();
        } else {
            calNoDataImage.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
//            Toast.makeText(this, "No Schedule for "+date, Toast.LENGTH_SHORT).show();
        }


//        selectedDateEvents=new ArrayList<>();
//        if (eventsMap != null && eventsMap.size() > 0) {
//            for (int i = 0; i<eventsMap.size(); i++) {
//                if (s.toString().equalsIgnoreCase(eventsMap.get(s.toString()).get(i).toString())){
//                    for (int j=0;j<eventsMap.get(i).size();j++){
//                        selectedDateEvents.add(eventsMap.get(s.toString()).get(j));
//
//                    }
//                }
//
//            }
//            if (selectedDateEvents !=null && selectedDateEvents.size()>0){
//                displaySupplierList();
//            }else {
//                Toast.makeText(this, "No Schedule for "+date, Toast.LENGTH_SHORT).show();
//            }
//
//        }

    }

    @Override
    public void onDayLongClick(Date date) {
        Toast.makeText(this, "onDayLongClick: " + date, Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onRightButtonClick() {
        getDataFromApi();
//        Toast.makeText(this, "onRightButtonClick!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLeftButtonClick() {
//        Toast.makeText(this, "onLeftButtonClick!", Toast.LENGTH_SHORT).show();
        getDataFromApi();
    }

    private void getDataFromApi() {
        if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, CalendarViewWithNotesActivity.this).equalsIgnoreCase(Constants.Organizer)) {
            getOrgBooking();
        } else if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, CalendarViewWithNotesActivity.this).equalsIgnoreCase(Constants.Athlete)) {
            getBookingList();
        } else if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, CalendarViewWithNotesActivity.this).equalsIgnoreCase(Constants.Coach)) {
            getCoachBooking();
        }
    }

}
