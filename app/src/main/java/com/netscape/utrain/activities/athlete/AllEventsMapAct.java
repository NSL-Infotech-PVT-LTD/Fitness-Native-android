package com.netscape.utrain.activities.athlete;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textview.MaterialTextView;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.AskPermission;
import com.netscape.utrain.activities.organization.GPSService;
import com.netscape.utrain.adapters.Ath_PlaceRecyclerAdapter;
import com.netscape.utrain.adapters.Ath_SessionRecyclerAdapter;
import com.netscape.utrain.adapters.CoachesRecyclerAdapter;
import com.netscape.utrain.adapters.SportBottomDialogAdapter;
import com.netscape.utrain.databinding.ActivityAllEventsMapBinding;
import com.netscape.utrain.model.AthleteEventListModel;
import com.netscape.utrain.model.AthletePlaceModel;
import com.netscape.utrain.model.AthleteSessionModel;
import com.netscape.utrain.model.SportListModel;
import com.netscape.utrain.response.AthleteEventListResponse;
import com.netscape.utrain.response.AthletePlaceResponse;
import com.netscape.utrain.response.AthleteSessionResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PaginationScrollListener;
import com.netscape.utrain.utils.PrefrenceConstant;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllEventsMapAct extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener, SportBottomDialogAdapter.sportFilterInter {

    private static final int PAGE_START = 1;
    GoogleMap mGoogleMap;
    RecyclerView recyclerViewFindPlace;
    LinearLayoutManager layoutManager;
    CoachesRecyclerAdapter adapter;
    Ath_SessionRecyclerAdapter adapterSesson;
    Ath_PlaceRecyclerAdapter adapterPlace;
    ProgressBar progressBar;
    RecyclerView.LayoutManager layoutManagerSport;
    BottomSheetDialog mBottomSheetDialog;
    private ActivityAllEventsMapBinding binding;
    private AskPermission askPermObj;
    private GPSService gpsService;
    private SportListModel.DataBeanX.DataBean sport;
    private List<AthleteEventListModel> listModels = new ArrayList<>();
    private List<AthleteSessionModel> listModelSession = new ArrayList<>();
    private List<AthletePlaceModel> listModelPlace = new ArrayList<>();
    private ArrayList<SportListModel.DataBeanX.DataBean> dropDownList = new ArrayList<>();
    private Retrofitinterface api;
    private AllEventsMapAct activity;
    private LatLng latng;
    private ConstraintLayout layoutBottomSheet, bottomsheet_list_laout;
    private AppCompatImageView filterIcon, imageLeftArrow, sportFilterIon, noDataImg;
    private AppCompatAutoCompleteTextView searchAtuoCompleteEdt;
    private BottomSheetBehavior sheetBehavior, bottomsheet_list;
    private TextView sort_distance, sort_high, sort_low, sort_latest, resetFilter;
    private int sort_count = 1;
    private String search = "";
    private MaterialTextView allEventFindAPalceTv;
    private ConstraintLayout constraint_background;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES;
    private int pageCurrent = 0;
    //    private int currentPage = PAGE_START;
    private String currentPage = "1";
    private int page = 1;
    private MapFragment map;
    private String sCoach_Id = "";
    private int getItemPerPage;
    private String orderBy = "latest";
    private ArrayList<String> data1;
    private ProgressDialog progressDialog;
    private SportBottomDialogAdapter sportAdapter;
    private RecyclerView recyclerView;
    private View sheetView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (googleServicesAvailable()) {
//            Toast.makeText(AllEventsWithMap.this, "Perfect!!!!", Toast.LENGTH_LONG).show();
//        setContentView(R.layout.activity_all_events_map);
        binding = DataBindingUtil.setContentView(AllEventsMapAct.this, R.layout.activity_all_events_map);
        api = RetrofitInstance.getClient().create(Retrofitinterface.class);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        inItMap();
        initBottomDialog();
//        } else {
//            // else Google map Layout.....
//        }

        activity = this;
        progressBar = findViewById(R.id.main_progress);
        layoutBottomSheet = findViewById(R.id.bottomsheet_sort);
        bottomsheet_list_laout = findViewById(R.id.bottomsheet_list);
        allEventFindAPalceTv = findViewById(R.id.allEventFindAPalceTv);
        constraint_background = findViewById(R.id.constraint_background);
        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        bottomsheet_list = BottomSheetBehavior.from(bottomsheet_list_laout);
        recyclerViewFindPlace = findViewById(R.id.findPlaceRecycler);
        filterIcon = findViewById(R.id.filterIcon);
        sportFilterIon = findViewById(R.id.sportFilterIon);
        resetFilter = findViewById(R.id.resetFilter);
        noDataImg = findViewById(R.id.noDataImg);
        searchAtuoCompleteEdt = findViewById(R.id.searchAtuoCompleteEdt);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewFindPlace.setLayoutManager(layoutManager);
        if (getIntent().getStringExtra("from").equalsIgnoreCase("3"))
            sportFilterIon.setVisibility(View.GONE);

        sCoach_Id = getIntent().getStringExtra("coach_id");


        sportsListApi();

        imageLeftArrow = findViewById(R.id.imageLeftArrow);
        imageLeftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        resetFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search = "";
                if (getIntent().getStringExtra("from") != null)
                    if (getIntent().getStringExtra("from").equalsIgnoreCase("1")) {
                        getAthleteEventApi("distance", search, sCoach_Id);
                    } else if (getIntent().getStringExtra("from").equalsIgnoreCase("2")) {
                        getAthleteSessionApi("distance", search, sCoach_Id);
                    } else if (getIntent().getStringExtra("from").equalsIgnoreCase("3")) {
                        getAthletePlaceApi("distance", search, sCoach_Id);
                    }
            }
        });


        layoutBottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetUpDown_address();
            }
        });
        bottomSheetBehavior_sort();
        bottomSheetBehavior_List();
        filterIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetUpDown_address();
            }
        });
        sportFilterIon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialog = new BottomSheetDialog(AllEventsMapAct.this);
                sheetView = AllEventsMapAct.this.getLayoutInflater().inflate(R.layout.sports_bottom_sheet, null);
                recyclerView = sheetView.findViewById(R.id.recyclerSportView);
                layoutManagerSport = new LinearLayoutManager(AllEventsMapAct.this);
                recyclerView.setLayoutManager(layoutManagerSport);
                sportAdapter = new SportBottomDialogAdapter(AllEventsMapAct.this, dropDownList, AllEventsMapAct.this);
                recyclerView.setAdapter(sportAdapter);
                mBottomSheetDialog.setContentView(sheetView);
                mBottomSheetDialog.show();
            }
        });
        searchAtuoCompleteEdt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search = searchAtuoCompleteEdt.getText().toString();
                    orderBy = "latest";
                    currentPage = "";
                    isLastPage = false;
                    if (getIntent().getStringExtra("from").equalsIgnoreCase("1")) {
                        constraint_background.setBackground(getResources().getDrawable(R.drawable.card_shape_outline));
                        getAthleteEventApi(orderBy, search, "");
                    } else if (getIntent().getStringExtra("from").equalsIgnoreCase("2")) {
                        constraint_background.setBackground(getResources().getDrawable(R.drawable.card_shape_outline_skyblue_bottom_round));
                        getAthleteSessionApi(orderBy, search, "");
                    } else if (getIntent().getStringExtra("from").equalsIgnoreCase("3")) {
                        constraint_background.setBackground(getResources().getDrawable(R.drawable.card_shape_outline_yellow_top_round));
                        getAthletePlaceApi(orderBy, search, "");
                    }
                    return true;
                }
                return false;
            }
        });


        recyclerFunc(layoutManager);
