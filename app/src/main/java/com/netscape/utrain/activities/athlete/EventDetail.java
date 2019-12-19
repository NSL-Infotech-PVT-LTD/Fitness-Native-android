package com.netscape.utrain.activities.athlete;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.EventBookingActivity;
import com.netscape.utrain.activities.SelectedServiceList;
import com.netscape.utrain.activities.SpaceBookingActivity;
import com.netscape.utrain.activities.organization.OrgHomeScreen;
import com.netscape.utrain.adapters.MyCustomPagerAdapter;
import com.netscape.utrain.adapters.ViewPagerAdapter;
import com.netscape.utrain.databinding.ActivityEventDetailBinding;
import com.netscape.utrain.model.AthletePlaceModel;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PrefrenceConstant;

import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Text;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.prefs.Preferences;

import me.relex.circleindicator.CircleIndicator;

public class EventDetail extends AppCompatActivity {

    MaterialTextView title;
    MaterialTextView endDateTime;
    String eventType = "";
    AppCompatImageView imgBackArrowImage;
    ViewPager viewPager;
    ViewPagerAdapter adapter;
    List<String> imageList = new ArrayList<>();
    MyCustomPagerAdapter pagerAdapter;
    private ActivityEventDetailBinding binding;
    private AthletePlaceModel placeModel;
    private String eventId;
    private String gmapLat="",gmapLong="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_event_detail);

        binding = DataBindingUtil.setContentView(EventDetail.this, R.layout.activity_event_detail);

        binding.eventBookingBackImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

        title = findViewById(R.id.title);
        binding.eventMarathonHeaderTv.setText(getIntent().getStringExtra("eventName"));
        binding.eventVanueDetailTv.setText(getIntent().getStringExtra("eventVenue"));//eventEndDateTime
        binding.eventTimeDetailTv.setText(getIntent().getStringExtra("eventTime"));
        binding.eventDateDetailTv.setText(getIntent().getStringExtra("eventDate"));
        binding.eventNumOfCandidateTv.setText(getIntent().getStringExtra("guest_allowed"));
        binding.seatNo.setText(getIntent().getStringExtra("guest_allowed_left"));
        eventId = getIntent().getStringExtra("event_id");
        gmapLat=getIntent().getStringExtra("gmapLat");
        gmapLong=getIntent().getStringExtra("gmapLong");



        binding.getDirectionImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(gmapLat) && ! TextUtils.isEmpty(gmapLong)) {
                    float latitude = Float.parseFloat(gmapLat);
                    float longitude = Float.parseFloat(gmapLong);
//                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", 28.7040, 77.1025);
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
//               startActivity(intent);

                    String uri = "http://maps.google.com/maps?q=loc:" + latitude + "," + longitude;
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    intent.setPackage("com.google.android.apps.maps");
                    startActivity(intent);
                }else {
                    Toast.makeText(EventDetail.this, "No lat long found", Toast.LENGTH_SHORT).show();
                }


//                String urlAddress = "http://maps.google.com/maps?q="+ gmapLat  +"," + gmapLong+"("+ "India" + ")&iwloc=A&hl=es";
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlAddress));
//                startActivity(intent);

//                Intent intent = null;
//                intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse("geo:19.076,72.8777"));
//                startActivity(intent);
//                float latitude = Float.parseFloat(gmapLat);
//                float longitude = Float.parseFloat(gmapLong);
//                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
//                        Uri.parse("http://maps.google.com/maps?saddr="+latitude +","+longitude));
//                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                startActivity(intent);
//                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
//                        Uri.parse("http://maps.google.com/maps?saddr="+latitude+","+longitude+""));
//                startActivity(intent);

