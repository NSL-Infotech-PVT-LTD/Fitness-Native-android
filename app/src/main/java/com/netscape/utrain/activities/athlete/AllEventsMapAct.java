package com.netscape.utrain.activities.athlete;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
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
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.AskPermission;
import com.netscape.utrain.activities.organization.GPSService;
import com.netscape.utrain.activities.organization.OrgMapFindAddressActivity;
import com.netscape.utrain.adapters.Ath_PlaceRecyclerAdapter;
import com.netscape.utrain.adapters.Ath_SessionRecyclerAdapter;
import com.netscape.utrain.adapters.CoachesRecyclerAdapter;
import com.netscape.utrain.model.AthleteEventListModel;
import com.netscape.utrain.model.AthletePlaceModel;
import com.netscape.utrain.model.AthleteSessionModel;
import com.netscape.utrain.response.AthleteEventListResponse;
import com.netscape.utrain.response.AthletePlaceResponse;
import com.netscape.utrain.response.AthleteSessionResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PaginationScrollListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllEventsMapAct extends AppCompatActivity implements OnMapReadyCallback {

    private static final int PAGE_START = 0;
    GoogleMap mGoogleMap;
    private AskPermission askPermObj;
    RecyclerView recyclerViewFindPlace;
    LinearLayoutManager layoutManager;
    CoachesRecyclerAdapter adapter;
    private GPSService gpsService;
    Ath_SessionRecyclerAdapter adapterSesson;
    Ath_PlaceRecyclerAdapter adapterPlace;
    ProgressBar progressBar;
    private List<AthleteEventListModel> listModels = new ArrayList<>();
    private List<AthleteSessionModel> listModelSession = new ArrayList<>();
    private List<AthletePlaceModel> listModelPlace = new ArrayList<>();
    private Retrofitinterface api;
    private AllEventsMapAct activity;
    private LatLng latng;
    private ConstraintLayout layoutBottomSheet, bottomsheet_list_laout;
    private AppCompatImageView filterIcon;
    private AppCompatAutoCompleteTextView searchAtuoCompleteEdt;
    private BottomSheetBehavior sheetBehavior, bottomsheet_list;
    private TextView sort_distance, sort_high, sort_low, sort_latest;
    private int sort_count = 1;
    private String search;
    private MaterialTextView allEventFindAPalceTv;
    private ConstraintLayout constraint_background;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 3;
    private int currentPage = PAGE_START;
    private int page = 1;
    private  MapFragment map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (googleServicesAvailable()) {
//            Toast.makeText(AllEventsWithMap.this, "Perfect!!!!", Toast.LENGTH_LONG).show();
        setContentView(R.layout.activity_all_events_map);
        inItMap();
//        } else {
//            // else Google map Layout.....
//        }

        activity = this;
        progressBar = (ProgressBar) findViewById(R.id.main_progress);
        layoutBottomSheet = findViewById(R.id.bottomsheet_sort);
        bottomsheet_list_laout = findViewById(R.id.bottomsheet_list);
        allEventFindAPalceTv = findViewById(R.id.allEventFindAPalceTv);
        constraint_background = findViewById(R.id.constraint_background);
        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        bottomsheet_list = BottomSheetBehavior.from(bottomsheet_list_laout);
        recyclerViewFindPlace = findViewById(R.id.findPlaceRecycler);
        filterIcon = findViewById(R.id.filterIcon);
        searchAtuoCompleteEdt = findViewById(R.id.searchAtuoCompleteEdt);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewFindPlace.setLayoutManager(layoutManager);


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
        searchAtuoCompleteEdt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search = searchAtuoCompleteEdt.getText().toString();
                    if (getIntent().getStringExtra("from").equalsIgnoreCase("1")) {
                        constraint_background.setBackground(getResources().getDrawable(R.drawable.card_shape_outline));
                        getAthleteEventApi("distance", search);
                    } else if (getIntent().getStringExtra("from").equalsIgnoreCase("2")) {
                        constraint_background.setBackground(getResources().getDrawable(R.drawable.card_shape_outline_skyblue_bottom_round));
                        getAthleteSessionApi("distance", search);
                    } else if (getIntent().getStringExtra("from").equalsIgnoreCase("3")) {
                        constraint_background.setBackground(getResources().getDrawable(R.drawable.card_shape_outline_yellow_top_round));
                        getAthletePlaceApi("distance", search);
                    }
                    return true;
                }
                return false;
            }
        });