//        pagination(adapter, listModels);

    }

    private void initBottomDialog() {

    }

    private void getAthletePlaceApi(final String order_by, final String s, final String sCoach_Id) {
        allEventFindAPalceTv.setText("Find Spaces");

        Call<AthletePlaceResponse> call = api.getAthletePlacesList("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, activity), Constants.CONTENT_TYPE, s, getItemPerPage + "", order_by, currentPage + "", sCoach_Id);
        call.enqueue(new Callback<AthletePlaceResponse>() {
            @Override
            public void onResponse(Call<AthletePlaceResponse> call, Response<AthletePlaceResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().isStatus()) {
                        if (response.body().getData().getData() != null && response.body().getData().getData().size() > 0) {
                            recyclerViewFindPlace.setVisibility(View.VISIBLE);
                            noDataImg.setVisibility(View.GONE);
                            resetFilter.setVisibility(View.GONE);
                            listModelPlace.clear();
                            listModelPlace.addAll(response.body().getData().getData());

                            int value = response.body().getData().getData().size();

                            BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_marker);
                            Bitmap b = bitmapdraw.getBitmap();
                            Bitmap smallMarker = Bitmap.createScaledBitmap(b, 60, 60, false);
                            mGoogleMap.clear();
                            for (int i = 0; i < value; i++) {
                                if (response.body().getData().getData().get(i).getLatitude() != null && response.body().getData().getData().get(i).getLongitude() != null) {
                                    latng = new LatLng(Double.parseDouble(response.body().getData().getData().get(i).getLatitude()), Double.parseDouble(response.body().getData().getData().get(i).getLongitude()));
                                    Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(latng).title(listModelPlace.get(i).getName())
                                            .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
                                    marker.setTag(i);
                                    CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latng, 6f);
//                                mGoogleMap.animateCamera(update);
//                                adapter = new CoachesRecyclerAdapter(activity, listModels);
//                                recyclerViewFindPlace.setAdapter(adapter);
                                }
                            }
//                        adapterPlace = new Ath_PlaceRecyclerAdapter(activity, listModelPlace);
//                        recyclerViewFindPlace.setAdapter(adapterPlace);

//                            recyclerViewFindPlace.setVisibility(View.VISIBLE);
//                            binding.noDataImageView.setVisibility(View.GONE);
                            adapterPlace = new Ath_PlaceRecyclerAdapter(AllEventsMapAct.this);
                            recyclerViewFindPlace.setLayoutManager(layoutManager);
                            recyclerViewFindPlace.setAdapter(adapterPlace);
                            List<AthletePlaceModel> results = fetchPlaceResults(response);

                            TOTAL_PAGES = response.body().getData().getLast_page();
                            getItemPerPage = Integer.parseInt(response.body().getData().getPer_page());
                            adapterPlace.addAll(results);
                            if (!TextUtils.isEmpty(currentPage)) {
                                pageCurrent = Integer.parseInt(currentPage);
                            }
                            if (pageCurrent < TOTAL_PAGES)
                                adapterPlace.addLoadingFooter();
                            else isLastPage = true;


                            String[] array = new String[response.body().getData().getData().size()];

                            for (int i = 0; i < response.body().getData().getData().size(); i++) {
                                array[i] = response.body().getData().getData().get(i).getName();
                            }
                            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_dropdown_item_1line, array);
                            searchAtuoCompleteEdt.setThreshold(1);
                            searchAtuoCompleteEdt.setAdapter(adapter);

                            searchAtuoCompleteEdt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    currentPage = "";
                                    getAthletePlaceApi(order_by, adapterView.getItemAtPosition(i).toString(), sCoach_Id);

                                }
                            });
                        } else {
                            recyclerViewFindPlace.setVisibility(View.GONE);
                            noDataImg.setVisibility(View.VISIBLE);
                            resetFilter.setVisibility(View.VISIBLE);
                        }
                    }
                }else {
                    recyclerViewFindPlace.setVisibility(View.GONE);
                    noDataImg.setVisibility(View.VISIBLE);
                    resetFilter.setVisibility(View.VISIBLE);
                    progressDialog.dismiss();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        JSONObject jsonObject = jObjError.getJSONObject("error");
                        String code = jsonObject.getString("code");
                        if (!TextUtils.isEmpty(code)) {
                            if (Integer.parseInt(code) == 401) {
                                CommonMethods.invalidAuthToken(AllEventsMapAct.this, AllEventsMapAct.this);
                            }
                        }
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);

                        Toast.makeText(AllEventsMapAct.this, "" + errorMessage, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {

                    }
                }


            }

            @Override
            public void onFailure(Call<AthletePlaceResponse> call, Throwable t) {

                progressDialog.dismiss();
                Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void getNextPageAthletePlaceApi(final String order_by, final String s, final String sCoach_Id) {
        allEventFindAPalceTv.setText("Find Spaces");


        Call<AthletePlaceResponse> call = api.getAthletePlacesList("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, activity), Constants.CONTENT_TYPE, s, getItemPerPage + "", order_by, currentPage + "", "");
        call.enqueue(new Callback<AthletePlaceResponse>() {
            @Override
            public void onResponse(Call<AthletePlaceResponse> call, Response<AthletePlaceResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().isStatus()) {
                        listModelPlace.clear();
                        listModelPlace.addAll(response.body().getData().getData());

                        int value = response.body().getData().getData().size();

                        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_marker);
                        Bitmap b = bitmapdraw.getBitmap();
                        Bitmap smallMarker = Bitmap.createScaledBitmap(b, 60, 60, false);
                        mGoogleMap.clear();
                        for (int i = 0; i < value; i++) {
                            if (response.body().getData().getData().get(i).getLatitude() != null && response.body().getData().getData().get(i).getLongitude() != null) {
                                latng = new LatLng(Double.parseDouble(response.body().getData().getData().get(i).getLatitude()), Double.parseDouble(response.body().getData().getData().get(i).getLongitude()));
                                Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(latng).title(listModelPlace.get(i).getName())
                                        .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
                                marker.setTag(i);
                                CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latng, 6f);
//                                mGoogleMap.animateCamera(update);
//                                adapter = new CoachesRecyclerAdapter(activity, listModels);
//                                recyclerViewFindPlace.setAdapter(adapter);
                            }
                        }
//                        adapterPlace = new Ath_PlaceRecyclerAdapter(activity, listModelPlace);
//                        recyclerViewFindPlace.setAdapter(adapterPlace);

                        recyclerViewFindPlace.setVisibility(View.VISIBLE);
//                            binding.noDataImageView.setVisibility(View.GONE);
                        adapterPlace.removeLoadingFooter();
                        isLoading = false;

                        List<AthletePlaceModel> results = fetchPlaceResults(response);

                        //TOTAL_PAGES = response.body().getData().getLast_page();
                        getItemPerPage = Integer.parseInt(response.body().getData().getPer_page());

                        adapterPlace.addAll(results);
                        if (!TextUtils.isEmpty(currentPage)) {
                            pageCurrent = Integer.parseInt(currentPage);
                        }
                        if (pageCurrent != TOTAL_PAGES)
                            adapterPlace.addLoadingFooter();
                        else isLastPage = true;


                        String[] array = new String[response.body().getData().getData().size()];

                        for (int i = 0; i < response.body().getData().getData().size(); i++) {
                            array[i] = response.body().getData().getData().get(i).getName();
                        }
                        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_dropdown_item_1line, array);
                        searchAtuoCompleteEdt.setThreshold(1);
                        searchAtuoCompleteEdt.setAdapter(adapter);

                        searchAtuoCompleteEdt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                currentPage = "";
                                getAthletePlaceApi(order_by, adapterView.getItemAtPosition(i).toString(), sCoach_Id);
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<AthletePlaceResponse> call, Throwable t) {

//                progressDialog.dismiss();
                Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void getAthleteSessionApi(final String orderBy, final String s, final String sCoach_Id) {
        allEventFindAPalceTv.setText("Find Sessions");

        progressDialog.show();
        Call<AthleteSessionResponse> call = api.getAthleteSessionList("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, activity), Constants.CONTENT_TYPE, s, getItemPerPage + "", orderBy, currentPage + "", sCoach_Id);
        call.enqueue(new Callback<AthleteSessionResponse>() {
            @Override
            public void onResponse(Call<AthleteSessionResponse> call, Response<AthleteSessionResponse> response) {

                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().isStatus()) {
                        if (response.body().getData().getData() != null && response.body().getData().getData().size() > 0) {
                            recyclerViewFindPlace.setVisibility(View.VISIBLE);
                            noDataImg.setVisibility(View.GONE);
                            resetFilter.setVisibility(View.GONE);
                            listModelSession.clear();
                            listModelSession.addAll(response.body().getData().getData());

                            int value = response.body().getData().getData().size();

                            BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_marker);
                            Bitmap b = bitmapdraw.getBitmap();
                            Bitmap smallMarker = Bitmap.createScaledBitmap(b, 60, 60, false);
                            mGoogleMap.clear();
                            for (int i = 0; i < value; i++) {
                                if ((response.body().getData().getData().get(i).getLatitude() != null) && (response.body().getData().getData().get(i).getLongitude() != null)) {
                                    latng = new LatLng(Double.parseDouble(response.body().getData().getData().get(i).getLatitude()), Double.parseDouble(response.body().getData().getData().get(i).getLongitude()));
                                    Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(latng).title(listModelSession.get(i).getName())
                                            .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
                                    marker.setTag(i);
                                    CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latng, 6f);
//                            mGoogleMap.animateCamera(update);
//                                adapter = new CoachesRecyclerAdapter(activity, listModels);
//                            recyclerViewFindPlace.setAdapter(adapter);
                                }
//


//                            binding.noDataImageView.setVisibility(View.GONE);
                                adapterSesson = new Ath_SessionRecyclerAdapter(AllEventsMapAct.this);
                                recyclerViewFindPlace.setLayoutManager(layoutManager);
                                recyclerViewFindPlace.setAdapter(adapterSesson);
                                List<AthleteSessionModel> results = fetchSessionResults(response);

                                TOTAL_PAGES = response.body().getData().getLast_page();
                                getItemPerPage = Integer.parseInt(response.body().getData().getPer_page());
                                adapterSesson.addAll(results);
                                if (!TextUtils.isEmpty(currentPage)) {
                                    pageCurrent = Integer.parseInt(currentPage);
                                }
                                if (pageCurrent < TOTAL_PAGES)
                                    adapterSesson.addLoadingFooter();
                                else isLastPage = true;

                            }
//                        adapterSesson = new Ath_SessionRecyclerAdapter(activity, listModelSession);
//                        recyclerViewFindPlace.setAdapter(adapterSesson);


                            String[] array = new String[response.body().getData().getData().size()];

                            for (int i = 0; i < response.body().getData().getData().size(); i++) {
                                array[i] = response.body().getData().getData().get(i).getName();
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_dropdown_item_1line, array);
                            searchAtuoCompleteEdt.setThreshold(1);
                            searchAtuoCompleteEdt.setAdapter(adapter);

                            searchAtuoCompleteEdt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    currentPage = "";
                                    getAthleteSessionApi(orderBy, adapterView.getItemAtPosition(i).toString(), sCoach_Id);
                                }
                            });
                        } else {
                            recyclerViewFindPlace.setVisibility(View.GONE);
                            noDataImg.setVisibility(View.VISIBLE);
                            resetFilter.setVisibility(View.VISIBLE);
                        }
                    }
                }else {
                    recyclerViewFindPlace.setVisibility(View.GONE);
                    noDataImg.setVisibility(View.VISIBLE);
                    resetFilter.setVisibility(View.VISIBLE);
                    progressDialog.dismiss();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        JSONObject jsonObject = jObjError.getJSONObject("error");
                        String code = jsonObject.getString("code");
                        if (!TextUtils.isEmpty(code)) {
                            if (Integer.parseInt(code) == 401) {
                                CommonMethods.invalidAuthToken(AllEventsMapAct.this, AllEventsMapAct.this);
                            }
                        }
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);

                        Toast.makeText(AllEventsMapAct.this, "" + errorMessage, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {

                    }
                }
            }

            @Override
            public void onFailure(Call<AthleteSessionResponse> call, Throwable t) {

                progressDialog.dismiss();
                Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void nextGetAthleteSessionApi(final String orderBy, final String s, final String sCoach_Id) {
        allEventFindAPalceTv.setText("Find Sessions");
//        final ProgressDialog progressDialog = new ProgressDialog(activity);
//        progressDialog.setMessage("Loading Session....");
//        progressDialog.show();

        Call<AthleteSessionResponse> call = api.getAthleteSessionList("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, activity), Constants.CONTENT_TYPE, s, getItemPerPage + "", orderBy, currentPage + "", sCoach_Id);
        call.enqueue(new Callback<AthleteSessionResponse>() {
            @Override
            public void onResponse(Call<AthleteSessionResponse> call, Response<AthleteSessionResponse> response) {

//                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().isStatus()) {
                        listModelSession.clear();
                        listModelSession.addAll(response.body().getData().getData());

                        int value = response.body().getData().getData().size();

                        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_marker);
                        Bitmap b = bitmapdraw.getBitmap();
                        Bitmap smallMarker = Bitmap.createScaledBitmap(b, 60, 60, false);
                        mGoogleMap.clear();
                        for (int i = 0; i < value; i++) {
                            if ((response.body().getData().getData().get(i).getLatitude() != null) && (response.body().getData().getData().get(i).getLongitude() != null)) {
                                latng = new LatLng(Double.parseDouble(response.body().getData().getData().get(i).getLatitude()), Double.parseDouble(response.body().getData().getData().get(i).getLongitude()));
                                Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(latng).title(listModelSession.get(i).getName())
                                        .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
                                marker.setTag(i);
                                CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latng, 6f);
//                            mGoogleMap.animateCamera(update);

                            }
//                            adapter = new CoachesRecyclerAdapter(activity, listModels);
//                            recyclerViewFindPlace.setAdapter(adapter);
                        }
//                        adapterSesson = new Ath_SessionRecyclerAdapter(activity, listModelSession);
//                        recyclerViewFindPlace.setAdapter(adapterSesson);

                        recyclerViewFindPlace.setVisibility(View.VISIBLE);
//                            binding.noDataImageView.setVisibility(View.GONE);
                        adapterSesson.removeLoadingFooter();
                        isLoading = false;

                        List<AthleteSessionModel> results = fetchSessionResults(response);

                        //TOTAL_PAGES = response.body().getData().getLast_page();
                        getItemPerPage = Integer.parseInt(response.body().getData().getPer_page());

                        adapterSesson.addAll(results);
                        if (!TextUtils.isEmpty(currentPage)) {
                            pageCurrent = Integer.parseInt(currentPage);
                        }
                        if (pageCurrent != TOTAL_PAGES)
                            adapterSesson.addLoadingFooter();
                        else isLastPage = true;

                        String[] array = new String[response.body().getData().getData().size()];

                        for (int i = 0; i < response.body().getData().getData().size(); i++) {
                            array[i] = response.body().getData().getData().get(i).getName();
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_dropdown_item_1line, array);
                        searchAtuoCompleteEdt.setThreshold(1);
                        searchAtuoCompleteEdt.setAdapter(adapter);

                        searchAtuoCompleteEdt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                currentPage = "";
                                getAthleteSessionApi(orderBy, adapterView.getItemAtPosition(i).toString(), sCoach_Id);
                            }
                        });
                    }
                }


            }

            @Override
            public void onFailure(Call<AthleteSessionResponse> call, Throwable t) {

//                progressDialog.dismiss();
                Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void getAthleteEventApi(final String sortBy, final String search, final String coach_id) {

        allEventFindAPalceTv.setText("Find Events");
        progressDialog.show();

        Call<AthleteEventListResponse> call = api.getAthleteEventList("Bearer " +
                        CommonMethods.getPrefData(Constants.AUTH_TOKEN, activity),
                Constants.CONTENT_TYPE, sortBy,
                search, getItemPerPage + "", currentPage + "", "100", coach_id);
        CommonMethods.getPrefData(PrefrenceConstant.USER_ID, AllEventsMapAct.this);
        call.enqueue(new Callback<AthleteEventListResponse>() {
            @Override
            public void onResponse(Call<AthleteEventListResponse> call, final Response<AthleteEventListResponse> response) {

                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().isStatus()) {
                        if (response.body().getData().getData() != null && response.body().getData().getData().size() > 0) {
                            noDataImg.setVisibility(View.GONE);
                            resetFilter.setVisibility(View.GONE);
                            listModels.clear();
                            listModels.addAll(response.body().getData().getData());
                            int value = response.body().getData().getData().size();

                            BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_marker);
                            Bitmap b = bitmapdraw.getBitmap();
                            Bitmap smallMarker = Bitmap.createScaledBitmap(b, 60, 60, false);
                            if (mGoogleMap != null) mGoogleMap.clear();

                            for (int i = 0; i < value; i++) {
                                if (response.body().getData().getData().get(i).getLatitude() != null && response.body().getData().getData().get(i).getLongitude() != null) {
                                    latng = new LatLng(Double.parseDouble(response.body().getData().getData().get(i).getLatitude()), Double.parseDouble(response.body().getData().getData().get(i).getLongitude()));

                                    Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(latng).title(response.body().getData().getData().get(i).getName())
                                            .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
                                    marker.setTag(i);


                                    CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latng, 6f);
//                            mGoogleMap.animateCamera(update);
//                            adapter = new CoachesRecyclerAdapter(activity, listModels);
//                            recyclerViewFindPlace.setAdapter(adapter);


                                    recyclerViewFindPlace.setVisibility(View.VISIBLE);
//                            binding.noDataImageView.setVisibility(View.GONE);
                                    adapter = new CoachesRecyclerAdapter(AllEventsMapAct.this);
                                    recyclerViewFindPlace.setLayoutManager(layoutManager);
                                    recyclerViewFindPlace.setAdapter(adapter);
                                    List<AthleteEventListModel> results = fetchResults(response);

                                    TOTAL_PAGES = response.body().getData().getLast_page();
                                    getItemPerPage = Integer.parseInt(response.body().getData().getPer_page());

                                    adapter.addAll(results);
                                    if (!TextUtils.isEmpty(currentPage)) {
                                        pageCurrent = Integer.parseInt(currentPage);
                                    }
//                            else {
//                                pageCurrent=1;
//                                currentPage="1";
//                                orderBy="latest";
//                            }
                                    if (pageCurrent < TOTAL_PAGES) {
                                        adapter.addLoadingFooter();
                                    } else isLastPage = true;
                                }

                            }

                            String[] array = new String[response.body().getData().getData().size()];

                            for (int i = 0; i < response.body().getData().getData().size(); i++) {
                                array[i] = response.body().getData().getData().get(i).getName();
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_dropdown_item_1line, array);
                            searchAtuoCompleteEdt.setThreshold(1);
                            searchAtuoCompleteEdt.setAdapter(adapter);

                            searchAtuoCompleteEdt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    currentPage = "";
                                    getAthleteEventApi(sortBy, adapterView.getItemAtPosition(i).toString(), coach_id);
                                }
                            });
                        } else {
                            recyclerViewFindPlace.setVisibility(View.GONE);
                            noDataImg.setVisibility(View.VISIBLE);
                            resetFilter.setVisibility(View.VISIBLE);
                        }
                    }
                } else {
                    recyclerViewFindPlace.setVisibility(View.GONE);
                    noDataImg.setVisibility(View.VISIBLE);
                    resetFilter.setVisibility(View.VISIBLE);
                    progressDialog.dismiss();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        JSONObject jsonObject = jObjError.getJSONObject("error");
                        String code = jsonObject.getString("code");
                        if (!TextUtils.isEmpty(code)) {
                            if (Integer.parseInt(code) == 401) {
                                CommonMethods.invalidAuthToken(AllEventsMapAct.this, AllEventsMapAct.this);
                            }
                        }
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);

                        Toast.makeText(AllEventsMapAct.this, "" + errorMessage, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {

                    }
                }


            }

            @Override
            public void onFailure(Call<AthleteEventListResponse> call, Throwable t) {
                progressDialog.dismiss();

                Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void nextGetAthleteEventApi(final String sortBy, final String search, final String coach_id) {
        allEventFindAPalceTv.setText("Find Events");
//        final ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Loading....");
//        progressDialog.show();

        Call<AthleteEventListResponse> call = api.getAthleteEventList("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, activity), Constants.CONTENT_TYPE, sortBy, search, getItemPerPage + "", currentPage + "", "100", "");
        CommonMethods.getPrefData(PrefrenceConstant.USER_ID, AllEventsMapAct.this);
        call.enqueue(new Callback<AthleteEventListResponse>() {
            @Override
            public void onResponse(Call<AthleteEventListResponse> call, final Response<AthleteEventListResponse> response) {

//                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().isStatus()) {
                        listModels.clear();
                        listModels.addAll(response.body().getData().getData());


                        int value = response.body().getData().getData().size();

                        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_marker);
                        Bitmap b = bitmapdraw.getBitmap();
                        Bitmap smallMarker = Bitmap.createScaledBitmap(b, 60, 60, false);
                        if (mGoogleMap != null) mGoogleMap.clear();
                        for (int i = 0; i < value; i++) {
                            latng = new LatLng(Double.parseDouble(response.body().getData().getData().get(i).getLatitude()), Double.parseDouble(response.body().getData().getData().get(i).getLongitude()));
                            Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(latng).title(listModels.get(i).getName())
                                    .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
                            marker.setTag(i);
                            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latng, 6f);
//                            mGoogleMap.animateCamera(update);
//                            adapter = new CoachesRecyclerAdapter(activity, listModels);
//                            recyclerViewFindPlace.setAdapter(adapter);


                        }
                        recyclerViewFindPlace.setVisibility(View.VISIBLE);
//                            binding.noDataImageView.setVisibility(View.GONE);
                        adapter.removeLoadingFooter();
                        isLoading = false;

                        List<AthleteEventListModel> results = fetchResults(response);

                        //TOTAL_PAGES = response.body().getData().getLast_page();
                        getItemPerPage = Integer.parseInt(response.body().getData().getPer_page());

                        adapter.addAll(results);
                        if (!TextUtils.isEmpty(currentPage)) {
                            pageCurrent = Integer.parseInt(currentPage);
                        }
                        if (pageCurrent != TOTAL_PAGES)
                            adapter.addLoadingFooter();
                        else isLastPage = true;


                        String[] array = new String[response.body().getData().getData().size()];

                        for (int i = 0; i < response.body().getData().getData().size(); i++) {
                            array[i] = response.body().getData().getData().get(i).getName();
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_dropdown_item_1line, array);
                        searchAtuoCompleteEdt.setThreshold(1);
                        searchAtuoCompleteEdt.setAdapter(adapter);

                        searchAtuoCompleteEdt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                currentPage = "";
                                getAthleteEventApi(sortBy, adapterView.getItemAtPosition(i).toString(), coach_id);
                            }
                        });

                    }
                }


            }


            @Override
            public void onFailure(Call<AthleteEventListResponse> call, Throwable t) {
//                progressDialog.dismiss();

                Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private List<AthleteEventListModel> fetchResults(Response<AthleteEventListResponse> response) {
        AthleteEventListResponse topRatedMovies = response.body();
        return topRatedMovies.getData().getData();
    }

    private List<AthleteSessionModel> fetchSessionResults(Response<AthleteSessionResponse> response) {
        AthleteSessionResponse topRatedMovies = response.body();
        return topRatedMovies.getData().getData();
    }

    private List<AthletePlaceModel> fetchPlaceResults(Response<AthletePlaceResponse> response) {
        AthletePlaceResponse topRatedMovies = response.body();
        return topRatedMovies.getData().getData();
    }

    private void bottomSheetUpDown_List() {
        if (bottomsheet_list.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            bottomsheet_list.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            bottomsheet_list.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }

    private void bottomSheetUpDown_address() {
        if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        bottomOnClickSort();

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

    private void bottomSheetBehavior_List() {

        bottomsheet_list.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
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

    private void inItMap() {
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment));
        map.getMapAsync(this);
        askPermObj = new AskPermission(AllEventsMapAct.this, this);

    }


    private void bottomOnClickSort() {

        sort_distance = findViewById(R.id.sort_distance);
        sort_high = findViewById(R.id.sort_high);
        sort_low = findViewById(R.id.sort_low);
        sort_latest = findViewById(R.id.sort_latest);

        if (sort_count == 2) {
            checkClick();
            sort_high.setTypeface(null, Typeface.BOLD);
            sort_high.setTextColor(getResources().getColor(R.color.colorGreen));

        } else if (sort_count == 3) {
            checkClick();
            sort_low.setTypeface(null, Typeface.BOLD);
            sort_low.setTextColor(getResources().getColor(R.color.colorGreen));

        } else if (sort_count == 1) {
            checkClick();
            sort_distance.setTypeface(null, Typeface.BOLD);
            sort_distance.setTextColor(getResources().getColor(R.color.colorGreen));
        } else if (sort_count == 4) {
            checkClick();
            sort_latest.setTypeface(null, Typeface.BOLD);
            sort_latest.setTextColor(getResources().getColor(R.color.colorGreen));

        }
        sort_distance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sort_distance.setTypeface(null, Typeface.BOLD);
                bottomSheetUpDown_address();
                sort_count = 1;
                currentPage = "1";
                orderBy = "distance";
//                search = "";
                isLastPage = false;
                if (getIntent().getStringExtra("from").equalsIgnoreCase("1"))
                    getAthleteEventApi(orderBy, search, sCoach_Id);
                else if (getIntent().getStringExtra("from").equalsIgnoreCase("2"))
                    getAthleteSessionApi(orderBy, search, sCoach_Id);
                else if (getIntent().getStringExtra("from").equalsIgnoreCase("3"))
                    getAthletePlaceApi(orderBy, search, sCoach_Id);
            }
        });
        sort_high.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sort_high.setTypeface(null, Typeface.BOLD);
                bottomSheetUpDown_address();
                sort_count = 2;
                currentPage = "1";
                orderBy = "price_high";
