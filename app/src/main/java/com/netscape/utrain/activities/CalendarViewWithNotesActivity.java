package com.netscape.utrain.activities;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CalendarDialog;
import com.netscape.utrain.utils.CalendarView;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.Event;
import com.netscape.utrain.utils.PrefrenceConstant;

import org.json.JSONObject;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalendarViewWithNotesActivity extends AppCompatActivity {

    private final static int CREATE_EVENT_REQUEST_CODE = 100;

    private String[] mShortMonths;
    private CalendarView mCalendarView;
    private CalendarDialog mCalendarDialog;
    private int colour1;
    private int colour2;

    List<AllBookingListModel.DataBeanX.DataBean>  mEventList = new ArrayList<>();
    private ProgressDialog progressDialog;
    private Retrofitinterface retrofitinterface;

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

        if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, CalendarViewWithNotesActivity.this).equalsIgnoreCase(Constants.Organizer))
            getBookingList();
        else if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, CalendarViewWithNotesActivity.this).equalsIgnoreCase(Constants.Athlete)){

        }
//            a_getUpcommingEvents();
        else if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, CalendarViewWithNotesActivity.this).equalsIgnoreCase(Constants.Coach)){

        }
//            getCoachUpcommingEvents();

    }

    private void initializeUI() {

        setContentView(R.layout.activity_calendar_view_with_notes);
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
                displaySupplierList(calendarObjects);
            }
//                else {
//                    if (diffYMD(previousDate, selectedDate) == 0)
//                        createEvent(selectedDate);
//                }
//            }
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_toolbar_calendar_view, menu);

        return true;
    }
    public void displaySupplierList(List<CalendarView.CalendarObject> calendarObjects) {
        RecyclerView mRecyclerView;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final View content = inflater.inflate(R.layout.calander_item_list_dialog_view, null);
        builder.setView(content);
        mRecyclerView = (RecyclerView) content.findViewById(R.id.customeDialogRecycler);
        MaterialButton noBtn = (MaterialButton) content.findViewById(R.id.closeBtn);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        final CalendarEventListAdapter adapter = new CalendarEventListAdapter(this, calendarObjects);
        mRecyclerView.setAdapter(adapter);
        final AlertDialog dialog = builder.create();
        dialog.show();
        // Change the alert dialog background color
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
//        dialog.show();
    }

    private void getBookingList() {
        progressDialog.show();
        Call<AllBookingListModel> call = retrofitinterface.getAllBooking("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getApplicationContext()), Constants.CONTENT_TYPE, "");
        call.enqueue(new Callback<AllBookingListModel>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<AllBookingListModel> call, Response<AllBookingListModel> response) {
                if (response.body() != null) {
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        mEventList  = response.body().getData().getData();
                        if (mEventList.size() > 0) {

                            for (AllBookingListModel.DataBeanX.DataBean e : mEventList) {
                                String type=e.getType();
                                if (e.getType().equalsIgnoreCase("event")){
                                     colour1=Color.GREEN;
                                     colour2=Color.BLACK;
                                }
                                if (e.getType().equalsIgnoreCase("session")){
                                    colour1=Color.RED;
                                    colour2=Color.BLACK;
                                }
                                if (e.getType().equalsIgnoreCase("space")){
                                    colour1=Color.BLUE;
                                    colour2=Color.BLACK;
                                }
                                String startDate = e.getTarget_data().getStart_date();
                                String endDate = e.getTarget_data().getEnd_date();
                                try {

                                    Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
                                    Date date2=new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
                                    List<Date> list = CommonMethods.getDatesBetweenUsingJava7(date1,date2);
                                    Log.e("","list"+ list);
                                    if (list.size() > 0){
                                        for (Date date : list){
                                            Calendar calendar = Calendar.getInstance();
                                            calendar.setTime(date);
                                            mCalendarView.addCalendarObject(new CalendarView.CalendarObject(
                                                    e.getId()+"",
                                                    calendar,
                                                    colour1,colour2,e.getTarget_data().getName(),type));
                                        }
                                        Calendar calendar = Calendar.getInstance();
                                        calendar.setTime(date2);
                                        mCalendarView.addCalendarObject(new CalendarView.CalendarObject(
                                                e.getId()+"",
                                                calendar,
                                                colour1,colour2,e.getTarget_data().getName(),type));
                                    }else {
                                        Calendar calendar = Calendar.getInstance();
                                        calendar.setTime(date2);
                                        mCalendarView.addCalendarObject(new CalendarView.CalendarObject(
                                                e.getId()+"",
                                                calendar,
                                                colour1,colour2,e.getTarget_data().getName(),type));
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
            public void onFailure(Call<AllBookingListModel> call, Throwable t) {
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
                for (AllBookingListModel.DataBeanX.DataBean e : mEventList) {
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

}