//        pagination(adapter, listModels);

    }

    private void getAthletePlaceApi(final String order_by, final String s) {
        allEventFindAPalceTv.setText("Find Spaces");
        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Loading Spaces....");
        progressDialog.show();
        api = RetrofitInstance.getClient().create(Retrofitinterface.class);
        Call<AthletePlaceResponse> call = api.getAthletePlacesList("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, activity), Constants.CONTENT_TYPE, s, "10", order_by);
        call.enqueue(new Callback<AthletePlaceResponse>() {
            @Override
            public void onResponse(Call<AthletePlaceResponse> call, Response<AthletePlaceResponse> response) {
                progressDialog.dismiss();
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
                                mGoogleMap.addMarker(new MarkerOptions().position(latng).title("")
                                        .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));

                                CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latng, 6f);
//                                mGoogleMap.animateCamera(update);
                                adapter = new CoachesRecyclerAdapter(activity, listModels);
                                recyclerViewFindPlace.setAdapter(adapter);
                            }
                        }
                        adapterPlace = new Ath_PlaceRecyclerAdapter(activity, listModelPlace);
                        recyclerViewFindPlace.setAdapter(adapterPlace);
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

                                getAthletePlaceApi(order_by, adapterView.getItemAtPosition(i).toString());
                            }
                        });
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

    private void getAthleteSessionApi(final String orderBy, final String s) {
        allEventFindAPalceTv.setText("Find Sessions");
        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Loading Session....");
        progressDialog.show();
        api = RetrofitInstance.getClient().create(Retrofitinterface.class);
        Call<AthleteSessionResponse> call = api.getAthleteSessionList("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, activity), Constants.CONTENT_TYPE, s, "10", orderBy);
        call.enqueue(new Callback<AthleteSessionResponse>() {
            @Override
            public void onResponse(Call<AthleteSessionResponse> call, Response<AthleteSessionResponse> response) {

                progressDialog.dismiss();
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
                                mGoogleMap.addMarker(new MarkerOptions().position(latng).title("")
                                        .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));

                            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latng, 6f);
//                            mGoogleMap.animateCamera(update);

                            }
                            adapter = new CoachesRecyclerAdapter(activity, listModels);
                            recyclerViewFindPlace.setAdapter(adapter);
                        }
                        adapterSesson = new Ath_SessionRecyclerAdapter(activity, listModelSession);
                        recyclerViewFindPlace.setAdapter(adapterSesson);
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

                                getAthleteSessionApi(orderBy, adapterView.getItemAtPosition(i).toString());
                            }
                        });
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

    private void getAthleteEventApi(final String sortBy, final String search) {
        allEventFindAPalceTv.setText("Find Events");
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        api = RetrofitInstance.getClient().create(Retrofitinterface.class);
        Call<AthleteEventListResponse> call = api.getAthleteEventList("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, activity), Constants.CONTENT_TYPE, sortBy, search, "10", page + "", "100");
        call.enqueue(new Callback<AthleteEventListResponse>() {
            @Override
            public void onResponse(Call<AthleteEventListResponse> call, final Response<AthleteEventListResponse> response) {

                progressDialog.dismiss();
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
                            mGoogleMap.addMarker(new MarkerOptions().position(latng).title("")
                                    .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));

                            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latng, 6f);
//                            mGoogleMap.animateCamera(update);
                            adapter = new CoachesRecyclerAdapter(activity, listModels);
                            recyclerViewFindPlace.setAdapter(adapter);
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

                                getAthleteEventApi(sortBy, adapterView.getItemAtPosition(i).toString());
                            }
                        });

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
                if (getIntent().getStringExtra("from").equalsIgnoreCase("1"))
                    getAthleteEventApi("distance", search);
                else if (getIntent().getStringExtra("from").equalsIgnoreCase("2"))
                    getAthleteSessionApi("distance", search);
                else if (getIntent().getStringExtra("from").equalsIgnoreCase("3"))
                    getAthletePlaceApi("distance", search);
            }
        });
        sort_high.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sort_high.setTypeface(null, Typeface.BOLD);
                bottomSheetUpDown_address();
                sort_count = 2;
                if (getIntent().getStringExtra("from").equalsIgnoreCase("1"))
                    getAthleteEventApi("price_high", search);
                else if (getIntent().getStringExtra("from").equalsIgnoreCase("2"))
                    getAthleteSessionApi("price_high", search);
                else if (getIntent().getStringExtra("from").equalsIgnoreCase("3"))
                    getAthletePlaceApi("price_high", search);

            }
        });
        sort_latest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sort_latest.setTypeface(null, Typeface.BOLD);
                bottomSheetUpDown_address();
                sort_count = 4;
                if (getIntent().getStringExtra("from").equalsIgnoreCase("1"))
                    getAthleteEventApi("latest", search);
                else if (getIntent().getStringExtra("from").equalsIgnoreCase("2"))
                    getAthleteSessionApi("latest", search);
                else if (getIntent().getStringExtra("from").equalsIgnoreCase("3"))
                    getAthletePlaceApi("latest", search);

            }
        });
        sort_low.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sort_low.setTypeface(null, Typeface.BOLD);
                bottomSheetUpDown_address();
                sort_count = 3;
