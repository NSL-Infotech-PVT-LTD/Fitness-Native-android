package com.netscape.utrain.activities;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.facebook.all.All;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.netscape.utrain.R;
import com.netscape.utrain.model.AllBookingListModel;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CalendarDialog;
import com.netscape.utrain.utils.CalendarView;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.Event;

import org.json.JSONObject;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalendarViewWithNotesActivity extends AppCompatActivity {

    private final static int CREATE_EVENT_REQUEST_CODE = 100;

    private String[] mShortMonths;
    private CalendarView mCalendarView;
    private CalendarDialog mCalendarDialog;

    private List<AllBookingListModel.DataBeanX> mEventList = new ArrayList<>();
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
    }

    private void initializeUI() {

        setContentView(R.layout.activity_calendar_view_with_notes);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading.....");
        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getTopOrgaNization();

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
                mCalendarDialog.show();
            }
//                else {
//                    if (diffYMD(previousDate, selectedDate) == 0)
//                        createEvent(selectedDate);
//                }
//            }
        });

        for (AllBookingListModel.DataBeanX e : mEventList) {
//            mCalendarView.addCalendarObject(parseCalendarObject(e));
        }

        if (getSupportActionBar() != null) {
            int month = mCalendarView.getCurrentDate().get(Calendar.MONTH);
            int year = mCalendarView.getCurrentDate().get(Calendar.YEAR);
            getSupportActionBar().setTitle(mShortMonths[month]);
            getSupportActionBar().setSubtitle(Integer.toString(year));
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                createEvent(mCalendarView.getSelectedDate());
            }
        });

        mCalendarDialog = CalendarDialog.Builder.instance(this)
                .setEventList(mEventList)
                .setOnItemClickListener(new CalendarDialog.OnCalendarDialogListener() {
                    @Override
                    public void onEventClick(AllBookingListModel.DataBeanX event) {
                        // onEventSelected(event);
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

    private void getTopOrgaNization() {
        progressDialog.show();
        Call<AllBookingListModel> call = retrofitinterface.getAllBooking("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getApplicationContext()), Constants.CONTENT_TYPE,"");
        call.enqueue(new Callback<AllBookingListModel>() {
            @Override
            public void onResponse(Call<AllBookingListModel> call, Response<AllBookingListModel> response) {
                if (response.body() != null) {
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData().getData().size() > 0) {
                            mEventList.add(response.body().getData());
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
                for (AllBookingListModel.DataBeanX e : mEventList) {
                    if (Objects.equals(e.getData().get(0).getId(), e.getData().get(0).getId())) {
                        oldEvent = e;
                        break;
                    }
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