//                search = "";
                isLastPage = false;
                if (getIntent().getStringExtra("from").equalsIgnoreCase("1"))
                    getAthleteEventApi(orderBy, search, sCoach_Id);
                else if (getIntent().getStringExtra("from").equalsIgnoreCase("2"))
                    getAthleteSessionApi(orderBy, search, sCoach_Id);
                else if (getIntent().getStringExtra("from").equalsIgnoreCase("3"))
                    getAthletePlaceApi(orderBy, search, sCoach_Id);
            }
        });
        sort_latest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sort_latest.setTypeface(null, Typeface.BOLD);
                bottomSheetUpDown_address();
                sort_count = 4;
                currentPage = "1";
                orderBy = "latest";
//                search = "";
                isLastPage = false;
                if (getIntent().getStringExtra("from").equalsIgnoreCase("1"))
                    getAthleteEventApi(orderBy, search, sCoach_Id);
                else if (getIntent().getStringExtra("from").equalsIgnoreCase("2"))
                    getAthleteSessionApi(orderBy, search, sCoach_Id);
                else if (getIntent().getStringExtra("from").equalsIgnoreCase("3"))
                    getAthletePlaceApi(orderBy, search, sCoach_Id);

            }
        });
        sort_low.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sort_low.setTypeface(null, Typeface.BOLD);
                bottomSheetUpDown_address();
                sort_count = 3;
                currentPage = "1";
                orderBy = "price_low";
