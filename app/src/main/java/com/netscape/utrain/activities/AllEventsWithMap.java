package com.netscape.utrain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.netscape.utrain.R;
import com.netscape.utrain.adapters.FindPlaceAdapter;

import java.util.ArrayList;
import java.util.List;

public class AllEventsWithMap extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap mGoogleMap;

    RecyclerView recyclerViewFindPlace;
    RecyclerView.LayoutManager layoutManager;
    FindPlaceAdapter adapter;
    List<String> dataList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (googleServicesAvailable()){
            Toast.makeText(AllEventsWithMap.this, "Perfect!!!!", Toast.LENGTH_LONG).show();
            setContentView(R.layout.activity_all_events_with_map);
            inItMap();
        } else
        {
            // else Google map Layout.....
        }

        recyclerViewFindPlace = findViewById(R.id.findPlaceRecycler);
        layoutManager = new LinearLayoutManager(this);
        dataList.add("Silvesta park | Upto 70 Candidate");
        dataList.add("Sukhna Lake | Upto 60 Candidate");
        dataList.add("Abc park | Upto 80 Candidate");
        dataList.add("xyz park | Upto 90 Candidate");
        dataList.add("Silvesta park | Upto 50 Candidate");

        adapter = new FindPlaceAdapter(AllEventsWithMap.this,dataList);
        recyclerViewFindPlace.setLayoutManager(layoutManager);
        recyclerViewFindPlace.setAdapter(adapter);


    }

    private void inItMap() {

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);

    }

    public boolean googleServicesAvailable() {

        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(AllEventsWithMap.this);
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (api.isUserResolvableError(isAvailable)){

            Dialog dialog = api.getErrorDialog(AllEventsWithMap.this,isAvailable,0);
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