//                getAthleteEventApi("price_low", search);
                if (getIntent().getStringExtra("from").equalsIgnoreCase("1"))
                    getAthleteEventApi("price_low", search);
                else if (getIntent().getStringExtra("from").equalsIgnoreCase("2"))
                    getAthleteSessionApi("price_low", search);
                else if (getIntent().getStringExtra("from").equalsIgnoreCase("3"))
                    getAthletePlaceApi("price_low", search);
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
        if (getIntent().getStringExtra("from").equalsIgnoreCase("1")) {
            constraint_background.setBackground(getResources().getDrawable(R.drawable.card_shape_outline));
            getAthleteEventApi("distance", search);
        } else if (getIntent().getStringExtra("from").equalsIgnoreCase("2")) {
            constraint_background.setBackground(getResources().getDrawable(R.drawable.card_shape_outline_skyblue_bottom_round));
            getAthleteSessionApi("distance", search);
        } else if (getIntent().getStringExtra("from").equalsIgnoreCase("3")) {
            constraint_background.setBackground(getResources().getDrawable(R.drawable.card_shape_outline_yellow_top_round));
            getAthletePlaceApi("distance", search);
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
    private void pagination(final CoachesRecyclerAdapter adapter, final List<AthleteEventListModel> listModels) {
        recyclerViewFindPlace.addOnScrollListener(new PaginationScrollListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                // mocking network delay for API call
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadNextPage(adapter, listModels);
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


        // mocking network delay for API call
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadFirstPage(adapter, listModels);
            }
        }, 1000);


    }


    private void loadFirstPage(CoachesRecyclerAdapter adapter, List<AthleteEventListModel> listModels) {
//        listModels = AthleteEventListModel.createMovies(adapter.getItemCount());
        if (adapter != null) {
            progressBar.setVisibility(View.GONE);
            getAthleteEventApi("latest", "");

            Toast.makeText(activity, "" + adapter.getItemCount(), Toast.LENGTH_SHORT).show();

            if (currentPage <= TOTAL_PAGES) adapter.addLoadingFooter();
            else isLastPage = true;

        }
    }

    private void loadNextPage(CoachesRecyclerAdapter adapter, List<AthleteEventListModel> listModels) {
//        listModels = AthleteEventListModel.createMovies(adapter.getItemCount());

        if (adapter != null) {
            adapter.removeLoadingFooter();
            isLoading = false;

            page++;
            getAthleteEventApi("latest", "");

            if (currentPage != TOTAL_PAGES) adapter.addLoadingFooter();
            else isLastPage = true;
        }
    }
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
}