//                search = "";
                isLastPage = false;

                //                getAthleteEventApi("price_low", search);
                if (getIntent().getStringExtra("from").equalsIgnoreCase("1"))
                    getAthleteEventApi(orderBy, search, sCoach_Id);
                else if (getIntent().getStringExtra("from").equalsIgnoreCase("2"))
                    getAthleteSessionApi(orderBy, search, sCoach_Id);
                else if (getIntent().getStringExtra("from").equalsIgnoreCase("3"))
                    getAthletePlaceApi(orderBy, search, sCoach_Id);
            }
        });


    }

    private void checkClick() {
        sort_high.setTypeface(null, Typeface.NORMAL);
        sort_low.setTypeface(null, Typeface.NORMAL);
        sort_distance.setTypeface(null, Typeface.NORMAL);
        sort_latest.setTypeface(null, Typeface.NORMAL);
        //change color
        sort_high.setTextColor(getResources().getColor(R.color.colorBlack));
        sort_low.setTextColor(getResources().getColor(R.color.colorBlack));
        sort_distance.setTextColor(getResources().getColor(R.color.colorBlack));
        sort_latest.setTextColor(getResources().getColor(R.color.colorBlack));
    }

    public boolean googleServicesAvailable() {

        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(activity);
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (api.isUserResolvableError(isAvailable)) {

            Dialog dialog = api.getErrorDialog(activity, isAvailable, 0);
            dialog.show();
        } else {
            Toast.makeText(activity, "Cant connect to play store", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(AllEventsMapAct.this);

        mGoogleMap = googleMap;
        if (askPermObj.isPermissionGiven(this, Manifest.permission.ACCESS_FINE_LOCATION))
            mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(false);
        mGoogleMap.getUiSettings().setRotateGesturesEnabled(true);
        mGoogleMap.getUiSettings().setScrollGesturesEnabled(true);
        mGoogleMap.getUiSettings().setTiltGesturesEnabled(true);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        mGoogleMap.getUiSettings().setMapToolbarEnabled(false);
        googleMap.setOnInfoWindowClickListener(this);

        if (getIntent().getStringExtra("from") != null)
            if (getIntent().getStringExtra("from").equalsIgnoreCase("1")) {
                constraint_background.setBackground(getResources().getDrawable(R.drawable.card_shape_outline));
                getAthleteEventApi("distance", search, sCoach_Id);
            } else if (getIntent().getStringExtra("from").equalsIgnoreCase("2")) {
                constraint_background.setBackground(getResources().getDrawable(R.drawable.card_shape_outline_skyblue_bottom_round));
                getAthleteSessionApi("distance", search, sCoach_Id);
            } else if (getIntent().getStringExtra("from").equalsIgnoreCase("3")) {
                constraint_background.setBackground(getResources().getDrawable(R.drawable.card_shape_outline_yellow_top_round));
                getAthletePlaceApi("distance", search, sCoach_Id);
//            } else if (getIntent().getStringExtra("from").equalsIgnoreCase("topEvent")) {
//                getAthleteEventApi("distance", search, sCoach_Id);
//            } else if (getIntent().getStringExtra("from").equalsIgnoreCase("topSession")) {
//                getAthleteSessionApi("distance", search, sCoach_Id);
//            } else if (getIntent().getStringExtra("from").equalsIgnoreCase("topSpace")) {
//                getAthletePlaceApi("distance", search, sCoach_Id);

            }


        askPermission();

        View locationButton = ((View) map.getView().findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        rlp.setMargins(0, 180, 0, 0);

    }

    /*ask permissoin of location*/
    private void askPermission() {
        if (!askPermObj.isPermissionGiven(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            askPermObj.askPermission(Manifest.permission.ACCESS_FINE_LOCATION, Constants.ACCESS_FINE_LOCATION_REQUEST);
            return;
        }
        if (mGoogleMap != null) {
            getUserCurrentLocation();
        }
    }
//    private void pagination(final CoachesRecyclerAdapter adapter, final List<AthleteEventListModel> listModels) {
//        recyclerViewFindPlace.addOnScrollListener(new PaginationScrollListener(layoutManager) {
//            @Override
//            protected void loadMoreItems() {
//                isLoading = true;
//                currentPage += 1;
//
//                // mocking network delay for API call
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        loadNextPage(adapter, listModels);
//                    }
//                }, 1000);
//            }
//
//            @Override
//            public int getTotalPageCount() {
//                return TOTAL_PAGES;
//            }
//
//            @Override
//            public boolean isLastPage() {
//                return isLastPage;
//            }
//
//            @Override
//            public boolean isLoading() {
//                return isLoading;
//            }
//        });
//
//
//        // mocking network delay for API call
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                loadFirstPage(adapter, listModels);
//            }
//        }, 1000);
//
//
//    }


//    private void loadFirstPage(CoachesRecyclerAdapter adapter, List<AthleteEventListModel> listModels) {
////        listModels = AthleteEventListModel.createMovies(adapter.getItemCount());
//        if (adapter != null) {
//            progressBar.setVisibility(View.GONE);
//            getAthleteEventApi("latest", "","");
//
//            Toast.makeText(activity, "" + adapter.getItemCount(), Toast.LENGTH_SHORT).show();
//
//            if (currentPage <= TOTAL_PAGES) adapter.addLoadingFooter();
//            else isLastPage = true;
//
//        }
//    }

    //    private void loadNextPage(CoachesRecyclerAdapter adapter, List<AthleteEventListModel> listModels) {
////        listModels = AthleteEventListModel.createMovies(adapter.getItemCount());
//
//        if (adapter != null) {
//            adapter.removeLoadingFooter();
//            isLoading = false;
//
//            page++;
//            getAthleteEventApi("latest", "","");
//
//            if (currentPage != TOTAL_PAGES) adapter.addLoadingFooter();
//            else isLastPage = true;
//        }
//    }
    private void getUserCurrentLocation() {
        gpsService = new GPSService(AllEventsMapAct.this);
        Location userLocation = gpsService.getLocation();
        if (userLocation != null) {
            LatLng placeOn = new LatLng(userLocation.getLatitude(), userLocation.getLongitude());
            CameraUpdate zoom = CameraUpdateFactory.zoomTo(14);
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(placeOn));
            mGoogleMap.animateCamera(zoom);
        }
    }

    private void recyclerFunc(LinearLayoutManager layoutManager) {
        recyclerViewFindPlace.addOnScrollListener(new PaginationScrollListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                if (!TextUtils.isEmpty(currentPage)) {
                    pageCurrent = Integer.parseInt(currentPage);
                }
                pageCurrent += 1;
                currentPage = pageCurrent + "";

                // mocking network delay for API call
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (getIntent().getStringExtra("from").equalsIgnoreCase("1"))
                            nextGetAthleteEventApi(orderBy, search, "");
                        if (getIntent().getStringExtra("from").equalsIgnoreCase("2"))
                            nextGetAthleteSessionApi(orderBy, search, "");
                        if (getIntent().getStringExtra("from").equalsIgnoreCase("3"))
                            getNextPageAthletePlaceApi(orderBy, search, "");
//                        if (getIntent().getStringExtra(Constants.TOP_TYPE_INTENT).equalsIgnoreCase(Constants.TOP_COACHES));
//                            nextCoachListApi();
//                        if (getIntent().getStringExtra(Constants.TOP_TYPE_INTENT).equalsIgnoreCase(Constants.TOP_ORG));
//                            nextGetTopOrgaNization();
//                        nextProductsAPI(orderbyValue, searchBy);
                    }
                }, 1000);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
    }


    @Override
    public void onInfoWindowClick(Marker marker) {
        if (getIntent().getStringExtra("from").equalsIgnoreCase("1")) {
            Intent intent = new Intent(AllEventsMapAct.this, EventDetail.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("eventName", listModels.get(Integer.parseInt(marker.getTag().toString())).getName());
            intent.putExtra("eventVenue", listModels.get(Integer.parseInt(marker.getTag().toString())).getLocation());
            intent.putExtra("event_id", listModels.get(Integer.parseInt(marker.getTag().toString())).getId() + "");
            intent.putExtra("guest_allowed", listModels.get(Integer.parseInt(marker.getTag().toString())).getGuest_allowed() + "");
            intent.putExtra("guest_allowed_left", listModels.get(Integer.parseInt(marker.getTag().toString())).getGuest_allowed_left() + "");
            intent.putExtra("eventDate", listModels.get(Integer.parseInt(marker.getTag().toString())).getStart_date());
            intent.putExtra("eventEndDate", listModels.get(Integer.parseInt(marker.getTag().toString())).getEnd_date());
            intent.putExtra("eventTime", listModels.get(Integer.parseInt(marker.getTag().toString())).getStart_time());
            intent.putExtra("eventEndTime", listModels.get(Integer.parseInt(marker.getTag().toString())).getEnd_time());
            intent.putExtra("eventDescription", listModels.get(Integer.parseInt(marker.getTag().toString())).getDescription());
            intent.putExtra("image_url", Constants.IMAGE_BASE_EVENT);
            intent.putExtra("from", "events");
            intent.putExtra("capacity", listModels.get(Integer.parseInt(marker.getTag().toString())).getGuest_allowed());
            intent.putExtra("gmapLat", listModels.get(Integer.parseInt(marker.getTag().toString())).getLatitude() + "");
            intent.putExtra("gmapLong", listModels.get(Integer.parseInt(marker.getTag().toString())).getLongitude() + "");
            Bundle b = new Bundle();
            b.putString("Array", listModels.get(Integer.parseInt(marker.getTag().toString())).getImages());
            intent.putExtras(b);
            startActivity(intent);
        }
        if (getIntent().getStringExtra("from").equalsIgnoreCase("2")) {
            Intent intent = new Intent(AllEventsMapAct.this, EventDetail.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("eventName", listModelSession.get(Integer.parseInt(marker.getTag().toString())).getName());
            intent.putExtra("guest_allowed", listModelSession.get(Integer.parseInt(marker.getTag().toString())).getGuest_allowed() + "");
            intent.putExtra("guest_allowed_left", listModelSession.get(Integer.parseInt(marker.getTag().toString())).getGuest_allowed_left() + "");
            intent.putExtra("eventVenue", listModelSession.get(Integer.parseInt(marker.getTag().toString())).getLocation());
            intent.putExtra("eventTime", listModelSession.get(Integer.parseInt(marker.getTag().toString())).getStart_time());
            intent.putExtra("eventEndTime", listModelSession.get(Integer.parseInt(marker.getTag().toString())).getEnd_time());
            intent.putExtra("eventDate", listModelSession.get(Integer.parseInt(marker.getTag().toString())).getStart_date());
            intent.putExtra("eventEndDate", listModelSession.get(Integer.parseInt(marker.getTag().toString())).getEnd_date());
            intent.putExtra("eventDescription", listModelSession.get(Integer.parseInt(marker.getTag().toString())).getDescription());
            intent.putExtra("image_url", Constants.IMAGE_BASE_SESSION);
            intent.putExtra("event_id", listModelSession.get(Integer.parseInt(marker.getTag().toString())).getId() + "");
            intent.putExtra("from", "sessions");
            intent.putExtra("gmapLat", listModelSession.get(Integer.parseInt(marker.getTag().toString())).getLatitude() + "");
            intent.putExtra("gmapLong", listModelSession.get(Integer.parseInt(marker.getTag().toString())).getLongitude() + "");
            Bundle b = new Bundle();
            b.putString("Array", listModelSession.get(Integer.parseInt(marker.getTag().toString())).getImages());
            intent.putExtras(b);
            startActivity(intent);

        }
        if (getIntent().getStringExtra("from").equalsIgnoreCase("3")) {
            Intent intent = new Intent(AllEventsMapAct.this, EventDetail.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("eventName", listModelPlace.get(Integer.parseInt(marker.getTag().toString())).getName());
            intent.putExtra("eventVenue", listModelPlace.get(Integer.parseInt(marker.getTag().toString())).getLocation());
            intent.putExtra("eventTime", listModelPlace.get(Integer.parseInt(marker.getTag().toString())).getOpen_hours_from());
            intent.putExtra("eventEndTime", listModelPlace.get(Integer.parseInt(marker.getTag().toString())).getOpen_hours_to());
            intent.putExtra("eventALLImages", listModelPlace.get(Integer.parseInt(marker.getTag().toString())).getImages());
//                intent.putExtra("eventDate", data.getAvailability_week());
            intent.putExtra("image_url", Constants.IMAGE_BASE_PLACE);
            intent.putExtra("event_id", listModelPlace.get(Integer.parseInt(marker.getTag().toString())).getId() + "");
            intent.putExtra("from", "places");
            intent.putExtra("desc", listModelPlace.get(Integer.parseInt(marker.getTag().toString())).getDescription());
            intent.putExtra("gmapLat", listModelPlace.get(Integer.parseInt(marker.getTag().toString())).getLatitude() + "");
            intent.putExtra("gmapLong", listModelPlace.get(Integer.parseInt(marker.getTag().toString())).getLongitude() + "");
            Bundle b = new Bundle();
            b.putString("Array", listModelPlace.get(Integer.parseInt(marker.getTag().toString())).getImages());
            intent.putExtras(b);
            data1 = new ArrayList<>();
            data1 = (ArrayList<String>) (listModelPlace.get(Integer.parseInt(marker.getTag().toString())).getAvailability_week());
            intent.putStringArrayListExtra(Constants.SPACE_DATA, data1);
            startActivity(intent);
        }
    }

    private void sportsListApi() {
        progressDialog.show();
        Call<SportListModel> call = api.getSportList(Constants.CONTENT_TYPE, "", "");
        call.enqueue(new Callback<SportListModel>() {
            @Override
            public void onResponse(Call<SportListModel> call, Response<SportListModel> response) {
                progressDialog.dismiss();
                if (response.body().isStatus()) {
//                    sport = new SportListModel.DataBeanX.DataBean();
//                    sport.setName("Select Sports");
//                    dropDownList.add(sport);
                    if (response.body() != null) {
                        dropDownList.addAll(response.body().getData().getData());

                    }
                }
            }

            @Override
            public void onFailure(Call<SportListModel> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void getSportName(String name) {
        mBottomSheetDialog.dismiss();
        search = name;
        currentPage = "";
        if (getIntent().getStringExtra("from").equalsIgnoreCase("1"))
            getAthleteEventApi(orderBy, search, sCoach_Id);
        else if (getIntent().getStringExtra("from").equalsIgnoreCase("2"))
            getAthleteSessionApi(orderBy, search, sCoach_Id);

    }
}