//// Create a Uri from an intent string. Use the result to create an Intent.
//                Uri gmmIntentUri = Uri.parse("google.streetview:cbll="+gmapLat+","+gmapLong);
//
//// Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
//                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//// Make the Intent explicit by setting the Google Maps package
//                mapIntent.setPackage("com.google.android.apps.maps");
//
//// Attempt to start an activity that can handle the Intent
//                startActivity(mapIntent);
//
            }
        });

        if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, EventDetail.this).equalsIgnoreCase(Constants.Athlete)) {
            binding.inviteAthlete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                    sendIntent.setType("text/plain");

                    Intent shareIntent = Intent.createChooser(sendIntent, null);
                    startActivity(shareIntent);

                }
            });

        } else {
            binding.inviteAthlete.setVisibility(View.GONE);
            binding.view4.setVisibility(View.GONE);
        }

        binding.descriptionTv.setText(getIntent().getStringExtra("eventDescription"));


        if (getIntent().getStringExtra("from") != null)
            if (getIntent().getStringExtra("from").equalsIgnoreCase("places")) {
                title.setText("Spaces");
//                placeModel= (AthletePlaceModel) getIntent().getSerializableExtra(Constants.SPACE_DATA);
                if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, getApplicationContext()).equalsIgnoreCase(Constants.Organizer) ||CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, getApplicationContext()).equalsIgnoreCase(Constants.Coach)) {
                    binding.view1.setVisibility(View.GONE);
                }
                binding.eventNumOfCandidateAttendingTv.setVisibility(View.GONE);
                binding.eventCandidateTv.setVisibility(View.GONE);
                binding.eventNumOfCandidateTv.setVisibility(View.GONE);
                binding.noOfSeatText.setVisibility(View.GONE);
                binding.seatNo.setVisibility(View.GONE);
                binding.totalAvailableSeat.setVisibility(View.GONE);
                binding.view3.setVisibility(View.VISIBLE);
                binding.view4.setVisibility(View.GONE);
                binding.descriptionTv.setText(getIntent().getStringExtra("desc"));
                eventType = "space";
            }

        if (getIntent().getStringExtra("from") != null)
            if (getIntent().getStringExtra("from").equalsIgnoreCase("events")) {
                title.setText("Events");
                binding.eventNumOfCandidateAttendingTv.setVisibility(View.VISIBLE);
                binding.eventCandidateTv.setVisibility(View.VISIBLE);
                binding.eventNumOfCandidateTv.setVisibility(View.VISIBLE);
                binding.view2.setVisibility(View.VISIBLE);
                binding.eventNumOfCandidateTv.setText(getIntent().getIntExtra("capacity", 0) + "");
                eventType = "event";
                binding.noOfSeatText.setVisibility(View.VISIBLE);
                binding.seatNo.setVisibility(View.VISIBLE);
                binding.totalAvailableSeat.setVisibility(View.VISIBLE);
            }
        if (getIntent().getStringExtra("from") != null)
            if (getIntent().getStringExtra("from").equalsIgnoreCase("sessions")) {
                title.setText("Sessions");
                binding.eventNumOfCandidateAttendingTv.setVisibility(View.VISIBLE);
                binding.eventCandidateTv.setVisibility(View.VISIBLE);
                binding.eventNumOfCandidateTv.setVisibility(View.VISIBLE);
                binding.view2.setVisibility(View.VISIBLE);
                eventType = "session";
                binding.noOfSeatText.setVisibility(View.VISIBLE);
                binding.seatNo.setVisibility(View.VISIBLE);
                binding.totalAvailableSeat.setVisibility(View.VISIBLE);

            }
        Bundle b = getIntent().getExtras();
        if (b != null) {
            String Array = b.getString("Array");
            try {
                JSONArray jsonArray = new JSONArray(Array);
                for (int i = 0; i < jsonArray.length(); i++) {
                    imageList.add(String.valueOf(getIntent().getStringExtra("image_url") + jsonArray.get(i)));
                }
            } catch (JSONException e) {
                Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            imgBackArrowImage = findViewById(R.id.eventBookingBackImg);
            imgBackArrowImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, EventDetail.this).equalsIgnoreCase(Constants.Athlete)) {
                        Intent intent = new Intent(EventDetail.this, AthleteHomeScreen.class);
                        startActivity(intent);
                    } else if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, EventDetail.this).equalsIgnoreCase("Organizer")) {
                        Intent intent = new Intent(EventDetail.this, OrgHomeScreen.class);
                        startActivity(intent);
                    }
                }
            });

            binding.evntJoinNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (eventType.equalsIgnoreCase("space")) {
                        SelectedServiceList.getInstance().getList().clear();
                        Intent intent = new Intent(EventDetail.this, SpaceBookingActivity.class);
                        intent.putExtra("event_id", eventId);
                        intent.putExtra("eventName", binding.eventMarathonHeaderTv.getText());
                        intent.putExtra("eventVenue", binding.eventVanueDetailTv.getText());
                        intent.putExtra("eventTime", binding.eventTimeDetailTv.getText());
                        intent.putExtra("eventDate", binding.eventDateDetailTv.getText());
                        intent.putExtra("Array", getIntent().getIntExtra("image_url", 0));
                        intent.putExtra("type", eventType);
                        startActivity(intent);

                    } else {
                        Intent intent = new Intent(EventDetail.this, EventBookingActivity.class);
                        intent.putExtra("event_id", eventId);
                        intent.putExtra("eventName", binding.eventMarathonHeaderTv.getText());
                        intent.putExtra("seatLeft", binding.seatNo.getText());
                        intent.putExtra("eventVenue", binding.eventVanueDetailTv.getText());
                        intent.putExtra("eventTime", binding.eventTimeDetailTv.getText());
                        intent.putExtra("eventDate", binding.eventDateDetailTv.getText());
                        intent.putExtra("Array", getIntent().getIntExtra("image_url", 0));
                        intent.putExtra("type", eventType);
                        startActivity(intent);
                    }
                }
            });
            if (eventType.equalsIgnoreCase("space")) {
            } else {
                if (getIntent().getStringExtra("guest_allowed_left").equalsIgnoreCase("0")) {
                    binding.evntJoinNow.setClickable(false);
                    binding.evntJoinNow.setText("No Seats Available");
                }
            }
            viewPager = findViewById(R.id.viewPagerImage);
            pagerAdapter = new MyCustomPagerAdapter(this, imageList);
            viewPager.setAdapter(pagerAdapter);
            CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
            if (viewPager.getAdapter().getCount() <= 1)

                indicator.setViewPager(null);
            else
                indicator.setViewPager(viewPager);
        }
    }

}


