package com.netscape.utrain.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.netscape.utrain.R;
import com.netscape.utrain.adapters.CoachesRecyclerAdapter;
import com.netscape.utrain.model.AthleteEventListModel;
import com.netscape.utrain.response.AthleteEventListResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllEventsWithMap extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap mGoogleMap;

    RecyclerView recyclerViewFindPlace;
    RecyclerView.LayoutManager layoutManager;
    CoachesRecyclerAdapter adapter;
    private List<AthleteEventListModel> listModels = new ArrayList<>();
    private Retrofitinterface api;
    private AllEventsWithMap activity;
    private LatLng latng;
    private LinearLayout layoutBottomSheet;
    private AppCompatImageView filterIcon;
    private AppCompatEditText searchAtuoCompleteEdt;

    private BottomSheetBehavior sheetBehavior;
    private TextView sort_distance, sort_high, sort_low, sort_latest;
    private int sort_count = 4;
    private String search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (googleServicesAvailable()) {
//            Toast.makeText(AllEventsWithMap.this, "Perfect!!!!", Toast.LENGTH_LONG).show();
            setContentView(R.layout.activity_all_events_with_map);
            inItMap();
        } else {
            // else Google map Layout.....
        }

        activity = this;
        layoutBottomSheet = findViewById(R.id.bottomsheet_sort);
        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        recyclerViewFindPlace = findViewById(R.id.findPlaceRecycler);
        filterIcon = findViewById(R.id.filterIcon);
        searchAtuoCompleteEdt = findViewById(R.id.searchAtuoCompleteEdt);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewFindPlace.setLayoutManager(layoutManager);

        bottomSheetBehavior_sort();
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
                    getAthleteEventApi("latest", search);
                    return true;
                }
                return false;
            }
        });
        getAthleteEventApi("latest", "");
    }


    private void getAthleteEventApi(String sortBy, String search) {
        api = RetrofitInstance.getClient().create(Retrofitinterface.class);
        Call<AthleteEventListResponse> call = api.getAthleteEventList("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, activity), Constants.CONTENT_TYPE, sortBy, search, "1000000");
        call.enqueue(new Callback<AthleteEventListResponse>() {
            @Override
            public void onResponse(Call<AthleteEventListResponse> call, Response<AthleteEventListResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().isStatus()) {
                        listModels.clear();
                        listModels.addAll(response.body().getData());

                        int value = response.body().getData().size();

                        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.sophie);
                        Bitmap b = bitmapdraw.getBitmap();
                        Bitmap smallMarker = Bitmap.createScaledBitmap(b, 20, 20, false);
                        mGoogleMap.clear();
                        for (int i = 0; i < value; i++) {
                            latng = new LatLng(Double.parseDouble(response.body().getData().get(i).getLatitude()), Double.parseDouble(response.body().getData().get(i).getLongitude()));
                            mGoogleMap.addMarker(new MarkerOptions().position(latng).title("")
                                    .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));

                            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latng, 6f);
                            mGoogleMap.animateCamera(update);
                            adapter = new CoachesRecyclerAdapter(activity, listModels);
                            recyclerViewFindPlace.setAdapter(adapter);
                        }
                    }
                }


            }

            @Override
            public void onFailure(Call<AthleteEventListResponse> call, Throwable t) {

                Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


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

    private void inItMap() {

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);

    }


    private void bottomOnClickSort() {

        sort_distance = findViewById(R.id.sort_distance);
        sort_high = findViewById(R.id.sort_high);
        sort_low = findViewById(R.id.sort_low);
        sort_latest = findViewById(R.id.sort_latest);

        if (sort_count == 2) {
            checkClick();
            sort_high.setTypeface(null, Typeface.BOLD);
            sort_high.setTextColor(getResources().getColor(R.color.colorAccent));

        } else if (sort_count == 3) {
            checkClick();
            sort_low.setTypeface(null, Typeface.BOLD);
            sort_low.setTextColor(getResources().getColor(R.color.colorAccent));

        } else if (sort_count == 1) {
            checkClick();
            sort_distance.setTypeface(null, Typeface.BOLD);
            sort_distance.setTextColor(getResources().getColor(R.color.colorAccent));
        } else if (sort_count == 4) {
            checkClick();
            sort_latest.setTypeface(null, Typeface.BOLD);
            sort_latest.setTextColor(getResources().getColor(R.color.colorAccent));

        }
        sort_distance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sort_distance.setTypeface(null, Typeface.BOLD);
                bottomSheetUpDown_address();
                sort_count = 1;
                getAthleteEventApi("distance",search);

            }
        });
        sort_high.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sort_high.setTypeface(null, Typeface.BOLD);
                bottomSheetUpDown_address();
                sort_count = 2;
                getAthleteEventApi("price_high",search);

            }
        });
        sort_latest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sort_latest.setTypeface(null, Typeface.BOLD);
                bottomSheetUpDown_address();
                sort_count = 4;
                getAthleteEventApi("latest",search);

            }
        });
        sort_low.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sort_low.setTypeface(null, Typeface.BOLD);
                bottomSheetUpDown_address();
                sort_count = 3;
                getAthleteEventApi("price_low",search);

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
        int isAvailable = api.isGooglePlayServicesAvailable(AllEventsWithMap.this);
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (api.isUserResolvableError(isAvailable)) {

            Dialog dialog = api.getErrorDialog(AllEventsWithMap.this, isAvailable, 0);
            dialog.show();
        } else {
            Toast.makeText(AllEventsWithMap.this, "Cant connect to play store", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
    }
}
