package com.netscape.utrain.activities.organization;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.textview.MaterialTextView;
import com.netscape.utrain.R;
import com.netscape.utrain.adapters.O_EventListAdapter;
import com.netscape.utrain.adapters_org.O_BookedEventListAdapter;
import com.netscape.utrain.model.BookedUserModel;
import com.netscape.utrain.model.O_BookedEventDataModel;
import com.netscape.utrain.model.O_BookedSessionDataModel;
import com.netscape.utrain.model.O_SessionDataModel;
import com.netscape.utrain.model.O_SpaceDataModel;
import com.netscape.utrain.model.O_SpaceListDataModel;
import com.netscape.utrain.response.O_BookedSpaceListResponse;
import com.netscape.utrain.response.O_EventBookedListResponse;
import com.netscape.utrain.response.O_EventListResponse;
import com.netscape.utrain.response.O_SessionBookedListResponse;
import com.netscape.utrain.response.O_SessionListResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventAppliedList extends AppCompatActivity implements O_BookedEventListAdapter.onClick {
    BookedUserModel model;
    private RecyclerView.LayoutManager layoutManager;
    private O_BookedEventListAdapter adapter;
    private ProgressDialog progressDialog;
    private Retrofitinterface retrofitinterface;
    private ConstraintLayout userBottomSheeet;
    private List<O_BookedEventDataModel> list;
    private List<O_BookedSessionDataModel> sessionData;
    private List<O_SpaceListDataModel> spaceData;
    private String id = "";
    private String type = "";
    private ImageView customerImage;
    private MaterialTextView userName, bookingIdText, bookingPlaceName, eventText, bookingDateText, ti_locationText, ti_Booking_Ticket,
            ti_TotalTicketPrice, ti_TotalPrice, ti_tax, totalAmount;

    private BottomSheetBehavior sheetBehavior;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_applied_list);


        layoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.appliedListRecycler);


        userBottomSheeet = findViewById(R.id.userBottomSheeet);
        userName = findViewById(R.id.userName);
        bookingIdText = findViewById(R.id.bookingIdText);
        bookingPlaceName = findViewById(R.id.bookingPlaceName);
        eventText = findViewById(R.id.eventText);
        bookingDateText = findViewById(R.id.bookingDateText);
        ti_locationText = findViewById(R.id.ti_locationText);
        ti_Booking_Ticket = findViewById(R.id.ti_Booking_Ticket);
        ti_TotalTicketPrice = findViewById(R.id.ti_TotalTicketPrice);
        ti_TotalPrice = findViewById(R.id.ti_TotalPrice);
        ti_tax = findViewById(R.id.ti_tax);
        totalAmount = findViewById(R.id.totalAmount);
        customerImage = findViewById(R.id.customerImage);
        sheetBehavior = BottomSheetBehavior.from(userBottomSheeet);

        bottomSheetBehavior_sort();
        init();


    }

    private void bottomSheetUpDown_address() {
        if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }


    }

    private void bottomSheetBehavior_sort() {

        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:

                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    private void init() {
        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        if (getIntent().getExtras() != null) {
            id = getIntent().getStringExtra(Constants.SELECTED_ID);
            type = getIntent().getStringExtra(Constants.SELECTED_TYPE);
        }
        if (type.equalsIgnoreCase(Constants.EVENT)) {
            getNumOfBookedList();
        }
        if (type.equalsIgnoreCase(Constants.SPACE)) {
            getNumSpaceList();
        }
        if (type.equalsIgnoreCase(Constants.SESSION)) {
            getNumSessionList();
        }


    }
//        adapter = new O_BookedEventListAdapter(EventAppliedList.this, list, new O_BookedEventListAdapter.onClick() {
//            @Override
//            public void onClick() {
//
//                bottomSheetUpDown_address();
//
//            }
//        });
//
//        recyclerView.setAdapter(adapter);


    public void getNumOfBookedList() {
        progressDialog.show();
        Call<O_EventBookedListResponse> call = retrofitinterface.getOrganiserBookedList("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getApplicationContext()), Constants.CONTENT_TYPE, id, getIntent().getStringExtra(Constants.STATUS), "event");
        call.enqueue(new Callback<O_EventBookedListResponse>() {
            @Override
            public void onResponse(Call<O_EventBookedListResponse> call, Response<O_EventBookedListResponse> response) {
                if (response.body() != null) {
                    list = new ArrayList<>();
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData().size() > 0) {
//                            binding.topRateRecycler.setVisibility(View.VISIBLE);
//                            binding.noDataImageView.setVisibility(View.GONE);
//                            data.addAll(response.body().getData());
                            list.addAll(response.body().getData());
                            adapter = new O_BookedEventListAdapter(EventAppliedList.this, list, 1, EventAppliedList.this);
//                                    new O_BookedEventListAdapter.onClick() {
//                                @Override
//                                public void onClick() {
//
//                                    bottomSheetUpDown_address();
//
//                                }
//                            });
                            recyclerView.setAdapter(adapter);
                        } else {
//                            binding.topRateRecycler.setVisibility(View.GONE);
//                            binding.noDataImageView.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();

                    }
                } else {
//                    binding.topRateRecycler.setVisibility(View.GONE);
//                    binding.noDataImageView.setVisibility(View.VISIBLE);
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
            public void onFailure(Call<O_EventBookedListResponse> call, Throwable t) {
//                binding.topRateRecycler.setVisibility(View.GONE);
//                binding.noDataImageView.setVisibility(View.VISIBLE);
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getNumSessionList() {
        progressDialog.show();
        Call<O_SessionBookedListResponse> callSession = retrofitinterface.getOrganiserBookedSessionList("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getApplicationContext()), Constants.CONTENT_TYPE, id, getIntent().getStringExtra(Constants.STATUS), "session");
        callSession.enqueue(new Callback<O_SessionBookedListResponse>() {
            @Override
            public void onResponse(Call<O_SessionBookedListResponse> call, Response<O_SessionBookedListResponse> response) {
                if (response.body() != null) {
                    sessionData = new ArrayList<>();
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData().size() > 0) {
//                            binding.topRateRecycler.setVisibility(View.VISIBLE);
//                            binding.noDataImageView.setVisibility(View.GONE);
//                            data.addAll(response.body().getData());
                            sessionData.addAll(response.body().getData());
                            adapter = new O_BookedEventListAdapter(EventAppliedList.this, sessionData, 3, EventAppliedList.this);
//                                    new O_BookedEventListAdapter.onClick() {
//                                @Override
//                                public void onClick() {
//
//                                    bottomSheetUpDown_address();
//
//                                }
//                            });
                            recyclerView.setAdapter(adapter);
                        } else {
//                            binding.topRateRecycler.setVisibility(View.GONE);
//                            binding.noDataImageView.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();

                    }
                } else {
//                    binding.topRateRecycler.setVisibility(View.GONE);
//                    binding.noDataImageView.setVisibility(View.VISIBLE);
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
            public void onFailure(Call<O_SessionBookedListResponse> call, Throwable t) {
//                binding.topRateRecycler.setVisibility(View.GONE);
//                binding.noDataImageView.setVisibility(View.VISIBLE);
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getNumSpaceList() {
        progressDialog.show();
        Call<O_BookedSpaceListResponse> callSession = retrofitinterface.getOrganiserBookedSpaceList("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getApplicationContext()), Constants.CONTENT_TYPE, id, getIntent().getStringExtra(Constants.STATUS), "space");
        callSession.enqueue(new Callback<O_BookedSpaceListResponse>() {
            @Override
            public void onResponse(Call<O_BookedSpaceListResponse> call, Response<O_BookedSpaceListResponse> response) {
                if (response.body() != null) {
                    spaceData = new ArrayList<>();
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData().size() > 0) {
//                            binding.topRateRecycler.setVisibility(View.VISIBLE);
//                            binding.noDataImageView.setVisibility(View.GONE);
//                            data.addAll(response.body().getData());
                            spaceData.addAll(response.body().getData());
                            adapter = new O_BookedEventListAdapter(EventAppliedList.this, spaceData, 2, EventAppliedList.this);
//                                    new O_BookedEventListAdapter.onClick() {
//                                @Override
//                                public void onClick() {
//
//                                    bottomSheetUpDown_address();
//
//                                }
//                            });
                            recyclerView.setAdapter(adapter);
                        } else {
//                            binding.topRateRecycler.setVisibility(View.GONE);
//                            binding.noDataImageView.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();

                    }
                } else {
//                    binding.topRateRecycler.setVisibility(View.GONE);
//                    binding.noDataImageView.setVisibility(View.VISIBLE);
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
            public void onFailure(Call<O_BookedSpaceListResponse> call, Throwable t) {
//                binding.topRateRecycler.setVisibility(View.GONE);
//                binding.noDataImageView.setVisibility(View.VISIBLE);
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(int type, int position) {
        if (type == 1) {
            userName.setText(list.get(position).getUser_details().getName());
            bookingIdText.setText("Booking ID : " + list.get(position).getId());
            bookingPlaceName.setText(list.get(position).getEvent().getName());
            eventText.setText("Event");
            String currentStringEnd = list.get(position).getEvent().getStart_date();


            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
            final SimpleDateFormat sdfs = new SimpleDateFormat("hh:mm aa");
            Date dt = null, dtEnd;


            try {

                dt = sdf.parse(list.get(position).getEvent().getStart_time());


                String value = null;
                if (dt != null) {
                    value = parseDateToddMMyyyy(currentStringEnd) + " | " + sdfs.format(dt);
                }
                bookingDateText.setText(value);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            ti_locationText.setText(list.get(position).getEvent().getLocation());
            ti_Booking_Ticket.setText(list.get(position).getTickets() + " Attendees & Tickets (1 per person)");
            ti_TotalTicketPrice.setText(list.get(position).getTickets() + " Tickets @ $" + list.get(position).getEvent().getPrice() + " each");
            ti_TotalPrice.setText("$" + list.get(position).getPrice() + ".00");
            ti_tax.setText("$0.00");
            totalAmount.setText("$" + list.get(position).getPrice() + ".00");

        }
        if (type == 2) {
            userName.setText(spaceData.get(position).getUser_details().getName());
            bookingIdText.setText("Booking ID : " + spaceData.get(position).getId());
            bookingPlaceName.setText(spaceData.get(position).getSpace().getName());
            eventText.setText("Event");
//            String currentStringEnd = spaceData.get(position).getSpace().getAvailability_week();
//
//
//            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
//            final SimpleDateFormat sdfs = new SimpleDateFormat("hh:mm aa");
//            Date dt = null, dtEnd;
//
//
//            try {
//
//                dt = sdf.parse(list.get(position).getEvent().getStart_time());
//
//
//                String value = null;
//                if (dt != null) {
//                    value = parseDateToddMMyyyy(currentStringEnd) + " | " + sdfs.format(dt);
//                }
//                bookingDateText.setText(value);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }


            ti_locationText.setText(spaceData.get(position).getSpace().getLocation());
            ti_Booking_Ticket.setText(spaceData.get(position).getTickets() + " Attendees & Tickets (1 per person)");
            ti_TotalTicketPrice.setText(spaceData.get(position).getTickets() + " Tickets @ $" + list.get(position).getEvent().getPrice() + " each");
            ti_TotalPrice.setText("$" + spaceData.get(position).getPrice() + ".00");
            ti_tax.setText("$0.00");
            totalAmount.setText("$" + spaceData.get(position).getPrice() + ".00");
        }
        if (type == 3) {
            userName.setText(sessionData.get(position).getUser_details().getName());
            bookingIdText.setText("Booking ID : " + sessionData.get(position).getId());
            bookingPlaceName.setText(sessionData.get(position).getSession().getName());
            eventText.setText("Event");
            String currentStringEnd = sessionData.get(position).getSession().getDate();


            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
            final SimpleDateFormat sdfs = new SimpleDateFormat("hh:mm aa");
            Date dt = null, dtEnd;


            try {

                dt = sdf.parse(sessionData.get(position).getSession().getDate());


                String value = null;
                if (dt != null) {
                    value = parseDateToddMMyyyy(currentStringEnd) + " | " + sdfs.format(dt);
                }
                bookingDateText.setText(value);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            ti_locationText.setText(sessionData.get(position).getSession().getLocation());
            ti_Booking_Ticket.setText(sessionData.get(position).getTickets() + " Attendees & Tickets (1 per person)");
            ti_TotalTicketPrice.setText(sessionData.get(position).getTickets() + " Tickets @ $" + sessionData.get(position).getSession().getHourly_rate() + " each");
            ti_TotalPrice.setText("$" + sessionData.get(position).getPrice() + ".00");
            ti_tax.setText("$0.00");
            totalAmount.setText("$" + sessionData.get(position).getPrice() + ".00");
        }

        bottomSheetUpDown_address();
    }

    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "EEE, dd MMM";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

}
