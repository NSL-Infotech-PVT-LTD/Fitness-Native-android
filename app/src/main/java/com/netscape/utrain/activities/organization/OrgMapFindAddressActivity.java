package com.netscape.utrain.activities.organization;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.textview.MaterialTextView;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.AskPermission;
import com.netscape.utrain.utils.Constants;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class OrgMapFindAddressActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener,
        View.OnClickListener, GoogleMap.OnMapClickListener {
    private GoogleMap mGoogleMap;
    private MapView mapView;
    private AskPermission askPermObj;
    private GPSService gpsService;
    private LatLng selectedLatLng;
    private String mAddress, placeName;
    private boolean isSignUpFlow = false;
    private String TAG = OrgMapFindAddressActivity.class.getSimpleName();
    private String locationLatLng = "";
    private String locationLat = "";
    private String locationLong = "";
    private RelativeLayout confirmLocRel;
    private LinearLayout searchLin;
    private MaterialTextView searchEdt;
    SupportMapFragment mapFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_map_find_address);
        confirmLocRel=findViewById(R.id.confirmLocRel);
        searchLin=findViewById(R.id.searchLin);
        searchEdt=findViewById(R.id.searchEdt);
//        SupportMapFragment mapFragment = (SupportMapFragment) this.getSupportFragmentManager().
//                findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
//        MapFragment mapFragment = ((MapFragment) getFragmentManager().findFragmentById(R.id.map));
//        mapFragment.getMapAsync(this);
        inIt();
    }


    public void inIt() {
        askPermObj = new AskPermission(OrgMapFindAddressActivity.this, this);
//        setupToolBar(OrgMapFindAddressActivity.this.getResources().getString(R.string.find_address));

//        binding.searchImgBtn.setOnClickListener(this);
//        binding.searchEdt.setOnClickListener(this);

        confirmLocRel.setOnClickListener(this);
        searchLin.setOnClickListener(this);

    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(OrgMapFindAddressActivity.this);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mGoogleMap = googleMap;

        mGoogleMap.getUiSettings().setZoomControlsEnabled(false);
        mGoogleMap.getUiSettings().setRotateGesturesEnabled(true);
        mGoogleMap.getUiSettings().setScrollGesturesEnabled(true);
        mGoogleMap.getUiSettings().setTiltGesturesEnabled(true);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
        mGoogleMap.getUiSettings().setMapToolbarEnabled(false);
        mGoogleMap.setPadding(0,(int)getDptoPx(OrgMapFindAddressActivity.this,60),0,(int)getDptoPx(OrgMapFindAddressActivity.this,60));

        mGoogleMap.setOnMapClickListener(this);

        if (askPermObj.isPermissionGiven(this, Manifest.permission.ACCESS_FINE_LOCATION))
            mGoogleMap.setMyLocationEnabled(true);

        askPermission();
//        mGoogleMap.setOnMapLongClickListener(this);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
//        AppDelegate.Log("MapLongClick ", latLng.latitude + " " + latLng.longitude);

        if (latLng != null) {
            selectedLatLng = latLng;
            getAddress(OrgMapFindAddressActivity.this, latLng.latitude, latLng.longitude);
            if (mGoogleMap != null)
                setMarkerOnMap(latLng);
        }
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

    /*function getting the current location of user*/
    private void getUserCurrentLocation() {
        gpsService = new GPSService(OrgMapFindAddressActivity.this);
        Location userLocation = gpsService.getLocation();
        if (userLocation != null) {
            LatLng placeOn = new LatLng(userLocation.getLatitude(), userLocation.getLongitude());
            CameraUpdate zoom = CameraUpdateFactory.zoomTo(14);
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(placeOn));
            mGoogleMap.animateCamera(zoom);
        }
    }

    /*permission granted/denied listener*/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constants.ACCESS_FINE_LOCATION_REQUEST) {
            /*detects whether write permission is given*/
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (mGoogleMap != null)
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                mGoogleMap.setMyLocationEnabled(true);
                askPermission();
            }
        }
    }

    /*=========code to get the addres using the latlong=========*/
    public String getAddress(Context ctx, double lat, double lng) {
        String fullAdd = null;
        try {
            Geocoder geocoder = new Geocoder(ctx, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                fullAdd = address.getAddressLine(0);
                locationLatLng =lat+","+ lng;
                // if you want only city or pin code use following code //
           /* String Location = address.getLocality();
            String zip = address.getPostalCode();
            String Country = address.getCountryName(); */
            }


        } catch (IOException ex) {
            ex.printStackTrace();
        }
       searchEdt.setText(fullAdd);
        return fullAdd;
    }
    /*==================================================*/

    /*=========code to get lat lng from address==========*/
    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (IOException ex) {

            ex.printStackTrace();
        }
        return p1;
    }

    /*code to add the marker on the location using lat lng*/
    private void setMarkerOnMap(LatLng latLng) {
        mGoogleMap.clear();
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_map);
        mGoogleMap.addMarker(new MarkerOptions()
                .position(latLng)
                .icon(icon));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(14);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mGoogleMap.animateCamera(zoom);
    }

    /*====================================================*/

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.searchImgBtn:
                if (!searchEdt.getText().toString().trim().isEmpty()) {
                    LatLng latLng = getLocationFromAddress(OrgMapFindAddressActivity.this,
                            searchEdt.getText().toString().trim());
                    if (latLng != null) {
                        selectedLatLng = latLng;
                        if (mGoogleMap != null)
                            setMarkerOnMap(latLng);
                    }
                }
                break;
            case R.id.confirmLocRel:
//                AppDelegate.Log(TAG,"confirm Loc Rel calling ");
                if (searchEdt.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Empty address", Toast.LENGTH_SHORT).show();
//                    AppDelegate.showToast(OrgMapFindAddressActivity.this, OrgMapFindAddressActivity.this.getResources().getString(R.string.empty_address));
                    return;
                }
                if(isSignUpFlow){
                    Intent intent = new Intent();
                    intent.putExtra(Constants.ADDRESS,searchEdt.getText().toString());
//                    locationLatLng = selectedLatLng.latitude +","+ selectedLatLng.longitude;
                    locationLat=selectedLatLng.latitude+"";
                    locationLong=selectedLatLng.longitude+"";
                    intent.putExtra(Constants.LOCATION_LAT,locationLat);
                    intent.putExtra(Constants.LOCATION_LONG,locationLong);
                    setResult(RESULT_OK,intent);
                    OrgMapFindAddressActivity.this.finish();
                }else {
//                    validateValues();
                }
                break;
            case R.id.searchLin:
                try {
                    AutocompleteFilter autocompleteFilter = new AutocompleteFilter.Builder()
                            .setTypeFilter(Place.TYPE_COUNTRY)
                            .setCountry("US")
                            .build();
                    Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).
                            setFilter(autocompleteFilter).
                            build(this);
                    startActivityForResult(intent, Constants.REQUEST_CODE_GOOGLE_PLACE_SEARCH);
                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
//                    e.printStackTrace();
                }
//                try {
//                    Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).build(this);
//                    startActivityForResult(intent, Constants.REQUEST_CODE_GOOGLE_PLACE_SEARCH);
//                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
//                    // TODO: Handle the error.
//                }

                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_CODE_GOOGLE_PLACE_SEARCH) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                selectedLatLng = place.getLatLng();
//                mAddress = place.getName().toString();
                // placeName = place.getAddress().toString().replace(place.getName().toString(), "");

                placeName = place.getName().toString();

                mAddress =  place.getAddress().toString().replace(place.getName().
                        toString(),"");

//                AppDelegate.Log("OnPlaceSelectResult--->", mAddress + " " + place.getAddress().toString());
                setMarkerOnMap(selectedLatLng);
                searchEdt.setText(placeName+ " "+mAddress);

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
//                AppDelegate.LogE(status.getStatusMessage());
            }
        }
    }
    @Override
    public void onMapClick(LatLng latLng) {
        setMarkerOnMap(latLng);
    }
    public static float getDptoPx(Context context,int dip){
        Resources r = context.getResources();
        return  TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dip,
                r.getDisplayMetrics()
        );
    }
}
